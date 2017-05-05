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
import java.util.Collections;
import properties_manager.PropertiesManager;

/**
 *
 * @author Navin
 */
public class EditRecitation_jTPS_Transaction implements jTPS_Transaction {

    CSGApp app;
    RecitationData data;
    Recitation oldRec;
    Recitation newRec;

    public EditRecitation_jTPS_Transaction(CSGApp app, Recitation oldRec, Recitation newRec) {
        this.app = app;
        data = (RecitationData) app.getRecitationDataComponent();
        this.oldRec = new Recitation(oldRec.getSection(), oldRec.getInstructor(),
                oldRec.getDayTime(), oldRec.getLocation(), oldRec.getFirstTA(),
                oldRec.getSecondTA());
        this.newRec = new Recitation(newRec.getSection(), newRec.getInstructor(),
                newRec.getDayTime(), newRec.getLocation(), newRec.getFirstTA(),
                newRec.getSecondTA());

    }

    @Override
    public void doTransaction() {
        data.deleteRec(data.getRecitation(oldRec.getSection()));
        data.getRecitations().add(newRec);
        Collections.sort(data.getRecitations());
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        workspace.getRecitationTable().getSelectionModel().select(data.getRecitation(newRec.getSection()));
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        workspace.getRecAddButton().setText(props.getProperty(CSGProp.ADDEDIT2_TEXT
                .toString()));
        workspace.getRecLocationText().setText(newRec.getLocation());
        workspace.getRecInstructorText().setText(newRec.getInstructor());
        workspace.getRecSectionText().setText(newRec.getSection());
        workspace.getRecDayTimeText().setText(newRec.getDayTime());
        workspace.getRecTA1Combo().setValue(newRec.getFirstTA());
        workspace.getRecTA2Combo().setValue(newRec.getSecondTA());
    }

    @Override
    public void undoTransaction() {
        data.deleteRec(data.getRecitation(newRec.getSection()));
        data.getRecitations().add(oldRec);
        Collections.sort(data.getRecitations());
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        workspace.getRecitationTable().getSelectionModel().select(data.getRecitation(oldRec.getSection()));
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        workspace.getRecAddButton().setText(props.getProperty(CSGProp.ADDEDIT2_TEXT
                .toString()));
        workspace.getRecLocationText().setText(oldRec.getLocation());
        workspace.getRecInstructorText().setText(oldRec.getInstructor());
        workspace.getRecSectionText().setText(oldRec.getSection());
        workspace.getRecDayTimeText().setText(oldRec.getDayTime());
        workspace.getRecTA1Combo().setValue(oldRec.getFirstTA());
        workspace.getRecTA2Combo().setValue(oldRec.getSecondTA());
    }
}
