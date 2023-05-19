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
    public NoteNode head, tail = null;
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

    //addNode() will add a node to the end of the list  
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
            //System.out.println("List is empty");
            return "";
        }
        //System.out.println("Nodes of doubly linked list: ");
        while (current != null) {
            //Prints each node by incrementing the pointer.  
            out += current.beep + " ";
            current = current.next;
        }

        return out;
    }

    //inserts a node at a specific index
    public void insert(int index, Note data) {
        NoteNode newNode = new NoteNode(data);

        if (head == null) {
            // If the list is empty and index is 0, make the new node the head
            if (index == 0) {
                head = tail = newNode;
                head.previous = null;
                tail.next = null;
            } else {
                // If the list is empty and index is not 0, throw an exception or handle it accordingly
                throw new IndexOutOfBoundsException("Invalid index");
            }
        } else if (index == 0) {
            // If the index is 0, insert at the beginning
            newNode.next = head;
            head.previous = newNode;
            head = newNode;
            head.previous = null;
        } else {
            NoteNode current = head;
            int currentIndex = 0;

            // Traverse the list to find the desired index or the end of the list
            while (current.next != null && currentIndex < index - 1) {
                current = current.next;
                currentIndex++;
            }

            if (currentIndex == index - 1) {
                // Insert the new node at the desired index
                newNode.next = current.next;
                newNode.previous = current;
                if (current.next != null) {
                    current.next.previous = newNode;
                }
                current.next = newNode;
            } else {
                // If the desired index is out of bounds, throw an exception or handle it accordingly
                throw new IndexOutOfBoundsException("Invalid index");
            }
        }
    }

    // deletes a node at a specific index
    public void delete(int index) {
        if (head == null) {
            // If the list is empty, throw an exception or handle it accordingly
            throw new IndexOutOfBoundsException("Invalid index");
        } else if (index == 0) {
            // If the index is 0, delete the head node
            if (head == tail) {
                // If there is only one node in the list
                head = tail = null;
            } else {
                head = head.next;
                head.previous = null;
            }
        } else {
            NoteNode current = head;
            int currentIndex = 0;

            // Traverse the list to find the desired index or the end of the list
            while (current != null && currentIndex < index) {
                current = current.next;
                currentIndex++;
            }

            if (currentIndex == index && current != null) {
                // Delete the node at the desired index
                if (current == tail) {
                    // If the node to be deleted is the tail node
                    tail = tail.previous;
                    tail.next = null;
                } else {
                    current.previous.next = current.next;
                    if (current.next != null) {
                        current.next.previous = current.previous;
                    }
                }

                // Adjust the indices of the Note objects after deletion
                current = current.next;
                while (current != null) {
                    current.beep.setIndex(current.beep.getIndex() - 2);
                    current = current.next;
                }
            } else {
                // If the desired index is out of bounds, throw an exception or handle it accordingly
                throw new IndexOutOfBoundsException("Invalid index");
            }
        }
    }

    //makes it so that there is a guarantee that every note will shift over
    public void indexManage()
    {
        NoteNode current = head;
        
        //for loop in the style of a while that runs until the end is reached and forcibly sets everything to the correct indices
        int i = 5;
        while (current != null)
        {
            current.beep.setIndex(i);
            current = current.next;
            i += 2;
        }
    }

    /*
    public void save()
    {
        
    }
     */
    //returns the node at a specific index assuming it is within bounds
    public NoteNode getNodeAtIndex(int index) {
        if (head == null || index < 0) {
            // If the list is empty or the index is negative, throw an exception or handle it accordingly
            //throw new IndexOutOfBoundsException("Invalid index");
            return null;
        }

        NoteNode current = head;
        int currentIndex = 0;

        // Traverse the list to find the desired index or the end of the list
        while (current != null && currentIndex < index) {
            current = current.next;
            currentIndex++;
        }

        if (currentIndex == index && current != null) {
            // Return the node at the desired index
            return current;
        } else {
            // If the desired index is out of bounds, throw an exception or handle it accordingly
            throw new IndexOutOfBoundsException("Invalid index");
        }
    }

    //returns the size of the NoteList
    public int getNodeCount() {
        int count = 0;
        NoteNode current = head;

        while (current != null) {
            count++;
            current = current.next;
        }

        return count;
    }

    //returns the String such that it can be run with a Fugue object
    @Override
    public String toString() {
        String out = display().trim();
        return "T" + "" + tempo + " " + out;
    }
}
