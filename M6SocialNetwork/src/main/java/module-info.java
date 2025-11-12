module com.example.samplesocialnetinterface {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.samplesocialnetinterface to javafx.fxml;
    exports com.example.samplesocialnetinterface;
}