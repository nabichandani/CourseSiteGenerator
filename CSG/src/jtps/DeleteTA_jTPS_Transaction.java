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
import csg.data.Recitation;
import csg.data.RecitationData;
import csg.data.TeachingAssistant;
import csg.data.TAData;
import csg.ui.CSGWorkspace;
import java.util.ArrayList;
import java.util.Collections;
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
        ArrayList<Recitation> oldRec;
        RecitationData recData;
        CSGWorkspace workspace;
        
    public DeleteTA_jTPS_Transaction(CSGApp initApp, String name, String email, 
        boolean isUndergrad, HashMap<String, String> taHours, ArrayList<Recitation> oldR){
        app = initApp;
        data = (TAData) app.getTADataComponent();
        recData = (RecitationData) app.getRecitationDataComponent();
        this.name = name;
        this.email = email;
        isUG = new SimpleBooleanProperty();
        isUG.setValue(isUndergrad);
        taOfficeHours = new HashMap<String,String>(taHours);
        for(String key: taHours.keySet()){
            taOfficeHours.put(key, taHours.get(key));   
            }
        oldRec = new ArrayList();
            for(Recitation recitation: oldR){
                oldRec.add(new Recitation(recitation.getSection(), recitation.getInstructor(), 
                  recitation.getDayTime(), recitation.getLocation(), recitation.getFirstTA(), 
                  recitation.getSecondTA()));
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
        ArrayList<String> delArr = new ArrayList();
            for(Recitation recitation: recData.getRecitations()){
                if(recitation.getFirstTA().equals(name) && recitation.getSecondTA().isEmpty()){
                    delArr.add(recitation.getSection());
                }
                else if(recitation.getFirstTA().equals(name) && !recitation.getSecondTA().isEmpty()){
                    recitation.setFirstTA(recitation.getSecondTA());
                    recitation.setSecondTA("");
                }
                else if(recitation.getSecondTA().equals(name)){
                    recitation.setSecondTA("");
                }
            }
            for(int i = 0; i < delArr.size(); i++){
                recData.getRecitations().remove(recData.getRecitation(delArr.get(i)));
            }
            workspace.getRecitationTable().refresh();
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
        recData.getRecitations().clear();
        for(Recitation recitation: oldRec){
            recData.getRecitations().add(new Recitation(recitation.getSection(), recitation.getInstructor(), 
                  recitation.getDayTime(), recitation.getLocation(), recitation.getFirstTA(), 
                  recitation.getSecondTA()));
        }
        workspace.getRecitationTable().refresh();
        Collections.sort(recData.getRecitations());
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        workspace.getTaAddButton().setText(props.getProperty(CSGProp.ADD_BUTTON_TEXT
                        .toString()));
        workspace.getTaNameTextField().clear();
        workspace.getEmailTextField().clear();
        
    }
    
}
