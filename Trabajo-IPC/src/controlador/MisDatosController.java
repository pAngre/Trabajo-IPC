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
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.*;
import model.ClubDAOException;

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
    
    private Member usuario;
    private boolean pressedGuardar = false;
    @FXML
    private Button btnAtras;
    @FXML
    private Button btnGuardar;
    @FXML
    private ImageView avatar;
    
    
    public void initMember(Member usuario) throws ClubDAOException, IOException{
        //Club c = Club.getInstance();
        //usuario = c.getMemberByCredentials(nick, pass);
        this.usuario = usuario;
        TextFieldNombre.setText(usuario.getName());
        TextFieldApellidos.setText(usuario.getSurname());
        TextFieldNombreUsuario.setText(usuario.getNickName());
        TextFieldContraseña.setText(usuario.getPassword());
        TextFieldRepetirContraseña.setText(usuario.getPassword());
        TextFieldTelefono.setText(usuario.getTelephone());
        if(usuario.getCreditCard() != null){
            TextFieldNumeroTarjeta.setText(usuario.getCreditCard());
        }
        if(usuario.getSvc() != 0){
            TextFieldSVC.setText(Integer.toString(usuario.getSvc()));
        }
        avatar.setImage(usuario.getImage());
    }
    
    public boolean isGuardarPressed(){
        return pressedGuardar;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void clickedAtras(ActionEvent event) {
        //CERRAR VENTANA
        
        Stage stage = new Stage();
        stage = (Stage) btnAtras.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void clickedGuardar(ActionEvent event) {
        String nombre = TextFieldNombre.getText();
        String apellido = TextFieldApellidos.getText();
        String nick = TextFieldNombreUsuario.getText();
        String contra = TextFieldContraseña.getText();
        String tlf = TextFieldTelefono.getText();
        String tarjCredito = TextFieldNumeroTarjeta.getText();
        int svc = Integer.parseInt(TextFieldSVC.getText());
        Image avatar;
    }
    
}
