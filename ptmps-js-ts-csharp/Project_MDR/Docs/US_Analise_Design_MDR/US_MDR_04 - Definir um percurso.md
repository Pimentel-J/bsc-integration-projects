**US_MDR_04 - Definir um percurso de ida/volta de uma linha**
=======================================

# 1. Requisitos

**US_MDR_04** Como data administrator, quero definir um percurso de ida/volta de uma linha. Definir os vários segmentos que constituem um percurso indicando a ordem e a distância e tempo de viagem de cada segmento.

## 1.1 Requisitos adicionais do cliente

* O utilizador deve criar um percurso indicando se se trata de ida ou volta bem como adicionar os segmentos desse percurso.
* Um segmento é criado "na hora" quando o utilizador indica o nó origem e destino do segmento.
* A validação a implementar é que o nó origem de um segmento seja o nó de destino do segmento anterior.
	- ex: numa linha com um percurso A -> B -> C -> D existirão 3 segmentos (A-B, B-C e C-D).

# 2. Análise

## 2.1. Glossário de conceitos

* **Data Administrator**: utilizador do caso de uso. É ele o responsável por definir um percurso no sistema.
* **Percurso**: trajeto correspondendo a uma sequência de nós.
* **Linha**: conjunto de percursos. Uma linha terá, pelo menos, dois percursos em sentidos opostos. Por vezes poderá ter percursos alternativos que permitem reforçar a oferta em algumas das áreas cobertas pela linha.
* **Nó**: ponto da rede de transportes com importância para o processo de planeamento. Exemplos de nós são estações de recolha, términos, e paragens ao público.
* **Segmento**: ligação direta entre dois nós, ex, Paredes-Cete.

## 2.2. Modelo de domínio

Classes correspondentes a entidades do domínio:
* **Percurso**(aggregate root - Percurso)
* **Segmento**
* **Linha** (aggregate root - Linha)
* **Nó** (aggregate root - Nó)

## 2.3. Regras de negócio

* A **Duração** e **Distancia** deverão ser obrigatoriamente valores numéricos positivos
* O nó origem de um **Segmento** seja o nó de destino do **Segmento** anterior, considerando a **Ordem**

# 3. Design

## 3.1. Realização da Funcionalidade


## 3.3. Padrões Aplicados

Os padrões utilizados podem ser enquadrados nos seguintes:
* GRASP: Controller, Creator, Information Expert, High Cohesion, Low Coupling
* SOLID: Single-responsibility principle

## 3.4. Testes

* Testes unitários recorrendo ao Jest:
 * Percurso (model):
 	* Criação de novo modelo Percurso;
	* Validação de cada atributo criado;
	* Validação de objetos iguais com atributos iguais;
	* Validação de instância do objeto criado;
	* Validação de tipo de campos correto


# 4. Implementação

* As validações das regras de negócio foram feitas recorrendo a validações do Mongoose (noMongoDB). No entanto, faria mais sentido fazê-las no No (model), uma vez que devem ser da camada de domínio. Para tal, pode recorrer-se ao módulo Joi do JS.

# 5. Integração/Demonstração



# 6. Observações
