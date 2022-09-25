using Newtonsoft.Json;

namespace MDV.DTO
{
    public class CreatingViagemDTO
    {
        public int HoraInicio { get; set; }
        public string PercursoId { get; set; }
        public string PercursoIdaId { get; set; }
        public string PercursoVoltaId { get; set; }
        public int Frequencia { get; set; }
        public int NViagens { get; set; }

        public CreatingViagemDTO(string percId)
        {
            this.PercursoId = percId;
        }

        //[JsonConstructor]
        //public CreatingViagemDTO(int horaInicio, string percId)
        //{
        //    this.HoraInicio = horaInicio;
        //    this.PercursoId = percId;
        //}

        [JsonConstructor]
        public CreatingViagemDTO(int horaInicio, string percursoId, string percursoIdaId, string percursoVoltaId, int frequencia, int nViagens)
        {
            this.HoraInicio = horaInicio;
            this.PercursoId = percursoId;
            this.PercursoIdaId = percursoIdaId;
            this.PercursoVoltaId = percursoVoltaId;
            this.Frequencia = frequencia;
            this.NViagens = nViagens;
        }
    }
}