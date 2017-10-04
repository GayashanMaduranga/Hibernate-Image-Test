import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * Created by gayashan on 8/28/2017.
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {





        FXMLLoader fxmlLoader = new FXMLLoader();
        try {
            fxmlLoader.load(getClass().getResource("test.fxml").openStream());
            primaryStage.setTitle("Kaiz Rulz");
            primaryStage.setScene(new Scene(fxmlLoader.getRoot()));
            primaryStage.show();
        } catch (Exception e) {

            e.printStackTrace();
        }

        primaryStage.setOnCloseRequest(event -> System.exit(0));




    }


    public static void main(String[] args) {
        launch(args);
    }
}