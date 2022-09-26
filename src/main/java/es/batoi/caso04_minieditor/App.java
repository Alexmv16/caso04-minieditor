package es.batoi.caso04_minieditor;

import javafx.application.Application;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

	private Stage escenario;
    private AnchorPane escena;    
    
    @Override
    public void start(Stage primaryStage) {
        this.escenario = primaryStage;
        this.escenario.setTitle("MINI EDITOR DE TEXTOS");
        try {
            
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("appvista.fxml"));
            escena = (AnchorPane) loader.load();
            
            AppController ac = loader.getController();
            ac.setEscenario(escenario);

            // Show the scene containing the root layout.            
            escenario.setScene(new Scene(escena));
            escenario.show();
        } catch (IOException e) {
            System.err.println("Error principal!"+e.getMessage());
        }
    }   

    public static void main(String[] args) {
        launch();
    }

}