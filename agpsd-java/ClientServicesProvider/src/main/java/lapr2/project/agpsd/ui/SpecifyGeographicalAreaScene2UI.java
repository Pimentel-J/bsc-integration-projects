/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr2.project.agpsd.ui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import lapr2.project.agpsd.controller.UC5SpecifyGeographicalAreaController;
import lapr2.project.agpsd.model.ActsOn;

/**
 * FXML Controller class
 *
 *
 */
public class SpecifyGeographicalAreaScene2UI implements Initializable {

    @FXML
    private Button btnConfirm;
    @FXML
    private Button btnReturn;
    @FXML
    private Button btnCancel;
    @FXML
    private Label lblGeoArea;
    @FXML
    private ListView<ActsOn> lstVwZipCodes;
    
    private Stage stage;
    private SpecifyGeographicalAreaUI uc5MainUI;
    private UC5SpecifyGeographicalAreaController uc5Controller;
    private Scene previousScene;
    private Scene nextScene;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void clickBtnConfirm(ActionEvent event) {
        if (!uc5Controller.registerGeographicalArea()) {
            this.uc5MainUI.setEndText("An error occurred! Geographical Area created wasn't registered!");
        }
        this.stage.setScene(nextScene);
    }

    @FXML
    private void clickBtnReturn(ActionEvent event) {
        this.stage.setScene(previousScene);
    }

    @FXML
    private void clickBtnCancel(ActionEvent event) {
        this.uc5MainUI.finishUseCase();
    }
    
    public void setUI(Stage stage, SpecifyGeographicalAreaUI uc5MainUI, 
            UC5SpecifyGeographicalAreaController uc5Controller, Scene previousScene, Scene nextScene) {
        this.stage = stage;
        this.uc5MainUI = uc5MainUI;
        this.uc5Controller = uc5Controller;
        this.previousScene = previousScene;
        this.nextScene = nextScene;
    }
    
    public void showGeographicalArea() {
        this.lblGeoArea.setText(this.uc5Controller.getGeographicalArea().toString());
        this.lstVwZipCodes.getItems().setAll(this.uc5Controller.getGeographicalArea().getActsOnList());
    }
    
}
