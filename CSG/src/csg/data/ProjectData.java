/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.data;

import csg.CSGApp;
import djf.components.AppDataComponent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Navin
 */
public class ProjectData implements AppDataComponent{
    
    CSGApp app;
    ObservableList<Team> teams;
    ObservableList<Student> students;
    
    public ProjectData(CSGApp initapp){
        teams = FXCollections.observableArrayList();
        students = FXCollections.observableArrayList();
        
        app = initapp;
    }

    public ObservableList<Team> getTeams() {
        return teams;
    }

    public ObservableList<Student> getStudents() {
        return students;
    }
    
    
    @Override
    public void resetData() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
