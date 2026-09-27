package edu.co.uniquindio.lenguajecafetero.model;

/**
 * Patrón Factory Method: cada fábrica concreta sabe crear un tipo de curso
 * y asignarle sus beneficios por defecto.
 */
public interface CursoFactory {
    Curso crearCurso(DatosCurso datos);
}
