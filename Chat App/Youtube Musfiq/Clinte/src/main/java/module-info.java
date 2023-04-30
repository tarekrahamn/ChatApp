module com.example.clinte {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.clinte to javafx.fxml;
    exports com.example.clinte;
}