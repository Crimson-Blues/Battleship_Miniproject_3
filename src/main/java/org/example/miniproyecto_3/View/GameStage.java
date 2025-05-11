package org.example.miniproyecto_3.View;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameStage extends Stage {
    public GameStage() throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/miniproyecto_3/HomeView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        setTitle("Batalla Naval");
        setResizable(true);
        setScene(scene);
        show();
    }
}
