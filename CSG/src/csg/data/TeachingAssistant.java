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
public class TeachingAssistant{
    private StringProperty name;
    private StringProperty email;
    private boolean undergrad;
    
    public TeachingAssistant(StringProperty name, 
        StringProperty email, boolean undergrad){
        this.name = name;
        this.email = email;
        this.undergrad = undergrad;
    }

    public StringProperty getName() {
        return name;
    }

    public void setName(StringProperty name) {
        this.name = name;
    }

    public StringProperty getEmail() {
        return email;
    }

    public void setEmail(StringProperty email) {
        this.email = email;
    }

    public boolean isUndergrad() {
        return undergrad;
    }

    public void setUndergrad(boolean isUndergrad) {
        this.undergrad = isUndergrad;
    }
    
    public boolean isUnique(TeachingAssistant TA){
        if(this.name.getValue().equals(TA.getName().getValue())){
            if(this.email.getValue().equals(TA.getEmail().getValue())){
                if(this.isUndergrad() == TA.isUndergrad()){
                    return true;
                }
            }
        }
        return false;
    }
    
    public String toString() {
        return name.getValue();
    }
        
    
}
