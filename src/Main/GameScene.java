package Main;

import Tools.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

public class GameScene extends JFrame implements ActionListener{
	
	//game data
	public static ArrayList<Pokers> cardsNow = new ArrayList<Pokers>();
	public static int pass = 0,sizeP = 13;
	public static int sizePC[] = {13,13,13};
	
	private ArrayList<Integer> choice = new ArrayList<Integer>();
	private int[] valuenow = {0,0,0};
	private int count = 3;
	
	public static Pokers[] pokers = new Pokers[52];                //will not to be rearrange
	private Pokers[] allPoker = new Pokers[52];                    //will be rearrange and distribute
	
	
	//GUI
    PlayButton playButton = new PlayButton();         
    PassCheck passCheck = new PassCheck();
    
    RemainsCards[] remainsCards = new RemainsCards[3];
    JLabel whoGoesNow = new JLabel();
    JLabel[] computerNum =new JLabel[3];
    
    public static Clip clipBackground2;
   
    GameScene(){
    	
    	for(int i = 0 ; i < 3; i++){
            Main.computers[i] = new Computer(i+1);
        }
        Main.person = new Person();
        
    	//GUI setting
    		//background
        ImageIcon imageIcon = new ImageIcon("images/background3.png");
        Image image=imageIcon.getImage();
        image = image.getScaledInstance(1200,800 ,Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(image);
        Container c = new JLabel(imageIcon);
        this.setContentPane(c);
        
        	//computers' number
        for (int i = 0; i < 3; i++) {
            computerNum[i] = new JLabel("Computer " + (i + 1));
            computerNum[i].setForeground(Color.white);
            computerNum[i].setFont(new Font("Helvetica", Font.BOLD, 30));  
        }
        computerNum[0].setBounds(65, 30, 200, 50);
        computerNum[1].setBounds(370, 30, 200, 50);
        computerNum[2].setBounds(990, 30, 200, 50);
        for (int i = 0; i < 3; i++) {
            this.add(computerNum[i]);
            computerNum[i].setVisible(true);
        }
        
        	//button and checkbox
        playButton.addActionListener(this);
        this.add(playButton, 0);
        this.add(passCheck, 0);
        
        	//pass label
        Main.computers[0].passLabel.setBounds(160, 400, 200, 50);
        Main.computers[1].passLabel.setBounds(380, 90, 200, 50);
        Main.computers[2].passLabel.setBounds(930, 400, 200, 50);
        Main.person.passLabel.setBounds(1000,600,200,50);
        for(int i = 0; i < 3; i++) {
        	Main.computers[i].passLabel.setVisible(false);
        	this.add(Main.computers[i].passLabel);
        }
        Main.person.passLabel.setVisible(false);
        this.add(Main.person.passLabel);
        
        	//who's turn
        whoGoesNow.setFont(new Font("Dialog", Font.BOLD, 30));
    	whoGoesNow.setVisible(true);
        whoGoesNow.setForeground(Color.WHITE);
    	whoGoesNow.setPreferredSize(new Dimension(300,100));
        whoGoesNow.setBounds(300, 500, 300, 100);
    	this.add(whoGoesNow);

    		//number of remain cards
        for (int i = 0;i<3;i++){
            remainsCards[i] = new RemainsCards();
            remainsCards[i].setImageIcon(90,120);
        }
        remainsCards[0].setBounds(60,400,90,120);
        remainsCards[1].setBounds(700,30,90,120);
        remainsCards[2].setBounds(1050,400,90,120);
        this.add(this.remainsCards[0]);
        this.add(this.remainsCards[1]);
        this.add(this.remainsCards[2]);

        	//frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(100,60,1200,800);
        this.setResizable(false);
        this.setLayout(null);
        this.setVisible(true);
        
        //game data setting
        cardsNow.clear();
        pass = 0;
        sizePC[0] = 13;
        sizePC[1] = 13;
        sizePC[2] = 13;
        sizeP = 13;
        choice.clear();
        count = 3;
        valuenow[0] = 0;
        valuenow[1] = 0;
        valuenow[2] = 0;
        CreatePoker(); 
        shuffle();
        	//deal
        for(int i=0;i<13;i++) {
        	for(int j=0;j<3;j++) {
        		Main.computers[j].unsortedcards.add(allPoker[i*4+j]);
        	}
        	Main.person.unsortedcards.add(allPoker[i*4+3]);
        }
        	//sort
        for(int j=0;j<3;j++) {
    		Collections.sort(Main.computers[j].unsortedcards,new PokerSort());
    	}
    	Collections.sort(Main.person.unsortedcards,new PokerSort());
    	for(int i=0;i<3;i++) {
    		if(Check.contain1(Main.computers[i].unsortedcards, 0)) {
    			Main.computers[i].setFirst(1);
    		}
    		Main.computers[i].sort();
    	}
        Main.person.showPoker(this);
        
        	//check who's turn
		for(int i = 0; i < 3; i++) {
			if(Main.computers[i].getFirst()==1) {
				count = i;
			}
		}
		if(count!=3) {
			whoGoesNow.setText("Computer"+(count+1)+"'s turn.");
		}else {
			whoGoesNow.setText("Your turn.");
		}
		this.setVisible(true);
		
        //start game,wait for listener
        Main.total++;
    }	
    
    private void CreatePoker(){
        for(int i = 0; i<52 ; i++){
        	allPoker[i] = new Pokers(i,null);
        	allPoker[i].pokerImage = new PokerImage();
            pokers[i] = new Pokers(i,null);
            pokers[i].pokerImage = new PokerImage();
        }

        pokers[0].pokerImage.imageIcon =new ImageIcon("pokers/club/club3.png");
        pokers[1].pokerImage.imageIcon =new ImageIcon("pokers/diamond/diamond3.png");
        pokers[2].pokerImage.imageIcon =new ImageIcon("pokers/heart/heart3.png");
        pokers[3].pokerImage.imageIcon =new ImageIcon("pokers/spade/spade3.png");
        
        pokers[4].pokerImage.imageIcon =new ImageIcon("pokers/club/club4.png");
        pokers[5].pokerImage.imageIcon =new ImageIcon("pokers/diamond/diamond4.png");
        pokers[6].pokerImage.imageIcon =new ImageIcon("pokers/heart/heart4.png");
        pokers[7].pokerImage.imageIcon =new ImageIcon("pokers/spade/spade4.png");
        
        pokers[8].pokerImage.imageIcon =new ImageIcon("pokers/club/club5.png");
        pokers[9].pokerImage.imageIcon =new ImageIcon("pokers/diamond/diamond5.png");
        pokers[10].pokerImage.imageIcon =new ImageIcon("pokers/heart/heart5.png");
        pokers[11].pokerImage.imageIcon =new ImageIcon("pokers/spade/spade5.png");
        
        pokers[12].pokerImage.imageIcon =new ImageIcon("pokers/club/club6.png");
        pokers[13].pokerImage.imageIcon =new ImageIcon("pokers/diamond/diamond6.png");
        pokers[14].pokerImage.imageIcon =new ImageIcon("pokers/heart/heart6.png");
        pokers[15].pokerImage.imageIcon =new ImageIcon("pokers/spade/spade6.png");
        
        pokers[16].pokerImage.imageIcon =new ImageIcon("pokers/club/club7.png");
        pokers[17].pokerImage.imageIcon =new ImageIcon("pokers/diamond/diamond7.png");
        pokers[18].pokerImage.imageIcon =new ImageIcon("pokers/heart/heart7.png");
        pokers[19].pokerImage.imageIcon =new ImageIcon("pokers/spade/spade7.png");
        
        pokers[20].pokerImage.imageIcon =new ImageIcon("pokers/club/club8.png");
        pokers[21].pokerImage.imageIcon =new ImageIcon("pokers/diamond/diamond8.png");
        pokers[22].pokerImage.imageIcon =new ImageIcon("pokers/heart/heart8.png");
        pokers[23].pokerImage.imageIcon =new ImageIcon("pokers/spade/spade8.png");
        
        pokers[24].pokerImage.imageIcon =new ImageIcon("pokers/club/club9.png");
        pokers[25].pokerImage.imageIcon =new ImageIcon("pokers/diamond/diamond9.png");
        pokers[26].pokerImage.imageIcon =new ImageIcon("pokers/heart/heart9.png");
        pokers[27].pokerImage.imageIcon =new ImageIcon("pokers/spade/spade9.png");
        
        pokers[28].pokerImage.imageIcon =new ImageIcon("pokers/club/club10.png");
        pokers[29].pokerImage.imageIcon =new ImageIcon("pokers/diamond/diamond10.png");
        pokers[30].pokerImage.imageIcon =new ImageIcon("pokers/heart/heart10.png");
        pokers[31].pokerImage.imageIcon =new ImageIcon("pokers/spade/spade10.png");
        
        pokers[32].pokerImage.imageIcon =new ImageIcon("pokers/club/clubJ.png");
        pokers[33].pokerImage.imageIcon =new ImageIcon("pokers/diamond/diamondJ.png");
        pokers[34].pokerImage.imageIcon =new ImageIcon("pokers/heart/heartJ.png");
        pokers[35].pokerImage.imageIcon =new ImageIcon("pokers/spade/spadeJ.png");
        
        pokers[36].pokerImage.imageIcon =new ImageIcon("pokers/club/clubQ.png");
        pokers[37].pokerImage.imageIcon =new ImageIcon("pokers/diamond/diamondQ.png");
        pokers[38].pokerImage.imageIcon =new ImageIcon("pokers/heart/heartQ.png");
        pokers[39].pokerImage.imageIcon =new ImageIcon("pokers/spade/spadeQ.png");
        
        pokers[40].pokerImage.imageIcon =new ImageIcon("pokers/club/clubK.png");    
        pokers[41].pokerImage.imageIcon =new ImageIcon("pokers/diamond/diamondK.png");
        pokers[42].pokerImage.imageIcon =new ImageIcon("pokers/heart/heartK.png");
        pokers[43].pokerImage.imageIcon =new ImageIcon("pokers/spade/spadeK.png");
        
        pokers[44].pokerImage.imageIcon =new ImageIcon("pokers/club/clubA.png");
        pokers[45].pokerImage.imageIcon =new ImageIcon("pokers/diamond/diamondA.png");
        pokers[46].pokerImage.imageIcon =new ImageIcon("pokers/heart/heartA.png");
        pokers[47].pokerImage.imageIcon =new ImageIcon("pokers/spade/spadeA.png");
        
        pokers[48].pokerImage.imageIcon =new ImageIcon("pokers/club/club2.png");
        pokers[49].pokerImage.imageIcon =new ImageIcon("pokers/diamond/diamond2.png");
        pokers[50].pokerImage.imageIcon =new ImageIcon("pokers/heart/heart2.png");
        pokers[51].pokerImage.imageIcon =new ImageIcon("pokers/spade/spade2.png");

        for(int i = 0; i<52 ; i++){
            pokers[i].pokerImage.setImageIcon(90,120);
        }

        allPoker[0].pokerImage.imageIcon =new ImageIcon("pokers/club/club3.png");
        allPoker[1].pokerImage.imageIcon =new ImageIcon("pokers/diamond/diamond3.png");
        allPoker[2].pokerImage.imageIcon =new ImageIcon("pokers/heart/heart3.png");
        allPoker[3].pokerImage.imageIcon =new ImageIcon("pokers/spade/spade3.png");
        
        allPoker[4].pokerImage.imageIcon =new ImageIcon("pokers/club/club4.png");
        allPoker[5].pokerImage.imageIcon =new ImageIcon("pokers/diamond/diamond4.png");
        allPoker[6].pokerImage.imageIcon =new ImageIcon("pokers/heart/heart4.png");
        allPoker[7].pokerImage.imageIcon =new ImageIcon("pokers/spade/spade4.png");
        
        allPoker[8].pokerImage.imageIcon =new ImageIcon("pokers/club/club5.png");
        allPoker[9].pokerImage.imageIcon =new ImageIcon("pokers/diamond/diamond5.png");
        allPoker[10].pokerImage.imageIcon =new ImageIcon("pokers/heart/heart5.png");
        allPoker[11].pokerImage.imageIcon =new ImageIcon("pokers/spade/spade5.png");
        
        allPoker[12].pokerImage.imageIcon =new ImageIcon("pokers/club/club6.png");
        allPoker[13].pokerImage.imageIcon =new ImageIcon("pokers/diamond/diamond6.png");
        allPoker[14].pokerImage.imageIcon =new ImageIcon("pokers/heart/heart6.png");
        allPoker[15].pokerImage.imageIcon =new ImageIcon("pokers/spade/spade6.png");
        
        allPoker[16].pokerImage.imageIcon =new ImageIcon("pokers/club/club7.png");
        allPoker[17].pokerImage.imageIcon =new ImageIcon("pokers/diamond/diamond7.png");
        allPoker[18].pokerImage.imageIcon =new ImageIcon("pokers/heart/heart7.png");
        allPoker[19].pokerImage.imageIcon =new ImageIcon("pokers/spade/spade7.png");
        
        allPoker[20].pokerImage.imageIcon =new ImageIcon("pokers/club/club8.png");
        allPoker[21].pokerImage.imageIcon =new ImageIcon("pokers/diamond/diamond8.png");
        allPoker[22].pokerImage.imageIcon =new ImageIcon("pokers/heart/heart8.png");
        allPoker[23].pokerImage.imageIcon =new ImageIcon("pokers/spade/spade8.png");
        
        allPoker[24].pokerImage.imageIcon =new ImageIcon("pokers/club/club9.png");
        allPoker[25].pokerImage.imageIcon =new ImageIcon("pokers/diamond/diamond9.png");
        allPoker[26].pokerImage.imageIcon =new ImageIcon("pokers/heart/heart9.png");
        allPoker[27].pokerImage.imageIcon =new ImageIcon("pokers/spade/spade9.png");
        
        allPoker[28].pokerImage.imageIcon =new ImageIcon("pokers/club/club10.png");
        allPoker[29].pokerImage.imageIcon =new ImageIcon("pokers/diamond/diamond10.png");
        allPoker[30].pokerImage.imageIcon =new ImageIcon("pokers/heart/heart10.png");
        allPoker[31].pokerImage.imageIcon =new ImageIcon("pokers/spade/spade10.png");
        
        allPoker[32].pokerImage.imageIcon =new ImageIcon("pokers/club/clubJ.png");
        allPoker[33].pokerImage.imageIcon =new ImageIcon("pokers/diamond/diamondJ.png");
        allPoker[34].pokerImage.imageIcon =new ImageIcon("pokers/heart/heartJ.png");
        allPoker[35].pokerImage.imageIcon =new ImageIcon("pokers/spade/spadeJ.png");
        
        allPoker[36].pokerImage.imageIcon =new ImageIcon("pokers/club/clubQ.png");
        allPoker[37].pokerImage.imageIcon =new ImageIcon("pokers/diamond/diamondQ.png");
        allPoker[38].pokerImage.imageIcon =new ImageIcon("pokers/heart/heartQ.png");
        allPoker[39].pokerImage.imageIcon =new ImageIcon("pokers/spade/spadeQ.png");
        
        allPoker[40].pokerImage.imageIcon =new ImageIcon("pokers/club/clubK.png");    
        allPoker[41].pokerImage.imageIcon =new ImageIcon("pokers/diamond/diamondK.png");
        allPoker[42].pokerImage.imageIcon =new ImageIcon("pokers/heart/heartK.png");
        allPoker[43].pokerImage.imageIcon =new ImageIcon("pokers/spade/spadeK.png");
        
        allPoker[44].pokerImage.imageIcon =new ImageIcon("pokers/club/clubA.png");
        allPoker[45].pokerImage.imageIcon =new ImageIcon("pokers/diamond/diamondA.png");
        allPoker[46].pokerImage.imageIcon =new ImageIcon("pokers/heart/heartA.png");
        allPoker[47].pokerImage.imageIcon =new ImageIcon("pokers/spade/spadeA.png");
        
        allPoker[48].pokerImage.imageIcon =new ImageIcon("pokers/club/club2.png");
        allPoker[49].pokerImage.imageIcon =new ImageIcon("pokers/diamond/diamond2.png");
        allPoker[50].pokerImage.imageIcon =new ImageIcon("pokers/heart/heart2.png");
        allPoker[51].pokerImage.imageIcon =new ImageIcon("pokers/spade/spade2.png");

        for(int i = 0; i<52 ; i++){
            allPoker[i].pokerImage.setImageIcon(90,120);
        }
        
        try {
        	File file = new File("music/PokerWelcome.wav");
    		AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
    		Clip clip = AudioSystem.getClip();
    		clip.open(audioStream);
    		clip.start();
    		clipBackground2=clip;
        }
        catch(Exception e) {
        	e.printStackTrace();
        }
        
        try {
        	File file = new File("music/PokerBackground2.wav");
    		AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
    		Clip clip = AudioSystem.getClip();
    		clip.open(audioStream);
    		clip.start();
    		clipBackground2=clip;
    		clip.loop(clip.LOOP_CONTINUOUSLY);
        }
        catch(Exception e) {
        	e.printStackTrace();
        }
    }
    
    public void shuffle() {
    	ArrayList<Pokers> pokersAsList = new ArrayList<Pokers>();
    	for(int i=0;i<52;i++) {
    		pokersAsList.add(allPoker[i]);
    	}
    	Collections.shuffle(pokersAsList);
    	for(int i=0;i<52;i++) {
    		allPoker[i] = pokersAsList.get(i);
    	}
    	pokersAsList.clear();
    	
    }
    
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource()==playButton) {
			try {
	    		File file = new File("music/PokerDeal.wav");
	    		AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
	    		Clip clip = AudioSystem.getClip();
	    		clip.open(audioStream);
	    		clip.start();
	    	}
	    	catch(Exception e1) {
	    		e1.printStackTrace();
	    	}
			//computers' turn
			if(count!=3) {
				Main.computers[count].setPassLabel(false);
				try {
					Main.computers[count].move(valuenow);
				} catch (FileNotFoundException e2) {
					e2.printStackTrace();
				}
				Main.computers[count].showCardNow(this);
				
				if(pass==3) {
					valuenow[0] = 0;
					valuenow[1] = 0;
					valuenow[2] = 0;
					pass=0;
					for(int i=0;i<cardsNow.size();i++) {
						cardsNow.get(i).pokerImage.setVisible(false);
					}
				}
				remainsCards[count].RefreshRemainsCard(count);
				remainsCards[count].ShowNewCardsNum();
				
				this.setVisible(true);
				//check whether player wins
				if(Main.computers[count].sortedcards.size()==0) {
					try {
			    		File file = new File("music/PokerLose.wav");
			    		AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
			    		Clip clip = AudioSystem.getClip();
			    		clip.open(audioStream);
			    		clip.start();
			    	}
			    	catch(Exception e1) {
			    		e1.printStackTrace();
			    	}
					this.dispose();
					//you lose
					LoseScene loseScene = new LoseScene();
				}else {
					count++;
					if(count!=3) {
						whoGoesNow.setText("Computer"+(count+1)+" 's turn.");
						}
					else {
						whoGoesNow.setText("Your turn.");
					}
					this.setVisible(true);
				}
				
			}else {
				//person's turn
				Main.person.setPassLabel(false);
				//no pass
				if(!passCheck.isSelected()) {
					//record which is chosen
					choice.clear();
					for(int i=0;i<sizeP;i++) {
						if(Main.person.unsortedcards.get(i).pokerImage.isChosen) {
							choice.add(Main.person.unsortedcards.get(i).getNum());
						}
					}
					boolean OK = false;
					try {
						OK = Main.person.move(valuenow,choice);
					} catch (FileNotFoundException e2) {
						e2.printStackTrace();
					}
					
					if(OK==true) {
						
						Main.person.showCardNow(this);
						Main.person.showPoker(this);
						count = 0;
						this.setVisible(true);
					
						//check whether you win
						if(Main.person.unsortedcards.size()==0) {
							Main.win++;
							try {
					    		File file = new File("music/PokerVictory.wav");
					    		AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
					    		Clip clip = AudioSystem.getClip();
					    		clip.open(audioStream);
					    		clip.start();
					    	}
					    	catch(Exception e1) {
					    		e1.printStackTrace();
					    	}
							this.dispose();
							WinScene winScene = new WinScene();
						}
						whoGoesNow.setText("Computer1 's turn.");
					}else {
						//illegal action
                        JOptionPane.showMessageDialog(null,"Please choose the suitable cards", "Illegal action", JOptionPane.WARNING_MESSAGE);
					}
				}else {
					//choose pass
                    whoGoesNow.setText("Computer1 's turn.");
					pass++;
					count = 0;
					Main.person.setPassLabel(true);
					
					if(pass==3) {
						valuenow[0] = 0;
						valuenow[1] = 0;
						valuenow[2] = 0;
						pass=0;
						for(int i=0;i<cardsNow.size();i++) {
							cardsNow.get(i).pokerImage.setVisible(false);
						}
					}	
					this.setVisible(true);
				}
			}
		}
	}
}