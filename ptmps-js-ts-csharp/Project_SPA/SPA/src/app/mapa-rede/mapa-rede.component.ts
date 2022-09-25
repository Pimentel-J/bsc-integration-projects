import { Component, OnInit, ViewChild, ElementRef, HostListener } from '@angular/core';
import { No } from 'src/app/No/no';
import { NoService } from 'src/app/No/no.service';
import { Percurso } from 'src/app/Percurso/percurso'
import { PercursoService } from 'src/app/Percurso/percurso.service'
import * as GeoJSON from "geojson";
import * as THREE from "three";
import { GLTFLoader } from 'three/examples/jsm/loaders/GLTFLoader.js';
import Config from 'src/config'


declare var harp: any;

@Component({
  selector: 'mapa-rede',
  templateUrl: './mapa-rede.component.html',
  styleUrls: ['./mapa-rede.component.css']
})
export class MapaRedeComponent implements OnInit {

  @ViewChild("map", { static: false })
  private mapElement: ElementRef;

  private token: string = Config.apis.hereMapsToken;

  modelScales: Map<string, number>;

  // variaveis do mapa
  private map: any;
  mapControls: any;
  mapWidth: Number = window.innerWidth - 78;
  mapHeight: Number = 700;
  mapOptions: any;
  private mapCenterLat: Number = 41.1937898023744;
  private mapCenterLong: Number = -8.38716802227697;
  private mapInitialZoom: Number = 12;  //12

  // modo navegacao
  modoNavegacaoVar: boolean = false;
  botaoNavegacaoLabel: string = "Modo Navegação";
  incrementoTranslacao: number = 100;
  incrementoAnguloGrausRotacao: number = 10;
  incrementoRotacao = this.incrementoAnguloGrausRotacao * Math.PI / 180;
  anguloGrausRotacaoY: number = 0;

  // variaveis do autocarro 
  busObject: any;
  busObjectAnchor: any;
  busGeoPosition: any;

  nos: No[];
  percursos: Percurso[];

  percursosOffset = 1;

  @HostListener('window:resize', ['$event'])
  onResize(event) {
    this.getWindowSize();
    this.resizeMap();
  }
  @HostListener('window:keydown', ['$event'])
  handleKeyDown(event: KeyboardEvent) {
    if (this.modoNavegacaoVar) {
      if (event.code == 'KeyA') {
        
        this.virarEsquerda();
      } else if (event.code == 'KeyW') {
        
        this.emFrente();
      } else if (event.code == 'KeyD') {
        
        this.virarDireita();
      } else if (event.code == 'KeyS') {
        
        this.paraTras();
      }
    }
  }


  public constructor(private noService: NoService,
    private percursoService: PercursoService
  ) { }

  public ngOnInit() {
    this.getWindowSize();
    this.definirEscalasModelos();
  }

  public ngAfterViewInit() {

    this.mostrarMapaRede();

  }
  
    // ---- CONSTRUCAO DO MAPA ---------------

  mostrarMapaRede() {
    this.renderMap()
      .then(() => {
        setTimeout(() => {
          this.percursoService.getPercursos()
            .subscribe(percs => {
              this.percursos = percs;
              this.renderPercursos();
            });
          this.noService.getNos()
            .subscribe(nos => {
              this.nos = nos;
              this.renderNos();
            });
        }, 50);
      }
      ).then(() => {
        //this.map.update();
      }
      );
  }

  renderMap(): Promise<any> {

    var prom = new Promise((resolve) => {

      this.map = new harp.MapView({
        canvas: this.mapElement.nativeElement,
        theme: "https://unpkg.com/@here/harp-map-theme@latest/resources/berlin_tilezen_base.json",
        enableShadows: true,  //Enable shadows in the map. Shadows will only be casted on features that use the "standard" or "extruded-polygon" technique in the map theme.
        enableMixedLod: true,  //enable rendering mixed levels of detail (increases rendering performance)
        minZoomLevel: 12,
        dynamicPixelRatio: window.devicePixelRatio,
        maxNumVisibleLabels: 3,
        maxVisibleDataSourceTiles: 12
      });
      this.mapControls = new harp.MapControls(this.map);
      const ui = new harp.MapControlsUI(this.mapControls);
      this.mapElement.nativeElement.parentElement!.appendChild(ui.domElement);
      this.map.resize(this.mapWidth, this.mapHeight);
      const omvDataSource = new harp.OmvDataSource({
        baseUrl: "https://xyz.api.here.com/tiles/herebase.02",
        apiFormat: harp.APIFormat.XYZOMV,
        styleSetName: "tilezen",
        authenticationCode: this.token
      });

      this.map.addDataSource(omvDataSource);
      this.map.setCameraGeolocationAndZoom(new harp.GeoCoordinates(Number(this.mapCenterLat), Number(this.mapCenterLong)), this.mapInitialZoom);

      this.map.setFovCalculation({
        fov: 70,
        type: "fixed"
      });

      resolve();

    })
      ;

    return prom;

  }

