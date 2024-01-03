module com.example.movietickers_tm {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.movietickers_tm to javafx.fxml;
    exports com.example.movietickers_tm;
}