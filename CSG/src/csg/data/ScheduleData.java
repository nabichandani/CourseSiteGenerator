/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.data;

import csg.CSGApp;
import djf.components.AppDataComponent;
import java.time.LocalDate;
import java.util.Collections;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Navin
 */
public class ScheduleData implements AppDataComponent{
    CSGApp app;
    
    ObservableList<ScheduleItem> schedule;
    
    public ScheduleData(CSGApp initapp){
        app = initapp;
        
        schedule = FXCollections.observableArrayList();
        
    }

    public ObservableList<ScheduleItem> getSchedule() {
        return schedule;
    }
     public ScheduleItem getScheduleItem(LocalDate date) {
        for (ScheduleItem schItem : schedule) {
            if (schItem.getDate().equals(date)) {
                return schItem;
            }
        }
        return null;
    }
    
    
    public void addScheduleItem(String type, LocalDate date, String time, String title,
         String topic, String link, String criteria) {
        // MAKE THE TA
        ScheduleItem schItem = new ScheduleItem(type, date, time, title,
         topic, link, criteria);

        schedule.add(schItem);
           

        Collections.sort(schedule);
    }
    
    public boolean isInSchedule(ScheduleItem sch){
        for(ScheduleItem scheduleItem : schedule){
            if(scheduleItem.getType().equals(sch.getType())){
               if(scheduleItem.getTopic().equals(sch.getTopic())){
                   if(scheduleItem.getTitle().equals(sch.getTitle())){
                      if(scheduleItem.getTime().equals(sch.getTime())){
                          if(scheduleItem.getLink().equals(sch.getLink())){
                              if(scheduleItem.getDate().equals(sch.getDate())){
                                  if(scheduleItem.getCriteria().equals(sch.getCriteria())){
                                     return true; 
                                  }
                              }
                          }
                      } 
                   }
               }
            }
        }
        return false;
    }
    
    
    public void addScheduleItem(ScheduleItem schItem) {
        // MAKE THE TA
        schedule.add(schItem);
           

        Collections.sort(schedule);
    }
    
      public void deleteScheduleItem(ScheduleItem scheduleItem){
         schedule.remove(scheduleItem);
        
    }
   
    @Override
    public void resetData() {
        schedule.clear();
    }
    
}
