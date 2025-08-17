package com.l3xxd.cos_alpha.controllers.layout;

import com.l3xxd.cos_alpha.models.OperatorModel;
import com.l3xxd.cos_alpha.utils.DialogUtils;
import com.l3xxd.cos_alpha.utils.SessionManager;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * Controlador del navbar superior.
 * Muestra hora, fecha, nombre del operador y gestiona cierre de sesión.
 */
public class NavbarController implements Initializable {

    @FXML private Text clockText;
    @FXML private Text dateText;
    @FXML private Text userprofileText;
    @FXML private Button logoutButton;

    /**
     * Inicializa el navbar: configura reloj, perfil y botón de logout.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupClock();
        showUserProfile();
        logoutButton.setOnAction(e -> cerrarSesion());
    }

    /**
     * Configura el reloj en tiempo real.
     * Actualiza cada segundo la hora y fecha.
     */
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

    /**
     * Muestra el nombre del operador con animación de entrada.
     */
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

        System.out.println("[Navbar] 👤 Usuario: " + saludo);
    }

    /**
     * Muestra cuadro de confirmación antes de cerrar sesión.
     */
    private void cerrarSesion() {
        boolean confirmado = DialogUtils.mostrarConfirmacion(
                "Confirmación",
                "¿Estás seguro que deseas cerrar sesión?",
                "Esta acción te llevará de vuelta al login."
        );

        if (confirmado) {
            ejecutarCierreSesion();
        } else {
            System.out.println("[Navbar] 🔄 Cierre de sesión cancelado por el usuario.");
        }
    }

    /**
     * Ejecuta el cierre de sesión y redirige al login.
     */
    private void ejecutarCierreSesion() {
        System.out.println("[Navbar] 🔒 Cerrando sesión...");

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/l3xxd/cos_alpha/views/login/login.fxml"));
            Parent loginRoot = loader.load();

            Scene loginScene = new Scene(loginRoot, 1920, 1080);

            Stage stage = (Stage) logoutButton.getScene().getWindow();
            stage.setScene(loginScene);
            stage.setTitle("COS_ALPHA v 2.1");
            stage.setResizable(false);
            stage.setMaximized(true);
            stage.show();

        } catch (Exception ex) {
            System.err.println("[Navbar] ❌ Error al cargar login.fxml");
            ex.printStackTrace();
        }
    }
}
