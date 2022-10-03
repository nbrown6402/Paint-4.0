package com.example.paints;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class drawing {

    public void coloring(GraphicsContext graph, ColorPicker colorPicker, TextField brushSize, boolean smart){
        smart = false;
        graph.setStroke(colorPicker.getValue());
        graph.setFill(colorPicker.getValue());
        graph.setLineWidth(Double.parseDouble(brushSize.getText()));
    }



    void draw(Canvas canvas, int pointCt, boolean complete, ColorPicker colorPicker, double[] xCoord, double[] yCoord) {
        GraphicsContext g = canvas.getGraphicsContext2D();
        g.setFill(Color.WHITE);
        g.fillRect(0,0,canvas.getWidth(),canvas.getHeight());
        if (pointCt == 0)
            return;
        g.setLineWidth(2);
        g.setStroke(Color.BLACK);
        if (complete) { // draw a polygon
            g.setFill(colorPicker.getValue());
            g.fillPolygon(xCoord, yCoord, pointCt);
            g.strokePolygon(xCoord, yCoord, pointCt);
        }
        else { // show the lines the user has drawn so far
            g.setFill(Color.BLACK);
            g.fillRect(xCoord[0]-2, yCoord[0]-2, 4, 4);  // small square marks first point
            for (int i = 0; i < pointCt - 1; i++) {
                g.strokeLine( xCoord[i], yCoord[i], xCoord[i+1], yCoord[i+1]);
            }
        }
    }



}

