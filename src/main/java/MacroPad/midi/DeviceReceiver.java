package MacroPad.midi;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;

public class DeviceReceiver implements Receiver {
    private final PageManager pageManager;

    private static final int[][] BUTTON_NOTES = {
            {64, 65, 66, 67, 96, 97, 98, 99},
            {60, 61, 62, 63, 92, 93, 94, 95},
            {56, 57, 58, 59, 88, 89, 90, 91},
            {52, 53, 54, 55, 84, 85, 86, 87},
            {48, 49, 50, 51, 80, 81, 82, 83},
            {44, 45, 46, 47, 76, 77, 78, 79},
            {40, 41, 42, 43, 72, 73, 74, 75},
            {36, 37, 38, 39, 68, 69, 70, 71}
    };

    public DeviceReceiver(PageManager pageManager) {
        this.pageManager = pageManager;
    }

    @Override
    public void send(MidiMessage message, long timeStamp) {

        if (!(message instanceof ShortMessage midi)) {
            return;
        }

        if (midi.getCommand() != ShortMessage.NOTE_ON) {
            return;
        }

        if(midi.getData2() == 0) {
            return;
        }

        int note = midi.getData1();
        int row = -1;
        int col = -1;

        if (note >= 100 && note <= 107) {
            int page = note - 100;
            pageManager.changePage(page);
            return;
        }

        for (int i = 0; i < BUTTON_NOTES.length; i++) {
            for (int j = 0; j < BUTTON_NOTES[i].length; j++) {
                if (BUTTON_NOTES[i][j] == note) {
                    row = i;
                    col = j;
                    break;
                }
            }

            if (row != -1) {
                break;
            }
        }

        if (row == -1) {
            return;
        }

        System.out.println("Button pressed: " + "Page: " + pageManager.getCurrentPage() + ", Note: " + note + ", Row: " + row + ", Col: " + col);
    }

    @Override
    public void close() {
    }
}