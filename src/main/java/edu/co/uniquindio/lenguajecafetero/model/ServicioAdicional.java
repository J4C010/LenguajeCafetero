package edu.co.uniquindio.lenguajecafetero.model;

import java.util.Objects;

/** Simulacro de certificación, tutoría de refuerzo, material impreso, talleres, etc. */
public class ServicioAdicional {

    private final String codigo;
    private String nombre;
    private String descripcion;
    private double precio;
    private boolean disponible;

    public ServicioAdicional(String codigo, String nombre, String descripcion,
                             double precio, boolean disponible) {
        this.codigo = Validaciones.requerido(codigo, "El código");
        this.nombre = Validaciones.requerido(nombre, "El nombre");
        this.descripcion = descripcion == null ? "" : descripcion;
        this.precio = Validaciones.noNegativo(precio, "El precio");
        this.disponible = disponible;
    }


    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public double getPrecio() { return precio; }
    public boolean isDisponible() { return disponible; }

    public void setNombre(String nombre) { this.nombre = Validaciones.requerido(nombre, "El nombre"); }
    public void setDescripcion(String d) { this.descripcion = d; }
    public void setPrecio(double precio) { this.precio = Validaciones.noNegativo(precio, "El precio"); }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }

    @Override
    public boolean equals(Object o) {
        return o instanceof ServicioAdicional s && codigo.equals(s.codigo);
    }

    @Override
    public int hashCode() { return Objects.hash(codigo); }

    @Override
    public String toString() { return nombre + " ($" + String.format("%,.0f", precio) + ")"; }
}
