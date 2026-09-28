package edu.co.uniquindio.lenguajecafetero.viewController;

import edu.co.uniquindio.lenguajecafetero.App;
import edu.co.uniquindio.lenguajecafetero.controller.MatriculaController;
import edu.co.uniquindio.lenguajecafetero.model.Curso;
import edu.co.uniquindio.lenguajecafetero.model.DescuentoPorDuracion;
import edu.co.uniquindio.lenguajecafetero.model.DescuentoPorcentual;
import edu.co.uniquindio.lenguajecafetero.model.Estudiante;
import edu.co.uniquindio.lenguajecafetero.model.Matricula;
import edu.co.uniquindio.lenguajecafetero.model.PoliticaDescuento;
import edu.co.uniquindio.lenguajecafetero.model.Profesor;
import edu.co.uniquindio.lenguajecafetero.model.ServicioAdicional;
import edu.co.uniquindio.lenguajecafetero.model.SinDescuento;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MatriculaViewController {

    private App app;
    private MatriculaController matriculaController;
    private final ObservableList<Matricula> listMatriculas = FXCollections.observableArrayList();
    private Matricula selectedMatricula;

    @FXML private TextField txtCodigo;
    @FXML private DatePicker dpFecha;
    @FXML private ComboBox<Estudiante> cbEstudiante;
    @FXML private ComboBox<Curso> cbCurso;
    @FXML private TextField txtMeses;
    @FXML private ComboBox<Profesor> cbProfesor;
    @FXML private ComboBox<PoliticaDescuento> cbDescuento;
    @FXML private ListView<ServicioAdicional> lstServicios;
    @FXML private Label lblValor;

    @FXML private TableView<Matricula> tblListMatricula;
    @FXML private TableColumn<Matricula, String> tbcCodigo;
    @FXML private TableColumn<Matricula, String> tbcFecha;
    @FXML private TableColumn<Matricula, String> tbcEstudiante;
    @FXML private TableColumn<Matricula, String> tbcCurso;
    @FXML private TableColumn<Matricula, String> tbcMeses;
    @FXML private TableColumn<Matricula, String> tbcProfesor;
    @FXML private TableColumn<Matricula, String> tbcServicios;
    @FXML private TableColumn<Matricula, String> tbcDescuento;
    @FXML private TableColumn<Matricula, String> tbcTotal;

    @FXML
    void initialize() {
        matriculaController = new MatriculaController(App.academia);
        dpFecha.setValue(LocalDate.now());
        cbEstudiante.setItems(FXCollections.observableArrayList(matriculaController.obtenerEstudiantes()));
        cbCurso.setItems(FXCollections.observableArrayList(matriculaController.obtenerCursos()));
        cbDescuento.setItems(FXCollections.observableArrayList(
                new SinDescuento(), new DescuentoPorDuracion(),
                new DescuentoPorcentual(5), new DescuentoPorcentual(10), new DescuentoPorcentual(15)));
        cbDescuento.getSelectionModel().selectFirst();
        lstServicios.setItems(FXCollections.observableArrayList(matriculaController.obtenerServiciosDisponibles()));
        lstServicios.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        cbProfesor.setDisable(true);
        cbCurso.valueProperty().addListener((obs, o, curso) -> actualizarProfesores(curso));

        tbcCodigo.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCodigo()));
        tbcFecha.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getFecha().toString()));
        tbcEstudiante.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getEstudiante().getNombre()));
        tbcCurso.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCurso().getNombre()
                + " (" + c.getValue().getCurso().getTipo() + ")"));
        tbcMeses.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getMesesContratados())));
        tbcProfesor.setCellValueFactory(c -> new SimpleStringProperty(
                c.getValue().getProfesor() == null ? "—" : c.getValue().getProfesor().getNombre()));
        tbcServicios.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getServicios().stream()
                .map(ServicioAdicional::getNombre).collect(Collectors.joining(", "))));
        tbcDescuento.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDescuento().getDescripcion()));
        tbcTotal.setCellValueFactory(c -> new SimpleStringProperty(
                UtilidadesVista.formatoMoneda(c.getValue().calcularValorTotal())));

        listMatriculas.setAll(matriculaController.obtenerListaMatriculas());
        tblListMatricula.setItems(listMatriculas);
        tblListMatricula.getSelectionModel().selectedItemProperty().addListener((obs, o, n) -> {
            selectedMatricula = n;
            if (n != null) mostrarDetalle(n);
        });
    }

    /** Solo los cursos personalizados llevan profesor, y debe enseñar el idioma del curso. */
    private void actualizarProfesores(Curso curso) {
        cbProfesor.getItems().clear();
        cbProfesor.setValue(null);
        boolean requiere = curso != null && curso.requiereProfesor();
        cbProfesor.setDisable(!requiere);
        if (requiere) {
            for (Profesor p : matriculaController.obtenerProfesores()) {
                if (p.ensena(curso.getIdioma())) {
                    cbProfesor.getItems().add(p);
                }
            }
        }
    }

    private Matricula buildMatricula() {
        List<ServicioAdicional> servicios = new ArrayList<>(lstServicios.getSelectionModel().getSelectedItems());
        return Matricula.builder()
                .codigo(txtCodigo.getText())
                .fecha(dpFecha.getValue())
                .estudiante(cbEstudiante.getValue())
                .curso(cbCurso.getValue())
                .mesesContratados(UtilidadesVista.leerEntero(txtMeses.getText(), "Los meses contratados"))
                .profesor(cbProfesor.getValue())
                .descuento(cbDescuento.getValue())
                .servicios(servicios)
                .build();
    }

    private void mostrarDetalle(Matricula m) {
        lblValor.setText("Curso: " + UtilidadesVista.formatoMoneda(m.calcularValorCurso())
                + "   Servicios: " + UtilidadesVista.formatoMoneda(m.calcularValorServicios())
                + "   Descuento: -" + UtilidadesVista.formatoMoneda(m.calcularDescuento())
                + "   TOTAL: " + UtilidadesVista.formatoMoneda(m.calcularValorTotal()));
    }

    @FXML
    void onCalcularValor() {
        try {
            mostrarDetalle(buildMatricula());
        } catch (RuntimeException e) {
            UtilidadesVista.mostrarError(e.getMessage());
        }
    }

    @FXML
    void onRegistrarMatricula() {
        try {
            Matricula matricula = buildMatricula();
            if (matriculaController.crearMatricula(matricula)) {
                listMatriculas.add(matricula);
                mostrarDetalle(matricula);
                limpiarCampos();
            } else {
                UtilidadesVista.mostrarError("Ya existe una matrícula con ese código.");
            }
        } catch (RuntimeException e) {
            UtilidadesVista.mostrarError(e.getMessage());
        }
    }

    /** Agrega los servicios seleccionados a la matrícula seleccionada en la tabla. */
    @FXML
    void onAgregarServicios() {
        if (selectedMatricula == null) {
            UtilidadesVista.mostrarError("Seleccione una matrícula de la tabla.");
            return;
        }
        List<ServicioAdicional> seleccionados = new ArrayList<>(lstServicios.getSelectionModel().getSelectedItems());
        if (seleccionados.isEmpty()) {
            UtilidadesVista.mostrarError("Seleccione uno o más servicios de la lista.");
            return;
        }
        try {
            for (ServicioAdicional s : seleccionados) {
                matriculaController.agregarServicio(selectedMatricula.getCodigo(), s.getCodigo());
            }
            tblListMatricula.refresh();
            mostrarDetalle(selectedMatricula);
        } catch (RuntimeException e) {
            UtilidadesVista.mostrarError(e.getMessage());
        }
    }

    @FXML
    void onEliminar() {
        if (selectedMatricula == null) {
            UtilidadesVista.mostrarError("Seleccione una matrícula de la tabla.");
            return;
        }
        if (matriculaController.eliminarMatricula(selectedMatricula.getCodigo())) {
            listMatriculas.remove(selectedMatricula);
            onLimpiar();
        }
    }

    @FXML
    void onLimpiar() {
        tblListMatricula.getSelectionModel().clearSelection();
        limpiarCampos();
        lblValor.setText("");
    }

    @FXML
    void onVolver() {
        app.openViewPrincipal();
    }

    private void limpiarCampos() {
        txtCodigo.clear();
        dpFecha.setValue(LocalDate.now());
        cbEstudiante.setValue(null);
        cbCurso.setValue(null);
        txtMeses.clear();
        cbDescuento.getSelectionModel().selectFirst();
        lstServicios.getSelectionModel().clearSelection();
    }

    public void setApp(App app) {
        this.app = app;
    }
}
