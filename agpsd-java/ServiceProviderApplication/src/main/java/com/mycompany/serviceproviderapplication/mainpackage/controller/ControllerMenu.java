/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.serviceproviderapplication.mainpackage.controller;

import com.mycompany.serviceproviderapplication.model.ServiceProvider;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 *
 */
public interface ControllerMenu {
    
    public void setController(ServiceProvider sp, Stage stage, Scene sceneMain);
    
}
