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
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lapr2.project.agpsd.controller.UC4SpecifyServiceController;
import lapr2.project.agpsd.model.Category;
import lapr2.project.agpsd.model.ServiceType;

/**
 * FXML Controller class
 *
 *
 */
public class SpecifyServiceScene1UI implements Initializable {

    @FXML
    private Button btnNext;
    @FXML
    private Button btnCancel;
    @FXML
    private TextField txtCode;
    @FXML
    private TextField txtShortDesc;
    @FXML
    private TextField txtLongDesc;
    @FXML
    private TextField txtCost;
    @FXML
    private ChoiceBox<ServiceType> chBoxServType;
    @FXML
    private ComboBox<Category> cmbBoxCategory;
    
    private Stage stage;
    private SpecifyServiceUI uc4MainUI;
    private UC4SpecifyServiceController uc4Controller;
    private Scene uc4Scene2;
    private Scene uc4Scene3;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void clickBtnNext(ActionEvent event) {
        try {
            uc4Controller.setServiceType((ServiceType) chBoxServType.getValue());
            boolean otherAttr = uc4Controller.newService(txtCode.getText(), 
                    txtShortDesc.getText(), txtLongDesc.getText(), 
                    Double.parseDouble(txtCost.getText()), (Category) cmbBoxCategory.getValue());
            if (otherAttr) {
                this.uc4MainUI.setPreviousSceneInScene3(uc4Scene2);
                this.stage.setScene(uc4Scene2);
            } else {
                if (this.uc4Controller.validate()) {
                    this.uc4MainUI.setPreviousSceneInScene3(((Node) event.getSource()).getScene());
                    this.uc4MainUI.showService();
                    this.stage.setScene(uc4Scene3);
                } else {
                    uc4MainUI.createAlert(Alert.AlertType.ERROR, "Data invalid!", "Identification Code must be unique!").show();
                }
            }
        
        } catch(NullPointerException npe) {
            uc4MainUI.createAlert(Alert.AlertType.ERROR, "Data invalid!", "Correct the inserted data please").show();
        
        } catch(NumberFormatException nfe) {
            uc4MainUI.createAlert(Alert.AlertType.ERROR, "Cost / hour should be a number!", "Correct the inserted data please").show();
        }
        
    }

    @FXML
    private void clickBtnCancel(ActionEvent event) {
        this.uc4MainUI.finishUseCase();
    }
    
    public void setUI(Stage stage, SpecifyServiceUI uc4MainUI, 
            UC4SpecifyServiceController uc4Controller, Scene uc4Scene2, Scene uc4Scene3) {
        this.stage = stage;
        this.uc4MainUI = uc4MainUI;
        this.uc4Controller = uc4Controller;
        this.uc4Scene2 = uc4Scene2;
        this.uc4Scene3 = uc4Scene3;
        chBoxServType.getItems().setAll(uc4Controller.getServiceTypes());
        cmbBoxCategory.getItems().setAll(uc4Controller.getCategories());
    }
    
}
