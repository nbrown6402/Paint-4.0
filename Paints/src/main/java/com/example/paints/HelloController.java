package com.example.paints;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import javafx.stage.Modality;

import java.awt.*;
import java.io.*;
import java.util.Stack;

import static javafx.scene.control.ButtonBar.ButtonData.OK_DONE;

public class HelloController {

    @FXML
    private RadioMenuItem copy;
    @FXML
    private RadioMenuItem paste;
    private Image clipboard;
    @FXML
    private TextField brushSize;

    @FXML
    private Canvas canv;
    @FXML
    private RadioMenuItem col;
    @FXML
    private ColorPicker colorPicker;
    @FXML
    private RadioMenuItem cork;
    @FXML
    private RadioMenuItem dash;
    @FXML
    private RadioMenuItem eraser;
    @FXML
    private RadioMenuItem lin;
    @FXML
    private RadioMenuItem obo;
    @FXML
    private TabPane pane;
    @FXML
    private StackPane panes;
    @FXML
    private RadioMenuItem pencil;
    @FXML
    private RadioMenuItem rect;
    @FXML
    private MenuItem saves;

    @FXML
    private RadioMenuItem sqar;

    @FXML
    private RadioMenuItem npoly;
    @FXML
    private GraphicsContext graph;
    @FXML
    private MenuItem qtbtn;
    @FXML
    private MenuItem undo;
    @FXML
    private MenuItem redo;

    public String direct;
    private PixelReader read;
    boolean smart;

    double[] xCoord, yCoord;  // Arrays to hold the coordinates for up to 500 points.
    int pointCt;              // Number of points in the arrays.
    boolean complete;


    @FXML
    private RadioMenuItem rr;

    Stack<javafx.scene.image.Image> copyHistory = new Stack();
    Stack<javafx.scene.image.Image> undoHistory = new Stack();
    Stack<javafx.scene.image.Image> redoHistory = new Stack();

