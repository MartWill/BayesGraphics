/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fb.bayesGraphics.view.componentes.grafico.probabilidade;

import jankovicsandras.imagetracer.ImageTracer;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.List;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.image.WritableImage;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.transform.Transform;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author Maia
 */
public class graficoProbabilidadeController implements Initializable {

    @FXML
    private BarChart<?, ?> grafico;
    @FXML
    private NumberAxis numericoEixo;
    @FXML
    private CategoryAxis categoriasEixo;
    @FXML
    private VBox opcaoPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        categoriasEixo.setVisible(false);

        /*CONFIGURANDO CATEGORIA EIXO*/
        categoriasEixo.setTickLabelsVisible(false);
        categoriasEixo.setTickMarkVisible(false);

        numericoEixo.setUpperBound(100);
        numericoEixo.setLowerBound(-30);
        numericoEixo.setTickUnit(30);
        /*CONFIGURANDO NUMBER EIXO*/

        grafico.setLegendVisible(false);
        grafico.setMinSize(300, 200);
        grafico.setMaxSize(300, 200);
        grafico.setPrefSize(300, 200);
        grafico.setVerticalGridLinesVisible(false);
        grafico.setHorizontalGridLinesVisible(false);
        grafico.setVerticalZeroLineVisible(false);
    }

    @FXML
    private void copiarButton(ActionEvent event) throws Exception {

        SnapshotParameters spa = new SnapshotParameters();
        spa.setTransform(Transform.scale(10, 10));

        grafico.getParent().setOpacity(1);
        WritableImage snapshot = grafico.getParent().snapshot(spa, null);

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
        fileChooser.setInitialFileName("Probabilidade");

        FileChooser.ExtensionFilter extFilter
                = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
        fileChooser.getExtensionFilters().add(extFilter);

        File selectedFile = fileChooser.showSaveDialog(primaryStage);
        url = selectedFile.getAbsolutePath();

        //Pegando a imagem
        SnapshotParameters spa = new SnapshotParameters();
        spa.setTransform(Transform.scale(10, 10));

        grafico.getParent().setOpacity(1);

        WritableImage snapshot = grafico.getParent().snapshot(spa, null);
        BufferedImage image = SwingFXUtils.fromFXImage(snapshot, null);

        //Salvando
        File file = new File(url);
        ImageIO.write(image, "png", file);

    }

    @FXML
    private void salvarTwoButton(ActionEvent event) throws IOException, Exception {

        SnapshotParameters spa = new SnapshotParameters();
        spa.setTransform(Transform.scale(10, 10));

        grafico.getParent().setOpacity(1);

        WritableImage snapshot = grafico.getParent().snapshot(spa, null);
        BufferedImage image = SwingFXUtils.fromFXImage(snapshot, null);

        //File Chooser
        String url;

        Stage primaryStage = new Stage();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName("Probabilidade");

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

    @FXML

    public boolean canShowOptionPane;

    public void setCanShowOptionPane(boolean aux) {
        canShowOptionPane = aux;
    }

    @FXML
    private void opcaoPaneOFF(MouseEvent event) {
        if (canShowOptionPane) {
            opcaoPane.setVisible(false);
            grafico.getParent().setOpacity(1);

        }
    }

    @FXML
    private void opcaoPaneOn(MouseEvent event) {
        if (canShowOptionPane) {
            opcaoPane.setVisible(true);
            grafico.getParent().setOpacity(0.2);

        }

    }

    public WritableImage getBImage() {
        WritableImage snapshot = this.grafico.getParent().snapshot(new SnapshotParameters(), null);

        return snapshot;
    }

    public void setDados(List<Double> pegandoListaValorProb, List<String> cores) {

        grafico.getData().clear();

        XYChart.Series serie = new XYChart.Series();
        /*CONFIGURANDO SERIE*/

        for (int i = 0; i < pegandoListaValorProb.size(); i++) {
            if (i < cores.size()) {
                serie.getData().add(mostrarValorIndividual("NOME " + i, (pegandoListaValorProb.get(i)), cores.get(i)));
            } else {
                serie.getData().add(mostrarValorIndividual("NOME " + i, (pegandoListaValorProb.get(i)), cores.get(i - cores.size())));
            }
        }

        grafico.getData().add(serie);
    }

    private XYChart.Data mostrarValorIndividual(String country, double value, String cor) {
        XYChart.Data data = new XYChart.Data(country, value);

        String text = new DecimalFormat("0.0").format(value) + "%";

        StackPane node = new StackPane();
        Label label = new Label(text);
        label.fontProperty().setValue(new Font(" Serif", 20));

        Group group = new Group(label);
        StackPane.setAlignment(group, Pos.TOP_CENTER);
        //StackPane.setMargin(group, new Insets(0, 0, 30, 0));
        node.getChildren().add(group);
        data.setNode(node);

        data.getNode().setStyle("-fx-bar-fill:" + cor + ";");
        return data;
    }
}
