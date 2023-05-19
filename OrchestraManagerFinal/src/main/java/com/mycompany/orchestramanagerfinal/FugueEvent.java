package com.mycompany.orchestramanagerfinal;

import org.jfugue.parser.Parser;

/*
The execute() method takes a Parser object as a parameter and is responsible for performing some action or 
notifying the parser about the occurrence of the specific musical event.
By implementing this interface, classes can define their own specific musical events and provide the implementation for the execute() method, 
allowing them to be executed by a Parser object in a standardized way.
*/

//author - Tanuj Tekkale

//executes a Parser method from the parser import (is an interface)
public interface FugueEvent {
    
    public void execute(Parser parser);
    
}