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
    
    //changes what is currently displayed
    //0 - quarter note
    //1 - half note
    //2 - whole note
    //3 - quarter rest
    //4 - half rest
    //5 - whole rest
    int currentSet = 0;

    //boolean copies of the restMode and playMode values in the MainWindow
    private boolean restMode = false;
    private boolean playMode = false;

    //sets the maximum length of the staff
    //int far = 960;
    int far = 10000;

    //the index of the last placed note
    int runningCount = 5;

    //how much shift has been applied so far
    int shiftTotal = 0;

    //integers to store the coordinates of the user's mouse
    int mouseX;
    int mouseY;

    //calls the repaint() method
    public void paint() {
        repaint();
    }

    public void changeCurrentSet(int newSet)
    {
        currentSet = newSet;
    }
    
    //shifts all horizontal elements over by modifying the xMarginBuffer and far
    public void frameShift(int shift) {
        xMarginBuffer -= shift;
        if (xMarginBuffer > 50) {
            xMarginBuffer = 50;
            repaint();
            return;
        }
        shiftTotal += shift;
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

    //toggles for the restMode and playMode class variables
    public void toggleRest() {
        restMode = !restMode;
        
        //allows toggling of the notes/rests
        currentSet += 3;
        if (currentSet > 5)
        {
            switch (currentSet)
            {
                case 6 -> currentSet = 0;
                case 7 -> currentSet = 1;
                case 8 -> currentSet = 2;
            }
        }
    }

    public void togglePlay() {
        playMode = !playMode;
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
        return (runningCount * 50) - shiftTotal;
    }

    //determines the location of the closest y snap point
    public int closestYSnap(int y) {
        if (restMode) {
            return yMarginStart + fraction(yMarginEnd - yMarginStart, 1, 2);
        }

        if (y < yMarginStart)
        {
            return yMarginStart;
        }
        else if (y > yMarginEnd)
        {
            //return yMarginEnd;
            return (8 * fraction(yMarginEnd - yMarginStart, 1, 8)) + yMarginStart;
        }

        y -= yMarginStart;

        int filter = y % fraction(yMarginEnd - yMarginStart, 1, 8);
        int buffer = y / fraction(yMarginEnd - yMarginStart, 1, 8);
        
        System.out.println(buffer);

        if (filter < fraction(yMarginEnd - yMarginStart, 1, 16)) {
            return (buffer * fraction(yMarginEnd - yMarginStart, 1, 8)) + yMarginStart;
        } 
        else
        {
            return ((buffer + 1) * fraction(yMarginEnd - yMarginStart, 1, 8)) + yMarginStart;
        }
    }

    //returns an image--encapsulates the process of creating a bufferedImage
    public Image summon(String filePath, int width, int height) {
        BufferedImage icon = null;
        try {
            icon = ImageIO.read(new File(filePath));
        } catch (IOException ex) {
            Logger.getLogger(GraphicPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        Image img = icon.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return img;
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

        //draws a note/rest at the closest point--done at the end to be on the top
        //uses a case-switch to determine what to draw
        g.setColor(Color.red);
        //g.fillOval(closestXSnap(mouseX) - 5, closestYSnap(mouseY) - 5, 10, 10);
        
        
        switch (currentSet)
        {
            case 0 -> g.drawImage(summon("notes\\noteQ.png",40, 50), closestXSnap(mouseX) - 5, closestYSnap(mouseY) - 40, this);
            case 1 -> g.drawImage(summon("notes\\noteH.png",60, 60), closestXSnap(mouseX) - 5, closestYSnap(mouseY) - 45, this);
            case 2 -> g.drawImage(summon("notes\\noteW.png",50, 50), closestXSnap(mouseX) - 5, closestYSnap(mouseY) - 25, this);
            case 3 -> g.drawImage(summon("notes\\restQ.png",40, 60), closestXSnap(mouseX) - 5, closestYSnap(mouseY) - 20, this);
            case 4 -> g.drawImage(summon("notes\\restH.png",50, 50), closestXSnap(mouseX) - 5, closestYSnap(mouseY) - 32, this);
            case 5 -> g.drawImage(summon("notes\\restW.png",50, 50), closestXSnap(mouseX) - 5, closestYSnap(mouseY) - 15, this);
        }
    }
}
