using System.Threading.Tasks;
using System.Collections.Generic;
using MDV.Domain.Shared;
using MDV.DTO;
using MDV.Repositories;
using MDV.Domain.ServicosTripulante;

namespace MDV.Services
{
    public class ServicoTripulanteService
    {
        private readonly IUnitOfWork _unitOfWork;
        private readonly IServicoTripulanteRepository _repo;

        public ServicoTripulanteService(IUnitOfWork unitOfWork, IServicoTripulanteRepository repo)
        {
            this._unitOfWork = unitOfWork;
            this._repo = repo;
        }
        public async Task<List<ServicoTripulanteDTO>> GetAllAsync()
        {
            var list = await this._repo.GetAllAsync();

            List<ServicoTripulanteDTO> listDto = list.ConvertAll<ServicoTripulanteDTO>(servicoTripulante =>
            new ServicoTripulanteDTO(servicoTripulante.Id.AsString(), servicoTripulante.codigoServicoTripulante));

            return listDto;
        }
        public async Task<ServicoTripulanteDTO> GetByIdAsync(ServicoTripulanteId codigoServicoTripulante)
        {
            var servicoTripulante = await this._repo.GetByIdAsync(codigoServicoTripulante);
            if (servicoTripulante == null)
                return null;

            return new ServicoTripulanteDTO(servicoTripulante.Id.AsString(), servicoTripulante.codigoServicoTripulante);
        }
        
        public async Task<ServicoTripulanteDTO> AddAsync(CreatingServicoTripulanteDTO dto)
        {

            // verifica se o codigo de servico de tripulante ja existe
            if (await _repo.GetByIdAsync(new ServicoTripulanteId(dto.Id)) != null)
            {
                throw new BusinessRuleValidationException("Codigo do Servico Tripulante ja existe no sistema");
            }

            var servicoTripulante = new ServicoTripulante(dto.codigoServicoTripulante);
            await this._repo.AddAsync(servicoTripulante);
            await this._unitOfWork.CommitAsync();

            return new ServicoTripulanteDTO(servicoTripulante.Id.AsString(), servicoTripulante.codigoServicoTripulante);
        }

        public async Task<ServicoTripulanteDTO> InactivateAsync(ServicoTripulanteId codigoServicoTripulanteId)
        {
            var servicoTripulante = await this._repo.GetByIdAsync(codigoServicoTripulanteId);

            if (servicoTripulante == null)
                return null;

            await this._unitOfWork.CommitAsync();

            return new ServicoTripulanteDTO(servicoTripulante.Id.AsString(), servicoTripulante.codigoServicoTripulante);
        }

        public async Task<ServicoTripulanteDTO> DeleteAsync(ServicoTripulanteId codigoServicoTripulanteId)
        {
            var servicoTripulante = await this._repo.GetByIdAsync(codigoServicoTripulanteId);

            if (servicoTripulante == null)
                return null;

            this._repo.Remove(servicoTripulante);
            await this._unitOfWork.CommitAsync();

            return new ServicoTripulanteDTO(servicoTripulante.Id.AsString(), servicoTripulante.codigoServicoTripulante);
        }

    }
}