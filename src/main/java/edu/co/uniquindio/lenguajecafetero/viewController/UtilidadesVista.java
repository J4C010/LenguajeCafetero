package edu.co.uniquindio.lenguajecafetero.viewController;

import javafx.scene.control.Alert;

import java.text.NumberFormat;
import java.util.Locale;

/** Métodos de apoyo compartidos por los viewController (mensajes y conversión de datos). */
public final class UtilidadesVista {

    private static final NumberFormat MONEDA = NumberFormat.getCurrencyInstance(Locale.of("es", "CO"));

    private UtilidadesVista() { }

    public static void mostrarMensaje(String titulo, String mensaje) {
        mostrar(Alert.AlertType.INFORMATION, titulo, mensaje);
    }

    public static void mostrarError(String mensaje) {
        mostrar(Alert.AlertType.ERROR, "Error", mensaje);
    }

    private static void mostrar(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public static String formatoMoneda(double valor) {
        return MONEDA.format(valor);
    }

    public static int leerEntero(String texto, String campo) {
        try {
            return Integer.parseInt(texto.trim());
        } catch (NumberFormatException | NullPointerException e) {
            throw new IllegalArgumentException(campo + " debe ser un número entero.");
        }
    }

    public static double leerDecimal(String texto, String campo) {
        try {
            return Double.parseDouble(texto.trim().replace(",", "."));
        } catch (NumberFormatException | NullPointerException e) {
            throw new IllegalArgumentException(campo + " debe ser un número.");
        }
    }
}
