/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jtps;

import csg.CSGApp;
import csg.CSGProp;
import csg.data.ProjectData;
import csg.ui.CSGWorkspace;
import properties_manager.PropertiesManager;

/**
 *
 * @author Navin
 */
public class DeleteStudent_jTPS_Transaction implements jTPS_Transaction{
    CSGApp app;
    ProjectData data;
    CSGWorkspace workspace;
    String firstName;
    String lastName;
    String team;
    String role;
    
    public DeleteStudent_jTPS_Transaction(CSGApp app, String firstName, 
        String lastName, String team, String role){
        this.app = app;
        data = (ProjectData)app.getProjectDataComponent();
        workspace = (CSGWorkspace) app.getWorkspaceComponent();
        this.firstName = firstName;
        this.lastName = lastName;
        this.team = team;
        this.role = role;
    }

    @Override
    public void doTransaction() {
        data.deleteStudent(data.getStudent(firstName, lastName));
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        workspace.getStudentAddUpdateButton().setText(props.getProperty(CSGProp.ADDEDIT_TEXT
                .toString()));
        workspace.getStudentFNameTextField().clear();
        workspace.getStudentLNameTextField().clear();
        workspace.getStudentTeamCombo().setValue(null);
        workspace.getStudentRoleTextField().clear();
    }

    @Override
    public void undoTransaction() {
        data.addStudent(firstName, lastName, team, role);
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        workspace.getStudentAddUpdateButton().setText(props.getProperty(CSGProp.ADDEDIT_TEXT
                .toString()));
        workspace.getStudentFNameTextField().clear();
        workspace.getStudentLNameTextField().clear();
        workspace.getStudentTeamCombo().setValue(null);
        workspace.getStudentRoleTextField().clear();
    }

}
