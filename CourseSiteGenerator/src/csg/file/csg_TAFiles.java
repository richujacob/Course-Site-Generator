package csg.file;

import djf.components.AppDataComponent;
import djf.components.AppFileComponent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.collections.ObservableList;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;
import csg.csg_App;
import csg.data.csg_TAData;
import csg.data.csg_TeachingAssistant;
import csg.data.csg_CourseDetails;
import csg.data.csg_Recitation;
import csg.data.csg_Schedule;
import csg.data.csg_Students;
import csg.data.csg_Teams;
import csg.workspace.csg_Workspace;
import djf.controller.AppFileController;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Richu
 */
public class csg_TAFiles implements AppFileComponent {
    // THIS IS THE APP ITSELF
    csg_App app;
    
    // THESE ARE USED FOR IDENTIFYING JSON TYPES
    static final String JSON_START_HOUR = "startHour";
    static final String JSON_END_HOUR = "endHour";
    static final String JSON_OFFICE_HOURS = "officeHours";
    static final String JSON_DAY = "day";
    static final String JSON_TIME = "time";
    static final String JSON_NAME = "name";
    static final String JSON_USETA = "use";
    static final String JSON_UNDERGRAD_TAS = "undergrad_tas";
    static final String JSON_EMAIL = "email";
    static final String JSON_SITEPAGES = "site_pages";
    static final String JSON_USE = "use";
    static final String JSON_NAVBARTITLE = "navbarTitle";
    static final String JSON_FILENAME = "fileName";
    static final String JSON_SCRIPT = "script";
    static final String JSON_RECITATION = "recitations";
    static final String JSON_SECTION = "section";
    static final String JSON_INSTRUCTOR = "instructor";
    static final String JSON_DAYTIME = "daytime";
    static final String JSON_LOCATION = "location";
    static final String JSON_TA = "ta";
    static final String JSON_TA2 = "ta2";
    static final String JSON_SCHEDULE = "schedule";
    static final String JSON_TYPE = "type";
    static final String JSON_DATE = "date";
    static final String JSON_TITLE = "title";
    static final String JSON_TOPIC = "topic";
    static final String JSON_SCH_TIME = "time";
    static final String JSON_SCH_LINK = "link";
    static final String JSON_CRITERIA = "criteria";
    static final String JSON_TEAMS = "teams";
    static final String JSON_TEAMNAME = "name";
    static final String JSON_COLOR = "color";
    static final String JSON_TEXTCOLOR = "textColor";
    static final String JSON_LINK = "link";
    static final String JSON_STUDENTS = "students";
    static final String JSON_FIRSTNAME = "firstName";
    static final String JSON_LASTNAME = "lastName";
    static final String JSON_TEAM = "team";
    static final String JSON_ROLE = "role";
    
    //export variables
    static final String JSON_SUBJECT_EXPORT = "subject";
    static final String JSON_SEMESTER_EXPORT = "semester";
    static final String JSON_NUMBER_EXPORT = "number";
    static final String JSON_YEAR_EXPORT = "year";
    static final String JSON_TITLE_EXPORT = "title";
    static final String JSON_INSTRUCTORNAME_EXPORT  = "instructor_name";
    static final String JSON_INSTRUCTORHOME_EXPORT = "instructor_home";
            
    static final String JSON_RECITATION_EXPORT = "recitations";
    static final String JSON_SECTION_EXPORT = "section";
    static final String JSON_INSTRUCTOR_EXPORT = "instructor";
    static final String JSON_DAYTIME_EXPORT = "day_time";
    static final String JSON_LOCATION_EXPORT = "location";
    static final String JSON_TA1_EXPORT = "ta_1";
    static final String JSON_TA2_EXPORT = "ta_2";
    
    static final String JSON_STARTINGMONDAY_MONTH_EXPORT = "startingMondayMonth";
    static final String JSON_STARTINGMONDAY_DAY_EXPORT = "startingMondayDay";
    static final String JSON_ENDINGFRIDAY_MONTH_EXPORT = "endingFridayMonth";
    static final String JSON_ENDINGFRIDAY_DAY_EXPORT = "endingFridayDay";
    
