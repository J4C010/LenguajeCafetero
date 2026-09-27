package edu.co.uniquindio.lenguajecafetero.controller;

import edu.co.uniquindio.lenguajecafetero.model.Academia;
import edu.co.uniquindio.lenguajecafetero.model.AsignacionProfesor;
import edu.co.uniquindio.lenguajecafetero.model.Profesor;

import java.util.Collection;

public class ProfesorController {

    private final Academia academia;

    public ProfesorController(Academia academia) {
        this.academia = academia;
    }

    public boolean crearProfesor(Profesor profesor) {
        return academia.agregarProfesor(profesor);
    }

    public Collection<Profesor> obtenerListaProfesores() {
        return academia.getProfesores();
    }

    public boolean actualizarProfesor(String identificacion, Profesor profesor) {
        return academia.actualizarProfesor(identificacion, profesor);
    }

    public boolean eliminarProfesor(String identificacion) {
        return academia.eliminarProfesor(identificacion);
    }

    public Collection<AsignacionProfesor> obtenerAsignaciones(Profesor profesor) {
        return academia.obtenerAsignaciones(profesor);
    }
}
