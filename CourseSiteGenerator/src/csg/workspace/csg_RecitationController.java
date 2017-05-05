
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
import csg.data.csg_Recitation;
import csg.style.csg_Style;
import csg.workspace.csg_Workspace;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyEvent;
import jtps.jTPS;
import jtps.jTPS_Transaction;
import csg.csg_Prop;
import jtps.RecAdderUR;
import jtps.RecDeleteUR;
import jtps.RecReplaceUR;
/**
 *
 * @author Richu
 */
public class csg_RecitationController {
    static jTPS jTPS = new jTPS();
    // THE APP PROVIDES ACCESS TO OTHER COMPONENTS AS NEEDED
    csg_App app;
    
    public csg_RecitationController(csg_App initApp){
        app = initApp;
    }
    
    private void markWorkAsEdited() {
        // MARK WORK AS EDITED
        AppGUI gui = app.getGUI();
        gui.getFileController().markAsEdited(gui);
    }
    
    public void handleAddRec(){
        try{
        csg_Workspace workspace = (csg_Workspace)app.getWorkspaceComponent();
        TextField sectionField = workspace.getSectionField();
        TextField instructorField = workspace.getInstructorField();
        TextField dayTimeField = workspace.getDayTimeField();
        TextField locationField  = workspace.getLocationField();
        String section =  sectionField.getText();
        String instructor = instructorField.getText();
        String dayTime = dayTimeField.getText();
        String location = locationField.getText();
        String TA1 = (String)workspace.getSupervisingTA().getValue().toString();
        String TA2 = (String)workspace.getSupervisingTA2().getValue().toString();
        //String ta1 = "";
        //String ta2 = "sdfsd";
        
        csg_TAData data = (csg_TAData)app.getDataComponent();
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        if(section.isEmpty()){
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_SECTION_REC), props.getProperty(MISSING_SECTION_REC));     
        }else if(instructor.isEmpty()){
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_INSTRUCTOR_TITLE), props.getProperty(MISSING_INSTRUCTOR_REC)); 
        }else if(dayTime.isEmpty()){
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_DAYTIME_TITLE), props.getProperty(MISSING_DAYTIME_REC)); 
        }else if(location.isEmpty()){
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_LOCATION_TITLE), props.getProperty(MISSING_LOCATION_REC)); 
        }else if(workspace.getSupervisingTA().getSelectionModel().isEmpty()){
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_SUPERVISING1_TITLE), props.getProperty(MISSING_SUPERVISING1_REC)); 
        }//else if(workspace.getSupervisingTA2().getSelectionModel().isEmpty()){
            //AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    //dialog.show(props.getProperty(MISSING_SUPERVISING2_TITLE), props.getProperty(MISSING_SUPERVISING2_REC)); 
        //}
        else if(data.containsRec(section, instructor, dayTime, location, TA1, TA2)){
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(REC_TITLE_NOT_UNIQUE_TITLE), props.getProperty(REC_TITLE_NOT_UNIQUE));
        }
        else{
            
            jTPS_Transaction addRecUR = new RecAdderUR(app);
            jTPS.addTransaction(addRecUR);
            
            sectionField.setText("");
            instructorField.setText("");
            dayTimeField.setText("");
            locationField.setText("");
            workspace.getSupervisingTA().getSelectionModel().clearSelection();
            workspace.getSupervisingTA2().getSelectionModel().clearSelection();
            markWorkAsEdited();
        }
        
        }catch(NullPointerException e){
            csg_Workspace workspace = (csg_Workspace)app.getWorkspaceComponent();
        TextField sectionField = workspace.getSectionField();
        TextField instructorField = workspace.getInstructorField();
        TextField dayTimeField = workspace.getDayTimeField();
        TextField locationField  = workspace.getLocationField();
        String section =  sectionField.getText();
        String instructor = instructorField.getText();
        String dayTime = dayTimeField.getText();
        String location = locationField.getText();
            jTPS_Transaction addRecUR = new RecAdderUR(app);
            jTPS.addTransaction(addRecUR);
            
            sectionField.setText("");
            instructorField.setText("");
            dayTimeField.setText("");
            locationField.setText("");
            workspace.getSupervisingTA().getSelectionModel().clearSelection();
            workspace.getSupervisingTA2().getSelectionModel().clearSelection();
            markWorkAsEdited();
        }
    }
    
    public void editExistingRecitation(){
        csg_Workspace workspace = (csg_Workspace)app.getWorkspaceComponent();
        TableView recTable = workspace.getRecitationTable();
        Object selectedItem = recTable.getSelectionModel().getSelectedItem();
        if(selectedItem!=null){
//            csg_Recitation rec  = (csg_Recitation)selectedItem;
//            String section = rec.getSection();
//            String newSection = workspace.getSectionField().getText();
//            String instructor = rec.getInstructor();
//            String newInstructor = workspace.getInstructorField().getText();
//            String dayTime = rec.getDayTime();
//            String newDayTime = workspace.getDayTimeField().getText();
//            String location = rec.getLocation();
//            String newLocation = workspace.getLocationField().getText();
//            String supervisingTA1 = rec.getTa();
//            String newTA1 = (String)workspace.getSupervisingTA().getValue();
//            String supervisingTA2 = rec.getTa2();
//            String newTA2 = (String)workspace.getSupervisingTA2().getValue();
            jTPS_Transaction replaceRecUR = new RecReplaceUR(app);
            jTPS.addTransaction(replaceRecUR);
            
            //jTPS_Transaction replaceTAUR = (jTPS_Transaction) new RecReplaceUR(app);
            //jTPS.addTransaction(replaceTAUR);
            markWorkAsEdited();
            
        }
    }
    
    public void loadRectotext(){
        csg_Workspace workspace = (csg_Workspace)app.getWorkspaceComponent();
        TableView recTable = workspace.getRecitationTable();
        Object selectedItem = recTable.getSelectionModel().getSelectedItem();
        if(selectedItem != null){
            csg_Recitation rec = (csg_Recitation)selectedItem;
            String section = rec.getSection();
            String instructor = rec.getInstructor();
            String dayTime = rec.getDayTime();
            String location = rec.getLocation();
            String ta1 = rec.getTa();
            String ta2 = rec.getTa2();
            workspace.getSectionField().setText(section);
            workspace.getInstructorField().setText(instructor);
            workspace.getDayTimeField().setText(dayTime);
            workspace.getLocationField().setText(location);
            workspace.getSupervisingTA().setValue(ta1);
            workspace.getSupervisingTA2().setValue(ta2);
            
        }
    }
    
    public void handleDeleteRec(){
            csg_Workspace workspace = (csg_Workspace)app.getWorkspaceComponent();
            TableView recTable = workspace.getRecitationTable();
            
            Object selectedItem = recTable.getSelectionModel().getSelectedItem();
            if(selectedItem!=null){
                csg_Recitation rec = (csg_Recitation)selectedItem;
                String section  = rec.getSection();
                csg_TAData data = (csg_TAData)app.getDataComponent();
                
                jTPS_Transaction deleteRec = new RecDeleteUR(app, section);
                jTPS.addTransaction(deleteRec);
                
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
