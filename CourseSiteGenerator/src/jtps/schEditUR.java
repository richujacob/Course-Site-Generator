
package jtps;

import javafx.scene.control.TableView;
import jtps.jTPS_Transaction;
import csg.csg_App;
import csg.data.csg_TAData;
import csg.data.csg_Schedule;
import csg.workspace.csg_Workspace;
import java.time.format.DateTimeFormatter;
/**
 *
 * @author Richu
 */
public class schEditUR implements  jTPS_Transaction{
    private String type;
    private String date;
    private String title;
    private String topic;
    private String time;
    private String link;
    private String criteria;
    private String newType;
    private String newDate;
    private String newTitle;
    private String newTopic;
    private String newTime;
    private String newLink;
    private String newCriteria;
    private csg_App app;
    private csg_Workspace workspace;
    private csg_TAData data;
    
    public schEditUR(csg_App app){
        this.app = app;
        csg_Workspace workspace = (csg_Workspace)app.getWorkspaceComponent();
        data = (csg_TAData)app.getDataComponent();
        newType = (String)workspace.getTypeEventBox().getValue();
        newDate = workspace.getDate3().getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        newTitle = workspace.getTitleScheduleField().getText();
        newTopic = workspace.getTopicField().getText();
        newTime = workspace.getTimeField().getText();
        newLink = workspace.getLinkField().getText();
        newCriteria = workspace.getCriteriaField().getText();
        TableView schTable = workspace.getScheduleTable();
        Object selectedItem  = schTable.getSelectionModel().getSelectedItem();
        csg_Schedule sch = (csg_Schedule)selectedItem;
        type = sch.getType();
        date = sch.getDate();
        title = sch.getTitle();
        topic = sch.getTopic();
        time = sch.getTime();
        link = sch.getLink();
        criteria = sch.getCriteria();   
        
        
    }

    @Override
    public void doTransaction() {
        data.removeSchedule(title);
        data.addSchedule(newType, newDate, newTitle, newTopic, newTime, newLink, newCriteria);
        csg_Workspace workspace  = (csg_Workspace)app.getWorkspaceComponent();
        TableView schTable  = workspace.getScheduleTable();
        schTable.getSelectionModel().select(data.getTitle(newTitle));
    }

    @Override
    public void undoTransaction() {
        data.removeSchedule(newTitle);
        data.addSchedule(type, date, title, topic, time, link, criteria);
        csg_Workspace workspace = (csg_Workspace)app.getWorkspaceComponent();
        TableView schTable = workspace.getScheduleTable();
        schTable.getSelectionModel().select(data.getTitle(title));
    }
    
    
}
