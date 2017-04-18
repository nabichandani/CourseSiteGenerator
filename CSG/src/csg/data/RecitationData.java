/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.data;

import csg.CSGApp;
import djf.components.AppDataComponent;
import java.util.Collections;
import javafx.beans.property.BooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Navin
 */
public class RecitationData implements AppDataComponent{
    CSGApp app;
    
    ObservableList<Recitation> recitations;
    
    public RecitationData(CSGApp initApp){
    app = initApp;   
    recitations = FXCollections.observableArrayList();
    
    
    }

    public ObservableList<Recitation> getRecitations() {
        return recitations;
    }
    
    public Recitation getRecitation(String section) {
        for (Recitation rec : recitations) {
            if (rec.getSection().equals(section)) {
                return rec;
            }
        }
        return null;
    }
    
    public boolean containsRecitation(String section) {
        for (Recitation recitation: recitations) {
            if (recitation.getSection().equals(section)) {
                return true;
            }  
        }
        return false;
    }
    
    public boolean containsRecitationTime(String dayTime) {
        for (Recitation recitation: recitations) {
            if (recitation.getDayTime().equals(dayTime)) {
                return true;
            }  
        }
        return false;
    }
    
    public void addRecitation(String section, String instructor, String dayTime, 
        String location, String taOne, String taTwo) {
        // MAKE THE TA
        Recitation rec = new Recitation(section, instructor, dayTime, 
        location, taOne, taTwo);

        // ADD THE TA
        if (!containsRecitation(section)) {
            if(!containsRecitationTime(dayTime)){
               recitations.add(rec);
            }
        }

        // SORT THE TAS
        Collections.sort(recitations);
    }
      public void deleteTA(Recitation rec){
         recitations.remove(rec);
        
    }

    @Override
    public void resetData() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
