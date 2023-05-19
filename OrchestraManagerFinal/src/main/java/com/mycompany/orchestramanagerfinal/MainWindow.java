/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.orchestramanagerfinal;

import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.Timer;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Tanuj Tekkale
 * @author atxbr
 */
public class MainWindow extends javax.swing.JFrame {

    private final BufferedImage pause = ImageIO.read(new File("buttons\\pause.png"));
    private final BufferedImage play = ImageIO.read(new File("buttons\\play.png"));
    //just creates te pause and save images because they wont change and can be called from different events
    private File directory;
    private File[] files;
    //these two are for each group of files in the msuic and images folder respectively
    private File directory2;
    private File[] files2;

    private ArrayList<String> songs;
    private ArrayList<String> covers;

    private int songNumber;
    private ArrayList<String> filePath;
    private String songs1;
    private String songs2;
    //file pathing and the song number within that arraylist
    
    //tracks whether or not to display rests in the composer tab and whether to play or not
    private boolean restMode;
    private boolean playMode;

    //creates class variables for the Image objects such that methods can reference them
    private Image restQ;
    private Image restH;
    private Image restW;
    private Image noteQ;
    private Image noteH;
    private Image noteW;

    private Image play2;
    private Image pause2;
    private MediaPlayer mediaPlayer;
    private String[] headers;

    /**
     * Creates new form GUI3
     */
    public MainWindow() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        initComponents();
        setResizable(false);

