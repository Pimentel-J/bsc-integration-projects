using System.Collections.Generic;
namespace MDV.DTO
{
  public class BlocoDTO
  {
        public string Codigo { get; set; }
        
        public int StartTime { get; set; }

        public int EndTime { get; set; }        

        public List<string> Viagens {get; set; }

        public string ServicoViaturaId { get; set; }

        public string ServicoTripulanteId { get; set; }
        

  }
}