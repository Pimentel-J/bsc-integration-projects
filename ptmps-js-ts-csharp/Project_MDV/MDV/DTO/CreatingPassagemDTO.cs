using System;
using MDV.Domain.Shared;

namespace MDV.DTO
{
    public class CreatingPassagemDTO 
    {

        public string ViagemId { get; private set; }

        public int HoraPassagem { get; private set; }

        public string AbreviaturaNo { get; private set; }

        private CreatingPassagemDTO()
        {
        }

        public CreatingPassagemDTO(string viagemId, int horaPassagem, string abrevNo)
        {
            this.ViagemId = viagemId;
            this.HoraPassagem = horaPassagem;
            this.AbreviaturaNo = abrevNo;
        }
    }
}