package MacroPad;

import MacroPad.midi.DeviceManager;

import javax.sound.midi.MidiDevice;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        System.out.println("Searching for Launchpad...");
        MidiDevice launchpad = DeviceManager.findLaunchpad();

        if (launchpad != null) {
            System.out.println("SUCCESS! Launchpad found: " + launchpad.getDeviceInfo().getName());
        } else {
            System.out.println("ERROR: Launchpad not found!");
        }
    }
}