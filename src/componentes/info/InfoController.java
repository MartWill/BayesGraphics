/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package componentes.info;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author maia
 */
public class InfoController implements Initializable {

    @FXML
    private TextField NomeField;
    @FXML
    private TextField NInstanciaField;
    @FXML
    private TextField NAtributoField;
    @FXML
    private TextField AtributoField;
    @FXML
    private TextField ValorField;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void UpdateInfoController(String nomeArquivo, int nInstanciaArquivo, int nAtributoArquivo, String atributoVariavelAlvoString, String atributoValorAlvo) {
        this.NomeField.setText(nomeArquivo);
        this.NInstanciaField.setText(String.valueOf(nInstanciaArquivo));
        this.NAtributoField.setText(String.valueOf(nAtributoArquivo));
        this.AtributoField.setText(atributoVariavelAlvoString);
        this.ValorField.setText(atributoValorAlvo);

    }

}
