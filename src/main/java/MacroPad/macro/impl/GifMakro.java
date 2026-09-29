package MacroPad.macro.impl;

import MacroPad.macro.Macro;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class GifMakro implements Macro {
    private final String filePath;

    public GifMakro(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void execute() {
        Platform.runLater(() -> {
           Stage stage = new Stage();
           stage.initStyle(StageStyle.TRANSPARENT);

           Image image = new Image(getClass().getResourceAsStream("/gifs/" + filePath));

           ImageView imageView = new ImageView(image);
           StackPane root = new StackPane(imageView);
           Scene scene = new Scene(root);
           scene.setFill(null);
           stage.setScene(scene);
           stage.show();
        });
    }
}
