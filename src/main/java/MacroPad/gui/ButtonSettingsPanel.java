
package MacroPad.gui;

import MacroPad.macro.Macro;
import MacroPad.macro.MacroManager;
import MacroPad.macro.Overlay.CounterManager;
import MacroPad.macro.Overlay.CounterOverlay;
import MacroPad.macro.impl.AudioMacro;
import MacroPad.macro.impl.CounterMacro;
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
    private final CounterManager counterManager;

    private boolean configureMode = false;

    private final Button configureButton = new Button("Button Configure Mode");
    private final Label selectedButtonLabel = new Label("No Button selected");
    private final TextField buttonNameField = new TextField();
    private final ComboBox<String> macroTypeBox = new ComboBox<>();
    private final Button saveButton = new Button("Save");
    private final Button cancelButton = new Button("Cancel");

    private final TextField audioPathField = new TextField();
    private final Button audioPathBrowseButton = new Button("Browse...");
    private final Label allowOverlapLabel = new Label("Overlapping Playback");
    private final CheckBox allowOverlapCheckBox = new CheckBox();
    private final HBox audioFileSettings = new HBox(10, audioPathField, audioPathBrowseButton);

    private final Label counterXPositionLabel =  new Label("Position X");
    private final Label counterYPositionLabel =  new Label("Position Y");
    private final TextField counterXPositionField = new TextField("100");
    private final TextField counterYPositionField = new TextField("100");

    public ButtonSettingsPanel(MacroManager macroManager, ButtonConfigManager configManager, CounterManager counterManager) {

        this.macroManager = macroManager;
        this.configManager = configManager;
        this.counterManager = counterManager;

        setSpacing(10);
        setPadding(new Insets(20, 10, 20, 10));
        getStyleClass().add("settings-panel");

        Label title = new Label("Button Settings");
        title.setMaxWidth(Double.MAX_VALUE);
        title.setAlignment(Pos.CENTER);
        title.setStyle("-fx-text-fill: white; -fx-font-size: 18px;");

        selectedButtonLabel.setStyle("-fx-text-fill: white;");

        /* Global Button Settings */
        buttonNameField.setPromptText("Button Name");
        buttonNameField.setEditable(false);
        buttonNameField.setDisable(true);

        macroTypeBox.getItems().addAll("None", "Audio", "Counter", "Gif", "System");
        macroTypeBox.setValue("None");
        macroTypeBox.setDisable(true);

        /* Audio Button Settings */
        audioFileSettings.setVisible(false);
        audioFileSettings.setManaged(false);

        audioPathField.setPromptText("Select Audio Path");
        audioPathField.setEditable(false);
        audioPathField.setDisable(true);
        audioPathBrowseButton.setDisable(true);

        allowOverlapLabel.setVisible(false);
        allowOverlapCheckBox.setVisible(false);
        allowOverlapCheckBox.setManaged(false);

        /* Counter Button Settings */

        counterXPositionLabel.setVisible(false);
        counterXPositionLabel.setManaged(false);
        counterXPositionField.setVisible(false);
        counterXPositionField.setManaged(false);

        counterYPositionLabel.setVisible(false);
        counterYPositionLabel.setManaged(false);
        counterYPositionField.setVisible(false);
        counterYPositionField.setManaged(false);

        /* Save/Cancel Button Settings */
        saveButton.setDisable(true);
        cancelButton.setDisable(true);

        configureButton.setOnAction(e -> toggleConfigureMode());
        saveButton.setOnAction(e -> saveConfig());
        cancelButton.setOnAction(e -> loadSelectedName());
        macroTypeBox.setOnAction(e -> {
            updateAudioSettings();
            updateCounterSettings();
        });

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
        form.add(audioFileSettings, 1, 2);
        form.add(allowOverlapLabel, 0, 3);
        form.add(allowOverlapCheckBox, 1, 3);

        form.add(counterXPositionLabel, 0, 2);
        form.add(counterXPositionField, 1, 2);

        form.add(counterYPositionLabel, 0, 3);
        form.add(counterYPositionField, 1, 3);

        buttonNameField.setMaxWidth(Double.MAX_VALUE);
        macroTypeBox.setMaxWidth(Double.MAX_VALUE);
        audioFileSettings.setMaxWidth(Double.MAX_VALUE);
        audioPathField.setMaxWidth(Double.MAX_VALUE);

        HBox.setHgrow(audioPathField, Priority.ALWAYS);
        GridPane.setHgrow(audioFileSettings, Priority.ALWAYS);

        audioLabel.visibleProperty().bind(audioFileSettings.visibleProperty());
        audioLabel.managedProperty().bind(audioFileSettings.managedProperty());

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

        selectedButtonLabel.setText("Page: " + (page + 1) + "  Row: " + (row + 1) + "  Col: " + (col + 1));

        loadSelectedName();
        macroTypeBox.setValue(configManager.getSelectedMacroType());
        loadSelectedAudioSettings();

        updateControlStates();
        updateAudioSettings();
        updateCounterSettings();
    }

    private void toggleConfigureMode() {

        if (configureMode) {
            saveConfig();
        }

        configureMode = !configureMode;

        if (!configureMode) {
            LaunchpadGrid.clearSelectedPad();
        }

        configureButton.setText(configureMode ? "Leave Configure Mode" : "Button Configure Mode");

        updateControlStates();
        updateAudioSettings();
        updateCounterSettings();
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

        Macro macro = null;

        switch (macroType) {
            case "Audio" -> {
                String path = audioPathField.getText().trim();

                if (!path.isEmpty() && new File(path).isFile()) {
                    boolean allowOverlap = allowOverlapCheckBox.isSelected();
                    macro = new AudioMacro(path, allowOverlap);
                } else {
                    macro = null;
                }
            }
            case "Counter" -> {
                String counterName = buttonNameField.getText().trim();

                if (counterName.isEmpty()) {
                    return;
                }

                double x = Double.parseDouble(counterXPositionField.getText());
                double y = Double.parseDouble(counterYPositionField.getText());

                CounterOverlay counter = counterManager.getOrCreateCounter(counterName);
                counter.setPosition(x, y);
                macro = new CounterMacro(counterManager, counterName, CounterMacro.Action.TOGGLE);

            }
            case "Gif" -> macro = new GifMacro("");
            case "System", "None" -> macro = null;
            default -> {
                return;
            }
        }

        configManager.saveSelectedName(buttonNameField.getText().trim());
        configManager.saveSelectedMacroType(macroType);
        configManager.saveSelectedAllowOverlap(allowOverlapCheckBox.isSelected());

        macroManager.setMacro(page, row, col, macro);

        loadSelectedName();
    }

    private void loadSelectedName() {
        buttonNameField.setText(configManager.getSelectedName());
    }

    private void loadSelectedAudioSettings() {
        Macro macro = macroManager.getMacro(
                configManager.getSelectedPage(),
                configManager.getSelectedRow(),
                configManager.getSelectedCol()
        );

        if (macro instanceof AudioMacro audioMacro) {
            audioPathField.setText(audioMacro.getFilePath());
        } else {
            audioPathField.clear();
        }

        allowOverlapCheckBox.setSelected(configManager.getSelectedAllowOverlap());
    }

    private void updateAudioSettings() {
        boolean showAudioSettings = configManager.hasSelection() && "Audio".equals(macroTypeBox.getValue());

        audioFileSettings.setVisible(showAudioSettings);
        audioFileSettings.setManaged(showAudioSettings);

        allowOverlapLabel.setVisible(showAudioSettings);
        allowOverlapLabel.setManaged(showAudioSettings);
        allowOverlapCheckBox.setVisible(showAudioSettings);
        allowOverlapCheckBox.setManaged(showAudioSettings);

        boolean canEditAudio = showAudioSettings && configureMode && configManager.hasSelection();

        audioPathField.setDisable(!canEditAudio);
        audioPathBrowseButton.setDisable(!canEditAudio);
        allowOverlapCheckBox.setDisable(!canEditAudio);
    }

    private void updateCounterSettings() {
        boolean showCounter = configManager.hasSelection() && "Counter".equals(macroTypeBox.getValue());

        counterXPositionLabel.setVisible(showCounter);
        counterXPositionLabel.setManaged(showCounter);

        counterXPositionField.setVisible(showCounter);
        counterXPositionField.setManaged(showCounter);

        counterYPositionLabel.setVisible(showCounter);
        counterYPositionLabel.setManaged(showCounter);

        counterYPositionField.setVisible(showCounter);
        counterYPositionField.setManaged(showCounter);

        boolean canEditCounter = showCounter && configureMode && configManager.hasSelection();

        counterXPositionField.setDisable(!canEditCounter);
        counterYPositionField.setDisable(!canEditCounter);
    }
}
