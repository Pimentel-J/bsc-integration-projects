using System;
using MDV.Domain.Shared;
using MDV.Domain.Viaturas;
using MDV.Domain.Viagens;
using MDV.Domain.Blocos;
using System.Collections.Generic;


namespace MDV.Domain.ServicoViaturas
{
    public class ServicoViatura : Entity<ServicoViaturaId>, IAggregateRoot
    {
        public ViaturaId ViaturaId { get; private set; }

        public ICollection<Viagem> Viagens {get; private set;}
        public ICollection<Bloco> Blocos {get; private set;}
        
        private ServicoViatura() {}

        public ServicoViatura(ServicoViaturaId Id)
        {
            this.Id = Id;
            this.Viagens = new List<Viagem>();
        }

        public ServicoViatura(ServicoViaturaId Id ,ViaturaId viaturaId)
        {
            if (viaturaId == null)
                throw new BusinessRuleValidationException("Matricula Invalida.");

            this.Id = Id;
            this.ViaturaId = viaturaId;
            this.Viagens = new List<Viagem>();
        }

        public ServicoViatura(ServicoViaturaId Id ,ViaturaId viaturaId,List<Viagem> viagens)  
        {
            this.Id = Id;
            this.ViaturaId = viaturaId;
            this.Viagens = viagens;
            
        }

        public void AdicionarViagens(Viagem v)
        {
            this.Viagens.Add(v);
        }

    }

}