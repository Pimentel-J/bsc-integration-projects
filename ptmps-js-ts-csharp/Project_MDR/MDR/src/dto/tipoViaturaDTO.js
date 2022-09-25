function TipoViaturaDTO(codigo,nome,tipoCombustivel,autonomia,
    consumoMedio,custoKm,velocidadeMedia,emissoesCO2) {
        this.codigo = codigo;
        this.nome = nome;
        this.autonomia = autonomia;
        this.consumoMedio = consumoMedio;
        this.custoKm = custoKm;
        this.tipoCombustivel = tipoCombustivel;
        this.velocidadeMedia = velocidadeMedia;
        this.emissoesCO2 = emissoesCO2;
    }

    module.exports = TipoViaturaDTO;