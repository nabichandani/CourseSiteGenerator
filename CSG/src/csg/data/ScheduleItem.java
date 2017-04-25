/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.data;

import java.time.LocalDate;
/**
 *
 * @author Navin
 */
public class ScheduleItem <E extends Comparable<E>> implements Comparable<E>{
    String type;
    LocalDate date;
    String time;
    String title;
    String topic;
    String link;
    String criteria;

    public ScheduleItem(String type, LocalDate date, String time, String title,
         String topic, String link, String criteria) {
        this.type = type;
        this.date = date;
        this.time = time;
        this.title = title;
        this.topic = topic;
        this.link = link;
        this.criteria = criteria;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    public int getDateMon(){
        return date.getMonth().getValue();
    }
    
    public int getDateDay(){
        return date.getDayOfMonth();
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getCriteria() {
        return criteria;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }

    @Override
    public int compareTo(E otherSch) {
        return getType().compareTo(((ScheduleItem)otherSch).getType());
    }
    
    
    
}
