package MacroPad.gui;

import MacroPad.macro.MacroManager;
import MacroPad.midi.PageManager;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class MacroPadApp extends Application {
    private static MacroManager macroManager;
    private static PageManager pageManager;
    private static final String[] TOP_BUTTON_NAMES = {
            "Vol +",
            "Vol -",
            "Mute",
            "Play/Pause",
            "???",
            "???",
            "???",
            "Capture",
            "Quit"
    };

    public static void setDependencies(MacroManager macroManager, PageManager pageManager) {
        MacroPadApp.macroManager = macroManager;
        MacroPadApp.pageManager = pageManager;
    }

    @Override
    public void start (Stage stage) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(9);
        grid.setVgap(9);

        for(int row = 0; row < 9; row++) {
            for(int col = 0; col < 9; col++) {
                final int r = row;
                final int c = col;

                Button button = new Button();
                button.setPrefSize(82, 75);
                button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

                if (row == 0) {
                    button.setText(TOP_BUTTON_NAMES[col]);
                } else if (col == 8) {
                    button.setText("" + row);
                } else {
                    button.setText("");
                }

                if (row == 0){
                    button.setOnAction(e -> macroManager.handleTopButtonMacros(c));
                } else if (col == 8) {
                    button.setOnAction(e -> pageManager.changePage(r - 1));
                } else {
                    button.setOnAction(e -> macroManager.executeMacro(pageManager.getCurrentPage(),  r - 1, c));
                }

                if (row == 0 || col == 8) {
                    button.getStyleClass().add("edge-pad");
                } else {
                    button.getStyleClass().add("macro-pad");
                }

                grid.add(button, col, row);
            }
        }

        Label title = new Label("MacroPad");
        title.setStyle("-fx-text-fill: white; -fx-font-size: 22px;");

        VBox header = new VBox(10, title);
        header.setPadding(new Insets(0, 0, 15, 0));

        StackPane launchpadFrame = new StackPane(grid);
        launchpadFrame.getStyleClass().add("launchpad-frame");
        launchpadFrame.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        launchpadFrame.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

        BorderPane root = new BorderPane();
        root.setCenter(launchpadFrame);


        root.setTop(header);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #0b1220;");

        Scene scene = new Scene(root, 1200, 900);
        scene.getStylesheets().add(getClass().getResource("/macropad.css").toExternalForm());

        stage.setTitle("MacroPad");
        stage.setMinWidth(1100);
        stage.setMinHeight(900);
        stage.setWidth(1200);
        stage.setHeight(900);
        stage.setScene(scene);
        stage.show();
    }
}
