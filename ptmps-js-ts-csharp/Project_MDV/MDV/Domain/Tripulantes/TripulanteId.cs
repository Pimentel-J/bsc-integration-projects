using System;
using MDV.Domain.Shared;
using Newtonsoft.Json;

namespace MDV.Domain.Tripulantes
{
    public class TripulanteId : EntityId
    {
        [JsonConstructor]
        public TripulanteId(Guid value) : base(value)
        {
        }

        public TripulanteId(String value) : base(value)
        {
        }   

        private TripulanteId() { }

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
       