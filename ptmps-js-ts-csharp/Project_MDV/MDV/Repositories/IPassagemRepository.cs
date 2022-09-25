using MDV.Domain.Passagens;
using MDV.Repositories.Shared;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace MDV.Repositories
{
    public interface IPassagemRepository: IRepository<Passagem, PassagemId>
    {
        Task<List<Passagem>> GetOfViagem(string viagem);

    }

    

}