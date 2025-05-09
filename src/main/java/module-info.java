module org.example.miniproyecto_3 {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.miniproyecto_3 to javafx.fxml;
    exports org.example.miniproyecto_3;
}