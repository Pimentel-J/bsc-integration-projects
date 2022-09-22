/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr2.project.agpsd.utils;

import lapr2.project.agpsd.model.PostalAddress;

/**
 *
 *
 */
public class ObjectRegistry {
    
    /*
    Use this class to maintain a collection of objects that can be used
    in use cases and tests. For example, the following returns an instance of 
    a PostalAddress
    */
    public static PostalAddress getInstancePostalAddress(){
        PostalAddress address = new PostalAddress("Porto", "Porto");
        return address;
    }
 
}
