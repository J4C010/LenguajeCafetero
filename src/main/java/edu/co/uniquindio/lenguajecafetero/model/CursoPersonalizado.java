package edu.co.uniquindio.lenguajecafetero.model;


import java.util.Objects;

public class CursoPersonalizado extends Curso {

    private final int cantidadSesiones;
    private final NivelReferencia nivelRequerido;
    private final String objetivosEstudiante;

    public CursoPersonalizado(DatosCurso datos) {
        super(datos);
        this.cantidadSesiones = Validaciones.positivo(datos.getCantidadSesiones(), "La cantidad de sesiones");
        this.nivelRequerido = Objects.requireNonNull(datos.getNivelRequerido(), "El nivel requerido es obligatorio.");
        this.objetivosEstudiante = Validaciones.requerido(datos.getObjetivos(), "Los objetivos del estudiante");
    }

    @Override
    public TipoCurso getTipo() { return TipoCurso.PERSONALIZADO; }

    @Override
    public double calcularValor(int mesesContratados) {
        validarMesesContratados(mesesContratados);
        return getValorMensual() * mesesContratados;
    }

    @Override
    public boolean requiereProfesor() { return true; }

    public double calcularValorSesiones(Profesor profesor) {
        return cantidadSesiones * profesor.getTarifaPorSesion();
    }

    public int getCantidadSesiones() { return cantidadSesiones; }
    public NivelReferencia getNivelRequerido() { return nivelRequerido; }
    public String getObjetivosEstudiante() { return objetivosEstudiante; }
}
