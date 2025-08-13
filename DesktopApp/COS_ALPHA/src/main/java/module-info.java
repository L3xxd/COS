module com.l3xxd.cos_alpha {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    // Exporta Launcher para que JavaFX pueda instanciarlo
    exports com.l3xxd.cos_alpha to javafx.graphics;

    // Exporta y abre controladores usados en FXML
    exports com.l3xxd.cos_alpha.controllers.login to javafx.fxml;
    exports com.l3xxd.cos_alpha.controllers.dashboard to javafx.fxml;
    exports com.l3xxd.cos_alpha.controllers.layout to javafx.fxml;

    opens com.l3xxd.cos_alpha.controllers.login to javafx.fxml;
    opens com.l3xxd.cos_alpha.controllers.dashboard to javafx.fxml;
    opens com.l3xxd.cos_alpha.controllers.layout to javafx.fxml;

    // Abre utilidades si usas reflexión
}
