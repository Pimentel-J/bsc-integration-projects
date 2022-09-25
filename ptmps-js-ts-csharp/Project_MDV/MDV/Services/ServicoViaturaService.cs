using System.Threading.Tasks;
using System.Collections.Generic;
using System.Linq;
using MDV.Domain.Shared;
using MDV.DTO;
using MDV.Repositories;
using MDV.Domain.ServicoViaturas;
using MDV.Domain.Viagens;

namespace MDV.Services
{
    public class ServicoViaturaService
    {
        private readonly IUnitOfWork _unitOfWork;
        private readonly IServicoViaturaRepository _repoSV;
        private readonly ViagemService _servViagem;
        private readonly int MAX_DAY_SEC = 86400;

        public ServicoViaturaService(IUnitOfWork unitOfWork, 
                    IServicoViaturaRepository repoSV,
                    ViagemService servViagem)
        {
            this._unitOfWork = unitOfWork;
            this._repoSV = repoSV; 
            this._servViagem = servViagem;
          
        }


        public async Task<List<ServicoViaturaDTO>> GetAllAsync()
        {
            var list = await this._repoSV.GetAllAsync();

           
            //var viagenslist = list.Select(i => i.Id.AsString()).ToList();
            List<ServicoViaturaDTO> listDto = list.ConvertAll<ServicoViaturaDTO>(sv =>
            new ServicoViaturaDTO {
                Id = sv.Id.AsString(),
                ViaturaId = (sv.ViaturaId==null ? "" : sv.ViaturaId.AsString()),
                Viagens = toString((HashSet<Viagem>)sv.Viagens)});

            return listDto;
        }


        public  HashSet<string> toString(HashSet<Viagem> lista){
            HashSet<string> l = new HashSet<string>();
            string v;
            if (lista == null)
            return null;
            foreach (Viagem word in lista)
            {
                v = word.Id.AsString();
                if (v== null)
                return null;
                l.Add(v);
            }
            return l;
        }

        public async Task<ServicoViaturaDTO> AddAsync(CreatingServicoViaturaDto dto)
         {

            // verifica se o codigo de servico de viatura ja existe em servicos de viagem
            if (await _repoSV.GetByIdAsync(new ServicoViaturaId(dto.Id)) != null)
                            throw new BusinessRuleValidationException("Codigo Servico já existe");

            var sv = new ServicoViatura(new ServicoViaturaId(dto.Id));
                        /* new ViaturaId(dto.ViaturaId)*/
                         
            string noFinal = "";
            int horaFinal = 0;
            int ttimeOfDay = 0;
            bool firstInteraction = true;

            foreach (var lviagem in dto.Viagens)
            {
                var viagem = await this._servViagem.GetViagem(new ViagemId(lviagem));
              
                if (viagem != null)
                {
                    if (viagem.ServicoViaturaId != null)
                         throw new BusinessRuleValidationException("Viagem já está referenciada");
                 

                     string noInicial  = getNoInicial(viagem.Descritivo);
                     int horaInicial = viagem.HoraInicio;
                    if (((noFinal != noInicial) || (horaFinal > horaInicial)) && !firstInteraction)
                            throw new BusinessRuleValidationException("Sequencia de Nós errada.");
                   
                    
                    noFinal = getNoFinal(viagem.Descritivo);
                    horaFinal = viagem.HoraFim;
                    ttimeOfDay += horaFinal-horaInicial;
                    if (ttimeOfDay > MAX_DAY_SEC)
                             throw new BusinessRuleValidationException("Servico Viatura excedeu o tempo de um dia.");
                    firstInteraction = false;
                    sv.AdicionarViagens(viagem);
                }
            }


            if (sv.Viagens.Count == 0)
                      throw new BusinessRuleValidationException("Não encontrou viagens validas.");
            
            SaveViagens(sv.Viagens, dto.Id);
            await this._repoSV.AddAsync(sv);
            await this._unitOfWork.CommitAsync();
            var viagenslist = sv.Viagens.Select(i => i.Id.AsString()).ToHashSet();

            return new ServicoViaturaDTO{
                Id = sv.Id.AsString(), 
                ViaturaId = (sv.ViaturaId==null ? "" : sv.ViaturaId.AsString()),
                Viagens = viagenslist};
    
        }

        private async void SaveViagens(ICollection<Viagem> lstViagens, string code) {
            foreach (var viagem in lstViagens) {
                await _servViagem.UpdateViagemAsync(viagem,code);
            }

        }

        private string getNoInicial(string descr) {
            return descr.Split('-').ElementAtOrDefault(0);

        }

        private string getNoFinal(string descr) {
            string descr2 = descr.Split('@').ElementAtOrDefault(0);
            //Console.WriteLine(descr2);
            return descr2.Split('-').ElementAtOrDefault(1);
           
        }

        public async Task<ServicoViaturaDTO> GetByIdAsync(ServicoViaturaId Id) {
            var sv = await this._repoSV.GetByIdAsync(Id);
            if (sv == null)
                return null;
             return new ServicoViaturaDTO{
                 Id = sv.Id.AsString(), 
                 ViaturaId = (sv.ViaturaId==null ? "" : sv.ViaturaId.AsString()),
                 Viagens = toString((HashSet<Viagem>)sv.Viagens)};
        }

        public async Task<ServicoViatura> GetServicoViatura(ServicoViaturaId Id) {
            var sv = await this._repoSV.GetByIdAsync(Id);
            if (sv == null)
                return null;
             return sv;
        }
    }
}