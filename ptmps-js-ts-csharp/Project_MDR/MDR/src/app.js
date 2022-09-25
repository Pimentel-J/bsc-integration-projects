var express = require('express');
var config = require('../config');
var loaders = require('./loaders');
var cors = require('cors')


async function startServer() {

    var app = express();

    app.use(cors());

    await loaders(app);

    var port = config.port;

    await app.listen(port);
    console.log('Listening on port ' + port);
}

startServer();

