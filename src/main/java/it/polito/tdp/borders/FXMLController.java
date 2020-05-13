
package it.polito.tdp.borders;

import java.awt.Button;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	private Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtAnno"
    private TextField txtAnno; // Value injected by FXMLLoader
    
    @FXML
    private ComboBox<Country> comboBox;

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCalcolaConfini(ActionEvent event) {
    	txtResult.clear();
    	int year;
    	try {
    		year = Integer.parseInt(txtAnno.getText().toString());
    		
    	} catch (NumberFormatException e) {
    		txtResult.setText("Errore: inserire un numero nell'apposito campo 'anno'\n");
    		return;
    	}
    	if(year < 1816 || year > 2016) {
    		txtResult.setText("Errore: inserire un anno compreso tra il 1816 e il 2016\n");
    		return;
    	}
    	model.createGraph(year);
    	comboBox.getItems().addAll(this.model.graphVertices());
    	Map<Country, Integer> countryBorders = this.model.countryBorders();
    	for(Country c : countryBorders.keySet()) {
    		txtResult.appendText(c.toString());
    		txtResult.appendText(" (stati confinanti: " + countryBorders.get(c) + ")\n");
    	}
    	txtResult.appendText("Il numero di componenti connesse via terra nell'anno " + year + " è: " + this.model.connectivity());
    }
    
    @FXML
    void doTrovaVicini(ActionEvent event) {
    	Country c = comboBox.getValue();
    	if(c == null) {
    		txtResult.setText("Errore: selezionare uno stato di cui si vogliono trovare i vicini!\n"
    				+ "Se non sono presenti nazioni nell'elenco, ricordare di selezionare prima un anno e "
    				+ "ottenere gli stati esistenti in quell'anno e i loro confini tramite il bottone 'Calcola confini'\n");
    		return;
    	}
    	List<Country> connectedCountries = this.model.connectedCountries(c);
    	if(connectedCountries.size() == 0) {
    		txtResult.setText("Lo stato selezionato non aveva nessun vicino raggiungibile via terra nell'anno selezionato\n");
    		return;
    	}
    	txtResult.setText("Gli stati raggiungibili via terra da " + c.toString() + " sono :\n");
    	for(Country connected : connectedCountries) {
    		txtResult.appendText(connected.toString() + "\n");
    	}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
