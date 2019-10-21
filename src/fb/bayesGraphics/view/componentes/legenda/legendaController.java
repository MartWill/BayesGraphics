/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package componentes.legenda;

import componentes.unicaLegenda.unicaLegendaController;
import jankovicsandras.imagetracer.ImageTracer;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Transform;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * FXML Controller class
 *
 * @author Maia
 */
public class legendaController implements Initializable {

    @FXML
    private VBox boxLegendas;
    @FXML
    private Pane opcaoPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }

    public void addUnicaLegenda(String title, String color) throws IOException {
        FXMLLoader loader = new FXMLLoader();

        Parent unicaLegenda = loader.load(getClass().getResource("/componentes/unicaLegenda/index.fxml").openStream());

        unicaLegendaController unicaLegendaController = loader.getController();
        unicaLegendaController.setIndexController(title, color);

        boxLegendas.getChildren().add(unicaLegenda);
    }

    @FXML
    private void copiarButton(ActionEvent event) throws Exception {

        SnapshotParameters spa = new SnapshotParameters();
        spa.setTransform(Transform.scale(10, 10));
        this.boxLegendas.getParent().setOpacity(1);
        WritableImage snapshot = this.boxLegendas.getParent().snapshot(spa, null);

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
        fileChooser.setInitialFileName("Legenda");

        FileChooser.ExtensionFilter extFilter
                = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
        fileChooser.getExtensionFilters().add(extFilter);

        File selectedFile = fileChooser.showSaveDialog(primaryStage);
        url = selectedFile.getAbsolutePath();

        //Pegando a imagem
        SnapshotParameters spa = new SnapshotParameters();
        spa.setTransform(Transform.scale(10, 10));
        this.boxLegendas.getParent().setOpacity(1);
        WritableImage snapshot = this.boxLegendas.getParent().snapshot(spa, null);
        BufferedImage image = SwingFXUtils.fromFXImage(snapshot, null);

        //Salvando
        File file = new File(url);
        ImageIO.write(image, "png", file);

    }

    @FXML
    private void salvarTwoButton(ActionEvent event) throws IOException, Exception {

        SnapshotParameters spa = new SnapshotParameters();
        spa.setTransform(Transform.scale(10, 10));
        this.boxLegendas.getParent().setOpacity(1);
        WritableImage snapshot = this.boxLegendas.getParent().snapshot(spa, null);
        BufferedImage image = SwingFXUtils.fromFXImage(snapshot, null);

        //File Chooser
        String url;

        Stage primaryStage = new Stage();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName("Legenda");

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
            this.boxLegendas.getParent().setOpacity(1);
        }
    }

    @FXML
    private void opcaoPaneOn(MouseEvent event) {
        if (canShowOptionPane) {
            opcaoPane.setVisible(true);
            this.boxLegendas.getParent().setOpacity(0.2);
        }

    }

    public WritableImage getBImage() {
        WritableImage snapshot = this.boxLegendas.getParent().snapshot(new SnapshotParameters(), null);

        return snapshot;
    }

}
