package com.example.paints;

import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class Tabs extends Tab {


    public void addTab(TabPane tabPane) {
        Tab tab = new Tab("Tab: " + 1);
        tab.setContent(createTabContent());
        tabPane.getTabs().add(tab);
        tabPane.getSelectionModel().select(tab);
    }

    private Node createTabContent() {
        Canvas canvas = new Canvas(100, 100);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.fillRect(0, 0, 100, 100);

        return canvas;
    }

    public Tab getTabs(TabPane pane) {
        return (Tab) pane.getSelectionModel().getSelectedItem();
    }


    public Canvas getCanvas(GraphicsContext graph) {
        Canvas canvas = new Canvas();
        canvas = graph.getCanvas();

        return canvas;

    }
}

