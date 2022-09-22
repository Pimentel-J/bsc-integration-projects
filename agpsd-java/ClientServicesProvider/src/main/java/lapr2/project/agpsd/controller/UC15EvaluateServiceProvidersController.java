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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import lapr2.project.agpsd.model.Company;
import lapr2.project.agpsd.model.ServiceProvider;
import lapr2.project.agpsd.model.ServiceProviderLabel;
import lapr2.project.agpsd.model.ServiceProviderRegistry;
import lapr2.project.agpsd.utils.LabelServiceProviderAlgorithm;
import lapr2.project.agpsd.utils.LabelServiceProviderAlgorithmInterface;
import lapr2.project.agpsd.utils.StatisticsLibrary;
import lapr2.project.agpsd.utils.StatisticsLibraryInterface;

/**
 *
 *
 */
public class UC15EvaluateServiceProvidersController implements Initializable, ControllerMenu {

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @Override
    public void setController(Stage stage, Company company, Scene sceneMain) {
        this.stage = stage;
        this.company = company;
        this.sceneMain = sceneMain;
    }

    public void setReferenceToUI(UC15EvaluateServiceProvidersUI uc15ui) {
        this.uc15ui = uc15ui;
        this.uc15ui.didNotConfirm = false;
    }

    private UC15EvaluateServiceProvidersUI uc15ui;
    private Stage stage;
    private Company company;
    private Scene sceneMain;
    private ServiceProvider sp;
    private ServiceProviderRegistry rsp;
    private ServiceProviderLabel label;
    private double popMean;
    private double popStdDeviation;
    private Object[][] absoluteDeviation;
    private List<ServiceProvider> listSP;

    public void showLabels() {

        if (sp != null) {
            uc15ui.didNotConfirm = true;
            uc15ui.labelListView.getItems().clear();
            for (ServiceProviderLabel labelObj : ServiceProviderLabel.values()) {
                uc15ui.labelListView.getItems().add(labelObj.toString());
            }
        }

    }

    public void setLabel(String labelID) {

        if (sp != null) {
            label = ServiceProviderLabel.fromString(labelID);
            uc15ui.labelListView.getItems().clear();
            uc15ui.labelListView.getItems().add(this.label.toString());
        }
    }

    public void setLabel() {

        if (sp != null) {
            sp.setLabel(label);
        }
    }

    public void computeAndShow() {

        rsp = company.getServiceProviderRegistry();
        listSP = rsp.getServiceProviderList();

        StatisticsLibraryInterface lib = new StatisticsLibrary(this.company);
        popMean = lib.computeServiceProviderMean(listSP);
        popStdDeviation = lib.computeServiceProviderStandardDeviation(listSP, popMean);
        absoluteDeviation = lib.computeAbsoluteDeviationFromPopulationMean(listSP, popMean);

        //Show//
        //Chart
        XYChart.Series dataSeries = new XYChart.Series();
        dataSeries.setName("Service Provider Ratings");
        for (int i = 1; i <= 5; ++i) {
            dataSeries.getData().add(new XYChart.Data(Integer.toString(i), lib.computerServiceProvidersWithSameRating(i, listSP)));
        }
        uc15ui.barChart.getData().add(dataSeries);

        //Other statistics
        uc15ui.statisticsListView.getItems().clear();
        uc15ui.statisticsListView.getItems().add("Population mean: " + popMean);
        uc15ui.statisticsListView.getItems().add("Population standard deviation: " + popStdDeviation);
        for (int i = 0; i < absoluteDeviation.length; ++i) {
            uc15ui.statisticsListView.getItems().add("Absolute deviation for Service Provider: " + ((ServiceProvider) absoluteDeviation[i][0]).toString());
            uc15ui.statisticsListView.getItems().add((double) absoluteDeviation[i][1]);
        }

        //show service providers
        for (ServiceProvider servProv : listSP) {
            uc15ui.spListView.getItems().clear();
            uc15ui.spListView.getItems().add(servProv.getRegistryNumber());
        }

    }

    public void getSPLabel(String ServiceProviderID) {

        if (rsp != null) {
            sp = rsp.getServiceProviderByID(ServiceProviderID);
            if (sp != null) {
                LabelServiceProviderAlgorithmInterface labeler = new LabelServiceProviderAlgorithm();
                label = labeler.labelServiceProvider(sp, popMean, popStdDeviation);
                uc15ui.chosenLabel.setText("You have chosen : " + label.toString() + " confirm?");
            }
        }
    }

}
