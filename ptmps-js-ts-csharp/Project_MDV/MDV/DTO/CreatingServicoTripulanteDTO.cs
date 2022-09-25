
namespace MDV.DTO
{
    public class CreatingServicoTripulanteDTO
    {
        public string Id { get; set; }
        public string codigoServicoTripulante { get; private set; }

        public CreatingServicoTripulanteDTO(string codigoServicoTripulante)
        {
            this.codigoServicoTripulante = codigoServicoTripulante;
        }

        public CreatingServicoTripulanteDTO(string Id, string codigoServicoTripulante)
        {
            this.Id = Id;
            this.codigoServicoTripulante = codigoServicoTripulante;
        }
    }
}