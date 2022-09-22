/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr2.project.agpsd.model;

import java.util.Objects;

/**
 *
 *
 */
public class OtherCost {
    
    private String designation;
    private String value;

    /**
     *
     * @param designation
     * @param value
     */
    public OtherCost(String designation, String value) {
        this.designation = designation;
        this.value = value;
    }
    
    /**
     *
     * @return hash
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.designation);
        hash = 41 * hash + Objects.hashCode(this.value);
        return hash;
    }

    /**
     *
     * @param obj
     * @return true or false
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final OtherCost other = (OtherCost) obj;
        if (!Objects.equals(this.designation, other.designation)) {
            return false;
        }
        if (!Objects.equals(this.value, other.value)) {
            return false;
        }
        return true;
    }

    /**
     *
     * @return designation
     */
    public String getDesignation() {
        return designation;
    }

    /**
     *
     * @param designation
     */
    public void setDesignation(String designation) {
        this.designation = designation;
    }

    /**
     *
     * @return value
     */
    public String getValue() {
        return value;
    }

    /**
     *
     * @param value
     */
    public void setValue(String value) {
        this.value = value;
    }
    
    
}
