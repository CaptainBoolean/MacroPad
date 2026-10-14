package MacroPad.macro.impl;

import MacroPad.macro.Macro;
import javafx.application.Platform;

public class QuitMacro implements Macro {
    @Override
    public void execute() {
        System.exit(0);
    }
}
