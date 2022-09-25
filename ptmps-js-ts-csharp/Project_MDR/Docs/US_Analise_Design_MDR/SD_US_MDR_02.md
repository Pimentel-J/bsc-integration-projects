title: US_MDR_02 - Criar um nó

HTTP->MDR: POST ../no
MDR->noRoute: router.post(req,..)
noRoute->criarNoController: criarNo(req.body,res)
criarNoController->noMapperJson2Dto: json2dto(body)
noMapperJson2Dto->noDTO: new(body.{abrev., nome, ...})
noMapperJson2Dto->criarNoController: noDTO
criarNoController->criarNoService: criarNo(noDTO,res)
criarNoService->noMapperDto2Domain: dto2model(noDTO)
noMapperDto2Domain->no: new(dto.{abrev., nome, ...})
criarNoService->noRepo: save(noModel, res)
noRepo->noMongoDB: new()
noRepo->noSchemaModel: new()
noRepo->noMapperDomain2Data: domain2data(noSchemaModel, noMongoDB)
noMapperDomain2Data->noRepo: noSchemaModel
noRepo->noSchemaModel: save
noRepo->criarNoService: res
criarNoService->criarNoController: res
criarNoController->noRoute: res
noRoute->MDR: res
MDR->HTTP: res = {message: 'Nó criado.'}
