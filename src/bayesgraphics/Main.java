package bayesgraphics;

import componentes.legenda.legendaController;
import componentes.linha.linhaGraficosController;
import componentes.unicaLegenda.unicaLegendaController;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import telas.relatorios.relatoriosController;


public class Main extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResource("/telas/inicializacao/index.fxml").openStream());
        
        /*unicaLegendaController unicaLegendaController = loader.getController();
        unicaLegendaController.setIndexController("MATHEUS", "#3275a8");
        */
        
        /*legendaController legendaController = loader.getController();
        legendaController.addUnicaLegenda("MATHEUS", "#3275a8");
        legendaController.addUnicaLegenda("MATHEUS", "#3275a8");
        legendaController.addUnicaLegenda("MATHEUS", "#3275a8");
        legendaController.addUnicaLegenda("MATHEUS", "#3275a8");*/

       /* linhaGraficosController linhaGraficosController = loader.getController();
        linhaGraficosController.addLegenda();
        linhaGraficosController.addLegenda();
        linhaGraficosController.addLegenda();
        linhaGraficosController.addLegenda();*/
        
       /* relatoriosController relatorioController = loader.getController();
        relatorioController.addLinhas();
        relatorioController.addInfos();*/

        
        
        
        Scene scene = new Scene(root);
        stage.resizableProperty().set(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
