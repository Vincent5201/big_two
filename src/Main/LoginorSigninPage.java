package Main;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

public class LoginorSigninPage implements ActionListener{

    JFrame frame = new JFrame("Select Login or Signin.");

    JButton gotoLoginButton = new JButton("Login");
    JButton gotoSigninButton = new JButton("Signin");
    JButton helpButton = new JButton("Help");

    HashMap<String,String> logininfo = new HashMap<String,String>();

    Object[][] IDset=new Object[logininfo.keySet().size()][1];
    
    public static Clip clipBackground1;

    LoginorSigninPage(HashMap<String,String> loginInfoOriginal){

        ImageIcon imageIcon = new ImageIcon("images/background1.jpg");
        Image image=imageIcon.getImage();
        image = image.getScaledInstance(600,600 ,Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(image);
        Container c = new JLabel(imageIcon);
        frame.setContentPane(c);

        logininfo = loginInfoOriginal;

        gotoLoginButton.setBounds(240,200,100,25);
        gotoLoginButton.setFocusable(false);
        gotoLoginButton.addActionListener(this);

        gotoSigninButton.setBounds(240,250,100,25);
        gotoSigninButton.setFocusable(false);
        gotoSigninButton.addActionListener(this);

        helpButton.setBounds(240,300,100,25);
        helpButton.setFocusable(false);
        helpButton.addActionListener(this);
        
        try {
        	File file = new File("music/PokerBackground1.wav");
    		AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
    		Clip clip = AudioSystem.getClip();
    		clip.open(audioStream);
    		clip.start();
    		clipBackground1=clip;
    		clip.loop(clip.LOOP_CONTINUOUSLY);
        }
        catch(Exception e) {
        	e.printStackTrace();
        }

        frame.add(gotoLoginButton);
        frame.add(gotoSigninButton);
        frame.add(helpButton);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(500,100);
        frame.setSize(600,600);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setResizable(false);

    }

    @Override

    public void actionPerformed(ActionEvent e) {
        IDandPasswords idandPasswords = new IDandPasswords();
        if(e.getSource()==gotoSigninButton) {
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
            frame.dispose();
            SigninPage signinPage=new SigninPage(logininfo);
        }
        if(e.getSource()==gotoLoginButton) {
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
            frame.dispose();
            LoginPage loginPage =new LoginPage(logininfo);
        }
        if(e.getSource()==helpButton){
        	try {
        		File file = new File("music/PokerButton2.wav");
        		AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
        		Clip clip = AudioSystem.getClip();
        		clip.open(audioStream);
        		clip.start();
        	}
        	catch(Exception e1) {
        		e1.printStackTrace();
        	}
        }
        if(e.getSource() == helpButton){
            HelpScene helpScene = new HelpScene();
        }
    }
}

