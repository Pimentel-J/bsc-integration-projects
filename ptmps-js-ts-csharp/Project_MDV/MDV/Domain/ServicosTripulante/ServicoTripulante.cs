using MDV.Domain.Shared;
using System.Text.RegularExpressions;

namespace MDV.Domain.ServicosTripulante
{

    public class ServicoTripulante : Entity<ServicoTripulanteId>, IAggregateRoot
    {
        public string codigoServicoTripulante { get; private set; }
        // public ICollection<Bloco> Blocos { get; private set; }

        private ServicoTripulante() { }

        public ServicoTripulante(string codigoServicoTripulante)
        {
            if (!isCodigoServicoTripulanteCorrect(codigoServicoTripulante))
                throw new BusinessRuleValidationException("Código Serviço Tripulante incorreto " +
                "(sequência alfanumérica de 10 caracteres)");

            this.Id = new ServicoTripulanteId(codigoServicoTripulante);

            this.codigoServicoTripulante = codigoServicoTripulante;
        }

        private bool isCodigoServicoTripulanteCorrect(string codigoServicoTripulante)
        {
            string pattern = @"^([A-zÀ-ú0-9]{10})$";
            return (Regex.IsMatch(codigoServicoTripulante, pattern));
        }

    }

}