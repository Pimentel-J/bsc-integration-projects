using System;


namespace MDV.DTO
{
    public class CreatingViaturaDTO
    {
        public string id { get; set; }
        public string niv { get; set; }
        public string tipoviatura { get; set; }
        public string data_entrada_servico { get; set; }

        public CreatingViaturaDTO(string id, string niv, string tipoviatura,
                      string dataEntServ)
        {
            this.id = id;
            this.niv = niv;
            this.tipoviatura = tipoviatura;
            this.data_entrada_servico = dataEntServ;

        }
    }
}