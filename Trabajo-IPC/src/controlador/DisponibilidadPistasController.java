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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    private ListView<Label> listaDispo;
    @FXML
    private ImageView img1;
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            Club c = Club.getInstance();
            
            //opciones a elegir de pistas
            List <Court> pistas = c.getCourts();
            String [] listaPistas = new String[pistas.size()];
            for(int i = 0; i < pistas.size(); i++ ){
                listaPistas[i] = pistas.get(i).getName();
            }
            comboPistas.getItems().addAll(listaPistas);
            //inicializar combobox
            comboPistas.setValue(listaPistas[0]);
            
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
            
        } catch (ClubDAOException ex) {
            Logger.getLogger(DisponibilidadPistasController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DisponibilidadPistasController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //imagen atras
        Image imagen1 = new Image(getClass().getResourceAsStream("/resources/imgAtras.png"));
        img1.setImage(imagen1);
       
        
        
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
    }    

    @FXML
    private void clickedAtras(ActionEvent event) throws IOException {
        // CERRAR VENTANA
        
        Stage stage = (Stage) botonAtras.getScene().getWindow();
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
                reservado.setText("RESRERVADO  por : " + reserva.getMember().getNickName());
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

    
}
