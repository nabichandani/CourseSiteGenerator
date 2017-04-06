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
public class Recitation {
   String section;
   String instructor;
   String dayTime;
   String location;
   String firstTA;
   String secondTA;
   
   public Recitation(String section, String instructor, String dayTime, 
        String location, TeachingAssistant taOne, TeachingAssistant taTwo){  
       
       this.section = section;
       this.location = location;
       this.instructor = instructor;
       this.dayTime = dayTime;
       firstTA = taOne.getName();
       secondTA = taTwo.getName();
   }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getDayTime() {
        return dayTime;
    }

    public void setDayTime(String dayTime) {
        this.dayTime = dayTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getFirstTA() {
        return firstTA;
    }

    public void setFirstTA(String firstTA) {
        this.firstTA = firstTA;
    }

    public String getSecondTA() {
        return secondTA;
    }

    public void setSecondTA(String secondTA) {
        this.secondTA = secondTA;
    }


   
   
}
