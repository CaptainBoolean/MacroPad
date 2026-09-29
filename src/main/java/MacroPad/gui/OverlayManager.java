package MacroPad.gui;

import javafx.application.Application;
import javafx.stage.Stage;

public class OverlayManager extends Application {
    @Override
    public void start(Stage stage) {}

    public static void startJavaFX() {

        Thread thread = new Thread(() -> Application.launch(OverlayManager.class));

        thread.setDaemon(true);
        thread.start();
    }
}
