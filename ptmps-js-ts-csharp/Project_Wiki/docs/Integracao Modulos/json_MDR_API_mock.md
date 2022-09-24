**MDR API JSON mocks**
# Planeamento <- MDR
    GET /nos
```json
[
    {
        "_id": "5fa5c2ee66af715f643a403a",
        "abreviatura": "NO_1",
        "nome": "Paragem 1",
        "latitude": 41.245432,
        "longitude": -8.648099,
        "estacaoRecolha": false,
        "pontoRendicao": false,
        "__v": 0
    },
    {
        "_id": "5fb0f6197d5b566f9ca3892a",
        "abreviatura": "NO_2",
        "nome": "Paragem 2",
        "latitude": 41.24765,
        "longitude": -8.64456,
        "estacaoRecolha": true,
        "pontoRendicao": false,
        "__v": 0
    },
    {
        "_id": "5fb17c97b65ebf5228642e00",
        "abreviatura": "NO_4",
        "nome": "Paragem 4",
        "latitude": 41.24765,
        "longitude": -8.64456,
        "estacaoRecolha": false,
        "pontoRendicao": false,
        "__v": 0
    },
    {
        "_id": "5fb1a3dc0c51884714fd8021",
        "abreviatura": "NO_7",
        "nome": "Paragem 7",
        "latitude": 41.24765,
        "longitude": -8.64456,
        "estacaoRecolha": false,
        "pontoRendicao": false,
        "__v": 0
    },
    {
        "_id": "5fb50cb62a8713002471e2ce",
        "abreviatura": "NO_9",
        "nome": "Paragem 7",
        "latitude": 41.24765,
        "longitude": -8.64456,
        "estacaoRecolha": false,
        "pontoRendicao": true,
        "__v": 0
    },
    {
        "_id": "5fb58370d0b2ea4900a43323",
        "abreviatura": "Gandra",
        "nome": "Gandra Station",
        "latitude": 3.134234234,
        "longitude": -8.234234234,
        "estacaoRecolha": false,
        "pontoRendicao": false,
        "__v": 0
    },
    {
        "_id": "5fb58402d0b2ea4900a43325",
        "abreviatura": "Paredes",
        "nome": "Paredes Station",
        "latitude": 4.134234234,
        "longitude": -8.234234234,
        "estacaoRecolha": false,
        "pontoRendicao": false,
        "__v": 0
    },
    {
        "_id": "5fb925530d6cf433fc388439",
        "abreviatura": "NO_10",
        "nome": "Paragem 7",
        "latitude": 41.24765,
        "longitude": -8.64456,
        "estacaoRecolha": false,
        "pontoRendicao": true,
        "__v": 0
    },
    {
        "_id": "5fbac315a1c27827f4aabbe3",
        "abreviatura": "AGUIA",
        "nome": "Aguiar de Sousa",
        "latitude": 41.1293363229325,
        "longitude": -8.4464785432391,
        "estacaoRecolha": false,
        "pontoRendicao": false,
        "__v": 0
    },
    {
        "_id": "5fbae933cb53054a30ab3670",
        "abreviatura": "NO_11",
        "nome": "Paragem 7",
        "latitude": 41.24765,
        "longitude": -8.64456,
        "estacaoRecolha": false,
        "pontoRendicao": true,
        "__v": 0
    }
]
```
    GET /percursos
