
package csg.data;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
/**
 *
 * @author Richu
 */
public class csg_Teams {
    private final StringProperty name;
    private final StringProperty color;
    private final StringProperty textColor;
    private final StringProperty link;
    
    public csg_Teams(String initName, String initColor, String initTextColor, String initLink){
        name = new SimpleStringProperty(initName);
        color = new SimpleStringProperty(initColor);
        textColor = new SimpleStringProperty(initTextColor);
        link  = new SimpleStringProperty(initLink);
    }
    
    public String getName(){
        return name.get();
    }
    
    public void setName(String initName){
        name.set(initName);
    }
    
    public String getColor(){
        return color.get();
    }
    
    public void setColor(String initColor){
        color.set(initColor);
    }
    
    public String getTextColor(){
        return textColor.get();
    }
    
    public void setTextColor(String initTextColor){
        textColor.set(initTextColor);
    }
    
    public String getLink(){
        return link.get();
    }
    
    public void setLink(String initLink){
        link.set(initLink);
    }
    
    public String toString(){
       return name.toString();
    }
    
    
}
