/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlador;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.*;
import model.ClubDAOException;

/**
 * Controlador de la ventana para ver la DIPOIBILIDAD de PISTAS
 *
 * @author angre
 */
public class DisponibilidadPistasController implements Initializable {

    @FXML
    private Button botonAtras;
    @FXML
    private DatePicker dia;
    @FXML
    private ComboBox<String> comboPistas;
    @FXML
    private ListView<LocalTime> listaHoras;
    @FXML
    private ListView<String> listaDispo;
    @FXML
    private Button verDispo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            Club c = model.Club.getInstance();
            
            //opciones a elegir de pistas
            List <Court> pistas = c.getCourts();
            String [] listaPistas = new String[pistas.size()];
            for(int i = 0; i < pistas.size(); i++ ){
                listaPistas[i] = pistas.get(i).getName();
            }
            comboPistas.getItems().addAll(listaPistas);
            
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
            
        } catch (ClubDAOException ex) {
            Logger.getLogger(DisponibilidadPistasController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DisponibilidadPistasController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    

    @FXML
    private void clickedAtras(ActionEvent event) throws IOException {
        // VOLVER A PRINCIPAL SIN LOGIN
        
        FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/vista/Principal.fxml"));
        Parent root = miCargador.load();
               
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Club de Tenis MASTURBANDA");
        stage.initModality(Modality.APPLICATION_MODAL);
        //stage.setMaximized(true);
        stage.show();
        // cerrar ventana principal
        stage = (Stage) botonAtras.getScene().getWindow();
        stage.close();
    }
    @FXML
    private void clickedVer(ActionEvent event) {
        if(comboPistas.getValue() != null && dia.getValue() != null){
            
            //lista de Horas
            ArrayList<LocalTime> misHoras = new ArrayList<LocalTime>();
            misHoras = crearListaHoras(misHoras);
            ObservableList<LocalTime> horas = FXCollections.observableArrayList(misHoras);
            listaHoras.setItems(horas);
            
            //lista de Disponibilidad
            ArrayList<String> miDispo = new ArrayList<String>();
            miDispo = crearListaDispo(misHoras,comboPistas.getValue(), dia.getValue());
            ObservableList<String> datos = FXCollections.observableArrayList(miDispo);
            listaDispo.setItems(datos);
        }
        else{
            // Alerta : FECHA O DIA INCORRECTOS
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("ERROR");
            alert.setContentText("Porfavor inserte correctamente el dia y la pista");
            alert.showAndWait();
        }
            
    }
    
    private ArrayList<LocalTime> crearListaHoras(ArrayList<LocalTime> misdatos){
        Club c = null;
        try {
            c = model.Club.getInstance();
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
    
    private ArrayList<String> crearListaDispo(ArrayList<LocalTime> misHoras, String pista, LocalDate dia){
        ArrayList<String> res = new ArrayList<String>();
        Club c = null;
        try {
            c = model.Club.getInstance();
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
                res.add("RESRERVADO  por : " + reserva.getMember().getNickName());
            }
            else res.add("LIBRE");
            }   
        }
        else{
            for(int i = 0; i < misHoras.size(); i++){
                res.add("LIBRE");
            }
        }
        
        return res;
    }

    
}
