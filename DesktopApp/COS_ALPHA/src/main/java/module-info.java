module com.l3xxd.cos_alpha {
    // JavaFX
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.swing;

    // 3rd-party UI
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    // Ikonli
    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.fontawesome5;
    requires org.kordamp.ikonli.antdesignicons;
    requires org.kordamp.ikonli.coreui;
    requires org.kordamp.ikonli.bootstrapicons;

    // JDBC & utilidades
    requires java.sql;
    requires java.logging;
    requires java.desktop;

    // ---- Aperturas para FXML/reflexión ----
    // Controladores y vistas (FXML necesita "opens")
    opens com.l3xxd.cos_alpha to javafx.fxml;
    opens com.l3xxd.cos_alpha.controllers to javafx.fxml;
    opens com.l3xxd.cos_alpha.controllers.login to javafx.fxml;
    opens com.l3xxd.cos_alpha.controllers.dashboard to javafx.fxml;
    opens com.l3xxd.cos_alpha.controllers.layout to javafx.fxml;
    opens com.l3xxd.cos_alpha.controllers.dashboard.empleados to javafx.fxml;
    opens com.l3xxd.cos_alpha.controllers.dashboard.pedidos to javafx.fxml;
    opens com.l3xxd.cos_alpha.controllers.dashboard.ventas to javafx.fxml;
    opens com.l3xxd.cos_alpha.controllers.dashboard.inventario to javafx.fxml;

    // Modelos con propiedades usadas por TableView/Bindings (javafx.base)
    opens com.l3xxd.cos_alpha.models to javafx.base;

    // (Normalmente no necesitas exports; si otro módulo usa tus APIs públicas, entonces sí)
    exports com.l3xxd.cos_alpha;

}
