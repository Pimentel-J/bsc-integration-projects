//var NoModel = require('no');

// Load the CSV with d3-request
let dataLoaded = false;
let nos;
let ligacoes;
var meshes = [];
var linhas = [];

var material = new THREE.MeshLambertMaterial({ color : 0x00dddf, side: 2, flatShading: THREE.FlatShading });
const material2 = new THREE.MeshBasicMaterial({
     color: 0x00dddf,
    wireframe: false,
    opacity: 0.5,
    transparent: false,
    alphaTest: 0.05,
    side: 2,
});


/*d3.json("nos.json").then(function(data) {
    console.log("teste");
    console.log(data);
    dados = data;
});*/

d3.csv("../Data/nozinhos.csv", (d) => {
    return {
        nome: d.nome,
        lat: d.latitude,
        lng: d.longitude,
    }
}).then(function (data) {
    nos = data;
    for (var i = 0; i < data.length; i++) {
        const radius = 1;
        const square = 1;
        var geometry = new THREE.TorusGeometry(radius, square, 16, 100);
        meshes.push(new THREE.Mesh(geometry, material));
    }
    dataLoaded = true;
});

d3.csv("../Data/ligacoes.csv", (d) => {
    return {
        p1: d.ponto1,
        p2: d.ponto2,
    }
}).then(function (data) {
    ligacoes = data;
    /* for(var i =0;i<data.length;i++){
         var linha = new THREE.Line();
     }*/
});

function convertRange(value, r1, r2) {
    return (value - r1[0]) * (r2[1] - r2[0]) / (r1[1] - r1[0]) + r2[0];
}

function getLongitude(nome) {
    for (let i = 0; i < nos.length; i++) {
        if (nos[i].nome === nome)
            return nos[i].lng;
    }
}

function getLatitude(nome) {
    for (let i = 0; i < nos.length; i++) {
        if (nome === nos[i].nome)
            return nos[i].lat;
    }
}




// Scene Configurations
const WIDTH = 1600;
const HEIGHT = 700;
const VIEW_ANGLE = 45;
const ASPECT = WIDTH / HEIGHT;
const NEAR = 0.1;
const FAR = 10000;

// Scene, camera, canvas, renderer
var scene = new THREE.Scene();
var camera = new THREE.PerspectiveCamera(VIEW_ANGLE, ASPECT, NEAR, FAR);
var canvas = document.getElementById("mapa");
var renderer = new THREE.WebGLRenderer({ alpha: true, canvas: canvas });
const camera1 = new THREE.PerspectiveCamera( 45,WIDTH /HEIGHT, 1, 10000 );
camera.position.z = 300;
scene.add(camera);
renderer.setSize(WIDTH, HEIGHT);
const controls = new THREE.OrbitControls( camera1 );
controls.update();
// Light

var light = new THREE.PointLight(0xffffff, 1.2);
light.position.set(0, 0, 6);
scene.add(light);

// API Key for Mapboxgl. Get one here:
// https://www.mapbox.com/studio/account/tokens/
var key = 'pk.eyJ1IjoibWFwcGF1c2VyIiwiYSI6ImNqNXNrbXIyZDE2a2cyd3J4Ym53YWxieXgifQ.JENDJqKE1SLISxL3Q_T22w'



var options = {
    lat: 41.15,
    lng: -8.44,
    zoom: 11,
    pitch: 50,

}

//var mappa = new Mappa('Mapboxgl', key);
// Create a new Mappa instance using Leaflet.
const mappa = new Mappa('MapboxGL', key);
var myMap = mappa.tileMap(options);
myMap.overlay(canvas);
myMap.onChange(update);

function update() {
    var cont = 1;
    scene.clear();
    scene.add(light);
    scene.add(camera);
    controls.enablePan = false;
    controls.update();
    var points = []
    if (dataLoaded) {
        meshes.forEach((mesh, item) => {
            const pos = myMap.latLngToPixel(nos[item].lat, nos[item].lng);
            const vector = new THREE.Vector3();
            vector.set((pos.x / WIDTH) * 2 - 1, -(pos.y / HEIGHT) * 2 + 1, 0.5);
            vector.unproject(camera);
            const dir = vector.sub(camera.position).normalize();
            const distance = -camera.position.z / dir.z;
            const newPos = camera.position.clone().add(dir.multiplyScalar(distance));
            mesh.position.set(newPos.x, newPos.y, newPos.z);
            points.push(mesh.position);
            console.log(points);
            
            if (cont === 2) {
                cont = 1;
                var geometry = new THREE.TubeGeometry(
                    new THREE.CatmullRomCurve3(points),
                    1,// path segments
                    2,// THICKNESS
                    10, //Roundness of Tube
                    false //closed
                );
                tubeBufferGeomtry = new THREE.BufferGeometry().fromGeometry(geometry);
                material2.transparent=false;
                line = new THREE.Line(geometry, material2);
                line.transparent=false;
                scene.add(line);
                points = [];
            } else {
                cont++;
            }
        })
    }
}
// Animate loop
var animate = function () {
    requestAnimationFrame(animate);
    if (dataLoaded) {
        meshes.forEach(function (mesh) {
            mesh.rotation.x += 0.01;
            mesh.rotation.y += 0.01;
        })
    }
    renderer.render(scene, camera);
};

animate();