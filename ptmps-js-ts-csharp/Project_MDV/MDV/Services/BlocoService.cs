using System.Threading.Tasks;
using System.Collections.Generic;
using MDV.Domain.Shared;
using MDV.Domain.Blocos;
using MDV.Domain.Viagens;
using MDV.Domain.ServicoViaturas;
using MDV.DTO;
using System;
using System.Linq;
using System.Net.Http;
using System.Net.Http.Headers;
using Newtonsoft.Json.Linq;
using MDV.Domain.ServicosTripulante;

namespace MDV.Services
{
      
    public class BlocoService
    {
        private readonly IUnitOfWork _unitOfWork;
        private readonly IBlocoRepository _repo;
        private readonly ServicoViaturaService _servViat;
        private readonly ServicoTripulanteService _servTrip;
        private readonly ViagemService _serViagem;

        public BlocoService(
            IUnitOfWork unitOfWork,
            IBlocoRepository repo, 
            ServicoViaturaService context,
            ViagemService serViagem,
            ServicoTripulanteService servTrip
            )
        {
            this._unitOfWork = unitOfWork;
            this._repo = repo;
            this._servViat = context;
            this._serViagem = serViagem;
            this._servTrip = servTrip;
        }

        public async Task<List<BlocoDTO>> GetAllAsync()
        {
            var list = await this._repo.GetAllAsync();
            
            List<BlocoDTO> listDto = list.ConvertAll<BlocoDTO>(bloco => 
            new BlocoDTO{
                Codigo = bloco.Id.AsString(), 
                StartTime = bloco.startTime,
                EndTime = bloco.endTime,    
                ServicoViaturaId = bloco.servicoViaturaId.AsString(),
                Viagens = oBJtoDTO( (HashSet<Viagem>) bloco.viagens)
                });

            return listDto;
        }

        public async Task<List<Bloco>> GetAllBlocos()
        {
            List<Bloco> list = await this._repo.GetAllAsync();
            return list;
        }

        public async Task<BlocoDTO> GetByIdAsync(BlocoId codigo)
        {
            var bloco = await this._repo.GetByIdAsync(codigo);
            
            if(bloco == null)
                return null;

            return new BlocoDTO{
                Codigo = bloco.Id.AsString(), 
                StartTime = bloco.startTime,
                EndTime = bloco.endTime,    
                ServicoViaturaId = bloco.servicoViaturaId.AsString(),
                Viagens = oBJtoDTO( (HashSet<Viagem>)bloco.viagens)
                };
        }

        public  List<String> oBJtoDTO(HashSet<Viagem> bloco){
            if(bloco == null)
            return null;
            List<string> viag = new List<string>();
            foreach (Viagem viagem in bloco)
            {
                viag.Add(viagem.Id.AsString());
            }
            return viag;
        }
        public  List<String> oBJtoDTO2(List<Viagem> bloco){
            if(bloco == null)
            return null;
            List<string> viag = new List<string>();
            foreach (Viagem viagem in bloco)
            {
                viag.Add(viagem.Id.AsString());
            }
            return viag;
        }

    public List<Viagem> sort(List<Viagem> lista) 
    { 
        int n = lista.Count; 
        for (int i = 0; i < n - 1; i++){ 
            for (int j = 0; j < n - i - 1; j++){
                if (lista[j].HoraInicio > lista[j + 1].HoraInicio) 
                { 
                    // swap temp and arr[i] 
                    Viagem temp = lista[j]; 
                    lista[j] = lista[j + 1]; 
                    lista[j + 1] = temp; 
                }
            }
        }
        return lista;    
    } 

        public async Task<List<Viagem>> strToObjList(List<String> lista){
            List<Viagem> l = new List<Viagem>();
            Viagem v;
            if (lista== null)
            return null;
            foreach (string word in lista)
            {
                v = await _serViagem.GetViagem(new ViagemId(word));
                if (v== null)
                return null;
                l.Add(v);
            }
            return l;
        }

        public async Task<List<Viagem>> getViagensServ(string id){
            List<Viagem> retorno= new List<Viagem>();
            List<Viagem> list = await _serViagem.GetAllViagens();
            foreach (Viagem item in list)
            {
                if(item.ServicoViaturaId == id)
                retorno.Add(item);
            }
            return retorno;
        }

