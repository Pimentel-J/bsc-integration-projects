# Sprint D - Requisitos Funcionais e Não Funcionais:

|módulo	|submódulo	|descrição breve/user story
|---------|---------|---------|
|	MDV	|		|	Como data administrator quero criar um serviço de tripulante ad hoc	|
|	MDV	|		|	Como data administrator quero importar viagens, serviços de viatura e serviços de tripulante	|
|	SPA	|	viagens	|	Como data administrator quero criar um serviço de tripulante ad hoc	|
|	SPA	|	viagens	|	Como data administrator quero importar viagens, serviços de viatura e serviços de tripulante	|
|	SPA	|	RGPD	|	Implementar a opção que permita o exercício do direito ao esquecimento (RGPD)	|
|	SPA	|	RGPD	|	Completar registo do 1º sprint incluindo o tratamento de dados pessoais de trabalhadores (motoristas), apresentando-o juntamente com um texto que revele conformidade com o RGPD	|
|	SPA	|	Visualização	|	Em ambos os modos de representação (2D e 3D), apresentar tooltips contendo informação acerca das características dos objectos (nós, linhas, percursos, etc.) apontados pelo cursor gráfico; remover os tooltips quando o cursor é afastado dos referidos objectos.	|
|	SPA	|	Visualização	|	Consumo de dados de viagens através da API do master data "viagens"	|
|	SPA	|	Visualização	|	Consumo de dados de serviços de tripulante através da API do planeamento	|
|	SPA	|	Visualização	|	No modo 3D, implementar a navegação na primeira pessoa (sugestão: tecla ‘A’ – rodar para a esquerda; tecla ‘D’ – rodar para a direita; tecla ‘W’ – avançar; tecla ‘S’ – recuar).	|
|	SPA	|	Visualização	|	No modo 3D, adicionar iluminação e projecção de sombras.	|
|	SPA	|	Visualização	|	Adicionar objectos de interacção (por exemplo, botões, sliders, etc.) que permitam configurar as condições de iluminação da cena; estes objectos deverão estar activos no modo 3D e inactivos ou invisíveis no modo 2D.	|
|	SPA	|	Visualização	|	Como cliente ou gestor pretendo aumentar o realismo da navegação na primeira pessoa. Detetar as colisões da viatura com os modelos 3D representativos dos nós da rede.	|
|	SPA	|		|	Como data administrator, quero listar nós de rede.	|
|	SPA	|		|	Como data administrator, quero listar linhas.	|
|	SPA	|		|	Como data administrator, quero listar percursos duma linha.	|
|	SPA	|		|	Como data administrator, quero listar serviço de viatura num determinado dia.	|
|	SPA	|		|	Como data administrator, quero listar serviço de tripulante num determinado dia.	|
|	Planeamento	|		|	Afetação de Motoristas aos autocarros (Vehicleduties) para 1 dia tendo em conta as políticas de gestão dos motoristas, nomeadamente o Balanceamento (Equilíbrio) do trabalho entre motoristas e quais motoristas deverão prestar serviço diário em mais do que 1 autocarro. Para cada vehicleduty deverá ser chamado o sistema de escalonamento de motoristas desenvolvido no Sprint C. Para motoristas prestando serviço em mais do que um vehicleduty deverá ser garantido o tempo para mudança de autocarro entre pontos de rendição/recolha desenvolvido no Sprint B.  	|
|	Planeamento	|		|	Propostas de estratégias para lidar com a violação de hard constraints e correção do escalonamento de motoristas nos vehicleduties de modo a possibilitar ultrapassar as hard constraints violadas.   	|
|	Planeamento	|		|	Estado da Arte do uso de metodologias/tecnologias aplicadas ao problema de transporte público terrestre dentro de um dos seguintes temas (Visão por Computador; Veículos Autónomos; Língua Natural; Machine Learning). **Tema: Visão por Computador - Segurança em grandes estações**. 	|
|	Desenho e operação de sistemas	|		|	Como responsável pela infraestrutura quero que seja criado um DRP para os sistemas considerados críticos da infraestrutura que identifique e quantifique  os riscos e os procedimentos para assegurar a continuidade de negócio	|
