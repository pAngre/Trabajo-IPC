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
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.*;

/**
 * Controlador de la ventana del LOGIN
 *
 * @author Pablo Angre
 */
public class LoginController implements Initializable {

  

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
    @FXML
    private Circle logo;
    
    private Member usuario;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // imagen de logo
        Image image = new Image(getClass().getResourceAsStream("/resources/logo2.PNG"));
        logo.setFill(new ImagePattern(image));
        
    }    

    @FXML
    private void irARegistro(ActionEvent event) throws IOException {
        // Abrir ventana de REGISTRO
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
        //crea instancia de club
        Club c = Club.getInstance();
        //boolean existe = c.existsLogin(nick);
        
        // revisa que el login y la contraseña sean correctos y da la bienvenida
        if(c.existsLogin(nick)){
         usuario = c.getMemberByCredentials(nick, pass);
         
         if(usuario == null){
            textoError.setText("Nickname y contraseña no coinciden");
        }
         else{
             //textoError.setText("Bienvenido " + nick);
             // Abrir ventana PRINCIPAL CON LOGIN
             FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/vista/PrincipalLogin.fxml"));
             Parent root = miCargador.load();
             
             PrincipalLoginController controlador = miCargador.getController();
             controlador.initMember(nick, pass);
        
        
             Scene scene = new Scene(root);
             String css = this.getClass().getResource("/estilos/estiloPrinLogin.css").toExternalForm();
             scene.getStylesheets().add(css);
             Stage stage = new Stage();
             stage.setScene(scene);
             stage.setTitle("Club de Tenis MASTURANDA");
             stage.setMinHeight(800);
             stage.setMinWidth(1000);
             stage.initModality(Modality.APPLICATION_MODAL);
             //stage.setMaximized(true);
             stage.show();
             // cerrar login y ventana principal
             stage = (Stage) botonEntrar.getScene().getWindow();
             stage.close();            
         }
        }
        else{
            textoError.setText("Login no existe. Porfavor registrate");
        }
    }
}
