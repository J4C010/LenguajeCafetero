package co.edu.uniquindio.lenguajecafetero.model;


public class CursoRegular extends Curso {

    public CursoRegular(DatosCurso datos) { super(datos); }

    @Override
    public TipoCurso getTipo() { return TipoCurso.REGULAR; }

    @Override
    public double calcularValor(int mesesContratados) {
        validarMesesContratados(mesesContratados);
        return getValorMensual() * mesesContratados;
    }
}