    static final String JSON_HOLIDAYS_EXPORT = "holidays";
    static final String JSON_HW_EXPORT = "hws";
    static final String JSON_SCHRECITATION_EXPORT = "recitations";
    static final String JSON_LECTURES_EXPORT = "lectures";
    static final String JSON_REFERENCES_EXPORT = "references";
    
    static final String JSON_MONTH_EXPORT = "month";
    static final String JSON_DAY_EXPORT = "day";
    static final String JSON_TITLESCH_EXPORT = "title";
    static final String JSON_LINK_EXPORT = "link";
    static final String JSON_TOPIC_EXPORT = "topic";
    static final String JSON_TIME_EXPORT = "time";
    static final String JSON_CRITERIA_EXPORT = "criteria";
    
    static final String JSON_TEAMS_EXPORT = "teams";
    static final String JSON_TEAMNAME_EXPORT = "name";
    static final String JSON_RED_EXPORT = "red";
    static final String JSON_GREEN_EXPORT = "green";
    static final String JSON_BLUE_EXPORT = "blue";
    static final String JSON_TEXTCOLOR_EXPORT = "text_color";
    
    public csg_TAFiles(csg_App initApp) {
        app = initApp;
    }

    @Override
    public void loadData(AppDataComponent data, String filePath) throws IOException {
	// CLEAR THE OLD DATA OUT
	csg_TAData dataManager = (csg_TAData)data;
        //csg_CourseDetailsData courseDetManager = (csg_CourseDetailsData)data;

	// LOAD THE JSON FILE WITH ALL THE DATA
	JsonObject json = loadJSONFile(filePath);
        
        
	// LOAD THE START AND END HOURS
	String startHour = json.getString(JSON_START_HOUR);
        String endHour = json.getString(JSON_END_HOUR);
        dataManager.initHours(startHour, endHour);

        // NOW RELOAD THE WORKSPACE WITH THE LOADED DATA
        app.getWorkspaceComponent().reloadWorkspace(app.getDataComponent());
        
               
        // NOW LOAD ALL THE UNDERGRAD TAs
        JsonArray jsonTAArray = json.getJsonArray(JSON_UNDERGRAD_TAS);
        for (int i = 0; i < jsonTAArray.size(); i++) {
            JsonObject jsonTA = jsonTAArray.getJsonObject(i);
            boolean use = jsonTA.getBoolean(JSON_USETA);
            String name = jsonTA.getString(JSON_NAME);
            String email = jsonTA.getString(JSON_EMAIL);
            dataManager.addTA(use, name, email);
        }

        // AND THEN ALL THE OFFICE HOURS
        JsonArray jsonOfficeHoursArray = json.getJsonArray(JSON_OFFICE_HOURS);
        for (int i = 0; i < jsonOfficeHoursArray.size(); i++) {
            JsonObject jsonOfficeHours = jsonOfficeHoursArray.getJsonObject(i);
            String day = jsonOfficeHours.getString(JSON_DAY);
            String time = jsonOfficeHours.getString(JSON_TIME);
            String name = jsonOfficeHours.getString(JSON_NAME);
            dataManager.addOfficeHoursReservation(day, time, name);
        }
        
        JsonArray jsonSitePagesArray = json.getJsonArray(JSON_SITEPAGES);
        for(int i=0; i<jsonSitePagesArray.size(); i++){
            JsonObject jsonSitePages = jsonSitePagesArray.getJsonObject(i);
            boolean use = jsonSitePages.getBoolean(JSON_USE);
            String navbar = jsonSitePages.getString(JSON_NAVBARTITLE);
            String fileName = jsonSitePages.getString(JSON_FILENAME);
            String script = jsonSitePages.getString(JSON_SCRIPT);
            dataManager.addPage(use, navbar, fileName, script);
        }
        
        JsonArray jsonRecitationArray = json.getJsonArray(JSON_RECITATION);
        for(int i=0; i<jsonRecitationArray.size(); i++){
            JsonObject jsonRecitationTable = jsonRecitationArray.getJsonObject(i);
            String section = jsonRecitationTable.getString(JSON_SECTION);
            String instructor = jsonRecitationTable.getString(JSON_INSTRUCTOR);
            String dayTime = jsonRecitationTable.getString(JSON_DAYTIME);
            String location = jsonRecitationTable.getString(JSON_LOCATION);
            String ta = jsonRecitationTable.getString(JSON_TA);
            String ta2 = jsonRecitationTable.getString(JSON_TA2);
            dataManager.addRec(section, instructor, dayTime, location, ta, ta2);
        }
        
        JsonArray jsonScheduleArray = json.getJsonArray(JSON_SCHEDULE);
        for(int i=0; i<jsonScheduleArray.size(); i++){
            JsonObject jsonScheduleTable = jsonScheduleArray.getJsonObject(i);
            String type = jsonScheduleTable.getString(JSON_TYPE);
            String date = jsonScheduleTable.getString(JSON_DATE);
            String title = jsonScheduleTable.getString(JSON_TITLE);
            String topic = jsonScheduleTable.getString(JSON_TOPIC);
            String time = jsonScheduleTable.getString(JSON_SCH_TIME);
            String link = jsonScheduleTable.getString(JSON_SCH_LINK);
            String criteria  = jsonScheduleTable.getString(JSON_CRITERIA);
            dataManager.addSchedule(type, date, title, topic, time, link, criteria);
        }
        
        JsonArray jsonTeamsArray = json.getJsonArray(JSON_TEAMS);
        for(int i=0; i<jsonTeamsArray.size(); i++){
            JsonObject jsonTeamsTable = jsonTeamsArray.getJsonObject(i);
            String name = jsonTeamsTable.getString(JSON_TEAMNAME);
            String color = jsonTeamsTable.getString(JSON_COLOR);
            String textColor = jsonTeamsTable.getString(JSON_TEXTCOLOR);
            String link = jsonTeamsTable.getString(JSON_LINK);
            dataManager.addTeam(name, color, textColor, link);
        }
        
        JsonArray jsonStudentsArray = json.getJsonArray(JSON_STUDENTS);
        for(int i=0; i<jsonStudentsArray.size(); i++){
            JsonObject jsonStudentsTable = jsonStudentsArray.getJsonObject(i);
            String firstName = jsonStudentsTable.getString(JSON_FIRSTNAME);
            String lastaName = jsonStudentsTable.getString(JSON_LASTNAME);
            String team = jsonStudentsTable.getString(JSON_TEAM);
            String role = jsonStudentsTable.getString(JSON_ROLE);
            dataManager.addStudent(firstName, lastaName, team, role);
        }
    }
      
