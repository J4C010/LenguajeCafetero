package edu.co.uniquindio.lenguajecafetero.model;


import java.util.Collections;
import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;


public abstract class Curso {

    private final String codigo;
    private String nombre;
    private Idioma idioma;
    private String descripcion;
    private int duracionMeses;
    private double valorMensual;
    private EstadoCurso estado;
    private final Set<Beneficio> beneficios = EnumSet.noneOf(Beneficio.class);

    protected Curso(DatosCurso datos) {
        this.codigo = Validaciones.requerido(datos.getCodigo(), "El código");
        this.nombre = Validaciones.requerido(datos.getNombre(), "El nombre");
        this.idioma = Objects.requireNonNull(datos.getIdioma(), "El idioma es obligatorio.");
        this.descripcion = datos.getDescripcion() == null ? "" : datos.getDescripcion();
        this.duracionMeses = Validaciones.positivo(datos.getDuracionMeses(), "La duración en meses");
        this.valorMensual = Validaciones.noNegativo(datos.getValorMensual(), "El valor mensual");
        this.estado = datos.getEstado() != null ? datos.getEstado() : EstadoCurso.ACTIVO;
        this.beneficios.addAll(datos.getBeneficios());
    }

    public abstract TipoCurso getTipo();

    /** Valor del curso por la cantidad de meses contratados, antes de servicios y descuentos. */
    public abstract double calcularValor(int mesesContratados);

    public boolean estaActivo() { return estado == EstadoCurso.ACTIVO; }

    public boolean requiereProfesor() { return false; }

    public void validarMesesContratados(int meses) {
        if (meses <= 0 || meses > duracionMeses) {
            throw new IllegalArgumentException(
                    "Los meses contratados deben estar entre 1 y " + duracionMeses + ".");
        }
    }

    public void agregarBeneficio(Beneficio beneficio) { beneficios.add(beneficio); }
    public void quitarBeneficio(Beneficio beneficio) { beneficios.remove(beneficio); }


    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public Idioma getIdioma() { return idioma; }
    public String getDescripcion() { return descripcion; }
    public int getDuracionMeses() { return duracionMeses; }
    public double getValorMensual() { return valorMensual; }
    public EstadoCurso getEstado() { return estado; }
    public Set<Beneficio> getBeneficios() { return Collections.unmodifiableSet(beneficios); }

    public void setNombre(String nombre) { this.nombre = Validaciones.requerido(nombre, "El nombre"); }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setValorMensual(double v) { this.valorMensual = Validaciones.noNegativo(v, "El valor mensual"); }
    public void setDuracionMeses(int m) { this.duracionMeses = Validaciones.positivo(m, "La duración en meses"); }
    public void setEstado(EstadoCurso estado) { this.estado = Objects.requireNonNull(estado); }

    @Override
    public String toString() { return codigo + " - " + nombre + " (" + getTipo() + ", " + idioma + ")"; }
}
