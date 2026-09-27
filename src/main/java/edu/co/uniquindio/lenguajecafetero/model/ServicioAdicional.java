package edu.co.uniquindio.lenguajecafetero.model;

public class ServicioAdicional implements  Cloneable{
    private String codigo;
    private String nombre;
    private String descripcion;
    private double precio;
    private boolean disponible;

    public ServicioAdicional(String codigo, String nombre, String descripcion, double precio, boolean disponible) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.disponible = disponible;
    }
    // implementamos el patrón prototype que nos perimite clonar el servicio a una matricula
    @Override
    public  ServicioAdicional clone (){
        try {
            return (ServicioAdicional) super.clone();
        }
        catch (CloneNotSupportedException e){
            return new ServicioAdicional(this.codigo, this.nombre, this.descripcion, this.precio, this.disponible);
        }
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public boolean isDisponible() {
        return disponible;
    }

    @Override
    public String toString(){
        return nombre + "(" + precio +")";
    }
}
