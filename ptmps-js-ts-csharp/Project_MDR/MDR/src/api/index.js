var express = require('express');

var linhaRoute = require('./routes/linhaRoute');
var noRoute = require('./routes/noRoute');
var percursoRoute = require('./routes/percursoRoute');
var tipoviaturaRoute = require('./routes/tipoviaturaRoute');
var tipoTripulanteRoute = require('./routes/tipoTripulanteRoute');
var importRoute = require('./routes/importRoute');
var app = express();

app.setMaxListeners(0);

app.use(linhaRoute);
app.use(noRoute);
app.use(percursoRoute);
app.use(tipoviaturaRoute);
app.use(tipoTripulanteRoute);
app.use(importRoute);

module.exports = app;