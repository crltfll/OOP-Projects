module com.example.m5minimaxtree {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.m5minimaxtree to javafx.fxml;
    exports com.example.m5minimaxtree;
}