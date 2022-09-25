using System.Collections.Generic;
using System;
using MDV.Domain.Shared;
using MDV.Domain.Passagens;

namespace MDV.Domain.Viagens
{
    public class Viagem : Entity<ViagemId>, IAggregateRoot
    {
        //public ViagemId Id { get; private set; }
        public string Key { get; private set; }
        public string PercursoId { get; private set; }
        public string ServicoViaturaId {get; private set;}
        public int HoraInicio { get; private set; }
        public int HoraFim { get; private set; }
        public string Descritivo { get; private set; }
        public  ICollection<Passagem> Passagens { get;  set; }

      //  public ServicoViatura ServicoViatura {get; private set;}

        private Viagem() {}

        public Viagem(string key, string percId)
        {
            this.Id = new ViagemId(Guid.NewGuid());
            this.Key = key;
            this.PercursoId = percId;
            this.Passagens = new List<Passagem>();
        }

        public void AdicionarPassagem(Passagem passagem)
        {
            this.Passagens.Add(passagem);
        }

        public void AdicionarHoraInicio(int horaInicio)
        {
            this.HoraInicio = horaInicio;
        }

        public void AdicionarHoraFim(int horaFim)
        {
            this.HoraFim = horaFim;
        }
        public void AlterarDescritivo(string desc)
        {
            this.Descritivo = desc;
        }

        public void change(string percId, string servViatId, ICollection<Passagem> lstPass)
        {
            this.PercursoId = percId;
            this.ServicoViaturaId = servViatId;
            this.Passagens = lstPass;
        }
    }

}