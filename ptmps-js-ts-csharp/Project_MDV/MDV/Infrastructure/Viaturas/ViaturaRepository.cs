using MDV.Domain.Viaturas;
using MDV.Repositories;
using MDV.Infrastructure.Shared;

namespace MDV.Infrastructure.Viaturas
{
    public class ViaturaRepository : BaseRepository<Viatura, ViaturaId>, IViaturaRepository
    {
    
        public ViaturaRepository(MDVDbContext context):base(context.Viaturas)
        {
           
        }


    }
}