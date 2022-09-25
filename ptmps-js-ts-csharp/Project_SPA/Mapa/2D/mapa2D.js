/* -----------
Leaflet Demo with OSM
Visualizing 45,716 Meteorite Landings.
Data from NASA's Open Data Portal.(https://data.nasa.gov/Space-Science/Meteorite-Landings/gh4g-9sfh)
----------- */

var options = {
  lat: 41.15,
  lng: -8.44,
  zoom: 11,
  style: 'http://{s}.tile.osm.org/{z}/{x}/{y}.png'
}

// Create an instance of Leaflet
var mappa = new Mappa('Leaflet');
var myMap;

var canvas;
var nos, ligacoes;

function setup() {
  canvas = createCanvas(800, 700);

  // Create a tile map and overlay the canvas on top.
  myMap = mappa.tileMap(options);
  myMap.overlay(canvas);

  // Load the data
  nos = loadTable('../Data/nozinhos.csv', 'csv', 'header');
  ligacoes = loadTable('../Data/ligacoes.csv', 'csv', 'header');

  // Only redraw the meteorites when the map change and not every frame.
  myMap.onChange(drawPaths);

  fill(70, 203,31);	
  stroke(100);
}

// The draw loop is fully functional but we are not using it for now.
function draw() {}

function drawPaths() {
  // Clear the canvas
  clear();
  var cont =1;
  var points =[];
  var firstpolyline ;
  for (var i = 0; i < nos.getRowCount(); i++) {
    // Get the lat/lng of each meteorite 
    var latitude = Number(nos.getString(i, 'latitude'));
    var longitude = Number(nos.getString(i, 'longitude'));
    if (myMap.map.getBounds().contains({lat: latitude, lng: longitude})) {
      // Transform lat/lng to pixel position
      var pos = myMap.latLngToPixel(latitude, longitude);
      ellipse(pos.x, pos.y, 5, 5);
      points.push(pos);
      if(cont == 2){
        cont=1;         
        p1=points[0];
        console.log(p1);
        p2=points[1];
        line(p1.x,p1.y,p2.x,p2.y);
        points=[];
      }else{
        cont++;
      }
    }
  }
}