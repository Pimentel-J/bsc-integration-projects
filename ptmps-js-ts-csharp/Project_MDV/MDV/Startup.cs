using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;
using Microsoft.EntityFrameworkCore.Storage.ValueConversion;
using MDV.Infrastructure;
using MDV.Infrastructure.Blocos;
using MDV.Infrastructure.Viaturas;
using MDV.Infrastructure.ServicoViaturas;
using MDV.Infrastructure.Viagens;
using MDV.Infrastructure.Passagens;
using MDV.Infrastructure.Tripulantes;
using MDV.Infrastructure.ServicosTripulante;
using MDV.Infrastructure.Shared;
using MDV.Domain.Shared;
using MDV.Services;
using MDV.Repositories;


namespace MDV
{
    public class Startup
    {
        public Startup(IConfiguration configuration)
        {
            Configuration = configuration;
        }

        public IConfiguration Configuration { get; }

        // This method gets called by the runtime. Use this method to add services to the container.
        public void ConfigureServices(IServiceCollection services)
        {
          /*  services.AddDbContext<MDVDbContext>(opt =>
                opt.UseInMemoryDatabase("MDVDB")
                .ReplaceService<IValueConverterSelector, StronglyEntityIdValueConverterSelector>());*/

            /* services.AddDbContext<MDVDbContext>(options =>
                 options.UseSqlServer(Configuration.GetConnectionString("DefaultConnection")));*/

            services.AddDbContext<MDVDbContext>(options =>
                options.UseSqlServer(Configuration.GetConnectionString("AzureConnection")));

            services.AddHttpClient();

            services.AddCors();

            services.AddMvc();

            ConfigureMyServices(services);

            services.AddControllers().AddNewtonsoftJson();
        }

        // This method gets called by the runtime. Use this method to con;gure the HTTP request pipeline.
        public void Configure(IApplicationBuilder app, IWebHostEnvironment env)
        {
            if (env.IsDevelopment())
            {
                app.UseDeveloperExceptionPage();
            }

            app.UseHttpsRedirection();

            app.UseRouting();

            app.UseAuthorization();

            app.UseCors(builder => builder
                 .AllowAnyOrigin()
                 .AllowAnyMethod()
                 .AllowAnyHeader());  

            app.UseEndpoints(endpoints =>
            {
                endpoints.MapControllers();
            });
        }

        public void ConfigureMyServices(IServiceCollection services)
        {
            services.AddTransient<IUnitOfWork,UnitOfWork>();

            services.AddTransient<IBlocoRepository,BlocoRepository>();
            services.AddTransient<BlocoService>();

            services.AddTransient<IViaturaRepository,ViaturaRepository>();
            services.AddTransient<ViaturaService>();
            
            services.AddTransient<IServicoViaturaRepository,ServicoViaturaRepository>();
            services.AddTransient<ServicoViaturaService>();

            services.AddTransient<IViagemRepository, ViagemRepository>();
            services.AddTransient<IPassagemRepository, PassagemRepository>();
            services.AddTransient<PassagemService>();
            services.AddTransient<ViagemService>();
            services.AddTransient<CriarViagemService>();

            services.AddTransient<ITripulanteRepository,TripulanteRepository>();
            services.AddTransient<TripulanteService>();

            services.AddTransient<IServicoTripulanteRepository,ServicoTripulanteRepository>();
            services.AddTransient<ServicoTripulanteService>();

        }
    }
}
