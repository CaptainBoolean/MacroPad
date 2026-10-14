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
    private static boolean configureMode = false;
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
                    button.setOnAction(e -> {
                        if (configureMode) {
                            System.out.println("Chosen Button: Page " + pageManager.getCurrentPage() + ", Row" + (r - 1) + ", Col" + c);

                        } else {
                            macroManager.executeMacro(pageManager.getCurrentPage(),  r - 1, c);
                        }
                    });
                }

                if (row == 0 || col == 8) {
                    button.getStyleClass().add("edge-pad");
                } else {
                    button.getStyleClass().add("macro-pad");
                }

                grid.add(button, col, row);
            }
        }

        Button configureButton = new Button("Configure Button");
        configureButton.setOnAction(e -> {
            configureMode = !configureMode;

            if(configureMode) {
                configureButton.setText("Cancel Configuration");
            } else {
                configureButton.setText("Configure Button");
            }
        });

        VBox header = new VBox(10, configureButton);
        header.setPadding(new Insets(0, 0, 15, 0));

        StackPane launchpadFrame = new StackPane(grid);
        launchpadFrame.getStyleClass().add("launchpad-frame");
        launchpadFrame.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        launchpadFrame.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

        VBox settingsPanel = new VBox(10);
        settingsPanel.setPadding(new Insets(20, 10, 20, 10));
        settingsPanel.setPrefWidth(320);
        settingsPanel.setMinWidth(320);
        settingsPanel.prefHeightProperty().bind(launchpadFrame.heightProperty());
        settingsPanel.minHeightProperty().bind(launchpadFrame.heightProperty());
        settingsPanel.maxHeightProperty().bind(launchpadFrame.heightProperty());
        settingsPanel.getStyleClass().add("settings-panel");

        Label settingsTitle = new Label("Button Settings");
        settingsTitle.setMaxWidth(Double.MAX_VALUE);
        settingsTitle.setAlignment(Pos.CENTER);
        settingsTitle.setStyle("-fx-text-fill: white; -fx-font-size: 18px;");

        settingsPanel.getChildren().add(settingsTitle);

        HBox content = new HBox(30, launchpadFrame, settingsPanel);
        content.setAlignment(Pos.TOP_LEFT);
        content.setPadding(new Insets(10));

        BorderPane root = new BorderPane();
        root.setCenter(content);
        root.setTop(header);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #0b1220;");

        Scene scene = new Scene(root, 1200, 900);
        scene.getStylesheets().add(getClass().getResource("/macropad.css").toExternalForm());

        stage.setTitle("MacroPad");
        stage.setMinWidth(1300);
        stage.setMinHeight(920);
        stage.setWidth(1300);
        stage.setHeight(920);
        stage.setScene(scene);
        stage.show();
    }
}
