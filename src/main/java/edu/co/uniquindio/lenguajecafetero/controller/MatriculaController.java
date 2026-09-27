package edu.co.uniquindio.lenguajecafetero.controller;

import edu.co.uniquindio.lenguajecafetero.model.Academia;
import edu.co.uniquindio.lenguajecafetero.model.Curso;
import edu.co.uniquindio.lenguajecafetero.model.Estudiante;
import edu.co.uniquindio.lenguajecafetero.model.Matricula;
import edu.co.uniquindio.lenguajecafetero.model.Profesor;
import edu.co.uniquindio.lenguajecafetero.model.ServicioAdicional;

import java.time.LocalDate;
import java.util.Collection;

public class MatriculaController {

    private final Academia academia;

    public MatriculaController(Academia academia) {
        this.academia = academia;
    }

    public boolean crearMatricula(Matricula matricula) {
        return academia.agregarMatricula(matricula);
    }

    public boolean eliminarMatricula(String codigo) {
        return academia.eliminarMatricula(codigo);
    }

    public boolean agregarServicio(String codigoMatricula, String codigoServicio) {
        return academia.agregarServicioAMatricula(codigoMatricula, codigoServicio);
    }

    public Collection<Matricula> obtenerListaMatriculas() {
        return academia.getMatriculas();
    }

    public Collection<Estudiante> obtenerEstudiantes() {
        return academia.getEstudiantes();
    }

    public Collection<Curso> obtenerCursos() {
        return academia.getCursos();
    }

    public Collection<Profesor> obtenerProfesores() {
        return academia.getProfesores();
    }

    public Collection<ServicioAdicional> obtenerServiciosDisponibles() {
        return academia.obtenerServiciosDisponibles();
    }

    public double calcularIngresos(LocalDate fechaInicial, LocalDate fechaFinal) {
        return academia.calcularIngresos(fechaInicial, fechaFinal);
    }

    public Collection<Matricula> obtenerMatriculasPeriodo(LocalDate fechaInicial, LocalDate fechaFinal) {
        return academia.obtenerMatriculasPeriodo(fechaInicial, fechaFinal);
    }
}
