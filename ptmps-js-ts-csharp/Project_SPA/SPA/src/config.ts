const mdrLocalURL = 'http://localhost:3000';
const mdvLocalURL = 'https://localhost:5001';
const planLocalURL = 'http://localhost:1234';

const mdrCloudURL = 'https://lapr5-20s5-3na-2-mdr.herokuapp.com';
const mdvCloudURL = 'https://20s5-3na-mdv.azurewebsites.net';
const planCloudURL = 'http://51.144.126.30:1234';
const authCloudURL = 'https://lapr5-20s5-3na-2-auth.herokuapp.com';

const apiPrefix = '/api/'

const hereApiToken = '***private***';

export default {
  apis: {
    mdrPrefix: mdrCloudURL + apiPrefix,
    mdvPrefix: mdvCloudURL + apiPrefix,
    planPrefix: planCloudURL,
    hereMapsToken: hereApiToken,
    authPrefix: authCloudURL
  }
}

