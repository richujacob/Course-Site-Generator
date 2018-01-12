
package csg.data;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
/**
 *
 * @author Richu
 */
public class csg_Students {
    private final StringProperty firstName;
    private final StringProperty lastName;
    private final StringProperty team;
    private final StringProperty role;
    
    public csg_Students(String FirstName, String LastName, String Team, String Role){
        firstName = new SimpleStringProperty(FirstName);
        lastName = new SimpleStringProperty(LastName);
        team = new SimpleStringProperty(Team);
        role = new SimpleStringProperty(Role);
    }
    
    public String getFirstName(){
        return firstName.get();
    }
    
    public void setFirstName(String FirstName){
        firstName.set(FirstName);
    }
    
    public String getLastName(){
        return lastName.get();
    }
    
    public void setLastName(String LastName){
        lastName.set(LastName);
    }
    
    public String getTeam(){
        return team.get();
    }
    
    public void setTeam(String Team){
        team.set(Team);
    }
    
    public String getRole(){
        return role.get();
    }
    
    public void setRole(String Role){
        role.set(Role);
    }
    
    public String toString(){
        return firstName.toString();
    }
}

