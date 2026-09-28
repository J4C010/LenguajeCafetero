package edu.co.uniquindio.lenguajecafetero.model;



public class CursoIntensivo extends Curso {

    public static final double RECARGO_INTENSIDAD = 0.25;

    private final int horasSemanales;

    public CursoIntensivo(DatosCurso datos) {
        super(datos);
        this.horasSemanales = Validaciones.positivo(datos.getHorasSemanales(), "Las horas semanales");
    }

    @Override
    public TipoCurso getTipo() { return TipoCurso.INTENSIVO; }

    @Override
    public double calcularValor(int mesesContratados) {
        validarMesesContratados(mesesContratados);
        return getValorMensual() * mesesContratados * (1 + RECARGO_INTENSIDAD);
    }

    public int getHorasSemanales() { return horasSemanales; }
}
