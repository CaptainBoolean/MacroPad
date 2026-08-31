package MacroPad;

import MacroPad.midi.DeviceManager;
import MacroPad.midi.DeviceReceiver;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Transmitter;

public class Main {
    public static void main(String[] args) {
        System.out.println("Searching for Launchpad...");
        MidiDevice launchpad = DeviceManager.findLaunchpad();

        if (launchpad == null) {
            System.err.println("Unable to find Launchpad");
            return;
        } else {
            System.out.println("Found Launchpad: " + launchpad.getDeviceInfo().getName());
        }

        try {
            launchpad.open();

            Transmitter transmitter = launchpad.getTransmitter();
            transmitter.setReceiver(new DeviceReceiver());

            Thread.sleep(Long.MAX_VALUE);
        } catch (Exception e) {
            System.err.println("Connection Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}