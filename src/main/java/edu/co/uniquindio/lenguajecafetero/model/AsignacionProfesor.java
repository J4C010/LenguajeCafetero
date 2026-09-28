package edu.co.uniquindio.lenguajecafetero.model;

import java.time.LocalDate;
import java.util.Objects;

public class AsignacionProfesor {

    private final Matricula matricula;
    private final Profesor profesor;
    private final LocalDate fechaAsignacion;

    public AsignacionProfesor(Matricula matricula, Profesor profesor, LocalDate fechaAsignacion) {
        this.matricula = Objects.requireNonNull(matricula);
        this.profesor = Objects.requireNonNull(profesor, "El profesor es obligatorio.");
        this.fechaAsignacion = fechaAsignacion != null ? fechaAsignacion : LocalDate.now();
    }

    public Estudiante getEstudiante() { return matricula.getEstudiante(); }
    public Curso getCurso() { return matricula.getCurso(); }
    public Profesor getProfesor() { return profesor; }
    public Matricula getMatricula() { return matricula; }
    public LocalDate getFechaAsignacion() { return fechaAsignacion; }

    @Override
    public String toString() {
        return getEstudiante().getNombre() + " – " + getCurso().getNombre() + " – " + profesor.getNombre();
    }
}
