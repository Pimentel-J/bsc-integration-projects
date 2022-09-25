using MDV.Domain.Tripulantes;
using MDV.Repositories;
using MDV.Infrastructure.Shared;

namespace MDV.Infrastructure.Tripulantes
{
    public class TripulanteRepository : BaseRepository<Tripulante, TripulanteId>, ITripulanteRepository
    {
    
        public TripulanteRepository(MDVDbContext context):base(context.Tripulantes)
        {
           
        }


    }
}