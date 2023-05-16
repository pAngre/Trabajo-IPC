/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlador;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.*;

/**
 * Controlador de la ventana PrRINCIPAL con el LOGIN hecho
 *
 * @author Pablo Angre
 */
public class PrincipalLoginController implements Initializable {

    @FXML
    private Text textTitulo;
    @FXML
    private Circle logo;
    @FXML
    private Button botonReservar;
    @FXML
    private Button botonMisReservas;
    @FXML
    private Button botonVerDispo;
    @FXML
    private Button botonMisDatos;
    @FXML
    private Button botonLogout;
    @FXML
    private ImageView reservar;
    @FXML
    private ImageView misReservas;
    @FXML
    private ImageView verDispo;
    @FXML
    private ImageView misDatos;
    @FXML
    private ImageView logout;
    
    private Member usuario;

    
    public void initMember(String nick, String pass) throws ClubDAOException, IOException{
        Club c = Club.getInstance();
        usuario = c.getMemberByCredentials(nick, pass);
        misDatos.setImage(usuario.getImage());
    }
    
    private void inicializar() throws ClubDAOException, IOException{
        Club c = Club.getInstance();
        // guardar usuario logeado
        
        Image imglogo = new Image(getClass().getResourceAsStream("/resources/logo2.PNG"));
        logo.setFill(new ImagePattern(imglogo));
        
        Image imgMisReservas = new Image(getClass().getResourceAsStream("/resources/imgMisReservas.png"));
        misReservas.setImage(imgMisReservas);
        
        Image imgReservar = new Image(getClass().getResourceAsStream("/resources/imgReservar.png"));
        reservar.setImage(imgReservar);
        
        Image imgVerDispo = new Image(getClass().getResourceAsStream("/resources/imgVerDispo.png"));
        verDispo.setImage(imgVerDispo);
        
        textTitulo.setText("ZONA DE MIEMBROS " + c.getName().toUpperCase());
     
        //misDatos.setImage(usuario.getImage());
        
        Image imgLogout = new Image(getClass().getResourceAsStream("/resources/imgSalir.png"));
        logout.setImage(imgLogout);
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            inicializar();
        } catch (ClubDAOException ex) {
            Logger.getLogger(PrincipalLoginController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PrincipalLoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
    

    @FXML
    private void clickedReservar(ActionEvent event) {
    }

    @FXML
    private void clickedMisReservas(ActionEvent event) {
    }

    @FXML
    private void clickedVerDispo(ActionEvent event) throws IOException {
        FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/vista/DisponibilidadPistas.fxml"));
        Parent root = miCargador.load();
        
        Scene scene = new Scene(root);
        String css = this.getClass().getResource("/estilos/estiloDispo.css").toExternalForm();
        scene.getStylesheets().add(css);
        Stage stage = new Stage();
        
        stage.setScene(scene);
        stage.setTitle("Ver Disponibilidad de Pistas");
        stage.setMinHeight(750);
        stage.setMinWidth(900);
        //stage.setMaximized(true);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    @FXML
    private void clickedMisDatos(ActionEvent event) {
    }

    @FXML
    private void clickedLogout(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmacion");
        alert.setHeaderText(null);
        alert.setContentText("Seguro que quieres salir?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK){
            Stage stage = (Stage) botonLogout.getScene().getWindow();
            stage.close();
        }
    }
    
}
