module com.example.m4wwtbam {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.m4wwtbam to javafx.fxml;
    exports com.example.m4wwtbam;
}