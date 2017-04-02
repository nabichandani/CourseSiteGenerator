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
public class Student {
    String firstName;
    String lastName;
    Team team;
    String role;
    
    public Student(String firstName, String lastName, Team team, String role){
        this.firstName = firstName;
        this.lastName = lastName;
        this.team = team;
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    
    public boolean isUnique(Student student){
        if(this.firstName.equals(student.getFirstName())){
             if(this.lastName.equals(student.getLastName())){
                  if(this.team.getName().equals(student.getTeam().getName())){
                       if(this.role.equals(student.getRole())){
                           return true;
                       }
                  }
             }
        }
        return false;
    }
}
