/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.orchestramanagerfinal;

/**
 *
 * @author tanuj
 */
public class NoteNode {
    //initializes class variables
    public Note beep;
    public NoteNode next;
    public NoteNode previous;

    //constructors and getters and setters for the notes
    public NoteNode(Note beep) {
        this.beep = beep;
    }
    
    public NoteNode(Note beep, NoteNode next, NoteNode previous) {
        this.beep = beep;
        this.next = next;
        this.previous = previous;
    }

    public Note getBeep() {
        return beep;
    }

    public void setBeep(Note beep) {
        this.beep = beep;
    }

    public NoteNode getNext() {
        return next;
    }

    public void setNext(NoteNode next) {
        this.next = next;
    }

    public NoteNode getPrevious() {
        return previous;
    }

    public void setPrevious(NoteNode previous) {
        this.previous = previous;
    }
}
