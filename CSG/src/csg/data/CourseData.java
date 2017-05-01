/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.data;

import csg.CSGApp;
import csg.ui.CSGWorkspace;
import djf.components.AppDataComponent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Navin
 */
public class CourseData implements AppDataComponent{
    CSGApp app;
    CSGWorkspace workspace;
 
    ObservableList<CourseTemplate> templates;
    CourseTemplate home;
    CourseTemplate syllabus;
    CourseTemplate schedule;
    CourseTemplate hws;
    CourseTemplate projects;
    
    String subject;
    String number;
    String semester;
    String year;
    String title;
    String insName;
    String insHome;
    String bannerLink;
    String leftFooterLink;
    String rightFooterLink;
    String exportDir;
    String templateDir;
    String styleSheet;
    
    public CourseData(CSGApp initApp){
        app = initApp;   
        templates = FXCollections.observableArrayList();
        home = new CourseTemplate(false, "Home", "index.html", "Homebuilder.js");
        syllabus = new CourseTemplate(false, "Syllabus", 
            "syllabus.html", "SyllabusBuilder.js");
        schedule = new CourseTemplate(false, "Schedule",
            "schedule.html", "ScheduleBuilder.js");
        hws = new CourseTemplate(false, "HWs", "hws.html", "HWsBuilder.js");
        projects = new CourseTemplate(false, "Projects", 
            "projects.html", "ProjectsBuilder.js");
        templates.add(home);
        templates.add(syllabus);
        templates.add(schedule);
        templates.add(hws);
        templates.add(projects);
        
        subject = "";
        number = "";
        semester = "";
        year = "";
        title = "";
        insName = "";
        insHome = "";
        bannerLink = "";
        leftFooterLink = "";
        rightFooterLink = "";
        exportDir = "";
        templateDir = "";
        styleSheet = "";
    }

    public String getSubject() {
        return subject;
    }

    public String getNumber() {
        return number;
    }

    public String getSemester() {
        return semester;
    }

    public String getYear() {
        return year;
    }

    public String getTitle() {
        return title;
    }

    public String getInsName() {
        return insName;
    }

    public String getInsHome() {
        return insHome;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setInsName(String insName) {
        this.insName = insName;
    }

    public String getExportDir() {
        return exportDir;
    }

    public void setExportDir(String exportDir) {
        this.exportDir = exportDir;
    }

    public String getTemplateDir() {
        return templateDir;
    }

    public void setTemplateDir(String templateDir) {
        this.templateDir = templateDir;
    }

    public String getStyleSheet() {
        return styleSheet;
    }

    public void setStyleSheet(String styleSheet) {
        this.styleSheet = styleSheet;
    }
    

    public void setInsHome(String insHome) {
        this.insHome = insHome;
    }

    public String getBannerLink() {
        return bannerLink;
    }

    public void setBannerLink(String bannerLink) {
        this.bannerLink = bannerLink;
    }

    public String getLeftFooterLink() {
        return leftFooterLink;
    }

    public void setLeftFooterLink(String leftFooterLink) {
        this.leftFooterLink = leftFooterLink;
    }

    public String getRightFooterLink() {
        return rightFooterLink;
    }

    public void setRightFooterLink(String rightFooterLink) {
        this.rightFooterLink = rightFooterLink;
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
        templates.clear();
        
                home = new CourseTemplate(false, "Home", "index.html", "Homebuilder.js");
        syllabus = new CourseTemplate(false, "Syllabus", 
            "syllabus.html", "SyllabusBuilder.js");
        schedule = new CourseTemplate(false, "Schedule",
            "schedule.html", "ScheduleBuilder.js");
        hws = new CourseTemplate(false, "HWs", "hws.html", "HWsBuilder.js");
        projects = new CourseTemplate(false, "Projects", 
            "projects.html", "ProjectsBuilder.js");
        templates.add(home);
        templates.add(syllabus);
        templates.add(schedule);
        templates.add(hws);
        templates.add(projects);
        
        subject = "";
        number = "";
        semester = "";
        year = "";
        title = "";
        insName = "";
        insHome = "";
        bannerLink = "";
        leftFooterLink = "";
        rightFooterLink = "";
        exportDir = "";
        templateDir = "";
        styleSheet = "";
    }
    
}
