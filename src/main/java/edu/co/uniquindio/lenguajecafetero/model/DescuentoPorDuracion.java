package co.edu.uniquindio.lenguajecafetero.model;

/** 10 % de descuento cuando se contratan 6 meses o más. */
public class DescuentoPorDuracion implements PoliticaDescuento {

    public static final int MESES_MINIMOS = 6;
    public static final double PORCENTAJE = 10;

    @Override
    public double calcularDescuento(double subtotal, Matricula matricula) {
        if (matricula.getMesesContratados() >= MESES_MINIMOS) {
            return subtotal * PORCENTAJE / 100.0;
        }
        return 0;
    }

    @Override
    public String getDescripcion() {
        return "10% por " + MESES_MINIMOS + " meses o más";
    }

    @Override
    public String toString() { return getDescripcion(); }
}
