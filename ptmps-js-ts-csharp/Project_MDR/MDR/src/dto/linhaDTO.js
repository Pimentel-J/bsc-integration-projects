
function LinhaDTO(codigo, nome, permissoesTipoMotorista, permissoesTipoViatura, cor) {
    this.codigo = codigo;
    this.nome = nome;
    this.permissoesTipoViatura = permissoesTipoViatura;
    this.permissoesTipoMotorista = permissoesTipoMotorista;
    this.cor = cor;
}

module.exports = LinhaDTO;