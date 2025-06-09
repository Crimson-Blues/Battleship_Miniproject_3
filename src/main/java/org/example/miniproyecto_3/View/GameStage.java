package org.example.miniproyecto_3.View;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Represents the main game window for the application.
 * <p>
 * This class extends {@link javafx.stage.Stage Stage} and loads the initial FXML view,
 * applies CSS styling, sets window properties, and displays the stage.
 *
 * @see javafx.stage.Stage
 * @see javafx.scene.Scene
 * @see javafx.fxml.FXMLLoader
 * @see javafx.scene.image.Image
 */
public class GameStage extends Stage {
    /**
     * Constructs a new {@code GameStage}, loading the main FXML layout,
     * setting the scene, title, icon, and applying the stylesheet.
     *
     * @throws Exception if the FXML or resources cannot be loaded
     */
    public GameStage() throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("/org/example/miniproyecto_3/HomeView.fxml")
        );
        Scene scene = new Scene(fxmlLoader.load());


        scene.getStylesheets().add(
                getClass().getResource("/org/example/miniproyecto_3/css/style.css")
                        .toExternalForm()
        );

        setTitle("Batalla Naval");
        getIcons().add(new Image(getClass().getResourceAsStream("/org/example/miniproyecto_3/images/favicon.png")));
        setResizable(false);
        setScene(scene);
        show();
    }
}