    // HELPER METHOD FOR LOADING DATA FROM A JSON FORMAT
    private JsonObject loadJSONFile(String jsonFilePath) throws IOException {
	InputStream is = new FileInputStream(jsonFilePath);
	JsonReader jsonReader = Json.createReader(is);
	JsonObject json = jsonReader.readObject();
	jsonReader.close();
	is.close();
	return json;
    }

    @Override
    public void saveData(AppDataComponent data, String filePath) throws IOException {
	// GET THE DATA
	csg_TAData dataManager = (csg_TAData)data;
        //csg_CourseDetailsData courseData = (csg_CourseDetailsData) data;    
        
	// NOW BUILD THE TA JSON OBJCTS TO SAVE
	JsonArrayBuilder taArrayBuilder = Json.createArrayBuilder();
	ObservableList<csg_TeachingAssistant> tas = dataManager.getTeachingAssistants();
	for (csg_TeachingAssistant ta : tas) {	    
	    JsonObject taJson = Json.createObjectBuilder()
                    .add(JSON_USETA, ta.isCheckBox())
		    .add(JSON_NAME, ta.getName())
		    .add(JSON_EMAIL, ta.getEmail()).build();
	    taArrayBuilder.add(taJson);
	}
	JsonArray undergradTAsArray = taArrayBuilder.build();

	// NOW BUILD THE TIME SLOT JSON OBJCTS TO SAVE
	JsonArrayBuilder timeSlotArrayBuilder = Json.createArrayBuilder();
	ArrayList<csg_TimeSlot> officeHours = csg_TimeSlot.buildOfficeHoursList(dataManager);
	for (csg_TimeSlot ts : officeHours) {	    
	    JsonObject tsJson = Json.createObjectBuilder()
		    .add(JSON_DAY, ts.getDay())
		    .add(JSON_TIME, ts.getTime())
		    .add(JSON_NAME, ts.getName()).build();
	    timeSlotArrayBuilder.add(tsJson);
	}
	JsonArray timeSlotsArray = timeSlotArrayBuilder.build();
        
        JsonArrayBuilder courseBuilder = Json.createArrayBuilder();
        ObservableList<csg_CourseDetails> cd = dataManager.getCourseDetailsInfo();
        for(csg_CourseDetails course: cd){
            JsonObject courseJson = Json.createObjectBuilder()
                    .add(JSON_USE, course.isCheckBox())
                    .add(JSON_NAVBARTITLE, course.getNavbarTitle())
                    .add(JSON_FILENAME, course.getFileName())
                    .add(JSON_SCRIPT, course.getScript()).build();
            courseBuilder.add(courseJson);
        }
        JsonArray sitePagesArray = courseBuilder.build();
        
        
        JsonArrayBuilder recTableArray = Json.createArrayBuilder();
        ObservableList<csg_Recitation> rec = dataManager.getRecTable();
        for(csg_Recitation recitation: rec){
            JsonObject recJson = Json.createObjectBuilder()
                    .add(JSON_SECTION, recitation.getSection())
                    .add(JSON_INSTRUCTOR, recitation.getInstructor())
                    .add(JSON_DAYTIME, recitation.getDayTime())
                    .add(JSON_LOCATION, recitation.getLocation())
                    .add(JSON_TA, recitation.getTa())
                    .add(JSON_TA2, recitation.getTa2()).build();
            recTableArray.add(recJson);
        }
        JsonArray recTablesArray = recTableArray.build();
        
        JsonArrayBuilder schTableArray = Json.createArrayBuilder();
        ObservableList<csg_Schedule> sch = dataManager.getScheduleTable();
        for(csg_Schedule schedule: sch){
            JsonObject schJson = Json.createObjectBuilder()
                    .add(JSON_TYPE, schedule.getType())
                    .add(JSON_DATE, schedule.getDate())
                    .add(JSON_TITLE, schedule.getTitle())
                    .add(JSON_TOPIC, schedule.getTopic())
                    .add(JSON_SCH_TIME, schedule.getTime())
                    .add(JSON_LINK, schedule.getLink())
                    .add(JSON_CRITERIA, schedule.getCriteria())
                    .build();
            schTableArray.add(schJson);
        }
        JsonArray schTablesArray = schTableArray.build();
        
        JsonArrayBuilder teamTableArray = Json.createArrayBuilder();
        ObservableList<csg_Teams> team = dataManager.getTeamsTable();
        for(csg_Teams teams: team){
            JsonObject teamJson = Json.createObjectBuilder()
                    .add(JSON_TEAMNAME, teams.getName())
                    .add(JSON_COLOR, teams.getColor())
                    .add(JSON_TEXTCOLOR, teams.getTextColor())
                    .add(JSON_LINK, teams.getLink()).build();
            teamTableArray.add(teamJson);        
        }
        JsonArray teamTablesArray  = teamTableArray.build();
        
        JsonArrayBuilder studentTableArray = Json.createArrayBuilder();
        ObservableList<csg_Students> student = dataManager.getStudentsTable();
        for(csg_Students students: student){
            JsonObject studentJson = Json.createObjectBuilder()
                    .add(JSON_FIRSTNAME, students.getFirstName())
                    .add(JSON_LASTNAME, students.getLastName())
                    .add(JSON_TEAM, students.getTeam())
                    .add(JSON_ROLE, students.getRole()).build();
            studentTableArray.add(studentJson);
        }
        JsonArray studentTablesArray = studentTableArray.build();
        
        
	// THEN PUT IT ALL TOGETHER IN A JsonObject
	JsonObject dataManagerJSO = Json.createObjectBuilder()
                
		.add(JSON_START_HOUR, "" + dataManager.getStartHour())
		.add(JSON_END_HOUR, "" + dataManager.getEndHour())
                .add(JSON_UNDERGRAD_TAS, undergradTAsArray)
                .add(JSON_OFFICE_HOURS, timeSlotsArray)
                .add(JSON_SITEPAGES, sitePagesArray)
                .add(JSON_RECITATION, recTablesArray)
                .add(JSON_SCHEDULE, schTablesArray)
                .add(JSON_TEAMS, teamTablesArray)
                .add(JSON_STUDENTS, studentTablesArray)
		.build();
	
	// AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
	Map<String, Object> properties = new HashMap<String, Object>(1);
	properties.put(JsonGenerator.PRETTY_PRINTING, true);
	JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
	StringWriter sw = new StringWriter();
	JsonWriter jsonWriter = writerFactory.createWriter(sw);
	jsonWriter.writeObject(dataManagerJSO);
	jsonWriter.close();

	// INIT THE WRITER
	OutputStream os = new FileOutputStream(filePath);
	JsonWriter jsonFileWriter = Json.createWriter(os);
	jsonFileWriter.writeObject(dataManagerJSO);
	String prettyPrinted = sw.toString();
	PrintWriter pw = new PrintWriter(filePath);
	pw.write(prettyPrinted);
	pw.close();
    }
    
    
    
