package com.mycompany.orchestramanagerfinal;

import org.jfugue.parser.Parser;
import org.jfugue.temporal.DurationTemporalEvent;
import org.jfugue.theory.Chord;
import org.jfugue.theory.Note;

public class Events {

    public class TrackEvent implements FugueEvent {

        private byte track;

        public TrackEvent(byte track) {
            this.track = track;
        }

        public void execute(Parser parser) {
            parser.fireTrackChanged(track);
        }
    }

    /**
     * The LayerEvent class represents a layer change event. It implements the
     * FugueEvent interface.
     */
    public class LayerEvent implements FugueEvent {

        private byte layer;

        public LayerEvent(byte layer) {
            this.layer = layer;
        }

        public void execute(Parser parser) {
            parser.fireLayerChanged(layer);
        }
    }

    /**
     * The InstrumentEvent class represents an instrument change event. It
     * implements the FugueEvent interface.
     */
    public class InstrumentEvent implements FugueEvent {

        private byte instrument;

        public InstrumentEvent(byte instrument) {
            this.instrument = instrument;
        }

        public void execute(Parser parser) {
            parser.fireInstrumentParsed(instrument);
        }
    }

    /**
     * The TempoEvent class represents a tempo change event. It implements the
     * FugueEvent interface.
     */
    public class TempoEvent implements FugueEvent {

        private int tempoBPM;

        public TempoEvent(int tempoBPM) {
            this.tempoBPM = tempoBPM;
        }

        public void execute(Parser parser) {
            parser.fireTempoChanged(tempoBPM);
        }
    }

    /**
     * The KeySignatureEvent class represents a key signature change event. It
     * implements the FugueEvent interface.
     */
    public class KeySignatureEvent implements FugueEvent {

        private byte key, scale;

        public KeySignatureEvent(byte key, byte scale) {
            this.key = key;
            this.scale = scale;
        }

        public void execute(Parser parser) {
            parser.fireKeySignatureParsed(key, scale);
        }
    }

    /**
     * The TimeSignatureEvent class represents a time signature change event. It
     * implements the FugueEvent interface.
     */
    public class TimeSignatureEvent implements FugueEvent {

        private byte numerator, powerOfTwo;

        public TimeSignatureEvent(byte numerator, byte powerOfTwo) {
            this.numerator = numerator;
            this.powerOfTwo = powerOfTwo;
        }

        public void execute(Parser parser) {
            parser.fireTimeSignatureParsed(numerator, powerOfTwo);
        }
    }

    /**
     * The BarEvent class represents a bar line event. It implements the
     * FugueEvent interface.
     */
    public class BarEvent implements FugueEvent {

        private long barId;

        public BarEvent(long barId) {
            this.barId = barId;
        }

        public void execute(Parser parser) {
            parser.fireBarLineParsed(barId);
        }
    }

    /**
     * The PitchWheelEvent class represents a pitch wheel event. It implements
     * the FugueEvent interface.
     */
    public class PitchWheelEvent implements FugueEvent {

        private byte lsb, msb;

        public PitchWheelEvent(byte lsb, byte msb) {
            this.lsb = lsb;
            this.msb = msb;
        }

        public void execute(Parser parser) {
            parser.fireKeySignatureParsed(lsb, msb);
        }
    }

    /**
     * The ChannelPressureEvent class represents a channel pressure event. It
     * implements the FugueEvent interface.
     */
    public class ChannelPressureEvent implements FugueEvent {

        private byte pressure;

        public ChannelPressureEvent(byte pressure) {
            this.pressure = pressure;
        }

        public void execute(Parser parser) {
            parser.fireChannelPressureParsed(pressure);
        }
    }

    /**
     * The PolyphonicPressureEvent class represents a polyphonic pressure event.
     * It implements the FugueEvent interface.
     */
    public class PolyphonicPressureEvent implements FugueEvent {

        private byte key, pressure;

        public PolyphonicPressureEvent(byte key, byte pressure) {
            this.key = key;
            this.pressure = pressure;
        }

        public void execute(Parser parser) {
            parser.firePolyphonicPressureParsed(key, pressure);
        }
    }

    /**
     * The SystemExclusiveEvent class represents a system exclusive event. It
     * implements the FugueEvent interface.
     */
    public class SystemExclusiveEvent implements FugueEvent {

        private byte[] bytes;

        public SystemExclusiveEvent(byte... bytes) {
            this.bytes = bytes;
        }

        public void execute(Parser parser) {
            parser.fireSystemExclusiveParsed(bytes);
        }
    }

    /**
     * The ControllerEvent class represents a controller event. It implements
     * the FugueEvent interface.
     */
    public class ControllerEvent implements FugueEvent {

        private byte controller, value;

        public ControllerEvent(byte controller, byte value) {
            this.controller = controller;
            this.value = value;
        }

        public void execute(Parser parser) {
            parser.fireControllerEventParsed(controller, value);
        }
    }

    /**
     * The LyricEvent class represents a lyric event. It implements the
     * FugueEvent interface.
     */
    public class LyricEvent implements FugueEvent {

        private String lyric;

        public LyricEvent(String lyric) {
            this.lyric = lyric;
        }

        public void execute(Parser parser) {
            parser.fireLyricParsed(lyric);
        }
    }

    /**
     * The MarkerEvent class represents a marker event. It implements the
     * FugueEvent interface.
     */
    public class MarkerEvent implements FugueEvent {

        private String marker;

        public MarkerEvent(String marker) {
            this.marker = marker;
        }

        public void execute(Parser parser) {
            parser.fireMarkerParsed(marker);
        }
    }

    /**
     * The UserEvent class represents a user-defined event. It implements the
     * FugueEvent interface.
     */
    public class UserEvent implements FugueEvent {

        private String id;
        private Object message;

        public UserEvent(String id, Object message) {
            this.id = id;
            this.message = message;
        }

        public void execute(Parser parser) {
            parser.fireFunctionParsed(id, message);
        }
    }

    /**
     * The NoteEvent class represents a note event with duration. It implements
     * the DurationTemporalEvent interface.
     */
    public class NoteEvent implements DurationTemporalEvent {

        private Note note;

        public NoteEvent(Note note) {
            this.note = note;
        }

        public void execute(Parser parser) {
            parser.fireNoteParsed(this.note);
        }

        public double getDuration() {
            return this.note.getDuration();
        }
    }

    /**
     * The ChordEvent class represents a chord event with duration. It
     * implements the DurationTemporalEvent interface.
     */
    public class ChordEvent implements DurationTemporalEvent {

        private Chord chord;

        public ChordEvent(Chord chord) {
            this.chord = chord;
        }

        public void execute(Parser parser) {
            parser.fireChordParsed(this.chord);
        }

        public double getDuration() {
            return this.chord.getNotes()[0].getDuration();
        }
    }

}
