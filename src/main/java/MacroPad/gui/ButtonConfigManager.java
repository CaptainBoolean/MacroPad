package MacroPad.gui;

public class ButtonConfigManager {
    private final String[][][] buttonNames = new String[8][8][8];
    private final String[][][] buttonMacroTypes = new String[8][8][8];
    private int selectedPage = -1;
    private int selectedRow = -1;
    private int selectedCol = -1;

    public void selectButton(int page, int row, int col) {
        selectedPage = page;
        selectedRow = row;
        selectedCol = col;
    }

    public boolean hasSelection() {
        return selectedPage != -1 && selectedRow != -1 && selectedCol != -1;
    }

    public String getSelectedName() {
        if (!hasSelection()) {
            return "";
        }

        String name = buttonNames[selectedPage][selectedRow][selectedCol];
        return name == null ? "" : name;
    }

    public void saveSelectedName(String name) {
        if (!hasSelection()) {
            return;
        }

        buttonNames[selectedPage][selectedRow][selectedCol] = name;
    }

    public String getSelectedMacroType() {
        if (!hasSelection()){
            return "None";
        }

        String type = buttonMacroTypes[selectedPage][selectedRow][selectedCol];
        return type == null ? "None" : type;
    }

    public void saveSelectedMacroType(String type) {
        if (!hasSelection()){
            return;
        }

        buttonMacroTypes[selectedPage][selectedRow][selectedCol] = type;
    }
}
