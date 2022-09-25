using System;
using MDV.Domain.Shared;
using Newtonsoft.Json;

namespace MDV.Domain.Viagens
{
    public class ViagemId : EntityId
    {
        [JsonConstructor]

        public ViagemId(Guid value) : base(value)
        {

        }

        public ViagemId(String value) : base(value)
        {

        }

        private ViagemId() { }

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