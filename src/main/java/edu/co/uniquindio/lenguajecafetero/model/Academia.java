package edu.co.uniquindio.lenguajecafetero.model;

import java.time.LocalDate;
import java.util.Collection;
import java.util.LinkedList;


public class Academia {

    private static Academia instancia;

    private String nombreComercial;
    private String nit;
    private String direccion;
    private String telefono;
    private String correo;
    private String paginaWeb;

    private final Collection<Estudiante> estudiantes = new LinkedList<>();
    private final Collection<Profesor> profesores = new LinkedList<>();
    private final Collection<Curso> cursos = new LinkedList<>();
    private final Collection<ServicioAdicional> servicios = new LinkedList<>();
    private final Collection<Matricula> matriculas = new LinkedList<>();

    private Academia() {
        this.nombreComercial = "LenguajeCafetero";
        this.nit = "900.123.456-7";
        this.direccion = "Armenia, Quindío";
        this.telefono = "6067000000";
        this.correo = "contacto@lenguajecafetero.com";
        this.paginaWeb = "www.lenguajecafetero.com";
    }

    public static Academia getInstance() {
        if (instancia == null) {
            instancia = new Academia();
        }
        return instancia;
    }

    // ---------------------------------------------------------------- Estudiantes

    public boolean agregarEstudiante(Estudiante estudiante) {
        boolean centinela = false;
        if (!verificarEstudiante(estudiante.getDocumento())) {
            estudiantes.add(estudiante);
            centinela = true;
        }
        return centinela;
    }

    public boolean verificarEstudiante(String documento) {
        return buscarEstudiante(documento) != null;
    }

    /** Búsqueda de un estudiante por su documento de identidad. Retorna null si no existe. */
    public Estudiante buscarEstudiante(String documento) {
        for (Estudiante estudiante : estudiantes) {
            if (estudiante.getDocumento().equals(documento)) {
                return estudiante;
            }
        }
        return null;
    }

    public boolean actualizarEstudiante(String documento, Estudiante actualizado) {
        Estudiante estudiante = buscarEstudiante(documento);
        if (estudiante == null) {
            return false;
        }
        estudiante.setNombre(actualizado.getNombre());
        estudiante.setTelefono(actualizado.getTelefono());
        estudiante.setCorreo(actualizado.getCorreo());
        estudiante.setEdad(actualizado.getEdad());
        return true;
    }

    public boolean eliminarEstudiante(String documento) {
        Estudiante estudiante = buscarEstudiante(documento);
        if (estudiante == null || tieneMatriculas(estudiante)) {
            return false;
        }
        return estudiantes.remove(estudiante);
    }

    private boolean tieneMatriculas(Estudiante estudiante) {
        for (Matricula m : matriculas) {
            if (m.getEstudiante().equals(estudiante)) {
                return true;
            }
        }
        return false;
    }

    // ---------------------------------------------------------------- Profesores

    public boolean agregarProfesor(Profesor profesor) {
        boolean centinela = false;
        if (!verificarProfesor(profesor.getIdentificacion())) {
            profesores.add(profesor);
            centinela = true;
        }
        return centinela;
    }

    public boolean verificarProfesor(String identificacion) {
        return buscarProfesor(identificacion) != null;
    }

    public Profesor buscarProfesor(String identificacion) {
        for (Profesor profesor : profesores) {
            if (profesor.getIdentificacion().equals(identificacion)) {
                return profesor;
            }
        }
        return null;
    }

    public boolean actualizarProfesor(String identificacion, Profesor actualizado) {
        Profesor profesor = buscarProfesor(identificacion);
        if (profesor == null) {
            return false;
        }
        profesor.setNombre(actualizado.getNombre());
        profesor.setTelefono(actualizado.getTelefono());
        profesor.setIdioma(actualizado.getIdioma());
        profesor.setTarifaPorSesion(actualizado.getTarifaPorSesion());
        return true;
    }

