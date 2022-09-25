using MDV.Domain.ServicosTripulante;
using MDV.Repositories;
using MDV.Infrastructure.Shared;

namespace MDV.Infrastructure.ServicosTripulante
{
    public class ServicoTripulanteRepository : BaseRepository<ServicoTripulante, ServicoTripulanteId>, 
    IServicoTripulanteRepository
    {
    
        public ServicoTripulanteRepository(MDVDbContext context):base(context.ServicosTripulante)
        {
           
        }


    }
}