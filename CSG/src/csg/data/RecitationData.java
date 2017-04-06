/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.data;

import csg.CSGApp;
import djf.components.AppDataComponent;
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
    
    

    @Override
    public void resetData() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
