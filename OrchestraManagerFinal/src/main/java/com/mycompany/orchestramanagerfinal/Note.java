/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.orchestramanagerfinal;

/**
 *
 * @author tanuj
 */
public class Note {
    //define all class variables for the Note
    private String clip;
    private int degree;
    private int index;

    //constructors and getters and setters for the variables
    public Note(String clip, int degree, int index) {
        this.clip = clip;
        this.degree = degree;
        this.index = index;
    }

    public String getClip() {
        return clip;
    }

    public void setClip(String clip) {
        this.clip = clip;
    }

    public int getDegree() {
        return degree;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
    
    //converts the integer degree to a String in order to output it
    public String toDegree()
    {
        String out = "";
        switch (degree)
        {
            
            case 1 -> out = "w";
            
            case 2 -> out = "h";
            
            case 4 -> out = "q";
            
        }
        return out;
    }
    
    //returns the note information such that it can be used in Fugue
    public String toString()
    {
        return clip + toDegree();
    }
}
