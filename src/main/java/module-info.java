module com.andre.dojo.javafx_contact_manager {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.xml;

    opens com.andre.dojo.javafx_contact_manager to javafx.fxml;
    exports com.andre.dojo.javafx_contact_manager;
}