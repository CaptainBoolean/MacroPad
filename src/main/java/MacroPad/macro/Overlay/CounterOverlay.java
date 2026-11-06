
package MacroPad.macro.Overlay;

import MacroPad.macro.Overlay.CustomOverlay;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class CounterOverlay {

    private final CustomOverlay overlay;
    private final Label counterLabel;
    private int count = 0;

    public CounterOverlay(String name) {

        Label nameLabel = new Label(name);
        nameLabel.setStyle(
                "-fx-text-fill: white;" +
                        "-fx-font-size: 20px;" +
                        "-fx-font-weight: bold;"
        );

        counterLabel = new Label("0");
        counterLabel.setStyle(
                "-fx-text-fill: white;" +
                        "-fx-font-size: 40px;" +
                        "-fx-font-weight: bold;"
        );

        VBox content = new VBox(5, nameLabel, counterLabel);
        content.setAlignment(Pos.CENTER);
        content.setPrefSize(220, 110);
        content.setStyle(
                "-fx-background-color: rgba(0,0,0,0.75);" +
                        "-fx-background-radius: 12px;" +
                        "-fx-padding: 10px;"
        );

        overlay = new CustomOverlay(content, 220, 110);
    }

    public void show() {
        overlay.show();
    }

    public void hide() {
        overlay.hide();
    }

    public void toggle() {
        overlay.toggle();
    }

    public void increment() {
        count++;
        updateCounter();
    }

    public void decrement() {
        if (count > 0) {
            count--;
        }
        updateCounter();
    }

    public void reset() {
        count = 0;
        updateCounter();
    }

    private void updateCounter() {
        javafx.application.Platform.runLater(() ->
                counterLabel.setText(String.valueOf(count))
        );
    }

    public int getCount() {
        return count;
    }
}
