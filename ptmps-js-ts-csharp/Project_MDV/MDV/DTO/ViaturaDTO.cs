using System;


namespace MDV.DTO
{
  public class ViaturaDTO
  {
        public string Id { get; set; }

        public string niv { get; set; }

        public string tipoviatura { get; set; }

        public string data_entrada_servico { get; set; }

        public ViaturaDTO(string id, string niv, string tipoviatura, string data_entrada_servico) {

              this.Id = id;
              this.niv = niv;
              this.tipoviatura = tipoviatura;
              this.data_entrada_servico = data_entrada_servico;
        }

  }
}