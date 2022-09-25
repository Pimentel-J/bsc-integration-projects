using System.Collections.Generic;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using MDV.Domain.Shared;
using MDV.Domain.Blocos;
using MDV.DTO;
using MDV.Services;

namespace MDV.Controllers
{
    [Route("api/blocos")]
    [ApiController]
    public class BlocosController : ControllerBase
    {
        private readonly BlocoService _service;

        public BlocosController(BlocoService context)
        {
            _service = context;
        }

        [HttpGet]
        public async Task<ActionResult<IEnumerable<BlocoDTO>>> GetAll()
        {
            return await _service.GetAllAsync();
        }

        [HttpGet("{codigo}")]
        public async Task<ActionResult<BlocoDTO>> GetGetById(string codigo)
        {
            var bloco = await _service.GetByIdAsync(new BlocoId(codigo));

            if (bloco == null)
            {
                return NotFound();
            }

            return bloco;
        }

        [HttpPut("{codigo}")]
        public async Task<ActionResult<BlocoDTO>> Update(string codigo, BlocoDTO blocoDTO)
        {
            if (codigo != blocoDTO.Codigo)
            {
                return BadRequest();
            }

            try{
                var bloco = await _service.UpdateAsync(blocoDTO);
                if (bloco == null)
                {
                    return NotFound();
                }
                return Ok(bloco);
            }
            catch(BusinessRuleValidationException ex)
            {
                return BadRequest(new {Message = ex.Message});
            }
        }

        /*[HttpGet("Viagens/{id}")]
        public async Task<List<ViagemDTO>> GetFreeTrips(String id)
        {
           return await _service.GetFreeTrips(new ServicoViaturaId(id));
        }*/

        [HttpPost]
        public async Task<ActionResult<BlocoDTO>> Create(CreatingBlocoDTO BlocoDTO)
        {
            try{
            return await _service.AddAsync(BlocoDTO);
            }
            catch (BusinessRuleValidationException ex)
            {
                 return BadRequest(new {Message = ex.Message});
            }
        }


        [HttpDelete("{codigo}")]
        public async Task<ActionResult<BlocoDTO>> HardDelete(string codigo)
        {
            try
            {
                var bloco = await _service.DeleteAsync(new BlocoId(codigo));

                if (bloco == null)
                {
                    return NotFound();
                }

                return Ok(bloco);
            }
            catch(BusinessRuleValidationException ex)
            {
               return BadRequest(new {Message = ex.Message});
            }
        }
    }
}
