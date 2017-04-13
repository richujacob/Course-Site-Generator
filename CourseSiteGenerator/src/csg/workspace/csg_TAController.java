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
import csg.data.csg_TeachingAssistant;
import csg.style.csg_Style;
import static csg.style.csg_Style.CLASS_HIGHLIGHTED_GRID_CELL;
import static csg.style.csg_Style.CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN;
import static csg.style.csg_Style.CLASS_OFFICE_HOURS_GRID_TA_CELL_PANE;
import csg.workspace.csg_Workspace;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyEvent;
import jtps.jTPS;
import jtps.jTPS_Transaction;
import csg.csg_Prop;
import csg.file.csg_TimeSlot;
import jtps.TAAdderUR;
import jtps.TAReplaceUR;
import jtps.TAdeletUR;
import jtps.TAhourschangeUR;
import jtps.TAtoggleUR;

/**
 * This class provides responses to all workspace interactions, meaning
 * interactions with the application controls not including the file
 * toolbar.
 * 
 * @author Richard McKenna
 * @version 1.0
 */
public class csg_TAController {
    static jTPS jTPS = new jTPS();
    // THE APP PROVIDES ACCESS TO OTHER COMPONENTS AS NEEDED
    csg_App app;

    /**
     * Constructor, note that the app must already be constructed.
     */
    public csg_TAController(csg_App initApp) {
        // KEEP THIS FOR LATER
        app = initApp;
    }
    
    /**
     * This helper method should be called every time an edit happens.
     */    
    private void markWorkAsEdited() {
        // MARK WORK AS EDITED
        AppGUI gui = app.getGUI();
        gui.getFileController().markAsEdited(gui);
    }
    