    public void saveCourseDetails(AppDataComponent data, String filePath) throws IOException{
        csg_TAData dataManager = (csg_TAData)data;
        csg_Workspace workspace = (csg_Workspace)app.getWorkspaceComponent();
       
            JsonObject courseDetailsJson = Json.createObjectBuilder()
                    .add(JSON_SUBJECT_EXPORT, (String)workspace.getSubjectCombo().getValue())
                    .add(JSON_SEMESTER_EXPORT, (String)workspace.getSemesterCombo().getValue())
                    .add(JSON_NUMBER_EXPORT, (String)workspace.getNumberCombo().getValue())
                    .add(JSON_YEAR_EXPORT, (String)workspace.getYearCombo().getValue())
                    .add(JSON_TITLE_EXPORT, workspace.getTitleField().getText())
                    .add(JSON_INSTRUCTORNAME_EXPORT, workspace.getInstructorNameField().getText())
                    .add(JSON_INSTRUCTORHOME_EXPORT, workspace.getInstructorHomeField().getText()).build();
            
        Map<String, Object> properties = new HashMap<String, Object>(1);
	properties.put(JsonGenerator.PRETTY_PRINTING, true);
	JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
	StringWriter sw = new StringWriter();
	JsonWriter jsonWriter = writerFactory.createWriter(sw);
	jsonWriter.writeObject(courseDetailsJson);
	jsonWriter.close();

	// INIT THE WRITER
	OutputStream os = new FileOutputStream(filePath);
	JsonWriter jsonFileWriter = Json.createWriter(os);
	jsonFileWriter.writeObject(courseDetailsJson);
	String prettyPrinted = sw.toString();
	PrintWriter pw = new PrintWriter(filePath);
	pw.write(prettyPrinted);
	pw.close();
        
    }
    
