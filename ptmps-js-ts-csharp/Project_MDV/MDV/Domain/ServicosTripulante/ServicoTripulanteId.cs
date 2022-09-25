using System;
using MDV.Domain.Shared;
using Newtonsoft.Json;

namespace MDV.Domain.ServicosTripulante
{
    public class ServicoTripulanteId : EntityId
    {
        [JsonConstructor]
        public ServicoTripulanteId(Guid value) : base(value)
        {
        }

        public ServicoTripulanteId(String value) : base(value)
        {
        }   

        private ServicoTripulanteId() { }

        override
        protected  Object createFromString(String text){
            // return new String(text);
            return text;
        }

        override
        public String AsString(){
            string obj = base.ObjValue.ToString();
            return obj;
        }
        
        public Guid GetAsGuid()
        {
            return (Guid)base.ObjValue;
        }
    }
}
       