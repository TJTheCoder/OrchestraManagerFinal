/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.orchestramanagerfinal;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author tekkale_905701
 */
public class GraphicPanel extends JPanel {

    //margin buffers for how seperated the staff needs to be from
    //the border and the range of the staff lines
    int xMarginBuffer = 50;
    int yMarginStart = 175;
    int yMarginEnd = 450;

    //sets the maximum length of the staff
    //int far = 960;
    int far = 10000;
    
    int runningCount = 5;

    //integers to store the coordinates of the user's mouse
    int mouseX;
    int mouseY;

    //calls the repaint() method
    public void paint() {
        repaint();
    }

    //shifts all horizontal elements over by modifying the xMarginBuffer and far
    public void frameShift(int shift) {
        xMarginBuffer -= shift;
        if (xMarginBuffer > 50) {
            xMarginBuffer = 50;
            repaint();
            return;
        }
        repaint();
    }

    //setter for mouseX and mouseY
    public void updateMouseCoords(int x, int y) {
        mouseX = x;
        mouseY = y;
        repaint();
    }

    //takes a fraction for an integer
    public int fraction(int value, int num, int denom) {
        return (value * num) / denom;
    }

    //determines the location of the closest x-snap point
    public int closestXSnap(int x) {
        /*
        if (x < xMarginBuffer) {
            return xMarginBuffer;
        }
        if (x > far) {
            return far;
        }

        int filter = x % 50;
        int buffer = x / 50;

        if (filter < 25) {
            return (buffer) * 50;
        } else {
            return (buffer + 1) * 50;
        }
        */
        return runningCount * 50;
    }

    public int closestYSnap(int y) {
        if (y < yMarginStart) {
            return yMarginStart;
        } else if (y > yMarginEnd) {
            return yMarginEnd;
        }

        y -= yMarginStart;

        int filter = y % fraction(yMarginEnd - yMarginStart, 1, 8);
        int buffer = y / fraction(yMarginEnd - yMarginStart, 1, 8);

        if (filter < fraction(yMarginEnd - yMarginStart, 1, 16)) {
            return (buffer * fraction(yMarginEnd - yMarginStart, 1, 8)) + yMarginStart;
        } else {
            return ((buffer + 1) * fraction(yMarginEnd - yMarginStart, 1, 8)) + yMarginStart;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        // Clear all of the panel content before drawing
        super.paintComponent(g);

        //creates and displays the treble clef icon on the composer
        BufferedImage clefIcon = null;
        try {
            clefIcon = ImageIO.read(new File("images\\trebleClef.png"));
        } catch (IOException ex) {
            Logger.getLogger(GraphicPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        Image treble = clefIcon.getScaledInstance(150, yMarginEnd - yMarginStart, Image.SCALE_SMOOTH);
        g.drawImage(treble, xMarginBuffer, yMarginStart, this);

        //draws the vertical staff lines to start with and returns the color to black
        g.setColor(Color.black);
        g.drawLine(xMarginBuffer, yMarginStart, xMarginBuffer, yMarginEnd);
        g.drawLine(far, yMarginStart, far, yMarginEnd);

        //draws the five lines of the staff
        g.drawLine(xMarginBuffer, yMarginStart, far, yMarginStart);
        g.drawLine(xMarginBuffer, yMarginStart + fraction(yMarginEnd - yMarginStart, 1, 4), far, yMarginStart + fraction(yMarginEnd - yMarginStart, 1, 4));
        g.drawLine(xMarginBuffer, yMarginStart + fraction(yMarginEnd - yMarginStart, 2, 4), far, yMarginStart + fraction(yMarginEnd - yMarginStart, 2, 4));
        g.drawLine(xMarginBuffer, yMarginStart + fraction(yMarginEnd - yMarginStart, 3, 4), far, yMarginStart + fraction(yMarginEnd - yMarginStart, 3, 4));
        g.drawLine(xMarginBuffer, yMarginStart + fraction(yMarginEnd - yMarginStart, 4, 4), far, yMarginStart + fraction(yMarginEnd - yMarginStart, 4, 4));

        //draws an oval at the closest point--done at the end to be on the top
        g.setColor(Color.red);
        g.fillOval(closestXSnap(mouseX) - 5, closestYSnap(mouseY) - 5, 10, 10);
    }
}
