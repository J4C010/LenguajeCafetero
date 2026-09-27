package edu.co.uniquindio.lenguajecafetero.controller;

import edu.co.uniquindio.lenguajecafetero.model.Academia;
import edu.co.uniquindio.lenguajecafetero.model.ServicioAdicional;

import java.util.Collection;

public class ServicioAdicionalController {

    private final Academia academia;

    public ServicioAdicionalController(Academia academia) {
        this.academia = academia;
    }

    public boolean crearServicio(ServicioAdicional servicio) {
        return academia.agregarServicio(servicio);
    }

    public Collection<ServicioAdicional> obtenerListaServicios() {
        return academia.getServicios();
    }

    public boolean actualizarServicio(String codigo, ServicioAdicional servicio) {
        return academia.actualizarServicio(codigo, servicio);
    }

    public boolean eliminarServicio(String codigo) {
        return academia.eliminarServicio(codigo);
    }
}
