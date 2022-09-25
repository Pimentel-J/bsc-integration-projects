// MongoDB settings
const server = '***private***.mongodb.net';
const database = 'ait_db';
const username = 'data_admin';
const password = '***private***';
const config = {
    port: process.env.PORT || '3000',
    mongoDatabaseURL: `mongodb+srv://${username}:${password}@${server}/${database}?retryWrites=true&w=majority`
};

module.exports = config;