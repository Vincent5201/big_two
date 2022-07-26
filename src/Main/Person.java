package Main;

import Tools.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Person extends Player {
	
	//show cards on hands
	void showPoker(GameScene gameScene) {
		int x = (unsortedcards.size()-1)*60, y = 600;
		for(int i=this.unsortedcards.size();i>0;i--){
			this.unsortedcards.get(i-1).pokerImage.setLocation(x, y);
			gameScene.add(this.unsortedcards.get(i-1).pokerImage);
			this.unsortedcards.get(i-1).pokerImage.setVisible(true);
			x-=60;
		}
	}

	boolean move(int[] va, ArrayList<Integer> choice) throws FileNotFoundException{
		
		int x=choice.size();
		if(!(x==1 || x==2 || x==5)){
			return false;
		}
		if(va[0]==0) {
			if(x==1) {
				return move1(va,choice);
			}else if(x==2) {
				return move2(va,choice);
			}else if(x==5) {
				return move5(va,choice);
			}
		}else if(va[0]==1 && x==1) {
			return move1(va,choice);
		}else if(va[0]==2 && x==2) {
			return move2(va,choice);
		}else if(va[0]==5 && x==5) {
			return move5(va,choice);
		}
		return false;
	}	

	boolean move1(int[] va, ArrayList<Integer> choice) throws FileNotFoundException {
		PrintWriter printWriter=new PrintWriter(new FileOutputStream(LoginPage.userID+".txt",true));
		
		//check whether choice is legal
		int a = choice.get(0);
		if(!Check.contain1(this.unsortedcards, a) || a<va[2]) {
			return false;
		}
		
		//change cards on table
		for(int i=0;i<GameScene.cardsNow.size();i++) {
			GameScene.cardsNow.get(i).pokerImage.setVisible(false);
		}
		GameScene.cardsNow.clear();
		GameScene.cardsNow.add(new Pokers(a).ClonePoker());
		
		//update data
		printWriter.println("you play "+Show.show(a));
		this.unsortedcards.get(findIndex(this.unsortedcards, a)).pokerImage.setVisible(false);
		this.unsortedcards.remove(findIndex(this.unsortedcards, a));
		
		va[0] = 1;
		va[1] = 1;
		va[2] = a;
		GameScene.pass=0;
		GameScene.sizeP-=1;
		
		return true;
	}
	boolean move2(int[] va, ArrayList<Integer> choice) throws FileNotFoundException {
		PrintWriter printWriter=new PrintWriter(new FileOutputStream(LoginPage.userID+".txt",true));
		
		//check whether choice is legal
		int[] cards = {(int)choice.get(0),(int)choice.get(1)};
		if(!Check.containmore(this.unsortedcards,cards) || !(cards[0]/4==cards[1]/4) || (cards[0]>cards[1]?cards[0]:cards[1])<va[2]) {
			return false;
		}
		
		//change cards on table
		for(int i=0;i<GameScene.cardsNow.size();i++) {
			GameScene.cardsNow.get(i).pokerImage.setVisible(false);
		}
		GameScene.cardsNow.clear();
		GameScene.cardsNow.add(new Pokers(cards[0]).ClonePoker());
		GameScene.cardsNow.add(new Pokers(cards[1]).ClonePoker());
		
		//update data
		va[0] = 2;
		va[1] = 2;
		va[2] = (cards[0]>cards[1]?cards[0]:cards[1]);
		printWriter.println("you play "+Show.show(cards[0])+" "+Show.show(cards[1]));
		for(int i=0;i<2;i++) {
			this.unsortedcards.get(findIndex(this.unsortedcards, cards[i])).pokerImage.setVisible(false);
			this.unsortedcards.remove(findIndex(this.unsortedcards, cards[i]));
		}
		GameScene.pass=0;
		GameScene.sizeP-=2;
		
		return true;
	}
	boolean move5(int[] va, ArrayList<Integer> choice) throws FileNotFoundException {
		PrintWriter printWriter=new PrintWriter(new FileOutputStream(LoginPage.userID+".txt",true));
		
		//check whether choice is legal
		int cards[] = {0,0,0,0,0};
		for(int i=0;i<5;i++) {
			cards[i] = choice.get(i);
		}
		int n = Check.typeCheck(cards);
		if(n<va[1] || !Check.containmore(this.unsortedcards,cards)) {
			return false;
		}else if(n==va[1]) {
			if(n==5 || n==6) {
				int tmp = 0,distinct = 0;
				for(int i=1;i<5;i++) {
					if(cards[0]==cards[i]) {
						tmp++;
					}else {
						distinct = i;
					}
				}
				if(tmp < 2) {
					int t = cards[distinct];
					cards[distinct] = cards[0];
					cards[0] = t;
				}
				if(cards[0]/4 < va[2]) {
					return false;
				}
			}else {
				if(cards[4] < va[2]) {
					return false;
				}
			}
		}
		
		//change cards on table
		for(int i=0;i<GameScene.cardsNow.size();i++) {
			GameScene.cardsNow.get(i).pokerImage.setVisible(false);
		}
		GameScene.cardsNow.clear();
		for(int i=0;i<5;i++) {
			GameScene.cardsNow.add(new Pokers(cards[i]).ClonePoker());
		}
		
		//update data
		va[0] = 5;
		va[1] = n;	
		switch (n) {
			case 3: 
			case 4: 
			case 7: {			
				va[2] = cards[0];				
				break;
			}
			case 5: 
			case 6: {	
				va[2] = cards[0]/4;
				break;
			}
		}
		for(int i=0;i<5;i++) {
			printWriter.print("you play "+Show.show(cards[i])+" ");
			this.unsortedcards.get(findIndex(this.unsortedcards, cards[i])).pokerImage.setVisible(false);
			this.unsortedcards.remove(findIndex(this.unsortedcards, cards[i]));
		}
		printWriter.println();
		GameScene.pass=0;
		GameScene.sizeP-=5;
		
		return true;
	}
}