using System;
using MDV.Domain.Shared;
using Newtonsoft.Json;

namespace MDV.Domain.Passagens
{

    public class PassagemId : EntityId
    {
        [JsonConstructor]
        public PassagemId(Guid value) : base(value)
        {
        }

        public PassagemId(String value) : base(value)
        {
        }

        override
        protected Object createFromString(String text)
        {
            return new Guid(text);
        }

        override
        public String AsString()
        {
            Guid obj = (Guid)base.ObjValue;
            return obj.ToString();
        }


        public Guid GetAsGuid()
        {
            return (Guid)base.ObjValue;
        }
    }
}