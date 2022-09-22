/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.serviceproviderapplication.utils;

/**
 *
 *
 */
public class Constants
{
    public static final String ROLE_ADMINISTRATOR = "ADMINISTRATIVO";
    public static final String ROLE_CLIENT = "CLIENTE";
    public static final String ROLE_HRO = "FUNC_RH";
    public static final String ROLE_SERVICE_PROVIDER = "PRESTADOR SERVIÇO";
    
    public static final String PARAMS_FILE = "config.properties";
    public static final String PARAMS_COMPANY_DESIGNATION = "Company.Designation";
    public static final String PARAMS_COMPANY_NIF = "Company.NIF";
    public static final String PARAMS_NUMBER_SERV_TYPES = "Company.NumberOfSupportedServiceTypes";
    public static final String PARAMS_SERVICE_TYPE = "Company.ServiceType";
    
    public static final int MAX_TRIES = 3;
    public static final String ADMINISTRATOR_MENU = "/fxml/AdministratorMenu.fxml";
    public static final String CUSTOMER_MENU = "/fxml/AdministratorMenu.fxml";
    public static final String HRO_MENU = "/fxml/HROMenu.fxml";
    public static final String SERVICE_PROVIDER_MENU = "/fxml/ServiceProviderMenu.fxml";
    public static final String ADMINISTRATOR_CONTROLLER = "lapr2.project.agpsd.controller.ControllerAdministrator.java";
    public static final String CUSTOMER_CONTROLLER = "lapr2.project.agpsd.controller.ControllerCustomer.java";
    public static final String HRO_CONTROLLER = "lapr2.project.agpsd.controller.ControllerHRO.java";
    public static final String SERVICE_PROVIDER_CONTROLLER = "lapr2.project.agpsd.controller.ControllerServiceProvider.java";
    
    public static final String OTHER_ATTRIBUTE_PREDETERM_DURATION = "Predetermined Duration";
    
}
