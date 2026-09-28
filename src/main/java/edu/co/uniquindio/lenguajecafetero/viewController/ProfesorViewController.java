package edu.co.uniquindio.lenguajecafetero.viewController;

import edu.co.uniquindio.lenguajecafetero.App;
import edu.co.uniquindio.lenguajecafetero.controller.ProfesorController;
import edu.co.uniquindio.lenguajecafetero.model.AsignacionProfesor;
import edu.co.uniquindio.lenguajecafetero.model.Idioma;
import edu.co.uniquindio.lenguajecafetero.model.Profesor;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ProfesorViewController {

    private App app;
    private ProfesorController profesorController;
    private final ObservableList<Profesor> listProfesores = FXCollections.observableArrayList();
    private Profesor selectedProfesor;

    @FXML private TextField txtIdentificacion;
    @FXML private TextField txtNombre;
    @FXML private ComboBox<Idioma> cbIdioma;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtTarifa;
    @FXML private ListView<String> lstAsignaciones;

    @FXML private TableView<Profesor> tblListProfesor;
    @FXML private TableColumn<Profesor, String> tbcIdentificacion;
    @FXML private TableColumn<Profesor, String> tbcNombre;
    @FXML private TableColumn<Profesor, String> tbcIdioma;
    @FXML private TableColumn<Profesor, String> tbcTelefono;
    @FXML private TableColumn<Profesor, String> tbcTarifa;

    @FXML
    void initialize() {
        profesorController = new ProfesorController(App.academia);
        cbIdioma.setItems(FXCollections.observableArrayList(Idioma.values()));
        tbcIdentificacion.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getIdentificacion()));
        tbcNombre.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNombre()));
        tbcIdioma.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getIdioma().toString()));
        tbcTelefono.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getTelefono()));
        tbcTarifa.setCellValueFactory(c -> new SimpleStringProperty(
                UtilidadesVista.formatoMoneda(c.getValue().getTarifaPorSesion())));
        listProfesores.setAll(profesorController.obtenerListaProfesores());
        tblListProfesor.setItems(listProfesores);
        tblListProfesor.getSelectionModel().selectedItemProperty().addListener((obs, o, n) -> {
            selectedProfesor = n;
            mostrarInformacionProfesor(n);
        });
    }

    private void mostrarInformacionProfesor(Profesor p) {
        lstAsignaciones.getItems().clear();
        if (p != null) {
            txtIdentificacion.setText(p.getIdentificacion());
            txtNombre.setText(p.getNombre());
            cbIdioma.setValue(p.getIdioma());
            txtTelefono.setText(p.getTelefono());
            txtTarifa.setText(String.valueOf(p.getTarifaPorSesion()));
            txtIdentificacion.setDisable(true);
            for (AsignacionProfesor a : profesorController.obtenerAsignaciones(p)) {
                lstAsignaciones.getItems().add(a.getMatricula().getCodigo() + " · Estudiante: "
                        + a.getEstudiante().getNombre() + " · Curso: " + a.getCurso().getNombre());
            }
        }
    }

    @FXML
    void onAgregarProfesor() {
        try {
            Profesor profesor = buildProfesor();
            if (profesorController.crearProfesor(profesor)) {
                listProfesores.add(profesor);
                limpiarCampos();
            } else {
                UtilidadesVista.mostrarError("Ya existe un profesor con esa identificación.");
            }
        } catch (IllegalArgumentException | NullPointerException e) {
            UtilidadesVista.mostrarError(e.getMessage());
        }
    }

    @FXML
    void onActualizarProfesor() {
        if (selectedProfesor == null) {
            UtilidadesVista.mostrarError("Seleccione un profesor de la tabla.");
            return;
        }
        try {
            if (profesorController.actualizarProfesor(selectedProfesor.getIdentificacion(), buildProfesor())) {
                tblListProfesor.refresh();
                onLimpiar();
            }
        } catch (IllegalArgumentException | NullPointerException e) {
            UtilidadesVista.mostrarError(e.getMessage());
        }
    }

    @FXML
    void onEliminar() {
        if (selectedProfesor == null) {
            UtilidadesVista.mostrarError("Seleccione un profesor de la tabla.");
            return;
        }
        if (profesorController.eliminarProfesor(selectedProfesor.getIdentificacion())) {
            listProfesores.remove(selectedProfesor);
            onLimpiar();
        } else {
            UtilidadesVista.mostrarError("No se puede eliminar: el profesor tiene estudiantes asignados.");
        }
    }

    @FXML
    void onLimpiar() {
        tblListProfesor.getSelectionModel().clearSelection();
        limpiarCampos();
    }

    @FXML
    void onVolver() {
        app.openViewPrincipal();
    }

    private Profesor buildProfesor() {
        return new Profesor(txtIdentificacion.getText(), txtNombre.getText(), cbIdioma.getValue(),
                txtTelefono.getText(), UtilidadesVista.leerDecimal(txtTarifa.getText(), "La tarifa por sesión"));
    }

    private void limpiarCampos() {
        txtIdentificacion.clear();
        txtNombre.clear();
        cbIdioma.setValue(null);
        txtTelefono.clear();
        txtTarifa.clear();
        lstAsignaciones.getItems().clear();
        txtIdentificacion.setDisable(false);
    }

    public void setApp(App app) {
        this.app = app;
    }
}
