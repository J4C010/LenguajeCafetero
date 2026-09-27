package edu.co.uniquindio.lenguajecafetero.model;

public abstract class Curso {
    private String codigo;
    private String nombre;
    private String idioma;
    private String descripcion;
    private int duraMeses;
    private double valorMensual;
    private EstadoCurso estado;

    public Curso(String codigo, String nombre, String idioma, String descripcion, int duraMeses, double valorMensual, EstadoCurso estado) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.idioma = idioma;
        this.descripcion = descripcion;
        this.duraMeses = duraMeses;
        this.valorMensual = valorMensual;
        this.estado = estado;
    }

    public double calcularCostoBase() {
        return duraMeses * valorMensual;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getIdioma() {
        return idioma;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getDuraMeses() {
        return duraMeses;
    }

    public double getValorMensual() {
        return valorMensual;
    }

    public EstadoCurso getEstado() {
        return estado;
    }
}
