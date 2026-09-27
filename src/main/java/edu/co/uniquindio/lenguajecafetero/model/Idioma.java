package co.edu.uniquindio.lenguajecafetero.model;

public enum Idioma {
    INGLES("Inglés"), FRANCES("Francés"), PORTUGUES("Portugués");

    private final String nombre;

    Idioma(String nombre) { this.nombre = nombre; }

    @Override
    public String toString() { return nombre; }
}
