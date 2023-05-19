package com.mycompany.orchestramanagerfinal;

import org.jfugue.parser.Parser;

//executes a Parser method from the parser import (is an interface)
public interface FugueEvent {
    public void execute(Parser parser);
}