        //adding mouseListeners to the panel so it can track the location of the mouse
        composerPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                ((GraphicPanel) composerPanel).updateMouseCoords(getX(), getY());
            }
        });
        composerPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                ((GraphicPanel) composerPanel).updateMouseCoords(getX(), getY());
            }
        });

        /*
        String[] options = {"Add", "Delete"};
        CRUDButton = new JComboBox(options);
         */
        songs = new ArrayList<>();
        directory = new File("music");
        files = directory.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                songs.add(files[i].getAbsolutePath());
            }

        }
        //saves all the files in the music folder by their pathing so it can be used for later
        //mainly used when choosing a new song so we can grab the song numberr in this list and play that song using the path saved
        System.out.println(songs);
        //sizes the images to the specific button sizes
        BufferedImage icons = ImageIO.read(new File("buttons\\pause.png"));
        BufferedImage icons2 = ImageIO.read(new File("buttons\\play.png"));

        //creates buffered images for the forward and backward arrow
        BufferedImage iconsLeft = ImageIO.read(new File("buttons\\faceLeft.png"));
        BufferedImage iconsRight = ImageIO.read(new File("buttons\\faceRight.png"));

        //initializes icons for all the notes/rests in the Composer panel
        BufferedImage restWIcon = ImageIO.read(new File("notes\\restW.png"));
        BufferedImage restHIcon = ImageIO.read(new File("notes\\restH.png"));
        BufferedImage restQIcon = ImageIO.read(new File("notes\\restQ.png"));
        BufferedImage noteWIcon = ImageIO.read(new File("notes\\noteW.png"));
        BufferedImage noteHIcon = ImageIO.read(new File("notes\\noteH.png"));
        BufferedImage noteQIcon = ImageIO.read(new File("notes\\noteQ.png"));

        //creates scaled instances for the play and pause icons
        Image pause = icons.getScaledInstance(40, 30, Image.SCALE_SMOOTH);
        Image play = icons2.getScaledInstance(45, 36, Image.SCALE_SMOOTH);

        //transfers the data from the buffered image into images
        Image left = iconsLeft.getScaledInstance(46, 41, Image.SCALE_SMOOTH);
        Image right = iconsRight.getScaledInstance(46, 41, Image.SCALE_SMOOTH);

        //clone Images to be used in the Composer method
        pause2 = icons.getScaledInstance(71, 69, Image.SCALE_SMOOTH);
        play2 = icons2.getScaledInstance(71, 69, Image.SCALE_SMOOTH);

        //creates images based on all the initialized icons
        restW = restWIcon.getScaledInstance(72, 71, Image.SCALE_SMOOTH);
        restH = restHIcon.getScaledInstance(72, 71, Image.SCALE_SMOOTH);
        restQ = restQIcon.getScaledInstance(72, 71, Image.SCALE_SMOOTH);
        noteW = noteWIcon.getScaledInstance(72, 71, Image.SCALE_SMOOTH);
        noteH = noteHIcon.getScaledInstance(72, 71, Image.SCALE_SMOOTH);
        noteQ = noteQIcon.getScaledInstance(72, 71, Image.SCALE_SMOOTH);
        //sets the play and pause buttons to icons that have been fitted to the respective buttons
        play_button.setIcon(new ImageIcon(play));
        pause_button.setIcon(new ImageIcon(pause));

        //adds the images for the move left and move right arrows to the actual buttons
        backButton.setIcon(new ImageIcon(left));
        forwardButton.setIcon(new ImageIcon(right));

        //sets the composer play button to default, which is the play button
        playStopToggle.setIcon(new ImageIcon(play2));
        playMode = false;

        //sets starting icons for the three placement buttons in the composer
        quarterButton.setIcon(new ImageIcon(noteQ));
        halfButton.setIcon(new ImageIcon(noteH));
        wholeButton.setIcon(new ImageIcon(noteW));

        //default starting state is in note mode, so the toggle should demonstrate the option to go to rest mode
        noteRestToggle.setText("To Rest");
        restMode = false;

        BufferedImage img = ImageIO.read(new File("images\\cover.png"));
        Image img1 = img.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        Image img2 = img.getScaledInstance(250, 241, Image.SCALE_SMOOTH);
        //creating a buffered image that can be shaped to a specific size for the cover of the album
        coverShow_label.setIcon(new ImageIcon(img1));
        coverShow2_label.setIcon(new ImageIcon(img));

        ((GraphicPanel) composerPanel).paint();
        //adds all the cover images to an array where the path of the files are saved
        //can be used to change the album cover when different songs are played
        covers = new ArrayList<>();
        directory2 = new File("images");
        files2 = directory.listFiles();
        if (files2 != null) {
            
            for (int j = 0; j < files2.length; j++) {
                
                covers.add(files2[j].getAbsolutePath());
                
            }
            
        }
        
        coverShow_label.setIcon(new ImageIcon(img1));
        coverShow2_label.setIcon(new ImageIcon(img2));
        //media = new Media(songs.get(songNumber).toURI().toString());
        //mediaPlayer = new MediaPlayer(media);
        headers = songs.get(songNumber).split("\\.");
        //splits the file into components to seperate the artist that wrote the song from the rest
        albumName2_label.setText((headers[0].substring(91)));
        albumName_label.setText((headers[0].substring(91)));

        buildSongsTable();
        mediaPlayer = new MediaPlayer(songs.get(0));
        //builds the list of songs inside a table
        //sets the current song to the first in the list
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        tabs = new javax.swing.JTabbedPane();
        shuffle_panel = new javax.swing.JPanel();
        play2_button = new javax.swing.JButton();
        Shuffle_button = new javax.swing.JButton();
        coverShow2_label = new javax.swing.JLabel();
        albumName2_label = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        songsTable_table = new javax.swing.JTable();
        player_panel = new javax.swing.JPanel();
        progressBar_slider = new javax.swing.JSlider();
        skipFor_button = new javax.swing.JButton();
        coverShow_label = new javax.swing.JLabel();
        skipBack_button = new javax.swing.JButton();
        play_button = new javax.swing.JButton();
        volume_slider = new javax.swing.JSlider();
        albumName_label = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        artist_label = new javax.swing.JLabel();
        pause_button = new javax.swing.JButton();
        composerPanel = new GraphicPanel();
        forwardButton = new javax.swing.JButton();
        backButton = new javax.swing.JButton();
        wholeButton = new javax.swing.JButton();
        quarterButton = new javax.swing.JButton();
        halfButton = new javax.swing.JButton();
        playStopToggle = new javax.swing.JToggleButton();
        noteRestToggle = new javax.swing.JToggleButton();
        crudButton = new javax.swing.JComboBox<>();
        instrumentButton = new javax.swing.JComboBox<>();

        jButton1.setText("jButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        shuffle_panel.setBackground(new java.awt.Color(0, 51, 102));

        play2_button.setText("Play");
        play2_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                play2_buttonActionPerformed(evt);
            }
        });

        Shuffle_button.setText("Shuffle");
        Shuffle_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Shuffle_buttonActionPerformed(evt);
            }
        });

        albumName2_label.setFont(new java.awt.Font("Segoe UI Semibold", 1, 36)); // NOI18N
        albumName2_label.setForeground(new java.awt.Color(255, 255, 255));
        albumName2_label.setText("Proof");

        songsTable_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(songsTable_table);

        javax.swing.GroupLayout shuffle_panelLayout = new javax.swing.GroupLayout(shuffle_panel);
        shuffle_panel.setLayout(shuffle_panelLayout);
        shuffle_panelLayout.setHorizontalGroup(
            shuffle_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(shuffle_panelLayout.createSequentialGroup()
                .addGap(140, 140, 140)
                .addGroup(shuffle_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(shuffle_panelLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 728, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(141, Short.MAX_VALUE))
                    .addGroup(shuffle_panelLayout.createSequentialGroup()
                        .addGroup(shuffle_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(shuffle_panelLayout.createSequentialGroup()
                                .addComponent(play2_button)
                                .addGap(18, 18, 18)
                                .addComponent(Shuffle_button))
                            .addComponent(albumName2_label, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(coverShow2_label, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(182, 182, 182))))
        );
        shuffle_panelLayout.setVerticalGroup(
            shuffle_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, shuffle_panelLayout.createSequentialGroup()
                .addGroup(shuffle_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(shuffle_panelLayout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addComponent(albumName2_label, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(95, 95, 95)
                        .addGroup(shuffle_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(play2_button)
                            .addComponent(Shuffle_button)))
                    .addGroup(shuffle_panelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(coverShow2_label, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1)
                .addContainerGap())
        );

        tabs.addTab("Shuffle", shuffle_panel);

        player_panel.setBackground(new java.awt.Color(0, 51, 102));

        progressBar_slider.setValue(1);
        progressBar_slider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                progressBar_sliderStateChanged(evt);
            }
        });

        skipFor_button.setText(">>>");
        skipFor_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                skipFor_buttonActionPerformed(evt);
            }
        });

        skipBack_button.setText("<<<");
        skipBack_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                skipBack_buttonActionPerformed(evt);
            }
        });

        play_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                play_buttonActionPerformed(evt);
            }
        });

        volume_slider.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                volume_sliderMousePressed(evt);
            }
        });

        albumName_label.setFont(new java.awt.Font("Segoe UI Semibold", 1, 36)); // NOI18N
        albumName_label.setForeground(new java.awt.Color(255, 255, 255));
        albumName_label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        albumName_label.setText("Proof");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Volume");

        artist_label.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        artist_label.setForeground(new java.awt.Color(255, 255, 255));
        artist_label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        pause_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pause_buttonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout player_panelLayout = new javax.swing.GroupLayout(player_panel);
        player_panel.setLayout(player_panelLayout);
        player_panelLayout.setHorizontalGroup(
            player_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(player_panelLayout.createSequentialGroup()
                .addContainerGap(191, Short.MAX_VALUE)
                .addGroup(player_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, player_panelLayout.createSequentialGroup()
                        .addComponent(skipBack_button, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(play_button, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37)
                        .addComponent(pause_button, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38)
                        .addComponent(skipFor_button, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(323, 323, 323))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, player_panelLayout.createSequentialGroup()
                        .addComponent(volume_slider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, player_panelLayout.createSequentialGroup()
                        .addComponent(progressBar_slider, javax.swing.GroupLayout.PREFERRED_SIZE, 670, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(148, 148, 148))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, player_panelLayout.createSequentialGroup()
                        .addComponent(albumName_label, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(360, 360, 360))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, player_panelLayout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(88, 88, 88))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, player_panelLayout.createSequentialGroup()
                        .addComponent(artist_label, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(447, 447, 447))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, player_panelLayout.createSequentialGroup()
                        .addComponent(coverShow_label, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(337, 337, 337))))
        );
        player_panelLayout.setVerticalGroup(
            player_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, player_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(coverShow_label, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(albumName_label, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(artist_label, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(progressBar_slider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(player_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pause_button, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                    .addComponent(skipBack_button, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                    .addComponent(skipFor_button, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                    .addComponent(play_button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(84, 84, 84)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(volume_slider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );

        tabs.addTab("Player", player_panel);

        composerPanel.setBackground(new java.awt.Color(52, 235, 201));
        composerPanel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                composerPanelMouseMoved(evt);
            }
        });
        composerPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                composerPanelMousePressed(evt);
            }
        });

        forwardButton.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                forwardButtonMouseDragged(evt);
            }
        });
        forwardButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                forwardButtonMousePressed(evt);
            }
        });
        forwardButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                forwardButtonActionPerformed(evt);
            }
        });

        backButton.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                backButtonMouseDragged(evt);
            }
        });
        backButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                backButtonMouseClicked(evt);
            }
        });
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });

        wholeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wholeButtonActionPerformed(evt);
            }
        });

        quarterButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quarterButtonActionPerformed(evt);
            }
        });

        halfButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                halfButtonActionPerformed(evt);
            }
        });

        playStopToggle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playStopToggleActionPerformed(evt);
            }
        });

        noteRestToggle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                noteRestToggleActionPerformed(evt);
            }
        });

        crudButton.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {"Add", "Delete", "Clear", "Export", "Import"}));
        crudButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                crudButtonActionPerformed(evt);
            }
        });

        instrumentButton.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {"PIANO", "CLAVINET", "CELESTA", "GLOCKENSPIEL", "VIBRAPHONE", "MARIMBA", "XYLOPHONE", "DULCIMER", "ACCORDION", "HARMONICA", "GUITAR", "VIOLIN", "VIOLA", "CELLO", "CONTRABASS", "TIMPANI", "TRUMPET", "TROMBONE", "TUBA", "OBOE", "BASSOON", "CLARINET", "PICCOLO", "FLUTE", "RECORDER", "SHAKUHACHI", "WHISTLE", "OCARINA", "SAWTOOTH", "BASSLEAD", "METALLIC", "GOBLINS", "SITAR", "BANJO", "SHAMISEN", "KOTO", "KALIMBA", "BAGPIPE", "FIDDLE", "SHANAI", "AGOGO", "WOODBLOCK", "SEASHORE", "APPLAUSE", "GUNSHOT"}));
        instrumentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                instrumentButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout composerPanelLayout = new javax.swing.GroupLayout(composerPanel);
        composerPanel.setLayout(composerPanelLayout);
        composerPanelLayout.setHorizontalGroup(
            composerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, composerPanelLayout.createSequentialGroup()
                .addGroup(composerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(composerPanelLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(backButton, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38)
                        .addComponent(playStopToggle, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(composerPanelLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(quarterButton)
                        .addGap(39, 39, 39)
                        .addComponent(halfButton)
                        .addGap(39, 39, 39)
                        .addComponent(wholeButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 133, Short.MAX_VALUE)
                        .addGroup(composerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(crudButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(instrumentButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(composerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(composerPanelLayout.createSequentialGroup()
                        .addGap(398, 398, 398)
                        .addComponent(noteRestToggle, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(composerPanelLayout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(forwardButton, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(16, 16, 16))
        );
        composerPanelLayout.setVerticalGroup(
            composerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(composerPanelLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(composerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(composerPanelLayout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(crudButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(instrumentButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(composerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(wholeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(halfButton, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(noteRestToggle, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(quarterButton, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 508, Short.MAX_VALUE)
                .addGroup(composerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, composerPanelLayout.createSequentialGroup()
                        .addComponent(playStopToggle, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, composerPanelLayout.createSequentialGroup()
                        .addComponent(backButton, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, composerPanelLayout.createSequentialGroup()
                        .addComponent(forwardButton, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30))))
        );

        tabs.addTab("Composer", composerPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(tabs)
                .addGap(12, 12, 12))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(tabs)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void pause_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pause_buttonActionPerformed
        //sets the buffered image to nothing so if the ffile doesnt exist it doesnt crash
        BufferedImage icons = null;
        try {
            
            icons = ImageIO.read(new File("buttons\\pause.png"));
            
        } catch (IOException ex) {
            
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        //set the icon of the pause button to an image of the pause icon
        Image pause = icons.getScaledInstance(40, 30, Image.SCALE_SMOOTH);
        pause_button.setIcon(new ImageIcon(pause));
        mediaPlayer.pause();
        //pauses the clip playing
        System.out.println(songNumber);
    }//GEN-LAST:event_pause_buttonActionPerformed

    //a deprecated method--no longer used
    private void volume_sliderMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_volume_sliderMousePressed
        //        volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
        //
        //			@Override
        //			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
        //
        //				mediaPlayer.setVolume(volumeSlider.getValue() * 0.01);
        //			}
        //		});
    }//GEN-LAST:event_volume_sliderMousePressed

    //the reader that plays the .wav when clicked
    private void play_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_play_buttonActionPerformed
        mediaPlayer.pause();
        BufferedImage icons = null;
        try {
            
            icons = ImageIO.read(new File("buttons\\play.png"));
            
        } catch (IOException ex) {
            
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        Image play = icons.getScaledInstance(45, 36, Image.SCALE_SMOOTH);
        play_button.setIcon(new ImageIcon(play));
        //actually plays the clip
        mediaPlayer.play();
        //sets  the icon of the play button to the play icon
        headers = songs.get(songNumber).split("\\.");
        albumName2_label.setText((headers[0].substring(91)));
        albumName_label.setText((headers[0].substring(91)));
        artist_label.setText(headers[1]);
        //spllits the authors name rom the rest of the info and sets the name to that
        
        int one = 1;
        System.out.println(one);
    }//GEN-LAST:event_play_buttonActionPerformed

    //goes to the previous song in the table
    private void skipBack_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_skipBack_buttonActionPerformed
        Image img2 = pause.getScaledInstance(48, 36, Image.SCALE_SMOOTH);
        Image img3 = play.getScaledInstance(48, 36, Image.SCALE_SMOOTH);
        //if the media being played is not the first in the list it will
        if (songNumber > 0) {
            songNumber--;
            mediaPlayer.stop();
            //stops all current media playing
            String media = songs.get(songNumber);
            try {
                
                mediaPlayer = new MediaPlayer(media);
                
            } catch (UnsupportedAudioFileException ex) {
                
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                
            } catch (LineUnavailableException ex) {
                
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                
            } catch (IOException ex) {
                
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                
            }
            mediaPlayer.play();
            //plays the song before the current song in the list
        } else {
            // if the current song is the first in the list it will just restart the song
            songNumber = 0;
            mediaPlayer.stop();
            //stops all media being played
            String media = null;
            try {
                
                mediaPlayer = new MediaPlayer(media);
                
            } 
            catch (UnsupportedAudioFileException ex) {
                
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                
            }
            catch (LineUnavailableException ex) {
                
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                
            }
            catch (IOException ex) {
                
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                
            }
            mediaPlayer.play();
            //plays the first song in the list from the beginning
        }
        headers = songs.get(songNumber).split("\\.");
        albumName2_label.setText((headers[0].substring(91)));
        albumName_label.setText((headers[0].substring(91)));
        artist_label.setText(headers[1]);
        //resets all the song name and artist names on the GUi to that of the current song being played

        System.out.println(songNumber);
    }//GEN-LAST:event_skipBack_buttonActionPerformed

    /*
    //reader that checks if the mouse cursor moved
    private void mouseMoved(MouseEvent e) {
        ((GraphicPanel) composerPanel).updateMouseCoords(getX(), getY());
    }
     */

    //goes to the next song in the cycle
    private void skipFor_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_skipFor_buttonActionPerformed
        //Image img2 = pause.getScaledInstance(48, 36, Image.SCALE_SMOOTH);
        //Image img3 = play.getScaledInstance(48, 36, Image.SCALE_SMOOTH);
        //makes sure that if the current song is within the length of the list it can be chosen
        if (songNumber < songs.size() - 1) {
            
            songNumber++;
            mediaPlayer.stop();
            //stops all current media
            String media = songs.get(songNumber);
            try {
                
                mediaPlayer = new MediaPlayer(media);
                
            }
            
            catch (UnsupportedAudioFileException ex)
            {
                
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                
            }
            catch (LineUnavailableException ex) {
                
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                
            }
            catch (IOException ex) {
                
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                
            }
            mediaPlayer.play();
            //moves the song number up one and plays it
        } else {
            //if the current song is at the last spot on the list it will restart the album
            songNumber = 0;
            mediaPlayer.stop();
            //stops all current media
            String media = null;
            try
            {
                
                mediaPlayer = new MediaPlayer(media);
                
            }
            catch (UnsupportedAudioFileException ex)
            {
                
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                
            }
            catch (LineUnavailableException ex) {
                
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                
            }
            catch (IOException ex)
            {
                
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                
            }
            mediaPlayer.play();
            //plays the first song in the list
        }
        headers = songs.get(songNumber).split("\\.");
        albumName2_label.setText((headers[0].substring(91)));
        albumName_label.setText((headers[0].substring(91)));
        artist_label.setText(headers[1]);
        //resets all the song name and artist names on the GUi to that of the current song being played
        System.out.println(songNumber);
    }//GEN-LAST:event_skipFor_buttonActionPerformed

    private void Shuffle_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Shuffle_buttonActionPerformed
        mediaPlayer.pause();

        if ((Math.random() * directory.listFiles().length) < .5) {
            songNumber = 0;
        } else {
            songNumber = (int) (Math.random() * files.length);
        }
        //sets the song number to a random number less than the length of the songs in the list 
        try {
            
            mediaPlayer = new MediaPlayer(songs.get(songNumber));
            
        }
        catch (UnsupportedAudioFileException ex)
        {
            
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        catch (LineUnavailableException ex) {
            
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        catch (IOException ex) {
            
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        System.out.println(songs);
        mediaPlayer.play();
        //plays the randomized song list
        headers = songs.get(songNumber).split("\\.");
        albumName2_label.setText((headers[0].substring(91)));
        albumName_label.setText((headers[0].substring(91)));
        artist_label.setText(headers[1]);
        //resets the name of the song and the artist shown to the current song being played
        System.out.println(songNumber);
    }//GEN-LAST:event_Shuffle_buttonActionPerformed

    private void play2_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_play2_buttonActionPerformed
        mediaPlayer.pause();
        BufferedImage icons = null;
        try {
            icons = ImageIO.read(new File("buttons\\play.png"));
        } catch (IOException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        Image play = icons.getScaledInstance(45, 36, Image.SCALE_SMOOTH);
        play_button.setIcon(new ImageIcon(play));
        mediaPlayer.play();
        System.out.println(songNumber);
        //does the same thing and the other play button its just in a differen t location
    }//GEN-LAST:event_play2_buttonActionPerformed

    //turns the current note to a whole note or whole rest depending on the current state
    private void wholeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wholeButtonActionPerformed
        if (!restMode)
            
            ((GraphicPanel) composerPanel).changeCurrentSet(2);
        
        else
            
            ((GraphicPanel) composerPanel).changeCurrentSet(5);
    }//GEN-LAST:event_wholeButtonActionPerformed

    private void noteRestToggleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_noteRestToggleActionPerformed
        if (restMode) {
            //sets starting icons for the three placement buttons in the composer
            quarterButton.setIcon(new ImageIcon(noteQ));
            halfButton.setIcon(new ImageIcon(noteH));
            wholeButton.setIcon(new ImageIcon(noteW));

            //default starting state is in note mode, so the toggle should demonstrate the option to go to rest mode
            noteRestToggle.setText("To Rest");
        } else {
            //sets the alternate set of icons for the three placement buttons in the composer
            quarterButton.setIcon(new ImageIcon(restQ));
            halfButton.setIcon(new ImageIcon(restH));
            wholeButton.setIcon(new ImageIcon(restW));

            //turns it to rest mode, so now it asks whether to turn back to note mode
            noteRestToggle.setText("To Note");
        }
        //toggles restMode for future access
        restMode = !restMode;
        ((GraphicPanel) composerPanel).toggleRest();
    }//GEN-LAST:event_noteRestToggleActionPerformed

    private void playStopToggleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_playStopToggleActionPerformed
        //changes the icon depending on the current state
        if (!playMode) {
            playStopToggle.setIcon(new ImageIcon(pause2));
            //((GraphicPanel) composerPanel).setShouldPlay(true);
            try
            {
                
                ((GraphicPanel) composerPanel).startPlay();
                
            }
            catch (InterruptedException ex) {
                
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                
            }
            
            //changes it back to the play icon once all processes are executed
            //playStopToggle.setIcon(new ImageIcon(play2));
        } else {
            
            playStopToggle.setIcon(new ImageIcon(play2));
            //((GraphicPanel) composerPanel).setShouldPlay(false);
            
        }

        //toggles the variables storing the current state
        playMode = !playMode;
        ((GraphicPanel) composerPanel).togglePlay();
    }//GEN-LAST:event_playStopToggleActionPerformed

    //updatesMouseCoordinates if the mouse is moved at all
    private void composerPanelMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_composerPanelMouseMoved
        ((GraphicPanel) composerPanel).updateMouseCoords(evt.getX(), evt.getY());
    }//GEN-LAST:event_composerPanelMouseMoved

    //the next two methods make it so that the staff can be moved from side to side
    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
        //((GraphicPanel) composerPanel).frameShift(-10);
    }//GEN-LAST:event_backButtonActionPerformed

    private void forwardButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_forwardButtonActionPerformed
        //((GraphicPanel) composerPanel).frameShift(10);
    }//GEN-LAST:event_forwardButtonActionPerformed

    //the next two methods make it so that the staff can be moved from side to side
    private void backButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backButtonMouseClicked
        ((GraphicPanel) composerPanel).frameShift(-100);
    }//GEN-LAST:event_backButtonMouseClicked

    private void forwardButtonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_forwardButtonMousePressed
        ((GraphicPanel) composerPanel).frameShift(100);
    }//GEN-LAST:event_forwardButtonMousePressed

    //change the note/rest to be placed--factors in the rest/note toggle (half and quarter notes only)
    private void quarterButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quarterButtonActionPerformed
        if (!restMode)
            ((GraphicPanel) composerPanel).changeCurrentSet(0);
        else
            ((GraphicPanel) composerPanel).changeCurrentSet(3);
    }//GEN-LAST:event_quarterButtonActionPerformed

    private void halfButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_halfButtonActionPerformed
        if (!restMode)
            
            ((GraphicPanel) composerPanel).changeCurrentSet(1);
        
        else
            
            ((GraphicPanel) composerPanel).changeCurrentSet(4);
        
    }//GEN-LAST:event_halfButtonActionPerformed

    //allows the user to place down different notes in the composer
    private void composerPanelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_composerPanelMousePressed
        ((GraphicPanel) composerPanel).updateList();
    }//GEN-LAST:event_composerPanelMousePressed

    private void progressBar_sliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_progressBar_sliderStateChanged
        mediaPlayer.setFrames((long) (mediaPlayer.getMicrosecondLength() * ((double) (progressBar_slider.getValue() / 100.0))));
        //finds a percentage of the bar the point is on and sets the current frames to that percentage of the songs length
    }//GEN-LAST:event_progressBar_sliderStateChanged

    //calls the fast backward method when the mouse is dragged on the button
    private void backButtonMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backButtonMouseDragged
        ((GraphicPanel) composerPanel).fastBackward();
    }//GEN-LAST:event_backButtonMouseDragged

    //calls the fast forward method when the mouse is dragged on the button
    private void forwardButtonMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_forwardButtonMouseDragged
        //((GraphicPanel) composerPanel).fastForward();
    }//GEN-LAST:event_forwardButtonMouseDragged

    private void crudButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_crudButtonActionPerformed
        String chosen = (String) crudButton.getSelectedItem();

        //import function using BufferedReader
        if (chosen.equals("Import")) {
            
            BufferedReader brTest = null;
            String text = "";
            try {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
                fileChooser.setFileFilter(new FileNameExtensionFilter("Text Files", "txt"));
                int result = fileChooser.showOpenDialog(crudButton);
                File selectedFile = null;
                if (result == JFileChooser.APPROVE_OPTION) {
                    selectedFile = fileChooser.getSelectedFile();
                }
                brTest = new BufferedReader(new FileReader(selectedFile));
                text = brTest.readLine();
            }
            catch (FileNotFoundException ex) {
                
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                
            }
            catch (IOException ex) {
                
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                
            }
            finally {
                
                try {
                    
                    brTest.close();
                    
                }
                catch (IOException ex) {
                    
                    Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                    
                }
            }
            ((GraphicPanel) composerPanel).setList(text);
            crudButton.setSelectedIndex(0);
        }
        
        //export function using BufferedWriter
        else if (chosen.equals("Export")) {
            BufferedWriter bwTest = null;
            String text = ((GraphicPanel) composerPanel).getList();
            try {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
                fileChooser.setFileFilter(new FileNameExtensionFilter("Text Files", "txt"));
                int result = fileChooser.showOpenDialog(crudButton);
                File selectedFile = null;
                if (result == JFileChooser.APPROVE_OPTION) {
                    selectedFile = fileChooser.getSelectedFile();
                }
                bwTest = new BufferedWriter(new FileWriter(selectedFile));
                bwTest.write(text);
            }
            catch (FileNotFoundException ex) {
                
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                
            }
            catch (IOException ex) {
                
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                
            }
            finally {
                
                try {
                    
                    bwTest.close();
                    
                }
                catch (IOException ex)
                {
                    
                    Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                    
                }
            }
            crudButton.setSelectedIndex(0);
        }
        
        //activates delete mode
        else if (chosen.equals("Delete"))
        {
            
            ((GraphicPanel) composerPanel).typeChange(1);
            
        }
        
        //reverts to add mode
         else if (chosen.equals("Add"))
        {
            
            ((GraphicPanel) composerPanel).typeChange(0); 
            
        }
        
        //experimental edit program
        else if (chosen.equals("Edit"))
        {
            
           ((GraphicPanel) composerPanel).typeChange(2);
           
        }
        
         //clears all the notes
         else if (chosen.equals("Clear"))
         {
             
            ((GraphicPanel) composerPanel).purgeList();
            
         }
        
        //rudButton.setSelectedIndex(0);
    }//GEN-LAST:event_crudButtonActionPerformed

    private void instrumentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_instrumentButtonActionPerformed
        ((GraphicPanel) composerPanel).setInstrument((String) instrumentButton.getSelectedItem());
    }//GEN-LAST:event_instrumentButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try
                {
                    
                    new MainWindow().setVisible(true);
                    
                }
                catch (IOException ex) {
                    
                    Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                    
                }
                catch (UnsupportedAudioFileException ex) {
                    
                    Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                    
                } 
                catch (LineUnavailableException ex) {
                    
                    Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                    
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Shuffle_button;
    private javax.swing.JLabel albumName2_label;
    private javax.swing.JLabel albumName_label;
    private javax.swing.JLabel artist_label;
    private javax.swing.JButton backButton;
    private javax.swing.JPanel composerPanel;
    private javax.swing.JLabel coverShow2_label;
    private javax.swing.JLabel coverShow_label;
    private javax.swing.JComboBox<String> crudButton;
    private javax.swing.JButton forwardButton;
    private javax.swing.JButton halfButton;
    private javax.swing.JComboBox<String> instrumentButton;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToggleButton noteRestToggle;
    private javax.swing.JButton pause_button;
    private javax.swing.JButton play2_button;
    private javax.swing.JToggleButton playStopToggle;
    private javax.swing.JButton play_button;
    private javax.swing.JPanel player_panel;
    private javax.swing.JSlider progressBar_slider;
    private javax.swing.JButton quarterButton;
    private javax.swing.JPanel shuffle_panel;
    private javax.swing.JButton skipBack_button;
    private javax.swing.JButton skipFor_button;
    private javax.swing.JTable songsTable_table;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JSlider volume_slider;
    private javax.swing.JButton wholeButton;
    // End of variables declaration//GEN-END:variables
    private void buildSongsTable() throws UnsupportedAudioFileException, LineUnavailableException, IOException {

        Object[][] data = new Object[directory.listFiles().length][4];
        String[] columnHeaders = {"Title", "Artist", "Genre", "Time"};
        int row = 0;
        //creates a table with 4 columns and loops through to add files for the length of the music folder
        for (int i = 0; i < files.length; i++) {
            
            headers = songs.get(i).split("\\.");
            //splits the file name into parts to get info from each music file
            mediaPlayer = new MediaPlayer(songs.get(i));
            data[row][0] = headers[0].substring(91);
            //bulds the first column of the table to include the song name and substrings out all the unnescessary info
            data[row][1] = headers[1];
            //builds the secong column to include the split artist name
            data[row][2] = headers[2];
            //builds the third column to include the split genre of the music being played
            data[row][3] = ((int) ((mediaPlayer.getMicrosecondLength()) / 1000000)) + " seconds";
            //builds the fourth column in the table to include the length of the song in seconds
            row++;
            
        }
        headers = songs.get(songNumber).split("\\.");
        artist_label.setText(headers[1]);
        DefaultTableModel dfm = new DefaultTableModel(data, columnHeaders);
        songsTable_table.setModel(dfm);
        //builds the table and sets the artist label to that of the split headers above
    }

}
