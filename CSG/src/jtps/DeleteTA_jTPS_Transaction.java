/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jtps;
import java.util.HashMap;
import javafx.beans.property.StringProperty;
import csg.CSGApp;
import csg.CSGProp;
import csg.data.TeachingAssistant;
import csg.data.TAData;
import csg.ui.CSGWorkspace;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import properties_manager.PropertiesManager;
/**
 *
 * @author Navin
 */
    public class DeleteTA_jTPS_Transaction implements jTPS_Transaction{
        CSGApp app;
        TAData data;
        String name;
        String email;
        BooleanProperty isUG;
        TeachingAssistant TA;
        HashMap<String, String> taOfficeHours;
        CSGWorkspace workspace;
        
    public DeleteTA_jTPS_Transaction(CSGApp initApp, String name, String email, boolean isUndergrad, HashMap<String, String> taHours){
        app = initApp;
        data = (TAData) app.getTADataComponent();
        this.name = name;
        this.email = email;
        isUG = new SimpleBooleanProperty();
        isUG.setValue(isUndergrad);
        taOfficeHours = new HashMap<String,String>(taHours);
        for(String key: taHours.keySet()){
            taOfficeHours.put(key, taHours.get(key));   
            }
        workspace = (CSGWorkspace) app.getWorkspaceComponent();
    }

    @Override
    public void doTransaction() {
        data.deleteTA(TA);
        HashMap<String, StringProperty> officeHours = data.getOfficeHours();
          for(String key: officeHours.keySet()){
              StringProperty nameList = officeHours.get(key);
              if(nameList.getValue().contains(name)){
                  data.removeTAFromCell(nameList,name);
              }
          }
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        workspace.getTaAddButton().setText(props.getProperty(CSGProp.ADD_BUTTON_TEXT
                        .toString()));
        workspace.getTaNameTextField().clear();
        workspace.getEmailTextField().clear();
     
    }

    @Override
    public void undoTransaction() {
        data.addTA(name, email, isUG);
        TA = data.getTA(name);
        for(String key: taOfficeHours.keySet()){
            data.addToGrid(key, taOfficeHours.get(key));
         }
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        workspace.getTaAddButton().setText(props.getProperty(CSGProp.ADD_BUTTON_TEXT
                        .toString()));
        workspace.getTaNameTextField().clear();
        workspace.getEmailTextField().clear();
        
    }
    
}
