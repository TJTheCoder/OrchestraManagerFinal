package com.mycompany.orchestramanagerfinal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.jfugue.midi.MidiDefaults;

/**
 * Places musical data into the MIDI sequence. Package scope, final class.
 *
 * @author Tanuj Tekkale
 */

/*

This class is a final class called "EventManager" in the "com.mycompany.orchestramanagerfinal" package. 
It is responsible for placing musical data into a MIDI sequence. 
It contains various methods and fields for managing the musical events and timing within the sequence.
*/

final class EventManager {

    //instantiates the class variables and each type, mainly the TreeMap<>()
    private Map<Long, List<FugueEvent>> timeToEventMap = new TreeMap<Long, List<FugueEvent>>();
    private int tempoBeatsPerMinute = MidiDefaults.DEFAULT_TEMPO_BEATS_PER_MINUTE;
    private int beatsPerWhole = MidiDefaults.DEFAULT_TEMPO_BEATS_PER_WHOLE;
    private byte currentTrack = 0;
    private byte[] currentLayer = new byte[MidiDefaults.TRACKS];
    private double beatTime[][] = new double[MidiDefaults.TRACKS][MidiDefaults.LAYERS];
    private Map<String, Double> bookmarkedTrackTimeMap;

    //empty constructor that doesn't do anything
    public EventManager() {
    }

    //resets all the MIDI event threads to being empty
    public void reset() {
        this.bookmarkedTrackTimeMap = new HashMap<String, Double>();
        this.tempoBeatsPerMinute = MidiDefaults.DEFAULT_TEMPO_BEATS_PER_MINUTE;
        this.currentTrack = 0;
        for (int i = 0; i < MidiDefaults.TRACKS; i++) {
            
            this.currentLayer[i] = 0;
            
        }
        this.timeToEventMap.clear();
    }

    //empty method--not used
    public void finish() {
    }

    public void setTempo(int tempoBPM) {
        this.tempoBeatsPerMinute = tempoBPM;
    }

    /**
     * Sets the current track to which new events will be added.
     *
     * @param layer the track to select
     */
    public void setCurrentTrack(byte track) {
        currentTrack = track;
    }

    /**
     * Sets the current layer within the track to which new events will be
     * added.
     *
     * @param layer the layer to select
     */
    public void setCurrentLayer(byte layer) {
        currentLayer[currentTrack] = layer;
    }

    /**
     * Advances the timer for the current track by the specified duration, which
     * is specified in Pulses Per Quarter (PPQ)
     *
     * @param duration the duration to increase the track timer
     */
    public void advanceTrackBeatTime(double advanceTime) {
        beatTime[currentTrack][currentLayer[currentTrack]] += advanceTime;
    }

    /**
     * Sets the timer for the current track by the given time, which is
     * specified in Pulses Per Quarter (PPQ)
     *
     * @param newTickTime the time at which to set the track timer
     */
    public void setTrackBeatTime(double newTime) {
        beatTime[currentTrack][currentLayer[currentTrack]] = newTime;
    }

    /**
     * Returns the timer for the current track and current layer.
     *
     * @return the timer value for the current track, specified in Pulses Per
     * Quarter (PPQ)
     */
    public double getTrackBeatTime() {
        return beatTime[currentTrack][currentLayer[currentTrack]];
    }

    //adds a new map that creates a list of times at which events were tracked
    public void addTrackTickTimeBookmark(String timeBookmarkID) {
        bookmarkedTrackTimeMap.put(timeBookmarkID, getTrackBeatTime());
    }

    //returns the value at a time of a bookmark
    public double getTrackBeatTimeBookmark(String timeBookmarkID) {
        return bookmarkedTrackTimeMap.get(timeBookmarkID);
    }

    //creates a new FugueEvent for the parameter of a Duration
    public void addRealTimeEvent(Duration event) {
        addRealTimeEvent((FugueEvent) event);
        advanceTrackBeatTime(event.getDuration());
    }

    //creates a new FugueEvent for the parameter of a FugueEvent
    public void addRealTimeEvent(FugueEvent event) {
        List<FugueEvent> eventList = timeToEventMap.get(convertBeatsToMillis(getTrackBeatTime()));
        if (eventList == null) {
            
            eventList = new ArrayList<FugueEvent>();
            timeToEventMap.put(convertBeatsToMillis(getTrackBeatTime()), eventList);
            
        }
        eventList.add(event);
    }

    //returns the class variable of the the map
    public Map<Long, List<FugueEvent>> getTimeToEventMap() {
        return this.timeToEventMap;
    }

    //converts from BPM to millis--used for the rests, represented by "R"
    private long convertBeatsToMillis(double beats) {
        return (long) ((beats * beatsPerWhole * 60000.0D) / tempoBeatsPerMinute);
    }
}
