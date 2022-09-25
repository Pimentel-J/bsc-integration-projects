using System.Collections.Generic;
using System.Threading.Tasks;
using MDV.Domain.Viagens;
using MDV.Repositories.Shared;

namespace MDV.Repositories
{
    public interface IViagemRepository: IRepository<Viagem, ViagemId>
    {
        Task<List<Viagem>> GetAllWithOutSVAsync();
        Task<List<Viagem>> GetOfServicoViatura(string sv);
        Task<Viagem> GetByKey(string key);

    }

}