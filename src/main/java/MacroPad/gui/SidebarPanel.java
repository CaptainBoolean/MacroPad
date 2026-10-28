
package MacroPad.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.fontawesome5.FontAwesomeBrands;
import org.kordamp.ikonli.fontawesome5.FontAwesomeRegular;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

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
        loadConfigButton.getStyleClass().add("sidebar-button");
        loadConfigButton.setGraphic(new FontIcon(FontAwesomeRegular.FOLDER_OPEN));

        Button saveConfigButton = new Button("Save Config");
        saveConfigButton.getStyleClass().add("sidebar-button");
        saveConfigButton.setGraphic(new FontIcon(FontAwesomeSolid.SAVE));

        Button profilesButton = new Button("Profiles");
        profilesButton.getStyleClass().add("sidebar-button");
        profilesButton.setGraphic(new FontIcon(FontAwesomeSolid.BOXES));

        Button macroLibraryButton = new Button("Macro Library");
        macroLibraryButton.getStyleClass().add("sidebar-button");
        macroLibraryButton.setGraphic(new FontIcon(FontAwesomeSolid.BOOK));

        Region settingButtonSpacer = new Region();
        VBox.setVgrow(settingButtonSpacer, Priority.ALWAYS);

        Button settingsButton = new Button("Settings");
        settingsButton.getStyleClass().add("sidebar-button");
        settingsButton.setGraphic(new FontIcon(FontAwesomeSolid.COG));

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
                profilesButton,
                macroLibraryButton,
                settingButtonSpacer,
                new Separator(),
                settingsButton
        );
    }
}
