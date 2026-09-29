package MacroPad.macro.impl;

import MacroPad.macro.Macro;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.InputStream;

public class GifMakro implements Macro {
    private final String filePath;

    public GifMakro(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void execute() {
        Platform.runLater(() -> {
            try {
                InputStream inputStream = getClass().getResourceAsStream("/gifs/" + filePath);
                if (inputStream == null) {
                    System.out.println("GIF nicht gefunden: "  + filePath);
                    return;
                }

                Stage stage = new Stage();
                stage.initStyle(StageStyle.TRANSPARENT);

                Image image = new Image(getClass().getResourceAsStream("/gifs/" + filePath));

                ImageView imageView = new ImageView(image);
                StackPane root = new StackPane(imageView);
                Scene scene = new Scene(root);

                scene.setFill(null);

                stage.setScene(scene);
                stage.show();
                stage.setAlwaysOnTop(true);
                stage.toFront();

                PauseTransition delay = new PauseTransition(Duration.seconds(3));

                delay.setOnFinished(event -> stage.close());

                delay.play();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
