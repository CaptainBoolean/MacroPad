package MacroPad.macro;

import MacroPad.macro.impl.*;

public class MacroManager {
    private final Macro[][][] macros = new Macro[8][8][8];
    private final Macro[] topMacros = new Macro[9];

    public MacroManager() {
        topMacros[0] = new VolumeMacro(VolumeMacro.Action.UP);
        topMacros[1] = new VolumeMacro(VolumeMacro.Action.DOWN);
        topMacros[2] = new VolumeMacro(VolumeMacro.Action.MUTE);
        topMacros[4] = new TaskManagerMacro();
        topMacros[5] = new SettingsMacro();
        topMacros[6] = new LockMacro();
        topMacros[7] = new ScreenshotMacro();
        topMacros[8] = new QuitMacro();
    }

    public void setMacro(int page, int row, int col, Macro macro) {
        macros[page][row][col] = macro;
    }

    public Macro getMacro(int page, int row, int col) {
        return macros[page][row][col];
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
            case 2:
                topMacros[2].execute();
                break;
            case 4:
                topMacros[4].execute();
                break;
            case 5:
                topMacros[5].execute();
                break;
            case 6:
                topMacros[6].execute();
                break;
            case 7:
                topMacros[7].execute();
                break;
            case 8:
                topMacros[8].execute();
                break;
            default:
                System.out.println("Kein Makro für Spalte " + col + " definiert");
                break;
        }
    }
}
