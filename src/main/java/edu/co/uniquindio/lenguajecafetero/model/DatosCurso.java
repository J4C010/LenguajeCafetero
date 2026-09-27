package edu.co.uniquindio.lenguajecafetero.model;


import java.util.EnumSet;
import java.util.Set;

/**
 * Objeto de parámetros para crear cursos. Se construye con el patrón Builder
 * para no tener constructores con muchos parámetros opcionales.
 */
public final class DatosCurso {

    private final String codigo;
    private final String nombre;
    private final Idioma idioma;
    private final String descripcion;
    private final int duracionMeses;
    private final double valorMensual;
    private final EstadoCurso estado;
    private final Set<Beneficio> beneficios;
    private final int horasSemanales;
    private final int cantidadSesiones;
    private final NivelReferencia nivelRequerido;
    private final String objetivos;

    private DatosCurso(Builder b) {
        codigo = b.codigo; nombre = b.nombre; idioma = b.idioma; descripcion = b.descripcion;
        duracionMeses = b.duracionMeses; valorMensual = b.valorMensual; estado = b.estado;
        beneficios = b.beneficios; horasSemanales = b.horasSemanales;
        cantidadSesiones = b.cantidadSesiones; nivelRequerido = b.nivelRequerido; objetivos = b.objetivos;
    }

    public static Builder builder() { return new Builder(); }

    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public Idioma getIdioma() { return idioma; }
    public String getDescripcion() { return descripcion; }
    public int getDuracionMeses() { return duracionMeses; }
    public double getValorMensual() { return valorMensual; }
    public EstadoCurso getEstado() { return estado; }
    public Set<Beneficio> getBeneficios() { return beneficios; }
    public int getHorasSemanales() { return horasSemanales; }
    public int getCantidadSesiones() { return cantidadSesiones; }
    public NivelReferencia getNivelRequerido() { return nivelRequerido; }
    public String getObjetivos() { return objetivos; }

    public static final class Builder {
        private String codigo;
        private String nombre;
        private Idioma idioma;
        private String descripcion = "";
        private int duracionMeses;
        private double valorMensual;
        private EstadoCurso estado = EstadoCurso.ACTIVO;
        private final Set<Beneficio> beneficios = EnumSet.noneOf(Beneficio.class);
        private int horasSemanales;
        private int cantidadSesiones;
        private NivelReferencia nivelRequerido;
        private String objetivos;

        private Builder() { }

        public Builder codigo(String v) { codigo = v; return this; }
        public Builder nombre(String v) { nombre = v; return this; }
        public Builder idioma(Idioma v) { idioma = v; return this; }
        public Builder descripcion(String v) { descripcion = v; return this; }
        public Builder duracionMeses(int v) { duracionMeses = v; return this; }
        public Builder valorMensual(double v) { valorMensual = v; return this; }
        public Builder estado(EstadoCurso v) { estado = v; return this; }
        public Builder beneficio(Beneficio v) { beneficios.add(v); return this; }
        public Builder beneficios(Set<Beneficio> v) { beneficios.addAll(v); return this; }
        public Builder horasSemanales(int v) { horasSemanales = v; return this; }
        public Builder cantidadSesiones(int v) { cantidadSesiones = v; return this; }
        public Builder nivelRequerido(NivelReferencia v) { nivelRequerido = v; return this; }
        public Builder objetivos(String v) { objetivos = v; return this; }

        public DatosCurso build() { return new DatosCurso(this); }
    }
}
