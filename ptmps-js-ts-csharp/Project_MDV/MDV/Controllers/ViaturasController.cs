using System;
using Microsoft.AspNetCore.Mvc;
using System.Collections.Generic;
using System.Threading.Tasks;
using System.Net.Http;
using System.Net.Http.Headers;
using MDV.Domain.Viaturas;
using MDV.Domain.Shared;
using MDV.DTO;
using MDV.Services;


namespace MDV.Controllers
{
    [Route("api/viaturas")]
    [ApiController]
    public class ViaturasController: ControllerBase
    {
         private readonly ViaturaService _service; 
        
         public ViaturasController(ViaturaService context) {
             _service = context;
         }       
     
        //get api/viaturas
        [HttpGet]
        public async Task<ActionResult<IEnumerable<ViaturaDTO>>> GetAll()
        {
            return await _service.GetAllAsync();
        }
        
        //get api/viaturas/AA-23-ED
        [HttpGet("{matricula}")]
        public async Task<ActionResult<ViaturaDTO>> GetGetById(string matricula) 
        {
            var viatura = await _service.GetByIdAsync(new ViaturaId(matricula));
            if (viatura == null)
            {
                return NotFound();
            }
            return viatura;
        }

       [HttpPut("{matricula}")]  
        public async Task<ActionResult<ViaturaDTO>> Update(string matricula, ViaturaDTO dto)
        {
            if (matricula != dto.Id)
            {
                return BadRequest();
            }

            try{
                var bloco = await _service.UpdateAsync(dto);
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

        [HttpPost]
        public async Task<ActionResult<ViaturaDTO>> Create(CreatingViaturaDTO dto)
        {
            try {

                var codigo = dto.tipoviatura;
                string mdrPrefix = Config.WebApiApplicationJson();
                var client = new HttpClient();
                client.DefaultRequestHeaders.Accept.Clear();
                client.DefaultRequestHeaders.Accept.Add( new MediaTypeWithQualityHeaderValue("application/json"));
                Console.WriteLine(mdrPrefix);
                HttpResponseMessage resp = await client.GetAsync(mdrPrefix+"tiposViatura/"+codigo);

                if (resp.IsSuccessStatusCode)
                { 
                    string content = resp.Content.ReadAsStringAsync().Result;
                    if (content!="null") {
                        var viatura = await _service.AddAsync(dto);
                        Console.WriteLine("Viatura Criada ...");
                       // Console.ReadKey();
                        return CreatedAtAction(nameof(GetGetById),new {matricula = viatura.Id}, viatura);
                    }

                   

                }
                Console.WriteLine("Invalido Tipo de Viatura Id.");
                return BadRequest(new {Message = "Invalido Tipo de Viatura Id"});

            } 
            catch(BusinessRuleValidationException ex) 
            {
                return BadRequest(new {Message = ex.Message});
            }   

        }

        
        [HttpDelete("{matricula}")]
        public async Task<ActionResult<ViaturaDTO>> HardDelete(string matricula)
        {
            try
            {
                var viatura = await _service.DeleteAsync(new ViaturaId(matricula));

                if (viatura == null)
                {
                    return NotFound();
                }

                return Ok(matricula);
            }
            catch(BusinessRuleValidationException ex)
            {
               return BadRequest(new {Message = ex.Message});
            }
        }

    }
}