  getWindowSize() {
    this.mapWidth = window.innerWidth - 78;
    this.mapHeight = window.innerHeight - 250;
  }

  resizeMap() {
    this.map.resize(this.mapWidth, this.mapHeight);
  }

  definirEscalasModelos() {
    this.modelScales = new Map<string, number>();
    this.modelScales.set('paragem_1', 50);
    this.modelScales.set('paragem_2', 0.75);
    this.modelScales.set('paragem_3', 20);
    this.modelScales.set('paragem_4', 50);
    this.modelScales.set('autocarro', 20);
  }

  renderPercursos() {

    var segmentosLinhas = new Map();
    var coresLinhas = new Map();

    for (var perc of this.percursos) {

      var segmentos;
      var idLinha = null;
      if (perc.idLinha != null) {
        idLinha = perc.idLinha.codigo;
        coresLinhas.set(idLinha, perc.idLinha.cor);
      } else {
        coresLinhas.set(null, 'RGB(255,255,255)');
      }

      if (segmentosLinhas.has(idLinha)) {
        segmentos = segmentosLinhas.get(idLinha);
      } else {
        segmentos = [];
      }

      var segmentosPerc = perc.segmentos;
      segmentosPerc = segmentosPerc.sort((a, b) => (a.ordem > b.ordem) ? 1 : -1);

      for (var seg of segmentosPerc) {

        var noOrigem: Array<number> = [],
          noDestino: Array<number> = [];
        noOrigem.push(seg.noOrigem.longitude);
        noOrigem.push(seg.noOrigem.latitude);
        noDestino.push(seg.noDestino.longitude);
        noDestino.push(seg.noDestino.latitude);
        var segmentoPush = { noOrigem: noOrigem, noDestino: noDestino };
        if (!this.existsInSegmentosLinhas(segmentos, segmentoPush)) {
          segmentos.push(segmentoPush);
        }

      }
      segmentosLinhas.set(idLinha, segmentos);

    }

    var segCounter = 0;
    var segmentosDesenhados = [];
    for (var linha of segmentosLinhas.keys()) {
      //this.updatePercursosOffset();
      for (var segm of segmentosLinhas.get(linha)) {
        if (this.existsInSegmentosLinhas(segmentosDesenhados, segm)) {
          console.log(segm);
          segm.noOrigem[0] = parseFloat(segm.noOrigem[0]) + this.percursosOffset * 0.001;
          segm.noOrigem[1] = parseFloat(segm.noOrigem[1]);
          segm.noDestino[0] = parseFloat(segm.noDestino[0]) + this.percursosOffset * 0.001;
          segm.noDestino[1] = parseFloat(segm.noDestino[1]);
        }

        this.drawLinhas('seg' + segCounter, coresLinhas.get(linha), [{ segmento: [[segm.noOrigem[0], segm.noOrigem[1]], [segm.noDestino[0], segm.noDestino[1]]] }]);
        segmentosDesenhados.push(segm);
        segCounter++;
      }

    }

  }

  existsInSegmentosLinhas(linhas, segPush): boolean {

    for (var seg of linhas) {
      if ((seg.noOrigem[0] == segPush.noOrigem[0] && seg.noOrigem[1] == segPush.noOrigem[1] && seg.noDestino[0] == segPush.noDestino[0] && seg.noDestino[1] == segPush.noDestino[1])
        || (seg.noOrigem[0] == segPush.noDestino[0] && seg.noOrigem[1] == segPush.noDestino[1] && seg.noDestino[0] == segPush.noOrigem[0] && seg.noDestino[1] == segPush.noOrigem[1])) {
        return true;
      }
    }

    return false;

  }

  updatePercursosOffset() {
    //if (this.percursosOffset == 1) {
    //  this.percursosOffset = 0;
    //} else if (this.percursosOffset == -1) {
    //  this.percursosOffset = 1;
    //} else { //=0
    //  this.percursosOffset = -1;
    //}
    this.percursosOffset++;
  }

  public drawLinhas(name: string, cor: string, positions: any[]) {
    const geoJsonDataProvider = new harp.GeoJsonDataProvider(name, this.criarLinhas(positions));
    const geoJsonDataSource = new harp.OmvDataSource({
      dataProvider: geoJsonDataProvider,
      name: name
    });
    this.map.addDataSource(geoJsonDataSource).then(() => {
      const styles = [{
        when: "$geometryType == 'line'",
        technique: "solid-line",
        renderOrder: 10000,
        attr: {
          color: cor,
          opacity: 1,
          metricUnit: "Meter",
          lineWidth: 50
        }
      }];
      geoJsonDataSource.setStyleSet(styles);
      //this.map.update();
    });

  }

