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
import javafx.stage.Stage;
import lapr2.project.agpsd.controller.UC4SpecifyServiceController;

/**
 * FXML Controller class
 *
 *
 */
public class SpecifyServiceScene3UI implements Initializable {

    @FXML
    private Button btnConfirm;
    @FXML
    private Button btnReturn;
    @FXML
    private Button btnCancel;
    @FXML
    private Label lblService;
    
    private Stage stage;
    private SpecifyServiceUI uc4MainUI;
    private UC4SpecifyServiceController uc4Controller;
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
        if (!uc4Controller.registerService()) {
            this.uc4MainUI.setEndText("An error occurred! Service created wasn't registered!");
        }
        this.stage.setScene(nextScene);
    }

    @FXML
    private void clickBtnReturn(ActionEvent event) {
        this.stage.setScene(previousScene);
    }

    @FXML
    private void clickBtnCancel(ActionEvent event) {
        this.uc4MainUI.finishUseCase();
    }
    
    public void setUI(Stage stage, SpecifyServiceUI uc4MainUI, 
            UC4SpecifyServiceController uc4Controller, Scene nextScene) {
        this.stage = stage;
        this.uc4MainUI = uc4MainUI;
        this.uc4Controller = uc4Controller;
        this.nextScene = nextScene;
    }
    
    public void setPreviousScene(Scene scene) {
        this.previousScene = scene;
    }
    
    public void showService() {
        this.lblService.setText(this.uc4Controller.getService().toString());
    }
    
}
