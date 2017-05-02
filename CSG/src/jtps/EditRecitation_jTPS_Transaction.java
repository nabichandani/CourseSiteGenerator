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

    @Override
    public void undoTransaction() {
        data.deleteRec(data.getRecitation(newRec.getSection()));
        data.getRecitations().add(oldRec);
        Collections.sort(data.getRecitations());
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
