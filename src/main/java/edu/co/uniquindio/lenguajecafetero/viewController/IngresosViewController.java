package edu.co.uniquindio.lenguajecafetero.viewController;

import edu.co.uniquindio.lenguajecafetero.App;
import edu.co.uniquindio.lenguajecafetero.controller.MatriculaController;
import edu.co.uniquindio.lenguajecafetero.model.Matricula;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.time.LocalDate;

public class IngresosViewController {

    private App app;
    private MatriculaController matriculaController;

    @FXML private DatePicker dpFechaInicial;
    @FXML private DatePicker dpFechaFinal;
    @FXML private Label lblTotal;

    @FXML private TableView<Matricula> tblMatriculas;
    @FXML private TableColumn<Matricula, String> tbcCodigo;
    @FXML private TableColumn<Matricula, String> tbcFecha;
    @FXML private TableColumn<Matricula, String> tbcEstudiante;
    @FXML private TableColumn<Matricula, String> tbcCurso;
    @FXML private TableColumn<Matricula, String> tbcTotal;

    @FXML
    void initialize() {
        matriculaController = new MatriculaController(App.academia);
        LocalDate hoy = LocalDate.now();
        dpFechaInicial.setValue(hoy.withDayOfMonth(1));
        dpFechaFinal.setValue(hoy);
        tbcCodigo.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCodigo()));
        tbcFecha.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getFecha().toString()));
        tbcEstudiante.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getEstudiante().getNombre()));
        tbcCurso.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCurso().getNombre()));
        tbcTotal.setCellValueFactory(c -> new SimpleStringProperty(
                UtilidadesVista.formatoMoneda(c.getValue().calcularValorTotal())));
    }

    @FXML
    void onCalcularIngresos() {
        try {
            LocalDate inicio = dpFechaInicial.getValue();
            LocalDate fin = dpFechaFinal.getValue();
            double total = matriculaController.calcularIngresos(inicio, fin);
            tblMatriculas.setItems(FXCollections.observableArrayList(
                    matriculaController.obtenerMatriculasPeriodo(inicio, fin)));
            lblTotal.setText("Ingresos del " + inicio + " al " + fin + ": " + UtilidadesVista.formatoMoneda(total));
        } catch (IllegalArgumentException e) {
            UtilidadesVista.mostrarError(e.getMessage());
        }
    }

    @FXML
    void onVolver() {
        app.openViewPrincipal();
    }

    public void setApp(App app) {
        this.app = app;
    }
}
