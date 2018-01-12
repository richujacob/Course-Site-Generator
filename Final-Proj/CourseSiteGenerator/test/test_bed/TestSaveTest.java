/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test_bed;

import csg.csg_App;
import csg.data.csg_CourseDetails;
import csg.data.csg_Recitation;
import csg.data.csg_Schedule;
import csg.data.csg_Students;
import csg.data.csg_TAData;
import csg.data.csg_TeachingAssistant;
import csg.data.csg_Teams;
import csg.file.csg_TimeSlot;
import djf.components.AppDataComponent;
import java.io.FileInputStream;
import java.io.InputStream;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;
import static org.junit.Assert.*;

/**
 *
 * @author Richu
 */
public class TestSaveTest {
    static csg_App app; 
    static final String JSON_START_HOUR = "startHour";
    static final String JSON_END_HOUR = "endHour";
    static final String JSON_OFFICE_HOURS = "officeHours";
    static final String JSON_DAY = "day";
    static final String JSON_TIME = "time";
    static final String JSON_NAME = "name";
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

    
    public TestSaveTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of main method, of class TestSave.
     */
    @Test
//    public void testMain() {
//        System.out.println("main");
//        String[] args = null;
//        TestSave.main(args);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of loadData method, of class TestSave.
     */
    //@Test
    public void testLoadData() throws Exception {
        try{
        csg_App app = new csg_App();
        System.out.println("loadData");
        AppDataComponent data = null;
        csg_TAData dataManager = (csg_TAData)data;
        
        //csg_TAData data = new csg_TAData(app);
        String filePath = "C:\\Users\\Richu\\219NetBeans\\Final_Proj\\CourseSiteGenerator\\work\\SiteSaveTest.json";
        TestSave instance = new TestSave();
        instance.loadData(dataManager, filePath);
        //assertEquals(dataManager, data);
        InputStream is = new FileInputStream(filePath);
	JsonReader jsonReader = Json.createReader(is);
	JsonObject json = jsonReader.readObject();
	jsonReader.close();
	is.close();
	//json;
        //JsonObject json = loadJSONFile(filePath);
        
        
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
            String name = jsonTA.getString(JSON_NAME);
            String email = jsonTA.getString(JSON_EMAIL);
            assertEquals(name, ((csg_TeachingAssistant)dataManager.getTeachingAssistants().get(i)).getName());
            assertEquals(email, ((csg_TeachingAssistant)dataManager.getTeachingAssistants().get(i)).getEmail());
            //dataManager.addTA(name, email);
        }

        // AND THEN ALL THE OFFICE HOURS
        JsonArray jsonOfficeHoursArray = json.getJsonArray(JSON_OFFICE_HOURS);
        for (int i = 0; i < jsonOfficeHoursArray.size(); i++) {
            JsonObject jsonOfficeHours = jsonOfficeHoursArray.getJsonObject(i);
            String day = jsonOfficeHours.getString(JSON_DAY);
            String time = jsonOfficeHours.getString(JSON_TIME);
            String name = jsonOfficeHours.getString(JSON_NAME);
            assertEquals(day, ((csg_TimeSlot)dataManager.getTeachingAssistants().get(i)).getDay());
            assertEquals(time,((csg_TimeSlot)dataManager.getTeachingAssistants().get(i)).getTime());
            assertEquals(name, ((csg_TimeSlot)dataManager.getTeachingAssistants().get(i)).getName());
            //dataManager.addOfficeHoursReservation(day, time, name);
        }
        
        JsonArray jsonSitePagesArray = json.getJsonArray(JSON_SITEPAGES);
        for(int i=0; i<jsonSitePagesArray.size(); i++){
            JsonObject jsonSitePages = jsonSitePagesArray.getJsonObject(i);
            //String use = jsonSitePages.getString(JSON_USE);
            String navbar = jsonSitePages.getString(JSON_NAVBARTITLE);
            String fileName = jsonSitePages.getString(JSON_FILENAME);
            String script = jsonSitePages.getString(JSON_SCRIPT);
            //dataManager.addPage(false, navbar, fileName, script);
            assertEquals(navbar, ((csg_CourseDetails)dataManager.getCourseDetailsInfo().get(i)).getNavbarTitle());
            assertEquals(fileName, ((csg_CourseDetails)dataManager.getCourseDetailsInfo().get(i)).getFileName());
            assertEquals(script, ((csg_CourseDetails)dataManager.getCourseDetailsInfo().get(i)).getScript());
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
            //dataManager.addRec(section, instructor, dayTime, location, ta, ta2);
            assertEquals(section, ((csg_Recitation)dataManager.getRecTable().get(i)).getSection());
            assertEquals(instructor, ((csg_Recitation)dataManager.getRecTable().get(i)).getInstructor());
            assertEquals(dayTime, ((csg_Recitation)dataManager.getRecTable().get(i)).getDayTime());
            assertEquals(location, ((csg_Recitation)dataManager.getRecTable().get(i)).getLocation());
            assertEquals(ta, ((csg_Recitation)dataManager.getRecTable().get(i)).getTa());
            assertEquals(ta2, ((csg_Recitation)dataManager.getRecTable().get(i)).getTa2());
        }
        
        JsonArray jsonScheduleArray = json.getJsonArray(JSON_SCHEDULE);
        for(int i=0; i<jsonScheduleArray.size(); i++){
            JsonObject jsonScheduleTable = jsonScheduleArray.getJsonObject(i);
            String type = jsonScheduleTable.getString(JSON_TYPE);
            String date = jsonScheduleTable.getString(JSON_DATE);
            String title = jsonScheduleTable.getString(JSON_TITLE);
            String topic = jsonScheduleTable.getString(JSON_TOPIC);
            //dataManager.addSchedule(type, date, title, topic);
            assertEquals(type, ((csg_Schedule)dataManager.getScheduleTable().get(i)).getType());
            assertEquals(date, ((csg_Schedule)dataManager.getScheduleTable().get(i)).getDate());
            assertEquals(title, ((csg_Schedule)dataManager.getScheduleTable().get(i)).getTitle());
            assertEquals(topic, ((csg_Schedule)dataManager.getScheduleTable().get(i)).getTopic());
        }
        
        JsonArray jsonTeamsArray = json.getJsonArray(JSON_TEAMS);
        for(int i=0; i<jsonTeamsArray.size(); i++){
            JsonObject jsonTeamsTable = jsonTeamsArray.getJsonObject(i);
            String name = jsonTeamsTable.getString(JSON_TEAMNAME);
            String color = jsonTeamsTable.getString(JSON_COLOR);
            String textColor = jsonTeamsTable.getString(JSON_TEXTCOLOR);
            String link = jsonTeamsTable.getString(JSON_LINK);
            //dataManager.addTeam(name, color, textColor, link);
            assertEquals(name, ((csg_Teams)dataManager.getTeamsTable().get(i)).getName());
            assertEquals(color, ((csg_Teams)dataManager.getTeamsTable().get(i)).getColor());
            assertEquals(textColor, ((csg_Teams)dataManager.getTeamsTable().get(i)).getTextColor());
            assertEquals(link, ((csg_Teams)dataManager.getTeamsTable().get(i)).getLink());
            
        }
        
        JsonArray jsonStudentsArray = json.getJsonArray(JSON_STUDENTS);
        for(int i=0; i<jsonStudentsArray.size(); i++){
            JsonObject jsonStudentsTable = jsonStudentsArray.getJsonObject(i);
            String firstName = jsonStudentsTable.getString(JSON_FIRSTNAME);
            String lastName = jsonStudentsTable.getString(JSON_LASTNAME);
            String team = jsonStudentsTable.getString(JSON_TEAM);
            String role = jsonStudentsTable.getString(JSON_ROLE);
            //dataManager.addStudent(firstName, lastaName, team, role);
            assertEquals(firstName, ((csg_Students)dataManager.getStudentsTable().get(i)).getFirstName());
            assertEquals(lastName, ((csg_Students)dataManager.getStudentsTable().get(i)).getFirstName());
            assertEquals(team, ((csg_Students)dataManager.getStudentsTable().get(i)).getTeam());
            assertEquals(role, ((csg_Students)dataManager.getStudentsTable().get(i)).getRole());
        }
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Test of saveData method, of class TestSave.
     */
//    @Test
//    public void testSaveData() throws Exception {
//        System.out.println("saveData");
//        AppDataComponent data = null;
//        csg_TAData dataManager = (csg_TAData)data;
//        String filePath = "C:\\Users\\Richu\\219NetBeans\\Final_Proj\\CourseSiteGenerator\\work\\SiteSaveTest.json";
//        TestSave instance = new TestSave();
//        instance.saveData(dataManager, filePath);
//        //assertEquals(data, data);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    
}
