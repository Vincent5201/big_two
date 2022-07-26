package Main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;

public class StartFrame extends JFrame implements ActionListener {

    JFrame frame = new JFrame("Play Game or View History");

    JButton startButton;
    JButton viewHistoryButton;

    HashMap<String,String> logininfo = new HashMap<String,String>();
    String ID;
    
    StartFrame(String userID){
    	 ID = userID;
    	 
    	//background
        ImageIcon imageIcon = new ImageIcon("images/background1.jpg");
        Image image=imageIcon.getImage();
        image = image.getScaledInstance(500,500 ,Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(image);
        Container c = new JLabel(imageIcon);
        this.setContentPane(c);

        //buttons
        startButton = new JButton();
        startButton.setText("Start");
        startButton.setOpaque(true);
        startButton.setFocusable(false);
        startButton.setBounds(130,100,200,80);
        startButton.setBackground(Color.cyan);
        startButton.setFont(new Font("Comis Sans",Font.BOLD,20));
        startButton.addActionListener(this);
        startButton.setHorizontalAlignment(JButton.CENTER);
        startButton.setVerticalAlignment(JButton.CENTER);

        viewHistoryButton = new JButton();
        viewHistoryButton.setText("View History");
        viewHistoryButton.setOpaque(true);
        viewHistoryButton.setFocusable(false);
        viewHistoryButton.setBounds(130,200,200,80);
        viewHistoryButton.setBackground(Color.cyan);
        viewHistoryButton.setFont(new Font("Comis Sans",Font.BOLD,20));
        viewHistoryButton.addActionListener(this);
        viewHistoryButton.setHorizontalAlignment(JButton.CENTER);
        viewHistoryButton.setVerticalAlignment(JButton.CENTER);
        
        //frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(500,100,500,500);
        this.setLayout(null);
        this.setResizable(false);

        this.add(startButton);
        this.add(viewHistoryButton);
        this.setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
    	
        String userID = ID;
        if(e.getSource()==startButton){
        	
        	LoginorSigninPage.clipBackground1.stop();
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
        else if(e.getSource() ==  viewHistoryButton){
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
        	ViewHistory viewHistory= new ViewHistory(userID);
        }
    }
}
