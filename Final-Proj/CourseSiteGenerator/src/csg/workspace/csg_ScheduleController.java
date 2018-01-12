
package csg.workspace;

import djf.controller.AppFileController;
import djf.ui.AppGUI;
import static csg.csg_Prop.*;
import djf.ui.AppMessageDialogSingleton;
import djf.ui.AppYesNoCancelDialogSingleton;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import properties_manager.PropertiesManager;
import csg.csg_App;
import csg.data.csg_TAData;
import csg.data.csg_Schedule;
import csg.style.csg_Style;
import csg.workspace.csg_Workspace;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyEvent;
import jtps.jTPS;
import jtps.jTPS_Transaction;
import csg.csg_Prop;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import jtps.schAdderUR;
import jtps.schDeleteUR;
import jtps.schEditUR;

/**
 *
 * @author Richu
 */
public class csg_ScheduleController {
    static jTPS jTPS = new jTPS();
    // THE APP PROVIDES ACCESS TO OTHER COMPONENTS AS NEEDED
    csg_App app;
    
    public csg_ScheduleController(csg_App initApp){
        app = initApp;
    }
    
    private void markWorkAsEdited() {
        // MARK WORK AS EDITED
        AppGUI gui = app.getGUI();
        gui.getFileController().markAsEdited(gui);
    }
    
