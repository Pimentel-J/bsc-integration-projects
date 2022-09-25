# Sprint B - Requisitos Funcionais e Não Funcionais:


|módulo	|submódulo	|descrição breve/user story
|---------|---------|---------|
|	Desenho e operação de sistemas	|		|	setup dos projetos e repositórios Git (Bitbucket)	|
|	Desenho e operação de sistemas	|		|	Como administrador da infraestrutura quero que o servidor Windows e Linux forneçam endereços IP (na segunda placa de rede) da família 192.168.X.0/24 aos postos clientes, onde X é obtido por 100 + número_do_grupo (exemplo, para o grupo 99, X=199) 	|
|	Desenho e operação de sistemas	|		|	Como administrador da infraestrutura quero que os serviços acima referidos funcionem em failover, com um deles a facultar endereços de 192.168.X.50 a 192.168.X.150 e o outro de 192.168.X.151 a 192.168.X.200	|
|	Desenho e operação de sistemas	|		|	Como administrador da infraestrutura quero os servidores Windows e Linux estejam disponíveis apenas para pedidos HTTP e HTTPS. Tal não deve impedir o acesso por SSH ou RDP aos administradores (o grupo). 	|
|	Desenho e operação de sistemas	|		|	Como administrador da infraestrutura quero impedir o IP spoofing na minha rede 	|
|	Desenho e operação de sistemas	|		|	Como administrador da infraestrutura quero que os utilizadores registados no Linux com UID entre 6000 e 6500 só consigam aceder via SSH se esse acesso for a partir de uma máquina listada em /etc/remote-hosts	|
|	Desenho e operação de sistemas	|		|	Como administrador da infraestrutura quero que o acesso ao sistema seja inibido aos utilizadores listados em /etc/bad-guys	|
|	Desenho e operação de sistemas	|		|	Como administrador da infraestrutura quero que as mensagens pré-login e pós-login bem sucedido sejam dinâmicas (por exemplo, “[Bom dia] \| [Boa tarde] username”, etc.	|
|	Desenho e operação de sistemas	|		|	Como administrador da infraestrutura quero que o servidor Linux responda e envie pedidos ICMP para teste de conectividade apenas e só aos computadores dos elementos do grupo 	|
|	Geral	|		|	Definição da arquitetura ("walking skeleton") de integração da solução	|
|	Geral	|		|	Implementação do "walking skeleton"	|
|	Planeamento	|		|	Setup dos projetos e repositórios Git (Bitbucket)	|
|	Planeamento	|		|	Consumo de dados de rede através da API do master data	|
|	Planeamento	|		|	Implementação de gerador de todas as soluções para mudança de motorista/tripulação entre estações de rendição e escolha da solução que minimiza tempo total de mudança	|
|	Planeamento	|		|	Estudo de viabilidade e complexidade do gerador de todas as soluções	|
|	Planeamento	|		|	Estudo de heurísticas adequadas à mudança de motorista/tripulação entre estações de rendição 	|
|	Planeamento	|		|	Implementação do algoritmo A* para mudança de motorista/tripulação entre estações de rendição  	|
|	Planeamento	|		|	Comparação do A* com o gerador de todas as soluções	|
|	Planeamento	|		|	Implantação na VM do DEI ou Cloud	|
|	RGPD	|		|	Relatório onde se identifiquem i) o responsável pelo tratamento dos dados ii) os dados pessoais a recolher no momento do registo de utilizador, iii) a(s) finalidade(s) desse tratamento	|
|	SPA	|	Geral	|	Esqueleto da SPA com estrutura de menus para os vários módulos	|
|	SPA	|	Geral	|	Design arquitetural: |
||| o Nível 1: vista lógica e de cenários (revisão) |
||| o Nível 2: vista lógica, de processo, de implementação e física (revisão)	|
||| o Nível 3 (UI/SPA): vista lógica, de processo e de implementação |
||| o Adoção de estilos/padrões: cliente-servidor, SOA, DTO, ... |
|	SPA	|	Geral	|	Tecnologia: Angular ou React	|
|	SPA	|	Geral	|	Testes unitários, de integração (com isolamento via mocks) e End2End	|
|	SPA	|	Geral	|	Implantação na VM do DEI	|
|	SPA	|	Geral	|	Pipelines (Bitbucket Pipelines)	|
|	SPA	|	Planeamento	|	Como gestor pretendo visualizar as soluções do algoritmo de mudança de motorista	|
|	SPA	|	rede	|	Como data administrator, quero carregar dados dum ficheiro .glx.	|
|	SPA	|	rede	|	Como data administrator, quero criar nós da rede.	|
|	SPA	|	rede	|	Como data administrator, quero criar percurso(s) de uma linha.	|
|	SPA	|	rede	|	Como data administrator, quero criar linha.	|
|	SPA	|	rede	|	Como data administrator, quero definir um percurso de ida/volta/reforço de linha.	|
|	SPA	|	rede	|	Como data administrator, quero criar tipo de tripulante.	|
|	SPA	|	rede	|	Como data administrator, quero criar tipo de viatura.	|
|	SPA	|	Visualização	|	Setup dos projetos e repositórios Git (Bitbucket) - se necessário	|
|	SPA	|	Visualização	|	Representar graficamente em 2D a rede de transportes.	|
|	SPA	|	Visualização	|	Consumo de dados de rede através da API do master data	|
|	SPA	|	Visualização	|	Adicionar os comandos da câmara pan e zoom; o comando orbit deverá estar inactivo (sugestão: botão direito do rato – pan; roda do rato – zoom).	|
|	SPA	|	Visualização	|	Subpor a área geográfica (mapa) abrangida pela rede de transportes 	|
|	SPA	|		|	setup dos projetos e repositórios Git (Bitbucket)	|
