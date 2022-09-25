using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;
using MDV.Domain.Passagens;

namespace MDV.Infrastructure.Passagens
{
    internal class PassagemEntityTypeConfiguration : IEntityTypeConfiguration<Passagem>
    {
        public void Configure(EntityTypeBuilder<Passagem> builder)
        {
            builder.Property(b => b.Id).HasConversion(b => b.AsString(), b => new PassagemId(b));
            builder.Property(b => b.ViagemId).HasConversion(b => b.AsString(), b => new Domain.Viagens.ViagemId(b));
            builder.HasKey(b => b.Id);
        }
    }
}