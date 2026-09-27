package co.edu.uniquindio.lenguajecafetero.model;

/** Utilidades de validación usadas por las entidades del dominio. */
public final class Validaciones {

    private Validaciones() { }

    public static String requerido(String valor, String campo) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException(campo + " es obligatorio.");
        }
        return valor.trim();
    }

    public static double noNegativo(double valor, String campo) {
        if (valor < 0) {
            throw new IllegalArgumentException(campo + " no puede ser negativo.");
        }
        return valor;
    }

    public static int positivo(int valor, String campo) {
        if (valor <= 0) {
            throw new IllegalArgumentException(campo + " debe ser mayor que cero.");
        }
        return valor;
    }

    public static String correo(String valor) {
        String c = requerido(valor, "El correo electrónico");
        if (!c.matches("^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$")) {
            throw new IllegalArgumentException("El correo electrónico no es válido.");
        }
        return c;
    }
}
