package MacroPad.midi;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;

public class DeviceReceiver implements Receiver {

    @Override
    public void send(MidiMessage message, long timeStamp) {
        if (message instanceof ShortMessage){
            ShortMessage shortMessage = (ShortMessage)message;

            int command = shortMessage.getCommand();
            int padID = shortMessage.getData1();
            int velocity = shortMessage.getData2(); // How hard the pad is pressed

            if ((command == ShortMessage.NOTE_ON || command == ShortMessage.CONTROL_CHANGE) && velocity > 0) {
                System.out.println("Command: " + command + "padID: " + padID + "Velocity: " + velocity);
            }
        }
    }

    @Override
    public void close() {
        System.out.println("Device closed");
    }
}
