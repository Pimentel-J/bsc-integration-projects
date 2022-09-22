package lapr2.project.agpsd.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lapr2.project.agpsd.controller.UC2SubmitApplicationController;
import lapr2.project.agpsd.model.AcademicQualification;
import lapr2.project.agpsd.model.Application;
import lapr2.project.agpsd.model.Category;
import lapr2.project.agpsd.model.Document;
import lapr2.project.agpsd.model.ProfessionalQualification;
import lapr2.project.agpsd.utils.ForEachLists;
import lapr2.project.agpsd.utils.Utils;

/**
 * FXML Scene3 Controller class (UC2)
 *
 *
 */
public class NewApplicationScene3UI implements Initializable {

    private NewApplicationUI newApplicationUI;
    private UC2SubmitApplicationController UC2Controller;
    private Application newApp;
    private Stage finalStage;

    @FXML
    private Label titleLabel;
    @FXML
    private Button btnSubmit;
    @FXML
    private Button btnReturn;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnAcadQualif;
    @FXML
    private Button btnProfQualif;
    @FXML
    private Button btnDoc;
    @FXML
    private Button btnCat;
    @FXML
    private ComboBox<Category> cmbCategory;
    @FXML
    private TextField txtAQDesignation;
    @FXML
    private TextField txtAQDegree;
    @FXML
    private TextField txtAQScore;
    @FXML
    private TextField txtProfQualif;
    @FXML
    private TextField txtDoc;

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
    private void btnReturnAction(ActionEvent event) {
        this.newApplicationUI.toNewApplicationScene2();
    }

    public void initComboBox() {
        UC2Controller = this.newApplicationUI.getController();
        ObservableList<Category> options = FXCollections.
                observableArrayList(UC2Controller.getCategories());
        this.cmbCategory.setItems(options);
    }

    @FXML
    private void cmbCategoryAction(ActionEvent event) {
        this.titleLabel.setText(null);
    }

    @FXML
    private void btnSubmitAction(ActionEvent event) {
//        finalStage.show();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initStyle(StageStyle.UTILITY);

        TextArea textArea = new TextArea();

        GridPane content = new GridPane();
        content.setMaxWidth(Double.MAX_VALUE);
        content.setMaxHeight(750);
        content.add(textArea, 0, 0);
        
        alert.setTitle("AGPSD Application");
        alert.setHeaderText("Action Configuration");
        alert.setContentText("Are you sure you want to submit the application?");
        
        alert.getDialogPane().setContentText(this.newApplicationUI.getController().getApplication().toString() + 
                "\nCategories:\n" + ForEachLists.categoryForEach(this.newApplicationUI.getController().getApplication().getCategoryList()) + 
                "Academic Qualifications:\n" + ForEachLists.acadQualifForEach(this.newApplicationUI.getController().getApplication().getAcademicQualificationList()) + 
                "Professional Qualifications:\n" + ForEachLists.profQualifForEach(this.newApplicationUI.getController().getApplication().getProfessionalQualificationList()) + 
                "Documents:\n" + ForEachLists.docsForEach(this.newApplicationUI.getController().getApplication().getDocumentList()));

        if (alert.showAndWait().get() == ButtonType.OK) {
            this.newApplicationUI.getController().registerApplication(this.getController().getApplication());
            Utils.createAlert(Alert.AlertType.INFORMATION, "New Application", "AGPSD Application",
                    "Application successfully submitted!");
            alert.close();
            // downcasting from Scene to Stage
//            ((Stage) btnSubmit.getScene().getWindow()).close();
            this.newApplicationUI.getMainApp().toMainScene();
        } else {
            alert.close();
//            ((Stage) btnSubmit.getScene().getWindow()).close();
            this.newApplicationUI.getMainApp().toMainScene();
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
    private void btnAcadQualifAction(ActionEvent event) {
        try {
            AcademicQualification newAcadQualif = new AcademicQualification(txtAQDegree.getText(), txtAQDesignation.getText(), txtAQScore.getText());
            boolean added = this.newApplicationUI.getController().getApplication().addAcademicQualification(newAcadQualif);
            Utils.createAlert(Alert.AlertType.INFORMATION, "New Application", "New Academic Qualification", added ? "Academic qualification successfully added"
                    : "Unable to add academic qualification").show();

            this.txtAQDegree.setText("");
            this.txtAQDesignation.setText("");
            this.txtAQScore.setText("");
        } catch (IllegalArgumentException iae) {
            Utils.createAlert(Alert.AlertType.ERROR, "New Application", "Error! Invalid data.\n" + iae.getMessage(), iae.getMessage()).show();
        }
    }

    @FXML
    private void btnProfQualifAction(ActionEvent event) {
        try {
            ProfessionalQualification newProfQualif = new ProfessionalQualification(txtProfQualif.getText());
            boolean added = this.newApplicationUI.getController().getApplication().addProfessionalQualification(newProfQualif);
            Utils.createAlert(Alert.AlertType.INFORMATION, "New Application", "New Professional Qualification", added ? "Professional qualification successfully added"
                    : "Unable to add professional qualification").show();

            this.txtProfQualif.setText("");
        } catch (IllegalArgumentException iae) {
            Utils.createAlert(Alert.AlertType.ERROR, "New Application", "Error! Invalid data.", iae.getMessage()).show();
        }
    }

    @FXML
    private void btnDocAction(ActionEvent event) {
        try {
            Document newDoc = new Document(txtDoc.getText());
            boolean added = this.newApplicationUI.getController().getApplication().addDocument(newDoc);
            Utils.createAlert(Alert.AlertType.INFORMATION, "New Application", "New Document", added ? "Document successfully added"
                    : "Unable to add document").show();

            this.txtDoc.setText("");
        } catch (IllegalArgumentException iae) {
            Utils.createAlert(Alert.AlertType.ERROR, "New Application", "Error! Invalid data.", iae.getMessage()).show();
        }
    }

    @FXML
    private void btnCatAction(ActionEvent event) {
        try {
            Category newCat = (Category) this.cmbCategory.getSelectionModel().getSelectedItem();
            boolean added = this.newApplicationUI.getController().getApplication().addCategory(newCat);
            Utils.createAlert(Alert.AlertType.INFORMATION, "New Application", "New Category", added ? "Category successfully added"
                    : "Unable to add category.").show();

            this.cmbCategory.setValue(null);
        } catch (IllegalArgumentException iae) {
            Utils.createAlert(Alert.AlertType.ERROR, "New Application", "Error! Invalid data.", iae.getMessage()).show();
        }
    }

    @FXML
    private void txtNumberKeyPressed(KeyEvent event) {
        this.titleLabel.setText(null);
    }

    public UC2SubmitApplicationController getController() {
        return this.newApplicationUI.getController();
    }
}
