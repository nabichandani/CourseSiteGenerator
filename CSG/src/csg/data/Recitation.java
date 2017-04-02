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
   TeachingAssistant firstTA;
   TeachingAssistant secondTA;
   
   public Recitation(String section, String recitation, String dayTime, 
        String location, TeachingAssistant taOne, TeachingAssistant taTwo){  
       
       this.location = location;
       this.instructor = instructor;
       this.dayTime = dayTime;
       firstTA = taOne;
       secondTA = taTwo;
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

    public TeachingAssistant getFirstTA() {
        return firstTA;
    }

    public void setFirstTA(TeachingAssistant firstTA) {
        this.firstTA = firstTA;
    }

    public TeachingAssistant getSecondTA() {
        return secondTA;
    }

    public void setSecondTA(TeachingAssistant secondTA) {
        this.secondTA = secondTA;
    }
   
   
}
