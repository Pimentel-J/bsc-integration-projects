package lapr2.project.agpsd.mainpackage;

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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lapr2.project.agpsd.controller.AuthenticationController;
import lapr2.project.agpsd.controller.ControllerMenu;
import lapr2.project.agpsd.model.AGPSD;
import lapr2.project.agpsd.model.Company;
import lapr2.project.agpsd.ui.NewApplicationScene1UI;
import lapr2.project.agpsd.ui.NewApplicationUI;
import lapr2.project.agpsd.utils.Constants;
import lapr2.project.agpsd.utils.Utils;
import lapr2.project.userandauth.UserRole;

public class FXMLControllerMain implements Initializable {

    @FXML
    private Button btnUC2;
    @FXML
    private Button btnExit;

    private MainApplication mainApp;

    /**
     * Initializes the UI class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    private Stage stage;
    @FXML private Label titleLabel;
    @FXML private Label loginLabel;
    @FXML private TextField textFieldUsername;
    @FXML private TextField textFieldPassword;
    @FXML private Button buttonUC1;
    @FXML private Button buttonUC2;
    @FXML private Button loginButton;
    private Company company;
    private int triesLeft = Constants.MAX_TRIES;
    private Scene sceneMain;

    @FXML
    protected void handleButtonUC1Action(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/UC1RegisterCustomerScene.fxml"));
        Parent root = loader.load();
        Scene sceneTest = new Scene(root);
        sceneTest.getStylesheets().add("/styles/Styles.css");
        stage.setScene(sceneTest);

        //Instantiate your controller here
        //If you are in charge of this UC, do NOT forget to pass stage object to your controller
    }

    public void setMainApp(MainApplication mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void handleButtonUC2Action(ActionEvent event) {
        NewApplicationUI newApplicationUI = new NewApplicationUI(this.mainApp);
        newApplicationUI.toNewApplicationScene1();
    }

    @FXML
    protected void handleButtonLogin(ActionEvent event) throws IOException {

        if (this.triesLeft > 0) {

            AuthenticationController auth = new AuthenticationController();
            boolean successfulLogin = auth.doLogin(this.textFieldUsername.getText(), this.textFieldPassword.getText());

            if (successfulLogin) {

                List<UserRole> userRoles = auth.getUserRoles();

                this.textFieldUsername.clear();
                this.textFieldPassword.clear();

                if (userRoles.size() == 1) {

                    //Load scene via reflection and the user's role
                    FXMLLoader loader = new FXMLLoader(getClass().getResource(Utils.getMenuPathForRole(userRoles.get(0).getRole())));
                    Parent root = loader.load();
                    Scene sceneSubMenu = new Scene(root);
                    sceneSubMenu.getStylesheets().add("/styles/Styles.css");

                    //Instantiates controller
                    ControllerMenu roleController = loader.getController(); //ControllerMenu is an interface
                    roleController.setController(this.stage, this.company, this.sceneMain);

                    /*
                    !!!!!!!!!!
                    !!!!!!!!!!
                    
                    If you must send additional things to your controller, 
                    you should switch, case, downcast and do it here
                    
                    !!!!!!!!!!
                    !!!!!!!!!!
                     */
                    //Changes the scene
                    this.triesLeft = Constants.MAX_TRIES;
                    stage.setScene(sceneSubMenu);

                } else {
                    //Multiple roles not implemented yet
                }
            } else {

                --this.triesLeft;
                this.loginLabel.setText(triesLeft + " tries left");

            }

        }
    }

    public void setMainController(Company company, Stage stage, Scene sceneMain) {
        this.company = company;
        this.stage = stage;
        this.loginLabel.setText(triesLeft + " tries left");
        this.sceneMain = sceneMain;
    }

    @FXML
    private void btnExitAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle("AGPSD Application");
        alert.setHeaderText("Action Configuration");
        alert.setContentText("Are you sure you want to exit the application?");
//        alert.setContentText(AGPSD.getInstance().getCompany().getApplicationRegistry().getApplications().get(4).toString());
        
        if (alert.showAndWait().get() == ButtonType.OK) {
            // downcasting from Scene to Stage
            ((Stage) btnExit.getScene().getWindow()).close();
            System.exit(0);
        }
    }

}
