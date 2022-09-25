# Sprint A - Requisitos Funcionais e Não Funcionais:

1.	Planear o esqueleto da aplicação. Identificar que módulos e interfaces (detalhadas) de cada módulo. Identificar diferenças de terminologia se existirem entre o vários domínios.
2.	Setup dos projetos e repositórios Git (Bitbucket).
3.	Design arquitetural:
    1.	Nível 1: vista lógica e de cenários
    2.	Nível 2: vista lógica, de processo, de implementação e física
    3.	Nível 3 (Master Data Rede): vista lógica, de processo e de implementação
    4.	Adoção de estilos/padrões: cliente-servidor, SOA, DDD, Onion (inclui DI, IoC), DTO, ..."

4.	Tecnologia: Node.js, SGBD orientado a documentos (e.g. MongoDB), ODM (e.g. Mongoose)
5.	Testes unitários e de integração (com isolamento via mocks)
6.	Implantação na cloud (e.g. Heroku, MongoDB Atlas)
7.	Pipelines (Bitbucket Pipelines)
8.	serviços de backend para os seguintes casos de uso:
    1.	Como data administrator, quero importar nós, percursos, linhas, tipos de viatura e tipos de tripulantes de dum ficheiro .glx. 
    2.	Como data administrator, quero criar nós da rede indicando o seu nome, se é ou não uma estação de recolha ou ponto de rendição e as suas coordenadas. 
    3.	Como data administrator, quero criar linha indicando o seu código (ex., "C"), nome (ex., "Linha Verde") e os seus nós terminais (ex., Campanhã, ISMAI), bem como eventuais restrições sobre o tipo de viatura e tipo de tripulante. 
    4.	Como data administrator, quero definir um percurso de ida/volta de uma linha. Definir os vários segmentos que constituem um percurso indicando a ordem e a distância e tempo de viagem de cada segmento 
    5.	Como data administrator, quero criar tipo de tripulante, ex., "motorista sénior com conhecimento de línguas estrangeiras". Um tipo de tripulante é uma descrição livre (não catalogada) de características. 
    6.	Como data administrator, quero criar tipo de viatura (ex., "minibus a gasóleo"), indicando o seu tipo de combustível (i.e., Diesel, Gasolina, Elétrico, GPL, Gás), autonomia, custo por quilómetro, consumo médio e velocidade média.  

Como requisitos de menor prioridade para este sprint (podem não ser apresentados neste sprint e passar para o próximo):
1.	Serviços de backend:
    1.	Como data administrator, quero listar nós de rede. O utilizador deve poder ordenar por código/nome e filtrar por código/nome (ex., todos os nós cujo nome começa por "Par") os resultados.
    2.	Como data administrator, quero listar linhas. O utilizador deve poder ordenar por código/nome e filtrar os resultados por código/nome (ex., todos as linhas cujo nome começa por "Par").
    3.	Como data administrator, quero listar percursos duma linha.
