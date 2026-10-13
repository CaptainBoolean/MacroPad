
package MacroPad.midi;

import MacroPad.macro.MacroManager;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Transmitter;

public class DeviceManager {

    public static void start(MacroManager macroManager, PageManager pageManager) {

        MidiDevice launchpad = findLaunchpad();

        if (launchpad == null) {
            System.out.println("Launchpad MIDI input not found.");
            return;
        }

        try {
            launchpad.open();

            DeviceReceiver receiver = new DeviceReceiver(pageManager, macroManager);

            Transmitter transmitter = launchpad.getTransmitter();
            transmitter.setReceiver(receiver);

            System.out.println("Connected: " + launchpad.getDeviceInfo().getName());
            System.out.println("Setup complete. Press a button.");

            Thread.sleep(Long.MAX_VALUE);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static MidiDevice findLaunchpad() {
        for (MidiDevice.Info info : MidiSystem.getMidiDeviceInfo()) {
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
