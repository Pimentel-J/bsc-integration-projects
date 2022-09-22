/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr2.project.agpsd.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import lapr2.project.agpsd.model.AGPSD;
import lapr2.project.agpsd.model.Company;
import lapr2.project.agpsd.ui.SpecifyGeographicalAreaUI;
import lapr2.project.agpsd.ui.SpecifyServiceScene1UI;
import lapr2.project.agpsd.ui.SpecifyServiceUI;

/**
 *
 *
 */
public class ControllerAdministrator implements Initializable, ControllerMenu {
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    @Override
    public void setController(Stage stage, Company company, Scene sceneMain) {
        this.stage = stage;
        this.company = company;
        this.sceneMain = sceneMain;
    }
    
    private Stage stage;
    private Company company;
    private Scene sceneMain;
    
    @FXML 
    private Button buttonLogoff;
    @FXML
    private Button btnUC3;
    @FXML
    private Button btnUC4;
    @FXML
    private Button btnUC5;
    
    @FXML protected void handleButtonLogoff(ActionEvent event) throws IOException {
        AGPSD.getInstance().doLogout();
        this.stage.setScene(this.sceneMain);
    }
    
    @FXML
    private void clickBtnUC3(ActionEvent event) {
    }
    
    @FXML
    private void clickBtnUC4(ActionEvent event) {
        SpecifyServiceUI uc4UI = new SpecifyServiceUI(stage, ((Node) event.getSource()).getScene());
        this.stage.setScene(uc4UI.getFirstScene());
    }
    
    @FXML
    private void clickBtnUC5(ActionEvent event) {
        SpecifyGeographicalAreaUI uc5UI = new SpecifyGeographicalAreaUI(stage, ((Node) event.getSource()).getScene());
        this.stage.setScene(uc5UI.getFirstScene());
    }
    
}
