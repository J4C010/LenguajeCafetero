package edu.co.uniquindio.lenguajecafetero.model;


public class DescuentoPorcentual implements PoliticaDescuento {

    private final double porcentaje;

    public DescuentoPorcentual(double porcentaje) {
        if (porcentaje < 0 || porcentaje > 100) {
            throw new IllegalArgumentException("El porcentaje debe estar entre 0 y 100.");
        }
        this.porcentaje = porcentaje;
    }

    @Override
    public double calcularDescuento(double subtotal, Matricula matricula) {
        return subtotal * porcentaje / 100.0;
    }

    public double getPorcentaje() { return porcentaje; }

    @Override
    public String getDescripcion() { return "Descuento del " + porcentaje + "%"; }

    @Override
    public String toString() { return getDescripcion(); }
}
