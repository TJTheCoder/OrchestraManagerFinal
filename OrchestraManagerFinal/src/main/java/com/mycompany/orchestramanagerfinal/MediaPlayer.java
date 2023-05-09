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
    private String filePath;
    private Clip clip;
    private boolean status;
    AudioInputStream audioInputStream;
    
    public MediaPlayer(String filePath) throws UnsupportedAudioFileException, LineUnavailableException, IOException{
        this.filePath = filePath;
        audioInputStream = AudioSystem.getAudioInputStream(new File(filePath));
        clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.stop();
    }
    public String getFilePath(){
        return filePath;
    }
    public void setFilePath(String filePath){
        this.filePath = filePath;
    }
    public void play(){
        clip.start();
    }
    public void pause(){
        clip.stop();
    }
    public long getCurrentFrames(){
        return clip.getMicrosecondPosition();
    }
    public void setFrames(long frames){
        clip.setMicrosecondPosition(frames);
    }
}
