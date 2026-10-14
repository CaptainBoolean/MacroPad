package MacroPad.macro;

import MacroPad.macro.impl.QuitMacro;
import MacroPad.macro.impl.VolumeMacro;

public class MacroManager {
    private final Macro[][][] macros = new Macro[8][8][8];
    private final Macro[] topMacros = new Macro[9];

    public MacroManager() {
        topMacros[0] = new VolumeMacro(VolumeMacro.Action.UP);
        topMacros[1] = new VolumeMacro(VolumeMacro.Action.DOWN);
        topMacros[8] = new QuitMacro();
    }

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

    public void handleTopButtonMacros(int col) {
        switch (col) {
            case 0:
                topMacros[0].execute();
                break;
            case 1:
                topMacros[1].execute();
                break;
            case 8:
                topMacros[8].execute();
                break;
            default:
                System.out.println("Kein Makro für Spalte " + col + " definiert");
                break;
        }
    }

    public void handleArrowController(int controller) {
        switch (controller) {
            case 91 -> handleTopButtonMacros(0);
            case 92 -> handleTopButtonMacros(1);
            default -> {

            }
        }
    }
}
