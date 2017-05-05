
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
import csg.data.csg_Teams;
import csg.style.csg_Style;
import csg.workspace.csg_Workspace;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyEvent;
import jtps.jTPS;
import jtps.jTPS_Transaction;
import csg.csg_Prop;
import csg.data.csg_Students;
import javafx.scene.paint.Color;
import jtps.StudentAdderUR;
import jtps.StudentDeleteUR;
import jtps.TeamAdderUR;
import jtps.TeamDeleteUR;
import jtps.TeamReplaceUR;
import jtps.studentReplaceUR;
/**
 *
 * @author Richu
 */
public class csg_TeamController {
    static jTPS jTPS = new jTPS();
    // THE APP PROVIDES ACCESS TO OTHER COMPONENTS AS NEEDED
    csg_App app;
    
    public csg_TeamController(csg_App initApp){
        app = initApp;
    }
    
    private void markWorkAsEdited() {
        // MARK WORK AS EDITED
        AppGUI gui = app.getGUI();
        gui.getFileController().markAsEdited(gui);
    }
    
    public void handleAddTeam(){
        csg_Workspace workspace = (csg_Workspace)app.getWorkspaceComponent();
        TextField nameField = workspace.getNameTeams();
        String hexColor = Integer.toHexString(workspace.getColor1().getValue().hashCode());
        String textColor = Integer.toHexString(workspace.getColor2().getValue().hashCode()).substring(0, 6).toUpperCase();
        TextField linkField = workspace.getLinkTeams();
        String name = nameField.getText();
        String link = linkField.getText();
        
        csg_TAData data = (csg_TAData)app.getDataComponent();
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        if(name.isEmpty()){
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(NAME_MISSING_TEAM_TITLE), props.getProperty(NAME_MISSING_TEAM_MESSAGE));
        }
        else if(workspace.getColor1().getValue().equals(null)){
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(COLOR_MISSING_TITLE), props.getProperty(COLOR_MISSING_MESSAGE));
        }else if(workspace.getColor2().getValue().equals(null)){
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(TEXTCOLOR_MISSING_TITLE), props.getProperty(TEXTCOLOR_MISSING_MESSAGE));
        }else if(link.isEmpty()){
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(LINK_MISSING_TEAM_TITLE), props.getProperty(LINK_MISSING_TEAM_MESSAGE));
        }
        else if(data.containsTeam(name, hexColor, textColor, link)){
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(TEAMS_NOT_UNIQUE_TITLE), props.getProperty(TEAMS_NOT_UNIQUE_MESSAGE));
        }
        else{
            jTPS_Transaction addTeamUR = new TeamAdderUR(app);
            jTPS.addTransaction(addTeamUR);
            
            workspace.getNameTeams().setText("");
            workspace.getLinkTeams().setText("");
            
            markWorkAsEdited();
        }
        
        
    }
    
    public void loadTeamText(){
            csg_Workspace workspace = (csg_Workspace)app.getWorkspaceComponent();
            TableView teamTable = workspace.getTeamsTable();
            Object selectedItem = teamTable.getSelectionModel().getSelectedItem();
            if(selectedItem!=null){
                csg_Teams team = (csg_Teams)selectedItem;
                String color = team.getColor();
                String textColor = team.getTextColor();
                workspace.getNameTeams().setText(team.getName());
                workspace.getColor1().setValue(Color.valueOf(color));
                workspace.getLinkTeams().setText(team.getLink());
            }
    }
    
    public void editTeamText(){
        csg_Workspace workspace = (csg_Workspace)app.getWorkspaceComponent();
        TableView teamsTable = workspace.getTeamsTable();
        Object selectedItem = teamsTable.getSelectionModel().getSelectedItem();
        if(selectedItem!=null){
            jTPS_Transaction replaceTeamUR = new TeamReplaceUR(app);
            jTPS.addTransaction(replaceTeamUR);
            
            markWorkAsEdited();
        }
    }
    
    public void handleTeamDelete(){
        csg_Workspace workspace = (csg_Workspace)app.getWorkspaceComponent();
        TableView teamTable = workspace.getTeamsTable();
        
        Object selectedItem = teamTable.getSelectionModel().getSelectedItem();
        if(selectedItem!=null){
                csg_Teams team = (csg_Teams)selectedItem;
                String name = team.getName();
                csg_TAData data = (csg_TAData)app.getDataComponent();
                
                jTPS_Transaction deleteTeam = new TeamDeleteUR(app, name);
                jTPS.addTransaction(deleteTeam);
                
                markWorkAsEdited();
            }
        
    }
    
    public void handleStudentAdd(){
        try{
        csg_Workspace workspace = (csg_Workspace)app.getWorkspaceComponent();
        String firstName = workspace.getFirstNameField().getText();
        String lastName = workspace.getLastNameField().getText();
        //String team = (String)workspace.getTeams().getValue().toString();
        String role = workspace.getRoleField().getText();
        
        csg_TAData data = (csg_TAData)app.getDataComponent();
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        if(firstName.isEmpty()){
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(FIRSTNAME_MISSING_TITLE), props.getProperty(FIRSTNAME_MISSING_MESSAGE));
        }else if(lastName.isEmpty()){
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(LASTNAME_MISSING_TITLE), props.getProperty(LASTNAME_MISSING_MESSAGE));
        }else if(workspace.getTeams().getValue().equals(null)){
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(TEAM_MISSING_TITLE), props.getProperty(TEAM_MISSING_MESSAGE));
        }else if(role.isEmpty()){
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(ROLE_MISSING_TITLE), props.getProperty(ROLE_MISSING_MESSAGE));
        }else if(data.containsStudent(firstName, lastName, role)){
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(TEAMS_NOT_UNIQUE_TITLE), props.getProperty(TEAMS_NOT_UNIQUE_MESSAGE));
        }else{
            jTPS_Transaction addStudentUR = new StudentAdderUR(app);
            jTPS.addTransaction(addStudentUR);
            
            workspace.getFirstNameField().setText("");
            workspace.getLastNameField().setText("");
            workspace.getTeams().setValue(null);
            workspace.getRoleField().setText("");
            
            markWorkAsEdited();
        }
        }catch(NullPointerException e){
            
        }
        
    }
    
    public void loadStudentText(){
        csg_Workspace workspace = (csg_Workspace)app.getWorkspaceComponent();
            TableView studentTable = workspace.getStudentsTable();
            Object selectedItem = studentTable.getSelectionModel().getSelectedItem();
            if(selectedItem!=null){
                csg_Students student = (csg_Students)selectedItem;
                String firstName  = student.getFirstName();
                String lastName = student.getLastName();
                String team = student.getTeam();
                String role = student.getRole();
                workspace.getFirstNameField().setText(firstName);
                workspace.getLastNameField().setText(lastName);
                workspace.getTeams().setValue(team);
                workspace.getRoleField().setText(role);
            }
    }
    
    public void handleStudentEdit(){
        csg_Workspace workspace = (csg_Workspace)app.getWorkspaceComponent();
        TableView studentTable = workspace.getStudentsTable();
        Object selectedItem = studentTable.getSelectionModel().getSelectedItem();
        if(selectedItem!=null){
            jTPS_Transaction replaceStudentUR = new studentReplaceUR(app);
            jTPS.addTransaction(replaceStudentUR);
            
            markWorkAsEdited();
        }
    }
    
    public void handleStudentDelete(){
        csg_Workspace workspace = (csg_Workspace)app.getWorkspaceComponent();
        TableView studentTable = workspace.getStudentsTable();
        
        Object selectedItem = studentTable.getSelectionModel().getSelectedItem();
        if(selectedItem!=null){
                csg_Students student = (csg_Students)selectedItem;
                String firstName = student.getFirstName();
                csg_TAData data = (csg_TAData)app.getDataComponent();
                
                jTPS_Transaction deleteStudent = new StudentDeleteUR(app, firstName);
                jTPS.addTransaction(deleteStudent);
                
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
