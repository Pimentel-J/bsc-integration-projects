**US66 - Listar Linhas**
=======================================

# 1. Requisitos

Listar as linhas existente

## 1.1 Requisitos adicionais do cliente

--


# 2. Análise

## 2.1. Glossário de conceitos

* **Data Administrator**: utilizador do caso de uso. 
* **Linha**: Uma linha corresponde a um conjunto de percursos. Uma linha terá, pelo menos, dois percursos em sentidos opostos. Por vezes poderá ter percursos alternativos que permitem reforçar a oferta em algumas das áreas cobertas pela linha, ex., a linha C do Metro do Porto tem o percurso Campanhã-ISMAI mas também o percurso Campanhã-Fórum Maia.. 

## 2.2. Modelo de domínio


## 2.3. Regras de negócio

As linhas são obtidas através do servico linhas em que é feita uma chamada à API Master Data Rede, de modo obter todos as linhas existentes. Endpoint usado: `/api/linha`.

# 3. Design

## 3.1. Realização da Funcionalidade

## 3.2. API call

### Listar Linhas

* Endpoint:	`/api/linha`
* Method: *GET*
* Body:
	* Media type: *application/json*
	* Type: *object*
	* Properties:
		* **codigo**: string (required)
		* **nome**: string (required)
		* **permissaoViatura**: string (required)
		* **permissaoMotorista**: string (required)
		* **noFinal**: string (required)
		* **cor**: string (required)
}
## 3.3. Padrões Aplicados


## 3.4. Testes


# 4. Implementação


# 5. Integração/Demonstração



# 6. Observações
