/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Controlador de la ventana para ver la DIPOIBILIDAD de PISTAS
 *
 * @author angre
 */
public class DisponibilidadPistasController implements Initializable {

    @FXML
    private Button botonAtras;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
        stage.setMaximized(true);
        stage.show();
        // cerrar ventana principal
        stage = (Stage) botonAtras.getScene().getWindow();
        stage.close();
    }
    
}
