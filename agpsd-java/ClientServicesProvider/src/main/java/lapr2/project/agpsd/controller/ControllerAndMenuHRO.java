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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import lapr2.project.agpsd.model.AGPSD;
import lapr2.project.agpsd.model.Company;
import lapr2.project.agpsd.utils.Utils;

/**
 *
 *
 */
public class ControllerAndMenuHRO implements Initializable, ControllerMenu {

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
    @FXML private Button buttonUC8;
    @FXML private Button buttonUC15;

    @FXML
    protected void handleButtonLogoff(ActionEvent event) throws IOException {
        AGPSD.getInstance().doLogout();
        this.stage.setScene(this.sceneMain);
    }

    @FXML
    protected void handleButtonUC8(ActionEvent event) throws IOException {
        runUC8();    
    }
    
    @FXML
    protected void handleButtonUC15(ActionEvent event) throws IOException {

        runUC15();
        
    }
    
    public void runUC15() {
        
            try {
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/UC15EvaluateServiceProviders.fxml"));
            Parent root = loader.load();
            Scene sceneUc15 = new Scene(root);
            UC15EvaluateServiceProvidersUI uc15ui = loader.getController();
            this.stage.setScene(sceneUc15);
            uc15ui.setReference(this.stage, this.company, this.sceneMain);
            
        } catch (IOException e) {

            System.out.println(e);
            e.printStackTrace();
            
        }    
        
    }
    
    public void runUC8() {
        
        try {
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/UC8RegisterServiceProvider.fxml"));
            Parent root = loader.load();
            Scene sceneUc8 = new Scene(root);
            UC8RegisterServiceProviderUI uc8ui = loader.getController();
            this.stage.setScene(sceneUc8);
            uc8ui.setReference(this.stage, this.company, this.sceneMain);
            
        } catch (IOException e) {

            System.out.println(e);
            e.printStackTrace();
        }
        
    }
    
}
