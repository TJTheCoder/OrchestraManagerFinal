/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.orchestramanagerfinal;

import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
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
import javax.swing.Timer;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author atxbr
 */
public class MainWindow extends javax.swing.JFrame {

    private final BufferedImage pause = ImageIO.read(new File("buttons\\pause.png"));
    private final BufferedImage play = ImageIO.read(new File("buttons\\play.png"));

    private File directory;
    private File[] files;

    private File directory2;
    private File[] files2;

    private ArrayList<File> songs;
    private ArrayList<File> covers;

    private int songNumber;
    private ArrayList<String> filePath;
    private String songs1;
    private String songs2;

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

        songs = new ArrayList<>();
        directory = new File("music");
        files = directory.listFiles();
        if (files != null) {
            songs.addAll(Arrays.asList(files));
            songs1 = "music\\Butter.wav";
            songs2 = "music\\Solo.wav";
        }
        System.out.println(songs);

        BufferedImage icons = ImageIO.read(new File("buttons\\pause.png"));
        BufferedImage icons2 = ImageIO.read(new File("buttons\\play.png"));

        //initializes icons for all the notes/rests in the Composer panel
        BufferedImage restWIcon = ImageIO.read(new File("notes\\restW.png"));
        BufferedImage restHIcon = ImageIO.read(new File("notes\\restH.png"));
        BufferedImage restQIcon = ImageIO.read(new File("notes\\restQ.png"));
        BufferedImage noteWIcon = ImageIO.read(new File("notes\\noteW.png"));
        BufferedImage noteHIcon = ImageIO.read(new File("notes\\noteH.png"));
        BufferedImage noteQIcon = ImageIO.read(new File("notes\\noteQ.png"));

        Image pause = icons.getScaledInstance(40, 30, Image.SCALE_SMOOTH);
        Image play = icons2.getScaledInstance(45, 36, Image.SCALE_SMOOTH);

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

        play_button.setIcon(new ImageIcon(play));
        pause_button.setIcon(new ImageIcon(pause));

        //sets the composer play button to default, which is the play button
        PlayStop.setIcon(new ImageIcon(play2));
        playMode = false;

        //sets starting icons for the three placement buttons in the composer
        Quarter.setIcon(new ImageIcon(noteQ));
        Half.setIcon(new ImageIcon(noteH));
        Whole.setIcon(new ImageIcon(noteW));

        //default starting state is in note mode, so the toggle should demonstrate the option to go to rest mode
        NoteRest.setText("To Rest");
        restMode = false;

        BufferedImage img = ImageIO.read(new File("images\\cover.png"));
        Image img1 = img.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        Image img2 = img.getScaledInstance(250, 241, Image.SCALE_SMOOTH);

        coverShow_label.setIcon(new ImageIcon(img1));
        coverShow2_label.setIcon(new ImageIcon(img1));

        ((GraphicPanel) composerPanel).paint();

        covers = new ArrayList<File>();
        directory2 = new File("music");
        files2 = directory.listFiles();
        if (files2 != null) {
            covers.addAll(Arrays.asList(files2));
        }

        coverShow_label.setIcon(new ImageIcon(img1));
        coverShow2_label.setIcon(new ImageIcon(img2));
        //media = new Media(songs.get(songNumber).toURI().toString());
        //mediaPlayer = new MediaPlayer(media);

        albumName2_label.setText((songs.get(songNumber).getName().substring(0, songs.get(songNumber).getName().length() - 4)));
        albumName_label.setText((songs.get(songNumber).getName().substring(0, songs.get(songNumber).getName().length() - 4)));

        artist_label.setText(getArtist());
        buildSongsTable();
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
        Forward = new javax.swing.JButton();
        Back = new javax.swing.JButton();
        Whole = new javax.swing.JButton();
        Quarter = new javax.swing.JButton();
        Half = new javax.swing.JButton();
        PlayStop = new javax.swing.JToggleButton();
        NoteRest = new javax.swing.JToggleButton();
        CRUDButton = new javax.swing.JComboBox<>();

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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabs.addTab("Shuffle", shuffle_panel);

        player_panel.setBackground(new java.awt.Color(0, 51, 102));

