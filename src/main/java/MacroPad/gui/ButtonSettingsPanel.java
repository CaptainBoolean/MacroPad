package MacroPad.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ButtonSettingsPanel extends VBox {

    private final ButtonConfigManager configManager;

    private boolean configureMode = false;

    private final Button configureButton = new Button("Configure Button");
    private final Label selectedButton = new Label("No Button selected");
    private final TextField buttonNameField = new TextField();
    private final Button saveButton = new Button("Save");
    private final Button cancelButton = new Button("Cancel");

    public ButtonSettingsPanel(ButtonConfigManager configManager) {
        this.configManager = configManager;

        setSpacing(10);
        setPadding(new Insets(20, 10, 20, 10));
        setPrefWidth(320);
        setMinWidth(320);
        getStyleClass().add("settings-panel");

        Label title = new Label("Button Settings");
        title.setMaxWidth(Double.MAX_VALUE);
        title.setAlignment(Pos.CENTER);
        title.setStyle("-fx-text-fill: white; -fx-font-size: 18px;");

        selectedButton.setStyle("-fx-text-fill: white;");

        buttonNameField.setPromptText("Button Name");
        buttonNameField.setEditable(false);

        saveButton.setDisable(true);
        cancelButton.setDisable(true);

        configureButton.setOnAction(e -> toggleConfigureMode());
        saveButton.setOnAction(e -> saveName());
        cancelButton.setOnAction(e -> loadSelectedName());

        getChildren().addAll(
                configureButton,
                title,
                selectedButton,
                new Label("Button Name"),
                buttonNameField,
                new HBox(10, saveButton, cancelButton)
        );
    }

    public boolean isConfigureMode() {
        return configureMode;
    }

    public void selectButton(int page, int row, int col) {
        configManager.selectButton(page, row, col);
        selectedButton.setText("Page: " + (page + 1) + " Row: " + (row + 1) + " Col: " + (col + 1));
        loadSelectedName();
    }

    private void toggleConfigureMode() {
        configureMode = !configureMode;

        configureButton.setText(configureMode ? "Cancel Configuration" : "Configure Button");
        buttonNameField.setEditable(configureMode);
        saveButton.setDisable(!configureMode || !configManager.hasSelection());
        cancelButton.setDisable(!configureMode || !configManager.hasSelection());
    }

    private void saveName() {
        if (!configureMode || !configManager.hasSelection()) {
            return;
        }

        configManager.saveSelectedName(buttonNameField.getText().trim());
        loadSelectedName();
    }

    private void loadSelectedName() {
        buttonNameField.setText(configManager.getSelectedName());
    }
}