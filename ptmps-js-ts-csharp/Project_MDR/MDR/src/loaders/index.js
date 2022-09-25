var loader = require('./express');

async function bootstrap(app) {
    await require('./mongoose');
    await require('./swagger')(app);
    await loader(app);
};

module.exports = bootstrap;