using System;
using Microsoft.AspNetCore.Mvc;
using System.Collections.Generic;
using System.Threading.Tasks;
using MDV.Domain.Shared;
using MDV.Domain.ServicoViaturas;
using MDV.DTO;
using MDV.Services;

namespace MDV.Controllers
{

    [Route("api/servicosviatura")]
    [ApiController]
    public class ServicoViaturaController : ControllerBase
    {
        private readonly ServicoViaturaService _service;

        public ServicoViaturaController(ServicoViaturaService context)
        {
            _service = context;
        }

        // GET: api/servicoviatura/5
        [HttpGet("{id}")]
        public async Task<ActionResult<ServicoViaturaDTO>> GetGetById(String id)
        {
            var sv = await _service.GetByIdAsync(new ServicoViaturaId(id));

            if (sv == null)
            {
                return NotFound();
            }

            return sv;
        }
        
        //get api/servicoviatura
        [HttpGet]
        public async Task<ActionResult<IEnumerable<ServicoViaturaDTO>>> GetAll()
        {
            return await _service.GetAllAsync();
        }

        // POST: api/servicoviatura
        [HttpPost]
        public async Task<ActionResult<ServicoViaturaDTO>> Create(CreatingServicoViaturaDto dto)
        {
            try {
                var sv = await _service.AddAsync(dto);
                Console.WriteLine("Servico de Viatura Criado ...");
                // Console.ReadKey();
                return CreatedAtAction(nameof(GetGetById),new { id = sv.Id}, sv);
            }
            catch (BusinessRuleValidationException ex)
            {
                 return BadRequest(new {Message = ex.Message});
            }
        }

       
    }


}