    public void handleCalenderBounds(){
        try{
        boolean validDate = false;
        csg_Workspace workspace = (csg_Workspace)app.getWorkspaceComponent();
        csg_TAData data = (csg_TAData)app.getDataComponent();
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String mondayDate = workspace.getDate().getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        String fridayDate = workspace.getDate2().getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        if(workspace.getDate().getValue().getDayOfWeek().getValue()==1){
            if(workspace.getDate2().getValue().getDayOfWeek().getValue()==5){
                validDate = true;
            }else{
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(END_NOT_FRIDAY_TITLE), props.getProperty(END_NOT_FRIDAY_MESSAGE));
                workspace.getDate2().setValue(null);
                workspace.getDate().setValue(null);
                workspace.getDate().requestFocus();
            }
        }
        else{
            if(workspace.getDate2().getValue().getDayOfWeek().getValue()==5){
                workspace.getDate2().setValue(null);
                workspace.getDate().requestFocus();
                if(workspace.getDate().getValue().getDayOfWeek().getValue()==1){
                    
                }else{
                    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                    dialog.show(props.getProperty(START_NOT_MONDAY_TITLE), props.getProperty(START_NOT_MONDAY_MESSAGE));
                    workspace.getDate().setValue(null);
                    workspace.getDate().requestFocus();
                }
            }else{
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(END_NOT_FRIDAY_TITLE), props.getProperty(END_NOT_FRIDAY_MESSAGE));
                workspace.getDate2().setValue(null);
                workspace.getDate().requestFocus();
            }
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(START_NOT_MONDAY_TITLE), props.getProperty(START_NOT_MONDAY_MESSAGE));
            workspace.getDate().setValue(null);
            workspace.getDate().requestFocus();
            //workspace.getDate().setValue();
        }
        
        if(workspace.getDate2().getValue().getDayOfWeek().getValue()==5){
            if(workspace.getDate().getValue().getDayOfWeek().getValue()==1){
                validDate = true;    
                }else{
                    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                    dialog.show(props.getProperty(START_NOT_MONDAY_TITLE), props.getProperty(START_NOT_MONDAY_MESSAGE));
                    workspace.getDate().setValue(null);
                    workspace.getDate().requestFocus();
                }
        }else{
            if(workspace.getDate().getValue().getDayOfWeek().getValue()==1){
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(END_NOT_FRIDAY_TITLE), props.getProperty(END_NOT_FRIDAY_MESSAGE));
                workspace.getDate2().setValue(null);
                workspace.getDate().requestFocus();
            }else{
                    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                    dialog.show(props.getProperty(START_NOT_MONDAY_TITLE), props.getProperty(START_NOT_MONDAY_MESSAGE));
                    workspace.getDate().setValue(null);
                    workspace.getDate().requestFocus();
            }
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(END_NOT_FRIDAY_TITLE), props.getProperty(END_NOT_FRIDAY_MESSAGE));
                workspace.getDate2().setValue(null);
                workspace.getDate().requestFocus();
        }
        
        if(validDate = true){
            if(workspace.getDate().getValue().compareTo(workspace.getDate2().getValue())<0){
                
            }else{
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(SCH_START_BEFORE_END_TITLE), props.getProperty(SCH_START_BEFORE_END_MESSAGE));
                workspace.getDate2().setValue(null);
                workspace.getDate().setValue(null);
                workspace.getDate().requestFocus();
            }
        }
        }catch(NullPointerException e){
            
        }
        
    }
    
    public void handleAddSch(){
        try{
        csg_Workspace workspace = (csg_Workspace)app.getWorkspaceComponent();
        String type = (String)workspace.getTypeEventBox().getValue();
        String date = workspace.getDate3().getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        TextField timeField = workspace.getTimeField();
        TextField titleField = workspace.getTitleScheduleField();
        TextField topicField = workspace.getTopicField();
        TextField linkField = workspace.getLinkField();
        TextField criteriaField = workspace.getCriteriaField();
        String time = timeField.getText();
        String title = titleField.getText();
        String topic  = topicField.getText();
        String link = linkField.getText();
        String criteria = criteriaField.getText();       
       
        
        csg_TAData data = (csg_TAData)app.getDataComponent();
        PropertiesManager props = PropertiesManager.getPropertiesManager();
         
        if(workspace.getTypeEventBox().getSelectionModel().isEmpty()){
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(TYPE_MISSING_TITLE), props.getProperty(TYPE_MISSING_MESSAGE));
        }else if(date.equals(null)){
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(DATE_MISSING_TITLE), props.getProperty(DATE_MISSING_MESSAGE));
        }else if(title.isEmpty()){
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(TITLE_MISSING_TITLE), props.getProperty(TITLE_MISSING_MESSAGE));
        }else if(time.isEmpty()){
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(TIME_MISSING_TITLE), props.getProperty(TIME_MISSING_MESSAGE));
        }
        else if(data.containSch(title)){
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(SCH_NOT_UNIQUE_TITLE), props.getProperty(SCH_NOT_UNIQUE_MESSAGE));
            workspace.getTypeEventBox().getSelectionModel().clearSelection();
            workspace.getDate3().setValue(null);
            timeField.setText("");
            titleField.setText("");
            topicField.setText("");
            linkField.setText("");
            criteriaField.setText("");
        }else{
            jTPS_Transaction addSchUR = new schAdderUR(app);
            jTPS.addTransaction(addSchUR);
            
            workspace.getTypeEventBox().getSelectionModel().clearSelection();
            workspace.getDate3().setValue(null);
            timeField.setText("");
            titleField.setText("");
            topicField.setText("");
            linkField.setText("");
            criteriaField.setText("");
            
            
            markWorkAsEdited();
            
        }
        }catch(NullPointerException e){
            
        } 
        
    }
    
    public void loadSchToText(){
        csg_Workspace workspace = (csg_Workspace)app.getWorkspaceComponent();
        TableView schTable = workspace.getScheduleTable();
        Object selectedItem = schTable.getSelectionModel().getSelectedItem();
        if(selectedItem!=null){
            csg_Schedule sch = (csg_Schedule)selectedItem;
            String type = sch.getType();
            String date = sch.getDate();
            String title = sch.getTitle();
            String topic = sch.getTopic();
            String time = sch.getTime();
            String link = sch.getLink();
            String criteria  = sch.getCriteria();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            LocalDate localDate = LocalDate.parse(date, formatter);
            workspace.getTypeEventBox().setValue(type);
            workspace.getDate3().setValue(localDate);
            workspace.getTitleScheduleField().setText(title);
            workspace.getTopicField().setText(topic);
            workspace.getTimeField().setText(time);
            workspace.getLinkField().setText(link);
            workspace.getCriteriaField().setText(criteria);    
            
        }
    }
    
    public void handleEditSch(){
        csg_Workspace workspace = (csg_Workspace)app.getWorkspaceComponent();
        TableView schTable = workspace.getScheduleTable();
        Object selectedItem = schTable.getSelectionModel().getSelectedItem();
        if(selectedItem!=null){
//            csg_Schedule sch = (csg_Schedule)selectedItem;
//            String type = sch.getType();
//            String newType = (String)workspace.getTypeEventBox().getValue();
//            String date = sch.getDate();
//            String newDate = workspace.getDate3().getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
//            String title = sch.getTitle();
//            String newTitle = workspace.getTitleScheduleField().getText();
//            String topic = sch.getTopic();
//            String newTopic = workspace.getTopicField().getText();
//            String time = sch.getTime();
//            String newTime = workspace.getTimeField().getText();
//            String link = sch.getLink();
//            String newLink = workspace.getLinkField().getText();
//            String criteria = sch.getCriteria();
//            String newCriteria = workspace.getCriteriaField().getText();
            
            jTPS_Transaction editSchUR = new schEditUR(app);
            jTPS.addTransaction(editSchUR);
            
            markWorkAsEdited();
        }
    }
    
    public void handleDeleteSch(){
            csg_Workspace workspace = (csg_Workspace)app.getWorkspaceComponent();
            TableView schTable = workspace.getScheduleTable();
            
            Object selectedItem = schTable.getSelectionModel().getSelectedItem();
            if(selectedItem!=null){
                csg_Schedule sch = (csg_Schedule)selectedItem;
                String title  = sch.getTitle();
                csg_TAData data = (csg_TAData)app.getDataComponent();
                
                jTPS_Transaction deleteSch = new schDeleteUR(app, title);
                jTPS.addTransaction(deleteSch);
                
                markWorkAsEdited();
            }
    }
    
    public void Undo(){
        jTPS.undoTransaction();
        markWorkAsEdited();
    }
    public void Redo(){
        jTPS.doTransaction();
        markWorkAsEdited();
    }
}
