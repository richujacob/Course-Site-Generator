
package csg.data;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
/**
 *
 * @author Richu
 */
public class csg_Recitation {
    private final StringProperty section;
    private final StringProperty instructor;
    private final StringProperty dayTime;
    private final StringProperty location;
    private final StringProperty ta;
    private final StringProperty ta2;
    
    public csg_Recitation(String Section, String Instructor, String DayTime, String Location, String Ta, String Ta2){
        section = new SimpleStringProperty(Section);
        instructor = new SimpleStringProperty(Instructor);
        dayTime = new SimpleStringProperty(DayTime);
        location = new SimpleStringProperty(Location);
        ta = new SimpleStringProperty(Ta);
        ta2 = new SimpleStringProperty(Ta2);
    }
    
    public String getSection(){
       return section.get();
    }
    
    public void setSection(String Section){
        section.set(Section);
    }
    
    public String getInstructor(){
        return instructor.get();
    }
    
    public void setInstructor(String Instructor){
        instructor.set(Instructor);
    }
    
    public String getDayTime(){
        return dayTime.get();
    }
    
    public void setDayTime(String DayTime){
        dayTime.set(DayTime);
    }
    
    public String getLocation(){
        return location.get();
    }
    
    public void setLocation(String Location){
        location.set(Location);
    }
    
    public String getTa(){
        return ta.get();
    }
    
    public void setTa(String Ta){
        ta.set(Ta);
    }   
    
    public String getTa2(){
        return ta2.get();
    }
    
    public void setTa2(String Ta2){
        ta2.set(Ta2);
    }   
        
    public String toString(){
       return section.toString();
    }
}
