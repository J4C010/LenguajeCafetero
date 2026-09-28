package edu.co.uniquindio.lenguajecafetero.viewController;

import edu.co.uniquindio.lenguajecafetero.App;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class PrimaryController {

    private App app;

    @FXML
    private Label lblDatosAcademia;

    @FXML
    void initialize() {
        lblDatosAcademia.setText("NIT " + App.academia.getNit() + "  ·  " + App.academia.getDireccion()
                + "\n" + App.academia.getTelefono() + "  ·  " + App.academia.getCorreo()
                + "  ·  " + App.academia.getPaginaWeb());
    }

    @FXML
    void onOpenCrudEstudiante() { app.openCrudEstudiante(); }

    @FXML
    void onOpenCrudProfesor() { app.openCrudProfesor(); }

    @FXML
    void onOpenCrudCurso() { app.openCrudCurso(); }

    @FXML
    void onOpenCrudServicio() { app.openCrudServicio(); }

    @FXML
    void onOpenCrudMatricula() { app.openCrudMatricula(); }

    @FXML
    void onOpenReporteIngresos() { app.openReporteIngresos(); }

    public void setApp(App app) {
        this.app = app;
    }
}
