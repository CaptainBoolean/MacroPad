package MacroPad.macro.impl;

import MacroPad.macro.Macro;

public class SettingsMacro implements Macro {

    @Override
    public void execute() {
        try {
            new ProcessBuilder( "cmd", "/c", "start", "", "ms-settings:").start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
