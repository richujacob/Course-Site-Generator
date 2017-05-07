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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyEvent;
import csg.file.csg_TimeSlot;
import csg.data.csg_Schedule;
import csg.data.csg_Recitation;
import csg.data.csg_Teams;
import csg.data.csg_Students;
import jtps.TAAdderUR;
import jtps.TAReplaceUR;
import jtps.TAdeletUR;
import jtps.TAhourschangeUR;
import jtps.TAtoggleUR;
import jtps.schAdderUR;
import jtps.schDeleteUR;
import jtps.schEditUR;
import jtps.RecAdderUR;
import jtps.RecDeleteUR;
import jtps.RecReplaceUR;
import javafx.scene.paint.Color;
import jtps.StudentAdderUR;
import jtps.StudentDeleteUR;
import jtps.TeamAdderUR;
import jtps.TeamDeleteUR;
import jtps.TeamReplaceUR;
import jtps.studentReplaceUR;

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
    
    
}