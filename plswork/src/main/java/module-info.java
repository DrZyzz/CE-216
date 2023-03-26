module com.example.plswork {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.plswork to javafx.fxml;
    exports com.example.plswork;
}