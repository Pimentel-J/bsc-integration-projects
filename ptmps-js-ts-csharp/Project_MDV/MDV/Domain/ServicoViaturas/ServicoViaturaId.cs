using System;
using MDV.Domain.Shared;
using Newtonsoft.Json;

namespace MDV.Domain.ServicoViaturas
{
    public class ServicoViaturaId : EntityId
    {
        public ServicoViaturaId(String value) : base(value)
        {
        }

        private ServicoViaturaId() { }

        override
        protected Object createFromString(String text)
        {
                  return new string(text);
            //return text;
        }

        override
        public String AsString()
        {
            string obj = base.ObjValue.ToString();
            return obj;
        }

         public Guid GetAsGuid()
        {
            return (Guid)base.ObjValue;
        }
    }
}
