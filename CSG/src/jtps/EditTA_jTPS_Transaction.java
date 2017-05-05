/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jtps;

import java.util.Collections;
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
public class EditTA_jTPS_Transaction implements jTPS_Transaction {

    CSGApp app;
    TAData data;
    CSGWorkspace workspace;
    String newName;
    String newEmail;
    BooleanProperty oldUG;
    String oldName;
    String oldEmail;
    BooleanProperty newUG;

    public EditTA_jTPS_Transaction(CSGApp application, String newN, String newE,
            boolean newUndergrad, String oldN, String oldE, boolean oldUndergrad) {
        app = application;
        data = (TAData) app.getTADataComponent();
        workspace = (CSGWorkspace) app.getWorkspaceComponent();
        newName = newN;
        newEmail = newE;
        newUG = new SimpleBooleanProperty();
        newUG.set(newUndergrad);
        oldName = oldN;
        oldEmail = oldE;
        oldUG = new SimpleBooleanProperty();
        oldUG.set(oldUndergrad);
    }

    @Override
    public void doTransaction() {
        HashMap<String, StringProperty> officeHours = data.getOfficeHours();
        for (String key : officeHours.keySet()) {
            StringProperty nameList = officeHours.get(key);
            if (nameList.getValue().contains(oldName)) {
                String replace = nameList.getValue().replace(oldName, newName);
                nameList.setValue(replace);
            }
        }
        for (int i = 0; i < data.getTeachingAssistants().size(); i++) {
            if (((TeachingAssistant) data.getTeachingAssistants().get(i)).getName().equals(oldName)) {
                if (((TeachingAssistant) data.getTeachingAssistants().get(i)).getEmail().equals(oldEmail)) {
                    data.getTeachingAssistants().remove(i);
                }
            }
        }
        TeachingAssistant newTA = new TeachingAssistant(newName, newEmail, newUG);
        data.getTeachingAssistants().add(newTA);
        Collections.sort(data.getTeachingAssistants());
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        workspace.getTaTable().getSelectionModel().select(data.getTA(newName));
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        workspace.getTaAddButton().setText(props.getProperty(CSGProp.UPDATE_TA
                .toString()));
        workspace.getTaNameTextField().setText(newName);
        workspace.getEmailTextField().setText(newEmail);

    }

    @Override
    public void undoTransaction() {
        HashMap<String, StringProperty> officeHours = data.getOfficeHours();
        for (String key : officeHours.keySet()) {
            StringProperty nameList = officeHours.get(key);
            if (nameList.getValue().contains(newName)) {
                String replace = nameList.getValue().replace(newName, oldName);
                nameList.setValue(replace);
            }
        }
        for (int i = 0; i < data.getTeachingAssistants().size(); i++) {
            if (((TeachingAssistant) data.getTeachingAssistants().get(i)).getName().equals(newName)) {
                if (((TeachingAssistant) data.getTeachingAssistants().get(i)).getEmail().equals(newEmail)) {
                    data.getTeachingAssistants().remove(i);
                }
            }
        }
        TeachingAssistant newTA = new TeachingAssistant(oldName, oldEmail, oldUG);
        data.getTeachingAssistants().add(newTA);
        Collections.sort(data.getTeachingAssistants());
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        workspace.getTaTable().getSelectionModel().select(data.getTA(oldName));
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        workspace.getTaAddButton().setText(props.getProperty(CSGProp.UPDATE_TA
                .toString()));
        workspace.getTaNameTextField().setText(newName);
        workspace.getEmailTextField().setText(newEmail);
    }

}
