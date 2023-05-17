/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.orchestramanagerfinal;

import org.jfugue.player.Player;

/**
 *
 * @author tanuj
 */
public class Fugue {
    
    //initializes the class variables
    public String output;

    //constructors and getter and setter for the output variable
    public Fugue(String output) {
        this.output = output;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }
    
    //spaces out the letter notes in the string
    public void delineate()
    {
        String spaced = "";
        for (int i = 0; i < output.length(); i++)
        {
            if (!("" + output.charAt(i)).equals(" "))
            {
                spaced += output.charAt(i) + " ";
            }
        }
        output = spaced.trim();
    }
    
    //plays the audio for the letter notes in a string
    public void sing() {
        Player player = new Player();
        delineate();
        player.play(output);
    }
}
