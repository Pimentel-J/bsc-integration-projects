using MDV.Domain.ServicoViaturas;
using MDV.Repositories;
using MDV.Infrastructure.Shared;
using System.Threading.Tasks;
using System.Collections.Generic;
using Microsoft.EntityFrameworkCore;
using System.Linq;

namespace MDV.Infrastructure.ServicoViaturas
{    
    public class ServicoViaturaRepository : BaseRepository<ServicoViatura, ServicoViaturaId>, IServicoViaturaRepository
    {
    
        MDVDbContext _context;
 
        public ServicoViaturaRepository(MDVDbContext context):base(context.ServicoViaturas)
        {
           this._context = context;
        }
        override
        public async Task<List<ServicoViatura>> GetAllAsync()
        {
            return await this._context.ServicoViaturas
                .Include(s => s.Viagens)
                .Include(s => s.Blocos)
                .ToListAsync();
        }
        override
        public async Task<ServicoViatura> GetByIdAsync(ServicoViaturaId id)
        {
            return await this._context.ServicoViaturas
                .Where(x => id.Equals(x.Id))
                .Include(s => s.Viagens)
                .Include(s => s.Blocos)
                .FirstOrDefaultAsync();
        }

    }
}