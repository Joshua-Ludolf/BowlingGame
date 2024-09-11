module com.example.bgame {
    requires javafx.controls;
    requires javafx.fxml;
    requires junit;


    opens com.example.bgame to javafx.fxml;
    exports com.example.bgame;
}