    //**********************    Open   **************************************\\
    @FXML
    void OpeningImage(ActionEvent event) throws FileNotFoundException {
        smart = false;
        graph = canv.getGraphicsContext2D();
        graph.clearRect(0, 0, canv.getWidth(), canv.getHeight());
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("C:\\Users\\nicol\\Pictures\\images"));
        File pic = fileChooser.showOpenDialog(null);
        direct = pic.toString();
        if (pic != null)
            try {
                InputStream im = new FileInputStream(pic);
                Image open = new Image(im);
                read = open.getPixelReader();
                double oh = open.getHeight();
                double ow = open.getWidth();
                canv.setHeight(oh);
                canv.setWidth(ow);


                graph.drawImage(open, 0, 0);
            } catch (IOException e) {
                System.out.println("NO IMAGE FOR YOU!");
            }
    }

    //*********************    Save As   ************************************\\
    @FXML
    void SavingNewImage(ActionEvent event) {
        new Save().SavingNewImage(event, canv);
    }

    //**********************    Save   **************************************\\
    @FXML
    void save(ActionEvent event) {
        new Save().save(event, smart, direct, canv);
    }

    //***************************    Pencil and Eraser   **************************************\\

    @FXML
    void Draw(ActionEvent event) {
        //initialize(graph.getCanvas());
    }

    private double x, y;
    public void initialize() {
        GraphicsContext graph = canv.getGraphicsContext2D();
        brushSize.setText("10");
        Rectangle rec = new Rectangle();
        Circle circ = new Circle();
        Ellipse elip = new Ellipse();
        Line line = new Line();
        Line lime = new Line();
        Polygon poly = new Polygon();
        Point point = new Point();
        Rectangle rore = new Rectangle();



        Canvas canv = new Tabs().getCanvas(graph);

        xCoord = new double[500];  // create arrays to hold the polygon's points
        yCoord = new double[500];
        pointCt = 0;

        // undoHistory.push(this.getRegion(0,0, canv.getWidth(), canv.getHeight()));

//***************************    Mouse Clicked  **************************************\\
        canv.setOnMouseClicked(e -> {

            smart = false;
            double size = Double.parseDouble(brushSize.getText());
            double x = e.getX();
            double y = e.getY();
            if (col.isSelected()) {
                Color cole = read.getColor((int) x, (int) y);
                colorPicker.setValue(cole);
            }

        });

//***************************    Mouse Pressed  **************************************\\
        canv.setOnMousePressed(e -> {
            x = e.getX();
            y = e.getY();

            smart = false;
            if (rect.isSelected()) {
               new drawing().coloring(graph, colorPicker, brushSize, smart);
                rec.setLocation((int) e.getX(), (int) e.getY());


            } else if (sqar.isSelected()) {
                new drawing().coloring(graph, colorPicker, brushSize, smart);
                rec.setLocation((int) e.getX(), (int) e.getY());


            } else if (cork.isSelected()) {
                new drawing().coloring(graph, colorPicker, brushSize, smart);
                circ.setCenterX(e.getX());
                circ.setCenterY(e.getY());

            } else if (obo.isSelected()) {
                new drawing().coloring(graph, colorPicker, brushSize, smart);
                elip.setCenterX(e.getX());
                elip.setCenterY(e.getY());


            } else if (lin.isSelected()) {
                new drawing().coloring(graph, colorPicker, brushSize, smart);
                line.setStartX(e.getX());
                line.setStartY(e.getY());


            } else if (dash.isSelected()) {
                new drawing().coloring(graph, colorPicker, brushSize, smart);
                lime.setStartX(e.getX());
                lime.setStartY(e.getY());

            } else if (npoly.isSelected()) {

                if (complete) {
                    // Start a new polygon at the point that was clicked.
                    complete = false;
                    xCoord[0] = e.getX();
                    yCoord[0] = e.getY();
                    pointCt = 1;
                }
                else if ( pointCt > 0 && pointCt > 0 && (Math.abs(xCoord[0] - e.getX()) <= 3)
                        && (Math.abs(yCoord[0] - e.getY()) <= 3) ) {
                    // User has clicked near the starting point.
                    // The polygon is complete.
                    complete = true;
                }
                else if (e.getButton() == MouseButton.SECONDARY || pointCt == 500) {
                    // The polygon is complete.
                    complete = true;
                }
                else {
                    // Add the point where the user clicked to the list of
                    // points in the polygon, and draw a line between the
                    // previous point and the current point.  A line can
                    // only be drawn if there are at least two points.
                    xCoord[pointCt] = e.getX();
                    yCoord[pointCt] = e.getY();
                    pointCt++;
                }
                new drawing().draw(canv, pointCt, complete, colorPicker, xCoord, yCoord);  // in all ca
            }


            else if(copy.isSelected()) {
                rec.setLocation((int) e.getX(), (int) e.getY());
            }
            else if (rr.isSelected()){
                new drawing().coloring(graph, colorPicker, brushSize, smart);
                rore.setLocation((int) e.getX(), (int) e.getY());

            }



        });

//***************************    Mouse Dragged   **************************************\\
        canv.setOnMouseDragged(e -> {
            smart = false;
            double size = Double.parseDouble(brushSize.getText());
            double x = e.getX() - size / 2;
            double y = e.getY() - size / 2;
            smart = true;
            if (eraser.isSelected()) {
                graph.clearRect(x, y, size, size);
                add2undo();



            } else if (pencil.isSelected()) {
                smart = false;
                graph.setFill(colorPicker.getValue());
                graph.fillOval(x, y, size, size);
                //graph.stroke();
                add2undo();
            }

        });

//***************************    Mouse Released   **************************************\\
        canv.setOnMouseReleased(e -> {
            if (rect.isSelected()) {
                smart = false;
                graph.setLineDashes(0);
                rec.setSize((int) Math.abs((e.getX() - rec.getX())), (int) Math.abs((e.getY() - rec.getY())));
                if (rec.getX() > e.getX() || rec.getY() > e.getY()) {
                    rec.setLocation((int) rec.getX(), (int) rec.getY());
                }

                graph.setFill(Color.TRANSPARENT);
                graph.fillRect(rec.getX(), rec.getY(), rec.getWidth(), rec.getHeight());
                graph.strokeRect(rec.getX(), rec.getY(), rec.getWidth(), rec.getHeight());
                add2undo();


            } else if (sqar.isSelected()) {
                smart = false;
                graph.setLineDashes(0);
                rec.setSize((int) Math.abs((e.getX() - rec.getX())), (int) Math.abs((e.getY() - rec.getY())));
                if (rec.getX() > e.getX() || rec.getY() > e.getY()) {
                    rec.setLocation((int) rec.getX(), (int) rec.getY());

                }

                graph.setFill(Color.TRANSPARENT);
                graph.fillRect(rec.getX(), rec.getY(), rec.getWidth(), rec.getWidth());
                graph.strokeRect(rec.getX(), rec.getY(), rec.getWidth(), rec.getWidth());
                add2undo();


            } else if (cork.isSelected()) {
                smart = false;
                circ.setRadius((Math.abs(e.getX() - circ.getCenterX()) + Math.abs(e.getY() - circ.getCenterY())) / 2);
                graph.setLineDashes(0);

                if (circ.getCenterX() > e.getX()) {
                    circ.setCenterX(e.getX());
                }
                if (circ.getCenterY() > e.getY()) {
                    circ.setCenterY(e.getY());
                }
                graph.setFill(Color.TRANSPARENT);
                graph.fillOval(circ.getCenterX(), circ.getCenterY(), circ.getRadius(), circ.getRadius());
                graph.strokeOval(circ.getCenterX(), circ.getCenterY(), circ.getRadius(), circ.getRadius());
                add2undo();



            } else if (obo.isSelected()) {
                smart = false;
                elip.setRadiusX((Math.abs(e.getX() - elip.getCenterX())));
                elip.setRadiusY((Math.abs(e.getY() - elip.getCenterY())));
                graph.setLineDashes(0);

                if (elip.getCenterX() > e.getX()) {
                    elip.setCenterX(e.getX());
                }
                if (elip.getCenterY() > e.getY()) {
                    elip.setCenterY(e.getY());
                }
                graph.setFill(Color.TRANSPARENT);
                graph.strokeOval(elip.getCenterX(), elip.getCenterY(), elip.getRadiusX(), elip.getRadiusY());
                graph.fillOval(elip.getCenterX(), elip.getCenterY(), elip.getRadiusX(), elip.getRadiusY());
                add2undo();



            } else if (lin.isSelected()) {
                smart = false;
                line.setEndX(e.getX());
                line.setEndY(e.getY());
                graph.setLineDashes(0);
                graph.strokeLine(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY());
                add2undo();


            } else if (dash.isSelected()) {
                smart = false;
                lime.setEndX(e.getX());
                lime.setEndY(e.getY());
                graph.strokeLine(lime.getStartX(), lime.getStartY(), lime.getEndX(), lime.getEndY());
                add2undo();

            }
            else if (copy.isSelected()) {
                smart = false;
                graph.setLineDashes(0);
                rec.setSize((int) Math.abs((e.getX() - rec.getX())), (int) Math.abs((e.getY() - rec.getY())));
                if (rec.getX() > e.getX() || rec.getY() > e.getY()) {
                    rec.setLocation((int) rec.getX(), (int) rec.getY());
                }

                clipboard = getRegion(rec.getX(), rec.getY(), e.getX(), e.getY());
                add2undo();
            }

            else if (paste.isSelected()) {
                if(clipboard !=null) {
                    try {
                        drawImageAt(clipboard, e, e.getX(), e.getY());
                        add2undo();
                    } catch (Exception f) {
                        System.out.println("AHHHH");
                    }
                }
                else System.out.println("no clipboard");
            }
            else if(rr.isSelected()) {
                rore.setSize((int) Math.abs((e.getX() - rore.getX())), (int) Math.abs((e.getY() - rore.getY())));

                if((rore.getX() > e.getX()) || (rore.getY() > e.getY())){
                    rore.setLocation((int) rore.getX(), (int) rore.getY());

                }
                graph.setLineDashes(0);
                graph.setFill(Color.TRANSPARENT);
                graph.fillRect(rore.getX(), rore.getY(), rore.getWidth(), rore.getHeight());
                graph.strokeRoundRect(rore.getX(), rore.getY(), rore.getWidth(), rore.getHeight(), 20, 30);

                add2undo();

            }

        });

    }
    @FXML
    void undo(ActionEvent event) throws FileNotFoundException {
        undoact(event);

    }

    @FXML
    void redo(ActionEvent event) throws FileNotFoundException {
        redoact(event);

    }



    public void drawImageAt(Image im, MouseEvent eve, double x, double y) throws FileNotFoundException {
    new Save().oi(eve,smart, graph, canv, read, clipboard ,x,y);
    }

    //*******************************    About menu   **************************************\\
    @FXML
    void About(ActionEvent event) {
        new Menus().About(event);
    }

    //*********************************    Help menu   **************************************\\
    @FXML
    void HELP(ActionEvent event) {
        new Menus().HELP(event);
    }

    //********************************    Zoom with mouse   **************************************\\
    @FXML
    void zoom(ScrollEvent event) {

    }

    //*******************   Tabs   ***************************\\
    @FXML
    void tabs(ActionEvent event) {
        new Tabs().addTab(pane);

    }


    //*************************   Smart Save   ************************\\
    @FXML
    void bye(ActionEvent event) throws IOException {
        new Save().bye(event, smart, direct, canv);
    }

    //*************************   Cut Copy Paste?   ************************\\


    @FXML
    void clearcanv(ActionEvent event) throws IOException {
        if (smart == false) {
            //Create an Alert with predefined warning image
            Alert alert = new Alert(Alert.AlertType.WARNING);
//Set text in conveinently pre-defined layout
            alert.setTitle("Warning");
            alert.setHeaderText("Are you sure?");
            alert.setContentText("Do you want to clear the application?");
//Set custom buttons
            ButtonType okButton = new ButtonType("Yes", OK_DONE);
            ButtonType cancelButton = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(okButton, cancelButton);
//Prevent all interaction with application until resolved.
            alert.initModality(Modality.APPLICATION_MODAL);
//Launch
            alert.showAndWait().ifPresent(response -> {
                if (response == okButton) {
                    graph = canv.getGraphicsContext2D();
                    graph.clearRect(0, 0, canv.getWidth(), canv.getHeight());
                }

            });
        } else {
            Platform.exit();
        }
    }

    public Image getRegion(double x1, double y1, double x2, double y2){
        SnapshotParameters snap = new SnapshotParameters();
        WritableImage write = new WritableImage((int)Math.abs(x1 - x2),(int)Math.abs(y1 - y2));

        snap.setViewport(new Rectangle2D(x1, y1, x2, y2));

        canv.snapshot(snap, write);
        return write;
    }

    public void undoact(ActionEvent eve) throws FileNotFoundException {
        Image im = undoHistory.pop();
        if(!undoHistory.empty()){
            redoHistory.push(im);
            new Save().ui(eve,smart, graph, canv, read, undoHistory.peek(), 0,0);
        }
        else{   //puts image back because in this case it's the base/only one in stack
            new Save().ui(eve,smart, graph, canv, read, im, 0,0);
          undoHistory.push(im);
        }
    }

    public void add2undo(){
        Image im = getRegion(0, 0, canv.getWidth(), canv.getHeight());
        undoHistory.push(im);
    }
    /**
     * Redoes the change that you undid and draws to canvas
     */
    public void redoact(ActionEvent eve) throws FileNotFoundException {
        if(!redoHistory.empty()){
            Image im = redoHistory.pop();
            undoHistory.push(im);
            new Save().ui(eve,smart, graph, canv, read, im, 0,0);
        }
    }

}








