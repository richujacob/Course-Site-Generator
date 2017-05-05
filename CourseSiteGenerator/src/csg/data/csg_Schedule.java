
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
    private String time;
    private String link;
    private String  criteria;
    
    public csg_Schedule(String Type, String Date, String Title, String Topic, String time, String link, String criteria){
        type = new SimpleStringProperty(Type);
        date = new SimpleStringProperty(Date);
        title = new SimpleStringProperty(Title);
        topic = new SimpleStringProperty(Topic);       
        this.time=time;
        this.link=link;
        this.criteria=criteria;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getCriteria() {
        return criteria;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }
    
    
    
    public String toString(){
       return type.toString();
    }
}
