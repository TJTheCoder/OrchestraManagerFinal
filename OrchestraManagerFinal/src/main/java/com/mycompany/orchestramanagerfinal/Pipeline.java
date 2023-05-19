package com.mycompany.orchestramanagerfinal;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jfugue.parser.Parser;
import org.jfugue.parser.ParserListener;
import org.jfugue.theory.Chord;
import org.jfugue.theory.Note;

public class Pipeline extends Parser implements ParserListener {

    private EventManager eventManager;
    private Events events;

    public Pipeline() {
        super();
        this.events = new Events();
        this.eventManager = new EventManager();
    }

    public Map<Long, List<FugueEvent>> getTimeToEventMap() {
        return eventManager.getTimeToEventMap();
    }

    /* ParserListener Events */
    /**
     * Called before parsing starts. It resets the event manager.
     */
    @Override
    public void beforeParsingStarts() {
        this.eventManager.reset();
    }

    /**
     * Called after parsing is finished. It finishes the event manager.
     */
    @Override
    public void afterParsingFinished() {
        this.eventManager.finish();
    }

    /**
     * Called when the track is changed. It sets the current track and adds a
     * track event to the event manager.
     *
     * @param track The new track.
     */
    @Override
    public void onTrackChanged(byte track) {
        this.eventManager.setCurrentTrack(track);
        this.eventManager.addRealTimeEvent(events.new TrackEvent(track));
    }

    /**
     * Called when the layer is changed. It sets the current layer and adds a
     * layer event to the event manager.
     *
     * @param layer The new layer.
     */
    @Override
    public void onLayerChanged(byte layer) {
        this.eventManager.setCurrentLayer(layer);
        this.eventManager.addRealTimeEvent(events.new LayerEvent(layer));
    }

    /**
     * Called when the instrument is parsed. It adds an instrument event to the
     * event manager.
     *
     * @param instrument The parsed instrument.
     */
    @Override
    public void onInstrumentParsed(byte instrument) {
        this.eventManager.addRealTimeEvent(events.new InstrumentEvent(instrument));
    }

    /**
     * Called when the tempo is changed. It sets the tempo and adds a tempo
     * event to the event manager.
     *
     * @param tempoBPM The new tempo in BPM.
     */
    @Override
    public void onTempoChanged(int tempoBPM) {
        this.eventManager.setTempo(tempoBPM);
        this.eventManager.addRealTimeEvent(events.new TempoEvent(tempoBPM));
    }

    /**
     * Called when the key signature is parsed. It adds a key signature event to
     * the event manager.
     *
     * @param key The key of the signature.
     * @param scale The scale of the signature.
     */
    @Override
    public void onKeySignatureParsed(byte key, byte scale) {
        this.eventManager.addRealTimeEvent(events.new KeySignatureEvent(key, scale));
    }

    /**
     * Called when the time signature is parsed. It adds a time signature event
     * to the event manager.
     *
     * @param numerator The numerator of the time signature.
     * @param powerOfTwo The power of two of the time signature.
     */
    @Override
    public void onTimeSignatureParsed(byte numerator, byte powerOfTwo) {
        this.eventManager.addRealTimeEvent(events.new TimeSignatureEvent(numerator, powerOfTwo));
    }

    /**
     * Called when a bar line is parsed. It adds a bar event to the event
     * manager.
     *
     * @param time The time of the bar line.
     */
    @Override
    public void onBarLineParsed(long time) {
        this.eventManager.addRealTimeEvent(events.new BarEvent(time));
    }

    /**
     * Called when a track beat time bookmark is added. It adds a track beat
     * time bookmark to the event manager.
     *
     * @param timeBookmarkID The ID of the time bookmark.
     */
    @Override
    public void onTrackBeatTimeBookmarked(String timeBookmarkID) {
        this.eventManager.addTrackTickTimeBookmark(timeBookmarkID);
    }

