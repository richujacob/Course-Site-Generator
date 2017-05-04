/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jtps;

import java.util.regex.Pattern;
import jtps.jTPS_Transaction;
import csg.csg_App;
import csg.data.csg_TAData;
import csg.workspace.csg_TAController;
import csg.workspace.csg_Workspace;
/**
 *
 * @author Richu
 */
public class RecAdderUR implements jTPS_Transaction {
    private String section;
    private String instructor;
    private String dayTime;
    private String location;
    private String ta1;
    private String ta2;
    private csg_App app;
    private csg_Workspace workspace;
    
    public RecAdderUR(csg_App app){
        this.app = app;
        workspace = (csg_Workspace)app.getWorkspaceComponent();
        section = workspace.getSectionField().getText();
        instructor = workspace.getInstructorField().getText();
        dayTime = workspace.getDayTimeField().getText();
        location = workspace.getLocationField().getText();
        ta1 = (String)workspace.getSupervisingTA().getValue().toString();
        try{
        
        ta2 = (String)workspace.getSupervisingTA2().getValue().toString();
        }catch(NullPointerException e){
            ta2="";
        }
    }
    
    @Override
    public void doTransaction(){
        if(!ta2.isEmpty()){
            ((csg_TAData)app.getDataComponent()).addRec(section, instructor, dayTime, location, ta1, ta2);
        }else{
            ((csg_TAData)app.getDataComponent()).addRec(section, instructor, dayTime, location, ta1, "");
        }
    }

    @Override
    public void undoTransaction() {
        ((csg_TAData)app.getDataComponent()).removeRecitation(section);
    }
    
    
}
