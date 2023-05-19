package com.mycompany.orchestramanagerfinal;

/**
 * Returns the duration of the temporal event.
 *
 * @return the duration as a double value.
 */

/*
This class is part of the "com.mycompany.orchestramanagerfinal" package and defines an interface called "Duration." 
It extends the "FugueEvent" interface and provides a method called "getDuration()" that returns the duration of a temporal event as a double value.
*/

public interface Duration extends FugueEvent {
	public double getDuration();
}