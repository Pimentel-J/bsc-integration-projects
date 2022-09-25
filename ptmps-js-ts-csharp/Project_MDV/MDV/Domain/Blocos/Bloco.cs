using MDV.Domain.Shared;
using MDV.Domain.ServicoViaturas;
using System.Collections.Generic;
using MDV.Domain.Viagens;
using MDV.Domain.ServicosTripulante;

namespace MDV.Domain.Blocos
{
    public class Bloco : Entity<BlocoId>, IAggregateRoot
    {

        public int startTime { get; set; }

        public int endTime { get; set; }        

        public ICollection<Viagem> viagens { get; set; }

        public ServicoViaturaId servicoViaturaId { get; set; }

        public ServicoTripulanteId servicoTripulanteId { get; set; }

        public Bloco(){}

        public Bloco(BlocoId Id, int startTime, int endTime, ServicoViaturaId servicoViaturaId, List<Viagem> vig)
        {
            this.viagens = new List<Viagem>();
            this.Id = Id;
            this.startTime = startTime;
            this.endTime = endTime;
            this.servicoViaturaId = servicoViaturaId;
            foreach (var item in vig)
            {
              this.viagens.Add(item);              
            }
        }

        public void change(int startTime, int endTime, ServicoViaturaId servicoViaturaId ,List<Viagem> viagens){
            this.viagens = viagens;
            this.startTime = startTime;
            this.endTime = endTime;
            this.servicoViaturaId = servicoViaturaId;
        }

        public void AtualizaServicoTripulante(ServicoTripulanteId servicoTripulanteId)
        {
            this.servicoTripulanteId = servicoTripulanteId;
        }

    //    public List<Models.Viagem> viagens { get; set; }


    }
}
