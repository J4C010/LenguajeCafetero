package co.edu.uniquindio.lenguajecafetero.model;

public class CursoIntensivoFactory implements CursoFactory {

    @Override
    public Curso crearCurso(DatosCurso datos) {
        Curso curso = new CursoIntensivo(datos);
        curso.agregarBeneficio(Beneficio.PLATAFORMA_VIRTUAL);
        curso.agregarBeneficio(Beneficio.MATERIAL_DIDACTICO);
        return curso;
    }
}
