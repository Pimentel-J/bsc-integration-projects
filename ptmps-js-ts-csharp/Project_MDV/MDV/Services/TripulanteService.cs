using System.Threading.Tasks;
using System.Collections.Generic;
using MDV.Domain.Shared;
using MDV.DTO;
using MDV.Repositories;
using MDV.Domain.Tripulantes;

namespace MDV.Services
{
    public class TripulanteService
    {
        private readonly IUnitOfWork _unitOfWork;
        private readonly ITripulanteRepository _repo;

        public TripulanteService(IUnitOfWork unitOfWork, ITripulanteRepository repo)
        {
            this._unitOfWork = unitOfWork;
            this._repo = repo;
        }


        public async Task<List<TripulanteDTO>> GetAllAsync()
        {
            var list = await this._repo.GetAllAsync();

            List<TripulanteDTO> listDto = list.ConvertAll<TripulanteDTO>(tripulante =>
            new TripulanteDTO(tripulante.Id.AsString(), tripulante.numeroMecanografico, tripulante.nome, tripulante.dataNascimento, 
            tripulante.numeroCartaoCidadao, tripulante.nif, tripulante.numeroCartaConducao, 
            tripulante.dataEmissaoLicencaConducao, tripulante.dataValidadeLicencaConducao, tripulante.tipoTripulante, 
            tripulante.dataEntradaEmpresa, tripulante.dataSaidaEmpresa));

            return listDto;
        }

        public async Task<TripulanteDTO> GetByIdAsync(TripulanteId numeroMecanografico)
        {
            var tripulante = await this._repo.GetByIdAsync(numeroMecanografico);
            if (tripulante == null)
                return null;

            return new TripulanteDTO(tripulante.Id.AsString(), tripulante.numeroMecanografico, tripulante.nome, tripulante.dataNascimento, 
            tripulante.numeroCartaoCidadao, tripulante.nif, tripulante.numeroCartaConducao, 
            tripulante.dataEmissaoLicencaConducao, tripulante.dataValidadeLicencaConducao, tripulante.tipoTripulante, 
            tripulante.dataEntradaEmpresa, tripulante.dataSaidaEmpresa);
        }

        public async Task<TripulanteDTO> AddAsync(CreatingTripulanteDTO dto)
        {

            var tripulante = new Tripulante(dto.numeroMecanografico, dto.nome, dto.dataNascimento, 
            dto.numeroCartaoCidadao, dto.nif, dto.numeroCartaConducao, 
            dto.dataEmissaoLicencaConducao, dto.dataValidadeLicencaConducao, dto.tipoTripulante, 
            dto.dataEntradaEmpresa, dto.dataSaidaEmpresa);
            await this._repo.AddAsync(tripulante);
            await this._unitOfWork.CommitAsync();

            return new TripulanteDTO(tripulante.Id.AsString(), tripulante.numeroMecanografico, tripulante.nome, tripulante.dataNascimento, 
            tripulante.numeroCartaoCidadao, tripulante.nif, tripulante.numeroCartaConducao, 
            tripulante.dataEmissaoLicencaConducao, tripulante.dataValidadeLicencaConducao, tripulante.tipoTripulante, 
            tripulante.dataEntradaEmpresa, tripulante.dataSaidaEmpresa);
        }

        public async Task<TripulanteDTO> UpdateAsync(TripulanteDTO dto)
        {
            var tripulante = await this._repo.GetByIdAsync(new TripulanteId(dto.numeroMecanografico));

            if (tripulante == null)
                return null;

            tripulante.change(dto.nome, dto.dataNascimento, 
            dto.numeroCartaoCidadao, dto.nif, dto.numeroCartaConducao, 
            dto.dataEmissaoLicencaConducao, dto.dataValidadeLicencaConducao, dto.tipoTripulante, 
            dto.dataEntradaEmpresa, dto.dataSaidaEmpresa);
            await this._unitOfWork.CommitAsync();

            return new TripulanteDTO(tripulante.Id.AsString(), tripulante.numeroMecanografico, tripulante.nome, tripulante.dataNascimento, 
            tripulante.numeroCartaoCidadao, tripulante.nif, tripulante.numeroCartaConducao, 
            tripulante.dataEmissaoLicencaConducao, tripulante.dataValidadeLicencaConducao, tripulante.tipoTripulante, 
            tripulante.dataEntradaEmpresa, tripulante.dataSaidaEmpresa);
        }

        public async Task<TripulanteDTO> InactivateAsync(TripulanteId numeroMecanograficoId)
        {
            var tripulante = await this._repo.GetByIdAsync(numeroMecanograficoId);

            if (tripulante == null)
                return null;

            await this._unitOfWork.CommitAsync();

            return new TripulanteDTO(tripulante.Id.AsString(), tripulante.numeroMecanografico, tripulante.nome, tripulante.dataNascimento, 
            tripulante.numeroCartaoCidadao, tripulante.nif, tripulante.numeroCartaConducao, 
            tripulante.dataEmissaoLicencaConducao, tripulante.dataValidadeLicencaConducao, tripulante.tipoTripulante, 
            tripulante.dataEntradaEmpresa, tripulante.dataSaidaEmpresa);
        }

        public async Task<TripulanteDTO> DeleteAsync(TripulanteId numeroMecanograficoId)
        {
            var tripulante = await this._repo.GetByIdAsync(numeroMecanograficoId);

            if (tripulante == null)
                return null;

            this._repo.Remove(tripulante);
            await this._unitOfWork.CommitAsync();

            return new TripulanteDTO(tripulante.Id.AsString(), tripulante.numeroMecanografico, tripulante.nome, tripulante.dataNascimento, 
            tripulante.numeroCartaoCidadao, tripulante.nif, tripulante.numeroCartaConducao, 
            tripulante.dataEmissaoLicencaConducao, tripulante.dataValidadeLicencaConducao, tripulante.tipoTripulante, 
            tripulante.dataEntradaEmpresa, tripulante.dataSaidaEmpresa);
        }

    }
}