/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.data;

/**
 *
 * @author Navin
 */
public class CourseInfo {
    String subject;
    String semester;
    String number;
    String title;
    String instructorName;
    String instructorHome;
    String link;
    
    public CourseInfo(String subject, String semester, String number, 
        String title, String insName, String insHome, String link){
        
        this.subject = subject;
        this.semester = semester;
        this.number = number;
        this.title = title;
        instructorName = insName;
        instructorHome = insHome;
        this.link = link;  
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public String getInstructorHome() {
        return instructorHome;
    }

    public void setInstructorHome(String instructorHome) {
        this.instructorHome = instructorHome;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
    
    
}
