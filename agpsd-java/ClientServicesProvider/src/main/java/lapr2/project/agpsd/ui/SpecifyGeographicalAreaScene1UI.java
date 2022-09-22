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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lapr2.project.agpsd.controller.UC5SpecifyGeographicalAreaController;

/**
 * FXML Controller class
 *
 *
 */
public class SpecifyGeographicalAreaScene1UI implements Initializable {

    @FXML
    private Button btnNext;
    @FXML
    private Button btnCancel;
    @FXML
    private TextField txtDesig;
    @FXML
    private TextField txtZipCode;
    @FXML
    private TextField txtRadius;
    @FXML
    private TextField txtCost;
    
    private Stage stage;
    private SpecifyGeographicalAreaUI uc5MainUI;
    private UC5SpecifyGeographicalAreaController uc5Controller;
    private Scene nextScene;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void clickBtnNext(ActionEvent event) {
        try {
            uc5Controller.newGeographicalArea(
                   txtDesig.getText(), 
                   Double.parseDouble(txtCost.getText()), 
                   txtZipCode.getText(), 
                   Double.parseDouble(txtRadius.getText()));
            
            this.uc5MainUI.showGeographicalArea();
            this.stage.setScene(nextScene);
        
        } catch (IllegalArgumentException iae) {
            uc5MainUI.createAlert(Alert.AlertType.ERROR, "Data invalid!", "Correct the inserted data please").show();
        }
    }

    @FXML
    private void clickBtnCancel(ActionEvent event) {
        this.uc5MainUI.finishUseCase();
    }
    
    public void setUI(Stage stage, SpecifyGeographicalAreaUI uc5MainUI, 
            UC5SpecifyGeographicalAreaController uc5Controller, Scene nextScene) {
        this.stage = stage;
        this.uc5MainUI = uc5MainUI;
        this.uc5Controller = uc5Controller;
        this.nextScene = nextScene;
    }    
    
}
