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
    
    public boolean containsScheduleItem(String time) {
        for (ScheduleItem schItem: schedule) {
            if (schItem.getTime().equals(time)) {
                return true;
            }  
        }
        return false;
    }
    
    
    public void addScheduleItem(String type, LocalDate date, String time, String title,
         String topic, String link, String criteria) {
        // MAKE THE TA
        ScheduleItem schItem = new ScheduleItem(type, date, time, title,
         topic, link, criteria);

        // ADD THE TA
        if (!containsScheduleItem(time)) {
               schedule.add(schItem);
            }

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