    public boolean eliminarProfesor(String identificacion) {
        Profesor profesor = buscarProfesor(identificacion);
        if (profesor == null) {
            return false;
        }
        for (Matricula m : matriculas) {
            if (profesor.equals(m.getProfesor())) {
                return false;
            }
        }
        return profesores.remove(profesor);
    }

    /** Profesores que pueden atender un curso personalizado (enseñan su idioma). */
    public Collection<Profesor> obtenerProfesoresPorIdioma(Idioma idioma) {
        Collection<Profesor> resultado = new LinkedList<>();
        for (Profesor p : profesores) {
            if (p.ensena(idioma)) {
                resultado.add(p);
            }
        }
        return resultado;
    }

    /** Asignaciones profesor – estudiante – curso de un profesor. */
    public Collection<AsignacionProfesor> obtenerAsignaciones(Profesor profesor) {
        Collection<AsignacionProfesor> resultado = new LinkedList<>();
        for (Matricula m : matriculas) {
            if (m.getAsignacion() != null && m.getProfesor().equals(profesor)) {
                resultado.add(m.getAsignacion());
            }
        }
        return resultado;
    }

    // ---------------------------------------------------------------- Cursos

    public boolean agregarCurso(Curso curso) {
        boolean centinela = false;
        if (!verificarCurso(curso.getCodigo())) {
            cursos.add(curso);
            centinela = true;
        }
        return centinela;
    }

    public boolean verificarCurso(String codigo) {
        return buscarCurso(codigo) != null;
    }

    public Curso buscarCurso(String codigo) {
        for (Curso curso : cursos) {
            if (curso.getCodigo().equals(codigo)) {
                return curso;
            }
        }
        return null;
    }

    public boolean actualizarCurso(String codigo, String nombre, String descripcion,
                                   int duracionMeses, double valorMensual, EstadoCurso estado) {
        Curso curso = buscarCurso(codigo);
        if (curso == null) {
            return false;
        }
        curso.setNombre(nombre);
        curso.setDescripcion(descripcion);
        curso.setDuracionMeses(duracionMeses);
        curso.setValorMensual(valorMensual);
        curso.setEstado(estado);
        return true;
    }

    public boolean eliminarCurso(String codigo) {
        Curso curso = buscarCurso(codigo);
        if (curso == null) {
            return false;
        }
        for (Matricula m : matriculas) {
            if (m.getCurso().equals(curso)) {
                return false;
            }
        }
        return cursos.remove(curso);
    }

    // ---------------------------------------------------------------- Servicios adicionales

    public boolean agregarServicio(ServicioAdicional servicio) {
        boolean centinela = false;
        if (!verificarServicio(servicio.getCodigo())) {
            servicios.add(servicio);
            centinela = true;
        }
        return centinela;
    }

    public boolean verificarServicio(String codigo) {
        return buscarServicio(codigo) != null;
    }

    public ServicioAdicional buscarServicio(String codigo) {
        for (ServicioAdicional s : servicios) {
            if (s.getCodigo().equals(codigo)) {
                return s;
            }
        }
        return null;
    }

    public boolean actualizarServicio(String codigo, ServicioAdicional actualizado) {
        ServicioAdicional servicio = buscarServicio(codigo);
        if (servicio == null) {
            return false;
        }
        servicio.setNombre(actualizado.getNombre());
        servicio.setDescripcion(actualizado.getDescripcion());
        servicio.setPrecio(actualizado.getPrecio());
        servicio.setDisponible(actualizado.isDisponible());
        return true;
    }

    public boolean eliminarServicio(String codigo) {
        ServicioAdicional servicio = buscarServicio(codigo);
        if (servicio == null) {
            return false;
        }
        for (Matricula m : matriculas) {
            if (m.getServicios().contains(servicio)) {
                return false;
            }
        }
        return servicios.remove(servicio);
    }

