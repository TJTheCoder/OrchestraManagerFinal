package com.mycompany.orchestramanagerfinal;

import java.util.HashMap;
import java.util.Map;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import org.jfugue.parser.ParserListener;

import org.jfugue.parser.ParserListenerAdapter;
import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;
import org.jfugue.player.SequencerManager;
import org.jfugue.theory.Note;
import org.staccato.StaccatoParser;

/*
The Temporal class contains a method called testTemporalTiming() that demonstrates
the temporal timing of notes played using JFugue and MIDI.
*/

//@author - Tanuj Tekkale

public class Temporal {

    public void testTemporalTiming() {
        try {
            
            long DELAY = 500;
            Pattern pattern = new Pattern(new Note(60), new Note(63), new Note(65));

            // First step: Let Pipeline process the pattern
            StaccatoParser parser = new StaccatoParser();
            Pipeline plp = new Pipeline();
            parser.addParserListener((ParserListener) plp);
            parser.parse(pattern);


            // Second step: Play the pattern, keeping track of when notes are played.

            // A) Listen for the note as played by Pipeline
            final Map<Byte, TimeStruct> noteTimeTracker = new HashMap<Byte, TimeStruct>();
            Player player = new Player();
            plp.addParserListener(new ParserListenerAdapter() {
                @Override
                public void onNoteParsed(Note note) {
                    
                    TimeStruct time = null;
                    if ((time = noteTimeTracker.get(note.getValue())) == null) {
                        
                        time = new TimeStruct();
                        noteTimeTracker.put(note.getValue(), time);
                        
                    }
                    
                    time.timeNotePlayedByTemporalPLP = System.currentTimeMillis();
                    
                }
                
            });

            // B) Listen for the note as played through MIDI by the Player
            SequencerManager.getInstance().getSequencer().getTransmitter().setReceiver(new Receiver() {
                @Override
                public void send(MidiMessage message, long timeStamp) {
                    
                    System.out.println("[TemporalPLPTest] In the Receiver.send() method");
                    System.out.println("[TemporalPLPTest] Message status = "+message.getStatus());
                    if (message.getStatus() == 0x90) {
                        
                        // Note On event for a note in Channel 0
                        System.out.println("[TemporalPLPTest] Found 'Note On' event for a note in Channel 0");
                        TimeStruct time = null;
                        System.out.println(message.getMessage()[0]);
                        
                        if ((time = noteTimeTracker.get(message.getMessage()[0])) == null)
                        {
                            
                            time = new TimeStruct();
                            noteTimeTracker.put(message.getMessage()[0], time);
                            
                        }
                        time.timeNotePlayedByPlayer = System.currentTimeMillis();
                        
                    }
                    
                }

                @Override
                public void close() {
                    // Unimplemented
                }
            });

            // C) Now actually play the music
            player.delayPlay(DELAY, pattern);
            plp.parse();

            // Third step: Let's see how long it took for each note to play
            for (Byte noteValue : noteTimeTracker.keySet()) {
                
                System.out.println("[TemporalPLPTest] For note value "+noteValue
                        +", timePlayedByPlayer = "+noteTimeTracker.get(noteValue).timeNotePlayedByPlayer
                        +", timePlayedByTemporalPLP = "+noteTimeTracker.get(noteValue).timeNotePlayedByTemporalPLP
                        +", calculated delay = "+(noteTimeTracker.get(noteValue).timeNotePlayedByPlayer - noteTimeTracker.get(noteValue).timeNotePlayedByTemporalPLP)
                        +" (expected delay = "+DELAY);
                
            }
        }
        catch (final MidiUnavailableException e)
        {
            
            e.printStackTrace();
            
        }
    }

    //suclass that creates a long to be used as secondary class variables
    class TimeStruct {
        
        public long timeNotePlayedByPlayer;
        public long timeNotePlayedByTemporalPLP;
        public TimeStruct() { }
        
    }
}