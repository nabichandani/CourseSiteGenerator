/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jtps;

import csg.CSGApp;
import csg.CSGProp;
import csg.data.ScheduleData;
import csg.ui.CSGWorkspace;
import java.time.LocalDate;
import properties_manager.PropertiesManager;

/**
 *
 * @author Navin
 */
public class DeleteSchItem_jTPS_Transaction implements jTPS_Transaction{
        
    CSGApp app;
    ScheduleData data;
    CSGWorkspace workspace;
    String type;
    LocalDate date;
    String time;
    String title;
    String topic;
    String link;
    String criteria;
    
    public DeleteSchItem_jTPS_Transaction(CSGApp app, String type, LocalDate date, 
      String time, String title, String topic, String link, String criteria){
        
        this.app = app;
        data = (ScheduleData) app.getScheduleDataComponent();
        workspace = (CSGWorkspace)app.getWorkspaceComponent();
        this.type = type;
        this.date = date;
        this.time = time;
        this.title = title;
        this.topic = topic;
        this.link = link;
        this.criteria = criteria;
    
    }
    
    
    @Override
    public void doTransaction() {
        data.getSchedule().remove(data.getScheduleItem(type, date, time, title,
                topic, link, criteria));
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        workspace.getScheduleAddUpdateButton().setText(props.getProperty(CSGProp.ADDEDIT_TEXT
                .toString()));
        workspace.getScheduleCriteriaTextField().setText("");
        workspace.getScheduleDatePicker().setValue(null);
        workspace.getScheduleLinkTextField().setText("");
        workspace.getScheduleTimeTextField().setText("");
        workspace.getScheduleTitleTextField().setText("");
        workspace.getScheduleTopicTextField().setText("");
        workspace.getScheduleTypeCombo().setValue("");

    }

    @Override
    public void undoTransaction() {
        data.addScheduleItem(type, date, time, title, topic, link, criteria);
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        workspace.getScheduleAddUpdateButton().setText(props.getProperty(CSGProp.ADDEDIT_TEXT
                .toString()));
        workspace.getScheduleCriteriaTextField().setText("");
        workspace.getScheduleDatePicker().setValue(null);
        workspace.getScheduleLinkTextField().setText("");
        workspace.getScheduleTimeTextField().setText("");
        workspace.getScheduleTitleTextField().setText("");
        workspace.getScheduleTopicTextField().setText("");
        workspace.getScheduleTypeCombo().setValue("");
    }

}
