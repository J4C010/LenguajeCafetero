package co.edu.uniquindio.lenguajecafetero.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Matrícula de un estudiante en un curso. Se crea con el patrón Builder porque
 * tiene datos obligatorios, opcionales y reglas que se validan al construirla.
 *
 * Valor total = valor del curso (según tipo y meses)
 *             + valor de sesiones con profesor (solo personalizados)
 *             + servicios adicionales
 *             - descuento.
 */
public class Matricula {

    private final String codigo;
    private final LocalDate fecha;
    private final Estudiante estudiante;
    private final Curso curso;
    private final int mesesContratados;
    private final List<ServicioAdicional> servicios = new ArrayList<>();
    private PoliticaDescuento descuento;
    private AsignacionProfesor asignacion;

    private Matricula(Builder b) {
        this.codigo = Validaciones.requerido(b.codigo, "El código de la matrícula");
        this.fecha = b.fecha != null ? b.fecha : LocalDate.now();
        this.estudiante = Objects.requireNonNull(b.estudiante, "Debe seleccionar un estudiante.");
        this.curso = Objects.requireNonNull(b.curso, "Debe seleccionar un curso.");
        if (!curso.estaActivo()) {
            throw new IllegalStateException("Solo se puede matricular en cursos activos.");
        }
        curso.validarMesesContratados(b.mesesContratados);
        this.mesesContratados = b.mesesContratados;
        this.descuento = b.descuento != null ? b.descuento : new SinDescuento();

        if (curso.requiereProfesor()) {
            if (b.profesor == null) {
                throw new IllegalArgumentException("Los cursos personalizados requieren un profesor asignado.");
            }
            asignarProfesor(b.profesor);
        }
        for (ServicioAdicional s : b.servicios) {
            agregarServicio(s);
        }
    }

    public static Builder builder() { return new Builder(); }

    public final void asignarProfesor(Profesor profesor) {
        if (!curso.requiereProfesor()) {
            throw new IllegalStateException("Solo los cursos personalizados tienen profesor asignado.");
        }
        if (!profesor.ensena(curso.getIdioma())) {
            throw new IllegalArgumentException("El profesor no enseña " + curso.getIdioma() + ".");
        }
        this.asignacion = new AsignacionProfesor(this, profesor, fecha);
    }

    public final void agregarServicio(ServicioAdicional servicio) {
        Objects.requireNonNull(servicio);
        if (!servicio.isDisponible()) {
            throw new IllegalStateException("El servicio " + servicio.getNombre() + " no está disponible.");
        }
        servicios.add(servicio);
    }

    public double calcularValorCurso() {
        double valor = curso.calcularValor(mesesContratados);
        if (asignacion != null && curso instanceof CursoPersonalizado personalizado) {
            valor += personalizado.calcularValorSesiones(asignacion.getProfesor());
        }
        return valor;
    }

    public double calcularValorServicios() {
        double total = 0;
        for (ServicioAdicional s : servicios) {
            total += s.getPrecio();
        }
        return total;
    }

    public double calcularSubtotal() { return calcularValorCurso() + calcularValorServicios(); }

    public double calcularDescuento() { return descuento.calcularDescuento(calcularSubtotal(), this); }

    public double calcularValorTotal() { return calcularSubtotal() - calcularDescuento(); }

    /** true si la fecha de la matrícula está dentro del periodo [inicio, fin] (ambos inclusive). */
    public boolean estaEnPeriodo(LocalDate inicio, LocalDate fin) {
        return !fecha.isBefore(inicio) && !fecha.isAfter(fin);
    }

    public String getCodigo() { return codigo; }
    public LocalDate getFecha() { return fecha; }
    public Estudiante getEstudiante() { return estudiante; }
    public Curso getCurso() { return curso; }
    public int getMesesContratados() { return mesesContratados; }
    public List<ServicioAdicional> getServicios() { return Collections.unmodifiableList(servicios); }
    public PoliticaDescuento getDescuento() { return descuento; }
    public AsignacionProfesor getAsignacion() { return asignacion; }
    public Profesor getProfesor() { return asignacion == null ? null : asignacion.getProfesor(); }

    public void setDescuento(PoliticaDescuento descuento) {
        this.descuento = descuento != null ? descuento : new SinDescuento();
    }

    public static final class Builder {
        private String codigo;
        private LocalDate fecha;
        private Estudiante estudiante;
        private Curso curso;
        private int mesesContratados;
        private Profesor profesor;
        private PoliticaDescuento descuento;
        private final List<ServicioAdicional> servicios = new ArrayList<>();

        private Builder() { }

        public Builder codigo(String v) { codigo = v; return this; }
        public Builder fecha(LocalDate v) { fecha = v; return this; }
        public Builder estudiante(Estudiante v) { estudiante = v; return this; }
        public Builder curso(Curso v) { curso = v; return this; }
        public Builder mesesContratados(int v) { mesesContratados = v; return this; }
        public Builder profesor(Profesor v) { profesor = v; return this; }
        public Builder descuento(PoliticaDescuento v) { descuento = v; return this; }
        public Builder servicio(ServicioAdicional v) { servicios.add(v); return this; }
        public Builder servicios(List<ServicioAdicional> v) { servicios.addAll(v); return this; }

        public Matricula build() { return new Matricula(this); }
    }
}
