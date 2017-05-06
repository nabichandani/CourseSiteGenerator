/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jtps;

import csg.CSGApp;
import csg.CSGProp;
import csg.data.ProjectData;
import csg.data.Team;
import csg.ui.CSGWorkspace;
import javafx.scene.paint.Color;
import properties_manager.PropertiesManager;

/**
 *
 * @author Navin
 */
public class EditTeam_jTPS_Transaction implements jTPS_Transaction {

    CSGApp app;
    ProjectData data;
    CSGWorkspace workspace;
    Team oldTeam;
    Team newTeam;

    public EditTeam_jTPS_Transaction(CSGApp app, Team oldTeam, Team newTeam) {
        this.app = app;
        data = (ProjectData) app.getProjectDataComponent();
        workspace = (CSGWorkspace) app.getWorkspaceComponent();
        this.oldTeam = new Team(oldTeam.getName(), oldTeam.getColor(),
                oldTeam.getTextColor(), oldTeam.getLink());
        this.newTeam = new Team(newTeam.getName(), newTeam.getColor(),
                newTeam.getTextColor(), newTeam.getLink());

    }

    @Override
    public void doTransaction() {
        data.deleteTeam(data.getTeam(oldTeam.getName()));
        data.addTeam(newTeam.getName(), newTeam.getColor(),
                newTeam.getTextColor(), newTeam.getLink());
        workspace.getTeamsTable().getSelectionModel().select(data.getTeam(newTeam.getName()));
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        workspace.getTeamAddUpdateButton().setText(props.getProperty(CSGProp.ADDEDIT2_TEXT
                .toString()));
        workspace.getTeamNameTextField().setText(newTeam.getName());
        workspace.getColorPicker().setValue(Color.valueOf(newTeam.getColor()));
        workspace.getTextColorPicker().setValue(Color.valueOf(newTeam.getTextColor()));
        workspace.getTeamLinkTextField().setText(newTeam.getLink());
    }

    @Override
    public void undoTransaction() {
        data.deleteTeam(data.getTeam(newTeam.getName()));
        data.addTeam(oldTeam.getName(), oldTeam.getColor(),
                oldTeam.getTextColor(), oldTeam.getLink());
        workspace.getTeamsTable().getSelectionModel().select(data.getTeam(oldTeam.getName()));
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        workspace.getTeamAddUpdateButton().setText(props.getProperty(CSGProp.ADDEDIT2_TEXT
                .toString()));
        workspace.getTeamNameTextField().setText(oldTeam.getName());
        workspace.getColorPicker().setValue(Color.valueOf(oldTeam.getColor()));
        workspace.getTextColorPicker().setValue(Color.valueOf(oldTeam.getTextColor()));
        workspace.getTeamLinkTextField().setText(oldTeam.getLink());
    }

}
