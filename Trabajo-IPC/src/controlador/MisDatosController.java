/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Pablo Angre
 */
public class MisDatosController implements Initializable {

    @FXML
    private TextField TextFieldNombre;
    @FXML
    private TextField TextFieldApellidos;
    @FXML
    private TextField TextFieldNombreUsuario;
    @FXML
    private TextField TextFieldContraseña;
    @FXML
    private TextField TextFieldRepetirContraseña;
    @FXML
    private TextField TextFieldNumeroTarjeta;
    @FXML
    private TextField TextFieldSVC;
    @FXML
    private TextField TextFieldTelefono;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
