**US65 - Listar Nos**
=======================================

# 1. Requisitos

Listar os nós existente

## 1.1 Requisitos adicionais do cliente

--


# 2. Análise

## 2.1. Glossário de conceitos

* **Data Administrator**: utilizador do caso de uso. 
* **Nó**: Um nó é um ponto da rede de transportes com importância para o processo de planeamento. Exemplos de nós são estações de recolha, términos, e paragens ao público. 

## 2.2. Modelo de domínio


## 2.3. Regras de negócio

Os nós são obtidos através do servico nós em que é feita uma chamada à API Master Data Rede, de modo obter todos os nós existentes. Endpoint usado: `/api/no`.

# 3. Design

## 3.1. Realização da Funcionalidade

## 3.2. API call

### Listar Nos

* Endpoint:	`/api/no`
* Method: *GET*
* Body:
	* Media type: *application/json*
	* Type: *object*
	* Properties:
		* **abreviatura**: string (required)
		* **nome**: string (required)
		* **latitude**: number (required)
		* **longitude**: number (required)
		* **estacaoRecolha**: boolean (required)
		* **pontoRendicao**: boolean (required)
		* **modeloMapa**: string (required)
		
}
## 3.3. Padrões Aplicados


## 3.4. Testes


# 4. Implementação


# 5. Integração/Demonstração



# 6. Observações
