/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jtps;
import csg.CSGApp;
import csg.data.TeachingAssistant;
import csg.data.TAData;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 *
 * @author Navin
 */
public class AddTA_jTPS_Transaction implements jTPS_Transaction{
    TeachingAssistant TA;
    CSGApp app;
    TAData data;
    String nameTA;
    String emailTA;
    BooleanProperty isUG;
    
    public AddTA_jTPS_Transaction(CSGApp initApp, String name, String email, boolean isUndergrad){
        app = initApp;
        data = (TAData) app.getTADataComponent();
        TA = data.getTA(name);
        nameTA = name;
        emailTA = email;
        isUG = new SimpleBooleanProperty();
        isUG.set(isUndergrad);
    }

    @Override
    public void doTransaction() {
        data.addTA(nameTA, emailTA, isUG);
        TA = data.getTA(nameTA);
    }

    @Override
    public void undoTransaction() {
        data.deleteTA(TA);
                
    }
}
