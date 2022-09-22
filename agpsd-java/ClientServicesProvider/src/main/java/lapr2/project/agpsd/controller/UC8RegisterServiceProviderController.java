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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lapr2.project.agpsd.model.Application;
import lapr2.project.agpsd.model.ApplicationRegistry;
import lapr2.project.agpsd.model.CategoriesRegistry;
import lapr2.project.agpsd.model.Category;
import lapr2.project.agpsd.model.Company;
import lapr2.project.agpsd.model.GeographicalArea;
import lapr2.project.agpsd.model.GeographicalAreaRegistry;
import lapr2.project.agpsd.model.ServiceProvider;
import lapr2.project.agpsd.model.ServiceProviderRegistry;

/**
 *
 *
 */
public class UC8RegisterServiceProviderController implements Initializable, ControllerMenu {

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        categoryList = new ArrayList<Category>();
    }

    @Override
    public void setController(Stage stage, Company company, Scene sceneMain) {
        this.stage = stage;
        this.company = company;
        this.sceneMain = sceneMain;
        this.spApp = null;
    }

    public void setReferenceToUI(UC8RegisterServiceProviderUI uc8ui) {
        this.uc8ui = uc8ui;
    }

    private UC8RegisterServiceProviderUI uc8ui;
    protected Stage stage;
    protected Company company;
    protected Scene sceneMain;
    private ApplicationRegistry ra;
    private Application spApp;
    private String fullName;
    private String shortName;
    private List<Category> categoryList;
    private ServiceProviderRegistry rsp;
    private String email;
    private int dataEntered;
    private String registryNumber;
    private ServiceProvider sp;
    private CategoriesRegistry rcat;
    private List<Category> catList;
    private GeographicalAreaRegistry rga;

    public void registerServiceProvider() {

        if (rsp != null && sp != null) {
            rsp.registerServiceProvider();
            if (rsp.validate()) {
                uc8ui.registerLabel.setText("Success!");
            } else {
                uc8ui.registerLabel.setText("Insuccessful registration");
            }
        }
    }

    public void addGeographicArea(String idGA) {

        if (rga != null) {
            GeographicalArea ga = rga.getGeographicalAreaByID(idGA);
            sp.addGeographicalArea(ga);
        }

    }

    public void addPostalAddress(String place, String zipCode, String location) {

        if (sp != null) {
            sp.addPostalAddress(place, zipCode, location);
            if (sp.getPostalAddress() != null) {
                uc8ui.postalAddressListView.getItems().clear();
                uc8ui.postalAddressListView.getItems().add("Introduced");
            } else {
                uc8ui.postalAddressListView.getItems().clear();
                uc8ui.postalAddressListView.getItems().add("Invalid address...");
            }

            rga = this.company.getGeographicalAreaRegistry();
            List<GeographicalArea> geographicalAreaList = rga.getGeographicalAreaList();
            uc8ui.geographicAreasListView.getItems().clear();
            uc8ui.geographicAreasListView.getItems().add(geographicalAreaList);
        }
    }

    public void addCategory(String idCat) {

        Category cat = rcat.getCategoryById(idCat);
        sp.addCategory(cat);

    }

    public void createNewProvider(String registryNumber, String email, String shortName) {

        if (rsp != null) {
            sp = rsp.newServiceProvider(fullName, shortName, registryNumber, email);

            if (!uc8ui.confirmedRetrievedData || spApp == null) {

                uc8ui.categoriesListView.getItems().clear();
                rcat = company.getCategoriesRegistry();
                catList = rcat.getCategories();
                uc8ui.categoriesListView.getItems().add(catList);

            } else {

                sp.addCategoryList(catList);

            }
        }

    }

    public void createNewProvider(String registryNumberStr, String fullNameStr, String shortNameStr, String emailStr) {

        this.fullName = fullNameStr;
        createNewProvider(registryNumberStr, emailStr, shortNameStr);

    }

    public void confirmRetrievedData() {

        if (spApp != null) {

            uc8ui.fullNameField.setText(fullName);
            uc8ui.confirmedRetrievedData = true;

        } else {

            uc8ui.NIFListView.getItems().clear();
            uc8ui.NIFListView.getItems().add("No data to confirm...");

        }

    }

    public void getServiceProviderData(String NIF) {

        ra = this.company.getApplicationRegistry();
        spApp = ra.getApplicationByNIF(NIF);

        if (spApp != null) {

            fullName = spApp.getFullName();
            uc8ui.dataListView.getItems().setAll(fullName);

            categoryList = spApp.getCategoryList();
            uc8ui.categoriesListView.getItems().setAll(categoryList);

        }

        rsp = company.getServiceProviderRegistry();

    }

}
