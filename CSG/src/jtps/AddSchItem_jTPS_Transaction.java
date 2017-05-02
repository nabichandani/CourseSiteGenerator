/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jtps;

import csg.CSGApp;
import csg.data.ScheduleData;
import java.time.LocalDate;

/**
 *
 * @author Navin
 */
public class AddSchItem_jTPS_Transaction implements jTPS_Transaction{
    
    CSGApp app;
    ScheduleData data;
    String type;
    LocalDate date;
    String time;
    String title;
    String topic;
    String link;
    String criteria;
    
    
    public AddSchItem_jTPS_Transaction(CSGApp app, String type, LocalDate date, 
      String time, String title, String topic, String link, String criteria){
        
        this.app = app;
        data = (ScheduleData) app.getScheduleDataComponent();
        this.type = type;
        this.date = date;
        this.time = time;
        this.title = title;
        this.topic = topic;
        this.link = link;
        this.criteria = criteria;
    
    }
    
    @Override
    public void doTransaction() {
       data.addScheduleItem(type, date, time, title, topic, link, criteria);
    }

    @Override
    public void undoTransaction() {
        data.getSchedule().remove(data.getScheduleItem(type, date, time, title,
            topic, link, criteria));
    }
    
}