    public void saveRecitationData(AppDataComponent data, String filePath) throws IOException{
        csg_TAData dataManager = (csg_TAData)data;
        
        
        JsonArrayBuilder recTableArray = Json.createArrayBuilder();
        ObservableList<csg_Recitation> rec = dataManager.getRecTable();
        for(csg_Recitation recitation: rec){
            JsonObject recJson = Json.createObjectBuilder()
                    .add(JSON_SECTION_EXPORT, recitation.getSection())
                    .add(JSON_INSTRUCTOR_EXPORT, recitation.getInstructor())
                    .add(JSON_DAYTIME_EXPORT, recitation.getDayTime())
                    .add(JSON_LOCATION_EXPORT, recitation.getLocation())
                    .add(JSON_TA1_EXPORT, recitation.getTa())
                    .add(JSON_TA2_EXPORT, recitation.getTa2()).build();
            recTableArray.add(recJson);
        }
        JsonArray recitationTablesArray = recTableArray.build();
        JsonObject dataManagerJSO = Json.createObjectBuilder()
                .add(JSON_RECITATION_EXPORT, recitationTablesArray).build();
        
        
        Map<String, Object> properties = new HashMap<String, Object>(1);
	properties.put(JsonGenerator.PRETTY_PRINTING, true);
	JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
	StringWriter sw = new StringWriter();
	JsonWriter jsonWriter = writerFactory.createWriter(sw);
	jsonWriter.writeObject(dataManagerJSO);
	jsonWriter.close();

	// INIT THE WRITER
	OutputStream os = new FileOutputStream(filePath);
	JsonWriter jsonFileWriter = Json.createWriter(os);
	jsonFileWriter.writeObject(dataManagerJSO);
	String prettyPrinted = sw.toString();
	PrintWriter pw = new PrintWriter(filePath);
	pw.write(prettyPrinted);
	pw.close();
        //JsonArray recTablesArray = recTableArray.build();
    }
    
