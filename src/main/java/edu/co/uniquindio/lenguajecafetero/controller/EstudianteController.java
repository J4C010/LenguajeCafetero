package edu.co.uniquindio.lenguajecafetero.controller;

import edu.co.uniquindio.lenguajecafetero.model.Academia;
import edu.co.uniquindio.lenguajecafetero.model.Estudiante;
import edu.co.uniquindio.lenguajecafetero.model.Matricula;

import java.util.Collection;

public class EstudianteController {

    private final Academia academia;

    public EstudianteController(Academia academia) {
        this.academia = academia;
    }

    public boolean crearEstudiante(Estudiante estudiante) {
        return academia.agregarEstudiante(estudiante);
    }

    public Collection<Estudiante> obtenerListaEstudiantes() {
        return academia.getEstudiantes();
    }

    public Estudiante buscarEstudiante(String documento) {
        return academia.buscarEstudiante(documento);
    }

    public boolean actualizarEstudiante(String documento, Estudiante estudiante) {
        return academia.actualizarEstudiante(documento, estudiante);
    }

    public boolean eliminarEstudiante(String documento) {
        return academia.eliminarEstudiante(documento);
    }

    public Collection<Matricula> obtenerMatriculasEstudiante(String documento) {
        return academia.obtenerMatriculasEstudiante(documento);
    }
}
