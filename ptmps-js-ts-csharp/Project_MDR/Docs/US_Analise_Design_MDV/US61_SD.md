# US61 SD
title Definir Serviço Tripulante

HTTP->MDV: http.post(servicoTripulante...)
MDV->ServicosTripulanteController: Create(dto)
ServicosTripulanteController->ServicoTripulanteService: AddAsync(dto)
ServicoTripulanteService->*ServicoTripulante: new(dto.codigo,...)
database IServicoTripulanteRepository
ServicoTripulanteService->IServicoTripulanteRepository: AddAsync(servicoTripulante)
ServicoTripulanteService->IUnitOfWork: CommitAsync()
ServicoTripulanteService->*CreatingServicoTripulanteDTO: new(dto.codigo,...)
ServicoTripulanteService->ServicosTripulanteController: dto
ServicosTripulanteController->ServicosTripulanteController: CreatedAtAction(dto.codigo,...)
ServicosTripulanteController->HTTP: response