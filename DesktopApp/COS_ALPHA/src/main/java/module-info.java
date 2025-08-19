module com.l3xxd.cos_alpha {
    // JavaFX core
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    // UI libraries
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    // Ikonli core
    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.antdesignicons;
    requires org.kordamp.ikonli.coreui;
    requires org.kordamp.ikonli.bootstrapicons;

    // JDBC & MySQL
    requires java.sql;

    // Logging
    requires java.logging;

    // Exporta Launcher para JavaFX
    exports com.l3xxd.cos_alpha to javafx.graphics;

    // Exporta y abre controladores usados en FXML
    exports com.l3xxd.cos_alpha.controllers.login to javafx.fxml;
    exports com.l3xxd.cos_alpha.controllers.dashboard to javafx.fxml;
    exports com.l3xxd.cos_alpha.controllers.layout to javafx.fxml;
    exports com.l3xxd.cos_alpha.controllers.dashboard.empleados to javafx.fxml;
    exports com.l3xxd.cos_alpha.config to javafx.graphics;

    opens com.l3xxd.cos_alpha.controllers.login to javafx.fxml;
    opens com.l3xxd.cos_alpha.controllers.dashboard to javafx.fxml;
    opens com.l3xxd.cos_alpha.controllers.layout to javafx.fxml;
    opens com.l3xxd.cos_alpha.controllers.dashboard.empleados to javafx.fxml;
    opens com.l3xxd.cos_alpha.controllers to javafx.fxml;

    // 🔥 Apertura crítica para modelos usados en TableView
    opens com.l3xxd.cos_alpha.models to javafx.base;
}