    /**
     * Called when a track beat time bookmark is requested. It sets the track
     * beat time based on the requested bookmark.
     *
     * @param timeBookmarkID The ID of the time bookmark.
     */
    @Override
    public void onTrackBeatTimeBookmarkRequested(String timeBookmarkID) {
        double time = this.eventManager.getTrackBeatTimeBookmark(timeBookmarkID);
        this.eventManager.setTrackBeatTime(time);
    }

    /**
     * Called when a track beat time is requested. It sets the track beat time
     * based on the requested time.
     *
     * @param time The requested time.
     */
    @Override
    public void onTrackBeatTimeRequested(double time) {
        this.eventManager.setTrackBeatTime(time);
    }

    /**
     * Called when a pitch wheel is parsed. It adds a pitch wheel event to the
     * event manager.
     *
     * @param lsb The least significant byte of the pitch wheel.
     * @param msb The most significant byte of the pitch wheel.
     */
    @Override
    public void onPitchWheelParsed(byte lsb, byte msb) {
        this.eventManager.addRealTimeEvent(events.new PitchWheelEvent(lsb, msb));
    }

    /**
     * Called when channel pressure is parsed. It adds a channel pressure event
     * to the event manager.
     *
     * @param pressure The parsed channel pressure.
     */
    @Override
    public void onChannelPressureParsed(byte pressure) {
        this.eventManager.addRealTimeEvent(events.new ChannelPressureEvent(pressure));
    }

    /**
     * Called when polyphonic pressure is parsed. It adds a polyphonic pressure
     * event to the event manager.
     *
     * @param key The key of the polyphonic pressure.
     * @param pressure The parsed polyphonic pressure.
     */
    @Override
    public void onPolyphonicPressureParsed(byte key, byte pressure) {
        this.eventManager.addRealTimeEvent(events.new PolyphonicPressureEvent(key, pressure));
    }

    /**
     * Called when a system exclusive event is parsed. It adds a system
     * exclusive event to the event manager.
     *
     * @param bytes The bytes of the system exclusive event.
     */
    @Override
    public void onSystemExclusiveParsed(byte... bytes) {
        this.eventManager.addRealTimeEvent(events.new SystemExclusiveEvent(bytes));
    }

    /**
     * Called when a controller event is parsed. It adds a controller event to
     * the event manager.
     *
     * @param controller The controller number.
     * @param value The value of the controller.
     */
    @Override
    public void onControllerEventParsed(byte controller, byte value) {
        this.eventManager.addRealTimeEvent(events.new ControllerEvent(controller, value));
    }

    @Override
    public void onLyricParsed(String lyric) {
        this.eventManager.addRealTimeEvent(events.new LyricEvent(lyric));
    }

    @Override
    public void onMarkerParsed(String marker) {
        this.eventManager.addRealTimeEvent(events.new MarkerEvent(marker));
    }

    @Override
    public void onFunctionParsed(String id, Object message) {
        this.eventManager.addRealTimeEvent(events.new UserEvent(id, message));
    }

    @Override
    public void onNotePressed(Note note) {
    }

    @Override
    public void onNoteReleased(Note note) {
    }

    @Override
    public void onNoteParsed(Note note) {
        this.eventManager.addRealTimeEvent((Duration) events.new NoteEvent(note));
    }

    @Override
    public void onChordParsed(Chord chord) {
        this.eventManager.addRealTimeEvent((Duration) events.new ChordEvent(chord));
    }

    private void delay(long millis) {
        try {
            Thread.sleep(millis);
        } catch (Exception e) {
            // An exception? Ain't no one got time for that!
        }
    }

    public void parse() {
        fireBeforeParsingStarts();

        long oldTime = 0;
        Set<Long> times = this.eventManager.getTimeToEventMap().keySet();
        for (long time : times) {
            delay(time - oldTime);
            oldTime = time;

            for (FugueEvent event : this.eventManager.getTimeToEventMap().get(time)) {
                event.execute(this);
            }
        }

        fireAfterParsingFinished();
    }

    /*
    void parse() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
     */
}
