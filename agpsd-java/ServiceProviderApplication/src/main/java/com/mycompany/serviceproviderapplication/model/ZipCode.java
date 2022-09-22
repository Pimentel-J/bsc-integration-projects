/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.serviceproviderapplication.model;

/**
 *
 *
 */
public class ZipCode {

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getZipCode() {
        return zipCode;
    }

    public ZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
    
    public ZipCode() {
        this.zipCode = null;
    }

    private String zipCode;
}
