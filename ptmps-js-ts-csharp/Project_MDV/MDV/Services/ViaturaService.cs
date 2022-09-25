using System.Threading.Tasks;
using System.Collections.Generic;
using MDV.Domain.Shared;
using MDV.DTO;
using MDV.Repositories;
using MDV.Domain.Viaturas;


namespace MDV.Services
{
    public class ViaturaService
    {
        private readonly IUnitOfWork _unitOfWork;
        private readonly IViaturaRepository _repo;
        


        public ViaturaService(IUnitOfWork unitOfWork, IViaturaRepository repo)
        {
            this._unitOfWork = unitOfWork;
            this._repo = repo;
        }


        public async Task<List<ViaturaDTO>> GetAllAsync()
        {
            var list = await this._repo.GetAllAsync();

            List<ViaturaDTO> listDto = list.ConvertAll<ViaturaDTO>(viatura =>
            new ViaturaDTO(viatura.Id.AsString(), viatura.niv, viatura.tipoviatura, viatura.data_entrada_servico));

            return listDto;
        }

        public async Task<ViaturaDTO> GetByIdAsync(ViaturaId matricula)
        {
            var viatura = await this._repo.GetByIdAsync(matricula);
            if (viatura == null)
                return null;

            return new ViaturaDTO(viatura.Id.AsString(), viatura.niv, viatura.tipoviatura, viatura.data_entrada_servico);
        }

        //
        public async Task<ViaturaDTO> AddAsync(CreatingViaturaDTO dto)
        {

            var viatura = new Viatura(dto.id, dto.niv, dto.tipoviatura, dto.data_entrada_servico);
            await this._repo.AddAsync(viatura);
            await this._unitOfWork.CommitAsync();

            return new ViaturaDTO(viatura.Id.AsString(), viatura.niv, viatura.tipoviatura, viatura.data_entrada_servico);
        }

        public async Task<ViaturaDTO> UpdateAsync(ViaturaDTO dto)
        {
            var viatura = await this._repo.GetByIdAsync(new ViaturaId(dto.Id));

            if (viatura == null)
                return null;

            viatura.change( dto.niv, dto.tipoviatura, dto.data_entrada_servico);
            await this._unitOfWork.CommitAsync();

            return new ViaturaDTO(viatura.Id.AsString(), viatura.niv, viatura.tipoviatura, viatura.data_entrada_servico);
        }

        public async Task<ViaturaDTO> InactivateAsync(ViaturaId matriculaId)
        {
            var viatura = await this._repo.GetByIdAsync(matriculaId);

            if (viatura == null)
                return null;

            await this._unitOfWork.CommitAsync();

            return new ViaturaDTO(viatura.Id.AsString(),viatura.niv, viatura.tipoviatura, viatura.data_entrada_servico);
        }

        public async Task<ViaturaDTO> DeleteAsync(ViaturaId matriculaId)
        {
            var viatura = await this._repo.GetByIdAsync(matriculaId);

            if (viatura == null)
                return null;

            this._repo.Remove(viatura);
            await this._unitOfWork.CommitAsync();

            return new ViaturaDTO(viatura.Id.AsString(), viatura.niv, viatura.tipoviatura, viatura.data_entrada_servico);
        }


    }
}