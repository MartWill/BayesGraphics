/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helloWorld;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import jankovicsandras.imagetracer.ImageTracer;
import java.awt.image.BufferedImage;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.transform.Transform;

/**
 *
 * @author Maia
 */
public class FXMLIndexController implements Initializable {

    @FXML
    private Label label;

    @FXML
    private void handleButtonAction(ActionEvent event) throws Exception {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
        
        SnapshotParameters spa = new SnapshotParameters();
        spa.setTransform(Transform.scale(10, 10));

        
        WritableImage snapshot = this.label.getParent().snapshot(spa, null);
        BufferedImage image = SwingFXUtils.fromFXImage(snapshot, null);

        System.out.println(ImageTracer.imageToSVG(image,null,null));
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