    /**
     * This method responds to when the user requests to add
     * a new TA via the UI. Note that it must first do some
     * validation to make sure a unique name and email address
     * has been provided.
     */
    public void handleAddTA() {
        // WE'LL NEED THE WORKSPACE TO RETRIEVE THE USER INPUT VALUES
        csg_Workspace workspace = (csg_Workspace)app.getWorkspaceComponent();
        TextField nameTextField = workspace.getNameTextField();
        TextField emailTextField = workspace.getEmailTextField();
        String name = nameTextField.getText();
        String email = emailTextField.getText();
        
        // WE'LL NEED TO ASK THE DATA SOME QUESTIONS TOO
        csg_TAData data = (csg_TAData)app.getDataComponent();
        
        // WE'LL NEED THIS IN CASE WE NEED TO DISPLAY ANY ERROR MESSAGES
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        // DID THE USER NEGLECT TO PROVIDE A TA NAME?
        if (name.isEmpty()) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_TA_NAME_TITLE), props.getProperty(MISSING_TA_NAME_MESSAGE));            
        }
        // DID THE USER NEGLECT TO PROVIDE A TA EMAIL?
        else if (email.isEmpty()) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_TA_EMAIL_TITLE), props.getProperty(MISSING_TA_EMAIL_MESSAGE));                        
        }
        // DOES A TA ALREADY HAVE THE SAME NAME OR EMAIL?
        else if (data.containsTA(name, email)) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(TA_NAME_AND_EMAIL_NOT_UNIQUE_TITLE), props.getProperty(TA_NAME_AND_EMAIL_NOT_UNIQUE_MESSAGE));                                    
        }
        else if (!Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$").matcher(email).matches()){
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(TA_EMAIL_INVALID_TITLE), props.getProperty(TA_EMAIL_INVALID_MESSAGE));
        }
        // EVERYTHING IS FINE, ADD A NEW TA
        else {
            
            // ADD THE NEW TA TO THE DATA
            jTPS_Transaction addTAUR = new TAAdderUR(app);
            jTPS.addTransaction(addTAUR);
            //data.addTA(name, email);
            // CLEAR THE TEXT FIELDS
            nameTextField.setText("");
            emailTextField.setText("");
            
            // AND SEND THE CARET BACK TO THE NAME TEXT FIELD FOR EASY DATA ENTRY
            nameTextField.requestFocus();
            
            // WE'VE CHANGED STUFF
            markWorkAsEdited();
        }
    }

    /**
     * This function provides a response for when the user presses a
     * keyboard key. Note that we're only responding to Delete, to remove
     * a TA.
     * 
     * @param code The keyboard code pressed.
     */
    public void handleKeyPress(KeyCode code) {
        // DID THE USER PRESS THE DELETE KEY?
        if (code == KeyCode.DELETE) {
            // GET THE TABLE
            csg_Workspace workspace = (csg_Workspace)app.getWorkspaceComponent();
            TableView taTable = workspace.getTATable();
            
            // IS A TA SELECTED IN THE TABLE?
            Object selectedItem = taTable.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                // GET THE TA AND REMOVE IT
                csg_TeachingAssistant ta = (csg_TeachingAssistant)selectedItem;
                String taName = ta.getName();
                csg_TAData data = (csg_TAData)app.getDataComponent();
                //data.removeTA(taName);
                jTPS_Transaction deletUR = new TAdeletUR(app, taName);
                jTPS.addTransaction(deletUR);
                
                // AND BE SURE TO REMOVE ALL THE TA'S OFFICE HOURS
                // WE'VE CHANGED STUFF
                markWorkAsEdited();
            }
        }
    }

    /**
     * This function provides a response for when the user clicks
     * on the office hours grid to add or remove a TA to a time slot.
     * 
     * @param pane The pane that was toggled.
     */
    public void handleCellToggle(Pane pane) {
        // GET THE TABLE
        csg_Workspace workspace = (csg_Workspace)app.getWorkspaceComponent();
        TableView taTable = workspace.getTATable();
        
        // IS A TA SELECTED IN THE TABLE?
        Object selectedItem = taTable.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            // GET THE TA
            csg_TeachingAssistant ta = (csg_TeachingAssistant)selectedItem;
            String taName = ta.getName();
            csg_TAData data = (csg_TAData)app.getDataComponent();
            String cellKey = pane.getId();
            //data.toggleTAOfficeHours(cellKey, taName);
            // AND TOGGLE THE OFFICE HOURS IN THE CLICKED CELL
            jTPS_Transaction toggleUR = new TAtoggleUR(taName, cellKey, data);
            jTPS.addTransaction(toggleUR);
            
            // WE'VE CHANGED STUFF
            markWorkAsEdited();
        }
    }
    
    void handleGridCellMouseExited(Pane pane) {
        String cellKey = pane.getId();
        csg_TAData data = (csg_TAData)app.getDataComponent();
        int column = Integer.parseInt(cellKey.substring(0, cellKey.indexOf("_")));
        int row = Integer.parseInt(cellKey.substring(cellKey.indexOf("_") + 1));
        csg_Workspace workspace = (csg_Workspace)app.getWorkspaceComponent();

        Pane mousedOverPane = workspace.getTACellPane(data.getCellKey(column, row));
        mousedOverPane.getStyleClass().clear();
        mousedOverPane.getStyleClass().add(CLASS_OFFICE_HOURS_GRID_TA_CELL_PANE);

        // THE MOUSED OVER COLUMN HEADER
        Pane headerPane = workspace.getOfficeHoursGridDayHeaderPanes().get(data.getCellKey(column, 0));
        headerPane.getStyleClass().remove(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);

        // THE MOUSED OVER ROW HEADERS
        headerPane = workspace.getOfficeHoursGridTimeCellPanes().get(data.getCellKey(0, row));
        headerPane.getStyleClass().remove(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);
        headerPane = workspace.getOfficeHoursGridTimeCellPanes().get(data.getCellKey(1, row));
        headerPane.getStyleClass().remove(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);
        
        // AND NOW UPDATE ALL THE CELLS IN THE SAME ROW TO THE LEFT
        for (int i = 2; i < column; i++) {
            cellKey = data.getCellKey(i, row);
            Pane cell = workspace.getTACellPane(cellKey);
            cell.getStyleClass().remove(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);
            cell.getStyleClass().add(CLASS_OFFICE_HOURS_GRID_TA_CELL_PANE);
        }

        // AND THE CELLS IN THE SAME COLUMN ABOVE
        for (int i = 1; i < row; i++) {
            cellKey = data.getCellKey(column, i);
            Pane cell = workspace.getTACellPane(cellKey);
            cell.getStyleClass().remove(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);
            cell.getStyleClass().add(CLASS_OFFICE_HOURS_GRID_TA_CELL_PANE);
        }
    }

    void handleGridCellMouseEntered(Pane pane) {
        String cellKey = pane.getId();
        csg_TAData data = (csg_TAData)app.getDataComponent();
        int column = Integer.parseInt(cellKey.substring(0, cellKey.indexOf("_")));
        int row = Integer.parseInt(cellKey.substring(cellKey.indexOf("_") + 1));
        csg_Workspace workspace = (csg_Workspace)app.getWorkspaceComponent();
        
        // THE MOUSED OVER PANE
        Pane mousedOverPane = workspace.getTACellPane(data.getCellKey(column, row));
        mousedOverPane.getStyleClass().clear();
        mousedOverPane.getStyleClass().add(CLASS_HIGHLIGHTED_GRID_CELL);
        
        // THE MOUSED OVER COLUMN HEADER
        Pane headerPane = workspace.getOfficeHoursGridDayHeaderPanes().get(data.getCellKey(column, 0));
        headerPane.getStyleClass().add(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);
        
        // THE MOUSED OVER ROW HEADERS
        headerPane = workspace.getOfficeHoursGridTimeCellPanes().get(data.getCellKey(0, row));
        headerPane.getStyleClass().add(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);
        headerPane = workspace.getOfficeHoursGridTimeCellPanes().get(data.getCellKey(1, row));
        headerPane.getStyleClass().add(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);
        
        // AND NOW UPDATE ALL THE CELLS IN THE SAME ROW TO THE LEFT
        for (int i = 2; i < column; i++) {
            cellKey = data.getCellKey(i, row);
            Pane cell = workspace.getTACellPane(cellKey);
            cell.getStyleClass().add(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);
        }

        // AND THE CELLS IN THE SAME COLUMN ABOVE
        for (int i = 1; i < row; i++) {
            cellKey = data.getCellKey(column, i);
            Pane cell = workspace.getTACellPane(cellKey);
            cell.getStyleClass().add(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);
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
    
    public void changeTime(){
        csg_TAData data = (csg_TAData)app.getDataComponent();
        csg_Workspace workspace = (csg_Workspace)app.getWorkspaceComponent();
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        ComboBox comboBox1 = workspace.getOfficeHour(true);
        ComboBox comboBox2 = workspace.getOfficeHour(false);
        int startTime = data.getStartHour();
        int endTime = data.getEndHour();
        int newStartTime = comboBox1.getSelectionModel().getSelectedIndex();
        int newEndTime = comboBox2.getSelectionModel().getSelectedIndex();
        if(newStartTime > endTime || newEndTime < startTime){
            comboBox1.getSelectionModel().select(startTime);
            comboBox2.getSelectionModel().select(endTime);
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(csg_Prop.START_OVER_END_TITLE.toString()), props.getProperty(csg_Prop.START_OVER_END_MESSAGE.toString()));
            return;
        }
        ArrayList<csg_TimeSlot> officeHours = csg_TimeSlot.buildOfficeHoursList(data);
        if(officeHours.isEmpty()){
            workspace.getOfficeHoursGridPane().getChildren().clear();
            data.initHours("" + newStartTime, "" + newEndTime);
        }
        String firsttime = officeHours.get(0).getTime();
        int firsthour = Integer.parseInt(firsttime.substring(0, firsttime.indexOf('_')));
        if(firsttime.contains("pm"))
            firsthour += 12;
        if(firsttime.contains("12"))
            firsthour -= 12;
        String lasttime = officeHours.get(officeHours.size() - 1).getTime();
        int lasthour = Integer.parseInt(lasttime.substring(0, lasttime.indexOf('_')));
        if(lasttime.contains("pm"))
            lasthour += 12;
        if(lasttime.contains("12"))
            lasthour -= 12;
        if(firsthour < newStartTime || lasthour + 1 > newEndTime){
            AppYesNoCancelDialogSingleton yesNoDialog = AppYesNoCancelDialogSingleton.getSingleton();
            yesNoDialog.show(props.getProperty(csg_Prop.OFFICE_HOURS_REMOVED_TITLE.toString()), props.getProperty(csg_Prop.OFFICE_HOURS_REMOVED_MESSAGE).toString());
            String selection = yesNoDialog.getSelection();
            if (!selection.equals(AppYesNoCancelDialogSingleton.YES)){
                comboBox1.getSelectionModel().select(startTime);
                comboBox2.getSelectionModel().select(endTime);
                return;
            }
        }
        
        jTPS_Transaction changeTimeUR = new TAhourschangeUR(app);
        jTPS.addTransaction(changeTimeUR);
        
        markWorkAsEdited();
    }
    
    public void changeExistTA(){
        csg_Workspace workspace = (csg_Workspace)app.getWorkspaceComponent();
        TableView taTable = workspace.getTATable();
        Object selectedItem = taTable.getSelectionModel().getSelectedItem();
        csg_TAData data = (csg_TAData)app.getDataComponent();
        csg_TeachingAssistant ta = (csg_TeachingAssistant)selectedItem;
        String name = ta.getName();
        String newName = workspace.getNameTextField().getText();
        String newEmail = workspace.getEmailTextField().getText();
        jTPS_Transaction replaceTAUR = new TAReplaceUR(app);
        jTPS.addTransaction(replaceTAUR);
        markWorkAsEdited();
    }
    
    public void loadTAtotext(){
        csg_Workspace workspace = (csg_Workspace)app.getWorkspaceComponent();
        TableView taTable = workspace.getTATable();
        Object selectedItem = taTable.getSelectionModel().getSelectedItem();
        if(selectedItem != null){
            csg_TeachingAssistant ta = (csg_TeachingAssistant)selectedItem;
            String name = ta.getName();
            String email = ta.getEmail();
            workspace.getNameTextField().setText(name);
            workspace.getEmailTextField().setText(email);
        }
    }
}