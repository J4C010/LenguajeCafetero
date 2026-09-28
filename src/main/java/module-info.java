module edu.co.uniquindio.lenguajecafetero {
    requires javafx.controls;
    requires javafx.fxml;

    opens edu.co.uniquindio.lenguajecafetero to javafx.fxml;
    exports edu.co.uniquindio.lenguajecafetero;
    exports edu.co.uniquindio.lenguajecafetero.viewController;
    opens edu.co.uniquindio.lenguajecafetero.viewController to javafx.fxml;
    exports edu.co.uniquindio.lenguajecafetero.controller;
    exports edu.co.uniquindio.lenguajecafetero.model;
}
