package co.edu.uniquindio.lenguajecafetero.model;

public class CursoPersonalizadoFactory implements CursoFactory {

    @Override
    public Curso crearCurso(DatosCurso datos) {
        Curso curso = new CursoPersonalizado(datos);
        curso.agregarBeneficio(Beneficio.PLATAFORMA_VIRTUAL);
        curso.agregarBeneficio(Beneficio.MATERIAL_DIDACTICO);
        curso.agregarBeneficio(Beneficio.CLUB_CONVERSACION);
        return curso;
    }
}
