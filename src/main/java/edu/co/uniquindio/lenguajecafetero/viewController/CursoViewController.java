package edu.co.uniquindio.lenguajecafetero.viewController;

import edu.co.uniquindio.lenguajecafetero.App;
import edu.co.uniquindio.lenguajecafetero.controller.CursoController;
import edu.co.uniquindio.lenguajecafetero.model.Beneficio;
import edu.co.uniquindio.lenguajecafetero.model.Curso;
import edu.co.uniquindio.lenguajecafetero.model.CursoIntensivo;
import edu.co.uniquindio.lenguajecafetero.model.CursoPersonalizado;
import edu.co.uniquindio.lenguajecafetero.model.DatosCurso;
import edu.co.uniquindio.lenguajecafetero.model.EstadoCurso;
import edu.co.uniquindio.lenguajecafetero.model.Idioma;
import edu.co.uniquindio.lenguajecafetero.model.NivelReferencia;
import edu.co.uniquindio.lenguajecafetero.model.TipoCurso;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.stream.Collectors;

public class CursoViewController {

    private App app;
    private CursoController cursoController;
    private final ObservableList<Curso> listCursos = FXCollections.observableArrayList();
    private Curso selectedCurso;

    @FXML private ComboBox<TipoCurso> cbTipo;
    @FXML private TextField txtCodigo;
    @FXML private TextField txtNombre;
    @FXML private ComboBox<Idioma> cbIdioma;
    @FXML private TextField txtDescripcion;
    @FXML private TextField txtDuracion;
    @FXML private TextField txtValorMensual;
    @FXML private ComboBox<EstadoCurso> cbEstado;
    @FXML private CheckBox chkPlataforma;
    @FXML private CheckBox chkMaterial;
    @FXML private CheckBox chkClub;
    // Intensivo
    @FXML private TextField txtHorasSemanales;
    // Personalizado
    @FXML private TextField txtSesiones;
    @FXML private ComboBox<NivelReferencia> cbNivel;
    @FXML private TextField txtObjetivos;

    @FXML private TableView<Curso> tblListCurso;
    @FXML private TableColumn<Curso, String> tbcCodigo;
    @FXML private TableColumn<Curso, String> tbcNombre;
    @FXML private TableColumn<Curso, String> tbcTipo;
    @FXML private TableColumn<Curso, String> tbcIdioma;
    @FXML private TableColumn<Curso, String> tbcDuracion;
    @FXML private TableColumn<Curso, String> tbcValor;
    @FXML private TableColumn<Curso, String> tbcEstado;
    @FXML private TableColumn<Curso, String> tbcBeneficios;

