package Main;

import Tools.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Computer extends Player{
	
	private int number = 0,first = 0;

	Computer(int n){
		this.number = n;
	}
	//play cards
	void move(int[] va) throws FileNotFoundException{
		
		int n = choose(va);
		//record
		PrintWriter printWriter=new PrintWriter(new FileOutputStream(LoginPage.userID+".txt",true));

		if(n>=0) {
			va[0] = this.values.get(n).get(0);
			va[1] = this.values.get(n).get(1);
			va[2] = this.values.get(n).get(2);
			GameScene.sizePC[this.number-1]-=va[0];
			
			switch (va[0]) {
				case 1: {
					for(int i=0;i<GameScene.cardsNow.size();i++) {
						GameScene.cardsNow.get(i).pokerImage.setVisible(false);
					}
					GameScene.cardsNow.clear();
					GameScene.cardsNow.add(new Pokers(va[2]).ClonePoker());
					printWriter.println("computer"+this.number+" play "+Show.show(va[2]));
					break;
				}
				case 2: {
					for(int i=0;i<GameScene.cardsNow.size();i++) {
						GameScene.cardsNow.get(i).pokerImage.setVisible(false);
					}
					GameScene.cardsNow.clear();
					GameScene.cardsNow.add(new Pokers(this.sortedcards.get(n).get(0).getNum()).ClonePoker());
					GameScene.cardsNow.add(new Pokers(this.sortedcards.get(n).get(1).getNum()).ClonePoker());
					printWriter.println("computer"+this.number+" play "+Show.show(this.sortedcards.get(n).get(0).getNum())+" "+Show.show(this.sortedcards.get(n).get(1).getNum()));
					break;
				}
				case 5: {
					for(int i=0;i<GameScene.cardsNow.size();i++) {
						GameScene.cardsNow.get(i).pokerImage.setVisible(false);
					}
					GameScene.cardsNow.clear();
					printWriter.println("computer"+this.number+" play ");
					for(int i=0;i<5;i++) {
						GameScene.cardsNow.add(new Pokers(this.sortedcards.get(n).get(i).getNum()).ClonePoker());
						printWriter.print(Show.show(this.sortedcards.get(n).get(i).getNum())+" ");
					}
					printWriter.println("\n");
					break;
				}
			}
			this.values.remove(n);
			this.sortedcards.remove(n);
			GameScene.pass=0;
		}else {
			GameScene.pass++;
			printWriter.println("computer"+this.number+" pass");
			Main.computers[this.number-1].setPassLabel(true);
		}
		printWriter.flush();
		printWriter.close();
	}
	
	//choose which one to play
	int choose(int va[]) {
		int n = this.sortedcards.size()-1;
		int count[] = new int[3];
		
		//count every combinations' number
		while(n>0 && this.values.get(n).get(0)!=5) {
			count[this.values.get(n).get(0)-1]++;
			n--;
		}
		if(this.values.get(n).get(0)==1 || this.values.get(n).get(0)==2) {
			count[2] = 0;
			count[this.values.get(n).get(0)-1]++;
		}else {
			count[2] = n+1;
		}
		
		if(va[0]==0) {
			//some special situations
			if(this.sortedcards.size()==2) {
				if(this.values.get(1).get(0)==1 && this.values.get(1).get(2)>=50) {
					n=1;
				}
			}else if(almostEnd(this.number-1, 1) && (count[1]!=0 || count[2] !=0)) {
				if(count[1] > count[2]) {
					n = count[1]+count[2]-1;
				}else {
					n = count[2]-1;
				}
			}else if(almostEnd(this.number-1, 1) && count[1]==0 && count[2]==0){
				n = 0;
			}else {
				//no special situation, play the most combinations
				if(count[0] > count[1] && count[0] > count[2]) {
					n = count[0]+count[1]+count[2]-1;
				}else if(count[1] >= count[0] && count[1] > count[2]) {
					n = count[1]+count[2]-1;
				}else if(count[2] >= count[0] && count[2] >= count[1]) {
					n = count[2]-1;
				}
			}
		}else if(va[0]==5) {
			if(count[2]!=0) {
				n = count[2]-1;
				while(n>=0 && this.values.get(n).get(1) < va[1]) {
					n--;
				}
				if(n!=-1) {
					while(n>=0 && this.values.get(n).get(1) == va[1] && this.values.get(n).get(2) < va[2]) {
						n--;
					}
				}
			}else {
				n = -1;
			}
		}else if(va[0]==2) {
			if(count[1]!=0) {
				n = count[1]+count[2]-1;
				while(n>=0 && this.values.get(n).get(0)==2 && this.values.get(n).get(2) < va[2]) {
					n--;
				}
				if(n!=-1 && this.values.get(n).get(0)!=2) {
					n = -1;
				}
			}else {
				n = -1;
			}
		}else if(va[0]==1) {
			if(count[0]!=0) {
				n = count[0]+count[1]+count[2]-1;
				while(n>=0 && this.values.get(n).get(0)==1 && this.values.get(n).get(2) < va[2]) {
					n--;
				}
				if(n!=-1 && this.values.get(n).get(0)!=1) {
					n = -1;
				}
				//hide, not to play sometimes
				if(n!=-1) {
					int s = count[0]+count[1]+count[2]-1, b = count[1]+count[2];
					if((double)(n-b)/(s-n) < 0.8) {
						n = -1;
					}
				}
			}else {
				n = -1;
			}
		}
		//split a combination sometimes
		if(n==-1 && va[0]==1 && this.sortedcards.size()==1 && almostEnd(this.number-1,4)) {
			if(this.values.get(0).get(0)==2 && this.values.get(0).get(2) > va[2]) {
				ArrayList<Integer> tmpv1 = new ArrayList<Integer>();
				ArrayList<Integer> tmpv2 = new ArrayList<Integer>();
				ArrayList<Pokers> tmpc1 = new ArrayList<Pokers>();
				ArrayList<Pokers> tmpc2 = new ArrayList<Pokers>();

				tmpv1.add(1);
				tmpv1.add(1);
				tmpv1.add(this.sortedcards.get(0).get(0).getNum());
				tmpv2.add(1);
				tmpv2.add(1);
				tmpv2.add(this.sortedcards.get(0).get(1).getNum());

				tmpc1.add(this.sortedcards.get(0).get(0));
				tmpc2.add(this.sortedcards.get(0).get(1));

				this.sortedcards.add(tmpc1);
				this.sortedcards.add(tmpc2);
				this.sortedcards.remove(0);

				this.values.add(tmpv1);
				this.values.add(tmpv2);
				this.values.remove(0);

				if(this.values.get(1).get(2) > va[2]) {
					n = 1;
				}else {
					n = 0;
				}
			}else if(this.values.get(0).get(0)==5 ) {
				if(!(this.values.get(0).get(1)==6 || this.values.get(0).get(1)==5) && this.values.get(0).get(2)> va[2]) {
					int tmpc[] = new int[5];
					int tmpv[][] = new int[5][3];

					for(int i=0;i<5;i++) {
						tmpc[i] = this.sortedcards.get(0).get(i).getNum();
					}
					for(int i=0;i<4;i++) {
						for(int j=i+1;j<5;j++) {
							if(tmpc[i] < tmpc[j]) {
								int t = tmpc[i];
								tmpc[i] = tmpc[j];
								tmpc[j] = t;
							}
						}
					}
					for(int i=0;i<5;i++) {
						for(int j=0;j<2;j++) {
							tmpv[i][j] = 1;
						}
						tmpv[i][2] = tmpc[i];
					}

					for(int i=0;i<5;i++) {
						ArrayList<Integer> tmpvl = new ArrayList<Integer>();
						ArrayList<Pokers> tmpcl = new ArrayList<Pokers>();
						for(int j=0;j<3;j++) {
							tmpvl.add(tmpv[i][j]);
						}
						tmpcl.add(new Pokers(tmpc[i]));
						this.values.add(tmpvl);
						this.sortedcards.add(tmpcl);
					}
					this.sortedcards.remove(0);
					this.values.remove(0);
					n = 4;
					while(this.sortedcards.get(n).get(0).getNum() < va[2]) {
						n--;
					}
				}else if(this.values.get(0).get(1)==6 && this.values.get(0).get(2)*4 > va[2]) {
					int tmpc[] = new int[5];
					int tmpv[][] = new int[5][3];

					for(int i=0;i<5;i++) {
						tmpc[i] = this.sortedcards.get(0).get(i).getNum();
					}
					if(tmpc[0]<tmpc[4]) {
						int t = tmpc[4];
						for(int i=4;i>=1;i--) {
							tmpc[i] = tmpc[i-1];
						}
						tmpc[0] = t;
					}

					for(int i=0;i<5;i++) {
						for(int j=0;j<2;j++) {
							tmpv[i][j] = 1;
						}
						tmpv[i][2] = tmpc[i];
					}

					for(int i=0;i<5;i++) {
						ArrayList<Integer> tmpvl = new ArrayList<Integer>();
						ArrayList<Pokers> tmpcl = new ArrayList<Pokers>();
						for(int j=0;j<3;j++) {
							tmpvl.add(tmpv[i][j]);
						}
						tmpcl.add(new Pokers(tmpc[i]));
						this.values.add(tmpvl);
						this.sortedcards.add(tmpcl);
					}
					this.sortedcards.remove(0);
					this.values.remove(0);

					n = 4;
					while(this.sortedcards.get(n).get(0).getNum() < va[2]) {
						n--;
					}

				}else if(this.values.get(0).get(1)==5 && this.values.get(0).get(2)*4+3 > va[2]) {
					int tmpc[][] = new int[3][2];
					int tmpv[][] = new int[3][3];

					int t = 0;
					while(t < 5) {
						tmpc[t/2][t%2] = this.sortedcards.get(0).get((t+1)%5).getNum();
						t++;
					}

					if(tmpc[0][0] < tmpc[1][0]) {
						int t1,t2;
						t1 = tmpc[0][0];
						t2 = tmpc[0][1];
						tmpc[0][0] = tmpc[1][0];
						tmpc[0][1] = tmpc[1][1];
						tmpc[1][0] = t1;
						tmpc[1][1] = t2;
					}
					for(int p=0;p<2;p++) {
						for(int q=0;q<2;q++) {
							tmpv[p][q] = 2;
						}
						tmpv[p][2] = tmpc[p][0];
					}
					tmpv[2][0] = tmpv[2][1] = 1;
					tmpv[2][2] = tmpc[2][0];

					for(int i=0;i<2;i++) {
						ArrayList<Integer> tmpvl = new ArrayList<Integer>();
						ArrayList<Pokers> tmpcl = new ArrayList<Pokers>();
						for(int j=0;j<3;j++) {
							tmpvl.add(tmpv[i][j]);
						}
						for(int j=0;j<2;j++) {
							tmpcl.add(new Pokers(tmpc[i][j]));
						}
						this.sortedcards.add(tmpcl);
						this.values.add(tmpvl);
					}
					ArrayList<Integer> tmpvl = new ArrayList<Integer>();
					ArrayList<Pokers> tmpcl = new ArrayList<Pokers>();
					for(int j=0;j<3;j++) {
						tmpvl.add(tmpv[2][j]);
					}
					tmpcl.add(new Pokers(tmpc[2][0]));
					this.sortedcards.add(tmpcl);
					this.values.add(tmpvl);

					this.sortedcards.remove(0);
					this.values.remove(0);
					n = 2;
				}
			}
		}
		return n;
	}
	//check whether other players have less than n cards
	boolean almostEnd(int p,int n) {
		for(int i=0;i<3;i++) {
			if(i!=p && GameScene.sizePC[i]<=n) {
				return true;
			}
		}
		if(GameScene.sizeP<=n) {
			return true;
		}
		return false;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getFirst() {
		return first;
	}

	public void setFirst(int first) {
		this.first = first;
	}


}