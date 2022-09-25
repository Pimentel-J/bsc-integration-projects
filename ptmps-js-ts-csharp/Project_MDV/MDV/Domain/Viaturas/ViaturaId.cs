using System;
using MDV.Domain.Shared;
using Newtonsoft.Json;

namespace MDV.Domain.Viaturas
{
    public class ViaturaId : EntityId
    {
        [JsonConstructor]
        public ViaturaId(Guid value) : base(value)
        {
        }

        public ViaturaId(String value) : base(value)
        {
        }   

        private ViaturaId() { }

        override
        protected  Object createFromString(String text){
     //       return new String(text);
            return text;
        }

        override
        public String AsString(){
            string obj = base.ObjValue.ToString();
            return obj;
        }
    }
}
       