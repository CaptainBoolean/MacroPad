package MacroPad;

import MacroPad.gui.MacroPadApp;
import MacroPad.macro.MacroManager;
import MacroPad.macro.impl.AudioMacro;
import MacroPad.macro.impl.GifMacro;
import MacroPad.midi.DeviceManager;
import MacroPad.midi.PageManager;
import javafx.application.Application;

public class Main {

    public static void main(String[] args) {

        MacroManager macroManager = new MacroManager();
        PageManager pageManager = new PageManager();

        macroManager.setMacro(0, 0, 0, new AudioMacro("airhorn.mp3"));
        macroManager.setMacro(0, 0, 1, new GifMacro("explosion.gif"));

        MacroPadApp.setDependencies(macroManager, pageManager);

        Thread midiThread = new Thread(() -> DeviceManager.start(macroManager, pageManager), "Launchpad-MIDI");

        midiThread.setDaemon(true);
        midiThread.start();

        Application.launch(MacroPadApp.class, args);
    }
}