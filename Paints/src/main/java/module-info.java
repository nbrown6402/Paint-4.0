module com.example.paints {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.swing;


    opens com.example.paints to javafx.fxml;
    exports com.example.paints;
}