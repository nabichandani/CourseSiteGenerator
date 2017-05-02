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

    public DeleteTeam_jTPS_Transaction(CSGApp app, String name, String color, String textColor,
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
        data.deleteTeam(data.getTeam(name));
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        workspace.getTeamAddUpdateButton().setText(props.getProperty(CSGProp.ADDEDIT_TEXT
                .toString()));
        workspace.getTeamNameTextField().clear();
        workspace.getColorPicker().setValue(Color.WHITE);
        workspace.getTextColorPicker().setValue(Color.WHITE);
        workspace.getTeamLinkTextField().clear();
    }

    @Override
    public void undoTransaction() {
        data.addTeam(name, color, textColor, link);
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        workspace.getTeamAddUpdateButton().setText(props.getProperty(CSGProp.ADDEDIT_TEXT
                .toString()));
        workspace.getTeamNameTextField().clear();
        workspace.getColorPicker().setValue(Color.WHITE);
        workspace.getTextColorPicker().setValue(Color.WHITE);
        workspace.getTeamLinkTextField().clear();

    }

}

