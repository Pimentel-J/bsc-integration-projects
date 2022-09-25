using MDV.Domain.Blocos;
using MDV.Infrastructure.Shared;
using System.Threading.Tasks;
using System.Collections.Generic;
using Microsoft.EntityFrameworkCore;
using System.Linq;

namespace MDV.Infrastructure.Blocos
{
    public class BlocoRepository : BaseRepository<Bloco, BlocoId>, IBlocoRepository
    {
    
        MDVDbContext _context;
 
        public BlocoRepository(MDVDbContext context):base(context.Blocos)
        {
           this._context = context;
        }
        override
        public async Task<List<Bloco>> GetAllAsync()
        {
            return await this._context.Blocos.Include("viagens").ToListAsync();
        }

        override
        public async Task<Bloco> GetByIdAsync(BlocoId id)
        {
            return await this._context.Blocos
                .Where(x => id.Equals(x.Id))
                .Include(s => s.viagens)
                .Select(b => OrdenaViagens(b))
                .FirstOrDefaultAsync();
        }

        private static Bloco OrdenaViagens(Bloco p)
        {
            p.viagens = p.viagens?
                .OrderBy(s => s.HoraInicio)
                .ToHashSet();
            return p;
        }

    }
}