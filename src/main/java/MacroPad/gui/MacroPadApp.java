package MacroPad.gui;

import MacroPad.macro.MacroManager;
import MacroPad.midi.PageManager;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
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
        SidebarPanel sidebar = new SidebarPanel();

        StackPane launchpadFrame = new StackPane(launchpadGrid);
        launchpadFrame.setAlignment(Pos.CENTER);
        launchpadFrame.getStyleClass().add("launchpad-frame");
        launchpadFrame.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        launchpadFrame.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

        HBox threePillars = new HBox(100);
        threePillars.setPadding(Insets.EMPTY);
        threePillars.setAlignment(Pos.CENTER);
        threePillars.setFillHeight(true);

        HBox.setHgrow(sidebar, Priority.ALWAYS);
        HBox.setHgrow(launchpadFrame, Priority.NEVER);
        HBox.setHgrow(settingsPanel, Priority.ALWAYS);
        sidebar.setMaxWidth(Double.MAX_VALUE);
        settingsPanel.setMaxWidth(Double.MAX_VALUE);

        threePillars.getChildren().addAll(sidebar, launchpadFrame, settingsPanel);

        BorderPane root = new BorderPane();
        root.setCenter(threePillars);
        root.setPadding(Insets.EMPTY);

        VBox mainLayout = new VBox(root);
        VBox.setVgrow(root, Priority.ALWAYS);
        root.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        sidebar.prefHeightProperty().bind(root.heightProperty());
        settingsPanel.prefHeightProperty().bind(root.heightProperty());

        Scene scene = new Scene(mainLayout);
        scene.getStylesheets().add(getClass().getResource("/macropad.css").toExternalForm());

        stage.setTitle("MacroPad");
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }
}