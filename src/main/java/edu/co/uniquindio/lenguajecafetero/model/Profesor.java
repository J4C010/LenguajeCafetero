package edu.co.uniquindio.lenguajecafetero.model;

public class Profesor {
    private String iD;
    private String nombre;
    private String idiomaEnseña;
    private String telefono;
    private double tarifaSesion;

    public Profesor(String iD, String nombre, String idiomaEnseña, String telefono, double tarifaSesion) {
        this.iD = iD;
        this.nombre = nombre;
        this.idiomaEnseña = idiomaEnseña;
        this.telefono = telefono;
        this.tarifaSesion = tarifaSesion;
    }

    public String getiD() {
        return iD;
    }

    public String getNombre() {
        return nombre;
    }

    public String getIdiomaEnseña() {
        return idiomaEnseña;
    }

    public String getTelefono() {
        return telefono;
    }

    public double getTarifaSesion() {
        return tarifaSesion;
    }

    @Override
    public String toString() {
        return nombre + idiomaEnseña;
    }
}

