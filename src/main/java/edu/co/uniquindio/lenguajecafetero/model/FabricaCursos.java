package co.edu.uniquindio.lenguajecafetero.model;

import java.util.EnumMap;
import java.util.Map;

/**
 * Punto único de creación de cursos. Para agregar un tipo nuevo basta con registrar
 * su fábrica (OCP): no hay switch ni if por tipo en el resto del sistema.
 */
public class FabricaCursos {

    private final Map<TipoCurso, CursoFactory> fabricas = new EnumMap<>(TipoCurso.class);

    public FabricaCursos() {
        registrarFabrica(TipoCurso.REGULAR, new CursoRegularFactory());
        registrarFabrica(TipoCurso.INTENSIVO, new CursoIntensivoFactory());
        registrarFabrica(TipoCurso.PERSONALIZADO, new CursoPersonalizadoFactory());
    }

    public final void registrarFabrica(TipoCurso tipo, CursoFactory fabrica) {
        fabricas.put(tipo, fabrica);
    }

    public Curso crearCurso(TipoCurso tipo, DatosCurso datos) {
        CursoFactory fabrica = fabricas.get(tipo);
        if (fabrica == null) {
            throw new IllegalArgumentException("No existe una fábrica para el tipo " + tipo);
        }
        return fabrica.crearCurso(datos);
    }
}
