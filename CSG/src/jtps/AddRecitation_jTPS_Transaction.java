/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jtps;

import csg.CSGApp;
import csg.CSGProp;
import csg.data.Recitation;
import csg.data.RecitationData;
import csg.ui.CSGWorkspace;
import properties_manager.PropertiesManager;

/**
 *
 * @author Navin
 */
public class AddRecitation_jTPS_Transaction implements jTPS_Transaction {

    CSGApp app;
    RecitationData data;
    String section;
    String instructor;
    String dayTime;
    String location;
    String firstTA;
    String secondTA;

    public AddRecitation_jTPS_Transaction(CSGApp app, String section, String instructor, String dayTime,
            String location, String taOne, String taTwo) {
        this.app = app;
        data = (RecitationData) app.getRecitationDataComponent();
        this.section = section;
        this.location = location;
        this.instructor = instructor;
        this.dayTime = dayTime;
        firstTA = taOne;
        secondTA = taTwo;

    }

    @Override
    public void doTransaction() {
        data.addRecitation(section, instructor, dayTime, location, firstTA, secondTA);
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        workspace.getRecitationTable().getSelectionModel().select(data.getRecitation(section));
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        workspace.getRecAddButton().setText(props.getProperty(CSGProp.ADDEDIT2_TEXT
                .toString()));
        workspace.getRecLocationText().setText(location);
        workspace.getRecInstructorText().setText(instructor);
        workspace.getRecSectionText().setText(section);
        workspace.getRecDayTimeText().setText(dayTime);
        workspace.getRecTA1Combo().setValue(firstTA);
        workspace.getRecTA2Combo().setValue(secondTA);
    }

    @Override
    public void undoTransaction() {
        try{
            data.getRecitations().remove(data.getRecitation(section));
            CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
            PropertiesManager props = PropertiesManager.getPropertiesManager();
            workspace.getRecAddButton().setText(props.getProperty(CSGProp.ADDEDIT2_TEXT
                    .toString()));
            Recitation rec = workspace.getRecitationTable().getSelectionModel().getSelectedItem();
            workspace.getRecLocationText().setText(rec.getLocation());
            workspace.getRecInstructorText().setText(rec.getInstructor());
            workspace.getRecSectionText().setText(rec.getSection());
            workspace.getRecDayTimeText().setText(rec.getDayTime());
            workspace.getRecTA1Combo().setValue(rec.getFirstTA());
            workspace.getRecTA2Combo().setValue(rec.getSecondTA());
        } catch (NullPointerException e) {
            CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
            PropertiesManager props = PropertiesManager.getPropertiesManager();
            workspace.getRecAddButton().setText(props.getProperty(CSGProp.ADDEDIT_TEXT
                    .toString()));
            workspace.getRecLocationText().setText("");
            workspace.getRecInstructorText().setText("");
            workspace.getRecSectionText().setText("");
            workspace.getRecDayTimeText().setText("");
            workspace.getRecTA1Combo().setValue("");
            workspace.getRecTA2Combo().setValue("");
        }
    }
        
}
