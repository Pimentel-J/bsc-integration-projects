using System;
using System.Collections.Generic;
using MDV.Domain.Shared;
using System.Text.RegularExpressions;
using System.ComponentModel.DataAnnotations;

namespace MDV.Domain.Tripulantes
{

    public class Tripulante : Entity<TripulanteId>, IAggregateRoot
    {
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

        private Tripulante() { }

        public Tripulante(string numeroMecanografico, string nome, string dataNascimento, string numeroCartaoCidadao,
        string nif, string numeroCartaConducao, string dataEmissaoLicencaConducao, string dataValidadeLicencaConducao,
        string tipoTripulante, string dataEntradaEmpresa, string dataSaidaEmpresa)
        {

            if (tipoTripulante == null)
                throw new BusinessRuleValidationException("Tipo de Tripulante Inválido.");

            if (!isNumeroMecanograficoCorrect(numeroMecanografico))
                throw new BusinessRuleValidationException("Número Mecanográfico incorreto (sequência alfanumérica de 9 caracteres)");

            // if (!isNifCorrect(nif))
            //     throw new BusinessRuleValidationException("NIF incorreto (sequência de 9 algarismos)");

            this.Id = new TripulanteId(numeroMecanografico);

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

        public void change(string nome, string dataNascimento,
        string numeroCartaoCidadao, string nif, string numeroCartaConducao, string dataEmissaoLicencaConducao,
        string dataValidadeLicencaConducao, string tipoTripulante, string dataEntradaEmpresa, string dataSaidaEmpresa)
        {
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

        private bool isNumeroMecanograficoCorrect(string numeroMecanografico)
        {
            string pattern = @"^([A-zÀ-ú0-9]{9})$";
            return (Regex.IsMatch(numeroMecanografico, pattern));
        }

        private bool isNifCorrect(string nif)
        {
            string pattern = @"^(\d{9})$";
            return (Regex.IsMatch(nif, pattern));
        }
    }

}