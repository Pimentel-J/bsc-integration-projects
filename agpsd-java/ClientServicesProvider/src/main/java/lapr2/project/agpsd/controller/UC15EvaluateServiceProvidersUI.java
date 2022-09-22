/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr2.project.agpsd.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lapr2.project.agpsd.model.AGPSD;
import lapr2.project.agpsd.model.Company;
import lapr2.project.agpsd.model.ServiceProviderLabel;
import lapr2.project.agpsd.model.ServiceProvider;
import lapr2.project.agpsd.model.ServiceProviderRegistry;
import lapr2.project.agpsd.utils.LabelServiceProviderAlgorithm;
import lapr2.project.agpsd.utils.StatisticsLibrary;

/**
 *
 *
 */
public class UC15EvaluateServiceProvidersUI implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void setReference(Stage stage, Company company, Scene sceneMain) {
        this.stage = stage;
        this.sceneMain = sceneMain;
        this.didNotConfirm = false;
        this.uc15controller = new UC15EvaluateServiceProvidersController();
        uc15controller.setController(stage, company, sceneMain);
        uc15controller.setReferenceToUI(this);
    }

    @FXML
    protected void handleChooseServiceProvider(ActionEvent event) {
        uc15controller.getSPLabel(chooseServiceProviderTextField.getText());
    }

    @FXML
    protected void handleConfirmLabel(ActionEvent event) {

        if (didNotConfirm) {
            uc15controller.setLabel(chooseLabelTextField.getText());
        } else {
            uc15controller.setLabel();
        }
    }

    @FXML
    protected void handleDeleteLabel(ActionEvent event) {
        chooseLabelTextField.clear();
        uc15controller.showLabels();
    }

    @FXML
    protected void handleLogoffButton(ActionEvent event) throws IOException {
        AGPSD.getInstance().doLogout();
        this.stage.setScene(this.sceneMain);
    }

    @FXML
    protected void handleShow(ActionEvent event) {
        uc15controller.computeAndShow();
    }

    private Stage stage;
    private Scene sceneMain;
    @FXML
    protected TextField chooseServiceProviderTextField;
    @FXML
    protected TextField chooseLabelTextField;
    @FXML
    protected ListView spListView;
    @FXML
    protected ListView labelListView;
    @FXML
    protected Button chooseServiceProviderButton;
    @FXML
    protected Label chosenLabel;
    @FXML
    protected BarChart barChart;
    @FXML
    protected ListView statisticsListView;
    private UC15EvaluateServiceProvidersController uc15controller;
    protected boolean didNotConfirm;

}
