package MacroPad.macro.impl;

import MacroPad.macro.Macro;

import java.awt.*;
import java.awt.event.KeyEvent;

public class TaskManagerMacro implements Macro {

    @Override
    public void execute() {
        try {
            new ProcessBuilder("taskmgr.exe").start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
