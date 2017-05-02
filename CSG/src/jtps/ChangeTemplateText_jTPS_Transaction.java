/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jtps;

import csg.CSGApp;
import csg.ui.CSGWorkspace;

/**
 *
 * @author Navin
 */
public class ChangeTemplateText_jTPS_Transaction implements jTPS_Transaction{
    CSGApp app;
    CSGWorkspace workspace;
    String prev;
    String post;
   
    public ChangeTemplateText_jTPS_Transaction(CSGApp app, String prevString, String newString){
        this.app = app;
        workspace = (CSGWorkspace)app.getWorkspaceComponent();
        prev = prevString;
        post = newString;
        
    }
    @Override
    public void doTransaction() {
        workspace.getCourseTemplateLocLabel().setText(post);
    }

    @Override
    public void undoTransaction() {
        workspace.getCourseTemplateLocLabel().setText(prev);
    }
    
}
