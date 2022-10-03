package com.example.paints;

import javafx.event.ActionEvent;

import java.awt.*;
import java.io.File;

public class Menus{
    //*******************************    About menu   **************************************\\
    void About(ActionEvent event) {

        File file = new File("C:\\Users\\nicol\\Desktop\\Cs250\\Paints\\src\\main\\assets\\About.txt");
        Desktop desktop = Desktop.getDesktop();
        if (file.exists())
            try {
                desktop.open(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
    //*******************************    Help menu   **************************************\\
    void HELP(ActionEvent event) {
        File file = new File("C:\\Users\\nicol\\Desktop\\Cs250\\Paints\\src\\main\\assets\\HELP.txt");
        Desktop desktop = Desktop.getDesktop();
        if (file.exists())
            try {
                desktop.open(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
}
