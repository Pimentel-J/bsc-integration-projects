using System.Threading.Tasks;
using MDV.Domain.Shared;

namespace MDV.Infrastructure
{
    public class UnitOfWork : IUnitOfWork
    {
        private readonly MDVDbContext _context;

        public UnitOfWork(MDVDbContext context)
        {
            this._context = context;
        }

        public async Task<int> CommitAsync()
        {
            return await this._context.SaveChangesAsync();
        }
    }
}