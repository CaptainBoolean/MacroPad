package MacroPad.macro.impl;

import MacroPad.macro.Macro;

public class VolumeMacro implements Macro {

    public enum Action {
        UP,
        DOWN
    }

    private final Action action;

    public VolumeMacro(Action action) {
        this.action = action;
    }

    @Override
    public void execute() {
        try {
            int vkCode = (action == Action.UP) ? 0xAF : 0xAE; // 0xAF = VK_VOLUME_UP, 0xAE = VK_VOLUME_DOWN

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