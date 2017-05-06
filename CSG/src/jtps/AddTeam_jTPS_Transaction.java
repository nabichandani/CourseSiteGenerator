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
public class AddTeam_jTPS_Transaction implements jTPS_Transaction {

    CSGApp app;
    ProjectData data;
    String name;
    String color;
    String textColor;
    String link;
    CSGWorkspace workspace;

    public AddTeam_jTPS_Transaction(CSGApp app, String name, String color, String textColor,
            String link) {
        this.app = app;
        data = (ProjectData) app.getProjectDataComponent();
        this.name = name;
        this.color = color;
        this.textColor = textColor;
        this.link = link;
        workspace = (CSGWorkspace) app.getWorkspaceComponent();
    }

    @Override
    public void doTransaction() {
        data.addTeam(name, color, textColor, link);
        workspace.getTeamsTable().getSelectionModel().select(data.getTeam(name));
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        workspace.getTeamAddUpdateButton().setText(props.getProperty(CSGProp.ADDEDIT2_TEXT
                .toString()));
        workspace.getTeamNameTextField().setText(name);
        workspace.getColorPicker().setValue(Color.valueOf(color));
        workspace.getTextColorPicker().setValue(Color.valueOf(textColor));
        workspace.getTeamLinkTextField().setText(link);
    }

    @Override
    public void undoTransaction() {
        try {
            data.deleteTeam(data.getTeam(name));
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

}
