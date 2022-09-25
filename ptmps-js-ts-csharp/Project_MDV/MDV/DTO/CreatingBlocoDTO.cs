using Newtonsoft.Json;
using System.Collections.Generic;

namespace MDV.DTO

{
    public class CreatingBlocoDTO
    {
        public string codigo { get; set; }

        public int startTime { get; set; }

        public int endTime { get; set; }

        public List<string> viagens { get; set; }

        public string servicoViaturaId { get; set; }

        [JsonConstructor]
        public CreatingBlocoDTO(string codigo, int startTime, int endTime, List<string> viagens, string servicoViaturaId)
        {
            this.codigo = codigo;
            this.startTime = startTime;
            this.endTime = endTime;
            this.viagens = viagens;
            this.servicoViaturaId = servicoViaturaId;
        }

        public CreatingBlocoDTO(string servicoViaturaId)
        {
            this.servicoViaturaId = servicoViaturaId;
        }
    }
}