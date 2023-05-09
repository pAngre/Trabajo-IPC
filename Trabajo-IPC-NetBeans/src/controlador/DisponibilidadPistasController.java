/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlador;

import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
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
    private TableView<Booking> tablaDispo;
    @FXML
    private TableColumn<Booking, LocalTime> columnaHora;
    @FXML
    private DatePicker dia;
    @FXML
    private TableColumn<Booking, String> columnaInfo;
    @FXML
    private ComboBox<String> comboPistas;

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
            
            //columnas de la tabla
            columnaHora.setCellValueFactory(new PropertyValueFactory<Booking, LocalTime>("forTime"));
            columnaInfo.setCellValueFactory(new PropertyValueFactory<Booking, String>("member"));
            
            //cargar informacion
            tablaDispo.setItems(crearReservas());
            
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
    
    private ObservableList<Booking> crearReservas() throws ClubDAOException, IOException{
        Club c = model.Club.getInstance();
        ObservableList<Booking> reservas = null;
        if(comboPistas.getValue() != null && dia.getValue() != null){
            reservas = (ObservableList<Booking>) c.getCourtBookings(comboPistas.getValue(), dia.getValue());
        }
        return reservas;
    }
}
