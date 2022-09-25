var mongoose = require('mongoose');
var config = require('../../config');

class Database {
    constructor() {
        this.connect();
    }
    connect() {
        mongoose.connect(config.mongoDatabaseURL, { useNewUrlParser: true, useUnifiedTopology: true, useCreateIndex: true })
            .then(() => { console.log('Ligacao ao mongoDB com sucesso.'); })
            .catch(err => { console.error('ERRO na ligacao ao mongoDB.'); })
    }
}

module.exports = new Database();