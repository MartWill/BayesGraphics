package fb.bayesGraphics;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        String urlLoader = "/fb/bayesGraphics/view/StartWindow.fxml";
        //String urlLoader = "/fb/bayesGraphics/view/componentes/grafico/atributos/index.fxml";
            
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResource(urlLoader).openStream());

        Scene scene = new Scene(root);
        //stage.resizableProperty().set(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
