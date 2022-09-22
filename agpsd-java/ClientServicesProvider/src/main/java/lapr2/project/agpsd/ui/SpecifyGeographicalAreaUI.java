/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr2.project.agpsd.ui;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import lapr2.project.agpsd.controller.UC5SpecifyGeographicalAreaController;

/**
 *
 *
 */
public class SpecifyGeographicalAreaUI {
    
    private final Stage mainStage;
    private final Scene menuAdminScene;
    private final UC5SpecifyGeographicalAreaController controller;
    private Scene scene1;
    private Scene scene2;
    private Scene scene3;
    private SpecifyGeographicalAreaScene1UI scene1UI;
    private SpecifyGeographicalAreaScene2UI scene2UI;
    private SpecifyGeographicalAreaScene3UI scene3UI;
    
    public SpecifyGeographicalAreaUI(Stage stage, Scene scene) {
        this.mainStage = stage;
        this.menuAdminScene = scene;
        this.controller = new UC5SpecifyGeographicalAreaController();
        createScenes();
    }
    
    public Scene getFirstScene() {
        return scene1;
    }
    
    public void finishUseCase() {
        this.mainStage.setScene(menuAdminScene);
    }
    
    public Alert createAlert(Alert.AlertType alertType, String header,
            String message) {
        Alert alert = new Alert(alertType);

        alert.setTitle("Specify Geographical Area");
        alert.setHeaderText(header);
        alert.setContentText(message);

        return alert;
    }
    
    public void showGeographicalArea() {
        this.scene2UI.showGeographicalArea();
    }
    
    public void setEndText(String text) {
        scene3UI.setText(text);
    }
    
    private void createScenes() {
        uploadScene1();
        uploadScene2();
        uploadScene3();
        scene1UI.setUI(mainStage, this, controller, scene2);
        scene2UI.setUI(mainStage, this, controller, scene1, scene3);
        scene3UI.setUI(this);
    }
    
    private void uploadScene1() {
        try {
            FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/fxml/SpecifyGeographicalAreaScene1.fxml"));
            Parent root = loader2.load();
            scene1 = new Scene(root);
            scene1UI = loader2.getController();

        } catch(IOException e) {
            
            System.out.println(e);
            e.printStackTrace();
        }
    }
    
    private void uploadScene2() {
        try {
            FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/fxml/SpecifyGeographicalAreaScene2.fxml"));
            Parent root = loader2.load();
            scene2 = new Scene(root);
            scene2UI = loader2.getController();

        } catch(IOException e) {
            
            System.out.println(e);
            e.printStackTrace();
        }
    }
    
    private void uploadScene3() {
        try {
            FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/fxml/SpecifyGeographicalAreaScene3.fxml"));
            Parent root = loader2.load();
            scene3 = new Scene(root);
            scene3UI = loader2.getController();

        } catch(IOException e) {
            
            System.out.println(e);
            e.printStackTrace();
        }
    }
    
}
