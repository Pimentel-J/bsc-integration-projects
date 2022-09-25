using Microsoft.AspNetCore.Mvc;
using System.Collections.Generic;
using System;
using System.Threading.Tasks;
using MDV.Domain.Shared;
using MDV.Domain.Viagens;
using MDV.Services;
using MDV.DTO;

namespace MDV.Controllers
{
    [Route("api/viagens")]
    [ApiController]

    public class ViagensController : ControllerBase
    {
        private readonly ViagemService _service;
        private readonly CriarViagemService _criarViagemService;

        public ViagensController(ViagemService service, CriarViagemService criarServ)
        {
            _service = service;
            _criarViagemService = criarServ;
        }

        // GET: api/viagens - Esta incluido o GET por servicoViatura, com uma condição que verifica se retorna todas as viagens ou com query
        [HttpGet]
        public async Task<ActionResult<IEnumerable<ViagemDTO>>> GetAll(string servicoViatura)
        {
            if (servicoViatura ==null)
            {
                return await _service.GetAllAsync();
            }
            else
            {
                return await _service.GetOfServicoViatura(servicoViatura);
            }
        }

        // GET: api/viagens/5
        [HttpGet("{id}")]
        public async Task<ActionResult<ViagemDTO>> GetGetById(Guid id)
        {
            var viagem = await _service.GetByIdAsync(new ViagemId(id));

            if (viagem == null)
            {
                return NotFound();
            }

            return viagem;
        }

        //[HttpGet]
        //public async Task<ActionResult<List<ViagemDTO>>> GetOfServicoViatura(string servicoViatura)
        //{
            
        //}


        //GET: api/viagens/semservico
        [HttpGet("semservico")]
        public async Task<ActionResult<IEnumerable<ViagemDTO>>> GetAllWithOutSVAsync()
        {
            return await _service.GetAllWithOutServViatAsync();
        }

        // POST: api/viagens
        //[HttpPost]
        //public async Task<ActionResult<ViagemDTO>> Create(CreatingViagemDTO dto)
        //{
        //    var viagem = await _service.AddAsync(dto);

        //    return CreatedAtAction(nameof(GetGetById), new { id = viagem.Id }, viagem);
        //}

        // POST: api/viagens
        [HttpPost]
        public async Task<ActionResult<ICollection<ViagemDTO>>> CriarViagem(CreatingViagemDTO dto)
        {
            //Console.WriteLine(dto.ToString());
            var horaInicio = (int)dto.HoraInicio;
            var percId = (string)dto.PercursoId;
            var percIda = (string)dto.PercursoIdaId;
            var percVolta = (string)dto.PercursoVoltaId;
            var freq = (int)dto.Frequencia;
            var nViagens = (int)dto.NViagens;
            var viagens = new List<ViagemDTO>();
            var viagem = new ViagemDTO();
            if (percId != null)
            {
                viagem = await _criarViagemService.CriarViagem(horaInicio, percId);
                if (viagem == null)
                {
                    return BadRequest(new { Message = "Viagem nao criada" });
                }
                viagens.Add(viagem);
            }

            else
            {
                viagens = await _criarViagemService.CriarViagens(horaInicio, percIda, percVolta, freq, nViagens);
                if (viagens == null)
                {
                    return BadRequest(new { Message = "Viagens nao criadas" });
                }
            }

            //return CreatedAtAction(nameof(GetGetById), new { id = viagem.Id }, viagem);
            //return CreatedAtAction("Viagens: ",viagens);

            return viagens;
        }


        // PUT: api/viagens/5
        [HttpPut("{id}")]
   /*     public async Task<ActionResult<ViagemDTO>> Update(Guid id, ViagemDTO dto)
        {
            if (id != dto.Id)
            {
                return BadRequest();
            }

            try
            {
                var cat = await _service.UpdateAsync(dto);

                if (cat == null)
                {
                    return NotFound();
                }
                return Ok(cat);
            }
            catch (BusinessRuleValidationException ex)
            {
                return BadRequest(new { Message = ex.Message });
            }
        }*/

        // Inactivate: api/viagens/5
        //[HttpDelete("{id}")]
        //public async Task<ActionResult<CategoryDto>> SoftDelete(Guid id)
        //{
        //    var cat = await _service.InactivateAsync(new CategoryId(id));

        //    if (cat == null)
        //    {
        //        return NotFound();
        //    }

        //    return Ok(cat);
        //}

        // DELETE: api/viagens/5
        [HttpDelete("{id}/hard")]
        public async Task<ActionResult<ViagemDTO>> HardDelete(Guid id)
        {
            try
            {
                var viagem = await _service.DeleteAsync(new ViagemId(id));

                if (viagem == null)
                {
                    return NotFound();
                }

                return Ok(viagem);
            }
            catch (BusinessRuleValidationException ex)
            {
                return BadRequest(new { Message = ex.Message });
            }
        }
    }
}
    