    public Collection<ServicioAdicional> obtenerServiciosDisponibles() {
        Collection<ServicioAdicional> resultado = new LinkedList<>();
        for (ServicioAdicional s : servicios) {
            if (s.isDisponible()) {
                resultado.add(s);
            }
        }
        return resultado;
    }

    // ---------------------------------------------------------------- Matrículas

    public boolean agregarMatricula(Matricula matricula) {
        boolean centinela = false;
        if (!verificarMatricula(matricula.getCodigo())) {
            matriculas.add(matricula);
            centinela = true;
        }
        return centinela;
    }

    public boolean verificarMatricula(String codigo) {
        return buscarMatricula(codigo) != null;
    }

    public Matricula buscarMatricula(String codigo) {
        for (Matricula m : matriculas) {
            if (m.getCodigo().equals(codigo)) {
                return m;
            }
        }
        return null;
    }

    public boolean eliminarMatricula(String codigo) {
        Matricula m = buscarMatricula(codigo);
        return m != null && matriculas.remove(m);
    }

    public boolean agregarServicioAMatricula(String codigoMatricula, String codigoServicio) {
        Matricula matricula = buscarMatricula(codigoMatricula);
        ServicioAdicional servicio = buscarServicio(codigoServicio);
        if (matricula == null || servicio == null) {
            return false;
        }
        matricula.agregarServicio(servicio);
        return true;
    }

    public Collection<Matricula> obtenerMatriculasEstudiante(String documento) {
        Collection<Matricula> resultado = new LinkedList<>();
        for (Matricula m : matriculas) {
            if (m.getEstudiante().getDocumento().equals(documento)) {
                resultado.add(m);
            }
        }
        return resultado;
    }

    /**
     * Ingresos generados por las matrículas realizadas en el periodo [fechaInicial, fechaFinal].
     * Recorre las matrículas, identifica las que están dentro del periodo y acumula su valor total.
     */
    public double calcularIngresos(LocalDate fechaInicial, LocalDate fechaFinal) {
        if (fechaInicial == null || fechaFinal == null) {
            throw new IllegalArgumentException("Debe indicar la fecha inicial y la fecha final.");
        }
        if (fechaInicial.isAfter(fechaFinal)) {
            throw new IllegalArgumentException("La fecha inicial no puede ser posterior a la fecha final.");
        }
        double total = 0;
        for (Matricula matricula : matriculas) {
            if (matricula.estaEnPeriodo(fechaInicial, fechaFinal)) {
                total += matricula.calcularValorTotal();
            }
        }
        return total;
    }

    public Collection<Matricula> obtenerMatriculasPeriodo(LocalDate fechaInicial, LocalDate fechaFinal) {
        Collection<Matricula> resultado = new LinkedList<>();
        for (Matricula m : matriculas) {
            if (m.estaEnPeriodo(fechaInicial, fechaFinal)) {
                resultado.add(m);
            }
        }
        return resultado;
    }

    // ---------------------------------------------------------------- Getters / setters

    public Collection<Estudiante> getEstudiantes() { return estudiantes; }
    public Collection<Profesor> getProfesores() { return profesores; }
    public Collection<Curso> getCursos() { return cursos; }
    public Collection<ServicioAdicional> getServicios() { return servicios; }
    public Collection<Matricula> getMatriculas() { return matriculas; }

    public String getNombreComercial() { return nombreComercial; }
    public String getNit() { return nit; }
    public String getDireccion() { return direccion; }
    public String getTelefono() { return telefono; }
    public String getCorreo() { return correo; }
    public String getPaginaWeb() { return paginaWeb; }

    public void setNombreComercial(String v) { nombreComercial = v; }
    public void setNit(String v) { nit = v; }
    public void setDireccion(String v) { direccion = v; }
    public void setTelefono(String v) { telefono = v; }
    public void setCorreo(String v) { correo = v; }
    public void setPaginaWeb(String v) { paginaWeb = v; }
}
