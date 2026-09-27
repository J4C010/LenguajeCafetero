package edu.co.uniquindio.lenguajecafetero.model;

/**
 * Estrategia de descuento aplicable a una matrícula (OCP + DIP): la matrícula depende
 * de esta abstracción y la academia puede crear nuevos descuentos sin modificarla.
 */
public interface PoliticaDescuento {

    /** Devuelve el valor a descontar sobre el subtotal de la matrícula. */
    double calcularDescuento(double subtotal, Matricula matricula);

    String getDescripcion();
}
