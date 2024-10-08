module org.example.csc311week9hw {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.csc311week9hw to javafx.fxml;
    exports org.example.csc311week9hw;
}