  public criarLinhas(positions: any[]): any {
    return GeoJSON.parse(positions, { LineString: "segmento" });
    //return GeoJSON.parse(positions, { Point: ["lat", "lng"] });
  }

  public renderNos() {

    for (var no of this.nos) {

      var positions = new harp.GeoCoordinates(no.latitude, no.longitude, 0);

      const geoPosition = positions;
      if (geoPosition === null) {
        return;
      }

      if (no.modeloMapa == null) {
        // Se um modelo3D não está definido para este nó, carrega o nó default (cilindro)
        const noObject = this.createNo();
        console.log(noObject);
        var noObjectAnchor = harp.MapAnchor;
        noObjectAnchor = noObject;
        noObjectAnchor.anchor = geoPosition;
        this.map.mapAnchors.add(noObjectAnchor);

      } else {
        // Carrega o modelo 3D definido no No
        this.createNoModel(geoPosition, no.modeloMapa);
      }


      this.inserirLabel(no.abreviatura, [{ lat: no.latitude, lng: no.longitude }]);

    }

    //this.map.update();

  }

  createNo() {

    //const geometry = new THREE.BoxGeometry(1 * scale, 1 * scale, 1 * scale);
    const geometry = new THREE.CylinderGeometry(200, 200, 50, 32).rotateX(Math.PI / 2);
    const prePassMaterial = new THREE.MeshStandardMaterial({
      color: "#ac403e",
      opacity: 0.3,
      depthTest: false,
      transparent: true
    });
    const material = new THREE.MeshStandardMaterial({
      color: "#ac403e",
      opacity: 1,
      transparent: true
    });

    const no = new THREE.Object3D();

    const prePassMesh = new THREE.Mesh(geometry, prePassMaterial);
    prePassMesh.renderOrder = Number.MAX_SAFE_INTEGER - 1;
    no.add(prePassMesh);

    const mesh = new THREE.Mesh(geometry, material);
    mesh.renderOrder = Number.MAX_SAFE_INTEGER;
    no.add(mesh);
    return no;
  }

  async createNoModel(geoPosition, modeloMapa) {

    const loader = new GLTFLoader();
    var object: THREE.Object3D;

    await new Promise(resolve => {
      loader.load('assets/' + modeloMapa + '/scene.gltf', function (gltf) {
        object = gltf.scene;
        resolve();
      }, undefined, function (error) {
        console.error(error);
        resolve();
      });
    });

    const noObject = object;
    const escala = this.modelScales.get(modeloMapa);
    noObject.renderOrder = 10000;
    noObject.scale.setScalar(escala);
    noObject.position.set(1000, -1000, 0);
    noObject.rotation.set(Math.PI / 2, 0, 0);
    console.log(noObject);
    var noObjectAnchor = harp.MapAnchor;
    noObjectAnchor = noObject;
    noObjectAnchor.anchor = geoPosition;
    this.map.mapAnchors.add(noObjectAnchor);
  }

  public inserirLabel(name: string, positions: any[]) {
    const geoJsonDataProvider = new harp.GeoJsonDataProvider(name, this.criarPontos(positions));
    const geoJsonDataSource = new harp.OmvDataSource({
      dataProvider: geoJsonDataProvider,
      name: name
    });
    this.map.addDataSource(geoJsonDataSource).then(() => {
      const styles = [{
        when: "$geometryType == 'point'",
        technique: "labeled-icon",
        renderOrder: 1000,
        attr: {
          color: "#000000",
          size: 15,
          text: name,
          opacity: 1,
          yOffset: -25,
          mayOverlap: true
        }
      }];
      geoJsonDataSource.setStyleSet(styles);
      //this.map.update();
    });
  }

  public criarPontos(positions: any[]): any {
    return GeoJSON.parse(positions, { Point: ["lat", "lng"] });
  }


  // ---- NAVEGACAO COM AUTOCARRO ---------------

  virarEsquerda() {
    // atualiza o angulo de rotacao em torno do eixo zz
    this.anguloGrausRotacaoY = this.anguloGrausRotacaoY + this.incrementoAnguloGrausRotacao;
    // incrementa rotacao ao objeto autocarro
    this.busObject.rotation.y = this.busObject.rotation.y + this.incrementoAnguloGrausRotacao * Math.PI / 180;
    // incrementa rotacao (orbit) à camara (sentido contrário da rotação do autocarro)
    this.mapOptions.heading = this.mapOptions.heading - this.incrementoAnguloGrausRotacao;
    // a camara orbita em torno do autocarro
    this.mapOptions.target = this.busGeoPosition;
    // aplica alterações ao MapView
    this.map.lookAt(this.mapOptions);
  }

