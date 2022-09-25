using Microsoft.EntityFrameworkCore;
using MDV.Domain.Blocos;
using MDV.Infrastructure.Blocos;
using MDV.Infrastructure.Viaturas;
using MDV.Infrastructure.ServicoViaturas;
using MDV.Infrastructure.Viagens;
using MDV.Infrastructure.Passagens;
using MDV.Infrastructure.Tripulantes;
using MDV.Infrastructure.ServicosTripulante;
using MDV.Domain.Viaturas;
using MDV.Domain.Viagens;
using MDV.Domain.Passagens;
using MDV.Domain.ServicoViaturas;
using MDV.Domain.Tripulantes;
using MDV.Domain.ServicosTripulante;

namespace MDV.Infrastructure
{
    public class MDVDbContext : DbContext
    {
        public DbSet<Bloco> Blocos { get; set; }
        public DbSet<Viatura> Viaturas { get; set; }
        public DbSet<Viagem> Viagens { get; set; }
        public DbSet<Passagem> Passagens { get; set; }
        public DbSet<ServicoViatura> ServicoViaturas { get; set; }
        public DbSet<Tripulante> Tripulantes { get; set; }
        public DbSet<ServicoTripulante> ServicosTripulante { get; set; }

        public MDVDbContext(DbContextOptions options) : base(options)
        {

        }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.ApplyConfiguration(new BlocoEntityTypeConfiguration());
            modelBuilder.ApplyConfiguration(new ViaturaEntityTypeConfiguration());
            modelBuilder.ApplyConfiguration(new ServicoViaturaEntityTypeConfiguration());
            modelBuilder.ApplyConfiguration(new PassagemEntityTypeConfiguration());
            modelBuilder.ApplyConfiguration(new ViagemEntityTypeConfiguration());
            modelBuilder.ApplyConfiguration(new TripulanteEntityTypeConfiguration());
            modelBuilder.ApplyConfiguration(new ServicoTripulanteEntityTypeConfiguration());
            
        }
    }
}