        progressBar_slider.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                progressBar_sliderMousePressed(evt);
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
                        .addComponent(coverShow_label, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(337, 337, 337))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, player_panelLayout.createSequentialGroup()
                        .addComponent(albumName_label, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(360, 360, 360))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, player_panelLayout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(88, 88, 88))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, player_panelLayout.createSequentialGroup()
                        .addComponent(artist_label, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(447, 447, 447))))
        );
        player_panelLayout.setVerticalGroup(
            player_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, player_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(coverShow_label, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(albumName_label, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(artist_label, javax.swing.GroupLayout.DEFAULT_SIZE, 18, Short.MAX_VALUE)
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

        Whole.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                WholeActionPerformed(evt);
            }
        });

        PlayStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PlayStopActionPerformed(evt);
            }
        });

        NoteRest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NoteRestActionPerformed(evt);
            }
        });

        CRUDButton.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout composerPanelLayout = new javax.swing.GroupLayout(composerPanel);
        composerPanel.setLayout(composerPanelLayout);
        composerPanelLayout.setHorizontalGroup(
            composerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, composerPanelLayout.createSequentialGroup()
                .addGroup(composerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(composerPanelLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Back, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38)
                        .addComponent(PlayStop, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(composerPanelLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(Quarter)
                        .addGap(39, 39, 39)
                        .addComponent(Half)
                        .addGap(39, 39, 39)
                        .addComponent(Whole)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 133, Short.MAX_VALUE)
                        .addComponent(CRUDButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(composerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(composerPanelLayout.createSequentialGroup()
                        .addGap(398, 398, 398)
                        .addComponent(NoteRest, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(composerPanelLayout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(Forward, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(16, 16, 16))
        );
        composerPanelLayout.setVerticalGroup(
            composerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(composerPanelLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(composerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(composerPanelLayout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(CRUDButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(composerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(Whole, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(Half, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(NoteRest, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(Quarter, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 422, Short.MAX_VALUE)
                .addGroup(composerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, composerPanelLayout.createSequentialGroup()
                        .addComponent(PlayStop, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, composerPanelLayout.createSequentialGroup()
                        .addComponent(Back, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, composerPanelLayout.createSequentialGroup()
                        .addComponent(Forward, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
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
        MediaPlayer mediaPlayer = null;
        try {
            mediaPlayer = new MediaPlayer(songs1);
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        BufferedImage icons = null;
        try {
            icons = ImageIO.read(new File("buttons\\pause.png"));
        } catch (IOException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        Image pause = icons.getScaledInstance(40, 30, Image.SCALE_SMOOTH);
        pause_button.setIcon(new ImageIcon(pause));
        mediaPlayer.pause();
        System.out.println(songNumber);
    }//GEN-LAST:event_pause_buttonActionPerformed

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

    private void play_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_play_buttonActionPerformed
        MediaPlayer mediaPlayer = null;
        try {
            mediaPlayer = new MediaPlayer(songs1);
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        BufferedImage icons = null;
        try {
            icons = ImageIO.read(new File("buttons\\play.png"));
        } catch (IOException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        Image play = icons.getScaledInstance(45, 36, Image.SCALE_SMOOTH);
        play_button.setIcon(new ImageIcon(play));
        mediaPlayer.play();
        int one = 1;
        System.out.println(one);
    }//GEN-LAST:event_play_buttonActionPerformed

    private void skipBack_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_skipBack_buttonActionPerformed
        MediaPlayer mediaPlayer = null;
        Image img2 = pause.getScaledInstance(48, 36, Image.SCALE_SMOOTH);
        Image img3 = play.getScaledInstance(48, 36, Image.SCALE_SMOOTH);

        try {
            mediaPlayer = new MediaPlayer(songs1);
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (songNumber > 0) {
            songNumber--;
            mediaPlayer.pause();
            String media = songs.get(songNumber).getName();
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
        } else {
            songNumber = 0;
            mediaPlayer.pause();
            String media = null;
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
        }
        System.out.println(songNumber);
    }//GEN-LAST:event_skipBack_buttonActionPerformed

    /*
    //reader that checks if the mouse cursor moved
    private void mouseMoved(MouseEvent e) {
        ((GraphicPanel) composerPanel).updateMouseCoords(getX(), getY());
    }
     */

    private void skipFor_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_skipFor_buttonActionPerformed
        MediaPlayer mediaPlayer = null;
        //Image img2 = pause.getScaledInstance(48, 36, Image.SCALE_SMOOTH);
        //Image img3 = play.getScaledInstance(48, 36, Image.SCALE_SMOOTH);

        try {
            mediaPlayer = new MediaPlayer(songs2);
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (songNumber < songs.size() - 1) {
            songNumber++;
            mediaPlayer.pause();
            String media = songs.get(songNumber).getName();
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
        } else {
            songNumber = 0;
            mediaPlayer.pause();
            String media = null;
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
        }
        System.out.println(songNumber);
    }//GEN-LAST:event_skipFor_buttonActionPerformed

    private void progressBar_sliderMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_progressBar_sliderMousePressed
        //        MediaPlayer.seek(Duration.seconds(progressBar.getValue()));
    }//GEN-LAST:event_progressBar_sliderMousePressed

    private void Shuffle_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Shuffle_buttonActionPerformed
        if (songNumber != (int) (Math.random() * directory.listFiles().length)) {
            songNumber = (int) (Math.random() * directory.listFiles().length);
        }
        songs = new ArrayList<>();
        directory = new File("music");
        files = directory.listFiles();
        if (files != null) {
            songs.addAll(Arrays.asList(files));
        }
        System.out.println(songs);
        MediaPlayer mediaPlayer = null;
        try {
            mediaPlayer = new MediaPlayer(songs2);
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        mediaPlayer.play();
        //        MediaPlayer.play(songNumber);
        System.out.println(songNumber);
    }//GEN-LAST:event_Shuffle_buttonActionPerformed

    private void play2_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_play2_buttonActionPerformed
        MediaPlayer mediaPlayer = null;
        try {
            mediaPlayer = new MediaPlayer(songs1);
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
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
    }//GEN-LAST:event_play2_buttonActionPerformed

    private void WholeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_WholeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_WholeActionPerformed

    private void NoteRestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NoteRestActionPerformed
        if (restMode) {
            //sets starting icons for the three placement buttons in the composer
            Quarter.setIcon(new ImageIcon(noteQ));
            Half.setIcon(new ImageIcon(noteH));
            Whole.setIcon(new ImageIcon(noteW));

            //default starting state is in note mode, so the toggle should demonstrate the option to go to rest mode
            NoteRest.setText("To Rest");
        } else {
            //sets the alternate set of icons for the three placement buttons in the composer
            Quarter.setIcon(new ImageIcon(restQ));
            Half.setIcon(new ImageIcon(restH));
            Whole.setIcon(new ImageIcon(restW));

            //turns it to rest mode, so now it asks whether to turn back to note mode
            NoteRest.setText("To Note");
        }
        //toggles restMode for future access
        restMode = !restMode;
    }//GEN-LAST:event_NoteRestActionPerformed

    private void PlayStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PlayStopActionPerformed
        //changes the icon depending on the current state
        if (!playMode) {
            PlayStop.setIcon(new ImageIcon(pause2));
        } else {
            PlayStop.setIcon(new ImageIcon(play2));
        }

        //toggles the variables storing the current state
        playMode = !playMode;
    }//GEN-LAST:event_PlayStopActionPerformed

    //updatesMouseCoordinates if the mouse is moved at all
    private void composerPanelMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_composerPanelMouseMoved
        ((GraphicPanel) composerPanel).updateMouseCoords(evt.getX(), evt.getY());
    }//GEN-LAST:event_composerPanelMouseMoved

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
                try {
                    new MainWindow().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnsupportedAudioFileException ex) {
                    Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                } catch (LineUnavailableException ex) {
                    Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Back;
    private javax.swing.JComboBox<String> CRUDButton;
    private javax.swing.JButton Forward;
    private javax.swing.JButton Half;
    private javax.swing.JToggleButton NoteRest;
    private javax.swing.JToggleButton PlayStop;
    private javax.swing.JButton Quarter;
    private javax.swing.JButton Shuffle_button;
    private javax.swing.JButton Whole;
    private javax.swing.JLabel albumName2_label;
    private javax.swing.JLabel albumName_label;
    private javax.swing.JLabel artist_label;
    private javax.swing.JPanel composerPanel;
    private javax.swing.JLabel coverShow2_label;
    private javax.swing.JLabel coverShow_label;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton pause_button;
    private javax.swing.JButton play2_button;
    private javax.swing.JButton play_button;
    private javax.swing.JPanel player_panel;
    private javax.swing.JSlider progressBar_slider;
    private javax.swing.JPanel shuffle_panel;
    private javax.swing.JButton skipBack_button;
    private javax.swing.JButton skipFor_button;
    private javax.swing.JTable songsTable_table;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JSlider volume_slider;
    // End of variables declaration//GEN-END:variables
    private void buildSongsTable() {
        Object[][] data = new Object[directory.listFiles().length][4];
        String[] columnHeaders = {"Title", "Artist", "Genre", "Time"};
        int row = 0;
        for (File i : files) {
//            Schedule schedule = schedules.get(key);
            data[row][0] = (songs.get(songNumber).getName().substring(0, songs.get(songNumber).getName().length() - 4));
            if (data[row][0].equals("Butter") || data[row][0].equals("Solo")) {
                data[row][1] = "BTS";
            } else {
                data[row][1] = (songs.get(songNumber).getName().substring(0, songs.get(songNumber).getName().length() - 4));
            }
            if (data[row][0].equals("Butter") || data[row][0].equals("Solo")) {
                data[row][2] = "K-Pop";
            } else {
                data[row][2] = (songs.get(songNumber).getName().substring(0, songs.get(songNumber).getName().length() - 2));
            }
            if (data[row][0].equals("Butter") || data[row][0].equals("Solo")) {
                data[row][3] = ((songs.get(songNumber).getName().length()) * 30) + " seconds";
            } else {
                data[row][3] = (30 * (songs.get(songNumber).getName().length())) + " seconds";
            }
            row++;
            songNumber++;

        }
        DefaultTableModel dfm = new DefaultTableModel(data, columnHeaders);
        songsTable_table.setModel(dfm);
    }

    private String getArtist() {
        if (songs.get(songNumber).getName().substring(0, songs.get(songNumber).getName().length() - 4).equals("Butter")) {
            return "BTS";
        }
        return "NONE";
    }

}