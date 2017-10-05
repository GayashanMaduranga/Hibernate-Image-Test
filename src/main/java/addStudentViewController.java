import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.file.Paths;
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
    private Circle empImage;

    @FXML
    private TreeTableColumn<Student, String> col2;

    @FXML
    private TextField id;

    @FXML
    private TextField name;

    private TreeItem<Student> items;

    private List<TreeItem<Student>> itemList;

    private   Session session;
    private FileChooser fileChooser ;
    private Image empimage =null;
    private BufferedImage employeeBufferedImage;

    File file = new File(getClass().getResource("/images/usrImg.jpg").getPath());
    byte[] bFile;

    @FXML
    void addToTable(MouseEvent event) {

        int sid = Integer.parseInt(id.getText());
        String sname = name.getText();


         //windows
        //File file = new File("images/extjsfirstlook.jpg");



        readByteImage();

        Student s = new Student();
        s.setId(sid);
        s.setName(sname);
        s.setImage(bFile);

        session.beginTransaction();
        session.save(s);
        session.getTransaction().commit();

        itemList.add(new TreeItem<>(s));

        table.getRoot().getChildren().clear();
        table.getRoot().getChildren().addAll(itemList);
        table.refresh();
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

    public void readByteImage(){
        bFile = new byte[(int) file.length()];

        try {
            System.out.println(file.length());
            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.read(bFile);
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void updateTable(MouseEvent event) {

        TreeItem<Student> s = table.getSelectionModel().getSelectedItem();
        s.getValue().setName(name.getText());
        readByteImage();
        s.getValue().setImage(bFile);

        session.beginTransaction();
        session.update(s.getValue());
        session.getTransaction().commit();


    }

    @FXML
    void setFields(MouseEvent event) {
        Student s = table.getSelectionModel().getSelectedItem().getValue();
        id.setText(String.valueOf(s.getId()));
        name.setText(s.getName());

        BufferedImage img = null;
        try {
             img = ImageIO.read(new ByteArrayInputStream(s.getImage()));
        }catch (Exception e){
            e.printStackTrace();
        }
        Image image = SwingFXUtils.toFXImage(img, null);

        empImage.setFill(new ImagePattern(image));

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


        fileChooser = new FileChooser();
        empImage.setFill(new ImagePattern(new Image("/images/usrImg.jpg")));

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

    @FXML
    void uplodePhoto(ActionEvent event) {

            fileChooser.setTitle("Select Employee Image");




//        fileChooser.getExtensionFilters().addAll(
//
//                new FileChooser.ExtensionFilter("JPEG Files", "*.jpg"));



            file = fileChooser.showOpenDialog(null);


            try {

                empimage = new Image(file.toURI().toString());
                empImage.setFill(new ImagePattern(empimage));

            }catch (Exception e){
                e.printStackTrace();
            }



    }
}
