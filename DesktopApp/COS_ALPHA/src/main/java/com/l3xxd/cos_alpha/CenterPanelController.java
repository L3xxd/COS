package com.l3xxd.cos_alpha;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class CenterPanelController {

    @FXML private Text welcomeLabel;
    @FXML private TextField userTextField;
    @FXML private PasswordField passwordTextField;
    @FXML private Button enterButton;
    @FXML private Hyperlink linkText;
    @FXML private Button themeToggleButton;

    public void initialize() {
        // Aquí puedes agregar lógica de bienvenida, animaciones, etc.
    }
}
