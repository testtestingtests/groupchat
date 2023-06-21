module Chat {
    requires javafx.fxml;
    requires javafx.controls;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens chat to javafx.fxml;
    exports chat;
}