package com.mycompany.orchestramanagerfinal;

/**
 * Returns the duration of the temporal event.
 *
 * @return the duration as a double value.
 */
public interface Duration extends FugueEvent {
	public double getDuration();
}