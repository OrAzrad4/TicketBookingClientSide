module com.hit.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    opens com.hit.client to javafx.fxml;
    opens com.hit.client.model to com.google.gson;
    exports com.hit.client;
    exports com.hit.client.model;
}