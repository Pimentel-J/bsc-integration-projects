# US41 SD
title Criar Tripulante

HTTP->MDV: http.post(tripulante...)
MDV->TripulantesController: Create(dto)
TripulantesController->MDR: client.GetAsync(tipoTripulante.codigo...)
MDR->TripulantesController: resp(taskObject)
TripulantesController->TripulanteService: AddAsync(dto)
TripulanteService->*Tripulante: new(dto.numeroMec,...)
database ITripulanteRepository
TripulanteService->ITripulanteRepository: AddAsync(tripulante)
TripulanteService->IUnitOfWork: CommitAsync()
TripulanteService->*CreatingTripulanteDTO: new(dto.id,...)
TripulanteService->TripulantesController: dto
TripulantesController->TripulantesController: CreatedAtAction(dto.id,...)
TripulantesController->HTTP: response