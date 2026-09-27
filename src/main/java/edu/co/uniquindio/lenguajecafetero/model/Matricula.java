package edu.co.uniquindio.lenguajecafetero.model;

import java.time.LocalDate;
import java.util.List;

public class Matricula {
    private String codigo;
    private LocalDate fechaMatricula;
    private edu.co.uniquindio.model.Estudiante estudiante;
    private Curso curso;
    private Profesor profesorAsignado;
    private List<ServicioAdicional> serviciosAdicionales;
    private double descuento;

    public Matricula (String codigo, LocalDate fechaMatricula, edu.co.uniquindio.model.Estudiante estudiante, Curso curso, Profesor profesor, ServicioAdicional servicioAdicional, double descuento){
        this.codigo = codigo;
        this.fechaMatricula = fechaMatricula;
        this.estudiante = estudiante;
        this.curso = curso;
        this.profesorAsignado = profesorAsignado;
        this.serviciosAdicionales = serviciosAdicionales;
        this.descuento = descuento;
    }

}
