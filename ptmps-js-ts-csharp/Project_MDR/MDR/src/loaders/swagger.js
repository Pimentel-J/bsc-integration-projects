
function swaggerOptions(app) {
    const expressSwagger = require('express-swagger-generator')(app);

    let options = {
        swaggerDefinition: {
            info: {
                description: 'Documentacao da API de Master Data Rede',
                title: 'MDR API',
                version: '1.0.0',
            },
            host: 'localhost:3000',
            basePath: '/api',
            produces: [
                "application/json"
            ],
            schemes: ['http', 'https'],
            /*securityDefinitions: {
                JWT: {
                    type: 'apiKey',
                    in: 'header',
                    name: 'Authorization',
                    description: "",
                }
            }*/
        },
        basedir: __dirname, //app absolute path
        files: ['../api/routes/*.js'] //Path to the API handle folder
    };
    expressSwagger(options)
}

module.exports = swaggerOptions;
