# **GLOSSÁRIO**

| **_Termo_** | **_Descrição_** |
|:------------------------|:----------------------------------------------------------------|
| **Agendamento** | Agendamento do processamento de **Mensagens**. |
| **Catálogo de Produtos** | Conjunto de vários **Produtos**. |
| **Categoria Material** | Classificação do **Material**. |
| **Categoria de Produto** | Categorização de um determinado **Produto**, definida por um código alfanumérico e por uma descrição. |
| **Código Depósito** | Código único alfanumérico de identificação do **Depósito**. |
| **Código Comercial Produto** | Código único de identificação do **Produto**. |
| **Código Interno Material** | Código único interno de identificação do **Material**. |
| **Código Único de Fabrico** | Código único de fabrico do **Produto**. |
| **Depósito** | Local onde são armazenados os **Produtos** e as **Matérias-primas**. |
| **Data de Emissão Ordem Produção** | Data em que é emitida uma **Ordem de produção**. |
| **Data Prevista de Execução Ordem Produção** | Data prevista para o início da execução da **Ordem de produção**. |
| **Descrição Breve Produto** | Descrição resumida do **Produto**. |
| **Descrição Completa Produto** | Descrição extensiva do **Produto**. |
| **Descrição Depósito** | Descrição do **Depósito**. |
| **Descrição Material** | Descrição do **Material**. |
| **Estado Agendamento** | Estado em que se encontra o agendamento do processamento de **Mensagens** (ativo ou inativo). |
| **Estado Mensagem** | Estado atual da **Mensagem** (processada ou não processada). |
| **Estado de Ordem de Produção** | Estado atual da **Ordem de produção** (pendente, em execução, execução parada temporariamente ou concluída). |
| **Estrutura Mensagem** | Estrutura da **Mensagem** gerada pela **Máquina**. |
| **Ficha de Produção** | Lista de **Matérias-primas** e respetivas quantidades usadas para produzir uma quantidade standard (e.g. 1 tonelada; 100 unidades) de um dado **Produto**. |
| **Ficheiro de Configuração da Máquina** | Ficheiro de configuração de uma determinada **Máquina**. |
| **Ficha Técnica Material** | Ficha técnica do **Material**. |
| **Formato Mensagem** | Formato da **Mensagem** gerada pela **Máquina**. |
| **Gestor de Produção** | utilizador do caso de uso. É ele o responsável por importar (cadastrar) um catálogo de produtos no sistema. |
| **ID Encomenda** | Código único de identificação da encomenda. |
| **ID Máquina** | Código único de identificação da **Máquina**. |
| **ID Ordem Produção** | Código único de identificação da **Ordem de produção**. |
| **Linha de Produção** | Organização sequencial de um conjunto de **Máquinas**. |
| **Lote** | Corresponde a uma característica atribuída a um conjunto de exemplares de um **Produto**. |
| **Máquina** | É um equipamento produtivo capaz de realizar operações com vista a produzir um **Produto** e capaz de gerar **Mensagens**. |
| **Matéria-Prima** | **Material** e/ou **Produto** usado no processo de fabrico de um ou mais **Produtos**. |
| **Material** | Corresponde a um item usado como **Matéria-Prima** para a produção de um **Produto**. |
| **Mensagem** | Corresponde a um conjunto de dados gerado pela **Máquina** e devidamente estruturado de acordo com um determinado tipo de **Mensagem**. |
| **Ordem de Produção** | Documento em que se autoriza/solicita a produção de um **Produto** numa determinada quantidade (a pretendida) através de um conjunto de **Matérias-primas** e respetivas quantidades (de referência). |
| **Período Agendamento** | Intervalo temporal pretendido para o processamento de **Mensagens**. |
| **Processador de Mensagens** | Subsistema de validação, enriquecimento e processamento de **Mensagens** de forma a atualizar e gerar nova informação no sistema. |
| **Produto** | Corresponde a um item produzido numa **Linha de Produção**. Nalguns casos, um **Produto** pode ser utilizado como **Matéria-Prima** para a produção de outro **Produto**. |
| **Quantidade do Movimento** | Quantidade de **Materiais** ou **Produtos** relacionados com o **Registo de Movimento**. |
| **Quantidade Matéria-Prima** | Quantidade de **Material** e/ou **Produto** usado no processo de fabrico de um ou mais **Produtos**. |
| **Recorrência Agendamento** | Frequência pretendida para um determinado **Agendamento** durante um **Período de Agendamento**. |
| **Registo de Atividade** | Corresponde ao estado da atividade da produção (início, fim, paragem e retoma). |
| **Registo de Movimento** | Movimento de **Material** ou **Produto** associado a uma **Mensagem** do tipo Consumo, Produção, Entrega de Produção ou Estorno. |
| **Sequência Máquina** | Ordem sequencial de uma **Máquina** numa **Linha de Produção**. |
| **Tipo de Mensagem** | Corresponde a um valor que permite determinar/classificar que género de informação consta de uma **Mensagem**. |
| **Timestamp Geração Mensagem** | Data e hora de geração de uma **Mensagem**. |
| **Timestamp Envio Mensagem** | Data e hora de envio de uma **Mensagem**. |
| **Unidade** | Valor que descreve a forma de contagem de cada **Produto**. |

## **Glossário de Tipos de Mensagens**

-------------------------

| **_Termo_** | **_Descrição_** |
|:------------------------|:----------------------------------------------------------------|
| **Inicio de Atividade** | Indica que a máquina começou a operar num novo contexto. Neste caso o contexto, é sempre uma ordem de produção. A informação referente ao contexto nem sempre consta na mensagem enviada pela máquina em causa. |
| **Fim de Atividade** | Indica que a máquina concluiu a sua operação no atual contexto. Opcionalmente, o contexto atual consta na mensagem enviada pela máquina em causa. |
| **Paragem Forçada** | Indica que a máquina interrompeu temporariamente a sua operação. Neste tipo de mensagens a máquina reporta sob a forma de um código a causa de paragem (e.g. interrupção solicitado pelo controlador; avaria; matéria-prima insuficiente; etc...). |
| **Retoma de Atividade** | Indica que após uma paragem forçada a máquina retomou a sua operação. |
| **Consumo** | Indica que deu entrada na máquina uma determinada quantidade de matéria-prima cuja origem é um depósito ou a máquina imediatamente anterior a si na linha de produção. A matéria-prima e a respetiva quantidade constam sempre na mensagem. Quando a origem é um depósito, a identificação do mesmo também consta na mensagem. Caso contrário, nenhuma máquina é identificada. |
| **Produção** | Indica que a máquina produziu (em resultado da transformação por si aplicada) uma determinada quantidade de produto. O produto e a respetiva quantidade constam sempre na mensagem. Opcionalmente, pode constar informação sobre o lote. |
| **Entrega de Produção** | Indica que a máquina entregou num depósito uma determinada quantidade de produto. O produto e a respetiva quantidade bem como a identificação do depósito constam sempre na mensagem. Opcionalmente, pode constar informação sobre o lote. |
| **Estorno** | Indica que deu saída da máquina uma determinada quantidade de matéria-prima cujo destino é um depósito. A matéria-prima e a respetiva quantidade bem como a identificação do depósito constam sempre na mensagem. Este tipo de mensagem distingue-se de "Entrega de Produção" por ser gerada em resultado de um consumo prévio e excessivo de matéria-prima ou em resultado de "desperdícios" gerados aquando da produção do produto pretendido e que são posteriormente reutilizáveis. |
