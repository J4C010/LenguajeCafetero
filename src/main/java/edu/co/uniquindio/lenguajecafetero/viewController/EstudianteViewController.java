package edu.co.uniquindio.lenguajecafetero.viewController;

import edu.co.uniquindio.lenguajecafetero.App;
import edu.co.uniquindio.lenguajecafetero.controller.EstudianteController;
import edu.co.uniquindio.lenguajecafetero.model.Estudiante;
import edu.co.uniquindio.lenguajecafetero.model.Matricula;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class EstudianteViewController {

    private App app;
    private EstudianteController estudianteController;
    private final ObservableList<Estudiante> listEstudiantes = FXCollections.observableArrayList();
    private Estudiante selectedEstudiante;

    @FXML private TextField txtDocumento;
    @FXML private TextField txtNombre;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtCorreo;
    @FXML private TextField txtEdad;
    @FXML private DatePicker dpFechaRegistro;
    @FXML private TextField txtBuscarDocumento;
    @FXML private Label lblResultadoBusqueda;

    @FXML private TableView<Estudiante> tblListEstudiante;
    @FXML private TableColumn<Estudiante, String> tbcDocumento;
    @FXML private TableColumn<Estudiante, String> tbcNombre;
    @FXML private TableColumn<Estudiante, String> tbcTelefono;
    @FXML private TableColumn<Estudiante, String> tbcCorreo;
    @FXML private TableColumn<Estudiante, String> tbcEdad;
    @FXML private TableColumn<Estudiante, String> tbcFechaRegistro;

    @FXML
    void initialize() {
        estudianteController = new EstudianteController(App.academia);
        dpFechaRegistro.setValue(LocalDate.now());
        initView();
    }

    private void initView() {
        initDataBinding();
        obtenerEstudiantes();
        tblListEstudiante.setItems(listEstudiantes);
        listenerSelection();
    }

    private void initDataBinding() {
        tbcDocumento.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDocumento()));
        tbcNombre.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNombre()));
        tbcTelefono.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getTelefono()));
        tbcCorreo.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCorreo()));
        tbcEdad.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getEdad())));
        tbcFechaRegistro.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getFechaRegistro().toString()));
    }

    private void obtenerEstudiantes() {
        listEstudiantes.setAll(estudianteController.obtenerListaEstudiantes());
    }

    private void listenerSelection() {
        tblListEstudiante.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            selectedEstudiante = newSel;
            mostrarInformacionEstudiante(selectedEstudiante);
        });
    }

    private void mostrarInformacionEstudiante(Estudiante e) {
        if (e != null) {
            txtDocumento.setText(e.getDocumento());
            txtNombre.setText(e.getNombre());
            txtTelefono.setText(e.getTelefono());
            txtCorreo.setText(e.getCorreo());
            txtEdad.setText(String.valueOf(e.getEdad()));
            dpFechaRegistro.setValue(e.getFechaRegistro());
            txtDocumento.setDisable(true);
        }
    }

    @FXML
    void onAgregarEstudiante() {
        try {
            Estudiante estudiante = buildEstudiante();
            if (estudianteController.crearEstudiante(estudiante)) {
                listEstudiantes.add(estudiante);
                limpiarCampos();
            } else {
                UtilidadesVista.mostrarError("Ya existe un estudiante con ese documento.");
            }
        } catch (IllegalArgumentException e) {
            UtilidadesVista.mostrarError(e.getMessage());
        }
    }

    @FXML
    void onActualizarEstudiante() {
        if (selectedEstudiante == null) {
            UtilidadesVista.mostrarError("Seleccione un estudiante de la tabla.");
            return;
        }
        try {
            if (estudianteController.actualizarEstudiante(selectedEstudiante.getDocumento(), buildEstudiante())) {
                tblListEstudiante.refresh();
                onLimpiar();
            }
        } catch (IllegalArgumentException e) {
            UtilidadesVista.mostrarError(e.getMessage());
        }
    }

    @FXML
    void onEliminar() {
        if (selectedEstudiante == null) {
            UtilidadesVista.mostrarError("Seleccione un estudiante de la tabla.");
            return;
        }
        if (estudianteController.eliminarEstudiante(selectedEstudiante.getDocumento())) {
            listEstudiantes.remove(selectedEstudiante);
            onLimpiar();
        } else {
            UtilidadesVista.mostrarError("No se puede eliminar: el estudiante tiene matrículas registradas.");
        }
    }

    @FXML
    void onBuscarEstudiante() {
        String documento = txtBuscarDocumento.getText() == null ? "" : txtBuscarDocumento.getText().trim();
        Estudiante estudiante = estudianteController.buscarEstudiante(documento);
        if (estudiante == null) {
            lblResultadoBusqueda.setText("No se encontró un estudiante con documento " + documento + ".");
            tblListEstudiante.getSelectionModel().clearSelection();
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(estudiante.getNombre()).append(" · ").append(estudiante.getCorreo())
          .append(" · ").append(estudiante.getEdad()).append(" años · Registrado el ")
          .append(estudiante.getFechaRegistro());
        for (Matricula m : estudianteController.obtenerMatriculasEstudiante(documento)) {
            sb.append("\n  • ").append(m.getCodigo()).append(": ").append(m.getCurso().getNombre())
              .append(" (").append(UtilidadesVista.formatoMoneda(m.calcularValorTotal())).append(")");
        }
        lblResultadoBusqueda.setText(sb.toString());
        tblListEstudiante.getSelectionModel().select(estudiante);
    }

    @FXML
    void onLimpiar() {
        tblListEstudiante.getSelectionModel().clearSelection();
        limpiarCampos();
    }

    @FXML
    void onVolver() {
        app.openViewPrincipal();
    }

    private Estudiante buildEstudiante() {
        return new Estudiante(txtDocumento.getText(), txtNombre.getText(), txtTelefono.getText(),
                txtCorreo.getText(), UtilidadesVista.leerEntero(txtEdad.getText(), "La edad"),
                dpFechaRegistro.getValue());
    }

    private void limpiarCampos() {
        txtDocumento.clear();
        txtNombre.clear();
        txtTelefono.clear();
        txtCorreo.clear();
        txtEdad.clear();
        dpFechaRegistro.setValue(LocalDate.now());
        txtDocumento.setDisable(false);
    }

    public void setApp(App app) {
        this.app = app;
    }
}
