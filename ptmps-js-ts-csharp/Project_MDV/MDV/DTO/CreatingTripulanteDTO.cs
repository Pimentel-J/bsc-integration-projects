using System;

namespace MDV.DTO
{
    public class CreatingTripulanteDTO
    {
        public string Id { get; set; }
        public string numeroMecanografico { get; private set; }
        public string nome { get; private set; }
        public string dataNascimento { get; private set; }
        public string numeroCartaoCidadao { get; private set; }
        public string nif { get; private set; }
        public string numeroCartaConducao { get; private set; }
        public string dataEmissaoLicencaConducao { get; private set; }
        public string dataValidadeLicencaConducao { get; private set; }
        public string tipoTripulante { get; private set; }
        public string dataEntradaEmpresa { get; private set; }
        public string dataSaidaEmpresa { get; private set; }

        public CreatingTripulanteDTO(string Id, string numeroMecanografico, string nome, string dataNascimento,
        string numeroCartaoCidadao, string nif, string numeroCartaConducao, string dataEmissaoLicencaConducao,
        string dataValidadeLicencaConducao, string tipoTripulante, string dataEntradaEmpresa, string dataSaidaEmpresa)
        {
            this.Id = Id;
            this.numeroMecanografico = numeroMecanografico;
            this.nome = nome;
            this.dataNascimento = dataNascimento;
            this.numeroCartaoCidadao = numeroCartaoCidadao;
            this.nif = nif;
            this.numeroCartaConducao = numeroCartaConducao;
            this.dataEmissaoLicencaConducao = dataEmissaoLicencaConducao;
            this.dataValidadeLicencaConducao = dataValidadeLicencaConducao;
            this.tipoTripulante = tipoTripulante;
            this.dataEntradaEmpresa = dataEntradaEmpresa;
            this.dataSaidaEmpresa = dataSaidaEmpresa;
        }
    }
}