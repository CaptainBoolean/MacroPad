
package MacroPad.gui;

import MacroPad.macro.Macro;
import MacroPad.macro.MacroManager;
import MacroPad.macro.impl.AudioMacro;
import MacroPad.macro.impl.GifMacro;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;

public class ButtonSettingsPanel extends VBox {

    private final MacroManager macroManager;
    private final ButtonConfigManager configManager;

    private boolean configureMode = false;

    private final Button configureButton = new Button("Button Configure Mode");
    private final Label selectedButtonLabel = new Label("No Button selected");
    private final TextField buttonNameField = new TextField();
    private final Button saveButton = new Button("Save");
    private final Button cancelButton = new Button("Cancel");
    private final ComboBox<String> macroTypeBox = new ComboBox<>();
    private final TextField audioPathField = new TextField();
    private final Button audioPathBrowseButton = new Button("Browse...");

    private final HBox audioSettings =
            new HBox(10, audioPathField, audioPathBrowseButton);

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

        selectedButtonLabel.setStyle("-fx-text-fill: white;");

        buttonNameField.setPromptText("Button Name");
        buttonNameField.setEditable(false);
        buttonNameField.setDisable(true);

        macroTypeBox.getItems().addAll("None", "Audio", "Gif", "System");
        macroTypeBox.setValue("None");
        macroTypeBox.setDisable(true);

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
        macroTypeBox.setOnAction(e -> updateAudioSettings());

        audioPathBrowseButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select Audio Path");
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Audio Files", "*.mp3")
            );

            Window window = getScene() != null
                    ? getScene().getWindow()
                    : null;

            File selectedFile = fileChooser.showOpenDialog(window);

            if (selectedFile != null) {
                audioPathField.setText(selectedFile.getAbsolutePath());
            }
        });

        GridPane form = new GridPane();
        form.setHgap(12);
        form.setVgap(14);
        form.setMaxWidth(Double.MAX_VALUE);

        ColumnConstraints labelColumn = new ColumnConstraints();
        labelColumn.setMinWidth(95);
        labelColumn.setPrefWidth(95);

        ColumnConstraints inputColumn = new ColumnConstraints();
        inputColumn.setHgrow(Priority.ALWAYS);

        form.getColumnConstraints().addAll(labelColumn, inputColumn);

        Label nameLabel = new Label("Button Name");
        Label macroLabel = new Label("Macro Type");
        Label audioLabel = new Label("Audio File");

        form.add(nameLabel, 0, 0);
        form.add(buttonNameField, 1, 0);

        form.add(macroLabel, 0, 1);
        form.add(macroTypeBox, 1, 1);

        form.add(audioLabel, 0, 2);
        form.add(audioSettings, 1, 2);

        buttonNameField.setMaxWidth(Double.MAX_VALUE);
        macroTypeBox.setMaxWidth(Double.MAX_VALUE);
        audioSettings.setMaxWidth(Double.MAX_VALUE);
        audioPathField.setMaxWidth(Double.MAX_VALUE);

        HBox.setHgrow(audioPathField, Priority.ALWAYS);
        GridPane.setHgrow(audioSettings, Priority.ALWAYS);

        audioLabel.visibleProperty().bind(audioSettings.visibleProperty());
        audioLabel.managedProperty().bind(audioSettings.managedProperty());

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        HBox actionButtons = new HBox(10, saveButton, cancelButton);
        actionButtons.setAlignment(Pos.CENTER_RIGHT);

        getChildren().addAll(
                title,
                new Separator(),
                configureButton,
                selectedButtonLabel,
                form,
                spacer,
                actionButtons
        );
    }

    public boolean isConfigureMode() {
        return configureMode;
    }

    public void selectButton(int page, int row, int col) {
        configManager.selectButton(page, row, col);

        selectedButtonLabel.setText(
                "Page: " + (page + 1)
                        + "  Row: " + (row + 1)
                        + "  Col: " + (col + 1)
        );

        loadSelectedName();
        macroTypeBox.setValue(configManager.getSelectedMacroType());

        updateControlStates();
        updateAudioSettings();
    }

    private void toggleConfigureMode() {
        configureMode = !configureMode;

        if (!configureMode) {
            LaunchpadGrid.clearSelectedPad();
        }

        configureButton.setText(
                configureMode
                        ? "Cancel Configuration"
                        : "Button Configure Mode"
        );

        updateControlStates();
        updateAudioSettings();
    }

    private void updateControlStates() {
        boolean hasSelection = configManager.hasSelection();
        boolean canEdit = configureMode && hasSelection;

        buttonNameField.setEditable(canEdit);
        buttonNameField.setDisable(!canEdit);
        macroTypeBox.setDisable(!canEdit);
        saveButton.setDisable(!canEdit);
        cancelButton.setDisable(!canEdit);
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
            case "Gif" -> macro = new GifMacro("");
            case "System", "None" -> macro = null;
            default -> {
                return;
            }
        }

        configManager.saveSelectedName(buttonNameField.getText().trim());
        configManager.saveSelectedMacroType(macroType);
        macroManager.setMacro(page, row, col, macro);

        loadSelectedName();
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

        boolean canEditAudio = showAudioSettings && configureMode
                && configManager.hasSelection();

        audioPathField.setDisable(!canEditAudio);
        audioPathBrowseButton.setDisable(!canEditAudio);
    }
}
