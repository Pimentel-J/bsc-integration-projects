
namespace MDV.DTO
{
    public class ServicoTripulanteDTO
    {
        public string Id { get; set; }
        public string codigoServicoTripulante { get; private set; }

        public ServicoTripulanteDTO(string Id, string codigoServicoTripulante)
        {
            this.Id = Id;
            this.codigoServicoTripulante = codigoServicoTripulante;
        }

    }
}