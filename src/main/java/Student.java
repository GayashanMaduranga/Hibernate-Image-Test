import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.*;

/**
 * Created by gayashan on 8/28/2017.
 */
@Entity
@Table(name = "STUENT_TABLE")
@Access(AccessType.PROPERTY)
public class Student {

    IntegerProperty id;
    StringProperty name;

    public Student() {
        this.id = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
    }

    @Id
    @Column(name = "Student_ID")
    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    @Column(name = "Student_Name")
    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }
}
