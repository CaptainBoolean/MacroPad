package MacroPad.midi;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;

public class DeviceManager {

    /**
     * Searches the system for Launchpads.
     */
    public static MidiDevice findLaunchpad() {
        MidiDevice.Info[] devices = MidiSystem.getMidiDeviceInfo();

        for (MidiDevice.Info deviceInfo : devices) {
            System.out.println("Device name: " + deviceInfo.getName());
            if(deviceInfo.getName().contains("Launchpad") || deviceInfo.getName().contains("LPX")) {
                try {
                    MidiDevice device = MidiSystem.getMidiDevice(deviceInfo);

                    // Some MIDI devices are only receivers, we need the one that are transmitters
                    // 0 = device cant send data (exit/speakers)
                    // -1 = unlimited connections
                    // > 0 = fixed max amount of transmitters
                    if(device.getMaxTransmitters() != 0) {
                        return device;
                    } else {
                        System.out.println("Skipping device " + deviceInfo.getName() + "(is a Output)");
                    }
                } catch (MidiUnavailableException e) {
                    System.err.println("Device not found: " + e.getMessage());
                }
            }
        }

        return null;
    }
}
