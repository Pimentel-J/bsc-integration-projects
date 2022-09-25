/**
 * Tipo de tripulante
 * @param {string} codigo 
 * @param {string} descricao 
 */
function TipoTripulante(codigo, descricao) {
    this.codigo = codigo,
    this.descricao = descricao
    return Object.freeze(this); // Immutability
};

module.exports = TipoTripulante;