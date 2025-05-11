module org.example.miniproyecto_3 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop;


    opens org.example.miniproyecto_3 to javafx.fxml;
    exports org.example.miniproyecto_3;
    exports org.example.miniproyecto_3.Controller;
    opens org.example.miniproyecto_3.Controller to javafx.fxml;
}