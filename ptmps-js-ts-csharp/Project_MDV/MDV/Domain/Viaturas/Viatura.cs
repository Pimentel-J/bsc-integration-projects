using System;
using System.Collections.Generic;
using MDV.Domain.Shared;
using MDV.Domain.ServicoViaturas;
using System.Text.RegularExpressions;


namespace MDV.Domain.Viaturas
{

    public  class Viatura : Entity<ViaturaId>, IAggregateRoot
    {
      
        //public string matricula { get; private set ;}
    
        public string niv { get; private set; }
        public string tipoviatura { get; private set;}
        public string data_entrada_servico {get ; private set;}
        public ICollection<ServicoViatura> ServicoViaturas { get; set; }
     
        private Viatura() {}

        public Viatura (string matricula, string niv, string tipoviatura, 
                         string data_ent_servico) {

            if (tipoviatura == null)
                throw new BusinessRuleValidationException("Tipo de Viatura Inválido.");

            if (!isMatricula(matricula))
                 throw new BusinessRuleValidationException("Matricula sem estrutura adequada XX-99-99 ou 99-XX-99 ou 99-99-XX");

            if (!isNivCorrect(niv))
                throw new BusinessRuleValidationException("N I V não está correto");

            this.Id = new ViaturaId(matricula);
           // this.matricula = matricula;

            this.niv = niv;
               

            this.tipoviatura = tipoviatura;
            this.data_entrada_servico = data_ent_servico;
        }

        public void change(string niv, string tipoviatura, string data_ent_servico){
        
            this.niv = niv;
            this.tipoviatura = tipoviatura;
            this.data_entrada_servico = data_ent_servico;
        }

        private bool isMatricula(string matricula) {
            string pattern = @"^(([A-Z]{2}-\d{2}-(\d{2}|[A-Z]{2}))|(\d{2}-(\d{2}-[A-Z]{2}|[A-Z]{2}-\d{2})))$";
            return (Regex.IsMatch(matricula,pattern));
        }

        private bool isNivCorrect(string niv) {
            string pattern = @"^[A-HJ-NPR-Z0-9]{17}$";
            return (Regex.IsMatch(niv,pattern));
        }
    }

}