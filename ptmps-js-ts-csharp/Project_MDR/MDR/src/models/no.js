
function No(abreviatura, nome, latitude, longitude, estacaoRecolha, pontoRendicao, modeloMapa) {
    this.abreviatura = abreviatura;
    this.nome = nome;
    this.latitude = latitude;
    this.longitude = longitude;
    this.estacaoRecolha = estacaoRecolha;
    this.pontoRendicao = pontoRendicao;
    this.modeloMapa = modeloMapa;
}

module.exports = No;