        public async Task<BlocoDTO> AddAsync(CreatingBlocoDTO dto)
        {
            // verificar se o Codigo do bloco ainda nao existe na BD
            Boolean codigoValido = await ValidaCodigoBloco(dto.codigo);
            if (!codigoValido)
            {
                throw new BusinessRuleValidationException("um bloco com o mesmo codigo ja existe no sistema");
            }

            // verificar se o servico de viatura existe
            Boolean servicoViaturaValido = await ValidaServicoViaturaBloco(dto.servicoViaturaId);
            if (!servicoViaturaValido)
            {
                throw new BusinessRuleValidationException("o servico de viatura referido nao existe");
            }

            // obter as viagens correspondentes aos IDs da lista proveniente do DTO
            Task<List<Viagem>> viagensTask = GetViagensDeKey(dto.viagens);
            List<Viagem> viagens = await viagensTask;
            if (viagens.Count == 0)
            {
                throw new BusinessRuleValidationException("lista de viagens vazia");
            }

            //// validar se todas as viagens pertencem ao Servico Viatura selecionado
            Viagem viagemInvalida = ValidaViagensBloco(viagens, dto.servicoViaturaId);
            if (viagemInvalida != null)
            {
                throw new BusinessRuleValidationException("a viagem " + viagemInvalida.Key + ": " + viagemInvalida.Descritivo + " nao pertence ao Servico Viatura selecionado");
            }

            // verificar se a hora de inicio e fim correspondem a passagens em nós de troca de motorista (estação de recolha ou ponto de rendição)
            Viagem primeiraViagem = viagens.First();
            Boolean horaInicioValida = await ValidaTrocaBloco(primeiraViagem, dto.startTime);
            if (!horaInicioValida)
            {
                throw new BusinessRuleValidationException("a hora de inicio nao corresponde a uma passagem num ponto de troca de motoristas");
            }
            Viagem ultimaViagem = viagens.Last();
            Boolean horaFimValida = await ValidaTrocaBloco(ultimaViagem, dto.endTime);
            if (!horaFimValida)
            {
                throw new BusinessRuleValidationException("a hora de fim nao corresponde a uma passagem num ponto de troca de motoristas");
            }

            // criar Bloco e persisti-lo na BD
            var bloco = new Bloco(new BlocoId(dto.codigo),dto.startTime,dto.endTime,new ServicoViaturaId(dto.servicoViaturaId), viagens);
            try
            {
                var blocoCriado = await this._repo.AddAsync(bloco);
                await this._unitOfWork.CommitAsync();
                // devolver como resposta o bloco persistido
                return new BlocoDTO
                {
                    Codigo = blocoCriado.Id.AsString(),
                    StartTime = blocoCriado.startTime,
                    EndTime = blocoCriado.endTime,
                    Viagens = toString(blocoCriado.viagens),
                    ServicoViaturaId = blocoCriado.servicoViaturaId.AsString()
                };
            }
            catch (Exception)
            {
                throw;
            }
            
        }

        private async Task<Boolean> ValidaCodigoBloco(string codigo)
        {
            var blocoComMesmoID = await this._repo.GetByIdAsync(new BlocoId(codigo));
            if (blocoComMesmoID != null)
            {
                return false;
            } else
            {
                return true;
            }
        }

        private async Task<Boolean> ValidaServicoViaturaBloco(string codigo)
        {
            var servicoViatura = await _servViat.GetServicoViatura(new ServicoViaturaId(codigo));
            if (servicoViatura == null)
            {
                return false;
            }
            else
            {
                return true;
            }
        }

        private async Task<List<Viagem>> GetViagensDeKey(List<string> viagensDTO)
        {
            List<Viagem> viagens = new List<Viagem>();
            foreach (string viagemID in viagensDTO)
            {
                var viagem = await _serViagem.GetViagem(new ViagemId(viagemID));
                if (viagem != null)
                {
                    viagens.Add(viagem);
                } else
                {
                    throw new BusinessRuleValidationException("a viagem " + viagemID + " nao existe");
                }
            }
            return viagens;
        }

        private static Viagem ValidaViagensBloco(List<Viagem> viagens, string ServicoViaturaID)
        {
            foreach (Viagem viagem in viagens)
            {
                if (viagem.ServicoViaturaId != ServicoViaturaID)
                {
                    return viagem;
                }
            }
            return  null;
        }

