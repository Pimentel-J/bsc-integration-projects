using MDV.Domain.Viagens;
using MDV.Infrastructure.Shared;
using MDV.Repositories;
using System.Threading.Tasks;
using System.Linq;
using System.Collections.Generic;
using Microsoft.EntityFrameworkCore;

namespace MDV.Infrastructure.Viagens
{
    public class ViagemRepository : BaseRepository<Viagem, ViagemId>, IViagemRepository
    {
        MDVDbContext _context;
 
        public ViagemRepository(MDVDbContext context):base(context.Viagens)
        {
           this._context = context;
        }
        override
        public async Task<List<Viagem>> GetAllAsync()
        {
               return await this._context.Viagens.Include("Passagens").ToListAsync();
        }

       
        public new async Task<Viagem> GetByIdAsync(ViagemId id)
        {
            return await this._context.Viagens
                .Include(s=> s.Passagens)
                .Where(x => id.Equals(x.Id))
                .Select(v => OrdenaPassagens(v))
                .FirstOrDefaultAsync();
        }

        private static Viagem OrdenaPassagens(Viagem v)
        {
            v.Passagens = v.Passagens?
                .OrderBy(s => s.HoraPassagem)
                .ToHashSet();
            return v;
        }

        public async Task<List<Viagem>> GetOfServicoViatura(string sv)
        {
            return await this._context.Viagens
                .Where(x => sv.Equals(x.ServicoViaturaId)).Include("Passagens").ToListAsync();
        }

        public async Task<Viagem> GetByKey(string key)
        {
            return await this._context.Viagens
                .Where(x => key.Equals(x.Key)).FirstOrDefaultAsync();
        }

        public async Task<List<Viagem>> GetAllWithOutSVAsync() {

             return await this._context.Viagens.Include("Passagens")
                .Where(v => v.ServicoViaturaId == null).ToListAsync();

        }

    }
}