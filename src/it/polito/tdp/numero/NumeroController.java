package it.polito.tdp.numero;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.numero.model.NumeroModel;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class NumeroController {
	private NumeroModel model;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private HBox boxControllopartita;

	@FXML
	private TextField txtRimasti;
	// numero di tentativi rimasti ancora da provare

	@FXML
	private HBox boxControlloTentativi;

	@FXML
	private TextField txtTentativo;
	// tentativo inserito dall'utente

	@FXML
	private TextArea txtMessaggi;

	
	@FXML
	void handleNuovaPartita(ActionEvent event) {
		// Gestisce l'inizio di una nuova partita
		
		// Gestione dell'interfaccia
		boxControllopartita.setDisable(true);
		boxControlloTentativi.setDisable(false);
		txtMessaggi.clear();
		//txtRimasti.setText(Integer.toString(0));
		
		//Comunico al modello di iniziare una nuova partita
		model.newGame();
	}

	@FXML
	void handleProvaTentativo(ActionEvent event) {

		// Leggi il valore del tentativo
		String ts = txtTentativo.getText();

		// Controlla se � valido (il tipo di dato)

		int tentativo ;
		try {
			tentativo = Integer.parseInt(ts);
		} catch (NumberFormatException e) {
			// la stringa inserita non � un numero valido
			txtMessaggi.appendText("Non e' un numero valido\n");
			return;
		}
		
		if (!model.tentativoValido(tentativo)) {
			this.txtMessaggi.appendText("Range non valido\n");
		}

		int risultato = model.tentativo(tentativo);
		if (risultato == 0) {
			txtMessaggi.appendText("Complimenti, hai indovinato in "+model.getTentativiFatti()+" tentativi\n");
			boxControllopartita.setDisable(false);
			boxControlloTentativi.setDisable(true);
		}
		
		else if (risultato<0) {
			txtMessaggi.appendText("Tentativo troppo BASSO\n");
		}
		else if (risultato>0) {
			txtMessaggi.appendText("Tentativo troppo ALTO\n");
		}
		//txtRimasti.setText(Integer.toString(model.getTMAX()-model.getTentativiFatti()));
		
		if (!model.isInGioco()) {
			//la partita � finita
			if (risultato != 0) {
				txtMessaggi.appendText("Hai perso!\n");
				txtMessaggi.appendText("Il numero segreto era: "+model.getSegreto());
				boxControllopartita.setDisable(false);
				boxControlloTentativi.setDisable(true);
			}
		}
	}

	@FXML
	void initialize() {
		assert boxControllopartita != null : "fx:id=\"boxControllopartita\" was not injected: check your FXML file 'Numero.fxml'.";
		assert txtRimasti != null : "fx:id=\"txtRimasti\" was not injected: check your FXML file 'Numero.fxml'.";
		assert boxControlloTentativi != null : "fx:id=\"boxControlloTentativi\" was not injected: check your FXML file 'Numero.fxml'.";
		assert txtTentativo != null : "fx:id=\"txtTentativo\" was not injected: check your FXML file 'Numero.fxml'.";
		assert txtMessaggi != null : "fx:id=\"txtMessaggi\" was not injected: check your FXML file 'Numero.fxml'.";
		
	}
	
	public void setModel(NumeroModel model) {
		this.model = model;
		txtRimasti.textProperty().bind(Bindings.convert(model.tentativiFattiProperty()));
		//Abbiamo definito una property per collegare il valore dei tentativi fatti, che si aggiorna nel model, direttamente 
		//con la grafica, senza passare dal controller. Infatti il campo txtRimasti � collegato alla SimpleIntegerProperty
		//tentativiFatti utilizzando la funzione bind.
		//poich� la property era intera per collegare ad una property di testo abbiamo utilizzato la funzione Bindings.convert
		//Esistono anche delle property booleane per collegarle per esempio ai disable delle aree di testo
	}
	
	
	
	
	
}
