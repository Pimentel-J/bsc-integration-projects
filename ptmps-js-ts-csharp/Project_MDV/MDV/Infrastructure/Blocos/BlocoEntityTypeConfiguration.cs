using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;
using MDV.Domain.Blocos;

namespace MDV.Infrastructure.Blocos
{
    internal class BlocoEntityTypeConfiguration : IEntityTypeConfiguration<Bloco>
    {
        public void Configure(EntityTypeBuilder<Bloco> builder)
        {
            builder.Property(b => b.Id).HasConversion(b => b.AsString(), b => new BlocoId(b));
            builder.Property(b => b.servicoViaturaId).HasConversion(b=>b.AsString(), b=> new Domain.ServicoViaturas.ServicoViaturaId(b));
            builder.Property(b => b.servicoTripulanteId).HasConversion(b => b.AsString(), b => new Domain.ServicosTripulante.ServicoTripulanteId(b));
            builder.HasKey(b => b.Id);
        }
    }
}