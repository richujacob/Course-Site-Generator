package csg.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import djf.components.AppDataComponent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import javafx.beans.property.StringProperty;
import javafx.scene.layout.Pane;
import properties_manager.PropertiesManager;
import csg.csg_App;
import csg.csg_Prop;
import csg.file.csg_TimeSlot;
import csg.workspace.csg_Workspace;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javafx.scene.control.DatePicker;
/**
 *
 * @author Richu
 */
public class csg_TAData implements AppDataComponent {
    
    csg_App app;

    // NOTE THAT THIS DATA STRUCTURE WILL DIRECTLY STORE THE
    // DATA IN THE ROWS OF THE TABLE VIEW
    ObservableList<csg_TeachingAssistant> teachingAssistants;
    ObservableList<csg_CourseDetails> courseDetailsInfo;
    ObservableList<csg_Recitation> recTable;
    ObservableList<csg_Schedule> scheduleTable;
    ObservableList<csg_Teams> teamsTable;
    ObservableList<csg_Students> studentsTable;

    // THIS WILL STORE ALL THE OFFICE HOURS GRID DATA, WHICH YOU
    // SHOULD NOTE ARE StringProperty OBJECTS THAT ARE CONNECTED
    // TO UI LABELS, WHICH MEANS IF WE CHANGE VALUES IN THESE
    // PROPERTIES IT CHANGES WHAT APPEARS IN THOSE LABELS
    HashMap<String, StringProperty> officeHours;
    
    // THESE ARE THE LANGUAGE-DEPENDENT VALUES FOR
    // THE OFFICE HOURS GRID HEADERS. NOTE THAT WE
    // LOAD THESE ONCE AND THEN HANG ON TO THEM TO
    // INITIALIZE OUR OFFICE HOURS GRID
    ArrayList<String> gridHeaders;

    // THESE ARE THE TIME BOUNDS FOR THE OFFICE HOURS GRID. NOTE
    // THAT THESE VALUES CAN BE DIFFERENT FOR DIFFERENT FILES, BUT
    // THAT OUR APPLICATION USES THE DEFAULT TIME VALUES AND PROVIDES
    // NO MEANS FOR CHANGING THESE VALUES
    int startHour;
    int endHour;
    
    // DEFAULT VALUES FOR START AND END HOURS IN MILITARY HOURS
    public static final int MIN_START_HOUR = 0;
    public static final int MAX_END_HOUR = 23;

    /**
     * This constructor will setup the required data structures for
     * use, but will have to wait on the office hours grid, since
     * it receives the StringProperty objects from the Workspace.
     * 
     * @param initApp The application this data manager belongs to. 
     */
    public csg_TAData(csg_App initApp) {
        // KEEP THIS FOR LATER
        app = initApp;

        // CONSTRUCT THE LIST OF TAs FOR THE TABLE
        teachingAssistants = FXCollections.observableArrayList();
        courseDetailsInfo = FXCollections.observableArrayList();
        recTable = FXCollections.observableArrayList();
        scheduleTable = FXCollections.observableArrayList();
        teamsTable = FXCollections.observableArrayList();
        studentsTable = FXCollections.observableArrayList();
        // CONSTRUCT THE LIST OF TAs FOR THE TABLE
        

        // THESE ARE THE DEFAULT OFFICE HOURS
        startHour = MIN_START_HOUR;
        endHour = MAX_END_HOUR;
        
        //THIS WILL STORE OUR OFFICE HOURS
        officeHours = new HashMap();
        
        // THESE ARE THE LANGUAGE-DEPENDENT OFFICE HOURS GRID HEADERS
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        ArrayList<String> timeHeaders = props.getPropertyOptionsList(csg_Prop.OFFICE_HOURS_TABLE_HEADERS);
        ArrayList<String> dowHeaders = props.getPropertyOptionsList(csg_Prop.DAYS_OF_WEEK);
        gridHeaders = new ArrayList();
        gridHeaders.addAll(timeHeaders);
        gridHeaders.addAll(dowHeaders);
    }
    