    public void saveScheduleData(AppDataComponent data, String filePath) throws IOException{
        csg_TAData dataManager = (csg_TAData)data;
        csg_Workspace workspace = (csg_Workspace)app.getWorkspaceComponent();
        
//        String startingMonday = workspace.getDate().getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
//        String endingFriday = workspace.getDate2().getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
//        String selectedDate = workspace.getDate3().getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        
        int mondayMonth = workspace.getDate().getValue().getMonthValue();
        String startingMondayMonth = Integer.toString(mondayMonth);
        int mondayDay = workspace.getDate().getValue().getDayOfMonth();
        String startingMondayDay = Integer.toString(mondayDay);
        
        int fridayMonth = workspace.getDate2().getValue().getMonthValue();
        String endingFridayMonth = Integer.toString(fridayMonth);
        int fridayDay = workspace.getDate2().getValue().getDayOfMonth();
        String endingFridayDay = Integer.toString(fridayDay);
        
//        int selectedMonth = workspace.getDate3().getValue().getMonthValue();
//        String selectedMonthNum = Integer.toString(selectedMonth);
//        int selectedDay = workspace.getDate3().getValue().getDayOfMonth();
//        String selectedDayNum = Integer.toString(selectedDay);
        

        
        JsonArrayBuilder schHolidayArray = Json.createArrayBuilder();
        ObservableList<csg_Schedule> sch = dataManager.getScheduleTable();
        for(csg_Schedule schedule: sch){
            if(schedule.getType().equals("Holiday")){
                String date = schedule.getDate();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                LocalDate localDate = LocalDate.parse(date, formatter);
                int selectedMonth = localDate.getMonthValue();
                String selectedMonthNum = Integer.toString(selectedMonth);
                int selectedDay = localDate.getDayOfMonth();
                String selectedDayNum = Integer.toString(selectedDay);
                
                JsonObject schHoliday = Json.createObjectBuilder()
                    .add(JSON_MONTH_EXPORT, selectedMonthNum)
                    .add(JSON_DAY_EXPORT, selectedDayNum)
                    .add(JSON_TITLESCH_EXPORT, schedule.getTitle())
                    .add(JSON_LINK_EXPORT, schedule.getLink())
                    .build(); 
                schHolidayArray.add(schHoliday);
            }
        }JsonArray scheduleHolidayArray = schHolidayArray.build();
        
        JsonArrayBuilder schLectureArray = Json.createArrayBuilder();
        ObservableList<csg_Schedule> schLec = dataManager.getScheduleTable();
            for(csg_Schedule schedule : schLec){
            if(schedule.getType().equals("Lecture")){
                String date = schedule.getDate();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                LocalDate localDate = LocalDate.parse(date, formatter);
                int selectedMonth = localDate.getMonthValue();
                String selectedMonthNum = Integer.toString(selectedMonth);
                int selectedDay = localDate.getDayOfMonth();
                String selectedDayNum = Integer.toString(selectedDay);
                
                JsonObject schLecture = Json.createObjectBuilder()
                    .add(JSON_MONTH_EXPORT, selectedMonthNum)
                    .add(JSON_DAY_EXPORT, selectedDayNum)
                    .add(JSON_TITLESCH_EXPORT, schedule.getTitle())
                    .add(JSON_TOPIC_EXPORT, schedule.getTopic())  
                    .add(JSON_LINK_EXPORT, schedule.getLink())
                    .build(); 
                schLectureArray.add(schLecture);                
                }
            }JsonArray scheduleLectureArray = schLectureArray.build();
            
            JsonArrayBuilder schReferenceArray = Json.createArrayBuilder();
            ObservableList<csg_Schedule> schRef = dataManager.getScheduleTable();
                for(csg_Schedule schedule : schRef){
                    if(schedule.getType().equals("Reference")){
                        String date = schedule.getDate();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                        LocalDate localDate = LocalDate.parse(date, formatter);
                        int selectedMonth = localDate.getMonthValue();
                        String selectedMonthNum = Integer.toString(selectedMonth);
                        int selectedDay = localDate.getDayOfMonth();
                        String selectedDayNum = Integer.toString(selectedDay);
                        
                        JsonObject schReference = Json.createObjectBuilder()
                                .add(JSON_MONTH_EXPORT, selectedMonthNum)
                                .add(JSON_DAY_EXPORT, selectedDayNum)
                                .add(JSON_TITLESCH_EXPORT, schedule.getTitle())
                                .add(JSON_TOPIC_EXPORT, schedule.getTopic())  
                                .add(JSON_LINK_EXPORT, schedule.getLink())
                                .build(); 
                        schReferenceArray.add(schReference);
                    }
                }JsonArray scheduleReferenceArray = schReferenceArray.build();
                
            JsonArrayBuilder schRecitationArray = Json.createArrayBuilder();
            ObservableList<csg_Schedule> schRec = dataManager.getScheduleTable();
                for(csg_Schedule schedule : schRec){
                    if(schedule.getType().equals("Recitation")){
                        String date = schedule.getDate();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                        LocalDate localDate = LocalDate.parse(date, formatter);
                        int selectedMonth = localDate.getMonthValue();
                        String selectedMonthNum = Integer.toString(selectedMonth);
                        int selectedDay = localDate.getDayOfMonth();
                        String selectedDayNum = Integer.toString(selectedDay);
                        
                        JsonObject schRecitation = Json.createObjectBuilder()
                                .add(JSON_MONTH_EXPORT, selectedMonthNum)
                                .add(JSON_DAY_EXPORT, selectedDayNum)
                                .add(JSON_TITLESCH_EXPORT, schedule.getTitle())
                                .add(JSON_TOPIC_EXPORT, schedule.getTopic())  
                                //.add(JSON_LINK_EXPORT, schedule.getLink())
                                .build(); 
                        schRecitationArray.add(schRecitation);
                    }
                }JsonArray scheduleRecitationArray = schRecitationArray.build();
                
            JsonArrayBuilder schHWArray = Json.createArrayBuilder();
            ObservableList<csg_Schedule> schHW = dataManager.getScheduleTable();
                for(csg_Schedule schedule : schHW){
                    if(schedule.getType().equals("HW")){
                        String date = schedule.getDate();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                        LocalDate localDate = LocalDate.parse(date, formatter);
                        int selectedMonth = localDate.getMonthValue();
                        String selectedMonthNum = Integer.toString(selectedMonth);
                        int selectedDay = localDate.getDayOfMonth();
                        String selectedDayNum = Integer.toString(selectedDay);
                        
                        JsonObject schHomework = Json.createObjectBuilder()
                                .add(JSON_MONTH_EXPORT, selectedMonthNum)
                                .add(JSON_DAY_EXPORT, selectedDayNum)
                                .add(JSON_TITLESCH_EXPORT, schedule.getTitle())
                                .add(JSON_TOPIC_EXPORT, schedule.getTopic())  
                                .add(JSON_LINK_EXPORT, schedule.getLink())
                                .add(JSON_TIME_EXPORT, schedule.getTime())
                                .add(JSON_CRITERIA_EXPORT, schedule.getCriteria())
                                .build(); 
                        schHWArray.add(schHomework);
                    }
                }JsonArray scheduleHWArray = schHWArray.build();
                    
                JsonObject dataManagerJSO = Json.createObjectBuilder()
                        .add(JSON_STARTINGMONDAY_MONTH_EXPORT, "" + startingMondayMonth)
                        .add(JSON_STARTINGMONDAY_DAY_EXPORT, "" + startingMondayDay)
                        .add(JSON_ENDINGFRIDAY_MONTH_EXPORT, "" + endingFridayMonth)
                        .add(JSON_ENDINGFRIDAY_DAY_EXPORT, "" + endingFridayDay)
                        .add(JSON_HOLIDAYS_EXPORT, scheduleHolidayArray)
                        .add(JSON_LECTURES_EXPORT, scheduleLectureArray)
                        .add(JSON_REFERENCES_EXPORT, scheduleReferenceArray)
                        .add(JSON_SCHRECITATION_EXPORT, scheduleRecitationArray)
                        .add(JSON_HW_EXPORT, scheduleHWArray)
                        .build();
                
        Map<String, Object> properties = new HashMap<String, Object>(1);
	properties.put(JsonGenerator.PRETTY_PRINTING, true);
	JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
	StringWriter sw = new StringWriter();
	JsonWriter jsonWriter = writerFactory.createWriter(sw);
	jsonWriter.writeObject(dataManagerJSO);
	jsonWriter.close();

	// INIT THE WRITER
	OutputStream os = new FileOutputStream(filePath);
	JsonWriter jsonFileWriter = Json.createWriter(os);
	jsonFileWriter.writeObject(dataManagerJSO);
	String prettyPrinted = sw.toString();
	PrintWriter pw = new PrintWriter(filePath);
	pw.write(prettyPrinted);
	pw.close();
        
    }
    
