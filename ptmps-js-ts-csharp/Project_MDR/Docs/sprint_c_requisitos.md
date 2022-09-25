# Sprint C - Requisitos Funcionais e Não Funcionais:


|módulo	|submódulo	|descrição breve/user story
|---------|---------|---------|
|	Desenho e operação de sistemas	|		|	Como administrador da infraestrutura quero que seja criada uma SAN iSCSI nos servidores Linux e Windows disponíveis para qualquer utilizador autenticado 	|
|	Desenho e operação de sistemas	|		|	Como administrador da infraestrutura quero que a SAN anterior esteja disponível sem necessidade de intervenção humana após um reboot de qualquer dos servidores	|
|	Desenho e operação de sistemas	|		|	Como administrador do servidor Linux quero que semanalmente seja verificado se todos os utilizadores registados em /etc/passwd possuem uma entrada no /etc/shadow, se o grupo primário existe, se a homedir existe e pertence ao dono e grupo correto. Qualquer inconformidade deve ser registada em /tmp/auth_error	|
|	Desenho e operação de sistemas	|		|	Como administrador da infraestrutura quero que todos os utilizadores registados no DC Windows tenham a sessão bloqueada ao fim de 3 minuto de inatividade 	|
|	Master Data Viagens	|		|	Setup/atualização dos projetos, repositórios Git e Pipelines	|
|	Master Data Viagens	|		| • Design arquitetural: |
||| o Nível 1: vista lógica e de cenários (revisão)  |
||| o Nível 2: vista lógica, de processo, de implementação e física (revisão) |
||| o Nível 3 (UI/SPA): vista lógica, de processo e de implementação |
||| o Adoção de estilos/padrões: cliente-servidor, SOA, DDD, Onion (inclui DI, IoC), DTO, ...	|
|	Master Data Viagens	|		|	Tecnologia: ASP.NET, C#, RDBMS	|
|	Master Data Viagens	|		|	Testes unitários, de integração (com isolamento via mocks) e End2End	|
|	Master Data Viagens	|		|	Implantação na cloud (e.g., MS Azure, MS Sql Server)	|
|	Master Data Viagens	|		|	Como data administrator, quero definir viatura	|
|	Master Data Viagens	|		|	Como data administrator, quero definir tripulante	|
|	Master Data Viagens	|		|	Como data administrator, quero criar viagem	|
|	Master Data Viagens	|		|	Como data administrator, quero criar bloco de trabalho	|
|	Master Data Viagens	|		|	Como data administrator quero criar um serviço de viatura ad hoc |
|	Planeamento	|		|	Consumo de dados de viagens através da API do master data	|
|	Planeamento	|		|	Adequação de Algoritmos Genéticos a problemas do tipo de escalonamento de motoristas/tripulações	|
|	Planeamento	|		|	Estudo de critérios de avaliação da viabilidade e qualidade das soluções geradas	|
|	Planeamento	|		|	Estudo das soluções obtidas pelos algoritmos usados em função dos parâmetros usados	|
|	SPA	|	Planeamento	|	Como gestor pretendo visualizar as soluções do algoritmo genético	|
|	SPA	|	RGPD	|	Apresentar o texto da informação legal devida no momento de registo de utilizadores, em conformidade com o RGPD	|
|	SPA	|	RGPD	|	Como cliente final pretendo registar-me na aplicação	|
|	SPA	|	RGPD	|	Como cliente final pretendo dar os meus consentimentos no âmbito do RGPD no momento de registo	|
|	SPA	|	Viagens	|	Como data administrator, quero definir viatura	|
|	SPA	|	Viagens	|	Como data administrator, quero definir tripulante	|
|	SPA	|	Viagens	|	Como data administrator, quero criar viagem	|
|	SPA	|	Viagens	|	Como data administrator, quero criar bloco de trabalho	|
|	SPA	|	Viagens	|	Como data administrator quero criar um serviço de viatura ad hoc	|
|	SPA	|	Visualização	|	Adicionar um objecto de interacção (por exemplo, um botão) que permita comutar o tipo de representação gráfica: 2D ou 3D.	|
|	SPA	|	Visualização	|	Na representação 3D, o comando da câmara orbit deverá estar activo (sugestão: botão esquerdo do rato – orbit); na representação 2D deverá permanecer inactivo	|
|	SPA	|	Visualização	|	Modelar ou importar objectos 3D representativos dos nós da rede e colocá-los nas posições apropriadas da cena 	|


## Algumas Regras de Negócio:


|  |  |
|---------|---------|
| Tripulante | Caracteriza-se pelo seu número mecanográfico, nome, data de nascimento, numero de cartão de cidadão, NIF, tipo de tripulante, data de entrada na empresa, data de saída da empresa. O número mecanográfico é uma sequencia alfanumérica de 9 caracteres. |
| Viagem | Caracteriza-se pelas horas de passagem em cada um dos nós do percurso da linha. |
| Bloco de Trabalho | Caracteriza-se pelo instante de inicio e instante de fim, bem como a quais as viagens a que se refere. Para realizar uma viagem podem ser necessários vários blocos de trabalho e um bloco de trabalho pode cumprir apenas parcialmente uma viagem. |
| Matrícula | É o identificador de negócio da viatura |

