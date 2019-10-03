/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package componentes.linha;

import componentes.grafico.estatisticas.graficoEstatisticasController;
import componentes.grafico.probabilidade.graficoProbabilidadeController;
import componentes.grafico.relevanciaGeral.graficoRelevanciaUmController;
import componentes.grafico.relevanciaValores.graficoRelevanciaDoisController;
import componentes.legenda.legendaController;
import jankovicsandras.imagetracer.ImageTracer;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.image.WritableImage;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Transform;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author Maia
 */
public class linhaGraficosController implements Initializable {

    @FXML
    private Label titleGeral;
    @FXML
    private HBox linhaGrafico;
    @FXML
    private VBox opcaoPane;

    private WritableImage bis;

    public boolean canShowOptionPane;

    private graficoEstatisticasController estatisticasController;
    private graficoProbabilidadeController probabilidadeController;
    private graficoRelevanciaUmController relevanciaUmController;
    private graficoRelevanciaDoisController relevanciaDoisController;
    private legendaController legendaController;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }

    private void setTitleGeral(String title) {
        titleGeral.setText(title);
    }

    private void addLegenda() throws IOException {

        FXMLLoader loader = new FXMLLoader();
        Parent legenda = loader.load(getClass().getResource("/componentes/legenda/index.fxml").openStream());

        legendaController = loader.getController();
        legendaController.addUnicaLegenda("MATHEUS", "#3275a8");
        legendaController.addUnicaLegenda("MATHEUS", "#3275a8");
        legendaController.addUnicaLegenda("MATHEUS", "#3275a8");
        legendaController.addUnicaLegenda("MATHEUS", "#3275a8");

        linhaGrafico.getChildren().add(legenda);

    }

    private void addGraficos() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        Parent grafico1 = loader.load(getClass().getResource("/componentes/grafico/estatisticas/index.fxml").openStream());
        estatisticasController = loader.getController();
        linhaGrafico.getChildren().add(grafico1);

        FXMLLoader loader2 = new FXMLLoader();
        Parent grafico2 = loader2.load(getClass().getResource("/componentes/grafico/probabilidade/index.fxml").openStream());
        probabilidadeController = loader2.getController();
        linhaGrafico.getChildren().add(grafico2);

        FXMLLoader loader3 = new FXMLLoader();
        Parent grafico3 = loader3.load(getClass().getResource("/componentes/grafico/relevanciaValores/index.fxml").openStream());
        relevanciaDoisController = loader3.getController();
        linhaGrafico.getChildren().add(grafico3);

        FXMLLoader loader4 = new FXMLLoader();
        Parent grafico4 = loader4.load(getClass().getResource("/componentes/grafico/relevanciaGeral/index.fxml").openStream());
        relevanciaUmController = loader4.getController();
        linhaGrafico.getChildren().add(grafico4);

    }

    public void addLegendaEGraficos() throws IOException {
        linhaGrafico.getChildren().clear();

        addLegenda();
        addGraficos();
    }

    public void setCanShowSingleOption(boolean aux) {
        estatisticasController.setCanShowOptionPane(aux);
        probabilidadeController.setCanShowOptionPane(aux);
        relevanciaUmController.setCanShowOptionPane(aux);
        relevanciaDoisController.setCanShowOptionPane(aux);
        legendaController.setCanShowOptionPane(aux);

        canShowOptionPane = !aux;
    }

    public void setCanShowOptionPane(boolean aux) {
        canShowOptionPane = aux;
    }

    @FXML
    private void opcaoPaneOFF(MouseEvent event) {
        if (canShowOptionPane) {
            opcaoPane.setVisible(false);
            linhaGrafico.getParent().setOpacity(1);

        }
    }

    @FXML
    private void opcaoPaneOn(MouseEvent event) {
        if (canShowOptionPane) {
            opcaoPane.setVisible(true);
            linhaGrafico.getParent().setOpacity(0.2);
        }

    }

    @FXML
    private void copiarButton(ActionEvent event) throws Exception {

        SnapshotParameters spa = new SnapshotParameters();
        spa.setTransform(Transform.scale(1, 1));

        linhaGrafico.getParent().setOpacity(1);
        WritableImage snapshot = linhaGrafico.getParent().snapshot(spa, null);

        ClipboardContent cc = new ClipboardContent();
        cc.putImage(snapshot);

        Clipboard.getSystemClipboard().setContent(cc);

    }

    @FXML
    private void salvarOneButton(ActionEvent event) throws IOException {
        //File Chooser
        String url;

        Stage primaryStage = new Stage();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName("Atributos");

        FileChooser.ExtensionFilter extFilter
                = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
        fileChooser.getExtensionFilters().add(extFilter);

        File selectedFile = fileChooser.showSaveDialog(primaryStage);
        url = selectedFile.getAbsolutePath();

        //Pegando a imagem
        SnapshotParameters spa = new SnapshotParameters();
        spa.setTransform(Transform.scale(10, 10));

        linhaGrafico.getParent().setOpacity(1);

        WritableImage snapshot = linhaGrafico.getParent().snapshot(spa, null);
        BufferedImage image = SwingFXUtils.fromFXImage(snapshot, null);

        //Salvando
        File file = new File(url);
        ImageIO.write(image, "png", file);

    }

    @FXML
    private void salvarTwoButton(ActionEvent event) throws IOException, Exception {

        SnapshotParameters spa = new SnapshotParameters();
        spa.setTransform(Transform.scale(10, 10));

        linhaGrafico.getParent().setOpacity(1);

        WritableImage snapshot = linhaGrafico.getParent().snapshot(spa, null);
        BufferedImage image = SwingFXUtils.fromFXImage(snapshot, null);

        //File Chooser
        String url;

        Stage primaryStage = new Stage();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName("Atributos");

        FileChooser.ExtensionFilter extFilter
                = new FileChooser.ExtensionFilter("SVG files (*.svg)", "*.svg");
        fileChooser.getExtensionFilters().add(extFilter);

        File selectedFile = fileChooser.showSaveDialog(primaryStage);
        url = selectedFile.getAbsolutePath();

        // Cria arquivo
        File file = new File(url);

        // Se o arquivo nao existir, ele gera
        if (!file.exists()) {
            file.createNewFile();
        }

        // Prepara para escrever no arquivo
        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);

        // Escreve e fecha arquivo
        bw.write(ImageTracer.imageToSVG(image, null, null));
        bw.close();
    }

    public WritableImage getBImage(int aux) {
        if (aux == 0) {
            return linhaGrafico.getParent().snapshot(new SnapshotParameters(), null);
        } else if (aux == 1) {
            return legendaController.getBImage();
        } else if (aux == 2) {
            return estatisticasController.getBImage();

        } else if (aux == 3) {
            return probabilidadeController.getBImage();

        } else if (aux == 4) {
            return relevanciaUmController.getBImage();

        } else if (aux == 5) {
            return relevanciaDoisController.getBImage();

        }

        return null;
    }
}