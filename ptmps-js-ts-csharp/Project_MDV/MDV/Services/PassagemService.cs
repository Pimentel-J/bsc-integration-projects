using System;
using System.Threading.Tasks;
using System.Collections.Generic;
using MDV.Repositories.Shared;
using MDV.Repositories;
using MDV.Domain.Passagens;
using MDV.Domain.Viagens;
using MDV.Domain.Shared;
using MDV.Services;
using MDV.DTO;
using Microsoft.AspNetCore.Mvc;
using System.Net.Http;
using System.Net.Http.Headers;
using Newtonsoft.Json.Linq;
using System.Linq;

namespace MDV.Services
{
    public class PassagemService
    {
        private readonly IUnitOfWork _unitOfWork;
        private readonly IPassagemRepository _repo;
        
        public PassagemService(IUnitOfWork unitOfWork, IPassagemRepository repo)
        {
            this._unitOfWork = unitOfWork;
            this._repo = repo;
        }

        public async Task<List<PassagemDTO>> GetAllAsync()
        {
            var list = await this._repo.GetAllAsync();

            List<PassagemDTO> listDto = list.ConvertAll<PassagemDTO>(passagem => new PassagemDTO { Id = passagem.Id.GetAsGuid(), ViagemId = passagem.ViagemId.AsString(), HoraPassagem = passagem.HoraPassagem, AbreviaturaNo = passagem.AbreviaturaNo });

            return listDto;
        }

        public async Task<ActionResult<IEnumerable<PassagemDTO>>> GetOfViagem(string viagem)
        {
            // obtem as passagens da viagem selecionada
            var list = await this._repo.GetOfViagem(viagem);

            List<PassagemDTO> listDto = list.ConvertAll<PassagemDTO>(passagem => new PassagemDTO
            {
                Id = passagem.Id.GetAsGuid(),
                ViagemId = passagem.ViagemId.AsString(),
                HoraPassagem = passagem.HoraPassagem,
                AbreviaturaNo = passagem.AbreviaturaNo
            });

            List<PassagemDTO> pontosTroca = new List<PassagemDTO>();


            string noUrl = Config.WebApiApplicationJson() + "nos";
            var client = new HttpClient();
            client.DefaultRequestHeaders.Accept.Clear();
            client.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));

            // obtem a lista completa de nos, para evitar fazer várias chamadas à API do MDR
            HttpResponseMessage resp = await client.GetAsync(noUrl);

            if (resp.Content != null)
            {
                JArray nos = JArray.Parse(resp.Content.ReadAsStringAsync().Result);
                foreach (PassagemDTO passagem in listDto)
                {
                    // para cada passagem, obtem o objeto do nó correspondente
                    JObject no = nos.Children<JObject>().FirstOrDefault(o => o["abreviatura"] != null && o["abreviatura"].ToString() == passagem.AbreviaturaNo);
                    if (no != null)
                    {
                        // se o nó for ponto de troca, é adicionado à lista a ser retornada
                        if ((Boolean)no["estacaoRecolha"] || (Boolean)no["pontoRendicao"])
                        {
                            pontosTroca.Add(passagem);
                        }
                    }
                }
            }

            return pontosTroca;
        }

        private async Task<Boolean> ValidaPontoTroca(PassagemDTO passagem)
        {

            string no = passagem.AbreviaturaNo;

            // obtem o nó do MDR com base na abreviatura encontrada
            string noUrl = Config.WebApiApplicationJson() + "nos/";
            var client = new HttpClient();
            client.DefaultRequestHeaders.Accept.Clear();
            client.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));
            HttpResponseMessage resp = await client.GetAsync(noUrl + no);
            if (resp.Content != null)
            {
                JObject noObj = JObject.Parse(resp.Content.ReadAsStringAsync().Result);
                // verifica se o Nó correponde a um ponto de troca de motoristas
                if (!(Boolean)noObj["estacaoRecolha"] && !(Boolean)noObj["pontoRendicao"])
                {
                    return false;
                }
                else
                {
                    return true;
                }
            }
            else
            {
                return false;
            }
        }

        public async Task<List<Passagem>> GetPassagens()
        {
            var list = await this._repo.GetAllAsync();
            return list;
        }
        public async Task<PassagemDTO> GetByIdAsync(PassagemId id)
        {
            var passagem = await this._repo.GetByIdAsync(id);

            if (passagem == null)
                return null;

            return new PassagemDTO { Id = passagem.Id.GetAsGuid(), ViagemId = passagem.ViagemId.AsString(), HoraPassagem = passagem.HoraPassagem, AbreviaturaNo = passagem.AbreviaturaNo };
        }

        public async Task<PassagemDTO> AddAsync(CreatingPassagemDTO dto)
        {
            var viagemId = new ViagemId(dto.ViagemId);
            var passagem = new Passagem(viagemId, dto.HoraPassagem, dto.AbreviaturaNo);

            await this._repo.AddAsync(passagem);

            await this._unitOfWork.CommitAsync();

            return new PassagemDTO { Id = passagem.Id.GetAsGuid(), ViagemId = passagem.ViagemId.AsString(), HoraPassagem = passagem.HoraPassagem, AbreviaturaNo = passagem.AbreviaturaNo };
        }

        public async Task<PassagemDTO> DeleteAsync(PassagemId id)
        {
            var passagem = await this._repo.GetByIdAsync(id);

            if (passagem == null)
                return null;

            //if (viagem.Active)
            //    throw new BusinessRuleValidationException("It is not possible to delete an active viagem.");

            this._repo.Remove(passagem);
            await this._unitOfWork.CommitAsync();

            return new PassagemDTO { Id = passagem.Id.GetAsGuid(), ViagemId = passagem.ViagemId.AsString(), HoraPassagem = passagem.HoraPassagem, AbreviaturaNo = passagem.AbreviaturaNo };
        }
    }
}