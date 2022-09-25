**US67 - Listar as linhas de um percurso**
=======================================

# 1. Requisitos

Listar os percursos de uma dada linha

## 1.1 Requisitos adicionais do cliente

--


# 2. Análise

## 2.1. Glossário de conceitos

* **Data Administrator**: utilizador do caso de uso. 
* **Linha**: Uma linha corresponde a um conjunto de percursos. Uma linha terá, pelo menos, dois percursos em sentidos opostos. Por vezes poderá ter percursos alternativos que permitem reforçar a oferta em algumas das áreas cobertas pela linha, ex., a linha C do Metro do Porto tem o percurso Campanhã-ISMAI mas também o percurso Campanhã-Fórum Maia.
* **Percurso**: Um percurso é um trajeto correspondendo a uma sequência de nós, ex., Paredes -> Cete -> Parada -> Recarei -> Aguiar.

## 2.2. Modelo de domínio


## 2.3. Regras de negócio

A **linha** é selecionada de uma lista definida e obtida através do servico linha em que é feita uma chamada à API Master Data Rede, de modo obter todos as linhas existentes. Endpoint usado: `/api/linha`.

Os percursos são obtidos através do servico nós em que é feita uma chamada à API Master Data Rede, de modo obter todos os nós existentes. Endpoint usado: `/api/percurso/linha`.

# 3. Design

## 3.1. Realização da Funcionalidade

## 3.2. API call

### Listar Percursos de uma linha

* Endpoint:	`/api/percurso/linha`
* Method: *GET*
* Body:
	* Media type: *application/json*
	* Type: *object*
	* Properties:
		* **idLinha**: Linha (required)
		* **idPercurso**: string (required)
		* **ida**: string (required)
		* **segmentos**: Segmento[] (required);
}
## 3.3. Padrões Aplicados


## 3.4. Testes


# 4. Implementação


# 5. Integração/Demonstração



# 6. Observações
