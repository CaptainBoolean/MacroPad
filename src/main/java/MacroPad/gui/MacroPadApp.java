package MacroPad.gui;

import MacroPad.macro.MacroManager;
import MacroPad.midi.PageManager;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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

        BorderPane root = new BorderPane();
        root.setCenter(grid);
        root.setTop(header);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #0b1220;");

        Scene scene = new Scene(root, 800, 850);
        scene.getStylesheets().add(getClass().getResource("/macropad.css").toExternalForm());

        stage.setTitle("MacroPad");
        stage.setMinWidth(700);
        stage.setMinHeight(750);
        stage.setScene(scene);
        stage.show();
    }
}
