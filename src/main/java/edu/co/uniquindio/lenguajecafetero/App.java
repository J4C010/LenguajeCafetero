package edu.co.uniquindio.lenguajecafetero;

import edu.co.uniquindio.lenguajecafetero.model.Academia;
import edu.co.uniquindio.lenguajecafetero.model.Beneficio;
import edu.co.uniquindio.lenguajecafetero.model.Curso;
import edu.co.uniquindio.lenguajecafetero.model.DatosCurso;
import edu.co.uniquindio.lenguajecafetero.model.DescuentoPorDuracion;
import edu.co.uniquindio.lenguajecafetero.model.Estudiante;
import edu.co.uniquindio.lenguajecafetero.model.FabricaCursos;
import edu.co.uniquindio.lenguajecafetero.model.Idioma;
import edu.co.uniquindio.lenguajecafetero.model.Matricula;
import edu.co.uniquindio.lenguajecafetero.model.NivelReferencia;
import edu.co.uniquindio.lenguajecafetero.model.Profesor;
import edu.co.uniquindio.lenguajecafetero.model.ServicioAdicional;
import edu.co.uniquindio.lenguajecafetero.model.TipoCurso;
import edu.co.uniquindio.lenguajecafetero.viewController.CursoViewController;
import edu.co.uniquindio.lenguajecafetero.viewController.EstudianteViewController;
import edu.co.uniquindio.lenguajecafetero.viewController.IngresosViewController;
import edu.co.uniquindio.lenguajecafetero.viewController.MatriculaViewController;
import edu.co.uniquindio.lenguajecafetero.viewController.PrimaryController;
import edu.co.uniquindio.lenguajecafetero.viewController.ProfesorViewController;
import edu.co.uniquindio.lenguajecafetero.viewController.ServicioViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

/**
 * JavaFX App
 */
public class App extends Application {

    private Stage primaryStage;

    /** Única instancia de la academia (Singleton) compartida por todas las vistas. */
    public static Academia academia = Academia.getInstance();

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Academia de idiomas " + academia.getNombreComercial());
        inicializarData();
        openViewPrincipal();
    }

    public static void main(String[] args) {
        launch();
    }

    public void openViewPrincipal() {
        PrimaryController controller = cargarVista("primary.fxml", academia.getNombreComercial());
        if (controller != null) controller.setApp(this);
    }

    public void openCrudEstudiante() {
        EstudianteViewController controller = cargarVista("crudEstudiante.fxml", "Gestión de estudiantes");
        if (controller != null) controller.setApp(this);
    }

    public void openCrudProfesor() {
        ProfesorViewController controller = cargarVista("crudProfesor.fxml", "Gestión de profesores");
        if (controller != null) controller.setApp(this);
    }

    public void openCrudCurso() {
        CursoViewController controller = cargarVista("crudCurso.fxml", "Gestión de cursos");
        if (controller != null) controller.setApp(this);
    }

    public void openCrudServicio() {
        ServicioViewController controller = cargarVista("crudServicio.fxml", "Servicios adicionales");
        if (controller != null) controller.setApp(this);
    }

    public void openCrudMatricula() {
        MatriculaViewController controller = cargarVista("crudMatricula.fxml", "Matrículas");
        if (controller != null) controller.setApp(this);
    }

    public void openReporteIngresos() {
        IngresosViewController controller = cargarVista("reporteIngresos.fxml", "Ingresos por periodo");
        if (controller != null) controller.setApp(this);
    }

    /** Carga un FXML (ubicado junto a esta clase en resources), lo muestra y retorna su controlador. */
    private <T> T cargarVista(String fxml, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource(fxml));
            Parent rootLayout = loader.load();
            Scene scene = new Scene(rootLayout);
            primaryStage.setTitle(titulo);
            primaryStage.setScene(scene);
            primaryStage.show();
            return loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // servicios
    public void inicializarData() {
        if (!academia.getEstudiantes().isEmpty()) {
            return;
        }
        Estudiante ana = new Estudiante("1094000001", "Ana María López", "3001112233",
                "ana@correo.com", 20, LocalDate.of(2026, 8, 1));
        Estudiante carlos = new Estudiante("1094000002", "Carlos Gómez", "3104445566",
                "carlos@correo.com", 34, LocalDate.of(2026, 8, 15));
        academia.agregarEstudiante(ana);
        academia.agregarEstudiante(carlos);

        Profesor laura = new Profesor("41900001", "Laura Restrepo", Idioma.INGLES, "3157778899", 60000);
        Profesor pierre = new Profesor("41900002", "Pierre Martin", Idioma.FRANCES, "3169990011", 70000);
        academia.agregarProfesor(laura);
        academia.agregarProfesor(pierre);

        FabricaCursos fabrica = new FabricaCursos();
        Curso inglesRegular = fabrica.crearCurso(TipoCurso.REGULAR, DatosCurso.builder()
                .codigo("ING-R1").nombre("Inglés básico").idioma(Idioma.INGLES)
                .descripcion("Curso regular de inglés A1-A2").duracionMeses(6).valorMensual(250000)
                .build());
        Curso portuguesIntensivo = fabrica.crearCurso(TipoCurso.INTENSIVO, DatosCurso.builder()
                .codigo("POR-I1").nombre("Portugués intensivo").idioma(Idioma.PORTUGUES)
                .descripcion("Curso intensivo de portugués").duracionMeses(3).valorMensual(300000)
                .horasSemanales(12).build());
        Curso inglesPersonalizado = fabrica.crearCurso(TipoCurso.PERSONALIZADO, DatosCurso.builder()
                .codigo("ING-P1").nombre("Inglés para certificación").idioma(Idioma.INGLES)
                .descripcion("Preparación personalizada").duracionMeses(4).valorMensual(200000)
                .cantidadSesiones(8).nivelRequerido(NivelReferencia.B1)
                .objetivos("Presentar examen B2").beneficio(Beneficio.CLUB_CONVERSACION).build());
        academia.agregarCurso(inglesRegular);
        academia.agregarCurso(portuguesIntensivo);
        academia.agregarCurso(inglesPersonalizado);

        ServicioAdicional simulacro = new ServicioAdicional("S01", "Simulacro de certificación",
                "Examen de práctica tipo certificación", 120000, true);
        ServicioAdicional tutoria = new ServicioAdicional("S02", "Tutoría de refuerzo",
                "Sesión individual de refuerzo", 80000, true);
        ServicioAdicional material = new ServicioAdicional("S03", "Material impreso",
                "Libro y cuadernillo", 50000, true);
        ServicioAdicional taller = new ServicioAdicional("S04", "Taller de conversación",
                "Taller grupal de conversación", 40000, false);
        academia.agregarServicio(simulacro);
        academia.agregarServicio(tutoria);
        academia.agregarServicio(material);
        academia.agregarServicio(taller);

        academia.agregarMatricula(Matricula.builder()
                .codigo("M001").fecha(LocalDate.of(2026, 9, 1))
                .estudiante(ana).curso(inglesRegular).mesesContratados(6)
                .descuento(new DescuentoPorDuracion()).servicio(material).build());
        academia.agregarMatricula(Matricula.builder()
                .codigo("M002").fecha(LocalDate.of(2026, 9, 10))
                .estudiante(carlos).curso(inglesPersonalizado).mesesContratados(2)
                .profesor(laura).servicio(simulacro).build());
    }
}
