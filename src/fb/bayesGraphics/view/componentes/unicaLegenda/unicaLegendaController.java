/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package componentes.unicaLegenda;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Maia
 */
public class unicaLegendaController implements Initializable {

    @FXML
    private Pane quadradoCor;
    @FXML
    private Label labelNome;

    public void setIndexController(String nome, String color) {
        setQuadradoCor(color);
        setLabelNome(nome);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    private void setQuadradoCor(String color) {
        quadradoCor.setStyle("-fx-background-color: " + color + ";");
    }

    private void setLabelNome(String nome) {
        labelNome.setText(nome);
    }

}
