using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;
using MDV.Domain.Viaturas;

namespace MDV.Infrastructure.Viaturas
{
    internal class ViaturaEntityTypeConfiguration : IEntityTypeConfiguration<Viatura>
    {
        public void Configure(EntityTypeBuilder<Viatura> builder)
        {
            builder.Property(b => b.Id).HasConversion(b => b.AsString(), b => new ViaturaId(b));
            builder.HasKey(v => v.Id);
        }

    }
}