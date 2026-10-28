package MacroPad.gui;

import MacroPad.macro.MacroManager;
import MacroPad.midi.PageManager;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class MacroPadApp extends Application {

    private static MacroManager macroManager;
    private static PageManager pageManager;

    public static void setDependencies(MacroManager macroManager, PageManager pageManager) {
        MacroPadApp.macroManager = macroManager;
        MacroPadApp.pageManager = pageManager;
    }

    @Override
    public void start(Stage stage) {
        ButtonConfigManager configManager = new ButtonConfigManager();
        ButtonSettingsPanel settingsPanel = new ButtonSettingsPanel(macroManager, configManager);
        LaunchpadGrid launchpadGrid = new LaunchpadGrid(macroManager, pageManager, settingsPanel);

        StackPane launchpadFrame = new StackPane(launchpadGrid);
        launchpadFrame.getStyleClass().add("launchpad-frame");
        launchpadFrame.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        launchpadFrame.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

        settingsPanel.prefHeightProperty().bind(launchpadFrame.heightProperty());
        settingsPanel.minHeightProperty().bind(launchpadFrame.heightProperty());
        settingsPanel.maxHeightProperty().bind(launchpadFrame.heightProperty());

        HBox content = new HBox(30, launchpadFrame, settingsPanel);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(10));

        BorderPane root = new BorderPane();
        root.setCenter(content);
        root.setPadding(new Insets(20));

        VBox mainLayout = new VBox(
                new MacroPadMenuBar(),
                root
        );

        Scene scene = new Scene(mainLayout);
        scene.getStylesheets().add(getClass().getResource("/macropad.css").toExternalForm());

        stage.setTitle("MacroPad");
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }
}