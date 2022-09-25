using System;
using MDV.Domain.Shared;

namespace MDV.Domain.Blocos
{
    public class BlocoId : EntityId
    {

        public BlocoId(String value) : base(value)
        {
            
        }

        override
        protected  Object createFromString(String text)
        {
            return new string(text);
        }

        override
        public String AsString(){
            string obj = base.ObjValue.ToString();
            return obj;
        }
    }
}
       