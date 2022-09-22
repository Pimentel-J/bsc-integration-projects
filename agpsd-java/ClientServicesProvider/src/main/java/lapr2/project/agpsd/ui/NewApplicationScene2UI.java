package lapr2.project.agpsd.ui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import lapr2.project.agpsd.model.Application;
import lapr2.project.agpsd.model.PostalAddress;
import lapr2.project.agpsd.model.ZipCode;
import lapr2.project.agpsd.utils.Utils;

/**
 * FXML Scene2 Controller class (UC2)
 * 
 *
 */
public class NewApplicationScene2UI implements Initializable {

    private NewApplicationUI newApplicationUI;
    private Application newApp;

    @FXML
    private Label titleLabel;
    @FXML
    private Button btnNext;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnReturn;
    @FXML
    private TextField txtPlace;
    @FXML
    private TextField txtLocation;
    @FXML
    private TextField txtZipCode;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void setNewApplicationUI(NewApplicationUI newApplicationUI) {
        this.newApplicationUI = newApplicationUI;
    }

    @FXML
    private void btnNextAction(ActionEvent event) {
        try {
            String place = this.txtPlace.getText();
            String location = this.txtLocation.getText();
            String zipCode = this.txtZipCode.getText();
            this.newApplicationUI.getController().getApplication().addPostalAddress(new PostalAddress(place, zipCode, location));
            this.newApplicationUI.toNewApplicationScene3();
//        } catch (NumberFormatException ex) {
//            Utils.createAlert(Alert.AlertType.ERROR, "New Application", "Error!",
//                    "Insert a valid number!").show();
//            this.txtNIF.requestFocus();
        } catch (IllegalArgumentException ex) {
            Utils.createAlert(Alert.AlertType.ERROR, "New Application", "Error!",
                    "Insert a valid expression! " + ex.getMessage()).show();
            if (ex.getMessage().toLowerCase().contains("name")) {
                this.txtPlace.requestFocus();
            }
        }
    }

    @FXML
    private void btnCancelAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle("AGPSD Application");
        alert.setHeaderText("Action Configuration");
        alert.setContentText("Are you sure you want to exit the application?");

        if (alert.showAndWait().get() == ButtonType.OK) {
            // downcasting from Scene to Stage
            ((Stage) btnCancel.getScene().getWindow()).close();
            System.exit(0);
        }
//        cancelNewApplication(event);
    }

    @FXML
    private void txtNameKeyPressed(KeyEvent event) {
        this.titleLabel.setText(null);
    }

    @FXML
    private void btnReturnAction(ActionEvent event) {
        this.newApplicationUI.toNewApplicationScene1();
    }

}
