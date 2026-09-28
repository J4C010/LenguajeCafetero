package edu.co.uniquindio.lenguajecafetero.controller;

import edu.co.uniquindio.lenguajecafetero.model.Academia;
import edu.co.uniquindio.lenguajecafetero.model.Curso;
import edu.co.uniquindio.lenguajecafetero.model.DatosCurso;
import edu.co.uniquindio.lenguajecafetero.model.EstadoCurso;
import edu.co.uniquindio.lenguajecafetero.model.FabricaCursos;
import edu.co.uniquindio.lenguajecafetero.model.TipoCurso;

import java.util.Collection;

public class CursoController {

    private final Academia academia;
    private final FabricaCursos fabricaCursos;

    public CursoController(Academia academia) {
        this.academia = academia;
        this.fabricaCursos = new FabricaCursos();
    }


    public boolean crearCurso(TipoCurso tipo, DatosCurso datos) {
        Curso curso = fabricaCursos.crearCurso(tipo, datos);
        return academia.agregarCurso(curso);
    }

    public Collection<Curso> obtenerListaCursos() {
        return academia.getCursos();
    }

    public boolean actualizarCurso(String codigo, String nombre, String descripcion,
                                   int duracionMeses, double valorMensual, EstadoCurso estado) {
        return academia.actualizarCurso(codigo, nombre, descripcion, duracionMeses, valorMensual, estado);
    }

    public boolean eliminarCurso(String codigo) {
        return academia.eliminarCurso(codigo);
    }
}
