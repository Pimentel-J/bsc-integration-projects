using Microsoft.AspNetCore.Mvc;
using System.Collections.Generic;
using System;
using System.Threading.Tasks;
using MDV.Services;
using MDV.DTO;
using System.Xml;
using System.IO;
using MDV.Domain.Blocos;

namespace MDV.Controllers
{
    [Route("api/importarDados")]
    [ApiController]

    public class ImportarDadosController : ControllerBase
    {
        private readonly CriarViagemService _criarViagemService;
        private readonly ServicoViaturaService _servicoViaturaService;
        private readonly ViagemService _viagemService;
        private readonly BlocoService _blocoService;
        private readonly ServicoTripulanteService _servicoTripulanteService;

        public ImportarDadosController(
            CriarViagemService criarViagemService,
            ServicoViaturaService servicoViaturaService,
            ViagemService viagemService,
            BlocoService blocoService,
            ServicoTripulanteService servicoTripulanteService
            )
        {
            _criarViagemService = criarViagemService;
            _servicoViaturaService = servicoViaturaService;
            _viagemService = viagemService;
            _blocoService = blocoService;
            _servicoTripulanteService = servicoTripulanteService;
        }

        [HttpPost]
        public async Task<ActionResult<ImportarDadosDTO>> Create([FromForm] ImportarDadosDTO dto)
        {

            //Converter de IFormFile (atributo do ImportarDadosDTO) para Stream
            Stream streamFicheiro = dto.ficheiro.OpenReadStream();

            //Carregar ficheiro para Doc XML
            XmlDocument doc = new XmlDocument();
            doc.Load(streamFicheiro);

            //Pedir para criar viagens na BD
            await CriarViagens(doc);

            //Pedir para criar serviços viatura na BD
            await CriarServicosViatura(doc);

            //Pedir para criar blocos de trabalho na BD
            await CriarBlocos(doc);

            //Pedir para criar serviços tripulante na BD
            await CriarServicosTripulante(doc);

            return dto;
        }

        private async Task CriarViagens(XmlDocument doc)
        {
            int contadorViagens = 0;
            List<ViagemDTO> viagens = new List<ViagemDTO>();

            //Percorrer viagens (Trips) existentes no ficheiro
            foreach (XmlNode viagem in doc.GetElementsByTagName("Trip"))
            {
                //Obter ID da viagem
                string KeyViagem = viagem.Attributes["key"].Value;

                //Obter ID do percurso da viagem
                string IDPercurso = GetIDPercurso(viagem.Attributes["Path"].Value);

                //Obter Hora de Inicio da viagem (primeira passagem)
                int horaInicio = GetHoraInicio(viagem.ChildNodes[0].ChildNodes);

                viagens.Add(new ViagemDTO(KeyViagem, IDPercurso, horaInicio));

                contadorViagens++;
                
            }
            Console.WriteLine("Viagens identificadas: " + contadorViagens);
            await _criarViagemService.CriarViagensImportadas(viagens);
        }

        private async Task CriarServicosViatura(XmlDocument doc)
        {
            int contadorServicosViatura = 0;
            

            //Percorrer servicos viatura (VehicleDuties) existentes no ficheiro
            foreach (XmlNode servicoViatura in doc.GetElementsByTagName("VehicleDuty"))
            {
                //Obter ID do servico viatura
                string IDServicoViatura = servicoViatura.Attributes["Name"].Value;
                Console.WriteLine(IDServicoViatura);

                var viagens = await GetViagens(servicoViatura.ChildNodes[0].ChildNodes, doc);
                
                try
                {
                    await _servicoViaturaService.AddAsync(new CreatingServicoViaturaDto(IDServicoViatura, viagens));
                    contadorServicosViatura++;
                }
                catch (Exception)
                {
                    Console.WriteLine(IDServicoViatura + " nao criado por falha nas validacoes");
                    //throw;
                }
                
            }
            Console.WriteLine("Servicos Viatura criados: " + contadorServicosViatura);
            
        }

