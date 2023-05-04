/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlador;

import java.io.File;
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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.*;

/**
 * FXML Controller class
 *
 * @author Pablo Angre
 */
public class LoginController implements Initializable {

    @FXML
    private ImageView logo;

    @FXML
    private Button botonRegistro;
    @FXML
    private TextField nickText;
    @FXML
    private TextField passwordText;
    @FXML
    private Text textoError;
    @FXML
    private Button botonEntrar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Image image = new Image(getClass().getResourceAsStream("/avatars/logo2.png"));
        logo.setImage(image);
        
        
    }    

    @FXML
    private void irARegistro(ActionEvent event) throws IOException {
        FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/vista/Registro.fxml"));
        Parent root = miCargador.load();
        
        
        Scene scene = new Scene(root, 600, 500);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Registro de Usuario Nuevo");
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    @FXML
    private void entrarClicked(ActionEvent event) throws ClubDAOException, IOException {
        String nick = nickText.getText();
        String pass = passwordText.getText();
        
        Club c = model.Club.getInstance();
        boolean existe = c.existsLogin(nick);
        
        if(existe){
         Member m = c.getMemberByCredentials(nick, pass);
         
         if(m == null){
            textoError.setText("Nickname y contraseña no coinciden");
        }
         else{
             textoError.setText("Bienvenido " + nick);
         }
        }
        else{
            textoError.setText("Login no existe. Porfavor registrate");
        }
    }
    
}
