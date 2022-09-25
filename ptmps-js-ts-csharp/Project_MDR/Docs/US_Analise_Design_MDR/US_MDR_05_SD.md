# US_MDR_05 SD
title Criar tipo de tripulante

HTTP->MDR: POST .../tipoTripulante
MDR->tipoTripulanteRoute: router.post('/',(req,...)
tipoTripulanteRoute->criarTipoTripulanteController: criarTipoTripulante(req.body,res)
criarTipoTripulanteController->*tipoTripulanteDTO: new(codigo,descricao)
criarTipoTripulanteController->criarTipoTripulanteService: criarTipoTripulante(tipoTripulanteDTO,res)
criarTipoTripulanteService->*tipoTripulante: new(DTO.codigo,...)
database tipoTripulanteMongoDB
criarTipoTripulanteService->tipoTripulanteMongoDB: save(tipoTripulante)
criarTipoTripulanteService->criarTipoTripulanteController: res
criarTipoTripulanteController->tipoTripulanteRoute: res
tipoTripulanteRoute->MDR: res
MDR->HTTP: res={message:'Tipo Tripulante criado...'}
