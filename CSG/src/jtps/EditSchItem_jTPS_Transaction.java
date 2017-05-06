/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jtps;

import csg.CSGApp;
import csg.CSGProp;
import csg.data.ScheduleData;
import csg.data.ScheduleItem;
import csg.ui.CSGWorkspace;
import java.time.LocalDate;
import properties_manager.PropertiesManager;

/**
 *
 * @author Navin
 */
public class EditSchItem_jTPS_Transaction implements jTPS_Transaction {

    CSGApp app;
    ScheduleData data;
    CSGWorkspace workspace;
    ScheduleItem oldSchItem;
    ScheduleItem newSchItem;

    public EditSchItem_jTPS_Transaction(CSGApp app, ScheduleItem oldSchItem, ScheduleItem newSchItem) {
        this.app = app;
        workspace = (CSGWorkspace) app.getWorkspaceComponent();
        data = (ScheduleData) app.getScheduleDataComponent();
        this.oldSchItem = new ScheduleItem(oldSchItem.getType(), oldSchItem.getDate(),
                oldSchItem.getTime(), oldSchItem.getTitle(), oldSchItem.getTopic(),
                oldSchItem.getLink(), oldSchItem.getCriteria());
        this.newSchItem = new ScheduleItem(newSchItem.getType(), newSchItem.getDate(),
                newSchItem.getTime(), newSchItem.getTitle(), newSchItem.getTopic(),
                newSchItem.getLink(), newSchItem.getCriteria());
    }

    @Override
    public void doTransaction() {
        data.getSchedule().remove(data.getScheduleItem(oldSchItem.getType(), oldSchItem.getDate(),
                oldSchItem.getTime(), oldSchItem.getTitle(), oldSchItem.getTopic(),
                oldSchItem.getLink(), oldSchItem.getCriteria()));
        data.addScheduleItem(newSchItem);
        workspace.getScheduleTable().getSelectionModel().select(data.getScheduleItem(newSchItem.getType(), newSchItem.getDate(),
                newSchItem.getTime(), newSchItem.getTitle(), newSchItem.getTopic(),
                newSchItem.getLink(), newSchItem.getCriteria()));
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        workspace.getScheduleAddUpdateButton().setText(props.getProperty(CSGProp.ADDEDIT2_TEXT
                .toString()));
        workspace.getScheduleCriteriaTextField().setText(newSchItem.getCriteria());
        workspace.getScheduleDatePicker().setValue(newSchItem.getDate());
        workspace.getScheduleLinkTextField().setText(newSchItem.getLink());
        workspace.getScheduleTimeTextField().setText(newSchItem.getTime());
        workspace.getScheduleTitleTextField().setText(oldSchItem.getTitle());
        workspace.getScheduleTopicTextField().setText(newSchItem.getTopic());
        workspace.getScheduleTypeCombo().setValue(newSchItem.getType());
    }

    @Override
    public void undoTransaction() {
        data.getSchedule().remove(data.getScheduleItem(newSchItem.getType(), newSchItem.getDate(),
                newSchItem.getTime(), newSchItem.getTitle(), newSchItem.getTopic(),
                newSchItem.getLink(), newSchItem.getCriteria()));
        data.addScheduleItem(oldSchItem);
                workspace.getScheduleTable().getSelectionModel().select(data.getScheduleItem(oldSchItem.getType(), oldSchItem.getDate(),
                oldSchItem.getTime(), oldSchItem.getTitle(), oldSchItem.getTopic(),
                oldSchItem.getLink(), oldSchItem.getCriteria()));         
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        workspace.getScheduleAddUpdateButton().setText(props.getProperty(CSGProp.ADDEDIT2_TEXT
                .toString()));
        workspace.getScheduleCriteriaTextField().setText(oldSchItem.getCriteria());
        workspace.getScheduleDatePicker().setValue(oldSchItem.getDate());
        workspace.getScheduleLinkTextField().setText(oldSchItem.getLink());
        workspace.getScheduleTimeTextField().setText(oldSchItem.getTime());
        workspace.getScheduleTitleTextField().setText(oldSchItem.getTitle());
        workspace.getScheduleTopicTextField().setText(oldSchItem.getTopic());
        workspace.getScheduleTypeCombo().setValue(oldSchItem.getType());
    }

}