        private async Task<Boolean> ValidaTrocaBloco(Viagem viagem, int hora)
        {

            string no;

            if (hora > viagem.HoraFim)  // se existe um gap depois do fim da última viagem
            {
                // obtem o último nó (abreviatura) da viagem
                no = viagem.Passagens.OrderBy(o => o.HoraPassagem).ToList().Last().AbreviaturaNo;
            } else if (hora < viagem.HoraInicio)    // se existe um gap antes do início da primeira viagem
            {
                // obtem o primeiro nó (abreviatura) da viagem
                no = viagem.Passagens.OrderBy(o => o.HoraPassagem).ToList().First().AbreviaturaNo;
            } else
            {
                // obtem o nó (abreviatura) correspondente à hora de input
                no = viagem.Passagens.ToList().Find(x => x.HoraPassagem == hora).AbreviaturaNo;
            }

            if (no == null)
            {
                return false;
            }

            // obtem o nó do MDR com base na abreviatura encontrada
            string noUrl = Config.WebApiApplicationJson() + "nos/";
            var client = new HttpClient();
            client.DefaultRequestHeaders.Accept.Clear();
            client.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));
            HttpResponseMessage resp = await client.GetAsync(noUrl + no);
            if (resp.Content != null)
            {
                JObject noObj = JObject.Parse(resp.Content.ReadAsStringAsync().Result);
                // verifica se o Nó correponde a um ponto de troca de motoristas
                if (!(Boolean)noObj["estacaoRecolha"] && !(Boolean)noObj["pontoRendicao"])
                {
                    return false;
                } else
                {
                    return true;
                }
            } else
            {
                return false;
            }
        }


        public List<string> toString(ICollection<Viagem> lista)
        {
            List<string> l = new List<string>();
            string v;
            if (lista == null)
                return null;
            foreach (Viagem word in lista)
            {
                v = word.Id.AsString();
                if (v == null)
                    return null;
                l.Add(v);
            }
            return l;
        }

        public async Task<BlocoDTO> UpdateAsync(BlocoDTO dto)
        {
            var bloco = await this._repo.GetByIdAsync(new BlocoId(dto.Codigo)); 

            if (bloco == null)
                return null;   

            var servViatura = await this._servViat.GetByIdAsync(new ServicoViaturaId(dto.ServicoViaturaId));

            if (servViatura == null)
                return null;   

            List<Viagem> lista = new List<Viagem>();
            bloco.change(dto.StartTime, dto.EndTime, new ServicoViaturaId(dto.ServicoViaturaId) ,  lista);
            await this._unitOfWork.CommitAsync();

            return new BlocoDTO { 
                Codigo = bloco.Id.AsString(), 
                StartTime = bloco.startTime,
                EndTime = bloco.endTime,    
                ServicoViaturaId = bloco.servicoViaturaId.AsString()
            };
        }

        public async Task<BlocoDTO> UpdateServicoTripulanteAsync(BlocoDTO dto)
        {
            var bloco = await this._repo.GetByIdAsync(new BlocoId(dto.Codigo));

            if (bloco == null)
                return null;

            var servTrip = await this._servTrip.GetByIdAsync(new ServicoTripulanteId(dto.ServicoTripulanteId));

            if (servTrip == null)
                return null;

            bloco.AtualizaServicoTripulante(new ServicoTripulanteId(dto.ServicoTripulanteId));
            await this._unitOfWork.CommitAsync();

            return new BlocoDTO
            {
                Codigo = bloco.Id.AsString(),
                StartTime = bloco.startTime,
                EndTime = bloco.endTime,
                ServicoViaturaId = bloco.servicoViaturaId.AsString(),
                ServicoTripulanteId = bloco.servicoTripulanteId.AsString()
            };
        }

        public async Task<BlocoDTO> InactivateAsync(BlocoId codigo)
        {
            var bloco = await this._repo.GetByIdAsync(codigo); 

            if (bloco == null)
                return null;   
            
            await this._unitOfWork.CommitAsync();

            return new BlocoDTO { 
                Codigo = bloco.Id.AsString(), 
                StartTime = bloco.startTime,
                EndTime = bloco.endTime,    
                ServicoViaturaId = bloco.servicoViaturaId.AsString()
            };
        }

         public async Task<BlocoDTO> DeleteAsync(BlocoId codigo)
        {
            var bloco = await this._repo.GetByIdAsync(codigo); 

            if (bloco == null)
                return null;   
            
            this._repo.Remove(bloco);
            await this._unitOfWork.CommitAsync();

            return new BlocoDTO { 
                Codigo = bloco.Id.AsString(), 
                StartTime = bloco.startTime,
                EndTime = bloco.endTime,    
                ServicoViaturaId = bloco.servicoViaturaId.AsString()
            };
        }

    }
}