```json
[
    {
        "_id": "5fbee592d58f8a2c903b0c05",
        "segmentos": [
            {
                "_id": "5fbee592d58f8a2c903b0bfb",
                "ordem": 1,
                "noOrigem": {
                    "_id": "5fa5c2ee66af715f643a403a",
                    "abreviatura": "NO_1",
                    "nome": "Paragem 1",
                    "latitude": 41.245432,
                    "longitude": -8.648099,
                    "estacaoRecolha": true,
                    "pontoRendicao": false,
                    "__v": 0
                },
                "noDestino": {
                    "_id": "5fb0f6197d5b566f9ca3892a",
                    "abreviatura": "NO_2",
                    "nome": "Paragem 2",
                    "latitude": 41.24765,
                    "longitude": -8.64456,
                    "estacaoRecolha": true,
                    "pontoRendicao": false,
                    "__v": 0
                },
                "duracao": 15,
                "distancia": 20,
                "__v": 0
            },
            {
                "_id": "5fbee592d58f8a2c903b0bfe",
                "ordem": 2,
                "noOrigem": {
                    "_id": "5fb0f6197d5b566f9ca3892a",
                    "abreviatura": "NO_2",
                    "nome": "Paragem 2",
                    "latitude": 41.24765,
                    "longitude": -8.64456,
                    "estacaoRecolha": true,
                    "pontoRendicao": false,
                    "__v": 0
                },
                "noDestino": {
                    "_id": "5fb17c97b65ebf5228642e00",
                    "abreviatura": "NO_4",
                    "nome": "Paragem 4",
                    "latitude": 41.24765,
                    "longitude": -8.64456,
                    "estacaoRecolha": true,
                    "pontoRendicao": false,
                    "__v": 0
                },
                "duracao": 12,
                "distancia": 40,
                "__v": 0
            },
            {
                "_id": "5fbee592d58f8a2c903b0c01",
                "ordem": 3,
                "noOrigem": {
                    "_id": "5fb17c97b65ebf5228642e00",
                    "abreviatura": "NO_4",
                    "nome": "Paragem 4",
                    "latitude": 41.24765,
                    "longitude": -8.64456,
                    "estacaoRecolha": true,
                    "pontoRendicao": false,
                    "__v": 0
                },
                "noDestino": {
                    "_id": "5fb1a3dc0c51884714fd8021",
                    "abreviatura": "NO_7",
                    "nome": "Paragem 7",
                    "latitude": 41.24765,
                    "longitude": -8.64456,
                    "estacaoRecolha": true,
                    "pontoRendicao": false,
                    "__v": 0
                },
                "duracao": 12,
                "distancia": 40,
                "__v": 0
            }
        ],
        "idLinha": {
            "_id": "5fb962be535e20055cd26c34",
            "permissoesTipoViatura": [
                {
                    "_id": "5fb962be535e20055cd26c36",
                    "codigo": "BUS_ELECT",
                    "nome": "Bus Electrico ",
                    "autonomia": 600000,
                    "consumoMedio": 32,
                    "tipoCombustivel": "Eletrico",
                    "velocidadeMedia": 20,
                    "emissoesCO2": 700
                }
            ],
            "permissoesTipoMotorista": [
                {
                    "_id": "5fb962be535e20055cd26c37",
                    "codigo": "PTENG",
                    "descricao": "Motorista senior com conhecimento de lingua inglesa"
                },
                {
                    "_id": "5fb962be535e20055cd26c38",
                    "codigo": "PTESP",
                    "descricao": "Motorista juniot com conhecimento de lingua espanhola"
                }
            ],
            "codigo": "Linha_S01112s1ssss1",
            "nome": "teste",
            "noFinal": {
                "_id": "5fa5c2ee66af715f643a403a",
                "abreviatura": "NO_1",
                "nome": "Paragem 1",
                "latitude": 41.245432,
                "longitude": -8.648099,
                "estacaoRecolha": true,
                "pontoRendicao": false,
                "__v": 0
            },
            "__v": 0
        },
        "idPercurso": "Percurso_1",
        "ida": true,
        "__v": 0
    },
    {
        "_id": "5fbef3e5d58f8a2c903b0c6e",
        "segmentos": [
            {
                "_id": "5fbef3e5d58f8a2c903b0c61",
                "ordem": 1,
                "noOrigem": {
                    "_id": "5fb50cb62a8713002471e2ce",
                    "abreviatura": "NO_9",
                    "nome": "Paragem 7",
                    "latitude": 41.24765,
                    "longitude": -8.64456,
                    "estacaoRecolha": false,
                    "pontoRendicao": true,
                    "__v": 0
                },
                "noDestino": {
                    "_id": "5fb58370d0b2ea4900a43323",
                    "abreviatura": "Gandra",
                    "nome": "Gandra Station",
                    "latitude": 3.134234234,
                    "longitude": -8.234234234,
                    "estacaoRecolha": false,
                    "pontoRendicao": false,
                    "__v": 0
                },
                "duracao": 11,
                "distancia": 22,
                "__v": 0
            },
            {
                "_id": "5fbef3e5d58f8a2c903b0c64",
                "ordem": 2,
                "noOrigem": {
                    "_id": "5fb58370d0b2ea4900a43323",
                    "abreviatura": "Gandra",
                    "nome": "Gandra Station",
                    "latitude": 3.134234234,
                    "longitude": -8.234234234,
                    "estacaoRecolha": false,
                    "pontoRendicao": false,
                    "__v": 0
                },
                "noDestino": {
                    "_id": "5fb0f6197d5b566f9ca3892a",
                    "abreviatura": "NO_2",
                    "nome": "Paragem 2",
                    "latitude": 41.24765,
                    "longitude": -8.64456,
                    "estacaoRecolha": true,
                    "pontoRendicao": false,
                    "__v": 0
                },
                "duracao": 12,
                "distancia": 40,
                "__v": 0
            },
            {
                "_id": "5fbef3e5d58f8a2c903b0c67",
                "ordem": 3,
                "noOrigem": {
                    "_id": "5fb0f6197d5b566f9ca3892a",
                    "abreviatura": "NO_2",
                    "nome": "Paragem 2",
                    "latitude": 41.24765,
                    "longitude": -8.64456,
                    "estacaoRecolha": true,
                    "pontoRendicao": false,
                    "__v": 0
                },
                "noDestino": {
                    "_id": "5fb58402d0b2ea4900a43325",
                    "abreviatura": "Paredes",
                    "nome": "Paredes Station",
                    "latitude": 4.134234234,
                    "longitude": -8.234234234,
                    "estacaoRecolha": false,
                    "pontoRendicao": false,
                    "__v": 0
                },
                "duracao": 17,
                "distancia": 35,
                "__v": 0
            },
            {
                "_id": "5fbef3e5d58f8a2c903b0c6a",
                "ordem": 4,
                "noOrigem": {
                    "_id": "5fb58402d0b2ea4900a43325",
                    "abreviatura": "Paredes",
                    "nome": "Paredes Station",
                    "latitude": 4.134234234,
                    "longitude": -8.234234234,
                    "estacaoRecolha": false,
                    "pontoRendicao": false,
                    "__v": 0
                },
                "noDestino": {
                    "_id": "5fbac315a1c27827f4aabbe3",
                    "abreviatura": "AGUIA",
                    "nome": "Aguiar de Sousa",
                    "latitude": 41.1293363229325,
                    "longitude": -8.4464785432391,
                    "estacaoRecolha": false,
                    "pontoRendicao": false,
                    "__v": 0
                },
                "duracao": 17,
                "distancia": 35,
                "__v": 0
            }
        ],
        "idLinha": {
            "_id": "5fb962be535e20055cd26c34",
            "permissoesTipoViatura": [
                {
                    "_id": "5fb962be535e20055cd26c36",
                    "codigo": "BUS_ELECT",
                    "nome": "Bus Electrico ",
                    "autonomia": 600000,
                    "consumoMedio": 32,
                    "tipoCombustivel": "Eletrico",
                    "velocidadeMedia": 20,
                    "emissoesCO2": 700
                }
            ],
            "permissoesTipoMotorista": [
                {
                    "_id": "5fb962be535e20055cd26c37",
                    "codigo": "PTENG",
                    "descricao": "Motorista senior com conhecimento de lingua inglesa"
                },
                {
                    "_id": "5fb962be535e20055cd26c38",
                    "codigo": "PTESP",
                    "descricao": "Motorista juniot com conhecimento de lingua espanhola"
                }
            ],
            "codigo": "Linha_S01112s1ssss1",
            "nome": "teste",
            "noFinal": {
                "_id": "5fa5c2ee66af715f643a403a",
                "abreviatura": "NO_1",
                "nome": "Paragem 1",
                "latitude": 41.245432,
                "longitude": -8.648099,
                "estacaoRecolha": true,
                "pontoRendicao": false,
                "__v": 0
            },
            "__v": 0
        },
        "idPercurso": "Percurso_2",
        "ida": true,
        "__v": 0
    },
    {
        "_id": "5fbef44bd58f8a2c903b0c8a",
        "segmentos": [
            {
                "_id": "5fbef44bd58f8a2c903b0c80",
                "ordem": 1,
                "noOrigem": {
                    "_id": "5fb1a3dc0c51884714fd8021",
                    "abreviatura": "NO_7",
                    "nome": "Paragem 7",
                    "latitude": 41.24765,
                    "longitude": -8.64456,
                    "estacaoRecolha": false,
                    "pontoRendicao": false,
                    "__v": 0
                },
                "noDestino": {
                    "_id": "5fb50cb62a8713002471e2ce",
                    "abreviatura": "NO_9",
                    "nome": "Paragem 7",
                    "latitude": 41.24765,
                    "longitude": -8.64456,
                    "estacaoRecolha": false,
                    "pontoRendicao": true,
                    "__v": 0
                },
                "duracao": 11,
                "distancia": 22,
                "__v": 0
            },
            {
                "_id": "5fbef44bd58f8a2c903b0c83",
                "ordem": 2,
                "noOrigem": {
                    "_id": "5fb50cb62a8713002471e2ce",
                    "abreviatura": "NO_9",
                    "nome": "Paragem 7",
                    "latitude": 41.24765,
                    "longitude": -8.64456,
                    "estacaoRecolha": false,
                    "pontoRendicao": true,
                    "__v": 0
                },
                "noDestino": {
                    "_id": "5fbac315a1c27827f4aabbe3",
                    "abreviatura": "AGUIA",
                    "nome": "Aguiar de Sousa",
                    "latitude": 41.1293363229325,
                    "longitude": -8.4464785432391,
                    "estacaoRecolha": false,
                    "pontoRendicao": false,
                    "__v": 0
                },
                "duracao": 12,
                "distancia": 40,
                "__v": 0
            },
            {
                "_id": "5fbef44bd58f8a2c903b0c86",
                "ordem": 3,
                "noOrigem": {
                    "_id": "5fbac315a1c27827f4aabbe3",
                    "abreviatura": "AGUIA",
                    "nome": "Aguiar de Sousa",
                    "latitude": 41.1293363229325,
                    "longitude": -8.4464785432391,
                    "estacaoRecolha": false,
                    "pontoRendicao": false,
                    "__v": 0
                },
                "noDestino": {
                    "_id": "5fbae933cb53054a30ab3670",
                    "abreviatura": "NO_11",
                    "nome": "Paragem 7",
                    "latitude": 41.24765,
                    "longitude": -8.64456,
                    "estacaoRecolha": false,
                    "pontoRendicao": true,
                    "__v": 0
                },
                "duracao": 17,
                "distancia": 35,
                "__v": 0
            }
        ],
        "idLinha": {
            "_id": "5fb9507348157b1c24bc48a9",
            "permissoesTipoViatura": [
                {
                    "_id": "5fa9add7f2042122c88a598b",
                    "codigo": "BUS_ELECT",
                    "nome": "Bus Electrico ",
                    "tipoCombustivel": "Eletrico",
                    "autonomia": 600000,
                    "velocidadeMedia": 20,
                    "consumoMedio": 32,
                    "emissoesCO2": 700
                }
            ],
            "permissoesTipoMotorista": [
                {
                    "_id": "5fb18d268df54731d851971e",
                    "codigo": "PTENG",
                    "descricao": "Motorista senior com conhecimento de lingua inglesa",
                    "__v": 0
                }
            ],
            "codigo": "Linha_S01112s1sss1",
            "nome": "teste",
            "noFinal": {
                "_id": "5fa5c2ee66af715f643a403a",
                "abreviatura": "NO_1",
                "nome": "Paragem 1",
                "latitude": 41.245432,
                "longitude": -8.648099,
                "estacaoRecolha": true,
                "pontoRendicao": false,
                "__v": 0
            },
            "__v": 0
        },
        "idPercurso": "Percurso_3",
        "ida": true,
        "__v": 0
    },
    {
        "_id": "5fbef46bd58f8a2c903b0c9f",
        "segmentos": [
            {
                "_id": "5fbef46bd58f8a2c903b0c98",
                "ordem": 1,
                "noOrigem": {
                    "_id": "5fa5c2ee66af715f643a403a",
                    "abreviatura": "NO_1",
                    "nome": "Paragem 1",
                    "latitude": 41.245432,
                    "longitude": -8.648099,
                    "estacaoRecolha": false,
                    "pontoRendicao": false,
                    "__v": 0
                },
                "noDestino": {
                    "_id": "5fb58370d0b2ea4900a43323",
                    "abreviatura": "Gandra",
                    "nome": "Gandra Station",
                    "latitude": 3.134234234,
                    "longitude": -8.234234234,
                    "estacaoRecolha": false,
                    "pontoRendicao": false,
                    "__v": 0
                },
                "duracao": 11,
                "distancia": 22,
                "__v": 0
            },
            {
                "_id": "5fbef46bd58f8a2c903b0c9b",
                "ordem": 2,
                "noOrigem": {
                    "_id": "5fb58370d0b2ea4900a43323",
                    "abreviatura": "Gandra",
                    "nome": "Gandra Station",
                    "latitude": 3.134234234,
                    "longitude": -8.234234234,
                    "estacaoRecolha": false,
                    "pontoRendicao": false,
                    "__v": 0
                },
                "noDestino": {
                    "_id": "5fbac315a1c27827f4aabbe3",
                    "abreviatura": "AGUIA",
                    "nome": "Aguiar de Sousa",
                    "latitude": 41.1293363229325,
                    "longitude": -8.4464785432391,
                    "estacaoRecolha": false,
                    "pontoRendicao": false,
                    "__v": 0
                },
                "duracao": 12,
                "distancia": 40,
                "__v": 0
            }
        ],
        "idLinha": {
            "_id": "5fb9507348157b1c24bc48a9",
            "permissoesTipoViatura": [
                {
                    "_id": "5fa9add7f2042122c88a598b",
                    "codigo": "BUS_ELECT",
                    "nome": "Bus Electrico ",
                    "tipoCombustivel": "Eletrico",
                    "autonomia": 600000,
                    "velocidadeMedia": 20,
                    "consumoMedio": 32,
                    "emissoesCO2": 700
                }
            ],
            "permissoesTipoMotorista": [
                {
                    "_id": "5fb18d268df54731d851971e",
                    "codigo": "PTENG",
                    "descricao": "Motorista senior com conhecimento de lingua inglesa",
                    "__v": 0
                }
            ],
            "codigo": "Linha_S01112s1sss1",
            "nome": "teste",
            "noFinal": {
                "_id": "5fa5c2ee66af715f643a403a",
                "abreviatura": "NO_1",
                "nome": "Paragem 1",
                "latitude": 41.245432,
                "longitude": -8.648099,
                "estacaoRecolha": true,
                "pontoRendicao": false,
                "__v": 0
            },
            "__v": 0
        },
        "idPercurso": "Percurso_4",
        "ida": true,
        "__v": 0
    }
]
```
# UI <- MDR
    GET /nos
