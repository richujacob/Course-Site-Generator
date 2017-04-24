package csg.data;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.CheckBox;

/**
 *
 * @author Richu
// */
public class csg_TeachingAssistant<E extends Comparable<E>> implements Comparable<E>  {
    // THE TABLE WILL STORE TA NAMES AND EMAILS
    private final StringProperty name;
    private final StringProperty email;
    private CheckBox checkBox;

    
    /**
     * Constructor initializes both the TA name and email.
     */
    public csg_TeachingAssistant(String initName, String initEmail/* boolean checkBox*/) {
        name = new SimpleStringProperty(initName);
        email = new SimpleStringProperty(initEmail);
        this.checkBox=checkBox;
    }

    // ACCESSORS AND MUTATORS FOR THE PROPERTIES

    public String getName() {
        return name.get();
    }

    public void setName(String initName) {
        name.set(initName);
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String initEmail) {
        email.set(initEmail);
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }
    
    
    
    @Override
    public int compareTo(E otherTA) {
        return getName().compareTo(((csg_TeachingAssistant)otherTA).getName());
    }
    
    @Override
    public String toString() {
        return name.getValue();
    }
}
