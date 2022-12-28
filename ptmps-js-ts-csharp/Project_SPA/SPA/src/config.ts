const mdrLocalURL = 'http://localhost:3000';
const mdvLocalURL = 'https://localhost:5001';
const planLocalURL = 'http://localhost:1234';

const mdrCloudURL = 'https://***private***.herokuapp.com';
const mdvCloudURL = 'https://***private***.azurewebsites.net';
const planCloudURL = 'http://***private***:1234';
const authCloudURL = 'https://***private***.herokuapp.com';

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

