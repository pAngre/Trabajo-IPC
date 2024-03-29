/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlador;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
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
    private DatePicker dia;
    @FXML
    private ComboBox<String> comboPistas;
    @FXML
    private ListView<LocalTime> listaHoras;
    @FXML
    private ListView<Label> listaDispo;
    @FXML
    private ImageView imgAtras;
    @FXML
    private Label horarioSelected;
    
    //formato de fecha y hora
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:mm");
    DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("EEEE d MMMM");

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        try {
            Club c = Club.getInstance();
            //opciones a elegir de pistas
            List <Court> pistas = c.getCourts();
            String [] listaPistas = new String[pistas.size()];
            for(int i = 0; i < pistas.size(); i++ ){
                listaPistas[i] = pistas.get(i).getName();
            }
            comboPistas.getItems().addAll(listaPistas);
            //inicailizar combobox
            comboPistas.setValue(listaPistas[0]);
        } catch (ClubDAOException ex) {
            Logger.getLogger(ReservarPistaController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ReservarPistaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //inicializar DatePicker al dia actual
        dia.setValue(LocalDate.now());
        //restringir fechas
        dia.setDayCellFactory((DatePicker picker) -> {
            return new  DateCell(){
                @Override
                public void updateItem(LocalDate date, boolean empty){
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                setDisable(empty || date.compareTo(today) < 0); 
                }
            };
        });
        
        //inicializar listas
            listaHoras.setItems(FXCollections.observableArrayList(crearListaHoras(new ArrayList<LocalTime>())));
            listaDispo.setItems(FXCollections.observableArrayList(crearListaDispo(crearListaHoras(new ArrayList<LocalTime>()), comboPistas.getValue(), dia.getValue())));
            
            //seleccionar horarios
            //listaHoras.getSelectionModel().select(0);
            //listaDispo.getSelectionModel().select(0);
            
            syncListas(listaDispo, listaHoras);
            registerHandlers(listaDispo, listaHoras);
            
        //imagen atras
        Image imagen1 = new Image(getClass().getResourceAsStream("/resources/imgAtras.png"));
        imgAtras.setImage(imagen1);
        
        //listener de date picker
        dia.valueProperty().addListener((a, b, c) -> {
            ArrayList<LocalTime> misHoras = crearListaHoras(new ArrayList<LocalTime>());
            listaHoras.setItems(FXCollections.observableArrayList(misHoras));
            listaDispo.setItems(FXCollections.observableArrayList(crearListaDispo(misHoras, comboPistas.getValue(), c)));
        });
        
        //listener de combobox
        comboPistas.valueProperty().addListener((a, b ,c) -> {
            ArrayList<LocalTime> misHoras = crearListaHoras(new ArrayList<LocalTime>());
            listaHoras.setItems(FXCollections.observableArrayList(misHoras));
            listaDispo.setItems(FXCollections.observableArrayList(crearListaDispo(misHoras, c, dia.getValue())));
        });
        
        //set horario selected
        listaHoras.getSelectionModel().selectedItemProperty().addListener((a, b, c) -> {
            if (c == null || listaDispo.getSelectionModel().getSelectedItem() == null) {
                horarioSelected.setText("");
            }
            else{
                horarioSelected.setText(comboPistas.getValue() + " esta " + listaDispo.getSelectionModel().getSelectedItem().getText() +
                        " a las: " + listaHoras.getSelectionModel().getSelectedItem().format(timeFormatter) + " para el: " + dia.getValue().format(dayFormatter));
            }
        });
        
        //listaDispo no seleccionable
        //listaDispo.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener() {
        //            @Override
        //            public void changed(ObservableValue observable, Object oldvalue, Object newValue) {
        //                Platform.runLater(new Runnable() {
        //                    public void run() {
        //                        listaDispo.getSelectionModel().select(-1);
        //                    }
        //                });
        //            }
        //        });
    }
    
    @FXML
    private void clickedAtras(ActionEvent event) {
        //CERRAR VENTANA
        
        Stage stage = new Stage();
        stage = (Stage) btnAtras.getScene().getWindow();
        stage.close();
    }
    
    private ArrayList<LocalTime> crearListaHoras(ArrayList<LocalTime> misdatos){
        Club c = null;
        try {
            c = Club.getInstance();
        } catch (IOException ex) {
            Logger.getLogger(DisponibilidadPistasController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClubDAOException ex) {
            Logger.getLogger(DisponibilidadPistasController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        int duracion = c.getBookingDuration();
        int numReservas = c.getBookingSlots();
        misdatos.add(LocalTime.of(9, 0));
        
        for(int i = 1; i < numReservas; i++){
            int hora = misdatos.get(i - 1).getHour();
            int min = misdatos.get(i - 1).getMinute();
            int nuevaHora = hora + (duracion / 60);
            int nuevoMin = min + (duracion % 60);
            misdatos.add(LocalTime.of(nuevaHora, nuevoMin));
        }
        
        return misdatos;
    }
    
    private ArrayList<Label> crearListaDispo(ArrayList<LocalTime> misHoras, String pista, LocalDate dia){
        ArrayList<Label> res = new ArrayList<Label>();
        //Label libre = null;
        
        Club c = null;
        try {
            c = Club.getInstance();
        } catch (IOException ex) {
            Logger.getLogger(DisponibilidadPistasController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClubDAOException ex) {
            Logger.getLogger(DisponibilidadPistasController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //int numReservas = c.getBookingSlots();
        List<Booking> reservas = c.getCourtBookings(pista, dia);
        int j = 0;
        
        if(reservas.size() >= 1){
            for(int i = 0; i < misHoras.size(); i++){
            LocalTime hora = misHoras.get(i);
            Booking reserva = reservas.get(j);
            
            if(hora.equals(reserva.getFromTime())){
                Label reservado = new Label();
                reservado.setText("RESRERVADO");
                reservado.setStyle("-fx-text-fill: red;");
                res.add(reservado);
            }
            else{
                Label libre = new Label();
                libre.setText("LIBRE");
                libre.setStyle("-fx-text-fill: green;");
                res.add(libre);
            }
            }   
        }
        else{
            for(int i = 0; i < misHoras.size(); i++){
                Label libre = new Label();
                libre.setText("LIBRE");
                libre.setStyle("-fx-text-fill: green;");
                res.add(libre);
            }
        }
        
        return res;
    }
    
    private void syncListas(ListView<Label> lista1, ListView<LocalTime> lista2){
        lista1.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener() {
                    public void changed(ObservableValue observable, Object oldvalue, Object newValue) {
                        lista2.getSelectionModel().select(lista1.getSelectionModel().getSelectedIndex());
                    }
                });
        
        lista2.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener() {
                    public void changed(ObservableValue observable, Object oldvalue, Object newValue) {
                        lista1.getSelectionModel().select(lista2.getSelectionModel().getSelectedIndex());
                    }
                });
    }
    
    private void registerHandlers(ListView<Label> lista1, ListView<LocalTime> lista2){
        try {
            Club club = Club.getInstance();
        } catch (ClubDAOException ex) {
            Logger.getLogger(ReservarPistaController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ReservarPistaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        lista1.setOnMouseClicked(new EventHandler<MouseEvent>() {
            
            @Override
            public void handle(MouseEvent click) {
                if(click.getClickCount() == 2){
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmacion");
                    alert.setHeaderText(null);
                    alert.setContentText("Seguro que quieres salir?");
                    Optional<ButtonType> result = alert.showAndWait();
                }
            }
        });
        
        lista2.setOnMouseClicked(new EventHandler<MouseEvent>() {
            
            @Override
            public void handle(MouseEvent click) {
                if(click.getClickCount() == 2){
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmacion");
                    alert.setHeaderText(null);
                    alert.setContentText("Seguro que quieres salir?");
                    Optional<ButtonType> result = alert.showAndWait();
                }
            }
        });
        
        
        
        //horarioSelected.setText(listaHoras.getSelectionModel().getSelectedItem().toString());
    }

   
}
