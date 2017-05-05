package jtps;

import java.util.ArrayList;
import java.util.HashMap;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import jtps.jTPS_Transaction;
import csg.csg_App;
import csg.data.csg_TAData;
import csg.workspace.csg_Workspace;
/**
 *
 * @author Richu
 */
public class schDeleteUR implements jTPS_Transaction {
    private csg_App app;
    private csg_TAData data;
    private String type;
    private String date;
    private String title;
    private String topic;
    private String time;
    private String link;
    private String criteria;
    
    public schDeleteUR(csg_App app, String title){
        this.app = app;
        //data = (csg_TAData)app.getDataComponent();
        this.title=title;
        this.type = data.getTitle(title).getType();
        this.date = data.getTitle(title).getDate();
        this.topic = data.getTitle(title).getTopic();
        this.time = data.getTitle(title).getTime();
        this.link = data.getTitle(title).getLink();
        this.criteria = data.getTitle(title).getCriteria();
    }
    
    public void doTransaction(){
        ((csg_TAData)app.getDataComponent()).removeSchedule(title);
    }
    
    public void undoTransaction(){
        ((csg_TAData) app.getDataComponent()).addSchedule(type, date, title, topic, time, link, criteria);
    }
}
