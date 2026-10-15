package MacroPad.gui;

import MacroPad.macro.MacroManager;
import MacroPad.midi.PageManager;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class MacroPadApp extends Application {
    private static MacroManager macroManager;
    private static PageManager pageManager;

    private static boolean configureMode = false;
    private int selectedPage = -1;
    private int selectedRow = -1;
    private int selectedCol = -1;

    private String[][][] buttonNames = new String[8][8][8];
    private TextField buttonNameField;
    private Button saveButton;
    private Button cancelButton;



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

        Button configureButton = new Button("Configure Button");

        Label selectedButton = new Label("No Button selected");
        selectedButton.setStyle("-fx-text-fill: white;");

        buttonNameField = new TextField();
        buttonNameField.setEditable(!configureMode);
        buttonNameField.setPromptText("Button Name");

        saveButton = new Button("Save");
        saveButton.setDisable(true);
        cancelButton = new Button("Cancel");
        cancelButton.setDisable(true);

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
                    button.setOnAction(e -> {
                        pageManager.changePage(r - 1);
                        int page = pageManager.getCurrentPage();

                        for (int rowIndex = 0; rowIndex < 8; rowIndex++) {
                            for (int colIndex = 0; colIndex < 8; colIndex++) {
                                String name = buttonNames[page][rowIndex][colIndex];
                            }
                        }
                    });
                } else {
                    button.setOnAction(e -> {
                        if (configureMode) {
                            selectedPage = pageManager.getCurrentPage();
                            selectedRow = r - 1;
                            selectedCol = c;
                            buttonNameField.setText(
                                buttonNames[selectedPage][selectedRow][selectedCol] == null ? "" :  buttonNames[selectedPage][selectedRow][selectedCol] // doesnt work for 2d array
                            );

                            selectedButton.setText("Page: " +  (selectedPage + 1) + " Row: " + (selectedRow + 1) + " Col: " + (selectedCol + 1));

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

        configureButton.setOnAction(e -> {
            configureMode = !configureMode;

            if(configureMode) {
                configureButton.setText("Cancel Configuration");
            } else {
                configureButton.setText("Configure Button");
                selectedButton.setText("No Button selected");
            }

            buttonNameField.setDisable(!configureMode);
            saveButton.setDisable(!configureMode);
            cancelButton.setDisable(!configureMode);
        });

        saveButton.setOnAction(e -> {
            if (!configureMode || selectedPage < 0 || selectedRow < 0 || selectedCol < 0) {
                return;
            }

            String name = buttonNameField.getText().trim();

            buttonNames[selectedPage][selectedRow][selectedCol] = name;
        });

        cancelButton.setOnAction(e -> {
            if (selectedPage < 0 || selectedRow < 0 || selectedCol < 0) {
                buttonNameField.clear();
                return;
            }

            String savedName =
                    buttonNames[selectedPage][selectedRow][selectedCol];

            buttonNameField.setText(savedName == null ? "" : savedName);
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

        settingsPanel.getChildren().addAll(settingsTitle, selectedButton, new Label("Button Name"), buttonNameField, new HBox(10, saveButton, cancelButton));

        HBox content = new HBox(30, launchpadFrame, settingsPanel);
        content.setAlignment(Pos.TOP_LEFT);
        content.setPadding(new Insets(10));

        BorderPane root = new BorderPane();
        root.setCenter(content);
        root.setTop(header);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #0b1220;");

        Scene scene = new Scene(root, 1300, 920);
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
