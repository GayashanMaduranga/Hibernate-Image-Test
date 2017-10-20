import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.openimaj.image.MBFImage;
import org.openimaj.video.Video;
import org.openimaj.video.capture.VideoCapture;
import org.openimaj.video.capture.VideoCaptureException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class VideoController implements Initializable{

    Video<MBFImage> video;

    @FXML
    private ImageView img;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void start(ActionEvent event) {



        cam();

    }


    public void cam(){
        Task<Void> task = new Task<Void>() {


            @Override
            protected Void call() throws Exception {

                try {
                    video = new VideoCapture(320, 240);

                    int i =0;
                    for (MBFImage mbfImage : video) {
                        //DisplayUtilities.displayName(mbfImage.process(new CannyEdgeDetector()), "videoFrames");
                        // img.setImage(mbfImage.getImage().);
                        i++;
                        BufferedImage bufferedImage = org.openimaj.image.ImageUtilities.createBufferedImageForDisplay(mbfImage);

                        System.out.println(i);
                        if(i>100)
                            break;

                        Image image = SwingFXUtils.toFXImage(bufferedImage, null);

                        img.setImage(image);
                        video.close();

                    }



                } catch (VideoCaptureException e) {
                    e.printStackTrace();
                }

                return null;
            }
        };
    }
}
