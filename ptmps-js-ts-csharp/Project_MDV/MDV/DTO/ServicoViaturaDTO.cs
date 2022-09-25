using System;
using System.Collections.Generic;
using System.Linq;
using MDV.Domain.Viaturas;
using MDV.Domain.Viagens;


namespace MDV.DTO
{
    public class ServicoViaturaDTO
    {
        public string Id { get; set; }
        public string ViaturaId { get; set; }
        public HashSet<string> Viagens { get; set; }

    }
}