package edu.co.uniquindio.lenguajecafetero.model;

public class SinDescuento implements PoliticaDescuento {

    @Override
    public double calcularDescuento(double subtotal, Matricula matricula) { return 0; }

    @Override
    public String getDescripcion() { return "Sin descuento"; }

    @Override
    public String toString() { return getDescripcion(); }
}
