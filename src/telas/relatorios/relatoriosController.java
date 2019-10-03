/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telas.relatorios;

import componentes.grafico.atributos.graficoAtributosController;
import componentes.linha.linhaGraficosController;
import jankovicsandras.imagetracer.ImageTracer;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Transform;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFPicture;
import org.apache.poi.xwpf.usermodel.XWPFRun;

/**
 * FXML Controller class
 *
 * @author Maia
 */
public class relatoriosController implements Initializable {

    //private ;
    private List<WritableImage> listBais = new ArrayList<>();

    @FXML
    private VBox boxDosGraficos;
    @FXML
    private VBox infoPane;

    private List<Parent> linhasParent = new ArrayList<>();
    private List<linhaGraficosController> linhasController = new ArrayList<>();

    private boolean canShowSingleOption;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        clearLinhas();

    }

    private void addUnicaLinhasEGrafico() throws IOException, Exception {
        FXMLLoader loader = new FXMLLoader();
        linhaGraficosController linhaGraficosController;

        Parent linhaGrafico = loader.load(getClass().getResource("/componentes/linha/index.fxml").openStream());

        linhaGraficosController = loader.getController();
        linhaGraficosController.addLegendaEGraficos();
        linhaGraficosController.addLegendaEGraficos();

        linhaGraficosController.addLegendaEGraficos();
        linhaGraficosController.addLegendaEGraficos();
        linhaGraficosController.addLegendaEGraficos();

        linhaGraficosController.setCanShowSingleOption(canShowSingleOption);

        linhasParent.add(linhaGrafico);

        linhasController.add(linhaGraficosController);

        //listBais.add(linhaGraficosController.getBImage());
    }

    private void clearLinhas() {
        boxDosGraficos.getChildren().clear();
    }

    public void addLinhas() throws IOException, Exception {
        linhasParent.clear();

        addUnicaLinhasEGrafico();
        addUnicaLinhasEGrafico();
        addUnicaLinhasEGrafico();
        addUnicaLinhasEGrafico();
        addUnicaLinhasEGrafico();
        addUnicaLinhasEGrafico();
        addUnicaLinhasEGrafico();
        addUnicaLinhasEGrafico();
        addUnicaLinhasEGrafico();
        addUnicaLinhasEGrafico();
        addUnicaLinhasEGrafico();
        addUnicaLinhasEGrafico();
        addUnicaLinhasEGrafico();
        addUnicaLinhasEGrafico();
        addUnicaLinhasEGrafico();
        addUnicaLinhasEGrafico();
        addUnicaLinhasEGrafico();
        addUnicaLinhasEGrafico();
        addUnicaLinhasEGrafico();
        addUnicaLinhasEGrafico();
        addUnicaLinhasEGrafico();
        addUnicaLinhasEGrafico();
        addUnicaLinhasEGrafico();
        addUnicaLinhasEGrafico();
        addUnicaLinhasEGrafico();

        for (int i = 0; i < linhasParent.size(); i++) {
            boxDosGraficos.getChildren().add(linhasParent.get(i));
        }

    }

    public void addInfos() throws IOException {
        infoPane.getChildren().clear();
        //info
        FXMLLoader loader2 = new FXMLLoader();
        Parent info = loader2.load(getClass().getResource("/componentes/info/index.fxml").openStream());
        infoPane.getChildren().add(info);

        //grafico
        FXMLLoader loader = new FXMLLoader();
        Parent Atributos = loader.load(getClass().getResource("/componentes/grafico/atributos/index.fxml").openStream());
        graficoAtributosController atributosController = loader.getController();

        infoPane.getChildren().add(Atributos);

        atributosController.setCanShowOptionPane(true);

    }

    @FXML
    private void MenuSalvarTudoPDF(ActionEvent event) {
    }

    @FXML
    private void MenuSalvarTudoDOC(ActionEvent event) throws FileNotFoundException, IOException, Exception {

        XWPFDocument doc = new XWPFDocument();

        XWPFParagraph para = doc.createParagraph();
        XWPFRun run = para.createRun();

        para.setAlignment(ParagraphAlignment.CENTER);

        /*WritableImage snapshot = linhasController.get(0).getBImage(1);
         BufferedImage image = SwingFXUtils.fromFXImage(snapshot, null);
         run.addPicture(bufferedImagetoInputStrem(image), XWPFDocument.PICTURE_TYPE_PNG, "image file1", Units.toEMU(50), Units.toEMU(100)); // 200x200 pixels

         snapshot = linhasController.get(0).getBImage(2);
         image = SwingFXUtils.fromFXImage(snapshot, null);
         run.addPicture(bufferedImagetoInputStrem(image), XWPFDocument.PICTURE_TYPE_PNG, "image file1", Units.toEMU(140), Units.toEMU(100)); // 200x200 pixels

         snapshot = linhasController.get(0).getBImage(3);
         image = SwingFXUtils.fromFXImage(snapshot, null);
         run.addPicture(bufferedImagetoInputStrem(image), XWPFDocument.PICTURE_TYPE_PNG, "image file1", Units.toEMU(140), Units.toEMU(100)); // 200x200 pixels

         snapshot = linhasController.get(0).getBImage(4);
         image = SwingFXUtils.fromFXImage(snapshot, null);
         run.addPicture(bufferedImagetoInputStrem(image), XWPFDocument.PICTURE_TYPE_PNG, "image file1", Units.toEMU(140), Units.toEMU(100)); // 200x200 pixels

         snapshot = linhasController.get(0).getBImage(5);
         image = SwingFXUtils.fromFXImage(snapshot, null);
         run.addPicture(bufferedImagetoInputStrem(image), XWPFDocument.PICTURE_TYPE_PNG, "image file1", Units.toEMU(140), Units.toEMU(100)); // 200x200 pixels
         */
        for (int i = 0; i < linhasParent.size(); i++) {
            WritableImage snapshot = linhasController.get(i).getBImage(0);
            BufferedImage image = SwingFXUtils.fromFXImage(snapshot, null);
            run.addPicture(bufferedImagetoInputStrem(image), XWPFDocument.PICTURE_TYPE_PNG, "image file1", Units.toEMU(430), Units.toEMU(80)); // 200x200 pixels

            run.addBreak();
        }
        
        //choose pane
        String url;

        Stage primaryStage = new Stage();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName("Relatorio");

        FileChooser.ExtensionFilter extFilter
                = new FileChooser.ExtensionFilter("DOCX files (*.docx)", "*.docx");
        fileChooser.getExtensionFilters().add(extFilter);

        File selectedFile = fileChooser.showSaveDialog(primaryStage);
        url = selectedFile.getAbsolutePath();

        // write word doc to file
        FileOutputStream fos = new FileOutputStream(url);
        doc.write(fos);
        fos.close();
    }

    @FXML
    private void MenuSalvarTudoJPG(ActionEvent event) {
    }

    @FXML
    private void selecionarIndividualAction(ActionEvent event) throws IOException, Exception {
        canShowSingleOption = true;
        clearLinhas();
        addLinhas();
    }

    @FXML
    private void selecionarLinhasAction(ActionEvent event) throws IOException, Exception {
        canShowSingleOption = false;
        clearLinhas();
        addLinhas();
    }

    private ByteArrayInputStream bufferedImagetoInputStrem(BufferedImage image) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        baos.flush();
        ByteArrayInputStream bis = new ByteArrayInputStream(baos.toByteArray());
        baos.close();
        return bis;
    }

}
