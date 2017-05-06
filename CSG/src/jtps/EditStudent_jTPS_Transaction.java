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
public class EditStudent_jTPS_Transaction implements jTPS_Transaction{
    CSGApp app;
    ProjectData data;
    CSGWorkspace workspace;
    Student oldStudent;
    Student newStudent;
    
    public EditStudent_jTPS_Transaction(CSGApp app, Student oldS, Student newS){
        this.app = app;
        data = (ProjectData) app.getProjectDataComponent();
        workspace = (CSGWorkspace) app.getWorkspaceComponent();
        oldStudent = new Student(oldS.getFirstName(), oldS.getLastName(), oldS.getTeam(), oldS.getRole());
        newStudent = new Student(newS.getFirstName(), newS.getLastName(), newS.getTeam(), newS.getRole());
    }

    @Override
    public void doTransaction() {
        data.deleteStudent(data.getStudent(oldStudent.getFirstName(), 
            oldStudent.getLastName()));
        data.addStudent(newStudent.getFirstName(), newStudent.getLastName(), 
            newStudent.getTeam(), newStudent.getRole());    
        workspace.getStudentTable().getSelectionModel().select(data.getStudent(newStudent.getFirstName(), newStudent.getLastName()));
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        workspace.getStudentAddUpdateButton().setText(props.getProperty(CSGProp.ADDEDIT2_TEXT
                .toString()));
        workspace.getStudentFNameTextField().setText(newStudent.getFirstName());
        workspace.getStudentLNameTextField().setText(newStudent.getLastName());
        workspace.getStudentTeamCombo().setValue(newStudent.getTeam());
        workspace.getStudentRoleTextField().setText(newStudent.getRole());
    }

    @Override
    public void undoTransaction() {
        data.deleteStudent(data.getStudent(newStudent.getFirstName(), 
            newStudent.getLastName()));
        data.addStudent(oldStudent.getFirstName(), oldStudent.getLastName(), 
            oldStudent.getTeam(), oldStudent.getRole());
        workspace.getStudentTable().getSelectionModel().select(data.getStudent(oldStudent.getFirstName(), oldStudent.getLastName()));
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        workspace.getStudentAddUpdateButton().setText(props.getProperty(CSGProp.ADDEDIT2_TEXT
                .toString()));
        workspace.getStudentFNameTextField().setText(oldStudent.getFirstName());
        workspace.getStudentLNameTextField().setText(oldStudent.getLastName());
        workspace.getStudentTeamCombo().setValue(oldStudent.getTeam());
        workspace.getStudentRoleTextField().setText(oldStudent.getRole());
    }

}
