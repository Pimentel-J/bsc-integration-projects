**US53 - Definir um tripulante**
=======================================

# 1. Requisitos

Um tripulante é caracterizado pelo seu numero mecanográfico, o seu nome, data de nascimento, cartão de cidadão, NIF, carta de condução e data de emissão e validade da mesma, a categoria de tripulante, e a data de entrada e saida da empresa.

## 1.1 Requisitos adicionais do cliente

--


# 2. Análise

## 2.1. Glossário de conceitos

* **Data Administrator**: utilizador do caso de uso. É ele o responsável por definir um tripulante.
* **Tripulante**: entidade responsavél pela condução dos veiculos.
* **TipoTripulante**: categoria do tripulante


## 2.2. Modelo de domínio


## 2.3. Regras de negócio

O **numero mecanografico** identifica a viatura e tem de ter 9 caracteres alfanumerico. 

O **tipo de tripulante** é selecionado de uma lista definida e obtida através do servico tipos de tripulantes em que é feita uma chamada à API Master Data Rede, de modo obter todos os tipos de tripulantes existentes. Endpoint usado: `/api/tipostripulante`.

# 3. Design

## 3.1. Realização da Funcionalidade

## 3.2. API call

### Definir Tripulante

* Endpoint:	`/api/tripulante`
* Method: *POST*
* Body:
	* Media type: *application/json*
	* Type: *object*
	* Properties:
		* **numeroMecanografico**: string (required)
		* **nome**: string (required)
		* **dataNascimento**: string (required)
		* **numeroCartaoCidadao**: string (required)
        * **nif**: string (required)
		* **numeroCartaConducao**: string (required)
		* **dataEmissaoLicencaConducao**: string (required)
		* **dataValidadeLicencaConducao**: string (required)
	    * **tipoTripulante**: string (required)
		* **dataEntradaEmpresa**: string (required)
		* **dataSaidaEmpresa**: string (required)
		
## 3.3. Padrões Aplicados


## 3.4. Testes


# 4. Implementação


# 5. Integração/Demonstração



# 6. Observações
