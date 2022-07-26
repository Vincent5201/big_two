package Main;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

public class ViewHistory implements ActionListener{

    JFrame frame = new JFrame("View History.");

    JButton exitPageButton = new JButton("Exit");

    ViewHistory(String userID){

        ImageIcon imageIcon = new ImageIcon("images/background1.jpg");
        Image image=imageIcon.getImage();
        image = image.getScaledInstance(600,600 ,Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(image);
        Container c = new JLabel(imageIcon);
        frame.setContentPane(c);

        String userIDininder=userID;

        exitPageButton.setBounds(125,300,100,25);
        exitPageButton.setFocusable(false);
        exitPageButton.addActionListener(this);
        
        try {                          //suck the file
            Scanner scanner=new Scanner(new FileInputStream(userID+".txt"));
            JTextArea jTextArea=new JTextArea();
            jTextArea.setEditable(false);
            while(scanner.hasNextLine()) {
                jTextArea.append(scanner.nextLine()+"\n");
            }

            JScrollPane jScrollPane=new JScrollPane(jTextArea);
            jScrollPane.setBounds(0,0,300,300);

            frame.add(jScrollPane);

        }
        catch(Exception e1) {

            try {                                  //born||output the file
                PrintWriter printWriter=new PrintWriter(new FileOutputStream(userID+".txt",true));
                printWriter.println(userIDininder);
                printWriter.flush();
                printWriter.close();
            }
            catch(FileNotFoundException e2) {
                e2.printStackTrace();
            }
            try {
            	Scanner scanner=new Scanner(new FileInputStream(userID+".txt"));
                JTextArea jTextArea=new JTextArea();
                jTextArea.setEditable(false);
                while(scanner.hasNextLine()) {
                    jTextArea.append(scanner.nextLine()+"\n");
                }

                JScrollPane jScrollPane=new JScrollPane(jTextArea);
                jScrollPane.setBounds(0,0,300,300);

                frame.add(jScrollPane);
            }
            catch(Exception e2){
            	e2.printStackTrace();
            }
            
        }
        frame.add(exitPageButton);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,600);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==exitPageButton) {
        	try {
        		File file = new File("music/PokerButton2.wav");
        		AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
        		Clip clip = AudioSystem.getClip();
        		clip.open(audioStream);
        		clip.start();
        		try {
        			Thread.sleep(300);
        			clip.close();
        		}
        		catch(Exception e2){
        			e2.printStackTrace();
        		}
        	}
        	catch(Exception e1) {
        		e1.printStackTrace();
        	}
        	frame.dispose();
        }
    }
}