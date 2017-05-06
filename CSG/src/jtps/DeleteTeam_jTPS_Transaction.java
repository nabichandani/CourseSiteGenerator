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
import csg.data.Team;
import csg.ui.CSGWorkspace;
import java.util.ArrayList;
import javafx.scene.paint.Color;
import properties_manager.PropertiesManager;

/**
 *
 * @author Navin
 */
public class DeleteTeam_jTPS_Transaction implements jTPS_Transaction {

    CSGApp app;
    ProjectData data;
    String name;
    String color;
    String textColor;
    String link;
    CSGWorkspace workspace;
    ArrayList<Student> oldStudents;

    public DeleteTeam_jTPS_Transaction(CSGApp app, String name, String color, String textColor,
            String link, ArrayList<Student> oldS) {
        this.app = app;
        data = (ProjectData) app.getProjectDataComponent();
        this.name = name;
        this.color = color;
        this.textColor = textColor;
        this.link = link;
        workspace = (CSGWorkspace) app.getWorkspaceComponent();
        oldStudents = new ArrayList();
        for (Student student : oldS) {
            oldStudents.add(new Student(student.getFirstName(), student.getLastName(),
                    student.getTeam(), student.getRole()));
        }
    }

    @Override
    public void doTransaction() {
        try {
            data.deleteTeam(data.getTeam(name));
            ArrayList<String> delArr = new ArrayList();
            for (Student student : data.getStudents()) {
                if (student.getTeam().equals(name)) {
                    delArr.add(student.getFirstName() + " " + student.getLastName());
                }
            }
            for (int i = 0; i < delArr.size(); i++) {
                String fName = delArr.get(i).split(" ")[0];
                String lName = delArr.get(i).split(" ")[1];
                data.getStudents().remove(data.
                        getStudent(fName, lName));
            }
            PropertiesManager props = PropertiesManager.getPropertiesManager();
            Team t = workspace.getTeamsTable().getSelectionModel().getSelectedItem();
            workspace.getTeamAddUpdateButton().setText(props.getProperty(CSGProp.ADDEDIT2_TEXT
                    .toString()));
            workspace.getTeamNameTextField().setText(t.getName());
            workspace.getColorPicker().setValue(Color.valueOf(t.getColor()));
            workspace.getTextColorPicker().setValue(Color.valueOf(t.getTextColor()));
            workspace.getTeamLinkTextField().setText(t.getLink());
        } catch (NullPointerException e) {
            PropertiesManager props = PropertiesManager.getPropertiesManager();
            workspace.getTeamAddUpdateButton().setText(props.getProperty(CSGProp.ADDEDIT_TEXT
                    .toString()));
            workspace.getTeamNameTextField().clear();
            workspace.getColorPicker().setValue(Color.WHITE);
            workspace.getTextColorPicker().setValue(Color.WHITE);
            workspace.getTeamLinkTextField().clear();
        }
    }

    @Override
    public void undoTransaction() {
        data.addTeam(name, color, textColor, link);
        data.getStudents().clear();
        for (Student student : oldStudents) {
            data.getStudents().add(new Student(student.getFirstName(), student.getLastName(),
                    student.getTeam(), student.getRole()));
        }
        workspace.getTeamsTable().getSelectionModel().select(data.getTeam(name));
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        workspace.getTeamAddUpdateButton().setText(props.getProperty(CSGProp.ADDEDIT2_TEXT
                .toString()));
        workspace.getTeamNameTextField().setText(name);
        workspace.getColorPicker().setValue(Color.valueOf(color));
        workspace.getTextColorPicker().setValue(Color.valueOf(textColor));
        workspace.getTeamLinkTextField().setText(link);

    }

}
