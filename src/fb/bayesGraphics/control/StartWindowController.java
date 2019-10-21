/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fb.bayesGraphics.control;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author maia
 */
public class StartWindowController implements Initializable {

    @FXML
    private TextField dirLabel;
    @FXML
    private ComboBox<?> variavelBox;
    @FXML
    private ComboBox<?> valorBox;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void ActionEscolher(ActionEvent event) {
    }

    @FXML
    private void ActionVariavelBox(ActionEvent event) {
    }

    @FXML
    private void ActionGerarAnalise(ActionEvent event) {
    }

    
}