    /**
     * Called each time new work is created or loaded, it resets all data
     * and data structures such that they can be used for new values.
     */
    @Override
    public void resetData() {
        startHour = MIN_START_HOUR;
        endHour = MAX_END_HOUR;
        teachingAssistants.clear();
        officeHours.clear();
        courseDetailsInfo.clear();
        recTable.clear();
        scheduleTable.clear();
        teamsTable.clear();
        studentsTable.clear();
        
        csg_Workspace workspaceComponent = (csg_Workspace)app.getWorkspaceComponent();
        
        workspaceComponent.getOfficeHour(true).getSelectionModel().select(null);
        workspaceComponent.getOfficeHour(true).getSelectionModel().select(startHour);
        workspaceComponent.getOfficeHour(false).getSelectionModel().select(null);
        workspaceComponent.getOfficeHour(false).getSelectionModel().select(endHour);
    }
    
    // ACCESSOR METHODS

    public int getStartHour() {
        return startHour;
    }

    public int getEndHour() {
        return endHour;
    }
    
    public ArrayList<String> getGridHeaders() {
        return gridHeaders;
    }

    public ObservableList getTeachingAssistants() {
        return teachingAssistants;
    }
    
    public String getCellKey(int col, int row) {
        return col + "_" + row;
    }

    public StringProperty getCellTextProperty(int col, int row) {
        String cellKey = getCellKey(col, row);
        return officeHours.get(cellKey);
    }

    public HashMap<String, StringProperty> getOfficeHours() {
        return officeHours;
    }
    
    public int getNumRows() {
        return ((endHour - startHour) * 2) + 1;
    }

    public String getTimeString(int militaryHour, boolean onHour) {
        String minutesText = "00";
        if (!onHour) {
            minutesText = "30";
        }

        // FIRST THE START AND END CELLS
        int hour = militaryHour;
        if (hour > 12) {
            hour -= 12;
        }
        String cellText = "" + hour + ":" + minutesText;
        if (militaryHour < 12) {
            cellText += "am";
        } else {
            cellText += "pm";
        }
        return cellText;
    }
    
    public String getCellKey(String day, String time) {
        int col = gridHeaders.indexOf(day);
        int row = 1;
        int hour = Integer.parseInt(time.substring(0, time.indexOf("_")));
        int milHour = hour;
//        if (hour < startHour)
        if(time.contains("pm"))
            milHour += 12;
        if(time.contains("12"))
            milHour -= 12;
        row += (milHour - startHour) * 2;
        if (time.contains("_30"))
            row += 1;
        return getCellKey(col, row);
    }
    
    public csg_TeachingAssistant getTA(String testName) {
        for (csg_TeachingAssistant ta : teachingAssistants) {
            if (ta.getName().equals(testName)) {
                return ta;
            }
        }
        return null;
    }
    
    public csg_Recitation getSection(String section){
        for(csg_Recitation rec: recTable){
            if(rec.getSection().equals(section)){
                return rec;
            }
        }
        return null;
    }
    
    public csg_Schedule getDate(String date){
        for(csg_Schedule sch: scheduleTable){
            if(sch.getDate().equals(date)){
                return sch;
            }
        }
        return null;
    }
    
    /**
     * This method is for giving this data manager the string property
     * for a given cell.
     */
    public void setCellProperty(int col, int row, StringProperty prop) {
        String cellKey = getCellKey(col, row);
        officeHours.put(cellKey, prop);
    }    
    
