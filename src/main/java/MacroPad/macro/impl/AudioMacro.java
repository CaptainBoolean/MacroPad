package MacroPad.macro.impl;

import MacroPad.macro.Macro;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class AudioMacro implements Macro {
    private final String filePath;

    public AudioMacro(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void execute() {
        new Thread(() -> {
            try {
                InputStream inputStream;
                File file = new File(filePath);

                if (file.isAbsolute()) {
                    inputStream = new FileInputStream(file);
                } else {
                    inputStream = getClass().getResourceAsStream("/sounds/" + filePath);
                }

                if (inputStream == null) {
                    System.out.println("File not found: " + filePath);
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
