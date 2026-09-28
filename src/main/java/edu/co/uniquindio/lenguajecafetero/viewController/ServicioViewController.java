package edu.co.uniquindio.lenguajecafetero.viewController;

import edu.co.uniquindio.lenguajecafetero.App;
import edu.co.uniquindio.lenguajecafetero.controller.ServicioAdicionalController;
import edu.co.uniquindio.lenguajecafetero.model.ServicioAdicional;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ServicioViewController {

    private App app;
    private ServicioAdicionalController servicioController;
    private final ObservableList<ServicioAdicional> listServicios = FXCollections.observableArrayList();
    private ServicioAdicional selectedServicio;

    @FXML private TextField txtCodigo;
    @FXML private TextField txtNombre;
    @FXML private TextField txtDescripcion;
    @FXML private TextField txtPrecio;
    @FXML private CheckBox chkDisponible;

    @FXML private TableView<ServicioAdicional> tblListServicio;
    @FXML private TableColumn<ServicioAdicional, String> tbcCodigo;
    @FXML private TableColumn<ServicioAdicional, String> tbcNombre;
    @FXML private TableColumn<ServicioAdicional, String> tbcDescripcion;
    @FXML private TableColumn<ServicioAdicional, String> tbcPrecio;
    @FXML private TableColumn<ServicioAdicional, String> tbcDisponible;

    @FXML
    void initialize() {
        servicioController = new ServicioAdicionalController(App.academia);
        tbcCodigo.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCodigo()));
        tbcNombre.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNombre()));
        tbcDescripcion.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDescripcion()));
        tbcPrecio.setCellValueFactory(c -> new SimpleStringProperty(UtilidadesVista.formatoMoneda(c.getValue().getPrecio())));
        tbcDisponible.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().isDisponible() ? "Sí" : "No"));
        listServicios.setAll(servicioController.obtenerListaServicios());
        tblListServicio.setItems(listServicios);
        tblListServicio.getSelectionModel().selectedItemProperty().addListener((obs, o, n) -> {
            selectedServicio = n;
            if (n != null) {
                txtCodigo.setText(n.getCodigo());
                txtNombre.setText(n.getNombre());
                txtDescripcion.setText(n.getDescripcion());
                txtPrecio.setText(String.valueOf(n.getPrecio()));
                chkDisponible.setSelected(n.isDisponible());
                txtCodigo.setDisable(true);
            }
        });
    }

    @FXML
    void onAgregarServicio() {
        try {
            ServicioAdicional servicio = buildServicio();
            if (servicioController.crearServicio(servicio)) {
                listServicios.add(servicio);
                limpiarCampos();
            } else {
                UtilidadesVista.mostrarError("Ya existe un servicio con ese código.");
            }
        } catch (IllegalArgumentException e) {
            UtilidadesVista.mostrarError(e.getMessage());
        }
    }

    @FXML
    void onActualizarServicio() {
        if (selectedServicio == null) {
            UtilidadesVista.mostrarError("Seleccione un servicio de la tabla.");
            return;
        }
        try {
            if (servicioController.actualizarServicio(selectedServicio.getCodigo(), buildServicio())) {
                tblListServicio.refresh();
                onLimpiar();
            }
        } catch (IllegalArgumentException e) {
            UtilidadesVista.mostrarError(e.getMessage());
        }
    }

    @FXML
    void onEliminar() {
        if (selectedServicio == null) {
            UtilidadesVista.mostrarError("Seleccione un servicio de la tabla.");
            return;
        }
        if (servicioController.eliminarServicio(selectedServicio.getCodigo())) {
            listServicios.remove(selectedServicio);
            onLimpiar();
        } else {
            UtilidadesVista.mostrarError("No se puede eliminar: el servicio está asociado a matrículas.");
        }
    }

    @FXML
    void onLimpiar() {
        tblListServicio.getSelectionModel().clearSelection();
        limpiarCampos();
    }

    @FXML
    void onVolver() {
        app.openViewPrincipal();
    }

    private ServicioAdicional buildServicio() {
        return new ServicioAdicional(txtCodigo.getText(), txtNombre.getText(), txtDescripcion.getText(),
                UtilidadesVista.leerDecimal(txtPrecio.getText(), "El precio"), chkDisponible.isSelected());
    }

    private void limpiarCampos() {
        txtCodigo.clear();
        txtNombre.clear();
        txtDescripcion.clear();
        txtPrecio.clear();
        chkDisponible.setSelected(true);
        txtCodigo.setDisable(false);
    }

    public void setApp(App app) {
        this.app = app;
    }
}
