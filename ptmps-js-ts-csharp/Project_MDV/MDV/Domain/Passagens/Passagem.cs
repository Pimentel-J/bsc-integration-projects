using System;
using MDV.Domain.Shared;
using MDV.Domain.Viagens;

namespace MDV.Domain.Passagens
{
    public class Passagem : Entity<PassagemId>, IAggregateRoot
    {
        public ViagemId ViagemId { get; private set; }

        public int HoraPassagem { get; private set; }

        public String AbreviaturaNo { get; private set; }

        private Passagem()
        {
        }

        public Passagem(ViagemId viagemId, int horaPassagem, string abrevNo)
        {
            this.Id = new PassagemId(Guid.NewGuid());
            this.ViagemId = viagemId;
            this.HoraPassagem = horaPassagem;
            this.AbreviaturaNo = abrevNo;
        }
    }
}