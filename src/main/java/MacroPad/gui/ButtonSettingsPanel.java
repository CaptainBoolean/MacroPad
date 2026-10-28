package MacroPad.gui;

import MacroPad.macro.Macro;
import MacroPad.macro.MacroManager;
import MacroPad.macro.impl.AudioMacro;
import MacroPad.macro.impl.GifMacro;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;

public class ButtonSettingsPanel extends VBox {

    private final MacroManager macroManager;
    private final ButtonConfigManager configManager;

    private boolean configureMode = false;

    private final Button configureButton = new Button("Button Configure Mode");
    private final Label selectedButton = new Label("No Button selected");
    private final TextField buttonNameField = new TextField();
    private final Button saveButton = new Button("Save");
    private final Button cancelButton = new Button("Cancel");
    private final ComboBox<String> macroTypeBox = new ComboBox<>();
    private final TextField audioPathField = new TextField();
    private final Button audioPathBrowseButton = new Button("Browse...");

    private final HBox audioSettings = new HBox(10, audioPathField, audioPathBrowseButton);

    public ButtonSettingsPanel(MacroManager macroManager, ButtonConfigManager configManager) {
        this.macroManager = macroManager;
        this.configManager = configManager;

        setSpacing(10);
        setPadding(new Insets(20, 10, 20, 10));
        getStyleClass().add("settings-panel");

        Label title = new Label("Button Settings");
        title.setMaxWidth(Double.MAX_VALUE);
        title.setAlignment(Pos.CENTER);
        title.setStyle("-fx-text-fill: white; -fx-font-size: 18px;");

        selectedButton.setStyle("-fx-text-fill: white;");

        buttonNameField.setPromptText("Button Name");
        buttonNameField.setEditable(false);
        buttonNameField.setDisable(true);

        audioSettings.setVisible(false);
        audioSettings.setManaged(false);
        audioPathField.setPromptText("Select Audio Path");
        audioPathField.setEditable(false);
        audioPathField.setDisable(true);
        audioPathBrowseButton.setDisable(true);

        saveButton.setDisable(true);
        cancelButton.setDisable(true);

        configureButton.setOnAction(e -> toggleConfigureMode());
        saveButton.setOnAction(e -> saveConfig());
        cancelButton.setOnAction(e -> loadSelectedName());

        macroTypeBox.getItems().addAll(
                "None",
                "Audio",
                "Gif",
                "System"
        );

        macroTypeBox.setOnAction(e -> {updateAudioSettings();});

        audioPathBrowseButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select Audio Path");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Audio Files", "*.mp3"));

            Window window = getScene() != null ? getScene().getWindow() : null;
            File selectedFile = fileChooser.showOpenDialog(window);

            if (selectedFile != null) {
                audioPathField.setText(selectedFile.getAbsolutePath());
            }
        });

        macroTypeBox.setValue("None");
        macroTypeBox.setDisable(true);

        getChildren().addAll(
                title,
                configureButton,
                selectedButton,
                new HBox(10, new Label("Button Name"), buttonNameField),
                new HBox(10, new Label("Macro Type"), macroTypeBox),
                audioSettings,
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
        macroTypeBox.setValue(configManager.getSelectedMacroType());

        buttonNameField.setDisable(!configureMode);
        macroTypeBox.setDisable(!configureMode || !configManager.hasSelection());
        saveButton.setDisable(!configureMode);
        cancelButton.setDisable(!configureMode);

        updateAudioSettings();
    }

    private void toggleConfigureMode() {
        configureMode = !configureMode;

        if (!configureMode) {
            LaunchpadGrid.clearSelectedPad();
        }

        configureButton.setText(configureMode ? "Cancel Configuration" : "Button Configure Mode");
        buttonNameField.setEditable(configureMode);
        buttonNameField.setDisable(!configureMode || !configManager.hasSelection());
        macroTypeBox.setDisable(!configureMode || !configManager.hasSelection());
        saveButton.setDisable(!configureMode || !configManager.hasSelection());
        cancelButton.setDisable(!configureMode || !configManager.hasSelection());

        updateAudioSettings();

        System.out.println("Configure Mode: " + configureMode);
        System.out.println("Editable: " + buttonNameField.isEditable());
    }

    private void saveConfig() {
        if (!configureMode || !configManager.hasSelection()) {
            return;
        }

        String macroType = macroTypeBox.getValue();

        if (macroType == null) {
            return;
        }

        int page = configManager.getSelectedPage();
        int row = configManager.getSelectedRow();
        int col = configManager.getSelectedCol();

        Macro macro;

        switch (macroType) {
            case "Audio" -> {
                String path = audioPathField.getText().trim();

                if (path.isEmpty() || !new File(path).isFile()) {
                    System.out.println("Audio Path is not valid");
                    return;
                }

                macro = new AudioMacro(path);
            }
            case "Gif" -> {
                macro = new GifMacro("");
            }
            case "System", "None" -> macro = null;
            default -> {
                return;
            }
        }

        configManager.saveSelectedName(buttonNameField.getText().trim());
        configManager.saveSelectedMacroType(macroType);

        macroManager.setMacro(page, row, col, macro);

        loadSelectedName();

        System.out.println("Config saved: " + macroType);
    }

    private void loadSelectedName() {
        buttonNameField.setText(configManager.getSelectedName());
    }

    private void updateAudioSettings() {
        boolean showAudioSettings =
                configManager.hasSelection()
                        && "Audio".equals(macroTypeBox.getValue());

        audioSettings.setVisible(showAudioSettings);
        audioSettings.setManaged(showAudioSettings);

        boolean editable = showAudioSettings && configureMode;

        audioPathField.setDisable(!editable);
        audioPathBrowseButton.setDisable(!editable);
    }
}