    /**
     * This method is for setting the string property for a given cell.
     */
    public void setGridProperty(ArrayList<ArrayList<StringProperty>> grid,
                                int column, int row, StringProperty prop) {
        grid.get(row).set(column, prop);
    }
    

    
    private void initOfficeHours(int initStartHour, int initEndHour) {
        // NOTE THAT THESE VALUES MUST BE PRE-VERIFIED
        startHour = initStartHour;
        endHour = initEndHour;
        
        // EMPTY THE CURRENT OFFICE HOURS VALUES
        officeHours.clear();
            
        // WE'LL BUILD THE USER INTERFACE COMPONENT FOR THE
        // OFFICE HOURS GRID AND FEED THEM TO OUR DATA
        // STRUCTURE AS WE GO
        csg_Workspace workspaceComponent = (csg_Workspace)app.getWorkspaceComponent();
        workspaceComponent.reloadOfficeHoursGrid(this);
        
        workspaceComponent.getOfficeHour(true).getSelectionModel().select(startHour);
        workspaceComponent.getOfficeHour(false).getSelectionModel().select(endHour);
    }
    
    
    public void initHours(String startHourText, String endHourText) {
        int initStartHour = Integer.parseInt(startHourText);
        int initEndHour = Integer.parseInt(endHourText);
        if ((initStartHour >= MIN_START_HOUR)
                && (initEndHour <= MAX_END_HOUR)
                && (initStartHour <= initEndHour)) {
            // THESE ARE VALID HOURS SO KEEP THEM
            initOfficeHours(initStartHour, initEndHour);
        }
    }



    public boolean containsTA(String testName, String testEmail) {
        for (csg_TeachingAssistant ta : teachingAssistants) {
            if (ta.getName().equals(testName)) {
                return true;
            }
            if (ta.getEmail().equals(testEmail)) {
                return true;
            }
        }
        return false;
    }
    
   



    public void addTA(String initName, String initEmail) {
        // MAKE THE TA
        csg_TeachingAssistant ta = new csg_TeachingAssistant(initName, initEmail);

        // ADD THE TA
        if (!containsTA(initName, initEmail)) {
            teachingAssistants.add(ta);
        }

        // SORT THE TAS
        Collections.sort(teachingAssistants);
    }

    public void removeTA(String name) {
        for (csg_TeachingAssistant ta : teachingAssistants) {
            if (name.equals(ta.getName())) {
                teachingAssistants.remove(ta);
                return;
            }
        }
    }
    
    
    
    public void addOfficeHoursReservation(String day, String time, String taName) {
        String cellKey = getCellKey(day, time);
        toggleTAOfficeHours(cellKey, taName);
    }
    


    /**
     * This function toggles the taName in the cell represented
     * by cellKey. Toggle means if it's there it removes it, if
     * it's not there it adds it.
     */
    public void toggleTAOfficeHours(String cellKey, String taName) {
        StringProperty cellProp = officeHours.get(cellKey);
        String cellText = cellProp.getValue();

        // IF IT ALREADY HAS THE TA, REMOVE IT
        if (cellText.contains(taName)) {
            removeTAFromCell(cellProp, taName);
        } // OTHERWISE ADD IT
        else if (cellText.length() == 0) {
            cellProp.setValue(taName);
        } else {
            cellProp.setValue(cellText + "\n" + taName);
        }
    }
    
    /**
     * This method removes taName from the office grid cell
     * represented by cellProp.
     */
    public void removeTAFromCell(StringProperty cellProp, String taName) {
        // GET THE CELL TEXT
        String cellText = cellProp.getValue();
        // IS IT THE ONLY TA IN THE CELL?
        if (cellText.equals(taName)) {
            cellProp.setValue("");
        }
        // IS IT THE FIRST TA IN A CELL WITH MULTIPLE TA'S?
        else if (cellText.indexOf(taName) == 0) {
            int startIndex = cellText.indexOf("\n") + 1;
            cellText = cellText.substring(startIndex);
            cellProp.setValue(cellText);
        }
        // IS IT IN THE MIDDLE OF A LIST OF TAs
        else if (cellText.indexOf(taName) < cellText.indexOf("\n", cellText.indexOf(taName))) {
            int startIndex = cellText.indexOf("\n" + taName);
            int endIndex = startIndex + taName.length() + 1;
            cellText = cellText.substring(0, startIndex) + cellText.substring(endIndex);
            cellProp.setValue(cellText);
        }
        // IT MUST BE THE LAST TA
        else {
            int startIndex = cellText.indexOf("\n" + taName);
            cellText = cellText.substring(0, startIndex);
            cellProp.setValue(cellText);
        }
    }
    
