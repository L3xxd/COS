package com.l3xxd.cos_alpha.controllers.dashboard.inventario;

import com.l3xxd.cos_alpha.config.DBConnection;
import com.l3xxd.cos_alpha.dao.InventarioDAO;
import com.l3xxd.cos_alpha.models.InventarioModel;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.sql.Connection;

public class InventarioFormController {

    private String modo; // "Agregar" o "Editar"
    private InventarioModel producto;

    @FXML private Label stockActualLabel;
    @FXML private TextField ajusteStockField;
    @FXML private Label stockPreviewLabel;

    @FXML private TextField nombreField;
    @FXML private ComboBox<String> categoriaCombo;
    @FXML private TextField precioCompraField;
    @FXML private TextField precioVentaField;
    @FXML private TextField stockField;
    @FXML private ComboBox<String> estadoComboBox;
    @FXML private TextField urlFotoField;
    @FXML private Button guardarButton;

    @FXML private Button btnCargarDesdeURL;
    @FXML private Button btnSeleccionarArchivo;

    @FXML private ImageView previewImageView;
    @FXML private Button cancelarButton;

    public void setModo(String modo) {
        this.modo = modo;
    }

    public void setProducto(InventarioModel producto) {
        this.producto = producto;
        cargarDatosEnFormulario();
    }

    @FXML
    private void initialize() {
        guardarButton.setOnAction(e -> guardarProducto());
        cancelarButton.setOnAction(e -> cerrarVentana());
        btnCargarDesdeURL.setOnAction(e -> cargarImagenDesdeURL());
        btnSeleccionarArchivo.setOnAction(event -> seleccionarImagenDesdeArchivo());

    }
    @FXML
    public void guardarProducto() {
        try {
            if (!validarCampos()) return;

            if (producto == null) {
                producto = new InventarioModel();
            }

            producto.setNombre(nombreField.getText().trim());
            producto.setTipo(categoriaCombo.getValue());
            producto.setPrecioCompra(Double.parseDouble(precioCompraField.getText()));
            producto.setPrecioVenta(Double.parseDouble(precioVentaField.getText()));
            producto.setStock(Integer.parseInt(stockField.getText()) + obtenerAjusteStock());
            producto.setEstado(estadoComboBox.getValue());
            producto.setUrlFoto(urlFotoField.getText().trim());

            Connection conn = DBConnection.getConnection();
            InventarioDAO dao = new InventarioDAO(conn);

            if ("Agregar".equals(modo)) {
                dao.insertar(producto);
            } else if ("Editar".equals(modo)) {
                dao.actualizar(producto);
            }

            cerrarVentana();
        } catch (Exception e) {
            mostrarAlerta("Error al guardar el producto. Verifica los campos.");
        }
    }

    private void cargarDatosEnFormulario() {
        if (producto != null) {
            nombreField.setText(producto.getNombre());
            categoriaCombo.setValue(producto.getTipo());
            precioCompraField.setText(String.valueOf(producto.getPrecioCompra()));
            precioVentaField.setText(String.valueOf(producto.getPrecioVenta()));
            stockField.setText(String.valueOf(producto.getStock()));
            estadoComboBox.setValue(producto.getEstado());
            urlFotoField.setText(producto.getUrlFoto());

            stockActualLabel.setText("Stock actual: " + producto.getStock());
            ajusteStockField.setText("0");
            stockPreviewLabel.setText("Stock resultante: " + producto.getStock());

            ajusteStockField.textProperty().addListener((obs, oldVal, newVal) -> {
                try {
                    int ajuste = Integer.parseInt(newVal);
                    int nuevoStock = producto.getStock() + ajuste;
                    stockPreviewLabel.setText("Stock resultante: " + nuevoStock);
                } catch (NumberFormatException e) {
                    stockPreviewLabel.setText("Valor inválido");
                }
            });
        }
    }

    private int obtenerAjusteStock() {
        try {
            return Integer.parseInt(ajusteStockField.getText());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private boolean validarCampos() {
        if (nombreField.getText().trim().isEmpty() ||
                categoriaCombo.getValue() == null ||
                precioCompraField.getText().trim().isEmpty() ||
                precioVentaField.getText().trim().isEmpty() ||
                stockField.getText().trim().isEmpty() ||
                estadoComboBox.getValue() == null) {
            mostrarAlerta("Todos los campos obligatorios deben estar completos.");
            return false;
        }
        return true;
    }

    private void cerrarVentana() {
        Stage stage = (Stage) guardarButton.getScene().getWindow();
        stage.close();
    }

    private void mostrarAlerta(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle("Advertencia");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
    @FXML
    public void cargarImagenDesdeURL() {
        String url = urlFotoField.getText();
        if (url == null || url.trim().isEmpty()) {
            mostrarAlerta("La URL de la foto está vacía o no inicializada.");
            previewImageView.setImage(null);
            return;
        }

        try {
            Image imagen = new Image(url.trim(), true);
            if (imagen.isError()) {
                throw new Exception("No se pudo cargar la imagen.");
            }
            previewImageView.setImage(imagen);
        } catch (Exception e) {
            mostrarAlerta("Error al cargar la imagen. Verifica la URL.");
            previewImageView.setImage(null);
        }
    }
    @FXML
    public void seleccionarImagenDesdeArchivo() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar imagen");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        File archivoSeleccionado = fileChooser.showOpenDialog(previewImageView.getScene().getWindow());

        if (archivoSeleccionado != null) {
            try {
                Image imagen = new Image(archivoSeleccionado.toURI().toString());
                previewImageView.setImage(imagen);
                urlFotoField.setText(archivoSeleccionado.toURI().toString()); // Actualiza el campo con la ruta
            } catch (Exception e) {
                mostrarAlerta("No se pudo cargar la imagen seleccionada.");
                previewImageView.setImage(null);
            }
        }
    }


}

