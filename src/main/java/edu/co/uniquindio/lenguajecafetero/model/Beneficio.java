package edu.co.uniquindio.lenguajecafetero.model;

public enum Beneficio {
    PLATAFORMA_VIRTUAL("Acceso a plataforma virtual"),
    MATERIAL_DIDACTICO("Material didáctico"),
    CLUB_CONVERSACION("Club de conversación");

    private final String descripcion;

    Beneficio(String descripcion) { this.descripcion = descripcion; }

    @Override
    public String toString() { return descripcion; }
}
