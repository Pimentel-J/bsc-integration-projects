/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr2.project.agpsd.model;

/**
 *
 *
 */
public class Event {
    /**
     * String related to the Event's description
     */
    private String description;
/**
 * Constructor with the instance variable
 * @param description 
 */
    public Event(String description) {
        this.description = description;
    }
//GETTERS
    public String getDescription() {
        return description;
    }
//SETTERS
    public void setDescription(String description) {
        this.description = description;
    }
/**
 * Overwrite of the toString method
 * @return String with the content of a Event type object
 */
    @Override
    public String toString() {
        return "Event{" + "description=" + description + '}';
    }
    
    
}
