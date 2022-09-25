
class Segmento {
    constructor(ordem, noOrigem, noDestino, duracao, distancia) {
        this.ordem = ordem;
        this.noOrigem = noOrigem;
        this.noDestino = noDestino;
        this.duracao = duracao;
        this.distancia = distancia;
    }
    validarSegmento(outro) {
        if (this.ordem == (outro.ordem - 1)){
            if (this.noDestino != outro.noOrigem.abreviatura) {
                return false;
            }
        } 
        if (this.ordem == (outro.ordem + 1)) {
            if (this.noOrigem != outro.noDestino.abreviatura) {
                return false;
            }
        }
        return true;
    }
}

module.exports = Segmento;