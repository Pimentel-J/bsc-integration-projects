using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;
using MDV.Domain.Viagens;
using MDV.Domain.ServicoViaturas;
using MDV.Domain.Blocos;

namespace MDV.Infrastructure.Viagens
{
    internal class ViagemEntityTypeConfiguration : IEntityTypeConfiguration<Viagem>
    {
        public void Configure(EntityTypeBuilder<Viagem> builder)
        {
            builder.Property(b => b.Id).HasConversion(b => b.AsString(), b => new ViagemId(b));
            builder.HasKey(v => new { v.PercursoId, v.HoraInicio });
        }
    }
}