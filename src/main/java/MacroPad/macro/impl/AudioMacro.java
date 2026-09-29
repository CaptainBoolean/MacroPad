package MacroPad.macro.impl;

import MacroPad.macro.Macro;
import javazoom.jl.player.Player;
import java.io.InputStream;

public class AudioMacro implements Macro {
    private final String filePath;

    public AudioMacro(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void execute() {
        new Thread(() -> {
            try (InputStream inputStream = getClass().getResourceAsStream("/sounds/" + filePath)) {
                if (inputStream == null) {
                    System.out.println("Sound file " + filePath + " not found");
                    return;
                }

                Player player = new Player(inputStream);
                player.play();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