  //roda a camara para a direita
  virarDireita() {
    // atualiza o angulo de rotacao em torno do eixo zz
    this.anguloGrausRotacaoY = this.anguloGrausRotacaoY - this.incrementoAnguloGrausRotacao;
    // incrementa rotacao ao objeto autocarro
    this.busObject.rotation.y = this.busObject.rotation.y - this.incrementoAnguloGrausRotacao * Math.PI / 180;
    // incrementa rotacao (orbit) à camara (sentido contrário da rotação do autocarro)
    this.mapOptions.heading = this.mapOptions.heading + this.incrementoAnguloGrausRotacao;
    // a camara orbita em torno do autocarro
    this.mapOptions.target = this.busGeoPosition;
    // aplica alterações ao MapView
    this.map.lookAt(this.mapOptions);
  }

  emFrente() {
    // aplica translação à camara, incrementando a sua posição no plano xOy tendo em consideração o ângulo de rotação 
    this.map.camera.position.y = this.map.camera.position.y + this.incrementoTranslacao * Math.cos(this.anguloGrausRotacaoY * Math.PI / 180);
    this.map.camera.position.x = this.map.camera.position.x - this.incrementoTranslacao * Math.sin(this.anguloGrausRotacaoY * Math.PI / 180);
    // atualiza a posicao do objeto, para ficar sempre à mesma distância da câmara
    this.atualizarPosicaoAutocarro();
  }

  paraTras() {
    // aplica translação à camara, incrementando a sua posição no plano xOy tendo em consideração o ângulo de rotação 
    this.map.camera.position.y = this.map.camera.position.y - this.incrementoTranslacao * Math.cos(this.anguloGrausRotacaoY * Math.PI / 180);
    this.map.camera.position.x = this.map.camera.position.x + this.incrementoTranslacao * Math.sin(this.anguloGrausRotacaoY * Math.PI / 180);
    // atualiza a posicao do objeto, para ficar sempre à mesma distância da câmara
    this.atualizarPosicaoAutocarro();
  }

  modoNavegacao() {

    // comuta de estado
    this.modoNavegacaoVar = !this.modoNavegacaoVar;

    if (this.modoNavegacaoVar) {
      this.botaoNavegacaoLabel = "Sair Navegação";
      this.mapOptions = {
        target: this.busGeoPosition,
        tilt: 80,
        heading: 0,
        globe: false,
        //headingSpeed: 1
      };
      this.map.tilt = 80; // inclinação 3D do mapa
      this.mapControls.enabled = false; // desativar controlos de zoom, orbit, etc
      this.map.zoomLevel = 15;  // definir zoom da camara
      this.map.heading = 0; // reset do angulo de rotação (em relação ao Norte)
      this.anguloGrausRotacaoY = 0; // reset ao engulo de rotação (variavel)
      this.createBusModel().then(() => this.atualizarPosicaoAutocarro()); // cria novo modelo do autocarro e coloca-o na cena
      this.map.update();  // render mapa com novas configurações
    } else {
      this.botaoNavegacaoLabel = "Modo Navegação";
      this.map.tilt = 0;
      this.mapControls.enabled = true;
      this.map.zoomLevel = 14;
      this.map.heading = 0;
      this.map.mapAnchors.remove(this.busObjectAnchor); // remove autocarro da cena
      this.map.update();
    }
  }

  atualizarPosicaoAutocarro() {

    var currentPosition = this.map.getGeoCoordinatesAt(<number>this.mapWidth / 2, <number>this.mapHeight / 2);
    var positions = new harp.GeoCoordinates(currentPosition.latitude, currentPosition.longitude, 0);
    this.busGeoPosition = positions;
    if (this.busGeoPosition === null) {
      return;
    }
    this.busObjectAnchor.anchor = this.busGeoPosition;
    this.map.update();
  }

  async createBusModel() {

    const loader = new GLTFLoader();
    var object: THREE.Object3D;

    await new Promise(resolve => {
      loader.load('assets/autocarro/scene.gltf', function (gltf) {
        object = gltf.scene;
        resolve();
      }, undefined, function (error) {
        console.error(error);
        resolve();
      });
    });

    this.busObject = object;
    const escala = this.modelScales.get("autocarro");
    this.busObject.renderOrder = 10000;
    this.busObject.scale.setScalar(escala);
    this.busObject.rotation.set(Math.PI / 2, Math.PI / 2, 0);
    this.busObjectAnchor = harp.MapAnchor;
    this.busObjectAnchor = this.busObject;
    this.busObjectAnchor.anchor = this.busGeoPosition;
    this.map.mapAnchors.add(this.busObjectAnchor);
  }

}
