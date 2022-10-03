package com.example.paints;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static javafx.scene.control.ButtonBar.ButtonData.OK_DONE;

public class Save {

    //*********************    Save As   ************************************\\
    void SavingNewImage(ActionEvent event, Canvas canv) {
        Stage stage = new Stage();
        FileChooser savefile = new FileChooser();
        savefile.setTitle("Save File");
        savefile.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG ", "*.png"), new FileChooser.ExtensionFilter("JPEG", "*.jpg"),
                new FileChooser.ExtensionFilter("BMP", "*.bmp"));

        File file = savefile.showSaveDialog(stage);
        if (file != null) {
            try {
                WritableImage writableImage = new WritableImage((int) canv.getWidth(), (int) canv.getHeight());
                canv.snapshot(null, writableImage);
                RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                ImageIO.write(renderedImage, "png", file);
            } catch (IOException ex) {
                System.out.println("Error!");
            }
        }
    }

    //**********************    Save   **************************************\\
    void save(ActionEvent event, Boolean smart, String direct, Canvas canv) {
        smart = true;
        File file = new File(direct);

        if (file != null) {
            try {
                WritableImage writableImage = new WritableImage((int) canv.getWidth(), (int) canv.getHeight());
                canv.snapshot(null, writableImage);
                RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                ImageIO.write(renderedImage, "png", file);
            } catch (IOException ex) {
                System.out.println("Error!");
            }
        }

    }


    //************************* Smart save  *********************************\\
    void bye(ActionEvent event, Boolean smart, String direct, Canvas canv) throws IOException {
        if (smart == false) {
            //Create an Alert with predefined warning image
            Alert alert = new Alert(Alert.AlertType.WARNING);
//Set text in conveinently pre-defined layout
            alert.setTitle("Warning");
            alert.setHeaderText("Are you sure?");
            alert.setContentText("Do you want to close the application?");
//Set custom buttons
            ButtonType okButton = new ButtonType("Yes, exit", OK_DONE);
            ButtonType cancelButton = new ButtonType("No, go back to Paint", ButtonBar.ButtonData.CANCEL_CLOSE);
            ButtonType sButton = new ButtonType("Save", OK_DONE);
            ButtonType SAButton = new ButtonType("Save As", OK_DONE);

            alert.getButtonTypes().setAll(okButton, cancelButton, sButton, SAButton);
//Prevent all interaction with application until resolved.
            alert.initModality(Modality.APPLICATION_MODAL);
//Launch
            alert.showAndWait().ifPresent(response -> {
                if (response == okButton) {
                    Platform.exit();
                } else if (response == sButton) {
                    save(event, smart, direct, canv);
                    Platform.exit();
                } else if (response == SAButton) {
                    SavingNewImage(event, canv);
                    Platform.exit();
                }

            });
        } else {
            Platform.exit();
        }
    }

    void oi(MouseEvent event, boolean smart, GraphicsContext graph, Canvas canv, PixelReader read, Image clip, double x, double y) throws FileNotFoundException {
        smart = false;
        graph = canv.getGraphicsContext2D();
        if (clip != null) {
            graph.drawImage(clip, x,y );
        }

    }
    void ui(ActionEvent event, boolean smart, GraphicsContext graph, Canvas canv, PixelReader read, Image clip, double x, double y) throws FileNotFoundException {
        smart = false;
        graph = canv.getGraphicsContext2D();
        if (clip != null) {
            graph.drawImage(clip, x,y );
        }

    }
}