= Planeamento <- MDR

    GET /linhas
```json
[
  {
        "_id": "5fb962be535e20055cd26c34",
        "permissoesTipoViatura": [
            {
                "_id": "5fb962be535e20055cd26c36",
                "codigo": "BUS_ELECT",
                "nome": "Bus Electrico ",
                "autonomia": 600000,
                "consumoMedio": 32,
                "tipoCombustivel": "Eletrico",
                "velocidadeMedia": 20,
                "emissoesCO2": 700
            }
        ],
        "permissoesTipoMotorista": [
            {
                "_id": "5fb962be535e20055cd26c37",
                "codigo": "PTENG",
                "descricao": "Motorista senior com conhecimento de lingua inglesa"
            },
            {
                "_id": "5fb962be535e20055cd26c38",
                "codigo": "PTESP",
                "descricao": "Motorista juniot com conhecimento de lingua espanhola"
            }
        ],
        "codigo": "Linha_S01112s1ssss1",
        "nome": "teste",
        "noFinal": {
            "_id": "5fa5c2ee66af715f643a403a",
            "abreviatura": "NO_1",
            "nome": "Paragem 1",
            "latitude": 41.245432,
            "longitude": -8.648099,
            "estacaoRecolha": true,
            "pontoRendicao": false,
            "__v": 0
        },
        "__v": 0
    },
    {
        "_id": "5fb9507348157b1c24bc48a9",
        "permissoesTipoViatura": [
            {
                "_id": "5fa9add7f2042122c88a598b",
                "codigo": "BUS_ELECT",
                "nome": "Bus Electrico ",
                "tipoCombustivel": "Eletrico",
                "autonomia": 600000,
                "velocidadeMedia": 20,
                "consumoMedio": 32,
                "emissoesCO2": 700,
                "__v": 0
            }
        ],
        "permissoesTipoMotorista": [
            {
                "_id": "5fb18d268df54731d851971e",
                "codigo": "PTENG",
                "descricao": "Motorista senior com conhecimento de lingua inglesa",
                "__v": 0
            }
        ],
        "codigo": "Linha_S01112s1sss1",
        "nome": "teste",
        "noFinal": {
            "_id": "5fa5c2ee66af715f643a403a",
            "abreviatura": "NO_1",
            "nome": "Paragem 1",
            "latitude": 41.245432,
            "longitude": -8.648099,
            "estacaoRecolha": true,
            "pontoRendicao": false,
            "__v": 0
        },
        "__v": 0
    }
]
```

