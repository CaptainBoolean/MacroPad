package MacroPad.macro.impl;

import MacroPad.macro.Macro;

import java.awt.*;
import java.awt.event.KeyEvent;

public class ScreenshotMacro implements Macro {

    @Override
    public void execute() {
        try {
            Robot robot = new Robot();

            robot.keyPress(KeyEvent.VK_WINDOWS);
            robot.keyPress(KeyEvent.VK_SHIFT);
            robot.keyPress(KeyEvent.VK_S);

            robot.keyRelease(KeyEvent.VK_WINDOWS);
            robot.keyRelease(KeyEvent.VK_SHIFT);
            robot.keyPress(KeyEvent.VK_S);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
