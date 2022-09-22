package lapr2.project.agpsd.ui;

import java.util.logging.Level;
import java.util.logging.Logger;
import lapr2.project.agpsd.controller.UC2SubmitApplicationController;
import lapr2.project.agpsd.mainpackage.MainApplication;
import lapr2.project.agpsd.model.Application;

/**
 * UC2 - New Application UI class
 *
 *
 */
public class NewApplicationUI {

    private MainApplication mainApp;
    private UC2SubmitApplicationController controller;
    
    public NewApplicationUI(MainApplication mainApp) {
        this.mainApp = mainApp;
        this.controller = new UC2SubmitApplicationController();
    }

    public MainApplication getMainApp() {
        return this.mainApp;
    }

    public UC2SubmitApplicationController getController() {
        return this.controller;
    }

    public void toNewApplicationScene1() {
        try {
            NewApplicationScene1UI newApplicationScene1UI = ((NewApplicationScene1UI) this.mainApp.replaceSceneContent("/fxml/NewApplicationScene1.fxml"));
            newApplicationScene1UI.setNewApplicationUI(this);
            Application application = this.controller.getApplication();
            if (application != null) {
                newApplicationScene1UI.showApplication(application);
            }
            newApplicationScene1UI.getTxtFullName().requestFocus();
        } catch (Exception ex) {
            Logger.getLogger(MainApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void toNewApplicationScene2() {
        try {
            NewApplicationScene2UI newApplicationScene2UI
                    = (NewApplicationScene2UI) this.mainApp.
                            replaceSceneContent("/fxml/NewApplicationScene2.fxml");
            newApplicationScene2UI.setNewApplicationUI(this);
//            Application newApp = application;
//            Employee employee = this.getController().getEmployee();
//            newApplicationScene2UI.showEmployee(employee);
//            newApplicationScene2UI.showEmployee();
        } catch (Exception ex) {
            Logger.getLogger(MainApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void toNewApplicationScene3() {
        try {
            NewApplicationScene3UI newApplicationScene3UI
                    = (NewApplicationScene3UI) this.mainApp.
                            replaceSceneContent("/fxml/NewApplicationScene3.fxml");
            newApplicationScene3UI.setNewApplicationUI(this);
            newApplicationScene3UI.initComboBox();
//            newApplicationScene3UI.showNotification(notification);
        } catch (Exception ex) {
            Logger.getLogger(MainApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void toNewApplicationFinal(Application application) {
        try {
            NewApplicationScene3UI newApplicationScene3UI
                    = (NewApplicationScene3UI) this.mainApp.
                            replaceSceneContent("/fxml/NewApplicationScene3.fxml");
            newApplicationScene3UI.setNewApplicationUI(this);
            newApplicationScene3UI.initComboBox();
//            newApplicationScene3UI.showNotification(notification);
        } catch (Exception ex) {
            Logger.getLogger(MainApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
