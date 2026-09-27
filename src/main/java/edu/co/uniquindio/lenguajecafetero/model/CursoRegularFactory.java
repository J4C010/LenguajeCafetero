package co.edu.uniquindio.lenguajecafetero.model;

public class CursoRegularFactory implements CursoFactory {

    @Override
    public Curso crearCurso(DatosCurso datos) {
        Curso curso = new CursoRegular(datos);
        curso.agregarBeneficio(Beneficio.PLATAFORMA_VIRTUAL);
        return curso;
    }
}
