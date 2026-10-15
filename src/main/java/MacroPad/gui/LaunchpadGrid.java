package MacroPad.gui;

import MacroPad.macro.MacroManager;
import MacroPad.midi.PageManager;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class LaunchpadGrid extends GridPane {
    private static Button selectedPad;

    private static final String[] TOP_BUTTON_NAMES = {
            "Vol +", "Vol -", "Mute", "Play/Pause", "???", "???", "???", "Capture", "Quit"
    };

    public LaunchpadGrid(MacroManager macroManager, PageManager pageManager, ButtonSettingsPanel settingsPanel) {
        setAlignment(Pos.CENTER);
        setHgap(9);
        setVgap(9);

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {

                final int r = row;
                final int c = col;

                Button button = new Button();
                button.setPrefSize(82, 75);
                button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

                if (row == 0) {
                    button.setText(TOP_BUTTON_NAMES[col]);
                    button.setOnAction(e -> macroManager.handleTopButtonMacros(c));

                } else if (col == 8) {
                    button.setText(String.valueOf(row));
                    button.setOnAction(e -> pageManager.changePage(r - 1));

                } else {
                    button.setText("");

                    button.setOnAction(e -> {
                        if (settingsPanel.isConfigureMode()) {
                            if (selectedPad != null) {
                                selectedPad.getStyleClass().remove("selected-pad");
                            }

                            selectedPad = button;
                            selectedPad.getStyleClass().add("selected-pad");

                            settingsPanel.selectButton(pageManager.getCurrentPage(), r - 1, c);

                        } else {
                            macroManager.executeMacro(pageManager.getCurrentPage(), r - 1, c);
                            settingsPanel.selectButton(pageManager.getCurrentPage(), r - 1, c);
                        }
                    });
                }

                if (row == 0 || col == 8) {
                    button.getStyleClass().add("edge-pad");
                } else {
                    button.getStyleClass().add("macro-pad");
                }

                add(button, col, row);
            }
        }
    }

    public static void clearSelectedPad() {
        if (selectedPad != null) {
            selectedPad.getStyleClass().remove("selected-pad");
            selectedPad = null;
        }
    }
}