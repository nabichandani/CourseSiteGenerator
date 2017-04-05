/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.data;

import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Navin
 */
public class TeachingAssistant<E extends Comparable<E>> implements Comparable<E>  {
    // THE TABLE WILL STORE TA NAMES AND EMAILS
    private final StringProperty name;
    private final StringProperty email;
    private boolean isUndergrad;
    /**
     * Constructor initializes the TA name
     */
    public TeachingAssistant(String initName, String initEmail, boolean ug) {
        name = new SimpleStringProperty(initName);
        email = new SimpleStringProperty(initEmail);
        isUndergrad = ug;
    }

    // ACCESSORS AND MUTATORS FOR THE PROPERTIES

    public String getName() {
        return name.get();
    }
    
    public String getEmail(){
        return email.get();
    }

    public void setName(String initName) {
        name.set(initName);
    }
    
    public void setEmail(String initEmail) {
        email.set(initEmail);
    }

    @Override
    public int compareTo(E otherTA) {
        return getName().compareTo(((TeachingAssistant)otherTA).getName());
    }
    
    @Override
    public String toString() {
        return name.getValue();
    }
        
    
}
