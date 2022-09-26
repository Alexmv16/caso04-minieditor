package es.batoi.caso04_minieditor;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author sergio
 */
public class AppController implements Initializable {

	@FXML
	private TextArea taEditor;
	@FXML
	private Label lblInfo;
	@FXML
	private Button btnAbrir;
	@FXML
	private Button btnCerrar;
	@FXML
	private Button btnGuardar;
	@FXML
	private Button btnNuevo;

	private Stage escenario;
	private File f;

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		taEditor.textProperty()
				.addListener((ObservableValue<? extends String> obs, String oldValor, String newValor) -> {
					if (!oldValor.equals(newValor)) {						
						int pos = 0;
						int numLin = 1;
						while (pos > -1) {
							pos = newValor.indexOf(System.getProperty("line.separator"), pos);
							if (pos > -1) {
								pos++;
								numLin++;
							}
						}
						muestraInfo(numLin, newValor.length() - numLin + 1);
					}
				});

	}

	private void muestraInfo(int numLin, int numCaracteres) {
		lblInfo.setText(numLin + " lineas - " + numCaracteres + " caracteres");
	}

	@FXML
	private void handleNuevo() {
		taEditor.setDisable(false);
		System.out.println("Has pulsado botón nuevo");
	}

	@FXML
	private void handleAbrir() {
		FileChooser fc = new FileChooser();
		fc.setTitle("ABRIR ARCHIVO");
		f = fc.showOpenDialog(escenario);
		System.out.println("Archivo seleccionado para abrir: " + f.getAbsolutePath());
		taEditor.setText("Esto es lo que estoy leyendo del fichero");

	}

	@FXML
	private void handleGuardar() {
		FileChooser fc = new FileChooser();
		fc.setTitle("GUARDAR ARCHIVO");
		f = fc.showSaveDialog(escenario);
		System.out.println("Archivo seleccionado para guardar: " + f.getAbsolutePath());

	}

	@FXML
	private void handleCerrar() {
		System.out.println("Has pulsado en cerrar");
	}

	void setEscenario(Stage escenario) {
		this.escenario = escenario;
	}

}