GET /tiposViatura
```json
[
    {
        "_id": "5fa999a5404a952da05c672a",
        "codigo": "BUS_ELECT9",
        "nome": "Bus Electrico 9",
        "tipoCombustivel": "Eletrico",
        "autonomia": 700000,
        "velocidadeMedia": 20,
        "consumoMedio": 32,
        "emissoesCO2": 800,
        "__v": 0
    },
    {
        "_id": "5fa9add7f2042122c88a598b",
        "codigo": "BUS_ELECT",
        "nome": "Bus Electrico ",
        "tipoCombustivel": "Eletrico",
        "autonomia": 600000,
        "velocidadeMedia": 20,
        "consumoMedio": 32,
        "emissoesCO2": 700,
        "__v": 0
    },
    {
        "_id": "5fa9ae09f2042122c88a598c",
        "codigo": "BUS_GASOLINA",
        "nome": "Bus a Gasolina ",
        "tipoCombustivel": "Gasolina",
        "autonomia": 700000,
        "velocidadeMedia": 50.85,
        "consumoMedio": 28,
        "emissoesCO2": 1500,
        "__v": 0
    },
    {
        "_id": "5fa9ae35f2042122c88a598d",
        "codigo": "BUS_GAS",
        "nome": "Bus a Gas",
        "tipoCombustivel": "Gas",
        "autonomia": 300000,
        "velocidadeMedia": 30.5,
        "consumoMedio": 30,
        "emissoesCO2": 700,
        "__v": 0
    },
    {
        "_id": "5fa9d7b2c186295d147c4d78",
        "codigo": "BUS_GPL",
        "nome": "Bus a GPL",
        "tipoCombustivel": "GPL",
        "autonomia": 300000,
        "velocidadeMedia": 30.5,
        "consumoMedio": 30,
        "emissoesCO2": 700,
        "__v": 0
    },
    {
        "_id": "5fab34a3f6f22370607a78d8",
        "codigo": "BUS_GPL3",
        "nome": "Bus a GPL",
        "tipoCombustivel": "GPL",
        "autonomia": 300000,
        "velocidadeMedia": 30.5,
        "consumoMedio": 30,
        "emissoesCO2": 700,
        "__v": 0
    },
    {
        "_id": "5fafa7124345d94858cff8f6",
        "codigo": "BUS_GPL7",
        "nome": "Bus a GPL x",
        "tipoCombustivel": "GPL",
        "autonomia": 200000,
        "custoKm": 40.5,
        "velocidadeMedia": 50,
        "consumoMedio": 18.5,
        "emissoesCO2": 700,
        "__v": 0
    },
    {
        "_id": "5fb17a5b2c2538460064cf4e",
        "codigo": "BUS_GAS4",
        "nome": "Bus GAS",
        "tipoCombustivel": "Gas",
        "autonomia": 30000,
        "custoKm": 7.5,
        "velocidadeMedia": 30,
        "consumoMedio": 10.6,
        "emissoesCO2": 100,
        "__v": 0
    }
]
```

GET /tiposTripulante
```json
[
    {
        "_id": "5fbd47c8bdc03f3da005e6f6",
        "codigo": "PTJAP",
        "descricao": "Motorista junior com japones basico.",
        "__v": 0
    },
    {
        "_id": "5fbd47c8bdc03f3da005e6f7",
        "codigo": "PTFR",
        "descricao": "Motorista senior com frances fluente.",
        "__v": 0
    },
    {
        "_id": "5fbdaae64f15e21fe0409fd2",
        "codigo": "12",
        "descricao": "trip1",
        "__v": 0
    }
]
```
