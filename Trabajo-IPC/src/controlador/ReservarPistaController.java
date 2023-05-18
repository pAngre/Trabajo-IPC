/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlador;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.*;

/**
 * FXML Controller class
 *
 * @author Pablo Angre
 */
public class ReservarPistaController implements Initializable {

    @FXML
    private Button btnAtras;
    @FXML
    private Label slotSelected;
    @FXML
    private Button btnReservar;
    @FXML
    private DatePicker dia;
    @FXML
    private Label labelCol;
    
    private final LocalTime firstSlotStart = LocalTime.of(9, 0);
    private final Duration slotLength = Duration.ofMinutes(60);
    private final LocalTime lastSlotStart = LocalTime.of(21, 0);
    
    private static final PseudoClass SELECTED_PSEUDO_CLASS = PseudoClass.getPseudoClass("selected");
    
    private List<List<TimeSlot>> timeSlots = new ArrayList<>();
    private ObjectProperty<TimeSlot> timeSlotSelected;
    private LocalDate daySelected;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        try {
            Club c = Club.getInstance();
        } catch (ClubDAOException ex) {
            Logger.getLogger(ReservarPistaController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ReservarPistaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    

    @FXML
    private void clickedAtras(ActionEvent event) {
        //CERRAR VENTANA
        
        Stage stage = new Stage();
        stage = (Stage) btnAtras.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void clickedReservar(ActionEvent event) {
    }
    
    
    public class TimeSlot {

        private final LocalDateTime start;
        private final Duration duration;
        protected final Pane view;

        private final BooleanProperty selected = new SimpleBooleanProperty();

        public final BooleanProperty selectedProperty() {
            return selected;
        }

        public final boolean isSelected() {
            return selectedProperty().get();
        }

        public final void setSelected(boolean selected) {
            selectedProperty().set(selected);
        }

        public TimeSlot(LocalDateTime start, Duration duration) {
            this.start = start;
            this.duration = duration;
            view = new Pane();
            view.getStyleClass().add("time-slot");
            // ---------------------------------------------------------------
            // de esta manera cambiamos la apariencia del TimeSlot cuando los seleccionamos
            selectedProperty().addListener((obs, wasSelected, isSelected)
                    -> view.pseudoClassStateChanged(SELECTED_PSEUDO_CLASS, isSelected));

        }
    
}
    
}
