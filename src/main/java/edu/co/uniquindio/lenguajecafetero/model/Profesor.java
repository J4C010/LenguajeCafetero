package co.edu.uniquindio.lenguajecafetero.model;


import java.util.Objects;

public class Profesor extends Persona {

    private Idioma idioma;
    private double tarifaPorSesion;

    public Profesor(String identificacion, String nombre, Idioma idioma,
                    String telefono, double tarifaPorSesion) {
        super(identificacion, nombre, telefono);
        this.idioma = Objects.requireNonNull(idioma, "El idioma es obligatorio.");
        this.tarifaPorSesion = Validaciones.noNegativo(tarifaPorSesion, "La tarifa por sesión");
    }

    public boolean ensena(Idioma idioma) { return this.idioma == idioma; }

    public Idioma getIdioma() { return idioma; }
    public double getTarifaPorSesion() { return tarifaPorSesion; }

    public void setIdioma(Idioma idioma) { this.idioma = Objects.requireNonNull(idioma); }
    public void setTarifaPorSesion(double tarifa) {
        this.tarifaPorSesion = Validaciones.noNegativo(tarifa, "La tarifa por sesión");
    }
}
