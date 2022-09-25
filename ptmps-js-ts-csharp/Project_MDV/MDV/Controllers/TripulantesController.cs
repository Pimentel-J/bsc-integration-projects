using System;
using Microsoft.AspNetCore.Mvc;
using System.Collections.Generic;
using System.Threading.Tasks;
using System.Net.Http;
using System.Net.Http.Headers;
using MDV.Domain.Tripulantes;
using MDV.Domain.Shared;
using MDV.DTO;
using MDV.Services;

namespace MDV.Controllers
{
    [Route("api/tripulantes")]
    [ApiController]
    public class TripulantesController : ControllerBase
    {
        private readonly TripulanteService _service;

        public TripulantesController(TripulanteService context)
        {
            _service = context;
        }

        [HttpGet]
        public async Task<ActionResult<IEnumerable<TripulanteDTO>>> GetAll()
        {
            return await _service.GetAllAsync();
        }

        //get api/tripulantes/TRP123456
        [HttpGet("{numeroMecanografico}")]
        public async Task<ActionResult<TripulanteDTO>> GetById(string numeroMecanografico)
        {
            var tripulante = await _service.GetByIdAsync(new TripulanteId(numeroMecanografico));
            if (tripulante == null)
            {
                return NotFound();
            }
            return tripulante;
        }

        [HttpPut("{numeroMecanografico}")]
        public async Task<ActionResult<TripulanteDTO>> Update(string numeroMecanografico, TripulanteDTO dto)
        {
            // if (numeroMecanografico != dto.Id)
            if (numeroMecanografico != dto.numeroMecanografico)
            {
                return BadRequest();
            }

            try
            {
                var tripulante = await _service.UpdateAsync(dto);
                if (tripulante == null)
                {
                    return NotFound();
                }
                return Ok(tripulante);
            }
            catch (BusinessRuleValidationException ex)
            {
                return BadRequest(new { Message = ex.Message });
            }
        }

        [HttpPost]
        public async Task<ActionResult<TripulanteDTO>> Create(CreatingTripulanteDTO dto)
        {
            try
            {

                var codigo = dto.tipoTripulante;
                string mdrPrefix = Config.WebApiApplicationJson();
                var client = new HttpClient();
                client.DefaultRequestHeaders.Accept.Clear();
                client.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));
                Console.WriteLine(mdrPrefix);
                HttpResponseMessage resp = await client.GetAsync(mdrPrefix + "tiposTripulante/" + codigo);

                if (resp.IsSuccessStatusCode)
                {
                    string content = resp.Content.ReadAsStringAsync().Result;
                    if (content != "null")
                    {
                        var tripulante = await _service.AddAsync(dto);
                        Console.WriteLine("Tripulante " + tripulante.numeroMecanografico + " criado com sucesso");
                        return CreatedAtAction(nameof(GetById), new { numeroMecanografico = tripulante.Id }, tripulante);
                    }

                }
                Console.WriteLine("Tipo de Tripulante Id inválido");
                return BadRequest(new { Message = "Tipo de Tripulante Id inválido" });

            }
            catch (BusinessRuleValidationException ex)
            {
                return BadRequest(new { Message = ex.Message });
            }
        }

        [HttpDelete("{numeroMecanografico}")]
        public async Task<ActionResult<TripulanteDTO>> HardDelete(string numeroMecanografico)
        {
            try
            {
                var tripulante = await _service.DeleteAsync(new TripulanteId(numeroMecanografico));

                if (tripulante == null)
                {
                    return NotFound();
                }

                // return Ok(tripulante);
                return Ok();
            }
            catch (BusinessRuleValidationException ex)
            {
                return BadRequest(new { Message = ex.Message });
            }
        }

    }
}