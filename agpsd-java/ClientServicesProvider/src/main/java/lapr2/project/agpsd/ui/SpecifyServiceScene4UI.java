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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import lapr2.project.agpsd.controller.UC4SpecifyServiceController;

/**
 * FXML Controller class
 *
 *
 */
public class SpecifyServiceScene4UI implements Initializable {

    @FXML
    private Button btnOk;
    @FXML
    private Label lbl;
    
    private Stage stage;
    private SpecifyServiceUI uc4MainUI;
    private UC4SpecifyServiceController uc4Controller;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void clickBtnOk(ActionEvent event) {
        this.uc4MainUI.finishUseCase();
    }
    
    public void setText(String text) {
        this.lbl.setText(text);
    }
    
    public void setUI(Stage stage, SpecifyServiceUI uc4MainUI, 
            UC4SpecifyServiceController uc4Controller) {
        this.stage = stage;
        this.uc4MainUI = uc4MainUI;
        this.uc4Controller = uc4Controller;
    }
    
}
