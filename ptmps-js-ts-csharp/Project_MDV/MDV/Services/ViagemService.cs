using System.Threading.Tasks;
using System.Collections.Generic;
using MDV.Repositories;
using MDV.Domain.Viagens;
using MDV.Domain.Shared;
using MDV.DTO;
using MDV.Domain.Passagens;
using Microsoft.AspNetCore.Mvc;

namespace MDV.Services
{
    public class ViagemService
    {
        private readonly IUnitOfWork _unitOfWork;
        private readonly IViagemRepository _repo;

        public ViagemService(IUnitOfWork unitOfWork, IViagemRepository repo)
        {
            this._unitOfWork = unitOfWork;
            this._repo = repo;
        }

        public async Task<List<ViagemDTO>> GetAllAsync()
        {
            var list = await this._repo.GetAllAsync();

            List<ViagemDTO> listDto = list.ConvertAll<ViagemDTO>(viagem => new ViagemDTO { Id = viagem.Id.GetAsGuid(),
                Descritivo = viagem.Descritivo,
                PercursoId = viagem.PercursoId,
                ServicoViaturaId = viagem.ServicoViaturaId,
                HoraInicio = viagem.HoraInicio,
                HoraFim = viagem.HoraFim,
                Passagens = toString(viagem.Passagens)});

            return listDto;
        }

        public async Task<List<Viagem>> GetAllViagens()
        {
            var list = await this._repo.GetAllAsync();
            return list;

        }
        
        public async Task<List<ViagemDTO>> GetAllWithOutServViatAsync()
        {
            var list = await this._repo.GetAllWithOutSVAsync();

            List<ViagemDTO> listDto = list.ConvertAll<ViagemDTO>(viagem => new ViagemDTO { Id = viagem.Id.GetAsGuid(),
                Descritivo = viagem.Descritivo,
                PercursoId = viagem.PercursoId,
                ServicoViaturaId = viagem.ServicoViaturaId,
                HoraInicio = viagem.HoraInicio,
                HoraFim = viagem.HoraFim,
                Passagens = toString(viagem.Passagens)});

            return listDto;
        }

        public async Task<ActionResult<IEnumerable<ViagemDTO>>> GetOfServicoViatura(string sv)
        {
            var list = await this._repo.GetOfServicoViatura(sv);

            List<ViagemDTO> listDto = list.ConvertAll<ViagemDTO>(viagem => new ViagemDTO
            {
                Id = viagem.Id.GetAsGuid(),
                Descritivo = viagem.Descritivo,
                PercursoId = viagem.PercursoId,
                ServicoViaturaId = viagem.ServicoViaturaId,
                HoraInicio = viagem.HoraInicio,
                HoraFim = viagem.HoraFim,
                Passagens = toString(viagem.Passagens)
            });

            return listDto;
        }

        public async Task<ViagemDTO> GetByKey(string key)
        {
            var viagem = await this._repo.GetByKey(key);

            if (viagem == null)
                return null;

            return new ViagemDTO
            {
                Id = viagem.Id.GetAsGuid(),
                Descritivo = viagem.Descritivo,
                PercursoId = viagem.PercursoId,
                ServicoViaturaId = viagem.ServicoViaturaId,
                HoraInicio = viagem.HoraInicio,
                HoraFim = viagem.HoraFim
            };
        }

        public  List<string> toString(ICollection<Passagem> lista){
            List<string> l = new List<string>();
            string v;
            if (lista == null)
            return null;
            foreach (Passagem word in lista)
            {
                v = word.Id.AsString();
                if (v== null)
                return null;
                l.Add(v);
            }
            return l;
        }

        public async Task<ViagemDTO> GetByIdAsync(ViagemId id)
        {
            var viagem = await this._repo.GetByIdAsync(id);

            if (viagem == null)
                return null;

            return new ViagemDTO { Id = viagem.Id.GetAsGuid(),
                Descritivo = viagem.Descritivo,
                PercursoId = viagem.PercursoId,
                ServicoViaturaId = viagem.ServicoViaturaId,
                HoraInicio = viagem.HoraInicio,
                HoraFim = viagem.HoraFim,
                Passagens = toString(viagem.Passagens) };
        }

        public async Task<Viagem> GetViagem(ViagemId id)
        {
            var viagem = await this._repo.GetByIdAsync(id);

            if (viagem == null)
                return null;

            return viagem;
        }

        public async Task<ViagemDTO> AddAsync(CreatingViagemDTO dto)
        {
            var viagem = new Viagem(null, dto.PercursoId);

            await this._repo.AddAsync(viagem);

            await this._unitOfWork.CommitAsync();

            return new ViagemDTO { Id = viagem.Id.GetAsGuid(), PercursoId = viagem.PercursoId };
        }

        // Update Servico de Viatura para as viagens selecionadas
        public async Task<Viagem> UpdateViagemAsync(Viagem viagem, string servViatId)
         {
                        
            viagem.change(viagem.PercursoId, servViatId,viagem.Passagens);
        //    await this._unitOfWork.CommitAsync();
            return viagem;

        }

        public async Task<ViagemDTO> DeleteAsync(ViagemId id)
        {
            var viagem = await this._repo.GetByIdAsync(id);

            if (viagem == null)
                return null;

            //if (viagem.Active)
            //    throw new BusinessRuleValidationException("It is not possible to delete an active viagem.");

            this._repo.Remove(viagem);
            await this._unitOfWork.CommitAsync();

            return new ViagemDTO { Id = viagem.Id.GetAsGuid(), PercursoId = viagem.PercursoId };
        }
    }
}