package MacroPad.macro.Overlay;

import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class CustomOverlay {

    private final Stage stage;

    public CustomOverlay (Parent content, int width, int height){
        stage = new Stage();
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setAlwaysOnTop(true);

        content.setMouseTransparent(true);

        Scene scene = new Scene(content, width, height);
        scene.setFill(Color.TRANSPARENT);

        stage.setScene(scene);
    }

    public void show() {
        Platform.runLater(() -> {
            if (!stage.isShowing()) {
                stage.show();
            }
        });
    }

    public void hide() {
        Platform.runLater(() -> {
            if (stage.isShowing()) {
                stage.hide();
            }
        });
    }

    public void toggle() {
        Platform.runLater(() -> {
            if (stage.isShowing()) {
                stage.hide();
            } else {
                stage.show();
            }
        });
    }

    public boolean isVisible() {
        return stage.isShowing();
    }

    public void close() {
        Platform.runLater(stage::close);
    }
}
