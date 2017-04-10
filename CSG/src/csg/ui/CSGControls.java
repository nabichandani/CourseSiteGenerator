/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.ui;

import CSGTransactions.TransactionStack;
import csg.CSGApp;
import static csg.CSGProp.MISSING_TA_EMAIL_MESSAGE;
import static csg.CSGProp.MISSING_TA_EMAIL_TITLE;
import static csg.CSGProp.MISSING_TA_NAME_MESSAGE;
import static csg.CSGProp.MISSING_TA_NAME_TITLE;
import static csg.CSGProp.TA_NAME_AND_EMAIL_NOT_UNIQUE_MESSAGE;
import static csg.CSGProp.TA_NAME_AND_EMAIL_NOT_UNIQUE_TITLE;
import csg.data.TAData;
import csg.data.TeachingAssistant;
import djf.controller.AppFileController;
import djf.ui.AppMessageDialogSingleton;
import java.util.Collections;
import java.util.HashMap;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import properties_manager.PropertiesManager;

/**
 *
 * @author Navin
 */
public class CSGControls {
     // THE APP PROVIDES ACCESS TO OTHER COMPONENTS AS NEEDED
    CSGApp app;
    AppFileController appFileController;

    /**
     * Constructor, note that the app must already be constructed.
     */
    public CSGControls(CSGApp initApp) {
        // KEEP THIS FOR LATER
        app = initApp;
        appFileController = app.getGUI().getAppFileController();
    }
    
    
        public void handleAddTA() {
        // WE'LL NEED THE WORKSPACE TO RETRIEVE THE USER INPUT VALUES
        CSGWorkspace workspace = (CSGWorkspace)app.getWorkspaceComponent();
        TextField nameTextField = workspace.getNameTextField();
        String name = nameTextField.getText();
        TextField emailTextField = workspace.getEmailTextField();
        String email = emailTextField.getText();
        
        
        // WE'LL NEED TO ASK THE DATA SOME QUESTIONS TOO
        TAData data = (TAData)app.getTADataComponent();
        
        // WE'LL NEED THIS IN CASE WE NEED TO DISPLAY ANY ERROR MESSAGES
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        // DID THE USER NEGLECT TO PROVIDE A TA NAME?
        if (name.isEmpty() || name.trim().length() == 0) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_TA_NAME_TITLE), props.getProperty(MISSING_TA_NAME_MESSAGE));            
        }
        else if(email.isEmpty()|| email.trim().length() == 0){
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_TA_EMAIL_TITLE), props.getProperty(MISSING_TA_EMAIL_MESSAGE));    
        }
        // DOES A TA ALREADY HAVE THE SAME NAME OR EMAIL?
        else if (data.containsTA(name)  || data.containsTAEmail(email)) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(TA_NAME_AND_EMAIL_NOT_UNIQUE_TITLE), props.getProperty(TA_NAME_AND_EMAIL_NOT_UNIQUE_MESSAGE));                                    
        }
        // EVERYTHING IS FINE, ADD A NEW TA
        else {
            if(email.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
		+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")){
                // ADD THE NEW TA TO THE DATA
                BooleanProperty ug = new SimpleBooleanProperty();
                ug.set(false);
                data.addTA(name, email, ug);
            
            
            // CLEAR THE TEXT FIELDS
            nameTextField.setText("");
            emailTextField.setText("");
            // AND SEND THE CARET BACK TO THE NAME TEXT FIELD FOR EASY DATA ENTRY
            nameTextField.requestFocus();
            
            appFileController.markAsEdited(app.getGUI());
            }
            else{
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(MISSING_TA_EMAIL_TITLE), props.getProperty(MISSING_TA_EMAIL_MESSAGE));    
            }
        }
        }
      public void editTA(TeachingAssistant ta){
          try{
          CSGWorkspace workspace = (CSGWorkspace)app.getWorkspaceComponent();
          TransactionStack jtps = workspace.getTransactionStack();
          TextField nameTextField = workspace.getNameTextField();
          String name = nameTextField.getText();
          TextField emailTextField = workspace.getEmailTextField();
          String email = emailTextField.getText();
          TableView table = workspace.getTATable();
          
          String initEmail = ta.getEmail();
          String initName = ta.getName();
          
          TAData data = (TAData)app.getTADataComponent();
        
          PropertiesManager props = PropertiesManager.getPropertiesManager();
         
          
          if (name.isEmpty() || name.trim().length() == 0) {  
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_TA_NAME_TITLE), props.getProperty(MISSING_TA_NAME_MESSAGE));      
          }
          else if(email.isEmpty()|| email.trim().length() == 0){  
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_TA_EMAIL_TITLE), props.getProperty(MISSING_TA_EMAIL_MESSAGE));
          }
          else if (( data.containsTA(name) && data.containsTAEmail(email))) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(TA_NAME_AND_EMAIL_NOT_UNIQUE_TITLE), props.getProperty(TA_NAME_AND_EMAIL_NOT_UNIQUE_MESSAGE)); 
           }
        // EVERYTHING IS FINE, ADD A NEW TA
          else {
                if(email.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
		+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")){
               // TeachingAssistant newTA = new TeachingAssistant(name, email);
                //data.getTeachingAssistants().add(newTA);
                //data.addTA(name,email);
                HashMap<String, StringProperty> officeHours = data.getOfficeHours();
                for(String key: officeHours.keySet()){
                    StringProperty nameList = officeHours.get(key);
                    if(nameList.getValue().contains(initName)){
                        String replace = nameList.getValue().replace(initName, name);
                        nameList.setValue(replace);
                    }
                }
                for(int i = 0; i < data.getTeachingAssistants().size(); i++){
                    if(((TeachingAssistant)data.getTeachingAssistants().get(i)).getName().equals(initName)){
                        if(((TeachingAssistant)data.getTeachingAssistants().get(i)).getEmail().equals(initEmail)){
                            data.getTeachingAssistants().remove(i);
                        }
                    }
                }
                //change ug
                BooleanProperty ug = new SimpleBooleanProperty();
                ug.set(false);
                
                TeachingAssistant newTA = new TeachingAssistant(name, email, ug);
                data.getTeachingAssistants().add(newTA);
                Collections.sort(data.getTeachingAssistants());
                appFileController.markAsEdited(app.getGUI()); 
            }
            else{
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(MISSING_TA_EMAIL_TITLE), props.getProperty(MISSING_TA_EMAIL_MESSAGE));
            }
        }
          }
          catch(Exception e){
          }
      }
    
      public void handleDeleteTA(){
          CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
          TAData data = (TAData)app.getTADataComponent();
          TableView table = workspace.getTATable();
          TeachingAssistant ta = (TeachingAssistant)(table.getFocusModel().getFocusedItem());
          data.deleteTA(ta);
          String taName = ta.getName();
          HashMap<String, StringProperty> officeHours = data.getOfficeHours();
          for(String key: officeHours.keySet()){
              StringProperty nameList = officeHours.get(key);
              if(nameList.getValue().contains(taName)){
                  data.removeTAFromCell(nameList,taName);
              }
          }
          appFileController.markAsEdited(app.getGUI());

    }

    /**
     * This function provides a response for when the user clicks
     * on the office hours grid to add or remove a TA to a time slot.
     * 
     * @param pane The pane that was toggled.
     */
    public void handleCellToggle(Pane pane) {
        try{ 
        // GET THE TABLE
        CSGWorkspace workspace = (CSGWorkspace)app.getWorkspaceComponent();
        TableView taTable = workspace.getTATable();
        TransactionStack jtps = workspace.getTransactionStack();
        
        // IS A TA SELECTED IN THE TABLE?
        Object selectedItem = taTable.getSelectionModel().getSelectedItem();
        
        // GET THE TA
        TeachingAssistant ta = (TeachingAssistant)selectedItem;
        String taName = ta.getName();
        TAData data = (TAData)app.getTADataComponent();
        String cellKey = pane.getId();
        String strPropVal = data.getOfficeHours().get(cellKey).getValue();
        // AND TOGGLE THE OFFICE HOURS IN THE CLICKED CELL
        data.toggleTAOfficeHours(cellKey, taName);
        appFileController.markAsEdited(app.getGUI());
        }
        catch(NullPointerException e){
    }
    
    
}
}
