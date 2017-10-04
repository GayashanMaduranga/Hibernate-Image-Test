import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.input.MouseEvent;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by gayashan on 8/28/2017.
 */
public class addStudentViewController implements Initializable {


    @FXML
    private TreeTableView<Student> table;


    @FXML
    private TreeTableColumn<Student, Number> col1;

    @FXML
    private TreeTableColumn<Student, String> col2;

    @FXML
    private TextField id;

    @FXML
    private TextField name;

    private TreeItem<Student> items;

    private List<TreeItem<Student>> itemList;

    private   Session session;
    @FXML
    void addToTable(MouseEvent event) {

        int sid = Integer.parseInt(id.getText());
        String sname = name.getText();

        Student s = new Student();
        s.setId(sid);
        s.setName(sname);

        session.beginTransaction();
        session.save(s);
        session.getTransaction().commit();

        itemList.add(new TreeItem<>(s));

        table.getRoot().getChildren().clear();
        table.getRoot().getChildren().addAll(itemList);
    }

    @FXML
    void deleteFromTable(MouseEvent event) {

        TreeItem<Student> s = table.getSelectionModel().getSelectedItem();

        session.beginTransaction();
        session.delete(s.getValue());
        session.getTransaction().commit();

        itemList.remove(s);
        table.getRoot().getChildren().clear();
        table.getRoot().getChildren().addAll(itemList);

    }


    @FXML
    void updateTable(MouseEvent event) {

        TreeItem<Student> s = table.getSelectionModel().getSelectedItem();
        s.getValue().setName(name.getText());

        session.beginTransaction();
        session.update(s.getValue());
        session.getTransaction().commit();


    }

    @FXML
    void setFields(MouseEvent event) {
        Student s = table.getSelectionModel().getSelectedItem().getValue();
        id.setText(String.valueOf(s.getId()));
        name.setText(s.getName());


    }


    public void initialize(URL location, ResourceBundle resources) {


        Configuration configuration = new Configuration().configure();

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        SessionFactory sessionFactory =configuration.buildSessionFactory(serviceRegistry);
        session=sessionFactory.openSession();


///############################################################################################################################
        session.beginTransaction();
        itemList = new ArrayList<>();

        Query query = session.createQuery("select s from Student s");
        List<Student> students = query.list();

        session.getTransaction().commit();

        for (Student s : students){
            itemList.add(new TreeItem<>(s));
        }



        col1.setCellValueFactory(param -> param.getValue().getValue().idProperty());

        col2.setCellValueFactory(param -> param.getValue().getValue().nameProperty());

        Student s = new Student();
        s.setName("NAME");
        s.setId(0);
        TreeItem<Student> root = new TreeItem<>(s);
        root.getChildren().addAll(itemList);
        table.setRoot(root);
        table.setShowRoot(false);


    }


    @FXML
    void exit(MouseEvent event) {
        //System.exit(0);




    }

    @FXML
    void reset(MouseEvent event) {
        id.setText("");
        name.setText("");
        table.getSelectionModel().select(null);
    }
}
