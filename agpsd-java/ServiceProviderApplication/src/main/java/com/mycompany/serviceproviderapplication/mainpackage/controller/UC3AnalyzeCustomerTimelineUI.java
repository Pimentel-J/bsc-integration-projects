/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.serviceproviderapplication.mainpackage.controller;

import com.mycompany.serviceproviderapplication.model.Customer;
import com.mycompany.serviceproviderapplication.model.ExecutionOrderRegistry;
import com.mycompany.serviceproviderapplication.model.Service;
import com.mycompany.serviceproviderapplication.model.ServiceProvider;
import com.mycompany.serviceproviderapplication.model.ServiceRequest;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 *
 */
public class UC3AnalyzeCustomerTimelineUI implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }   
    public void setReference(Scene sceneMain, Stage stage, ServiceProvider sp) {
        this.sceneMain = sceneMain;
        this.stage = stage;
        uc3controller = new UC3AnalyzeCustomerTimelineController();
        uc3controller.setController(sp, stage, sceneMain);
        uc3controller.setReferenceToUI(this);
    }

    
    private UC3AnalyzeCustomerTimelineController uc3controller;
    private Scene sceneMain;
    private List<ServiceRequest> orderedServiceList;
    private Stage stage;
    @FXML protected ListView customerListView;
    @FXML protected TextField customerSelectTextField;
    @FXML protected ListView timeline;
    
    
    @FXML protected void handleCustomerSelect(ActionEvent event) {
        uc3controller.showTimeline(customerSelectTextField.getText());
    }
    
    @FXML protected void showCustomerList(ActionEvent event) {
        uc3controller.showCustomerList();
    }
    @FXML protected void handleLogoff(ActionEvent event) {
        this.stage.setScene(sceneMain);
    }

}
