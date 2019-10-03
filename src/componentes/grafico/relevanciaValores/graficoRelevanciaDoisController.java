/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package componentes.grafico.relevanciaValores;

import jankovicsandras.imagetracer.ImageTracer;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.BarChart;
import javafx.scene.image.WritableImage;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;
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
public class graficoRelevanciaDoisController implements Initializable {

    @FXML
    private VBox opcaoPane;
    @FXML
    private BarChart<?, ?> grafico;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
        fileChooser.setInitialFileName("ValoresGeral");

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
        fileChooser.setInitialFileName("ValoresGeral");

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
}
