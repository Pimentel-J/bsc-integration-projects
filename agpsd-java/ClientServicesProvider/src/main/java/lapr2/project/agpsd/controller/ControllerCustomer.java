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
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import lapr2.project.agpsd.model.AGPSD;
import lapr2.project.agpsd.model.Company;

/**
 *
 *
 */
public class ControllerCustomer implements Initializable, ControllerMenu {
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
    @FXML private Button buttonLogoff;
    
    @FXML protected void handleButtonLogoff(ActionEvent event) throws IOException {
        AGPSD.getInstance().doLogout();
        this.stage.setScene(this.sceneMain);
    }
    
}
