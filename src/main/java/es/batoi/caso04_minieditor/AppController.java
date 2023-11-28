package es.batoi.caso04_minieditor;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import es.batoi.caso04_minieditor.util.AlertMessages;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
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
	private boolean textoGuardado;
	private boolean modificado;
	private Preferences pf;

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		taEditor.setDisable(true);
		try {
			usandoPreferences();
		} catch (BackingStoreException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		setTextoGuardadoFalse();
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
						setModificadoTrue();
					}
				});

	}

	private void setModificadoTrue() {
		modificado = true;
	}

	private void setModificadoFalse() {
		modificado = false;
	}

	private void muestraInfo(int numLin, int numCaracteres) {
		lblInfo.setText(numLin + " lineas - " + numCaracteres + " caracteres");
	}

	@FXML
	private void handleNuevo() {
		taEditor.setDisable(false);
	}

	private void setTextoGuardadoFalse(){
		textoGuardado = false;
	}
	private void setTextoGuardadoTrue(){
		textoGuardado = true;
	}

	@FXML
	private void handleAbrir() throws IOException {
		if (modificado){
			AlertMessages.mostrarAlertWarning("Debes guardar o cerrar el archivo anterior");
		} else {
			FileChooser fc = new FileChooser();
			fc.setInitialDirectory(new File(pf.get("path", System.getProperty("User.home"))));
			fc.setTitle("ABRIR ARCHIVO");
			f = fc.showOpenDialog(escenario);
			taEditor.setDisable(false);
			if (f!=null) {
				BufferedReader br = new BufferedReader(new FileReader(f));
				StringBuilder content = new StringBuilder();
				String line;
				while ((line = br.readLine()) != null) {
					content.append(line).append("\n");
				}
				br.close();

				taEditor.setText(content.toString());

				pf.put("path", f.getParent());
			}else{
				taEditor.setDisable(true);
			}
		}
	}

	@FXML
	private void handleGuardar() throws IOException {

		if (f != null) {
			BufferedWriter bw = new BufferedWriter(new FileWriter(f));
			bw.write(taEditor.getText());
			bw.close();
			setTextoGuardadoTrue();
			setModificadoFalse();
			AlertMessages.mostrarAlert("Información", "Texto guardado", Alert.AlertType.INFORMATION);
		} else {
			FileChooser fc = new FileChooser();
			fc.setInitialDirectory(new File(pf.get("path", System.getProperty("User.home"))));
			fc.setTitle("GUARDAR ARCHIVO");
			f = fc.showSaveDialog(escenario);
			BufferedWriter bw = new BufferedWriter(new FileWriter(f));
			bw.write(taEditor.getText());
			bw.close();
			setTextoGuardadoTrue();
			setModificadoFalse();
			AlertMessages.mostrarAlert("Información", "Texto guardado", Alert.AlertType.INFORMATION);
		}
	}

	@FXML
	private void handleCerrar() {
		if (f!= null && !textoGuardado){
			AlertMessages.mostrarAlertWarning("Debes guardar el archivo anterior");
		}else{
			f = null;
			taEditor.clear();
			taEditor.setDisable(true);
			setModificadoFalse();
		}
	}

	void setEscenario(Stage escenario) {
		this.escenario = escenario;
	}


	private void usandoPreferences() throws BackingStoreException, IOException {
		pf = Preferences.userNodeForPackage(AppController.class);
	}
}



