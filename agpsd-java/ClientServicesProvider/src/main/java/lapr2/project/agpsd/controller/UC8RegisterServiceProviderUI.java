/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr2.project.agpsd.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lapr2.project.agpsd.model.AGPSD;
import lapr2.project.agpsd.model.CategoriesRegistry;
import lapr2.project.agpsd.model.Category;
import lapr2.project.agpsd.model.Company;
import lapr2.project.agpsd.model.GeographicalArea;
import lapr2.project.agpsd.model.GeographicalAreaRegistry;
import lapr2.project.agpsd.model.ServiceProvider;
import lapr2.project.agpsd.model.Application;
import lapr2.project.agpsd.model.ApplicationRegistry;
import lapr2.project.agpsd.model.ServiceProviderRegistry;

/**
 *
 *
 */
public class UC8RegisterServiceProviderUI implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.registryNumberField.setText("registry number here");
        this.fullNameField.setText("full name here");
        this.shortNameField.setText("short name here");
        this.emailField.setText("email here");
        this.placeTextField.setText("place here");
        this.locationTextField.setText("location here");
        this.zipCodeTextField.setText("zip code here");
    }
    public void setReference(Stage stage, Company company, Scene sceneMain)  {
        this.stage = stage;
        this.company = company;
        this.sceneMain = sceneMain;
        this.confirmedRetrievedData = false;
        uc8controller = new UC8RegisterServiceProviderController();
        uc8controller.setController(stage, company, sceneMain);
        uc8controller.setReferenceToUI(this);
    }
    
    private UC8RegisterServiceProviderController uc8controller;
    
    protected Stage stage;
    protected Company company;
    protected Scene sceneMain;
    @FXML protected Button buttonLogoff;
    @FXML protected Button NIFButton;
    @FXML protected TextField NIFTextField;
    @FXML protected ListView NIFListView;
    @FXML protected ListView dataListView;    
    @FXML protected ListView categoriesListView;
    @FXML protected ListView geographicAreasListView;
    @FXML protected ListView postalAddressListView;
    @FXML protected TextField dataTextField;
    @FXML protected TextField categoriesTextField;
    @FXML protected TextField geographicAreaTextField;
    @FXML protected TextField registryNumberField;
    @FXML protected TextField fullNameField;
    @FXML protected TextField shortNameField;
    @FXML protected TextField emailField;    
    @FXML protected Label registerLabel;
    @FXML protected Button registerButton;
    @FXML protected TextField placeTextField;
    @FXML protected TextField zipCodeTextField;
    @FXML protected TextField locationTextField;
    protected boolean confirmedRetrievedData;
    
 
    @FXML protected void handleEventRegister(ActionEvent event) {
        
        uc8controller.registerServiceProvider();
        
    }
    
    
    @FXML protected void handleButtonLogoff(ActionEvent event) throws IOException {
        
        AGPSD.getInstance().doLogout();
        this.stage.setScene(this.sceneMain);
        
    }
    
    @FXML protected void handleEventInsertNIF(ActionEvent event) {
        
        this.NIFListView.getItems().clear();
        uc8controller.getServiceProviderData(this.NIFTextField.getText());
        this.NIFListView.getItems().setAll(this.NIFTextField.getText());
        
    }
    
    @FXML protected void handleConfirmation(ActionEvent event) {
        
        uc8controller.confirmRetrievedData();
        
    }
    
    @FXML protected void handleEventInsertData(ActionEvent event) {
        
        this.dataListView.getItems().clear();

       
        if (confirmedRetrievedData) {
            
            uc8controller.createNewProvider(registryNumberField.getText(), emailField.getText(), shortNameField.getText());
            this.dataListView.getItems().add("Data inserted: " + registryNumberField.getText() + emailField.getText() + shortNameField.getText());
            
        } else {

            this.dataListView.getItems().add("Data inserted: " + registryNumberField.getText() + emailField.getText() + shortNameField.getText() + fullNameField.getText());
            uc8controller.createNewProvider(registryNumberField.getText(), fullNameField.getText(), shortNameField.getText(), emailField.getText());
        
        }

    }
    
    @FXML protected void handleEventInsertPostalAddress(ActionEvent event) {
        
        uc8controller.addPostalAddress(placeTextField.getText(), zipCodeTextField.getText(), locationTextField.getText());
    
    }
    
    @FXML protected void handleEventAddCategories(ActionEvent event) {
        
        uc8controller.addCategory(categoriesTextField.getText());
        
    }
    
    @FXML protected void handleEventInsertGeographicAreas(ActionEvent event){
        
        uc8controller.addGeographicArea(geographicAreaTextField.getText());
        
    }
   
}