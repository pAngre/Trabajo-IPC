/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import model.*;

/**
 * FXML Controller class
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
    private TextField campoPass;
    @FXML
    private TextField campoCredit;
    @FXML
    private ComboBox<String> avatarCombo;
    @FXML
    private Text textoError;
    @FXML
    private Button botonRegistrar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        avatarCombo.getItems().addAll("/avatars/men.PNG","/avatars/men2.PNG",
                "/avatars/men3.PNG","/avatars/men4.PNG","/avatars/men5.PNG",
                "/avatars/woman.PNG","/avatars/woman2.PNG","/avatars/woman3.PNG",
                "/avatars/woman4.PNG","/avatars/woman5.PNG","/avatars/woman6.PNG");
        avatarCombo.setCellFactory(c -> new ImagenTabCell());
    }    

    @FXML
    private void registrarClicked(ActionEvent event) {
        Club c = model.Club.getInstance();
        boolean existe = c.existsLogin(campoNick.getText());
        
        if((!campoNombre.getText().isEmpty())
                && (campoNombre.getText().trim().length() != 0)
                && (!campoApellidos.getText().isEmpty())
                && (campoApellidos.getText().trim().length() != 0)
                && (!campoTlf.getText().isEmpty())
                && (campoTlf.getText().trim().length() != 0)
                && (!campoNick.getText().isEmpty())
                && (campoNick.getText().trim().length() != 0)
                && (!existe)
                && (!campoPass.getText().isEmpty())
                && (campoPass.getText().trim().length() != 0)){
        
    }
        //textoError.setText("Porfavor completa correctamente todos los campos");
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
    
}
