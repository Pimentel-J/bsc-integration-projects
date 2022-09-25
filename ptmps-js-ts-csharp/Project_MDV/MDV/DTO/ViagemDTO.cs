using System;
using System.Collections.Generic;

namespace MDV.DTO
{
    public class ViagemDTO
    {
        public Guid Id { get; set; }

        public string Key { get; set; }
        public string PercursoId { get; set; }
        public string ServicoViaturaId {get; set;}
        public string Descritivo { get; set; }
        public int HoraInicio { get; set; }
        public int HoraFim { get; set; }
        public ICollection<string> Passagens { get; set; }

        public ViagemDTO()
        {

        }

        public ViagemDTO(Guid Id, string percId)
        {
            this.Id = Id;
            this.PercursoId = percId;
        }

        public ViagemDTO(string key, string percId, int horaInicio)
        {
            this.Key = key;
            this.PercursoId = percId;
            this.HoraInicio = horaInicio;
        }

        public ViagemDTO(Guid Id, string percId, string servViatId, ICollection<string> passagens)
        {
            this.Id = Id;
            this.PercursoId = percId;
            this.ServicoViaturaId = servViatId;
            this.Passagens = passagens;
        }

        public ViagemDTO(Guid Id, string percId, string servViatId, string desc, ICollection<string> passagens)
        {
            this.Id = Id;
            this.PercursoId = percId;
            this.ServicoViaturaId = servViatId;
            this.Descritivo = desc;
            this.Passagens = passagens;
        }

        public ViagemDTO(Guid id, string percursoId, string servicoViaturaId, string descritivo, int horaInicio, int horaFim, ICollection<string> passagens) : this(id, percursoId)
        {
            ServicoViaturaId = servicoViaturaId;
            Descritivo = descritivo;
            HoraInicio = horaInicio;
            HoraFim = horaFim;
            Passagens = passagens;
        }
    }
}