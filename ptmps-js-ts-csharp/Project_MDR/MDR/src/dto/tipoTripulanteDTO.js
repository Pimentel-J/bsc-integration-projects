/**
 * DTO tipo de tripulante
 * @param {string} codigo 
 * @param {string} descricao 
 */
function TipoTripulanteDTO(codigo, descricao) {
    this.codigo = codigo;
    this.descricao = descricao;
};

module.exports = TipoTripulanteDTO;