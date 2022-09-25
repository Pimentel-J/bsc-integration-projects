using MDV.Domain.Viaturas;
using MDV.Domain.Blocos;
using MDV.Domain.ServicoViaturas;
using MDV.Domain.Viagens;
using MDV.Infrastructure;
using System;
using System.Linq;
using System.Collections.Generic;

namespace MDV.Infrastructure
{
    public static class DbInitializer
    {
        public static void Initialize(MDVDbContext context)
        {
            context.Database.EnsureCreated();

            if (context.Viaturas.Any())
            {
                return;   // DB has been seeded
            }

            /*var viaturas = new Viatura[]
            {
            new Viatura("32-27-EX","234234LDR12345657","MiniAutocarro", "2005-09-01"),
            new Viatura("32-31-EX","234234LDR12345100","MiniAutocarro", "2005-09-01"),
            new Viatura("32-30-EX","234234LDR12345200","MiniAutocarro", "2005-09-01"),
           
            };
            foreach (Viatura s in viaturas)
            {
                context.Viaturas.Add(s);
            }
            context.SaveChanges();
            Viagem v = new Viagem("asda");
            List<Viagem> lista = new List<Viagem>();
            lista.Add(v);
            var serViatura = new ServicoViatura[]
            {
            new ServicoViatura(new ServicoViaturaId("1"),new ViaturaId("32-27-EX")),
            new ServicoViatura(new ServicoViaturaId("2"),new ViaturaId("32-27-EX")),
            new ServicoViatura(new ServicoViaturaId("3"),new ViaturaId("32-27-EX"))
           
            };
            foreach (ServicoViatura serv in serViatura)
            {
                context.ServicoViaturas.Add(serv);
            }
            context.SaveChanges();

            Viagem a = new Viagem("1");
            Viagem b = new Viagem("2");
            Viagem c = new Viagem("3");
            List<Viagem> lista1 = new List<Viagem>();
            lista1.Add(a);
            List<Viagem> lista2 = new List<Viagem>();
            lista2.Add(b);
            List<Viagem> lista3 = new List<Viagem>();
            lista3.Add(c);
            var blocos = new Bloco[]
            {
            new Bloco(new BlocoId("1118"), 1500, 1800, new ServicoViaturaId("1"), lista1),
            new Bloco(new BlocoId("128"), 1500, 1800, new ServicoViaturaId("1"), lista2),
            new Bloco(new BlocoId("150"), 1500, 1800, new ServicoViaturaId("1"), lista3)
           
            };
            foreach (Bloco bl in blocos)
            {
                context.Blocos.Add(bl);
            }
            context.SaveChanges();*/

        }
    }
}
