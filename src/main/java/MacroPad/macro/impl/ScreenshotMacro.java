package MacroPad.macro.impl;

import MacroPad.macro.Macro;

import java.awt.*;
import java.awt.event.KeyEvent;

public class ScreenshotMacro implements Macro {

    @Override
    public void execute() {
        try {
            new ProcessBuilder("explorer.exe", "ms-screenclip:").start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