    public void replaceTAName(String name, String newName){
        csg_Workspace workspace = (csg_Workspace)app.getWorkspaceComponent();
        for(Pane p : workspace.getOfficeHoursGridTACellPanes().values()){
            String cellKey = p.getId();
            StringProperty cellProp = officeHours.get(cellKey);
            String cellText = cellProp.getValue();
            if (cellText.contains(name)) {
                toggleTAOfficeHours(cellKey, name);
                toggleTAOfficeHours(cellKey, newName);
            }
        }
    }
    
    public void changeTime(int startTime, int endTime, ArrayList<csg_TimeSlot> officeHours){
        initHours("" + startTime, "" + endTime);
        for(csg_TimeSlot ts : officeHours){
            String temp = ts.getTime();
            int tempint = Integer.parseInt(temp.substring(0, temp.indexOf('_')));
            if(temp.contains("pm"))
                tempint += 12;
            if(temp.contains("12"))
                tempint -= 12;
            if(tempint >= startTime && tempint <= endTime)
                addOfficeHoursReservation(ts.getDay(), ts.getTime(), ts.getName());
        }
    }
    
    public void addPage(boolean checkBox, String navbar, String fileName, String script) {
        // MAKE THE TA
        csg_CourseDetails cD = new csg_CourseDetails(navbar, fileName, script);
        
       courseDetailsInfo.add(cD);
        // ADD THE TA
//        if (!containsTA(initName, initEmail)) {
//            teachingAssistants.add(ta);
//        }

        //Collections.sort(courseDetailsInfo);
    }
     
    public ObservableList getCourseDetailsInfo() {
        return courseDetailsInfo;
    }
    
    public void addRec(String section, String instructor, String dayTime, String location, String ta, String ta2 ){
        csg_Recitation rec = new csg_Recitation(section, instructor, dayTime, location, ta, ta2);
        recTable.add(rec);
    }
    
    public ObservableList<csg_Recitation> getRecTable() {
        return recTable;
    }
    
    public boolean containsRec(String section, String instructor, String dayTime, String location, String ta1, String ta2){
        for(csg_Recitation rec: recTable){
            if(rec.getSection().equals(section)){
                return true;
            }
            if(rec.getInstructor().equals(instructor)){
                return true;
            }
            if(rec.getDayTime().equals(dayTime)){
                return true;
            }
            if(rec.getLocation().equals(location)){
                return true;
            }
//            if(rec.getTa().equals(ta1)){
//                return true;
//            }
//            if(rec.getTa2().equals(ta2)){
//                return true;
//            }
        }return false;
    }
     
    public void removeRecitation(String section){
        for(csg_Recitation rec: recTable){
            if(section.equals(rec.getSection())){
                recTable.remove(rec);
                return;
            }
        }
    }
    
    public void addSchedule(String type, String date, String title, String topic){
        csg_Schedule sch = new csg_Schedule(type, date, title, topic);
        scheduleTable.add(sch);
        
    }
    
    public ObservableList<csg_Schedule> getScheduleTable() {
        return scheduleTable;
    }
    
    public boolean containSch(String date){
        for(csg_Schedule sch: scheduleTable){
            if(sch.getDate().equals(date)){
                return true;
            }
        }return false;
    }   
    
    public void removeSchedule(String date){
        for(csg_Schedule sch: scheduleTable ){
            if(date.equals(sch.getDate())){
                scheduleTable.remove(sch);
            }
        }return;
    }
    
//    public void removeSchedule(DatePicker date, DatePicker date2) throws ParseException{
//        for(csg_Schedule sch: scheduleTable){
//            
//            //DatePicker date1 = new DateTimeFormatter("MM-dd-yyyy").parse(validTest); 
//        }
//    }
    
    public void addTeam(String name, String color, String textColor, String link){
        csg_Teams team = new csg_Teams(name, color, textColor, link);
        teamsTable.add(team);
    }
    
    public ObservableList<csg_Teams> getTeamsTable() {
        return teamsTable;
    }
    
    public void addStudent(String firstName, String lastName, String team, String role){
        csg_Students student = new csg_Students(firstName, lastName, team, role);
        studentsTable.add(student);
    }

    public ObservableList<csg_Students> getStudentsTable() {
        return studentsTable;
    }
    
    
    
    
    
}
