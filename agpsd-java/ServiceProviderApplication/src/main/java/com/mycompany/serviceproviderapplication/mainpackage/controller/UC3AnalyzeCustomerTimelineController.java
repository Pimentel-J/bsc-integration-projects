/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.serviceproviderapplication.mainpackage.controller;

import com.mycompany.serviceproviderapplication.model.Customer;
import com.mycompany.serviceproviderapplication.model.ExecutionOrderRegistry;
import com.mycompany.serviceproviderapplication.model.ServiceProvider;
import com.mycompany.serviceproviderapplication.model.ServiceRequest;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 *
 */
public class UC3AnalyzeCustomerTimelineController implements Initializable, ControllerMenu {
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }  
    @Override
    public void setController(ServiceProvider sp, Stage stage, Scene sceneMain) {
        
        this.stage = stage;
        this.sceneMain = sceneMain;
        this.sp = sp;
        
    }
    public void setReferenceToUI(UC3AnalyzeCustomerTimelineUI uc3ui) {
        this.uc3ui = uc3ui;
    }
    
    private UC3AnalyzeCustomerTimelineUI uc3ui;
    private Stage stage;
    private Scene sceneMain;
    private ServiceProvider sp;
    private ExecutionOrderRegistry reo;
    private List<Customer> customerList;
    private List<ServiceRequest> orderedServiceList;    
    
    
    public void showCustomerList() {
        
        reo = sp.getExecutionOrderRegistry();
        customerList = reo.getCustomerList();
        uc3ui.customerListView.getItems().add(customerList);
        
    }
    
    
    public void showTimeline(String customerID) {
        
        uc3ui.timeline.getItems().clear();
        orderedServiceList = reo.getOrderedServiceRequestListByCustomerID(customerID);
        if (orderedServiceList != null) {
            uc3ui.timeline.getItems().add(orderedServiceList);
        } else {
            uc3ui.timeline.getItems().add("Wrong customer inserted...");
        }
        
    }
    
}
