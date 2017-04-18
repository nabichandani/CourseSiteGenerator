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
public class CourseData implements AppDataComponent{
    CSGApp app;
 
    ObservableList<CourseTemplate> templates;
    CourseTemplate home;
    CourseTemplate syllabus;
    CourseTemplate schedule;
    CourseTemplate hws;
    CourseTemplate projects;
    
    public CourseData(CSGApp initApp){
        app = initApp;   
        templates = FXCollections.observableArrayList();
        home = new CourseTemplate(true, "Home", "index.html", "Homebuilder.js");
        syllabus = new CourseTemplate(true, "Syllabus", 
            "syllabus.html", "SyllabusBuilder.js");
        schedule = new CourseTemplate(true, "Schedule",
            "schedule.html", "ScheduleBuilder.js");
        hws = new CourseTemplate(true, "HWs", "hws.html", "HWsBuilder.js");
        projects = new CourseTemplate(true, "Projects", 
            "projects.html", "ProjectsBuilder.html");
        templates.add(home);
        templates.add(syllabus);
        templates.add(schedule);
        templates.add(hws);
        templates.add(projects);
    }

    public ObservableList<CourseTemplate> getTemplates() {
        return templates;
    }
    
    public void addTemplate(boolean use, String navbarTitle,
        String fileName, String script){
        CourseTemplate newTemplate = new CourseTemplate(use, navbarTitle,
        fileName, script);
        templates.add(newTemplate);
    }
    
    
    @Override
    public void resetData() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
