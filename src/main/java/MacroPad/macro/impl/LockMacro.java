package MacroPad.macro.impl;

import MacroPad.macro.Macro;

import java.awt.*;
import java.awt.event.KeyEvent;

public class LockMacro implements Macro {

    @Override
    public void execute() {
        try {
            new ProcessBuilder("rundll32.exe", "user32.dll,LockWorkStation").start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
