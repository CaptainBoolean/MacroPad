package MacroPad.midi;

import MacroPad.macro.MacroManager;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;

public class DeviceReceiver implements Receiver {
    private final PageManager pageManager;
    private final MacroManager macroManager;

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

    private static final int ARROW_UP_CONTROLLER = 91;
    private static final int ARROW_DOWN_CONTROLLER = 92;

    public DeviceReceiver(PageManager pageManager, MacroManager macroManager) {
        this.pageManager = pageManager;
        this.macroManager = macroManager;
    }

    @Override
    public void send(MidiMessage message, long timeStamp) {

        if (!(message instanceof ShortMessage midi)) {
            System.out.println("Other MIDI message: " + message.getClass().getSimpleName());
            return;
        }

        System.out.printf(
                "Command: 0x%02X | Channel: %d | Data1: %d | Data2: %d%n",
                midi.getCommand(),
                midi.getChannel(),
                midi.getData1(),
                midi.getData2()
        );

        if (midi.getCommand() == ShortMessage.CONTROL_CHANGE) {
            int controller = midi.getData1();
            int value = midi.getData2();

            if (value == 127 && (controller == ARROW_UP_CONTROLLER || controller == ARROW_DOWN_CONTROLLER)) {
                macroManager.handleArrowController(controller);
            }

            return;
        }

        if (midi.getCommand() != ShortMessage.NOTE_ON) {
            return;
        }

        if (midi.getData2() == 0) {
            return;
        }

        // Dein bisheriger Code geht hier unverändert weiter ...


        int note = midi.getData1();
        System.out.println("Note of Button is: " + note);
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

        macroManager.executeMacro(pageManager.getCurrentPage(), row, col);
    }

    @Override
    public void close() {
    }

    private static boolean isTopButton(int note) {
        return false;
    }
}