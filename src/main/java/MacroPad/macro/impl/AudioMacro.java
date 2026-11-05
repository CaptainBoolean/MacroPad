package MacroPad.macro.impl;

import MacroPad.macro.Macro;
import javazoom.jl.player.Player;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AudioMacro implements Macro {
    private final String filePath;
    private final boolean allowOverlap;
    private final List<Player> activePlayers =  new ArrayList<>();
    private Player currentPlayer;
    private final Object lock = new Object();

    public AudioMacro(String filePath, boolean allowOverlap) {
        this.filePath = filePath;
        this.allowOverlap = allowOverlap;
    }

    @Override
    public void execute() {
        synchronized (lock) {
            if (!allowOverlap) {
                stopCurrentPlayback();
            }

            try {
                InputStream inputStream;
                File file = new File(filePath);

                if (file.isAbsolute()){
                    inputStream = new FileInputStream(file);
                } else {
                    inputStream = getClass().getResourceAsStream("/sounds/" + filePath);
                }

                if (inputStream == null) {
                    System.out.println("File not found: " + filePath);
                    return;
                }

                Player player = new Player(inputStream);
                if (allowOverlap) {
                    activePlayers.add(player);
                } else {
                    currentPlayer = player;
                }

                Thread playbackThread = new Thread(() -> {
                    try{
                        player.play();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        player.close();

                        synchronized (lock) {
                            activePlayers.remove(player);

                            if (currentPlayer == player){
                                currentPlayer = null;
                            }
                        }
                    }
                });

                playbackThread.setName("AudioMacro-Playback");
                playbackThread.setDaemon(true);
                playbackThread.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        synchronized (lock) {
            stopCurrentPlayback();
        }
    }

    private void stopCurrentPlayback() {
        if (currentPlayer != null) {
            currentPlayer.close();
            currentPlayer = null;
        }
    }

    public String getFilePath() {
        return filePath;
    }

    public boolean isAllowOverlap() {
        return allowOverlap;
    }
}
