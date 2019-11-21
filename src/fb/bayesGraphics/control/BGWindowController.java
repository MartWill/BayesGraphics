package fb.bayesGraphics.control;

import algoritmos.naivebayes.naiveBayesAlgorithm;
import fb.bayesGraphics.view.componentes.grafico.graficoController;
import fb.bayesGraphics.view.componentes.info.InfoController;
import fb.bayesGraphics.view.componentes.linha.linhaGraficosController;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
import javafx.scene.image.WritableImage;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class BGWindowController implements Initializable {

    List<Double> relevanciaAux = new ArrayList<>();

    private final List<WritableImage> listBais;

    @FXML
    private VBox boxDosGraficos;
    @FXML
    private VBox infoPane;

    private final List<Parent> linhasParent = new ArrayList<>();
    private final List<linhaGraficosController> linhasController = new ArrayList<>();

    public naiveBayesAlgorithm nb;

    private boolean canShowSingleOption;

    public BGWindowController() {
        this.listBais = new ArrayList<>();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        clearLinhas();
    }

    private void addUnicaLinhasEGrafico(List<String> listaNomesValores, List<Double> pegandoListaValorProb, List<Double> pegarListaValorNaive, List<Double> pegarListaDiffAtributos, List<Double> pegarListaDiffGeral, String listaNomesAtributo) throws IOException, Exception {
        FXMLLoader loader = new FXMLLoader();
        linhaGraficosController linhaGraficosController;

        Parent linhaGrafico = loader.load(getClass().getResource("/fb/bayesGraphics/view/componentes/linha/index.fxml").openStream());

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

        //Lista com as relevancias
        List<Double> relevancias = new ArrayList<>();
        List<Integer> novasPosicoes = new ArrayList<>();
        
        for (int i = 0; i < nb.getListaNomesAtributos(false).size(); i++) {
            relevancias.add(nb.pegarListaDiffGeral(i).get(0));
            novasPosicoes.add(i);
        }

        double aux = 0f;
        int aux2 = 0;

        //Ordenar essa lista (crescente) (futuramente descrecente)
        for (int i = 0; i < relevancias.size(); i++) {
            for (int j = i; j < relevancias.size(); j++) {
                if (relevancias.get(i) < relevancias.get(j)) {
                    aux = relevancias.get(i);
                    aux2 = novasPosicoes.get(i);
                    
                    relevancias.set(i, relevancias.get(j));
                    novasPosicoes.set(i, novasPosicoes.get(j));

                    relevancias.set(j, aux);
                    novasPosicoes.set(j, aux2);

                }
            }
        }

        int j;
        for (int i = 0; i < nb.getListaNomesAtributos(false).size(); i++) {
            
            
            addUnicaLinhasEGrafico(
                    nb.getListaNomesValores(i, false),
                    nb.pegandoListaValorProb(i),
                    nb.pegarListaValorNaive(i),
                    nb.pegarListaDiffAtributos(i),
                    nb.pegarListaDiffGeral(i),
                    nb.getListaNomesAtributos(false).get(i).toUpperCase()
            );
        }
        
        for (int i = 0; i < linhasParent.size(); i++) {
            
            j =   novasPosicoes.get(i);
            boxDosGraficos.getChildren().add(linhasParent.get(j));
        }
    }

    public void addInfos() throws IOException {
        infoPane.getChildren().clear();
        //info
        FXMLLoader loader2 = new FXMLLoader();
        Parent info = loader2.load(getClass().getResource("/fb/bayesGraphics/view/componentes/info/index.fxml").openStream());
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
        Parent grafico1 = loaderInfo.load(getClass().getResource("/fb/bayesGraphics/view/componentes/grafico/index.fxml").openStream());
        graficoController atributosController2 = loaderInfo.getController();
        atributosController2.setDados(nb.pegarListValoresVariavelAlvo(), cores, nb.getListaNomesValores(nb.getAtributoVariavelAlvo(), true));
        //infoPane.getChildren().add(grafico1);

        atributosController2.setCanShowOptionPane(true);

    }

    @FXML
    private void MenuSalvarTudoPDF(ActionEvent event) {
    }

    @FXML
    private void MenuSalvarTudoDOC() throws FileNotFoundException, IOException, Exception {

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
        
        //Lista com as relevancias
        List<Double> relevancias = new ArrayList<>();
        List<Integer> novasPosicoes = new ArrayList<>();
        
        for (int i = 0; i < nb.getListaNomesAtributos(false).size(); i++) {
            relevancias.add(nb.pegarListaDiffGeral(i).get(0));
            novasPosicoes.add(i);
        }

        double aux = 0f;
        int aux2 = 0;

        //Ordenar essa lista (crescente) (futuramente descrecente)
        for (int i = 0; i < relevancias.size(); i++) {
            for (int j = i; j < relevancias.size(); j++) {
                if (relevancias.get(i) < relevancias.get(j)) {
                    aux = relevancias.get(i);
                    aux2 = novasPosicoes.get(i);
                    
                    relevancias.set(i, relevancias.get(j));
                    novasPosicoes.set(i, novasPosicoes.get(j));

                    relevancias.set(j, aux);
                    novasPosicoes.set(j, aux2);

                }
            }
        }

        int j;
        
        for (int i = 0; i < linhasParent.size(); i++) {
            j =   novasPosicoes.get(i);
            
            WritableImage snapshot = linhasController.get(j).getBImage(0);
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
