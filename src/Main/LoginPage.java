package Main;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

public class LoginPage implements ActionListener{

    public static String userID;
    JFrame frame = new JFrame("LoginPage");

    JButton loginButton = new JButton("Login");
    JButton resetButton = new JButton("Reset");
    JButton backButton=new JButton("Back");

    JTextField userIDField = new JTextField();
    JPasswordField userPasswordField = new JPasswordField();
    
    JLabel userIDLabel = new JLabel("userID:");
    JLabel userPasswordLabel = new JLabel("password:");
    JLabel messageLabel = new JLabel();

    HashMap<String,String> logininfo = new HashMap<String,String>();

    public LoginPage(HashMap<String,String> loginInfoOriginal){

        ImageIcon imageIcon = new ImageIcon("images/square1.jpeg");
        Image image=imageIcon.getImage();
        image = image.getScaledInstance(600,600 ,Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(image);
        Container c = new JLabel(imageIcon);
        frame.setContentPane(c);

        logininfo = loginInfoOriginal;

        userIDLabel.setBounds(50,100,75,25);
        userIDLabel.setForeground(Color.white);

        userPasswordLabel.setBounds(50,150,75,25);
        userPasswordLabel.setForeground(Color.white);

        messageLabel.setBounds(125,350,250,35);
        messageLabel.setFont(new Font(null,Font.ITALIC,25));

        userIDField.setBounds(125,100,200,25);
        userPasswordField.setBounds(125,150,200,25);

        loginButton.setBounds(125,200,100,25);
        loginButton.setFocusable(false);
        loginButton.addActionListener(this);

        resetButton.setBounds(225,200,100,25);
        resetButton.setFocusable(false);
        resetButton.addActionListener(this);

        backButton.setBounds(175,250,100,25);
        backButton.setFocusable(false);
        backButton.addActionListener(this);

        frame.add(userIDLabel);
        frame.add(userPasswordLabel);
        frame.add(messageLabel);
        frame.add(userIDField);
        frame.add(userPasswordField);
        frame.add(loginButton);
        frame.add(resetButton);
        frame.add(backButton);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,600);
        frame.setLocation(500,100);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setResizable(false);
    }

    @Override

    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==backButton) {
        	LoginorSigninPage.clipBackground1.stop();
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
            frame.dispose();
            LoginorSigninPage loginorsigninPage=new LoginorSigninPage(logininfo);
        }
        if(e.getSource()==resetButton) {
        	try {
        		File file = new File("misic/PokerButton2.wav");
        		AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
        		Clip clip = AudioSystem.getClip();
        		clip.open(audioStream);
        		clip.start();
        	}
        	catch(Exception e1) {
        		e1.printStackTrace();
        	}
            userIDField.setText("");
            userPasswordField.setText("");
        }
        if(e.getSource()==loginButton) {
            userID = userIDField.getText();
            String password = String.valueOf(userPasswordField.getPassword());

            if(logininfo.containsKey(userID)) {
                if(logininfo.get(userID).equals(password)) {
                	frame.dispose();
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
                    StartFrame startFrame = new StartFrame(userID);
                }
                else {
                    messageLabel.setForeground(Color.red);
                    messageLabel.setText("Wrong password");
                }
            }
            else {
                messageLabel.setForeground(Color.red);
                messageLabel.setText("username not found");
            }
        }
    }
}
