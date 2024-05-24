# Projeto Integrador ISEP-LEI - 5º Semestre

## **Módulo Master Data Viagem**

### Readme Principal/Wiki

* [Readme com Informação do Projeto](../Project_MDR/README.md)

* [Wiki - SAD](../Project_Wiki/Readme.md)

### Repositório dos Outros Módulos do Projeto

* [Master Data Rede (MDR) e Planeamento](../Project_MDR/)

* [SPA](../Project_SPA/)

### Constituição do Grupo de Trabalho

| |
|-----------|
| Calisto   |
| Esperança |
| Magalhães |
| Oliveira  |
| Pimentel  |

-------------------------

## Run MDV

Run `dotnet run`

Run Watch `dotnet watch run`

URL: `http://localhost:5000/`

## Microsoft Azure

URL: `https://20s5-3na-mdv.azurewebsites.net/api`

Server: `20s5-3na-mdv.database.windows.net`

Username: ***private***

Pass: ***private***

DB Name: MDV-BD

## Code-First - Update Schema and DB

### 7.2. EF Code-First - Add/Update DB Schema (NuGet Package Manager)

| Visual Studio | VS Code |
|---------|---------|
| `Add-Migration migrationName` | `dotnet ef migrations add migrationName` |
| `Update-DataBase` | `dotnet ef database update` |

## Change between In-Memory and Azure DB

Go to `ConfigureServices` in Startup.cs

-------------------------

# ASP.NET Core

ASP.NET Core is an open-source and cross-platform framework for building modern cloud based internet connected applications, such as web apps, IoT apps and mobile backends. ASP.NET Core apps run on [.NET Core](https://dot.net), a free, cross-platform and open-source application runtime. It was architected to provide an optimized development framework for apps that are deployed to the cloud or run on-premises. It consists of modular components with minimal overhead, so you retain flexibility while constructing your solutions. You can develop and run your ASP.NET Core apps cross-platform on Windows, Mac and Linux. [Learn more about ASP.NET Core](https://docs.microsoft.com/aspnet/core/).

## Get Started

Follow the [Getting Started](https://docs.microsoft.com/aspnet/core/getting-started) instructions in the [ASP.NET Core docs](https://docs.microsoft.com/aspnet/index).

Also check out the [.NET Homepage](https://www.microsoft.com/net) for released versions of .NET, getting started guides, and learning resources.

See the [Triage Process](https://github.com/dotnet/aspnetcore/blob/master/docs/TriageProcess.md) document for more information on how we handle incoming issues.

## How to Engage, Contribute, and Give Feedback

Some of the best ways to contribute are to try things out, file issues, join in design conversations,
and make pull-requests.

* [Download our latest daily builds](./docs/DailyBuilds.md)
* Follow along with the development of ASP.NET Core:
    * [Community Standup](https://live.asp.net): The community standup is held every week and streamed live to YouTube. You can view past standups in the linked playlist.
    * [Roadmap](https://github.com/dotnet/aspnetcore/wiki/Roadmap): The schedule and milestone themes for ASP.NET Core.
* [Build ASP.NET Core source code](./docs/BuildFromSource.md)
* Check out the [contributing](CONTRIBUTING.md) page to see the best places to log issues and start discussions.

## Reporting security issues and bugs

Security issues and bugs should be reported privately, via email, to the Microsoft Security Response Center (MSRC)  secure@microsoft.com. You should receive a response within 24 hours. If for some reason you do not, please follow up via email to ensure we received your original message. Further information, including the MSRC PGP key, can be found in the [Security TechCenter](https://technet.microsoft.com/en-us/security/ff852094.aspx).

## Related projects

These are some other repos for related projects:

* [Documentation](https://github.com/aspnet/Docs) - documentation sources for https://docs.microsoft.com/aspnet/core/
* [Entity Framework Core](https://github.com/dotnet/efcore) - data access technology
* [Extensions](https://github.com/dotnet/extensions) - Logging, configuration, dependency injection, and more.
