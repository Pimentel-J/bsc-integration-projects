/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr2.project.agpsd.controller;

import javafx.scene.Scene;
import javafx.stage.Stage;
import lapr2.project.agpsd.model.Company;

/**
 *
 *
 */
public interface ControllerMenu {
    
    public void setController(Stage stage, Company company, Scene sceneMain);
    
}
