package it.polito.tdp.rivers;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.rivers.model.Model;
import it.polito.tdp.rivers.model.River;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<River> boxRiver;

    @FXML
    private TextField txtStartDate;

    @FXML
    private TextField txtEndDate;

    @FXML
    private TextField txtNumMeasurements;

    @FXML
    private TextField txtFMed;

    @FXML
    private TextField txtK;

    @FXML
    private Button btnSimula;

    @FXML
    private TextArea txtResult;

    @FXML
    void doRiempi(ActionEvent event) {
    	//txtResult.appendText(boxRiver.getValue()+"\n");
    	this.model.getFlows(boxRiver.getValue());
    	
    	this.txtStartDate.setText(this.model.primaMisura().toString());
    	this.txtEndDate.setText(this.model.ultimaMisura().toString());
    	this.txtNumMeasurements.setText(this.model.nMisure()+"");
    	this.txtFMed.setText(String.format("%.2f",this.model.avgMisura()));

    }

    @FXML
    void doSImula(ActionEvent event) {
    	
    	if(boxRiver.getValue()==null) {
    		txtResult.setText("scegliere un fiume\n");
    		return;
    	}
    	
    	if(txtK.getText()==null) {
    		txtResult.setText("Inserire un numero K\n");
    		return;
    	}
    	
    	double k = 0;
    	try {
    		k = Double.parseDouble(txtK.getText());
    	}catch(NumberFormatException ne) {
    		txtResult.setText("Inserire un numero K maggiore di 0\n");
    		return;
    	}
    	
    	
    	//txtResult.clear();
    	
    	model.simula(k);
    	
    	txtResult.appendText("Numero di giorni in cui non si è potuta garantire l'erogazione minima: "+this.model.numNienteErogazione()+"\n");
    	txtResult.appendText(String.format("Occupazione media in metri cubi al giorno: %.2f",this.model.avgC())+"\n\n");
    	
    	

    }

    @FXML
    void initialize() {
        assert boxRiver != null : "fx:id=\"boxRiver\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtStartDate != null : "fx:id=\"txtStartDate\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtEndDate != null : "fx:id=\"txtEndDate\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtNumMeasurements != null : "fx:id=\"txtNumMeasurements\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtFMed != null : "fx:id=\"txtFMed\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtK != null : "fx:id=\"txtK\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    

    public void setModel(Model model) {
       	this.model = model;
       	this.boxRiver.getItems().addAll(this.model.getAllRivers());
       }
}

