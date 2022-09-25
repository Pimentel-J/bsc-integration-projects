using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;
using MDV.Domain.ServicosTripulante;

namespace MDV.Infrastructure.ServicosTripulante
{
    internal class ServicoTripulanteEntityTypeConfiguration : IEntityTypeConfiguration<ServicoTripulante>
    {
        public void Configure(EntityTypeBuilder<ServicoTripulante> builder)
        {
            builder.Property(servicoTripulante => servicoTripulante.Id)
            .HasConversion(id => id.AsString(), id => new ServicoTripulanteId(id));
            builder.HasKey(servicoTripulante => servicoTripulante.Id);
        }
    }

}