using System;
using MDV.Domain.Shared;

namespace MDV.DTO
{
    public class PassagemDTO 
    {
        public Guid Id { get; set; }

        public string ViagemId { get; set; }

        public int HoraPassagem { get; set; }

        public string AbreviaturaNo { get; set; }

        public PassagemDTO()
        {
        }

        public PassagemDTO(Guid Id, string viagemId, int horaPassagem, string abrevNo)
        {
            this.Id = Id;
            this.ViagemId = viagemId;
            this.HoraPassagem = horaPassagem;
            this.AbreviaturaNo = abrevNo;
        }
    }
}