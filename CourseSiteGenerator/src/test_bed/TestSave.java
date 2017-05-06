/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test_bed;

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
import csg.file.csg_TAFiles;
import csg.file.csg_TimeSlot;
import javafx.collections.FXCollections;

/**
 *
 * @author Richu
 */
public class TestSave {
    static csg_App app; 
    
        public static void main(String [] args){
            try{
            csg_App app = new csg_App();
            //app = new csg_App();
            app.loadProperties("app_properties.xml");
            csg_TAData data = new csg_TAData(app);
            data.addTA(false, "name", "name.email@domain.com");
            data.addPage(false, "Home", "index.html", "homebuilder.js");
            data.addRec("R02", "Mckenna", "Wed 3:30pm - tomorrow", "New CS 321", "Person", "");
            data.addSchedule("Holiday", "Today", "Spring Break", "nothing", "time", "link", "criteria");
            data.addTeam("Atomic Comics", "55211", "fff ffff", "http://atomic.com");
            data.addStudent("RA", "Jack", "Atomic Comics", "Lead Designer");
            TestSave test = new TestSave();
            test.saveData(data,"C:\\Users\\Richu\\219NetBeans\\Final_Proj\\CourseSiteGenerator\\work\\SiteSaveTest.json");
            //csg_TAFiles file = new csg_TAFiles(app);
            //file.loadData(data, "C:\\Users\\Richu\\219NetBeans\\Final_Proj\\CourseSiteGenerator\\work\\siteSaveTest");
            //file.saveData(data,"C:\\Users\\Richu\\219NetBeans\\Final_Proj\\CourseSiteGenerator\\work\\officehourstest");
            }catch(Exception e){
                e.printStackTrace();
            }
            
        }
        
        //csg_App app;
    
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
    //static final boolean JSON_USE = false;
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
    
    
//    public csg_TAFiles(csg_App initApp) {
//        app = initApp;
//    }

    //@Override
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

    //@Override
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
		    .add(JSON_EMAIL, ta.getEmail())
                    .build();
	    taArrayBuilder.add(taJson);
	}
	JsonArray undergradTAsArray = taArrayBuilder.build();

        ObservableList<csg_TimeSlot> officeHours;
	// NOW BUILD THE TIME SLOT JSON OBJCTS TO SAVE
	JsonArrayBuilder timeSlotArrayBuilder = Json.createArrayBuilder();
        officeHours = FXCollections.observableArrayList();
        csg_TimeSlot slot1 = new csg_TimeSlot("MONDAY", "9_30am", "name");
        csg_TimeSlot slot2 = new csg_TimeSlot("TUESDAY", "10_30am", "name");
        officeHours.add(slot1);
        officeHours.add(slot2);
	//ArrayList<csg_TimeSlot> officeHours = csg_TimeSlot.buildOfficeHoursList(dataManager);
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
    
    // IMPORTING/EXPORTING DATA IS USED WHEN WE READ/WRITE DATA IN AN
    // ADDITIONAL FORMAT USEFUL FOR ANOTHER PURPOSE, LIKE ANOTHER APPLICATION

//    @Override
//    public void importData(AppDataComponent data, String filePath) throws IOException {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public void exportData(AppDataComponent data, String filePath) throws IOException {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
    
        
}
