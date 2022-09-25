using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;
using MDV.Domain.ServicoViaturas;
using MDV.Domain.Viaturas;

namespace MDV.Infrastructure.ServicoViaturas
{
    internal class ServicoViaturaEntityTypeConfiguration : IEntityTypeConfiguration<ServicoViatura>
    {
        public void Configure(EntityTypeBuilder<ServicoViatura> builder)
        {
            builder.Property(b => b.ViaturaId).HasConversion(b => b.AsString(), b => new ViaturaId(b));
            builder.Property(b => b.Id).HasConversion(b => b.AsString(), b => new ServicoViaturaId(b));
            builder.HasKey(sv => sv.Id);
        }
    
    }
}