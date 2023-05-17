/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.orchestramanagerfinal;

/**
 *
 * @author tanuj
 */
public class NoteList {

    //initialize class variables
    NoteNode head, tail = null;
    int tempo;

    //constructor and getters and setters for the tempo class variable
    public NoteList(int tempo) {
        this.tempo = tempo;
    }

    public int getTempo() {
        return tempo;
    }

    public void setTempo(int tempo) {
        this.tempo = tempo;
    }

    //addNode() will add a node to the list  
    public void addNode(Note data) {
        //Create a new node  
        NoteNode newNode = new NoteNode(data);
        //If list is empty  
        if (head == null) {
            //Both head and tail will point to newNode  
            head = tail = newNode;
            //head's previous will point to null  
            head.previous = null;
            //tail's next will point to null, as it is the last node of the list  
            tail.next = null;
        } else {
            //newNode will be added after tail such that tail's next will point to newNode  
            tail.next = newNode;
            //newNode's previous will point to tail  
            newNode.previous = tail;
            //newNode will become new tail  
            tail = newNode;
            //As it is last node, tail's next will point to null  
            tail.next = null;
        }
    }

    //display() will print out the nodes of the list  
    public String display() {
        String out = "";
        
        //Node current will point to head  
        NoteNode current = head;
        if (head == null) {
            System.out.println("List is empty");
            return "";
        }
        System.out.println("Nodes of doubly linked list: ");
        while (current != null) {
            //Prints each node by incrementing the pointer.  
            out += current.beep + " ";
            current = current.next;
        }
        
        return out;
    }
    
    public String toString()
    {
        String out = display().trim();
        return "T" + "" + tempo + " " + out;
    }
}
