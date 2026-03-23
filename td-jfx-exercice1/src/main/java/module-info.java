module permissions {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens vues to javafx.fxml;
    exports pnt;
}