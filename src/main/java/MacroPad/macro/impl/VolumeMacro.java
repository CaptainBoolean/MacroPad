package MacroPad.macro.impl;

import MacroPad.macro.Macro;

public class VolumeMacro implements Macro {

    public enum Action {
        UP,
        DOWN,
        MUTE
    }

    private final Action action;

    public VolumeMacro(Action action) {
        this.action = action;
    }

    @Override
    public void execute() {
        try {
            int vkCode = switch (action) {
                case UP -> 0xAF; // VK_VOLUME_UP
                case DOWN -> 0xAE; // VK_VOLUME_DOWN
                case MUTE -> 0xAD; // VK_VOLUME_MUTE
            };

            String command = String.format(
                    "powershell -Command \"$wshell = New-Object -ComObject wscript.shell; $wshell.SendKeys([char]%d)\"",
                    vkCode
            );
            Runtime.getRuntime().exec(command);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}