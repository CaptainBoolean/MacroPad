package MacroPad.midi;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;

public class DeviceManager {

    public static MidiDevice findLaunchpad() {

        MidiDevice.Info[] devices = MidiSystem.getMidiDeviceInfo();

        for (MidiDevice.Info deviceInfo : devices) {

            String name = deviceInfo.getName();

            if (!name.equals("MIDIIN2 (LPX MIDI)")) {
                continue;
            }

            try {

                MidiDevice device = MidiSystem.getMidiDevice(deviceInfo);

                if (device.getMaxTransmitters() != 0) {
                    return device;
                }

            } catch (MidiUnavailableException e) {
                System.err.println(
                        "Could not open MIDI device: "
                                + e.getMessage()
                );
            }
        }

        return null;
    }
}