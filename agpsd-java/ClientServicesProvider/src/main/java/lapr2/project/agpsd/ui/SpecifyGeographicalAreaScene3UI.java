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

/**
 * FXML Controller class
 *
 *
 */
public class SpecifyGeographicalAreaScene3UI implements Initializable {

    @FXML
    private Button btnOk;
    @FXML
    private Label lbl;
    
    private SpecifyGeographicalAreaUI uc5MainUI;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void clickBtnOk(ActionEvent event) {
        this.uc5MainUI.finishUseCase();
    }
    
    public void setUI(SpecifyGeographicalAreaUI uc5MainUI) {
        this.uc5MainUI = uc5MainUI;
    }
    
    public void setText(String text) {
        this.lbl.setText(text);
    }
    
}
