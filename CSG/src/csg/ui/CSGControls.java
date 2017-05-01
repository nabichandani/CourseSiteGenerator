/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.ui;

import com.sun.xml.internal.bind.v2.util.FatalAdapter;
import csg.CSGApp;
import static csg.CSGProp.MISSING_TA_EMAIL_MESSAGE;
import static csg.CSGProp.MISSING_TA_EMAIL_TITLE;
import static csg.CSGProp.MISSING_TA_NAME_MESSAGE;
import static csg.CSGProp.MISSING_TA_NAME_TITLE;
import static csg.CSGProp.REC_MISSING_MESSAGE;
import static csg.CSGProp.REC_MISSING_TITLE;
import static csg.CSGProp.REC_NOT_UNIQUE_MESSAGE;
import static csg.CSGProp.REC_NOT_UNIQUE_TITLE;
import static csg.CSGProp.SCH_MISSING_MESSAGE;
import static csg.CSGProp.SCH_MISSING_TITLE;
import static csg.CSGProp.SCH_NOT_UNIQUE_MESSAGE;
import static csg.CSGProp.SCH_NOT_UNIQUE_TITLE;
import static csg.CSGProp.STUDENT_MISSING_MESSAGE;
import static csg.CSGProp.STUDENT_MISSING_TITLE;
import static csg.CSGProp.STUDENT_NOT_UNIQUE_MESSAGE;
import static csg.CSGProp.STUDENT_NOT_UNIQUE_TITLE;
import static csg.CSGProp.TA_NAME_AND_EMAIL_NOT_UNIQUE_MESSAGE;
import static csg.CSGProp.TA_NAME_AND_EMAIL_NOT_UNIQUE_TITLE;
import static csg.CSGProp.TEAM_MISSING_MESSAGE;
import static csg.CSGProp.TEAM_MISSING_TITLE;
import static csg.CSGProp.TEAM_NOT_UNIQUE_MESSAGE;
import static csg.CSGProp.TEAM_NOT_UNIQUE_TITLE;
import csg.data.ProjectData;
import csg.data.Recitation;
import csg.data.RecitationData;
import csg.data.ScheduleData;
import csg.data.ScheduleItem;
import csg.data.Student;
import csg.data.TAData;
import csg.data.TeachingAssistant;
import csg.data.Team;
import djf.controller.AppFileController;
import djf.ui.AppMessageDialogSingleton;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import jtps.*;
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
        jTPS jtps = workspace.getjTPS();
        String name = nameTextField.getText().trim();
        TextField emailTextField = workspace.getEmailTextField();
        String email = emailTextField.getText().trim();
        
        
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
                jtps.addTransaction(new AddTA_jTPS_Transaction(app, name, email, ug.getValue()));
            
            
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
          jTPS jtps = workspace.getjTPS();
          TextField nameTextField = workspace.getNameTextField();
          String name = nameTextField.getText().trim();
          TextField emailTextField = workspace.getEmailTextField();
          String email = emailTextField.getText().trim();
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
                EditTA_jTPS_Transaction edit= new EditTA_jTPS_Transaction
                    (app, name, email,ta.isUndergrad().get(), ta.getName(),
                    ta.getEmail(), ta.isUndergrad().get());
                jtps.addTransaction(edit);
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
                ug.set(ta.isUndergrad().getValue());
                
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
      
      public void handleAddStudent(){
           PropertiesManager props = PropertiesManager.getPropertiesManager();
          try{
              CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
              ProjectData data = (ProjectData) app.getProjectDataComponent();
              String firstName = workspace.getStudentFNameTextField().getText().trim();
              String lastName = workspace.getStudentLNameTextField().getText().trim();
              String team = workspace.getStudentTeamCombo().getValue().toString().trim();
              String role = workspace.getStudentRoleTextField().getText().trim();
              Student s = new Student(firstName, lastName, team, role);
              if(firstName.isEmpty() || lastName.isEmpty() || team.isEmpty() || 
                role.isEmpty()){
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(STUDENT_MISSING_TITLE), props.getProperty(STUDENT_MISSING_MESSAGE));
            }
            else if(data.isUniqueStudent(s)){
                data.addStudent(firstName, lastName, team, role);
                workspace.getStudentFNameTextField().clear();
                workspace.getStudentLNameTextField().clear();
                workspace.getStudentTeamCombo().setValue(null);
                workspace.getStudentRoleTextField().clear();
               appFileController.markAsEdited(app.getGUI()); 
            }
            else{
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(STUDENT_NOT_UNIQUE_TITLE), props.getProperty(STUDENT_NOT_UNIQUE_MESSAGE));
            }
          
          }catch(Exception e){
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(STUDENT_NOT_UNIQUE_TITLE), props.getProperty(STUDENT_NOT_UNIQUE_MESSAGE));
          }
      }
      
        public void handleEditStudent(Student student) {
        try {
            PropertiesManager props = PropertiesManager.getPropertiesManager();
            CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
            ProjectData data = (ProjectData) app.getProjectDataComponent();
              String firstName = workspace.getStudentFNameTextField().getText().trim();
              String lastName = workspace.getStudentLNameTextField().getText().trim();
              String team = workspace.getStudentTeamCombo().getValue().toString().trim();
              String role = workspace.getStudentRoleTextField().getText().trim();
              Student s = new Student(firstName, lastName, team, role);
            if(!data.isUniqueStudent(s) && (!student.getFirstName()
                .equals(firstName) || !student.getLastName().equals(lastName))) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(STUDENT_NOT_UNIQUE_TITLE), props.getProperty(STUDENT_NOT_UNIQUE_MESSAGE));
            }
            else if(student.getFirstName().equals(firstName) && student.getLastName().equals(lastName)
              && student.getRole().equals(role) && student.getTeam().equals(team)){
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(STUDENT_NOT_UNIQUE_TITLE), props.getProperty(STUDENT_NOT_UNIQUE_MESSAGE));
            }
            else if (firstName.isEmpty() || lastName.isEmpty() || team.isEmpty() || 
                role.isEmpty()) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(STUDENT_MISSING_TITLE), props.getProperty(STUDENT_MISSING_MESSAGE));
           }
           else{
                data.deleteStudent(student);
                data.addStudent(firstName, lastName, team, role);
                appFileController.markAsEdited(app.getGUI()); 
           }
          }catch(Exception e){
          }
      }
      
      
      
      
      public void handleAddTeam(){
          PropertiesManager props = PropertiesManager.getPropertiesManager();
          try{
              CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
              ProjectData data = (ProjectData) app.getProjectDataComponent();
              String name = workspace.getTeamNameTextField().getText().trim();
              String color = workspace.getColorPicker().getValue().toString();
              String textColor = workspace.getTextColorPicker().getValue().toString();
              String link = workspace.getTeamLinkTextField().getText().trim();
              
            if(name.isEmpty() || color.isEmpty() || textColor.isEmpty() || 
                link.isEmpty()){
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(TEAM_MISSING_TITLE), props.getProperty(TEAM_MISSING_MESSAGE));
            }
            else if(!data.containsTeam(name)){
               data.addTeam(name, color, textColor, link);
                workspace.getTeamNameTextField().setText("");
                workspace.getColorPicker().setValue(Color.WHITE);
                workspace.getTextColorPicker().setValue(Color.WHITE);
                workspace.getTeamLinkTextField().setText("");
               appFileController.markAsEdited(app.getGUI()); 
            }
            else{
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(TEAM_NOT_UNIQUE_TITLE), props.getProperty(TEAM_NOT_UNIQUE_MESSAGE));
            }
          
          }catch(Exception e){
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(TEAM_NOT_UNIQUE_TITLE), props.getProperty(TEAM_NOT_UNIQUE_MESSAGE));
          }
      }
      
      
      
     public void handleEditTeam(Team team) {
        try {
            PropertiesManager props = PropertiesManager.getPropertiesManager();
            CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
            ProjectData data = (ProjectData) app.getProjectDataComponent();
            String name = workspace.getTeamNameTextField().getText().trim();
            String color = workspace.getColorPicker().getValue().toString();
            String textColor = workspace.getTextColorPicker().getValue().toString();
            String link = workspace.getTeamLinkTextField().getText().trim();
            Team newTeam = new Team(name, color, textColor, link);
            if (data.containsTeam(name) && !team.getName().equals(name)) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(TEAM_NOT_UNIQUE_TITLE), props.getProperty(TEAM_NOT_UNIQUE_MESSAGE));
            }
            else if(team.getColor().equals(color) && team.getLink().equals(link)
              && team.getName().equals(name) && team.getTextColor().
              equals(textColor)){
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(TEAM_NOT_UNIQUE_TITLE), props.getProperty(TEAM_NOT_UNIQUE_MESSAGE));
            }
            else if (name.isEmpty() || color.isEmpty()|| textColor.isEmpty()
                  || link.isEmpty()) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(TEAM_MISSING_TITLE), props.getProperty(TEAM_MISSING_MESSAGE)); 
           }
           else{
                data.getTeams().remove(team);
                data.addTeam(name, color, textColor, link);
                appFileController.markAsEdited(app.getGUI()); 
           }
          }catch(Exception e){
          }
      }
      
      
      public void handleAddScheduleItem(){
          PropertiesManager props = PropertiesManager.getPropertiesManager();
          try{
           CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
           ScheduleData data = (ScheduleData)app.getScheduleDataComponent();
           String type = (String) workspace.getScheduleTypeCombo().getValue();
           LocalDate date = workspace.getScheduleDatePicker().getValue();
           String time = workspace.getScheduleTimeTextField().getText().trim();
           String title = workspace.getScheduleTitleTextField().getText().trim();
           String topic = workspace.getScheduleTopicTextField().getText().trim();
           String link = workspace.getScheduleLinkTextField().getText().trim();
           String criteria = workspace.getScheduleCriteriaTextField().getText().trim();
           ScheduleItem schItem = new ScheduleItem(type, date, time, title, topic, link, criteria);
           if(type.isEmpty() || date.equals(null) || time.isEmpty() || 
                title.isEmpty()){
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(SCH_MISSING_TITLE), props.getProperty(SCH_MISSING_MESSAGE));
           }
          else if(!data.isInSchedule(schItem)){
              data.addScheduleItem(schItem);
                workspace.getScheduleCriteriaTextField().setText("");
                workspace.getScheduleDatePicker().setValue(null);
                workspace.getScheduleLinkTextField().setText("");
                workspace.getScheduleTimeTextField().setText("");
                workspace.getScheduleTitleTextField().setText("");
                workspace.getScheduleTopicTextField().setText("");
                workspace.getScheduleTypeCombo().setValue("");
              appFileController.markAsEdited(app.getGUI()); 
          }
          else{
              AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
              dialog.show(props.getProperty(SCH_NOT_UNIQUE_TITLE), props.getProperty(SCH_NOT_UNIQUE_MESSAGE));
              
           }
          }catch(Exception e){
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(SCH_NOT_UNIQUE_TITLE), props.getProperty(SCH_NOT_UNIQUE_MESSAGE));
          }
           
      }
      
      public void handleEditScheduleItem(ScheduleItem sch){
          PropertiesManager props = PropertiesManager.getPropertiesManager();
          try{
           CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
           ScheduleData data = (ScheduleData)app.getScheduleDataComponent();
           String type = (String) workspace.getScheduleTypeCombo().getValue();
           LocalDate date = workspace.getScheduleDatePicker().getValue();
           String time = workspace.getScheduleTimeTextField().getText().trim();
           String title = workspace.getScheduleTitleTextField().getText().trim();
           String topic = workspace.getScheduleTopicTextField().getText().trim();
           String link = workspace.getScheduleLinkTextField().getText().trim();
           String criteria = workspace.getScheduleCriteriaTextField().getText().trim();
           ScheduleItem newSch = new ScheduleItem(type, date, time, title, topic, link, criteria);
           if(data.isInSchedule(newSch)){
               AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
               dialog.show(props.getProperty(REC_NOT_UNIQUE_TITLE), props.getProperty(REC_NOT_UNIQUE_MESSAGE));
               return;
           }
           else if (type.isEmpty() || date.equals(null)|| time.isEmpty()
                  || title.isEmpty()) {
              AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
              dialog.show(props.getProperty(REC_MISSING_TITLE), props.getProperty(REC_MISSING_MESSAGE));   
          }
           else{
                data.getSchedule().remove(sch);
                data.addScheduleItem(newSch);
                appFileController.markAsEdited(app.getGUI()); 
           }
          }catch(Exception e){
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(REC_NOT_UNIQUE_TITLE), props.getProperty(REC_NOT_UNIQUE_MESSAGE));    
          }
      }
      
      public void handleAddRecitation(){
          PropertiesManager props = PropertiesManager.getPropertiesManager();
          try{
           CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
           RecitationData data = (RecitationData)app.getRecitationDataComponent();
           String section = workspace.getRecSectionText().getText().trim();
           String instructor = workspace.getRecInstructorText().getText().trim();
           String dayTime = workspace.getRecDayTimeText().getText().trim();
           String location = workspace.getRecLocationText().getText().trim();
           String ta1 = workspace.getRecTA1Combo().getValue().toString();
           String ta2 = workspace.getRecTA2Combo().getValue().toString();
           if(section.isEmpty() || instructor.isEmpty() || dayTime.isEmpty() || 
                location.isEmpty() || ta1.isEmpty()){
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(REC_MISSING_TITLE), props.getProperty(REC_MISSING_MESSAGE));
           }
           else if(!data.containsRecitation(section)){
                data.addRecitation(section, instructor, dayTime, 
                    location, ta1, ta2);
                workspace.getRecLocationText().setText(""); 
                workspace.getRecInstructorText().setText("");
                workspace.getRecSectionText().setText("");
                workspace.getRecDayTimeText().setText("");
                workspace.getRecTA1Combo().setValue("");
                workspace.getRecTA2Combo().setValue("");
                appFileController.markAsEdited(app.getGUI()); 
           }
           else{
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(REC_NOT_UNIQUE_TITLE), props.getProperty(REC_NOT_UNIQUE_MESSAGE));
           }
          }
          catch(Exception e){
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(REC_NOT_UNIQUE_TITLE), props.getProperty(REC_NOT_UNIQUE_MESSAGE));             
          }
      }
      
      public void handleEditRecitation(Recitation rec){
           PropertiesManager props = PropertiesManager.getPropertiesManager();
           CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
           RecitationData data = (RecitationData)app.getRecitationDataComponent();
           String section = workspace.getRecSectionText().getText().trim();
           String instructor = workspace.getRecInstructorText().getText().trim();
           String dayTime = workspace.getRecDayTimeText().getText().trim();
           String location = workspace.getRecLocationText().getText().trim();
           String ta1 = workspace.getRecTA1Combo().getValue().toString();
           String ta2 = workspace.getRecTA2Combo().getValue().toString();          
           if(data.containsRecitation(section) && !rec.getSection().equals(section)){
               AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
               dialog.show(props.getProperty(REC_NOT_UNIQUE_TITLE), props.getProperty(REC_NOT_UNIQUE_MESSAGE));
               return;
           }
           else if (section.isEmpty() || instructor.isEmpty() || dayTime.isEmpty()
                  || location.isEmpty() || ta1.isEmpty() || ta2.isEmpty()) {
              AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
              dialog.show(props.getProperty(REC_MISSING_TITLE), props.getProperty(REC_MISSING_MESSAGE));   
          }
          else if (rec.getDayTime().equals(dayTime) && rec.getFirstTA().equals(ta1)
                  && rec.getInstructor().equals(instructor) && rec.getLocation().equals(location)
                  && rec.getSecondTA().equals(ta2) && rec.getSection().equals(section)) {
               
               AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
               dialog.show(props.getProperty(REC_NOT_UNIQUE_TITLE), props.getProperty(REC_NOT_UNIQUE_MESSAGE));
               
           }
           else{
                data.getRecitations().remove(rec);
                data.addRecitation(section, instructor, dayTime, 
                    location, ta1, ta2);
                appFileController.markAsEdited(app.getGUI()); 
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
        jTPS jtps = workspace.getjTPS();
        
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
        jtps.addTransaction(new CellToggle_jTPS_Transaction(app, cellKey, taName));
        appFileController.markAsEdited(app.getGUI());
        }
        catch(NullPointerException e){
    }
    
    
}
}
