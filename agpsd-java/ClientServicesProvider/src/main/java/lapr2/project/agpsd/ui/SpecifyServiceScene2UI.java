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
import lapr2.project.agpsd.controller.UC4SpecifyServiceController;

/**
 * FXML Controller class
 *
 *
 */
public class SpecifyServiceScene2UI implements Initializable {

    @FXML
    private Button btnNext;
    @FXML
    private Button btnReturn;
    @FXML
    private Button btnCancel;
    @FXML
    private TextField txtField;
    
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
    private void clickBtnNext(ActionEvent event) {
        try {
            this.uc4Controller.setAdditionalData(Double.parseDouble(txtField.getText()));
            if (this.uc4Controller.validate()) {
                this.uc4MainUI.showService();
                this.stage.setScene(nextScene);
            } else {
                uc4MainUI.createAlert(Alert.AlertType.ERROR, "Data invalid!", "Identification Code must be unique!").show();
            }
        } catch (Exception e) {
            uc4MainUI.createAlert(Alert.AlertType.ERROR, "Data invalid!", "Please correct the inserted data").show();
        }
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
            UC4SpecifyServiceController uc4Controller, Scene previousScene, Scene nextScene) {
        this.stage = stage;
        this.uc4MainUI = uc4MainUI;
        this.uc4Controller = uc4Controller;
        this.previousScene = previousScene;
        this.nextScene = nextScene;
    }
    
}
