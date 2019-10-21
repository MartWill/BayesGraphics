/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telas.relatorios;

import algoritmos.naivebayes.naiveBayesAlgorithm;
import componentes.grafico.atributos.graficoAtributosController;
import componentes.grafico.estatisticas.graficoEstatisticasController;
import componentes.grafico.graficoController;
import componentes.info.InfoController;
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
import javafx.scene.layout.HBox;
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

    List<Double> relevanciaAux = new ArrayList<>();

    //private ;
    private List<WritableImage> listBais = new ArrayList<>();

    @FXML
    private VBox boxDosGraficos;
    @FXML
    private VBox infoPane;

    private List<Parent> linhasParent = new ArrayList<>();
    private List<linhaGraficosController> linhasController = new ArrayList<>();

    public naiveBayesAlgorithm nb;

    private boolean canShowSingleOption;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        clearLinhas();

    }

    private void addUnicaLinhasEGrafico(List<String> listaNomesValores, List<Double> pegandoListaValorProb, List<Double> pegarListaValorNaive, List<Double> pegarListaDiffAtributos, List<Double> pegarListaDiffGeral, String listaNomesAtributo) throws IOException, Exception {
        FXMLLoader loader = new FXMLLoader();
        linhaGraficosController linhaGraficosController;

        Parent linhaGrafico = loader.load(getClass().getResource("/componentes/linha/index.fxml").openStream());

        linhaGraficosController = loader.getController();
        linhaGraficosController.addLegendaEGraficos(listaNomesValores, pegandoListaValorProb, pegarListaValorNaive, pegarListaDiffAtributos, pegarListaDiffGeral, listaNomesAtributo);
        linhaGraficosController.setCanShowSingleOption(canShowSingleOption);

        linhasParent.add(linhaGrafico);

        linhasController.add(linhaGraficosController);

        //linhasController.add(0, linhaGraficosController);
        //listBais.add(linhaGraficosController.getBImage());
    }

    private void clearLinhas() {
        boxDosGraficos.getChildren().clear();
        relevanciaAux.clear();
    }

    public void addLinhas() throws IOException, Exception {
        linhasParent.clear();

       /* List<Double> aux = new ArrayList<>();
        List<String> aux2 = new ArrayList<>();

        for (int i = 0; i < nb.getListaNomesAtributos(false).size(); i++) {
            for (int j = 0; j < aux.size(); j++) {
                if (j == aux.size() - 1 && nb.pegarListaDiffGeral(i).get(0) < aux.get(j)) {
                    aux.add(nb.pegarListaDiffGeral(i).get(0));
                    aux2.add(nb.getListaNomesAtributos(false).get(i).toUpperCase());
                }
                if (nb.pegarListaDiffGeral(i).get(0) > aux.get(j)) {
                    aux.add(j, nb.pegarListaDiffGeral(i).get(0));
                    aux2.add(j, nb.getListaNomesAtributos(false).get(i).toUpperCase());
                }
            }
            if (i == 0) {
                aux.add(nb.pegarListaDiffGeral(i).get(0));
                aux2.add(nb.getListaNomesAtributos(false).get(i).toUpperCase());
            }
        }*/

        for (int i = 0; i < nb.getListaNomesAtributos(false).size(); i++) {
            /*for (int j = 0; j < nb.getListaNomesAtributos(false).size(); j++) {
                if (aux2.get(j).equals(nb.getListaNomesAtributos(false).get(i).toUpperCase())) {*/
                    addUnicaLinhasEGrafico(
                            nb.getListaNomesValores(i, false),
                            nb.pegandoListaValorProb(i),
                            nb.pegarListaValorNaive(i),
                            nb.pegarListaDiffAtributos(i),
                            nb.pegarListaDiffGeral(i),
                            nb.getListaNomesAtributos(false).get(i).toUpperCase()
                    );
                }
            /*}

        }*/

        for (int i = 0; i < linhasParent.size(); i++) {
            boxDosGraficos.getChildren().add(linhasParent.get(i));
        }

    }

    public void addInfos() throws IOException {
        infoPane.getChildren().clear();
        //info
        FXMLLoader loader2 = new FXMLLoader();
        Parent info = loader2.load(getClass().getResource("/componentes/info/index.fxml").openStream());
        InfoController infoController = loader2.getController();
        infoController.UpdateInfoController(nb.getNomeArquivo(), nb.getNInstanciaArquivo(), nb.getNAtributoArquivo(), nb.getAtributoVariavelAlvoString(), nb.getAtributoValorAlvo());
        infoPane.getChildren().add(info);

        List<String> cores = new ArrayList<>();
        cores.clear();
        cores.add("#f59342");
        cores.add("#fae034");
        cores.add("#7dfa34");
        cores.add("#34f7c6");
        cores.add("#7935f0");

        //ESTATISTICAS
        FXMLLoader loaderInfo = new FXMLLoader();
        Parent grafico1 = loaderInfo.load(getClass().getResource("/componentes/grafico/index.fxml").openStream());
        graficoController atributosController2 = loaderInfo.getController();
        atributosController2.setDados(nb.pegarListValoresVariavelAlvo(), cores, nb.getListaNomesValores(nb.getAtributoVariavelAlvo(), true));
        infoPane.getChildren().add(grafico1);

        atributosController2.setCanShowOptionPane(true);

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
