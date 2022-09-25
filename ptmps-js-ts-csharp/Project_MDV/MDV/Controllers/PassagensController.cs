using Microsoft.AspNetCore.Mvc;
using System.Collections.Generic;
using System.Threading.Tasks;
using System;
using MDV.Services;
using MDV.DTO;
using MDV.Domain.Passagens;

namespace MDV.Controllers
{
    [Route("api/passagens")]
    [ApiController]

    public class PassagensController : ControllerBase
    {
        private readonly PassagemService _service;


        public PassagensController(PassagemService service)
        {
            _service = service;
        }

        // GET: api/passagens - Esta incluido o GET por viagem e pontoTroca, com uma condição que verifica se retorna todas as passagens ou com query
        [HttpGet]
        public async Task<ActionResult<IEnumerable<PassagemDTO>>> GetAll(string viagem, string pontoTroca)
        {
            Console.WriteLine("viagem:" + viagem + "; pontoTroca:" + pontoTroca);
            if (viagem != null && pontoTroca == "true")
            {
                // passagens?viagem=<viagemID>&pontoTroca=true
                return await _service.GetOfViagem(viagem);
            } else
            {
                return await _service.GetAllAsync();
            }
        }

        // GET: api/passagens/5
        [HttpGet("{id}")]
        public async Task<ActionResult<PassagemDTO>> GetGetById(Guid id)
        {
            var passagem = await _service.GetByIdAsync(new PassagemId(id));

            if (passagem == null)
            {
                return NotFound();
            }

            return passagem;
        }

    }
}
    