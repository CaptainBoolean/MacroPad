
package MacroPad.gui;

public class ButtonConfigManager {

    private final String[][][] buttonNames = new String[8][8][8];
    private final String[][][] buttonMacroTypes = new String[8][8][8];
    private final boolean[][][] allowOverlapSettings = new boolean[8][8][8];

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
        if (hasSelection()) {
            buttonNames[selectedPage][selectedRow][selectedCol] = name;
        }
    }

    public String getSelectedMacroType() {
        if (!hasSelection()) {
            return "None";
        }

        String type = buttonMacroTypes[selectedPage][selectedRow][selectedCol];
        return type == null ? "None" : type;
    }

    public void saveSelectedMacroType(String type) {
        if (hasSelection()) {
            buttonMacroTypes[selectedPage][selectedRow][selectedCol] = type;
        }
    }

    public boolean getSelectedAllowOverlap() {
        if (!hasSelection()) {
            return false;
        }

        return allowOverlapSettings[selectedPage][selectedRow][selectedCol];
    }

    public void saveSelectedAllowOverlap(boolean allowOverlap) {
        if (hasSelection()) {
            allowOverlapSettings[selectedPage][selectedRow][selectedCol] = allowOverlap;
        }
    }

    public int getSelectedPage() {
        return selectedPage;
    }

    public int getSelectedRow() {
        return selectedRow;
    }

    public int getSelectedCol() {
        return selectedCol;
    }
}