    @FXML
    void initialize() {
        cursoController = new CursoController(App.academia);
        cbTipo.setItems(FXCollections.observableArrayList(TipoCurso.values()));
        cbIdioma.setItems(FXCollections.observableArrayList(Idioma.values()));
        cbEstado.setItems(FXCollections.observableArrayList(EstadoCurso.values()));
        cbNivel.setItems(FXCollections.observableArrayList(NivelReferencia.values()));
        cbEstado.setValue(EstadoCurso.ACTIVO);
        cbTipo.valueProperty().addListener((obs, o, n) -> actualizarCamposPorTipo(n));
        actualizarCamposPorTipo(null);

        tbcCodigo.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCodigo()));
        tbcNombre.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNombre()));
        tbcTipo.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getTipo().toString()));
        tbcIdioma.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getIdioma().toString()));
        tbcDuracion.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDuracionMeses() + " meses"));
        tbcValor.setCellValueFactory(c -> new SimpleStringProperty(UtilidadesVista.formatoMoneda(c.getValue().getValorMensual())));
        tbcEstado.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getEstado().toString()));
        tbcBeneficios.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getBeneficios().stream()
                .map(Beneficio::toString).collect(Collectors.joining(", "))));

        listCursos.setAll(cursoController.obtenerListaCursos());
        tblListCurso.setItems(listCursos);
        tblListCurso.getSelectionModel().selectedItemProperty().addListener((obs, o, n) -> {
            selectedCurso = n;
            mostrarInformacionCurso(n);
        });
    }

    /** Habilita solo los campos propios del tipo de curso seleccionado. */
    private void actualizarCamposPorTipo(TipoCurso tipo) {
        txtHorasSemanales.setDisable(tipo != TipoCurso.INTENSIVO);
        boolean personalizado = tipo == TipoCurso.PERSONALIZADO;
        txtSesiones.setDisable(!personalizado);
        cbNivel.setDisable(!personalizado);
        txtObjetivos.setDisable(!personalizado);
    }

    private void mostrarInformacionCurso(Curso c) {
        if (c == null) return;
        cbTipo.setValue(c.getTipo());
        txtCodigo.setText(c.getCodigo());
        txtNombre.setText(c.getNombre());
        cbIdioma.setValue(c.getIdioma());
        txtDescripcion.setText(c.getDescripcion());
        txtDuracion.setText(String.valueOf(c.getDuracionMeses()));
        txtValorMensual.setText(String.valueOf(c.getValorMensual()));
        cbEstado.setValue(c.getEstado());
        chkPlataforma.setSelected(c.getBeneficios().contains(Beneficio.PLATAFORMA_VIRTUAL));
        chkMaterial.setSelected(c.getBeneficios().contains(Beneficio.MATERIAL_DIDACTICO));
        chkClub.setSelected(c.getBeneficios().contains(Beneficio.CLUB_CONVERSACION));
        txtHorasSemanales.clear();
        txtSesiones.clear();
        txtObjetivos.clear();
        cbNivel.setValue(null);
        if (c instanceof CursoIntensivo intensivo) {
            txtHorasSemanales.setText(String.valueOf(intensivo.getHorasSemanales()));
        } else if (c instanceof CursoPersonalizado p) {
            txtSesiones.setText(String.valueOf(p.getCantidadSesiones()));
            cbNivel.setValue(p.getNivelRequerido());
            txtObjetivos.setText(p.getObjetivosEstudiante());
        }
        // El tipo, el código y el idioma no se modifican después de creado el curso
        cbTipo.setDisable(true);
        txtCodigo.setDisable(true);
        cbIdioma.setDisable(true);
    }

    @FXML
    void onAgregarCurso() {
        try {
            if (cbTipo.getValue() == null) {
                throw new IllegalArgumentException("Seleccione el tipo de curso.");
            }
            DatosCurso.Builder datos = DatosCurso.builder()
                    .codigo(txtCodigo.getText())
                    .nombre(txtNombre.getText())
                    .idioma(cbIdioma.getValue())
                    .descripcion(txtDescripcion.getText())
                    .duracionMeses(UtilidadesVista.leerEntero(txtDuracion.getText(), "La duración"))
                    .valorMensual(UtilidadesVista.leerDecimal(txtValorMensual.getText(), "El valor mensual"))
                    .estado(cbEstado.getValue());
            if (chkPlataforma.isSelected()) datos.beneficio(Beneficio.PLATAFORMA_VIRTUAL);
            if (chkMaterial.isSelected()) datos.beneficio(Beneficio.MATERIAL_DIDACTICO);
            if (chkClub.isSelected()) datos.beneficio(Beneficio.CLUB_CONVERSACION);
            if (cbTipo.getValue() == TipoCurso.INTENSIVO) {
                datos.horasSemanales(UtilidadesVista.leerEntero(txtHorasSemanales.getText(), "Las horas semanales"));
            }
            if (cbTipo.getValue() == TipoCurso.PERSONALIZADO) {
                datos.cantidadSesiones(UtilidadesVista.leerEntero(txtSesiones.getText(), "La cantidad de sesiones"))
                     .nivelRequerido(cbNivel.getValue())
                     .objetivos(txtObjetivos.getText());
            }
            if (cursoController.crearCurso(cbTipo.getValue(), datos.build())) {
                listCursos.setAll(cursoController.obtenerListaCursos());
                onLimpiar();
            } else {
                UtilidadesVista.mostrarError("Ya existe un curso con ese código.");
            }
        } catch (IllegalArgumentException | NullPointerException e) {
            UtilidadesVista.mostrarError(e.getMessage());
        }
    }

    @FXML
    void onActualizarCurso() {
        if (selectedCurso == null) {
            UtilidadesVista.mostrarError("Seleccione un curso de la tabla.");
            return;
        }
        try {
            cursoController.actualizarCurso(selectedCurso.getCodigo(), txtNombre.getText(), txtDescripcion.getText(),
                    UtilidadesVista.leerEntero(txtDuracion.getText(), "La duración"),
                    UtilidadesVista.leerDecimal(txtValorMensual.getText(), "El valor mensual"),
                    cbEstado.getValue());
            tblListCurso.refresh();
            onLimpiar();
        } catch (IllegalArgumentException | NullPointerException e) {
            UtilidadesVista.mostrarError(e.getMessage());
        }
    }

    @FXML
    void onEliminar() {
        if (selectedCurso == null) {
            UtilidadesVista.mostrarError("Seleccione un curso de la tabla.");
            return;
        }
        if (cursoController.eliminarCurso(selectedCurso.getCodigo())) {
            listCursos.remove(selectedCurso);
            onLimpiar();
        } else {
            UtilidadesVista.mostrarError("No se puede eliminar: el curso tiene matrículas. Puede cambiar su estado.");
        }
    }

    @FXML
    void onLimpiar() {
        tblListCurso.getSelectionModel().clearSelection();
        selectedCurso = null;
        cbTipo.setDisable(false);
        txtCodigo.setDisable(false);
        cbIdioma.setDisable(false);
        cbTipo.setValue(null);
        txtCodigo.clear();
        txtNombre.clear();
        cbIdioma.setValue(null);
        txtDescripcion.clear();
        txtDuracion.clear();
        txtValorMensual.clear();
        cbEstado.setValue(EstadoCurso.ACTIVO);
        chkPlataforma.setSelected(false);
        chkMaterial.setSelected(false);
        chkClub.setSelected(false);
        txtHorasSemanales.clear();
        txtSesiones.clear();
        cbNivel.setValue(null);
        txtObjetivos.clear();
    }

    @FXML
    void onVolver() {
        app.openViewPrincipal();
    }

    public void setApp(App app) {
        this.app = app;
    }
}
