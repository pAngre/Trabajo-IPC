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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author angre
 */
public class PrincipalController implements Initializable {

    @FXML
    private Button botonDispo;
    @FXML
    private Button botonLogin;
    @FXML
    private ImageView logo;
    @FXML
    private ImageView cent;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Image imagen = new Image(getClass().getResourceAsStream("/avatars/logo.png"));
        logo.setImage(imagen);
        //Image imagen2 = new Image(getClass().getResourceAsStream("/avatars/cent.png"));
        //cent.setImage(imagen2);
    }    

    @FXML
    private void clickedDispo(ActionEvent event) throws IOException {
        FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/vista/DisponibilidadPistas.fxml"));
        Parent root = miCargador.load();
        
        
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Ver Disponibilidad de Pistas");
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
        stage = (Stage) botonDispo.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void clickedLogin(ActionEvent event) throws IOException {
        FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/vista/Login.fxml"));
        Parent root = miCargador.load();
        
        
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Login Club de Tenis MASTURBANDA");
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }
    
}
