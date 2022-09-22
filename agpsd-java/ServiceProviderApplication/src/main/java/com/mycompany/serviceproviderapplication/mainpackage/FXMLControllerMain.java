package com.mycompany.serviceproviderapplication.mainpackage;

import com.mycompany.serviceproviderapplication.mainpackage.controller.UC3AnalyzeCustomerTimelineController;
import com.mycompany.serviceproviderapplication.mainpackage.controller.UC3AnalyzeCustomerTimelineUI;
import com.mycompany.serviceproviderapplication.model.ServiceProvider;
import com.mycompany.serviceproviderapplication.ui.UC2AnalyzeExecutionOrdersUI;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FXMLControllerMain implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    private Stage stage;
    private Scene sceneMain;
    private ServiceProvider sp;
    @FXML
    private Button buttonUC1;
    @FXML
    private Button buttonUC2;
    @FXML
    private Button buttonUC3;

    @FXML
    protected void handleUC1(ActionEvent event) {

    }

    @FXML
    protected void handleUC2(ActionEvent event) {
        runUC2();
    }

    @FXML
    protected void handleUC3(ActionEvent event) throws IOException {
        runUC3();
    }

    public void runUC2() {
        try {
            FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/fxml/UC2AnalyzeExecutionOrdersScene.fxml"));
            Parent root = loader2.load();
            Scene scene = new Scene(root);
            UC2AnalyzeExecutionOrdersUI sceneUI = loader2.getController();
            stage.setScene(scene);
            sceneUI.setController(stage, sceneMain);

        } catch (IOException e) {

            System.out.println(e);
            e.printStackTrace();
        }
    }

    public void runUC3() throws IOException {
        try {
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/UC3AnalyzeCustomerTimelineMenu.fxml"));
            Parent root = loader.load();
            Scene sceneUc3 = new Scene(root);
            UC3AnalyzeCustomerTimelineUI uc3ui = loader.getController();
            this.stage.setScene(sceneUc3);
            uc3ui.setReference(this.sceneMain, this.stage, this.sp);
            
        } catch (IOException e) {

            System.out.println(e);
            e.printStackTrace();
        }
    }

    public void setMainController(ServiceProvider sp, Stage stage, Scene sceneMain) {

        this.stage = stage;
        this.sceneMain = sceneMain;
        this.sp = sp;

    }

    //UC1
}
