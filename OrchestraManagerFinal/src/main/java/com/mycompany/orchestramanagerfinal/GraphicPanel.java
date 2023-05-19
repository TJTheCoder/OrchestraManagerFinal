/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.orchestramanagerfinal;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
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

    //initializes a noteList
    NoteList list = new NoteList(120);

    //controller for the tempo--right now it is constant
    int tempo = 120;

    //margin buffers for how seperated the staff needs to be from
    //the border and the range of the staff lines
    int xMarginBuffer = 50;
    int yMarginStart = 175;
    int yMarginEnd = 450;

    //value that stores the current tier of the playable note
    int tier = 0;

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

    //determines whether or not the playing should continue
    boolean shouldPlay = true;

    //how much shift has been applied so far
    int shiftTotal = 0;

    //integers to store the coordinates of the user's mouse
    int mouseX;
    int mouseY;

    //0 - Add
    //1 - Delete
    int changeType = 0;

    //sets the instrument to be played
    String instrument = "PIANO";

    //calls the repaint() method
    public void paint() {
        repaint();
    }

    public void setInstrument(String newInstrument) {
        instrument = newInstrument;
    }

    //determines the add/delete/edit behavior of the GUI
    public void typeChange(int type)
    {
        changeType = type;
    }
    
    //returns the entire list of the GraphicPanel
    public String getList() {
        return "" + list;
    }

    //sets the listFauz based on a string input
    public void setList(String forcedList) {

        NoteList listFauz = new NoteList(120);
        String[] splitten = forcedList.split(" ");

        runningCount = 5;
        xMarginBuffer = 50;
        shiftTotal = 0;

        for (String present : splitten) {
            if (!present.contains("T")) {
                Note lego = new Note(getLetter(present), getDegree(present), runningCount);
                listFauz.addNode(lego);
                runningCount += 2;
                frameShift(100);
            }
        }

        //System.out.println();
        list = listFauz;

        repaint();
    }

    //gets the letter from String information
    public String getLetter(String splitten) {
        splitten = splitten.strip();
        if (splitten.contains("R")) {
            return "R";
        } else {
            return splitten.substring(0, 2);
        }
    }

    //gets the degree from String information
    public int getDegree(String splitten) {
        splitten = splitten.strip();
        String used = "";
        if (splitten.contains("R")) {
            used = splitten.substring(1, 2);
        } else {
            used = splitten.substring(2, 3);
        }

        int out = 4;

        switch (used) {
            case "w" ->
                out = 1;
            case "h" ->
                out = 2;
            case "q" ->
                out = 4;
        }
        return out;
    }

    public void fastForward() {
        int calculate = runningCount - 5;
        calculate /= 2;
        frameShift(calculate * 100);
    }

    //takes you all the way to the beginning
    public void fastBackward() {
        frameShift(-1 * shiftTotal);
    }

    //turns the tier variable into a letter
    public String determineLetter() {
        String out = "";
        if (restMode) {
            return "R";
        }
        switch (tier) {
            case 0 ->
                out = "F6";
            case 1 ->
                out = "E6";
            case 2 ->
                out = "D6";
            case 3 ->
                out = "C6";
            case 4 ->
                out = "B5";
            case 5 ->
                out = "A5";
            case 6 ->
                out = "G5";
            case 7 ->
                out = "F5";
            case 8 ->
                out = "E5";
        }
        return out;
    }

    //switch expression to determine the degree for creating a node
    public int determineDegree() {
        return switch (currentSet) {
            case 0, 3 ->
                4;
            case 1, 4 ->
                2;
            default ->
                1;
        };
    }

    //adds a noteNode to the listFauz when the staff is clicked on
    public void updateList() {
        if (changeType == 0) {
            Note lego = new Note(determineLetter(), determineDegree(), runningCount);
            list.addNode(lego);
            runningCount += 2;
            frameShift(100);

            Fugue fug = new Fugue("I[" + instrument + "] " + lego);
            fug.sing();

            repaint();
        }
    }

    //modify the currentSet class variable
    public void changeCurrentSet(int newSet) {
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
        /*
        else if (xMarginBuffer > 50 + (runningCount - 5) * 100)
        {
            xMarginBuffer = 50 + (runningCount - 5) * 100;
            repaint();
            return;
        }
         */
        shiftTotal += shift;
        repaint();
    }

    /*
    //returns a colored version of an image
    private static BufferedImage colorImage(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        WritableRaster raster = image.getRaster();

        for (int xx = 0; xx < width; xx++) {
            for (int yy = 0; yy < height; yy++) {
                int[] pixels = raster.getPixel(xx, yy, (int[]) null);
                pixels[0] = 0;
                pixels[1] = 255;
                pixels[2] = 255;
                raster.setPixel(xx, yy, pixels);
            }
        }
        return image;
    }
     */
    //new version of the colorImage method
    public BufferedImage colorImage(BufferedImage loadImg, int red, int green, int blue) {
        BufferedImage img = new BufferedImage(loadImg.getWidth(), loadImg.getHeight(),
                BufferedImage.TRANSLUCENT);
        Graphics2D graphics = img.createGraphics();
        Color newColor = new Color(red, green, blue, 0 /* alpha needs to be zero */);
        graphics.setXORMode(newColor);
        graphics.drawImage(loadImg, null, 0, 0);
        graphics.dispose();
        return img;
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
        if (currentSet > 5) {
            switch (currentSet) {
                case 6 ->
                    currentSet = 0;
                case 7 ->
                    currentSet = 1;
                case 8 ->
                    currentSet = 2;
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

    //returns the tier associated with each letter in the notes
    public int getTierFromLetter(Note data) {
        int tierFake = 0;
        switch (data.getClip()) {
            case "F6" ->
                tierFake = 0;
            case "E6" ->
                tierFake = 1;
            case "D6" ->
                tierFake = 2;
            case "C6" ->
                tierFake = 3;
            case "B5" ->
                tierFake = 4;
            case "A5" ->
                tierFake = 5;
            case "G5" ->
                tierFake = 6;
            case "F5" ->
                tierFake = 7;
            case "E5" ->
                tierFake = 8;
        }
        return tierFake;
    }

    //setter for the shouldPlay boolean
    public void setShouldPlay(boolean shouldPlay) {
        this.shouldPlay = shouldPlay;
    }

    //starts when the play button is triggered
    public void startPlay() throws InterruptedException {
        //temporary hides the note by changing the currentSet
        int store = currentSet;
        currentSet = 6;

        String total = "" + list;
        String[] parts = total.split(" ");
        Fugue fug = new Fugue(parts[0]);

        String test = "";
        for (String comp : parts) {
            test += comp + " ";
        }

        System.out.println(test);
        //System.out.println(shiftTotal);

        //tracks the current offset caused by the frameshift (how many notes from the beginning the play bar is)
        int offset = shiftTotal / 100;

        //int i = 1 + offset;
        int i = 1 + offset;
        while (i < parts.length && shouldPlay) {
            fug.setOutput("I[" + instrument + "] " + parts[i]);
            fug.sing();

            //present here because of a bug in Fugue where it skips over independent rests
            switch (parts[i].trim()) {
                /*
                case "Rq" -> Thread.sleep(fraction(6000 / tempo, 1, 1));
                case "Rh" -> Thread.sleep(fraction(6000 / tempo, 2, 1));
                case "Rw" -> Thread.sleep(fraction(6000 / tempo, 4, 1));
                 */
                case "Rq" ->
                    Thread.sleep(500);
                case "Rh" ->
                    Thread.sleep(1000);
                case "Rw" ->
                    Thread.sleep(2000);
            }

            i++;

            //makes sure that the placable note does not show
            currentSet = 6;
            frameShift(100);

            super.paintImmediately(0, 0, 1009, 599);
        }
        currentSet = store;
    }

    /*
    //actually runs the playing of the song
    public void runPlay(String remainder) throws InterruptedException {
        String run = remainder.split(" ")[1];

        Fugue fug = new Fugue(run);
        fug.sing();

        switch (run) {
            case "Rq" ->
                Thread.sleep(fraction(6000 / tempo, 1, 1));
            case "Rh" ->
                Thread.sleep(fraction(6000 / tempo, 2, 1));
            case "Rw" ->
                Thread.sleep(fraction(6000 / tempo, 4, 1));
        }
        
        frameShift(100);
        
        if (remainder.split(" ").length > 1)
        {
            runPlay(remainder.substring(run.length() + 1));
        }
    }
     */
    //returns the y-value associated with each tier in the panel
    public int getYFromTier() {
        return (tier * fraction(yMarginEnd - yMarginStart, 1, 8)) + yMarginStart;
    }

    //clone of the getYFromTier() method to read in inputs
    public int getYFromTierFake(int tierFake) {
        return (tierFake * fraction(yMarginEnd - yMarginStart, 1, 8)) + yMarginStart;
    }

    //combines methods to read the letter into the y-position
    public int getYFromLetter(Note data) {
        if (data.getClip().contains("R")) {
            return getYFromTierFake(4);
        }
        return getYFromTierFake(getTierFromLetter(data));
    }

    //determines the location of the closest y snap point
    public int closestYSnap(int y) {
        if (restMode) {
            tier = 4;
            return yMarginStart + fraction(yMarginEnd - yMarginStart, 1, 2);
        }

        if (y < yMarginStart) {
            tier = 0;
            return yMarginStart;
        } else if (y > yMarginEnd) {
            tier = 8;
            //return yMarginEnd;
            return (8 * fraction(yMarginEnd - yMarginStart, 1, 8)) + yMarginStart;
        }

        y -= yMarginStart;

        int filter = y % fraction(yMarginEnd - yMarginStart, 1, 8);
        int buffer = y / fraction(yMarginEnd - yMarginStart, 1, 8);

        if (filter < fraction(yMarginEnd - yMarginStart, 1, 16)) {
            tier = buffer;
            return (buffer * fraction(yMarginEnd - yMarginStart, 1, 8)) + yMarginStart;
        } else {
            tier = buffer + 1;
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

        //draws the rectangle that shows the playing bar
        g.setColor(Color.pink);
        g.fillRect((5 * 50) - 10, yMarginStart - 10, 50 + 5, yMarginEnd - yMarginStart + 20);

        //creates and displays the treble clef icon on the composer
        BufferedImage clefIcon = null;
        try {
            clefIcon = ImageIO.read(new File("images\\trebleClef.png"));
            //clefIcon = colorImage(clefIcon, 30, 19, 255);
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

        //Node current will point to head  
        NoteNode current = list.head;
        //System.out.println(current);

        //loops through everything in the listFauz to draw them on the staff
        while (current != null) {

            if (current.beep.getDegree() == 4 && current.beep.getClip().contains("R")) {
                g.drawImage(summon("notes\\restQ.png", 40, 60), ((current.beep.getIndex() * 50) - shiftTotal) - 5, getYFromLetter(current.beep) - 20, this);
            } else if (current.beep.getDegree() == 2 && current.beep.getClip().contains("R")) {
                g.drawImage(summon("notes\\restH.png", 50, 50), ((current.beep.getIndex() * 50) - shiftTotal) - 5, getYFromLetter(current.beep) - 32, this);
            } else if (current.beep.getDegree() == 1 && current.beep.getClip().contains("R")) {
                g.drawImage(summon("notes\\restW.png", 50, 50), ((current.beep.getIndex() * 50) - shiftTotal) - 5, getYFromLetter(current.beep) - 15, this);
            } else if (current.beep.getDegree() == 4) {
                g.drawImage(summon("notes\\noteQ.png", 40, 50), ((current.beep.getIndex() * 50) - shiftTotal) - 5, getYFromLetter(current.beep) - 40, this);
            } else if (current.beep.getDegree() == 2) {
                g.drawImage(summon("notes\\noteH.png", 60, 60), ((current.beep.getIndex() * 50) - shiftTotal) - 5, getYFromLetter(current.beep) - 45, this);
            } else if (current.beep.getDegree() == 1) {
                g.drawImage(summon("notes\\noteW.png", 50, 50), ((current.beep.getIndex() * 50) - shiftTotal) - 5, getYFromLetter(current.beep) - 25, this);
            }

            current = current.next;
        }

        //only if add mode is currently active
        if (changeType == 0) {
            //draws where the cursor currently snaps to
            switch (currentSet) {
                case 0 ->
                    g.drawImage(summon("notes\\noteQ.png", 40, 50), closestXSnap(mouseX) - 5, closestYSnap(mouseY) - 40, this);
                case 1 ->
                    g.drawImage(summon("notes\\noteH.png", 60, 60), closestXSnap(mouseX) - 5, closestYSnap(mouseY) - 45, this);
                case 2 ->
                    g.drawImage(summon("notes\\noteW.png", 50, 50), closestXSnap(mouseX) - 5, closestYSnap(mouseY) - 25, this);
                case 3 ->
                    g.drawImage(summon("notes\\restQ.png", 40, 60), closestXSnap(mouseX) - 5, closestYSnap(mouseY) - 20, this);
                case 4 ->
                    g.drawImage(summon("notes\\restH.png", 50, 50), closestXSnap(mouseX) - 5, closestYSnap(mouseY) - 32, this);
                case 5 ->
                    g.drawImage(summon("notes\\restW.png", 50, 50), closestXSnap(mouseX) - 5, closestYSnap(mouseY) - 15, this);
            }
        }
        
        if (changeType == 1)
        {
            
        }

        //System.out.println(listFauz);
    }
}
