package lapr2.project.agpsd.ui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import lapr2.project.agpsd.controller.UC2SubmitApplicationController;
import lapr2.project.agpsd.model.Application;
import lapr2.project.agpsd.model.Category;
import lapr2.project.agpsd.utils.OutOfBoundArgumentException;
import lapr2.project.agpsd.utils.Utils;

/**
 * FXML Scene1 Controller class (UC2)
 *
 *
 */
public class NewApplicationScene1UI implements Initializable {

    private NewApplicationUI newApplicationUI;
//to save the reference to the UC2Controller
    private UC2SubmitApplicationController UC2Controller;

    @FXML
    private TextField txtNome;
    @FXML
    private TextField txtTelefone;
    @FXML
    private TextField txtFullName;
    @FXML
    private TextField txtNIF;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtPhoneNumber;
    @FXML
    private Button btnCancel;
    @FXML
    private Label titleLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void setNewApplicationUI(NewApplicationUI newApplicationUI) {
        this.newApplicationUI = newApplicationUI;
    }

    public TextField getTxtFullName() {
        return this.txtFullName;
    }

    public void showApplication(Application application) {
        this.txtFullName.setText(String.valueOf(application.getFullName()));
        this.txtNIF.setText(application.getNIF());
    }

    @FXML
    private void btnNextAction(ActionEvent event) {
        try {
            int NIF = Integer.parseInt(this.txtNIF.getText());
            int phoneNumber = Integer.parseInt(this.txtPhoneNumber.getText());
            String name = this.txtFullName.getText();
            String email = this.txtEmail.getText();
            this.newApplicationUI.getController().newApplication(name,
                    txtNIF.getText(), email, txtPhoneNumber.getText(), "n/a", "n/a", "n/a");
            this.newApplicationUI.toNewApplicationScene2();
        } catch (NumberFormatException ex) {
            Utils.createAlert(Alert.AlertType.ERROR, "New Application", "Error!",
                    "Insert a valid number!").show();
            this.txtNIF.requestFocus();
        } catch (OutOfBoundArgumentException out) {
            Utils.createAlert(Alert.AlertType.ERROR, "New Application", "Error!",
                    out.getMessage()).show();
            this.txtNIF.requestFocus();
        } catch (IllegalArgumentException ex) {
            Utils.createAlert(Alert.AlertType.ERROR, "New Application", "Error!",
                    "Insert a valid char! " + ex.getMessage()).show();
            if (ex.getMessage().toLowerCase().contains("name")) {
                this.txtFullName.requestFocus();
            } else {
                this.txtEmail.requestFocus();
            }
        }
    }

    @FXML
    private void txtNumberKeyPressed(KeyEvent event) {
        this.titleLabel.setText(null);
    }

    @FXML
    private void txtNameKeyPressed(KeyEvent event) {
        this.titleLabel.setText(null);
    }

//my stuff
    public void linkController(UC2SubmitApplicationController UC2Controller) {
//saves a reference to UC2SubmitApplicationController which invoked this class
        this.UC2Controller = UC2Controller;
    }
//not used yet

    @FXML
    private void btnAddAction(ActionEvent event) {
        try {
//creates new application's attributes
            String fullName = txtFullName.getText();
            String NIF = txtNIF.getText();
            String email = txtEmail.getText();
            String phoneNumber = txtPhoneNumber.getText();

//sends the new application to UC2SubmitApplicationController
            boolean added = UC2Controller.registerApplication(new Application());

            Utils.createAlert(Alert.AlertType.INFORMATION, "New Application", "New Application Insert",
                    added ? "Application successfully added"
                            : "Application not added").show();

            cancelNewApplication(event);
        } catch (NumberFormatException nfe) {
            Utils.createAlert(Alert.AlertType.ERROR, "New Application", "Error!",
                    "Insert a valid number.").show();
        } catch (IllegalArgumentException iae) {
            Utils.createAlert(Alert.AlertType.ERROR, "New Application", "Error! Invalid data.", iae.getMessage()).show();
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
//not used yet

    private void cancelNewApplication(ActionEvent event) {
        txtFullName.setText("");
        txtNIF.setText("");
        txtEmail.setText("");
        txtPhoneNumber.setText("");
//hides the new application window
        ((Node) event.getSource()).getScene().getWindow().hide();
    }
}
