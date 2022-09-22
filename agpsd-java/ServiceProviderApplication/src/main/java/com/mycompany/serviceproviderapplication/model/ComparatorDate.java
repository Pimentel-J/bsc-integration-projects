/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.serviceproviderapplication.model;

import java.util.Comparator;

/**
 *
 *
 */
public class ComparatorDate implements Comparator {
    public int compare(Object obj1, Object obj2) {
        ServiceRequest servReq1 = (ServiceRequest) obj1;
        ServiceRequest servReq2 = (ServiceRequest) obj2;
        return servReq1.getDate().compareTo(servReq2.getDate());
    }
}
