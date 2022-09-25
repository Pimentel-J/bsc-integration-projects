**US_MDR_08 - Listar Linhas**
=======================================

# 1. Requisitos

**US_MDR_08** Como data administrator, quero listar linhas. O utilizador deve poder ordenar por código/nome e filtrar os resultados por código/nome (ex., todos as linhas cujo nome começa por “Par”).


## 1.1 Requisitos adicionais do cliente


# 2. Análise

O pedido HTTP é realizado pela seguinte estrutura:
./lstlinhas?nome=M&sort=asc ou ./lstlinhas?codigo=11&sort=desc

* ##? identifica a query, o ##nome campo da BD; ##M - valor a pesquisar



## 2.1. Glossário de conceitos

* **Data Administrator**: É o utilizador do caso de uso; É responsavel por criar o tipo de viatura.
* **Linha**: Uma linha corresponde a um conjunto de percursos Uma linha terá, pelo menos, dois percursos em sentidos opostos. Por vezes poderá ter percursos alternativos que permitem reforçar a oferta em algumas das áreas cobertas pela linha


## 2.2. Modelo de domínio

Classes correspondentes a entidades do domínio:
* **Linha**
	* Value objects relacionados:
		* Código
		* Nome
		* PermissoesMotoristas(Lista)
		* PermissoesViaturas(Lista)
		* NoFInal



## 2.3. Regras de negócio
A listagem refere 2 campos a filtrar (nome e codigo) e o tipo de ordenação (ascendente ou descendente) 

# 3. Design

## 3.1. Realização da Funcionalidade

## 3.2. Diagrama de Classes

## 3.3. Padrões Aplicados

Os padrões utilizados podem ser enquadrados nos seguintes:
* GRASP: Controller, Creator, Information Expert, High Cohesion, Low Coupling
* SOLID: Single-responsibility principle, Dependency Inversio

## 3.4. Testes


### 3.4.1 Planeamento


# 4. Implementação


# 5. Integração/Demonstração



# 6. Observações


