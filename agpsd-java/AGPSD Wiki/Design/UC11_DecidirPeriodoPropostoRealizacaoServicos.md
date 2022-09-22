Realização de UC11 - Decidir sobre Período Proposto para Realização de Serviços
==========================================

Racional
--------

| Fluxo Principal                                                               | Questão: Que Classe...                             | Resposta                         | Justificação                                                                                            |
|-------------------------------------------------------------------------------|----------------------------------------------------|----------------------------------|---------------------------------------------------------------------------------------------------------|
| 1. O sistema informa o cliente por e-mail que os seus pedidos foram afetados e pede acesso ao sistema para confirmar sugestões de horários.                     | ... inicia o envio do mail?                     | Empresa         | Pois é a Empresa quem detêm a lista dos pedidos afetados e os registos dos clientes. |
| 2. O cliente inicia a decisão sobre os horários afetados                     | ... interage com o utilizador?                     | DecidirPeriodoUI         | PureFabrication, pois não se justifica atribuir esta responsabilidade a nenhuma classe existente no MD. |
|                                                                               | ...coordena o UC?                                  | DecidirPeriodoController | Controller             
|                                                                               | ...cria/instancia OrdemExecucao?                                  | DescricaoPedidoPrestacaoServico | Creator, no MD a descrição do pedido de prestação possuí a ordem de execução    
|                                                                               | ...conhece a classe DescricaoPrestadorServico                                 | RegistoPedidosDescricaoServicos | HC + LC    
|                                                                               | ...conhece a classe RegistoPedidosDescricaoServicos                                 | Empresa | HC + LC                                                                                     |
| 3. O sistema mostra as sugestões de horários geradas por afetação e solicita ao cliente que tome uma decisão sobre as mesmas           | n/a                                                |                                  |                                                                                                         |
| 4. O Cliente toma decisão sobre as sugestões dos horários.                                   | ... guarda os dados introduzidos?             | OrdemExecucao                          | Information Expert (instância criada no passo 1)                                                                                      |
|                                                                               | ... guarda os dados introduzidos?                  | OrdemExecucao                  | IE - instância criada no passo 1   
|                                                                               | ... guarda a OrdemExecucao?                  | PedidoPrestacaoServicos                  | Creator, no MD a descrição do pedido de prestação possuí a ordem de execução                                                                |
| 5. O sistema atribui ordens de execução aos pedidos cujos horários foram aceites e informa do sucesso da operação           | ...guarda a OrdemExecucao  | PedidoPrestacaoServicos                   | IE                                                        |
|                                                                               | ...valida os dados do Endereço (validação global)? | Cliente                          | IE: Cliente contém/agrega todos os seus endereços postais.                                                           |
| 6. O Sistema informa do sucesso da operação                                                        |                                                    |                                  |                                                                                                         |
| 7. Os passos 4 a 5 repetem-se até que as sugestões sejam todas confirmadas |                        |                  |                                     |




Sistematização
--------------

Do racional resulta que as classes conceptuais promovidas a classes de software
são:

- Empresa

- Cliente

- PedidoPrestacaoServicos

- DescricaoPedidoServico

- OrdemExecucao

Outras classes de software (i.e. Pure Fabrication) identificadas:

-   DecidirPeriodoUI

-   DecidirPeriodoController

Diagrama de Sequência
---------------------

![SD_UC11_IT3.png](SD_UC11_IT3.png)



Diagrama de Classes
-------------------

![CD_UC11_IT3.png](CD_UC11_IT3.png)
