package MacroPad.macro.impl;

import MacroPad.macro.Macro;

public class AudioMacro implements Macro {
    private final String filePath;

    public AudioMacro(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void execute() {
        System.out.println("Audio Macro executing: " + filePath);
    }
}
