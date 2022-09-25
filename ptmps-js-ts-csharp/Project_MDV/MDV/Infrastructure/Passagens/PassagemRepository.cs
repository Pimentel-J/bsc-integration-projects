using MDV.Domain.Passagens;
using MDV.Domain.Viagens;
using MDV.Infrastructure.Shared;
using MDV.Repositories;
using Microsoft.EntityFrameworkCore;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace MDV.Infrastructure.Passagens
{
    public class PassagemRepository : BaseRepository<Passagem, PassagemId>, IPassagemRepository
    {
        MDVDbContext _context;

        public PassagemRepository(MDVDbContext context): base(context.Passagens)
        {
            this._context = context;
        }

        public async Task<List<Passagem>> GetOfViagem(string viagem)
        {
            return await this._context.Passagens
                .Where(x => new ViagemId(viagem).Equals(x.ViagemId)).ToListAsync();
        }

    }

}