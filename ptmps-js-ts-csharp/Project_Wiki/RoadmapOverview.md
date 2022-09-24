# Roadmap e Visão Geral da Documentação

## Índice
- [Roadmap e Visão Geral da Documentação](#markdown-header-roadmap-e-visao-geral-da-documentacao)
	- [Objetivo e Âmbito do SAD](#markdown-header-objetivo-e-ambito-do-sad)
	- [Como está o SAD Organizado](#markdown-header-como-esta-o-sad-organizado)
	- [Como é Documentada uma Vista](#markdown-header-como-e-documentada-uma-vista)

# Roadmap e Visão Geral da Documentação
*(Documentation Roadmap and Overview)*

O documento Roadmap e a Visão geral fornece informações sobre este documento e a quem se dirige.

## Objetivo e Âmbito do SAD
*(Purpose and Scope of the SAD)*

A arquitetura de software dum sistema é a estrutura ou estruturas desse sistema, que inclui elementos do software e suas propriedades visíveis externamente, e as relações entre ele (Bass 2012).

Este SAD descreve a arquitetura de software do sistema a desenvolver por solicitação da Autoridade Intermunicipal de Transportes (AIT) para gestão e planeamento de transportes públicos que permite a gestão bem como consulta pelo público em geral de diferentes redes de transportes, linhas e viagens, bem como o planeamento dos serviços de viaturas e tripulantes a efetuar nessas linhas.

Este SAD é desenvolvido num contexto académico de ensino-aprendizagem (no 5º semestre da LEI no ano letivo 2020-2021), em que várias competências estão a ser adquiridas ao longo do semestre pelos/as estudantes, ao mesmo tempo que desenvolvem o sistema.

Porque visa suportar o processo de ensino-aprendizagem, não tem como objetivo ser completo ou descrever a melhor arquitetura possível, mas servir de guia e exemplo, em linha com as competências a adquirir em cada iteração/sprint do projeto.

Embora os/as estudantes sejam destinatários principais do SAD, as competências a adquirir pelos/as estudantes nas várias UC do semestre permitem-lhe desempenhar diferentes papéis (diferentes partes interessadas/destinatários), e.g. licitadores, analistas, arquitetos de software, programadores/"testers", administradores e operadores (ops) e utilizadores.

## Como está o SAD Organizado
*(How the SAD Is Organized)*

Este SAD está organizado pelas seguintes sete secções:
> 1. Roadmap e a Visão geral fornecem informações sobre este documento e a quem se dirige. 
> 2. Architecture Background fornece informações sobre a arquitetura de software. Descreve a base e a lógica da arquitetura de software. Explica as restrições e influências que levaram a adotar a arquitetura definida. 
> 3. Vistas
> 4. Mapeamento entre Vistas; ambas especificam a arquitetura de software.
> 5. Materiais referenciados, fornece informações de consulta para documentos que são citados em outras partes deste SAD.
> 6. Glossary e Acronyms é um índice de elementos e relações que fazem parte deste documento SAD.

Este DAS/SAD adota a estrutura proposta acima.

## Como é Documentada uma Vista
*(How a View Is Documented)*

Neste DAS/SAD será adotado a notação UML, nomeadamente (mas não exclusivamente) diagramas de componentes, de sequência, de pacotes e de nós. Tal, garante 1.1 (geralmente é apresentado sob a forma gráfica), 1.2 (possui uma notação compreendida por todos) e 1.3 (mostra elementos e relações entre eles).

A organização das vistas pela combinação do modelo C4 (diferentes níveis de abstração/granularidade) e modelo 4+1 vistas (vários pontos de vista da arquitetura) permite desde logo endereçar o requisito 1.4 (mostra informações imediatas sob um ponto de vista visual).

Pela adoção do modelo C4, o requisito 1.5 é cumprido (identifica elementos e entidades externas ao âmbito da vista que o diagrama pretende representar).

