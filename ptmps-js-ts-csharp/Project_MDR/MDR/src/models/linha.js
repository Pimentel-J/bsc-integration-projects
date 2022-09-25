
function Linha(codigo,nome,permissoesTipoViatura,permissoesTipoMotorista,cor) {
    this.codigo = codigo;
    this.nome = nome;
    this.permissoesTipoViatura = permissoesTipoViatura;
    this.permissoesTipoMotorista = permissoesTipoMotorista;
    this.cor = cor;
}

module.exports = Linha;