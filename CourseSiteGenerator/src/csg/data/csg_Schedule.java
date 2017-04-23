
package csg.data;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
/**
 *
 * @author Richu
 */
public class csg_Schedule {
    private final StringProperty type;
    private final StringProperty date;
    private final StringProperty title;
    private final StringProperty topic;
    
    public csg_Schedule(String Type, String Date, String Title, String Topic){
        type = new SimpleStringProperty(Type);
        date = new SimpleStringProperty(Date);
        title = new SimpleStringProperty(Title);
        topic = new SimpleStringProperty(Topic);
    }
    
    public String getType(){
        return type.get();
    }
    
    public void setType(String Type){
        type.set(Type);
    }
    
    public String getDate(){
        return date.get();
    }
    
    public void setData(String Date){
        date.set(Date);
    }
    
    public String getTitle(){
        return title.get();
    }
    
    public void setTitle(String Title){
        title.set(Title);
    }
    
    public String getTopic(){
        return topic.get();
    }
    
    public void setTopic(String Topic){
        topic.set(Topic);
    }
    
    public String toString(){
       return type.toString();
    }
}
