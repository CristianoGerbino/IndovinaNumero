package it.polito.tdp.numero.model;

import java.security.InvalidParameterException;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class NumeroModel {
	
	private final int NMAX = 100;
	private final int TMAX = 8;

	private int segreto;
	//private int tentativiFatti;
	private IntegerProperty tentativiFatti;
	private boolean inGioco = false;
	
	public NumeroModel () {
		inGioco = false;
		this.tentativiFatti = new SimpleIntegerProperty();
	}

	/**
	 * Avvia nuova partita
	 */
	public void newGame() {
		inGioco = true;
		this.segreto = (int) (Math.random() * NMAX) + 1;
		this.tentativiFatti.set(0);
	}
	
	/**
	 * Metodo per effettuare un tentativo
	 * 1 se il tentativo è troppo alto
	 * 0 se il tentativo è giusto
	 * -1 se il tentativo è troppo basso
	 */
	
	public int tentativo (int t) {
		//controllo se la partita è in corso
		if (!inGioco) {
			throw new IllegalStateException("La partita e' terminata");
		}
		
		//contolliamo se l'input è nel range corretto
		if (!tentativoValido(t)) {
			throw new InvalidParameterException("Devi inserire un numero tra 1 e "+NMAX);
		}
		
		//gestisci tentativo
		tentativiFatti.set(tentativiFatti.get() +1);
		if (tentativiFatti.get()== TMAX) {
			inGioco = false; 
		}
		
		if (t == this.segreto) {
			inGioco = false;
			return 0;
		}
		
		if (t >this.segreto ) {
			return 1;
		}
		
			return -1;
	}
	
	public boolean tentativoValido(int t) {
		if (t<1 || t>NMAX) {
			return false;
		}
		else {
			return true;
		}
	}

	public int getSegreto() {
		return segreto;
	}


	public boolean isInGioco() {
		return inGioco;
	}

	public int getTMAX() {
		return TMAX;
	}

	public final IntegerProperty tentativiFattiProperty() {
		return this.tentativiFatti;
	}
	

	public final int getTentativiFatti() {
		return this.tentativiFattiProperty().get();
	}
	

	public final void setTentativiFatti(final int tentativiFatti) {
		this.tentativiFattiProperty().set(tentativiFatti);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
