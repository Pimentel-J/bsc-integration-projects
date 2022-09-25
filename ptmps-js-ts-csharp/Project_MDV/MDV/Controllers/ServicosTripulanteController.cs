using System;
using Microsoft.AspNetCore.Mvc;
using System.Collections.Generic;
using System.Threading.Tasks;
using MDV.Domain.ServicosTripulante;
using MDV.Domain.Shared;
using MDV.DTO;
using MDV.Services;

namespace MDV.Controllers
{
    [Route("api/servicosTripulante")]
    [ApiController]
    public class ServicosTripulanteController : ControllerBase
    {
        private readonly ServicoTripulanteService _service;

        public ServicosTripulanteController(ServicoTripulanteService context)
        {
            _service = context;
        }

        [HttpGet]
        public async Task<ActionResult<IEnumerable<ServicoTripulanteDTO>>> GetAll()
        {
            return await _service.GetAllAsync();
        }

        //get api/tripulantes/TRP123456
        [HttpGet("{codigoServicoTripulante}")]
        public async Task<ActionResult<ServicoTripulanteDTO>> GetById(string codigoServicoTripulante)
        {
            var servicoTripulante = await _service.GetByIdAsync(new ServicoTripulanteId(codigoServicoTripulante));
            if (servicoTripulante == null)
            {
                return NotFound();
            }
            return servicoTripulante;
        }

        [HttpPost]
        public async Task<ActionResult<ServicoTripulanteDTO>> Create(CreatingServicoTripulanteDTO dto)
        {
            try
            {
                var servicoTripulante = await _service.AddAsync(dto);
                Console.WriteLine("Serviço de Tripulante " + servicoTripulante.codigoServicoTripulante + " criado com sucesso");
                return CreatedAtAction(nameof(GetById), new { codigoServicoTripulante = servicoTripulante.Id }, servicoTripulante);
            }
            catch (BusinessRuleValidationException ex)
            {
                return BadRequest(new { Message = ex.Message });
            }
        }

        [HttpDelete("{codigoServicoTripulante}")]
        public async Task<ActionResult<ServicoTripulanteDTO>> HardDelete(string codigoServicoTripulante)
        {
            try
            {
                var servicoTripulante = await _service.DeleteAsync(new ServicoTripulanteId(codigoServicoTripulante));

                if (servicoTripulante == null)
                {
                    return NotFound();
                }

                // return Ok(servicoTripulante);
                return Ok();
            }
            catch (BusinessRuleValidationException ex)
            {
                return BadRequest(new { Message = ex.Message });
            }
        }

    }
}