package com.l3xxd.cos_alpha.controllers.layout;

import com.l3xxd.cos_alpha.models.OperatorModel;
import com.l3xxd.cos_alpha.utils.SessionManager;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import com.l3xxd.cos_alpha.utils.DialogUtils;


public class NavbarController implements Initializable {

    @FXML private Text clockText;
    @FXML private Text dateText;
    @FXML private Text userprofileText;
    @FXML private Button logoutButton;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupClock();
        showUserProfile();
        logoutButton.setOnAction(e -> cerrarSesion());

    }

    private void setupClock() {
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("hh:mma");
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        Timeline clockTimeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            LocalDateTime now = LocalDateTime.now();
            clockText.setText(timeFormat.format(now).toLowerCase());
            dateText.setText(dateFormat.format(now));
        }));

        clockTimeline.setCycleCount(Timeline.INDEFINITE);
        clockTimeline.play();
    }

    public void showUserProfile() {
        OperatorModel operador = SessionManager.getUser();

        String saludo = "Bienvenido";
        if (operador != null) {
            saludo += ", " + operador.getFirstName();
        }

        userprofileText.setText(saludo);
        userprofileText.setOpacity(0);

        FadeTransition fadeIn = new FadeTransition(Duration.millis(600), userprofileText);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();
    }

    private void cerrarSesion() {
        boolean confirmado = DialogUtils.mostrarConfirmacion(
                "Confirmación",
                "¿Estás seguro que deseas cerrar sesión?",
                "Esta acción te llevará de vuelta al login."
        );

        if (confirmado) {
            ejecutarCierreSesion();
        } else {
            System.out.println("🔄 Cancelado por el usuario");
        }
    }

    private void ejecutarCierreSesion() {
        System.out.println("🔒 Cerrando sesión...");

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/l3xxd/cos_alpha/views/login/login.fxml"));
            Parent loginRoot = loader.load();

            // Crear nueva escena con dimensiones específicas
            Scene loginScene = new Scene(loginRoot, 1920, 1080); // ← Aquí defines ancho y alto

            // Obtener el stage actual
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            stage.setScene(loginScene);
            stage.setResizable(false);



            stage.setResizable(false);
            stage.setTitle("COS_ALPHA v 2.1");
            stage.setMaximized(true);
            stage.show();

            System.out.println("✅ Sesión cerrada, redirigido a login con dimensiones 800x600");
        } catch (Exception ex) {
            System.err.println("❌ Error al redirigir al login:");
            ex.printStackTrace();
        }
    }







}
