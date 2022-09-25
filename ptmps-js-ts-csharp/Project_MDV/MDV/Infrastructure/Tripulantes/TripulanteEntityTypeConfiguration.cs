using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;
using MDV.Domain.Tripulantes;

namespace MDV.Infrastructure.Tripulantes
{
    internal class TripulanteEntityTypeConfiguration : IEntityTypeConfiguration<Tripulante>
    {
        public void Configure(EntityTypeBuilder<Tripulante> builder)
        {
            builder.Property(tripulante => tripulante.Id)
            .HasConversion(id => id.AsString(), id => new TripulanteId(id));
            builder.HasKey(tripulante => tripulante.Id);
        }
    }
}