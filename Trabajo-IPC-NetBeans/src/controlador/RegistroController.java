/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlador;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import model.*;

/**
 * Controlador de la ventana del REGISTRO
 *
 * @author Pablo Angre
 */
public class RegistroController implements Initializable {

    @FXML
    private TextField campoNombre;
    @FXML
    private TextField campoApellidos;
    @FXML
    private TextField campoTlf;
    @FXML
    private TextField campoNick;
    @FXML
    private TextField campoCredit;
    @FXML
    private ComboBox<String> avatarCombo;
    @FXML
    private Text textoError;
    @FXML
    private Button botonRegistrar;
    @FXML
    private PasswordField campoPassRepe;
    @FXML
    private PasswordField campoPass;
    @FXML
    private TextField campoSVC;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Añadir avatares al combo
        avatarCombo.getItems().addAll("/avatars/men.PNG","/avatars/men2.PNG",
                "/avatars/men3.PNG","/avatars/men4.PNG","/avatars/men5.PNG",
                "/avatars/woman.PNG","/avatars/woman2.PNG","/avatars/woman3.PNG",
                "/avatars/woman4.PNG","/avatars/woman5.PNG","/avatars/woman6.PNG");
        avatarCombo.setCellFactory(c -> new ImagenTabCell());
    }    

    @FXML
    private void registrarClicked(ActionEvent event) throws ClubDAOException, IOException {
        Club c = model.Club.getInstance();
        
        // revisar que todos los campos son correctos
        if(esValido(campoNombre) && esValido(campoApellidos) && esTlfValido(campoTlf)
                && esNickValido(campoNick) && esPassValida(campoPass, campoPassRepe)
                && esCreditValido(campoCredit, campoSVC)){
            int svc = 0;
            // si han completado el campo svc cambiarlo a INTEGER
            if(!campoSVC.getText().isEmpty()){
                svc = Integer.parseInt(campoSVC.getText());
            }
            Image img;
            // si no han elegido avatar poner el default
            // si han elegido darle un valor a img
            if(avatarCombo.getValue() == null){
                img = new Image("/avatars/default.png");
            }
            else{
                img = new Image(avatarCombo.getValue());
            }
            // registrar el nuevo miembro
            c.registerMember(campoNombre.getText(), campoApellidos.getText(),
                    campoTlf.getText(), campoNick.getText(), campoPass.getText(),
                    campoCredit.getText(), svc, img);
            Stage stage = (Stage) botonRegistrar.getScene().getWindow();
            stage.close();
            }
            
        else{
            
            textoError.setText("Porfavor completa correctamente todos los campos");         
        }
    }
    
    class ImagenTabCell extends ComboBoxListCell<String> {
        private ImageView view = new ImageView();
        private Image imagen;

        @Override
        public void updateItem(String t, boolean bln) {
            super.updateItem(t, bln); 
            if (t == null || bln) {
                setText(null);
                setGraphic(null);
            } else {
                imagen = new Image(t,50,60,true,true);
                view.setImage(imagen);
                setGraphic(view);
                setText(null);
            }
        }
    }
    
    // metodo para saber si el TextField es valido
    private boolean esValido(TextField t){
        String s = t.getText();
        return (!s.isEmpty()) && (s.trim().length() != 0);
    }
    
    // metodo para saber si el TextField del NICK es valido
    private boolean esNickValido(TextField t) throws ClubDAOException, IOException{
        Club c = model.Club.getInstance();
        boolean existe = c.existsLogin(campoNick.getText());
        String s = t.getText();
        return esValido(t) && !existe && !s.contains(" ");
    }
    
    // metodo para saber si el TextField de la CONTRASEÑA es valido
    private boolean esPassValida(TextField t, TextField t1){
        String s = t.getText();
        String r = "^(?=.*[0-9])"
                + "(?=.*[a-z])"
                + "(?=\\S+$).{6,20}$";
        
        Pattern p = Pattern.compile(r);
        Matcher m = p.matcher(s);
        return esValido(t) && m.matches() && s.equals(t1.getText());
    }
    
    // metodo para saber si el TextField del TELEFONO es valido
    private boolean esTlfValido(TextField t){
        String s = t.getText();
        Pattern p = Pattern.compile("^\\d{9}$");
        Matcher m = p.matcher(s);
        return esValido(t) && m.matches();
    }
    
    // metodo para saber si el TextField de la TARJETA y el SVC es valido
    private boolean esCreditValido(TextField t, TextField t1){
        String s = t.getText();
        Pattern p = Pattern.compile("^\\d{16}$");
        Matcher m = p.matcher(s);
        String s1 = t1.getText();
        Pattern p1 = Pattern.compile("^\\d{3}$");
        Matcher m1 = p1.matcher(s1);
        return (t.getText().isEmpty()&& t1.getText().isEmpty()) || (esValido(t) && esValido(t1) && m.matches() && m1.matches());
    }
    
    
}
