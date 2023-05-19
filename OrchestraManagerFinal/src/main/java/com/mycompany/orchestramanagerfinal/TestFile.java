/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.orchestramanagerfinal;

/**
 *
 * @author tanuj
 */
public class TestFile {
    //test method to ensure that all methods are operational
    public static void main (String[] args)
    {
        //tests the Fugue maven changes
        Fugue fug = new Fugue("I[Trumpet] C6q G5q C6q E5q E5q A5q A5q E5q E5q B6w");
        fug.sing();
        
        /*
        NoteList list = new NoteList(150);
        
        Note n1 = new Note("C", 4, 9);
        Note n2 = new Note("D", 2, 10);
        Note n3 = new Note("E", 1, 11);
        
        System.out.println();
        
        list.addNode(n1);
        System.out.println(list);
        
        list.addNode(n2);
        System.out.println(list);
        
        list.insert(1, n3);
        System.out.println(list);
        
        list.delete(1);
        System.out.println(list);
        */
    }
}
