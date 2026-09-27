package edu.co.uniquindio.lenguajecafetero.model;

import java.util.Objects;

/** Datos comunes de estudiantes y profesores. */
public abstract class Persona {

    private final String identificacion;
    private String nombre;
    private String telefono;

    protected Persona(String identificacion, String nombre, String telefono) {
        this.identificacion = Validaciones.requerido(identificacion, "La identificación");
        this.nombre = Validaciones.requerido(nombre, "El nombre");
        this.telefono = Validaciones.requerido(telefono, "El teléfono");
    }


    public String getIdentificacion() { return identificacion; }
    public String getNombre() { return nombre; }
    public String getTelefono() { return telefono; }

    public void setNombre(String nombre) { this.nombre = Validaciones.requerido(nombre, "El nombre"); }
    public void setTelefono(String telefono) { this.telefono = Validaciones.requerido(telefono, "El teléfono"); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return identificacion.equals(((Persona) o).identificacion);
    }

    @Override
    public int hashCode() { return Objects.hash(identificacion); }

    @Override
    public String toString() { return nombre + " (" + identificacion + ")"; }
}
