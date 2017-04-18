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
public class Student <E extends Comparable<E>> implements Comparable<E>{
    String firstName;
    String lastName;
    String team;
    String role;
    
    public Student(String firstName, String lastName, String team, String role){
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

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
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
                  if(this.team.equals(student.getTeam())){
                       if(this.role.equals(student.getRole())){
                           return true;
                       }
                  }
             }
        }
        return false;
    }

    @Override
    public int compareTo(E otherStudent) {
        return getLastName().compareTo(((Student)otherStudent).getLastName());
    }
}
