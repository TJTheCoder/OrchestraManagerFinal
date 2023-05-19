/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.orchestramanagerfinal;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author atxbr
 */
public class MediaPlayer {
    //initializes class variables
    private String filePath;
    private Clip clip;
    private boolean status = true;
    
    AudioInputStream audioInputStream;
    
    public MediaPlayer(String filePath) throws UnsupportedAudioFileException, LineUnavailableException, IOException{
        this.filePath = filePath;
        audioInputStream = AudioSystem.getAudioInputStream(new File(filePath));
        clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.stop();
    }
    
    //getters and setters for the filePath() class variables
    public String getFilePath(){
        return filePath;
    }
    
    public void setFilePath(String filePath){
        this.filePath = filePath;
    }
    
    //starts the song by triggering the play() method
    public void play(){
        clip.start();
    }
    
    //ends the song by triggering the pause() method
    public void pause() {
        clip.stop();
    }
    
    //ends the song completely
    public void stop(){
        clip.close();
    }
    
    //returns the clip's microsecond length
    public long getMicrosecondLength(){
        return clip.getMicrosecondLength();
    }
    
    //returns the amount of current frames
    public long getCurrentFrames(){
        return clip.getMicrosecondPosition();
    }
    
    //setter for the clip's microsecondlength
    public void setFrames(long frames){
        clip.setMicrosecondPosition(frames);
    }
}
