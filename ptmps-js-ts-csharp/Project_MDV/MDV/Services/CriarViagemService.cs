using System;
using Microsoft.AspNetCore.Mvc;
using System.Threading.Tasks;
using System.Collections.Generic;
using System.Net.Http;
using System.Net.Http.Headers;
using MDV.Repositories;
using MDV.Domain.Viagens;
using MDV.Domain.Passagens;
using MDV.Domain.Shared;
using MDV.DTO;
using Newtonsoft.Json.Linq;
using System.Linq;

namespace MDV.Services
{

    public class CriarViagemService
    {
        private readonly IUnitOfWork _unitOfWork;
        private readonly IViagemRepository _repoViagem;
        private readonly IPassagemRepository _repoPassagem;

        public CriarViagemService(IUnitOfWork unitOfWork, IViagemRepository repoViagem, IPassagemRepository repoPassagem)
        {
            this._unitOfWork = unitOfWork;
            this._repoViagem = repoViagem;
            this._repoPassagem = repoPassagem;
        }



        public async Task<ViagemDTO> CriarViagem(int horaSaida, string codPercurso)
        {
            string percursoUrl = Config.WebApiApplicationJson() + "percursos/";
            var client = new HttpClient();
            client.DefaultRequestHeaders.Accept.Clear();
            client.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));
            Console.WriteLine(percursoUrl);

            HttpResponseMessage resp = await client.GetAsync(percursoUrl + codPercurso);

            if (resp.Content != null)
            {
                JObject percurso = JObject.Parse(resp.Content.ReadAsStringAsync().Result);
                return await PersistirViagem(null, horaSaida, codPercurso, percurso);
            }
            Console.WriteLine("Codigo de Percurso Invalido.");
            BadRequestObjectResult res = new BadRequestObjectResult(new { Message = "Codigo de Percurso Invalido" });
            //return res;
            return null;

        }

        public async Task<ViagemDTO> CriarViagensImportadas(List<ViagemDTO> viagens)
        {
            string percursoUrl = Config.WebApiApplicationJson() + "percursos";
            var client = new HttpClient();
            client.DefaultRequestHeaders.Accept.Clear();
            client.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));
            Console.WriteLine(percursoUrl);

            // obtem a lista completa de percursos, para evitar fazer centenas de chamadas à API do MDR
            HttpResponseMessage resp = await client.GetAsync(percursoUrl);

            if (resp.Content != null)
            {
                JArray percursos = JArray.Parse(resp.Content.ReadAsStringAsync().Result);
                foreach (ViagemDTO viagem in viagens)
                {
                    JObject percurso = percursos.Children<JObject>().FirstOrDefault(o => o["idPercurso"] != null && o["idPercurso"].ToString() == viagem.PercursoId);
                    if (percurso != null)
                    {
                        try
                        {
                            await PersistirViagem(viagem.Key, viagem.HoraInicio, viagem.PercursoId, percurso);
                        }
                        catch (Exception)
                        {
                            Console.WriteLine(viagem.Key + " nao criada por nao ter passado nas validacoes");
                        }
                        
                    } else
                    {
                        Console.WriteLine("Codigo de Percurso Invalido.");
                    }
                }
                return new ViagemDTO();

            } else
            {
                Console.WriteLine("Sem Percursos no MDR");
                //BadRequestObjectResult res = new BadRequestObjectResult(new { Message = "Sem Percursos no MDR" });
                return null;
            }
        }

        private async Task<ViagemDTO> PersistirViagem(string key, int horaSaida, string codPercurso, JObject percurso)
        {
            var viagem = new Viagem(key, codPercurso);
            int horaPassagem = horaSaida, horaFim = 0;
            viagem.AdicionarHoraInicio(horaSaida);
            
            JToken no;
            string noAbrev, inicial = null, final = null;
            Passagem passagem = null;
            List<string> listaPassagens = new List<string>();

            foreach (var segmento in percurso["segmentos"])
            {
                if ((int)segmento["ordem"] == 1)
                {
                    no = segmento["noOrigem"];
                    noAbrev = (string)no["abreviatura"].ToString();
                    inicial = noAbrev;
                    passagem = new Passagem(viagem.Id, horaPassagem, noAbrev);
                    await this._repoPassagem.AddAsync(passagem);
                    //await this._unitOfWork.CommitAsync();
                    viagem.AdicionarPassagem(passagem);
                    listaPassagens.Add(passagem.Id.AsString());

                    horaPassagem += (int)segmento["duracao"];
                    horaFim = horaPassagem;

                    no = segmento["noDestino"];
                    noAbrev = (string)no["abreviatura"].ToString();
                    final = noAbrev;
                    passagem = new Passagem(viagem.Id, horaPassagem, noAbrev);
                    await this._repoPassagem.AddAsync(passagem);
                    //await this._unitOfWork.CommitAsync();
                    viagem.AdicionarPassagem(passagem);
                    listaPassagens.Add(passagem.Id.AsString());

                }
                else
                {
                    horaPassagem += (int)segmento["duracao"];
                    horaFim = horaPassagem;
                    no = segmento["noDestino"];
                    noAbrev = (string)no["abreviatura"].ToString();
                    final = noAbrev;
                    passagem = new Passagem(viagem.Id, horaPassagem, noAbrev);
                    viagem.AdicionarPassagem(passagem);
                    listaPassagens.Add(passagem.Id.AsString());
                    await this._repoPassagem.AddAsync(passagem);
                    //await this._unitOfWork.CommitAsync();
                }
            }

            string desc = inicial + '-' + final + '@' + viagem.HoraInicio;
            viagem.AlterarDescritivo(desc);
            viagem.AdicionarHoraFim(horaFim);

            await this._repoViagem.AddAsync(viagem);
            await this._unitOfWork.CommitAsync();
            return new ViagemDTO
            {
                Id = viagem.Id.GetAsGuid(),
                Descritivo = viagem.Descritivo,
                PercursoId = viagem.PercursoId,
                ServicoViaturaId = viagem.ServicoViaturaId,
                HoraInicio = viagem.HoraInicio,
                HoraFim = viagem.HoraFim,
                Passagens = listaPassagens
            };
        }

        public async Task<List<ViagemDTO>> CriarViagens(int horaInicio, string percIda, string percVolta, int freq, int nViagens)
        {
            var viagens = new List<ViagemDTO>();

            for (int i = 0; i < nViagens; i++)
            {
                ViagemDTO vIda = await CriarViagem(horaInicio, percIda);
                if (vIda == null)
                {
                    return null;
                }
                viagens.Add(vIda);
                int horaVolta = vIda.HoraFim;
                ViagemDTO vVolta = await CriarViagem(horaVolta, percVolta);
                if (vVolta == null)
                {
                    return null;
                }
                viagens.Add(vVolta);
                horaInicio += freq;
            }

            return viagens;
        }
    }
}
    
