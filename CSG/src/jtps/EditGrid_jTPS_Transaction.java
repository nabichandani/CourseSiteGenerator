/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jtps;
import java.util.HashMap;
import javafx.beans.property.StringProperty;
import javafx.beans.property.adapter.JavaBeanStringProperty;
import csg.CSGApp;
import csg.data.TeachingAssistant;
import csg.data.TAData;
import csg.ui.CSGWorkspace;


/**
 *
 * @author Navin
 */
public class EditGrid_jTPS_Transaction implements jTPS_Transaction{
    CSGApp app;
    TAData data;
    CSGWorkspace workspace;
    int newStart;
    int newEnd;
    int oldStart;
    int oldEnd;
    HashMap<String, StringProperty> oldHashMap;
    
    public EditGrid_jTPS_Transaction(CSGApp application, int nStart, 
      int nEnd, int oStart, int oEnd, HashMap<String, StringProperty> oldHash){
        app = application;
        data = (TAData)app.getTADataComponent();
        workspace = (CSGWorkspace) app.getWorkspaceComponent();
        newStart = nStart;
        newEnd = nEnd;
        oldStart = oStart;
        oldEnd = oEnd;
        oldHashMap = new HashMap<String, StringProperty>(oldHash);
        for(String key: oldHash.keySet()){
            StringProperty strProp = oldHash.get(key);
            oldHashMap.put(key, strProp);   
        }
        
    }

    @Override
    public void doTransaction() {
        int prevStartIndex = oldStart * 2;
        workspace.resetGrid(); 
        data.initOfficeHours(newStart, newEnd);
        
        for(String key: oldHashMap.keySet()){
            String[] keyArr = key.split("_");
            int hour = Integer.parseInt(keyArr[1]);
            int startIndex = 2 * newStart;
            int endIndex = 2 * newEnd;
            hour = (hour - 1) + prevStartIndex;
            if(hour == prevStartIndex - 1){   
            }
            else if(hour >= startIndex && hour < endIndex){
                hour = hour - startIndex + 1;
                String newKey = keyArr[0] + "_" + hour;
                data.addToGrid(newKey, oldHashMap.get(key).getValue());
            }
        }
    }

    @Override
    public void undoTransaction() {
        int prevStartIndex = newStart * 2;
        workspace.resetGrid(); 
        data.initOfficeHours(oldStart, oldEnd);
        for(String key: oldHashMap.keySet()){
             data.addToGrid(key, oldHashMap.get(key).getValue());
        }
        
    }
}
