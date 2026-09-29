package MacroPad.midi;

import MacroPad.macro.MacroManager;
import MacroPad.macro.impl.AudioMacro;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Transmitter;

public class DeviceManager {

    public static void start() {

        MidiDevice launchpad = findLaunchpad();

        if (launchpad == null) {
            System.out.println("Launchpad not found.");
            return;
        }

        try {
            launchpad.open();

            System.out.println("Launchpad connected: " + launchpad.getDeviceInfo().getName());

            PageManager pageManager = new PageManager();
            MacroManager macroManager = new MacroManager();

            AudioMacro audioMacro = new AudioMacro("airhorn.mp3");
            macroManager.setMacro(0,0,0,audioMacro);

            Transmitter transmitter = launchpad.getTransmitter();
            transmitter.setReceiver(new DeviceReceiver(pageManager,  macroManager));

            System.out.println("Setup complete. Press a Button");

            Thread.sleep(Long.MAX_VALUE);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static MidiDevice findLaunchpad() {

        MidiDevice.Info[] devices = MidiSystem.getMidiDeviceInfo();

        for (MidiDevice.Info info : devices) {
            if (!info.getName().equals("MIDIIN2 (LPX MIDI)")) {
                continue;
            }

            try {

                MidiDevice device = MidiSystem.getMidiDevice(info);

                if (device.getMaxTransmitters() != 0) {
                    return device;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}