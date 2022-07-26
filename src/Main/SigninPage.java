package Main;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

public class SigninPage implements ActionListener{

    JFrame frame = new JFrame("SigninPage");

    JButton signinButton = new JButton("Signin");
    JButton resetButton = new JButton("Reset");
    JButton backButton=new JButton("Back");

    JTextField userIDField = new JTextField();
    JPasswordField userPasswordField = new JPasswordField();

    JLabel userIDLabel = new JLabel("New ID:");
    JLabel userPasswordLabel = new JLabel("New password:");
    JLabel messageLabel = new JLabel();

    HashMap<String,String> logininfo = new HashMap<String,String>();

    SigninPage(HashMap<String,String> loginInfoOriginal){

        ImageIcon imageIcon = new ImageIcon("images/square2.jpeg");
        Image image=imageIcon.getImage();
        image = image.getScaledInstance(600,600 ,Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(image);
        Container c = new JLabel(imageIcon);
        frame.setContentPane(c);

        logininfo = loginInfoOriginal;

        userIDLabel.setBounds(50,100,100,25);
        userPasswordLabel.setBounds(50,150,100,25);

        messageLabel.setBounds(150,250,250,35);
        messageLabel.setFont(new Font(null,Font.ITALIC,25));

        userIDField.setBounds(150,100,200,25);
        userPasswordField.setBounds(150,150,200,25);

        signinButton.setBounds(150,200,100,25);
        signinButton.setFocusable(false);
        signinButton.addActionListener(this);

        resetButton.setBounds(250,200,100,25);
        resetButton.setFocusable(false);
        resetButton.addActionListener(this);

        backButton.setBounds(200,250,100,25);
        backButton.setFocusable(false);
        backButton.addActionListener(this);

        frame.add(userIDLabel);
        frame.add(userPasswordLabel);
        frame.add(messageLabel);
        frame.add(userIDField);
        frame.add(userPasswordField);
        frame.add(signinButton);
        frame.add(resetButton);
        frame.add(backButton);
        frame.setLocation(500,150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,600);
        frame.setLayout(null);
        frame.setVisible(true);
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
            LoginorSigninPage loginorsigninPage = new LoginorSigninPage(logininfo);
        }
        if(e.getSource()==resetButton) {
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
            userIDField.setText("");
            userPasswordField.setText("");
        }
        if(e.getSource()==signinButton) {
            String newID=userIDField.getText();
            String newPassword=String.valueOf(userPasswordField.getPassword());
            IDandPasswords.logininfo.put(newID, newPassword);
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
            LoginorSigninPage.clipBackground1.stop();
            frame.dispose();
            LoginorSigninPage loginorsigninPage=new LoginorSigninPage(logininfo);
        }
    }
}
