**US52 - Criar uma viatura**
=======================================

# 1. Requisitos

Uma viatura é caracterizada pela matricula, VIN, pelo seu tipo e data de entrada ao serviço. 

## 1.1 Requisitos adicionais do cliente

--


# 2. Análise

## 2.1. Glossário de conceitos

* **Data Administrator**: utilizador do caso de uso. É ele o responsável por definir um percurso no sistema.
* **Viatura**: equipamento fisíco conduzida por um tripulado credenciado e que transporta utentes / clientes entre diferentes nós (paragens,  estações de recolha ou términos) que auxiliam a definir um percurso.
* **Tipo de Viatura**: Carateriza um tipo de viatura (i.e., Diesel, Gasolina, Elétrico, GPL, Gás), que integra uma determinada linha.

## 2.2. Modelo de domínio


## 2.3. Regras de negócio

A **matricula** identifica a viatura. 

O **vin** (número de chasis cumpre as regras apresentadas no seguinte endereço https://en.m.wikipedia.org/wiki/Vehicle_identification_number.

O **tipo de viatura** é selecionada de uma lista definida e obtida através do servico tipos de viatura em que é feita uma chamada à API Master Data Rede, de modo obter todos os tipos de viatura existentes. Endpoint usado: `/api/tiposviatura`.

# 3. Design

## 3.1. Realização da Funcionalidade

## 3.2. API call

### Criar Viatura

* Endpoint:	`/api/viaturas`
* Method: *POST*
* Body:
	* Media type: *application/json*
	* Type: *object*
	* Properties:
		* **id**: string (required)
		* **niv**: string (required)
		* **tipoviatura**: string (required)
		* **data_entrada_servico**: string (required)
	

## 3.3. Padrões Aplicados


## 3.4. Testes


# 4. Implementação


# 5. Integração/Demonstração



# 6. Observações
