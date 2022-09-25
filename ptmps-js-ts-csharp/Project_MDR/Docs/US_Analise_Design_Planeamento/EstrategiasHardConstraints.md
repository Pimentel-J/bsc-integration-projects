**Propostas de estratégias: violação de hard constraints e correção do escalonamento**
=======================================

# 1. Requisito

Propostas de estratégias para lidar com a violação de hard constraints e correção do escalonamento de motoristas nos vehicleduties de modo a possibilitar ultrapassar as hard constraints violadas.

# 2. Análise

* **Enquadramento**:

Este requisito está diretamente relacionado com a afetação de todos os motoristas a todos autocarros. Na medida em que, perante o resultado do escalonamento de motoristas nos vehicle duties poderá ser necessário fazer correções. Correções essas para resolver a violação de hard constraints.

* **Hard constraints**:

    * Não ultrapassar o período normal de trabalho de 8 horas diárias;
    * Obrigatoriedade de descanso mínimo de 1 hora depois de 4 horas de serviço prestado continuamente;
    * Ter 1 hora livre para almoçar ou jantar num determinado intervalo de tempo.

> Fonte: _TP12 de ALGAV_

## 2.1.1. Caracterização do Serviço de Tripulante:

* **Código (alfanumérico)**
    * Relação com um conjunto de blocos de trabalho

[Moodle Fórum](https://moodle.isep.ipp.pt/mod/forum/discuss.php?d=2727#p3633)

## 2.2. Regras de negócio / Outros requisitos

|  |  |
|---------|---------|
| **Código** | Alfanumérico de 10 caracteres |
| **Bloco de Trabalho** | O serviço de tripulante está relacionado com um conjunto de blocos de trabalho |
| | |

**Notas Módulo Planeamento**:

* Também tem uma duração máxima de 8 horas (parametrizável no sistema) e uma pausa obrigatória depois de 4 horas ininterruptas de serviço.

* A duração do serviço de tripulante corresponde à duração dos blocos associados (que devem ser contínuos em cada período).

> [Moodle Fórum](https://moodle.isep.ipp.pt/mod/forum/discuss.php?d=2727#p3633)

# 3. Design

Implementação de acordo com a estrutura base do módulo Planeamento.

## 3.1. Realização da Funcionalidade

O fluxo que permite realizar esta funcionalidade pode ser descrito através do diagrama seguinte:

![Criar tipo de tripulante](./US61_SD.png)
[Raw file](./US61_SD.md)

### 3.2. Representação do conhecimento do domínio

TODO

|**Entity** |**Value Object**  |
|---------|---------|
| ServicoTripulante | CodigoServicoTripulante |
| | |

## 3.3. Consult's necessários

TODO

## 3.4. Planeamento de testes

Testar com um input que viola uma hard constraint.

Voltar a testar com um input que viola.

# 4. Integração/Demonstração

Realizar testes de integração em conformidade com o ficheiro principal, `app.pl`.

# 5. Observações

TODO

### [**Voltar para o Índice de User Stories**](../us.md)
