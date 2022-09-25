using System.Collections.Generic;

namespace MDV.DTO
{
  public class CreatingServicoViaturaDto
  {
      public string Id {get; set;}
      public string ViaturaId { get; set; }
      public ICollection<string> Viagens { get; set; }

        public CreatingServicoViaturaDto(string Id, List<string> viagens)
        {
            this.Id = Id;
            this.Viagens = viagens;
        }
    }
}