    public void saveTeamsData(AppDataComponent data, String filePath) throws IOException{
        csg_TAData dataManager = (csg_TAData)data;
        csg_Workspace workspace = (csg_Workspace)app.getWorkspaceComponent();
        
        JsonArrayBuilder teamArray = Json.createArrayBuilder();
        ObservableList<csg_Teams> team = dataManager.getTeamsTable();
            for(csg_Teams teams: team){
                String color = teams.getColor();
                int r = hex2decimal(color.substring(0, 2));
                int g = hex2decimal(color.substring(2, 4));
                int b = hex2decimal(color.substring(4, 6));
                String red = Integer.toString(r);
                String green = Integer.toString(g);
                String blue = Integer.toString(b);
                String textColor = teams.getTextColor();
                
                JsonObject teamExport = Json.createObjectBuilder()
                        .add(JSON_TEAMNAME_EXPORT, teams.getName())
                        .add(JSON_RED_EXPORT, "" + red)
                        .add(JSON_GREEN_EXPORT, "" + green)
                        .add(JSON_BLUE_EXPORT, "" + blue)
                        .add(JSON_TEXTCOLOR_EXPORT, teams.getTextColor())
                        .build();      
                teamArray.add(teamExport);
            }JsonArray teamsArray = teamArray.build();
            
            JsonObject dataManagerJSO = Json.createObjectBuilder()
                .add(JSON_TEAMS_EXPORT, teamsArray).build();
            
            Map<String, Object> properties = new HashMap<String, Object>(1);
	properties.put(JsonGenerator.PRETTY_PRINTING, true);
	JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
	StringWriter sw = new StringWriter();
	JsonWriter jsonWriter = writerFactory.createWriter(sw);
	jsonWriter.writeObject(dataManagerJSO);
	jsonWriter.close();

	// INIT THE WRITER
	OutputStream os = new FileOutputStream(filePath);
	JsonWriter jsonFileWriter = Json.createWriter(os);
	jsonFileWriter.writeObject(dataManagerJSO);
	String prettyPrinted = sw.toString();
	PrintWriter pw = new PrintWriter(filePath);
	pw.write(prettyPrinted);
	pw.close();
            
    }
    
    // IMPORTING/EXPORTING DATA IS USED WHEN WE READ/WRITE DATA IN AN
    // ADDITIONAL FORMAT USEFUL FOR ANOTHER PURPOSE, LIKE ANOTHER APPLICATION

    @Override
    public void importData(AppDataComponent data, String filePath) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void exportData(AppDataComponent data, String filePath) throws IOException {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        csg_TAData dataManager = (csg_TAData)data;
        saveCourseDetails(data, filePath);       
    }
    
    public static int hex2decimal(String s) {
             String digits = "0123456789ABCDEF";
             s = s.toUpperCase();
             int val = 0;
             for (int i = 0; i < s.length(); i++) {
                 char c = s.charAt(i);
                 int d = digits.indexOf(c);
                 val = 16*val + d;
             }
             return val;
         }
}