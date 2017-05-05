/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jtps;

import java.util.regex.Pattern;
import jtps.jTPS_Transaction;
import csg.csg_App;
import csg.data.csg_Schedule;
import csg.data.csg_TAData;
import csg.workspace.csg_TAController;
import csg.workspace.csg_Workspace;
import java.time.format.DateTimeFormatter;
/**
 *
 * @author Richu
 */
public class schAdderUR implements jTPS_Transaction {
    private String type;
    private String date;
    private String title;
    private String topic;
    private String time;
    private String link;
    private String criteria;
    private csg_App app;
    private csg_Workspace workspace;
    private csg_Schedule sch;
    
    public schAdderUR(csg_App app){
        this.app=app;
        workspace = (csg_Workspace)app.getWorkspaceComponent();
        type = (String)workspace.getTypeEventBox().getValue();
        date = workspace.getDate3().getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        title = workspace.getTitleScheduleField().getText();
        topic = workspace.getTopicField().getText();
        time = workspace.getTimeField().getText();
        link  = workspace.getLinkField().getText();
        criteria = workspace.getCriteriaField().getText();
        //sch = new csg_Schedule(type, date, title, topic);
    }
    
    public void doTransaction(){
        ((csg_TAData) app.getDataComponent()).addSchedule(type, date, title, topic, time, link, criteria);
//        sch.setTime(time);
//        sch.setLink(link);
//        sch.setCriteria(criteria);        
    }
    
    public void undoTransaction(){
        ((csg_TAData)app.getDataComponent()).removeSchedule(title);
//        sch.setTime(null);
//        sch.setLink(null);
//        sch.setCriteria(null);
    }
}
