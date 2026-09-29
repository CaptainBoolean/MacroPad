package MacroPad.macro;

public class MacroManager {
    private final Macro[][][] macros = new Macro[8][8][8];

    public void setMacro(int page, int row, int col, Macro macro) {
        macros[page][row][col] = macro;
    }

    public void executeMacro(int page, int row, int col) {
        Macro macro = macros[page][row][col];
        System.out.println("Executing Macro: " + ", Page: " + page + ", Row: " + row + ", Col: " + col);

        if (macro == null) {
            return;
        }

        macro.execute();
    }
}