        private async Task CriarBlocos(XmlDocument doc)
        {
            int contadorBlocos = 0;

            //Percorrer blocos (WorkBlocks) existentes no ficheiro
            foreach (XmlNode bloco in doc.GetElementsByTagName("WorkBlock"))
            {
                //Obter atributos do bloco
                string codigoBloco = bloco.Attributes["key"].Value;
                int startTimeBloco = int.Parse(bloco.Attributes["StartTime"].Value);
                int endTimeBloco = int.Parse(bloco.Attributes["EndTime"].Value);
                string servicoViaturaID = null;

                //Obter viagens do bloco
                List<string> viagens = new List<string>();
                foreach (XmlNode viagem in bloco.ChildNodes[0].ChildNodes)
                {
                    var viagemDTO = await _viagemService.GetByKey(viagem.Attributes["key"].Value);
                    if (viagemDTO != null)
                    {
                        string viagemID = viagemDTO.Id.ToString();
                        if (!viagens.Contains(viagemID))
                        {
                            viagens.Add(viagemID);
                            if (servicoViaturaID == null)
                            {
                                servicoViaturaID = viagemDTO.ServicoViaturaId;
                            }
                        }
                    }
                    else
                    {
                        Console.WriteLine(viagem.Attributes["key"].Value + " nao existe");
                        goto NextBloco; //Nao cria este bloco por faltar (pelo menos) uma das viagens
                    }
                }
                if (servicoViaturaID == null)
                {
                    goto NextBloco;
                }
                
                try
                {
                    await _blocoService.AddAsync(new CreatingBlocoDTO(codigoBloco,startTimeBloco,endTimeBloco,viagens,servicoViaturaID));
                    contadorBlocos++;
                }
                catch (Exception)
                {
                    Console.WriteLine(codigoBloco + " nao criado por falha nas validacoes");
                    //throw;
                }

            NextBloco:;
            }
            Console.WriteLine("Blocos criados: " + contadorBlocos);

        }

        private async Task CriarServicosTripulante(XmlDocument doc)
        {
            int contadorServicosTripulante = 0;

            //Percorrer servicos viatura (VehicleDuties) existentes no ficheiro
            foreach (XmlNode servicoTripulante in doc.GetElementsByTagName("DriverDuty"))
            {
                //Obter ID do servico tripulante
                string IDServicoTripulante = servicoTripulante.Attributes["Name"].Value;
                Console.WriteLine(IDServicoTripulante);
                
                try
                {
                    await _servicoTripulanteService.AddAsync(new CreatingServicoTripulanteDTO(IDServicoTripulante,IDServicoTripulante));
                    contadorServicosTripulante++;
                }
                catch (Exception)
                {
                    Console.WriteLine(IDServicoTripulante + " nao criado por falha nas validacoes");
                    throw;
                }
                foreach (XmlNode bloco in servicoTripulante.ChildNodes[0].ChildNodes)
                {
                    string blocoID = bloco.Attributes["key"].Value;
                    BlocoDTO blocoDTO = await _blocoService.GetByIdAsync(new BlocoId(blocoID));
                    if (blocoDTO != null)
                    {
                        blocoDTO.ServicoTripulanteId = IDServicoTripulante;
                        await _blocoService.UpdateServicoTripulanteAsync(blocoDTO);
                    }
                }

            }
            Console.WriteLine("Servicos Tripulante criados: " + contadorServicosTripulante);

        }

        private async Task<List<string>> GetViagens(XmlNodeList blocos, XmlDocument doc)
        {
            List<ViagemDTO> viagensDTO = new List<ViagemDTO>();
            List<string> viagens = new List<string>();
            foreach (XmlNode blocoServicoViatura in blocos)
            {
                foreach (XmlNode bloco in doc.GetElementsByTagName("WorkBlock"))
                {
                    if (bloco.Attributes["key"].Value == blocoServicoViatura.Attributes["key"].Value)
                    {
                        foreach (XmlNode viagem in bloco.ChildNodes[0].ChildNodes)
                        {
                            var viagemDTO = await _viagemService.GetByKey(viagem.Attributes["key"].Value);
                            if (viagemDTO != null)
                            {
                                viagensDTO.Add(viagemDTO);
                            } else
                            {
                                //Console.WriteLine(viagem.Attributes["key"].Value + " nao existe");
                            }
                            
                        }
                    }
                }
            }
            // ordenar as viagens antes de enviar
            viagensDTO.Sort((x, y) => x.HoraInicio.CompareTo(y.HoraInicio));
            foreach (ViagemDTO viagem in viagensDTO)
            {
                string viagemID = viagem.Id.ToString();
                if (!viagens.Contains(viagemID))
                {
                    viagens.Add(viagemID);
                }
            }
            return viagens;
        }

        private string GetIDPercurso(string IDPercursoRaw)
        {
            string[] IDPercursoSplit = IDPercursoRaw.Split(':');
            string IDPercurso = IDPercursoSplit[1];
            return IDPercurso;
        }

        private int GetHoraInicio(XmlNodeList passagens)
        {
            int min = int.MaxValue;
            foreach (XmlNode passagem in passagens)
            {
                int time = int.Parse(passagem.Attributes["Time"].Value);
                if (time < min)
                {
                    min = time;
                }
            }
            return min;
        }

        

    }
}
    