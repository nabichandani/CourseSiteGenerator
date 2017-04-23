/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.data;

import csg.CSGApp;
import djf.components.AppDataComponent;
import java.util.Collections;
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
    
    public Team getTeam(String testName) {
        for (Team team : teams) {
            if (team.getName().equals(testName)) {
                return team;
            }
        }
        return null;
    }
    
    public boolean containsTeam(String testName) {
        for (Team team : teams) {
            if (team.getName().equals(testName)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean containsTeamLink(String link) {
        for (Team team : teams) {
            if (team.getLink().equals(link)) {
                return true;
            }
        }
        return false;
    }
    
    public Student getStudent(String fName,String lName) {
        for (Student student: students) {
            if (student.getFirstName().equals(fName)) {
                if (student.getLastName().equals(lName)) {
                return student;
            }
            }
        }
        return null;
    }
    
    public boolean containsStudentFirstName(String testName) {
        for (Student student: students) {
            if (student.getFirstName().equals(testName)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean containsStudentLastName(String testName) {
        for (Student student: students) {
            if (student.getLastName().equals(testName)) {
                return true;
            }
        }
        return false;
    }

    public void addTeam(String name, String color, String textColor, String link) {
        // MAKE THE TA
        Team team = new Team(name, color, textColor, link);

        // ADD THE TA
        if (!containsTeam(name)) {
            if(!containsTeamLink(link)){
            teams.add(team);
            }
        }

        // SORT THE TAS
        Collections.sort(teams);
    }
    
    public void deleteTeam(Team team){
         teams.remove(team);
        
    }
    
    public void addStudent(String firstName, String lastName, String team, String role) {
        // MAKE THE TA
        Student student = new Student(firstName, lastName, team, role);

        // ADD THE TA
        if (!containsStudentFirstName(firstName)) {
            if(!containsStudentLastName(lastName)){
            students.add(student);
            }
        }

        // SORT THE TAS
        Collections.sort(students);
    }
    
    public void deleteStudents(Student student){
         teams.remove(student);
        
    }
      
    
      
      
    
    
    @Override
    public void resetData() {
        students.clear();
        teams.clear();
    }
    
}
