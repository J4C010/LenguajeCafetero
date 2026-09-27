package co.edu.uniquindio.lenguajecafetero.model;

import java.time.LocalDate;

public class Estudiante extends Persona {

    private String correo;
    private int edad;
    private final LocalDate fechaRegistro;

    public Estudiante(String documento, String nombreCompleto, String telefono,
                      String correo, int edad, LocalDate fechaRegistro) {
        super(documento, nombreCompleto, telefono);
        this.correo = Validaciones.correo(correo);
        this.edad = Validaciones.positivo(edad, "La edad");
        this.fechaRegistro = fechaRegistro != null ? fechaRegistro : LocalDate.now();
    }

    public String getDocumento() { return getIdentificacion(); }
    public String getCorreo() { return correo; }
    public int getEdad() { return edad; }
    public LocalDate getFechaRegistro() { return fechaRegistro; }

    public void setCorreo(String correo) { this.correo = Validaciones.correo(correo); }
    public void setEdad(int edad) { this.edad = Validaciones.positivo(edad, "La edad"); }
}
