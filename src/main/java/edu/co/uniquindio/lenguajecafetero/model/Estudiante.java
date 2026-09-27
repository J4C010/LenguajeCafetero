package edu.co.uniquindio.model;

import java.time.LocalDate;

public class Estudiante {
    private String nombreCompleto;
    private String documento;
    private String telefono;
    private String correoElectronico;
    private int edad;
    private LocalDate fRegistro;

    public Estudiante (String nombreCompleto, String documento, String telefono, String correoElectronico, int edad, LocalDate fRegistro){
        this.nombreCompleto = nombreCompleto;
        this.documento = documento;
        this.telefono = telefono;
        this.correoElectronico = correoElectronico;
        this.edad = edad;
        this.fRegistro = fRegistro;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public String getDocumento() {
        return documento;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public int getEdad() {
        return edad;
    }

    public LocalDate getfRegistro() {
        return fRegistro;
    }

    @Override
    public String toString (){
        return nombreCompleto + "("+ documento +")";
    }

}
