package MacroPad.midi;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;

public class DeviceReceiver implements Receiver {

    @Override
    public void send(MidiMessage message, long timeStamp) {

        if (!(message instanceof ShortMessage shortMessage)) {
            return;
        }

        int command = shortMessage.getCommand();
        int padID = shortMessage.getData1();
        int velocity = shortMessage.getData2();

        if (command == ShortMessage.NOTE_ON && velocity > 0) {

            System.out.println(
                    "Pad pressed: "
                            + padID
                            + " | Velocity: "
                            + velocity
            );
        }
    }

    @Override
    public void close() {
        System.out.println("Device closed");
    }
}