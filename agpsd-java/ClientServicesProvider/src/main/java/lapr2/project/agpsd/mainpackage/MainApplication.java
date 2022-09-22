package lapr2.project.agpsd.mainpackage;

import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lapr2.project.agpsd.model.AGPSD;
import lapr2.project.agpsd.model.Company;

public class MainApplication extends Application {
    
    private Stage stage;
    private Scene sceneMain;
    private final double MINIMUM_WINDOW_WIDTH = 400.0;
    private final double MINIMUM_WINDOW_HEIGHT = 300.0;
    private final double SCENE_WIDTH = 450.0;
    private final double SCENE_HEIGHT = 350.0;

    @Override
    public void start(Stage stage) throws Exception {
        
        //Loads a scene by way of a loader
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainScene.fxml"));
        Parent root = loader.load();
        sceneMain = new Scene(root);
//        sceneMain.getStylesheets().add("/styles/Styles.css");
        this.stage = stage;
        stage.setTitle("LAPR2 - AGPSD application");
        stage.setMinWidth(MINIMUM_WINDOW_WIDTH);
        stage.setMinHeight(MINIMUM_WINDOW_HEIGHT);
//        toMainScene();
//        this.stage.show();
        //Initializes main controller
        FXMLControllerMain mainController = loader.getController();
        mainController.setMainController(initializedCompany(), stage, sceneMain);
        mainController.setMainApp(this);
//        this.stage.show();

        //Show stage
        stage.setScene(sceneMain);
        stage.show();
    }
    
    public Stage getStage() {
        return this.stage;
    }
    
    public void toMainScene() {
        stage.setScene(sceneMain);
    }
    
//    public void toMainScene() {
//        try {
//            FXMLControllerMain mainUI = (FXMLControllerMain) replaceSceneContent("/fxml/MainScene.fxml");
//            mainUI.setMainApp(this);
//        } catch (Exception ex) {
//            Logger.getLogger(FXMLControllerMain.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }

    public Initializable replaceSceneContent(String fxml) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        InputStream in = MainApplication.class.getResourceAsStream(fxml);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(MainApplication.class.getResource(fxml));
        Pane page;
        try {
            page = (Pane) loader.load(in);
        } finally {
            in.close();
        }
        Scene scene = new Scene(page, SCENE_WIDTH, SCENE_HEIGHT);
//        scene.getStylesheets().add("/styles/Styles.css");
        this.stage.setScene(scene);
        this.stage.sizeToScene();
        return (Initializable) loader.getController();
    }

    public static Company initializedCompany() {

        //Pre-processing etc goes in here
        Company company = AGPSD.getInstance().getCompany();
        return company;
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
