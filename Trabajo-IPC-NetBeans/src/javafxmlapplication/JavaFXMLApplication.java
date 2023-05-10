/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxmlapplication;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Club;


public class JavaFXMLApplication extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        
        Club c = Club.getInstance();
        
        FXMLLoader loader= new  FXMLLoader(getClass().getResource("/vista/Principal.fxml"));
        Parent root = loader.load();
        
        Scene scene = new Scene(root);
        String css = this.getClass().getResource("/estilos/estiloPrincipal.css").toExternalForm();
        scene.getStylesheets().add(css);
        
        stage.setScene(scene);
        stage.setTitle("Club de Tenis MASTURBANDA");
        stage.setMinHeight(400);
        stage.setMinWidth(600);
        //stage.setMaximized(true);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        
    }


    
}
