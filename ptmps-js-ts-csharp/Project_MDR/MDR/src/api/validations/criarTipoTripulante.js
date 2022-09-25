const Joi = require('joi');
const regexText = 'alphanumerics, space (except at start), dot, comma chars only';

/**
 * Tipo Tripulante Joi validation schema
 */
const schemaUnit = Joi.object({
    codigo: Joi.string().max(20).required().regex(/^[^-\s][A-zÀ-ú0-9,. ]*$/, regexText),
    descricao: Joi.string().max(250).required().regex(/^[^-\s][A-zÀ-ú0-9,. ]*$/, regexText)
});

/**
 * Tipo Tripulante Joi validation schema
 */
const schemaUnitAlterar = Joi.object({
    codigo: Joi.string().max(20).required().regex(/^[^-\s][A-zÀ-ú0-9,. ]*$/, regexText),
    descricao: Joi.string().max(250).required().regex(/^[^-\s][A-zÀ-ú0-9,. ]*$/, regexText)
}).options({ allowUnknown: true });

/**
 * Tipo Tripulante array Joi validation schema
 */
const schemaArray = Joi.array().items(Joi.object({
    $: {
        key: Joi.string(),
        name: Joi.string().max(20).required().regex(/^[^-\s][A-zÀ-ú0-9,. ]*$/, regexText),
        description: Joi.string().max(250).required().regex(/^[^-\s][A-zÀ-ú0-9,. ]*$/, regexText)
    }
}));


module.exports = { 
    schemaUnit, 
    schemaArray,
    schemaUnitAlterar
 };