/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.serviceproviderapplication.ui;

import com.mycompany.serviceproviderapplication.mainpackage.controller.UC2AnalyzeExecutionOrdersController;
import com.mycompany.serviceproviderapplication.model.DescribingField;
import com.mycompany.serviceproviderapplication.model.ExecutionOrder;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 *
 */
public class UC2AnalyzeExecutionOrdersUI implements Initializable {

    @FXML
    private ChoiceBox<DescribingField> chBoxCriteria;
    @FXML
    private Button btnSort;
    @FXML
    private Button btnFinish;
    @FXML
    private ListView<ExecutionOrder> lstViewOrders;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    private Stage mainStage;
    private Scene mainMenu;
    private UC2AnalyzeExecutionOrdersController controller;
    
    @FXML
    private void clickBtnSort(ActionEvent event) {
        DescribingField criteria = chBoxCriteria.getValue();
        if (criteria != null) {
            lstViewOrders.getItems().setAll(controller.sortExecutionOrders(criteria));
        }
    }

    @FXML
    private void clickBtnFinish(ActionEvent event) {
        this.mainStage.setScene(mainMenu);
    }
    
    public void setController(Stage stage, Scene scene) {
        this.mainStage = stage;
        this.mainMenu = scene;
        this.controller = new UC2AnalyzeExecutionOrdersController();
        chBoxCriteria.getItems().setAll(DescribingField.values());
        lstViewOrders.getItems().setAll(controller.getExecutionOrders());
    }
    
}
