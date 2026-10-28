
package MacroPad.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;

public class SidebarPanel extends VBox {

    public SidebarPanel() {
        setSpacing(10);
        setPadding(new Insets(20, 10, 20, 10));

        getStyleClass().add("sidebar-panel");

        Label title = new Label("MacroPad");
        title.setMaxWidth(Double.MAX_VALUE);
        title.setAlignment(Pos.CENTER);
        title.setStyle("-fx-text-fill: white; -fx-font-size: 18px;");

        Button loadConfigButton = new Button("Load Config");
        Button saveConfigButton = new Button("Save Config");
        Button profilesButton = new Button("Profiles");
        Button macroLibraryButton = new Button("Macro Library");
        Button settingsButton = new Button("Settings");

        loadConfigButton.setMaxWidth(Double.MAX_VALUE);
        saveConfigButton.setMaxWidth(Double.MAX_VALUE);
        profilesButton.setMaxWidth(Double.MAX_VALUE);
        macroLibraryButton.setMaxWidth(Double.MAX_VALUE);
        settingsButton.setMaxWidth(Double.MAX_VALUE);

        getChildren().addAll(
                title,
                new Separator(),
                loadConfigButton,
                saveConfigButton,
                new Separator(),
                profilesButton,
                macroLibraryButton,
                new Separator(),
                settingsButton
        );
    }
}
