/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlador;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author Pablo Angre
 */
public class LoginController implements Initializable {

    @FXML
    private ImageView logo;
    @FXML
    private TextField password;
    @FXML
    private TextField nick;
    @FXML
    private Button botonRegistro;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //File file = new File("avatars/logo1.png");
        Image image = new Image(getClass().getResourceAsStream("/avatars/logo.png"));
        logo.setImage(image);
        
        //logo = new ImageView("/avatars/logo.png");
    }    

    @FXML
    private void irARegistro(ActionEvent event) {
    }
    
}
