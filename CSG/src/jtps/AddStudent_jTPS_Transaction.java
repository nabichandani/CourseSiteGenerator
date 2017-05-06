/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jtps;

import csg.CSGApp;
import csg.CSGProp;
import csg.data.ProjectData;
import csg.data.Student;
import csg.ui.CSGWorkspace;
import properties_manager.PropertiesManager;

/**
 *
 * @author Navin
 */
public class AddStudent_jTPS_Transaction implements jTPS_Transaction{
    CSGApp app;
    ProjectData data;
    CSGWorkspace workspace;
    String firstName;
    String lastName;
    String team;
    String role;
    
    public AddStudent_jTPS_Transaction(CSGApp app, String firstName, String lastName, String team, String role){
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
        data.addStudent(firstName, lastName, team, role);
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        workspace.getStudentTable().getSelectionModel().select(data.getStudent(firstName, lastName));
        workspace.getStudentAddUpdateButton().setText(props.getProperty(CSGProp.ADDEDIT2_TEXT
                .toString()));
        workspace.getStudentFNameTextField().setText(firstName);
        workspace.getStudentLNameTextField().setText(lastName);
        workspace.getStudentTeamCombo().setValue(team);
        workspace.getStudentRoleTextField().setText(role);
    }

    @Override
    public void undoTransaction() {
        try{
        data.deleteStudent(data.getStudent(firstName, lastName));
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        workspace.getStudentAddUpdateButton().setText(props.getProperty(CSGProp.ADDEDIT2_TEXT
                .toString()));
        Student s = workspace.getStudentTable().getSelectionModel().getSelectedItem();
        workspace.getStudentFNameTextField().setText(s.getFirstName());
        workspace.getStudentLNameTextField().setText(s.getLastName());
        workspace.getStudentTeamCombo().setValue(s.getTeam());
        workspace.getStudentRoleTextField().setText(s.getRole());
        }catch(NullPointerException ex){
            PropertiesManager props = PropertiesManager.getPropertiesManager();
                    workspace.getStudentAddUpdateButton().setText(props.getProperty(CSGProp.ADDEDIT_TEXT
                .toString()));
        workspace.getStudentFNameTextField().clear();
        workspace.getStudentLNameTextField().clear();
        workspace.getStudentTeamCombo().setValue(null);
        workspace.getStudentRoleTextField().clear();
        }
    }

}
