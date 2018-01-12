package csg.file;


import java.util.ArrayList;
import java.util.HashMap;
import javafx.beans.property.StringProperty;
import csg.data.csg_TAData;

/**
 *
 * @author Richu
 */
public class csg_TimeSlot {
    private String day;
    private String time;
    private String name;
    
    public csg_TimeSlot(String initDay, String initTime, String initName) {
        day = initDay;
        time = initTime;
        name = initName;
    }
    
    // ACCESSORS AND MUTATORS
    
    public String getDay() { return day; }
    public String getTime() { return time; }
    public String getName() { return name; }
    public String getKey() {
        return day + " " + time + " " + name;
    }    
    
    public void setDay(String initDay) {
        day = initDay;
    }
    public void setTime(String initTime) {
        time = initTime;
    }
    public void setName(String initName) {
        name = initName;
    }
    
    
    public static ArrayList<csg_TimeSlot> buildOfficeHoursList(csg_TAData data) {
        // BUILD AND RETURN THIS LIST
        ArrayList<csg_TimeSlot> officeHoursList = new ArrayList();
        ArrayList<String> gridHeaders = data.getGridHeaders();
        HashMap<String, StringProperty> officeHours = data.getOfficeHours();
        for (int row = 1; row < data.getNumRows(); row++) {
            for (int col = 2; col < 7; col++) {
                // WE ONLY WANT THE DATA, NOTE THE HEADERS
                String cellKey = data.getCellKey(col, row);
                StringProperty timeSlotProp = officeHours.get(cellKey);
                String timeSlotText = timeSlotProp.getValue();
                String[] taNames = timeSlotText.split("\n");
                String day = gridHeaders.get(col);
                String timeCellKey = data.getCellKey(0, row);
                String time = officeHours.get(timeCellKey).getValue().replace(":", "_");
                for (int i = 0; i < taNames.length; i++) {
                    String taName = taNames[i];
                    if (taName.length() > 0) {
                        // ADD A TIME SLOT FOR EACH TA FOR EACH GRID CELL
                        csg_TimeSlot ts = new csg_TimeSlot(day, time, taName);
                        officeHoursList.add(ts);
                    }
                }
            }
        }
        return officeHoursList;
    }
}