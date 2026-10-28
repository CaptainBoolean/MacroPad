
package MacroPad.gui;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class MacroPadMenuBar extends MenuBar {

    public MacroPadMenuBar() {
        Menu fileMenu = new Menu("File");
        Menu settingsMenu = new Menu("Settings");
        Menu helpMenu = new Menu("Help");

        MenuItem saveConfig = new MenuItem("Save Configuration");
        MenuItem loadConfig = new MenuItem("Load Configuration");
        MenuItem quit = new MenuItem("Quit");

        fileMenu.getItems().addAll(
                saveConfig,
                loadConfig,
                quit
        );

        this.getMenus().addAll(
                fileMenu,
                settingsMenu,
                helpMenu
        );

        quit.setOnAction(e -> System.exit(0));
    }
}
