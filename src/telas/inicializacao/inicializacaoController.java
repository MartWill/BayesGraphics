package telas.inicializacao;

import algoritmos.naivebayes.naiveBayesAlgorithm;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class inicializacaoController {

    private naiveBayesAlgorithm nb;

    private String url;

    @FXML
    private TextField dirLabel;
    @FXML
    private ComboBox<String> variavelBox;
    @FXML
    private ComboBox<String> valorBox;

    private void setDirLabel(String text) {
        dirLabel.setText(text);
    }

    private void setVariavelBox(List<String> aux) {
        variavelBox.getItems().clear();
        for (int i = 0; i < aux.size(); i++) {
            variavelBox.getItems().add(aux.get(i));
        }
    }

    private void setValorBox(List<String> aux) {
        valorBox.getItems().clear();
        for (int i = 0; i < aux.size(); i++) {
            valorBox.getItems().add(aux.get(i));
        }
    }

    @FXML
    private void ActionEscolher(ActionEvent event) throws IOException {
        //FRONT
        dirLabel.setText("AGUARDE...");

        //File Chooser
        String name;

        Stage primaryStage = new Stage();
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(primaryStage);

        url = selectedFile.getPath();
        name = selectedFile.getName();

        //NaiveBayes
        nb = new naiveBayesAlgorithm(url);

        //Front UPGRADE
        setDirLabel(name);
        setVariavelBox(nb.getListaNomesAtributos(true));
    }

    @FXML
    private void ActionGerarAnalise(ActionEvent event) throws Exception {
        System.out.println("Apertou Botao GERADOR DE GRAFICOS");

        nb.setNumAtributoAlvo(variavelBox.getSelectionModel().getSelectedIndex());
        nb.setNumValorAlvo(valorBox.getSelectionModel().getSelectedIndex());
        System.out.println("MODELO NAIVE CRIADO COM SUCESSO: " + nb.criarModeloNaive());
        System.out.println("NAIVE BAYES LOGS(FIM)");

    }

    @FXML
    private void ActionVariavelBox(ActionEvent event) {
        nb.setNumAtributoAlvo(variavelBox.getSelectionModel().getSelectedIndex());
        setValorBox(nb.getListaNomesValores(variavelBox.getSelectionModel().getSelectedIndex(), true));

    }

}
