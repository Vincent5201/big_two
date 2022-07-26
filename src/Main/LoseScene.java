package Main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class LoseScene extends JFrame implements ActionListener {
    JButton backButton;
    JButton restartButton;
    JLabel winLabel;
    
    public static Clip clipBackgroundLose;

    LoseScene(){

        ImageIcon imageIcon = new ImageIcon("images/square1.jpeg");
        Image image=imageIcon.getImage();
        image = image.getScaledInstance(600,600 ,Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(image);
        Container c = new JLabel(imageIcon);
        this.setContentPane(c);

        this.setBounds(500,100,600,600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        winLabel = new JLabel("You lose");
        winLabel.setForeground(Color.red);
        winLabel.setBounds(220,50,200,30);
        winLabel.setFont(new Font("Comis Sans",Font.BOLD,30));

        backButton = new JButton();
        backButton.setText("Back");
        backButton.setOpaque(true);
        backButton.setFocusable(false);
        backButton.setBounds(220,120,150,50);
        backButton.setBackground(Color.cyan);
        backButton.setFont(new Font("Comis Sans",Font.BOLD,20));
        backButton.addActionListener(this);
        backButton.setHorizontalAlignment(JButton.CENTER);
        backButton.setVerticalAlignment(JButton.CENTER);

        restartButton = new JButton();
        restartButton.setText("Restart");
        restartButton.setOpaque(true);
        restartButton.setFocusable(false);
        restartButton.setBounds(220,200,150,50);
        restartButton.setBackground(Color.cyan);
        restartButton.setFont(new Font("Comis Sans",Font.BOLD,20));
        restartButton.addActionListener(this);
        restartButton.setHorizontalAlignment(JButton.CENTER);
        restartButton.setVerticalAlignment(JButton.CENTER);

        this.add(backButton);
        this.add(restartButton);
        this.add(winLabel);
        this.setVisible(true);
        
        GameScene.clipBackground2.close();
        
        try {
        	File file = new File("music/PokerBackgroundLose.wav");
    		AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
    		Clip clip = AudioSystem.getClip();
    		clip.open(audioStream);
    		clip.start();
    		clipBackgroundLose=clip;
    		clip.loop(clip.LOOP_CONTINUOUSLY);
        }
        catch(Exception e) {
        	e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       
    	if(e.getSource() == backButton){
    		clipBackgroundLose.stop();
    		try {
        		File file = new File("music/PokerButton1.wav");
        		AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
        		Clip clip = AudioSystem.getClip();
        		clip.open(audioStream);
        		clip.start();
        	}
        	catch(Exception e1) {
        		e1.printStackTrace();
        	
        	}
        	LoginorSigninPage.clipBackground1.start();
            this.dispose();
            StartFrame startFrame = new StartFrame(LoginPage.userID);
        }
        
    	if(e.getSource() == restartButton){
    		clipBackgroundLose.stop();
    		try {
        		File file = new File("music/PokerButton1.wav");
        		AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
        		Clip clip = AudioSystem.getClip();
        		clip.open(audioStream);
        		clip.start();
        	}
        	catch(Exception e1) {
        		e1.printStackTrace();
        	}
            this.dispose();
            GameScene gameScene = new GameScene();
        }
    }
}
