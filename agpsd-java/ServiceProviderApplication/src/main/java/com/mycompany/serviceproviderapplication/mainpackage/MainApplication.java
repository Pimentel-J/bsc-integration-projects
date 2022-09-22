package com.mycompany.serviceproviderapplication.mainpackage;

import com.mycompany.serviceproviderapplication.model.ServiceProvider;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
    
        //Loads a scene by way of a loader
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainMenu.fxml"));
        Parent root = loader.load();
        Scene sceneMain = new Scene(root);
        sceneMain.getStylesheets().add("/styles/Styles.css");
        stage.setTitle("LAPR2 - ServiceProvider application");

        
        //Initializes main controller
        FXMLControllerMain mainController = loader.getController();
        mainController.setMainController(initializedServiceProvider(), stage, sceneMain);

        //Show stage
        stage.setScene(sceneMain);
        stage.show(); 

    }
    
    
    public static ServiceProvider initializedServiceProvider() {
        
        //Pre-processing etc goes in here
        
        //UC1
        
        ServiceProvider sp = SPGPSD.getInstance().getServiceProvider(); //alocates memory to a ServiceProvider object and makes the dependency explicit
        return sp;
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        
    }

}
