module group.randomwalk {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens group.randomwalk to javafx.fxml;
    exports group.randomwalk;
}