package Main;

import Tools.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Player {

	public JLabel passLabel;  
	public ImageIcon imageIcon;
	public Image image;

	protected ArrayList<Pokers> unsortedcards = new ArrayList<Pokers>();
	protected ArrayList<ArrayList<Pokers>> sortedcards = new ArrayList<ArrayList<Pokers>>();
	protected ArrayList<ArrayList<Integer>> values = new ArrayList<ArrayList<Integer>>();

	Player(){
		this.passLabel = new JLabel();
		this.imageIcon=new ImageIcon("images/pass.jpg");
		this.setImageIcon();
		passLabel.setVisible(true);
	}
	//update cards on table
	public void showCardNow(GameScene gameScene) {
		int x = 400+(GameScene.cardsNow.size()-1)*60, y = 200;
		for(int i=GameScene.cardsNow.size();i>0;i--){
			GameScene.cardsNow.get(i-1).pokerImage.setLocation(x, y);
			gameScene.add(GameScene.cardsNow.get(i-1).pokerImage);
			GameScene.cardsNow.get(i-1).pokerImage.setVisible(true);
			x-=60;
		}
	}

	public void setPassLabel(Boolean isShown){
		this.passLabel.setVisible(isShown);
	}
	
	public void setImageIcon(){
		image = imageIcon.getImage();
		image = image.getScaledInstance(100,50 ,Image.SCALE_SMOOTH);
		imageIcon = new ImageIcon(image);

		passLabel.setIcon(imageIcon);
		passLabel.setVisible(false);
		passLabel.setBounds(300,400,200,100);
	}
	
	//put cards in unsortedcards into sortedcards and set values
	public void sort() {
		straightFlush();
		fourOfaKind();
		fullHouse();
		flush();
		straight();
		pair();

		while(this.unsortedcards.size()>0) {
			ArrayList<Integer> tmpv = new ArrayList<Integer>();
			tmpv.add(1);
			tmpv.add(1);
			tmpv.add(this.unsortedcards.get(this.unsortedcards.size()-1).getNum());
			this.values.add(tmpv);

			ArrayList<Pokers> tmpc = new ArrayList<Pokers>();
			tmpc.add(new Pokers(this.unsortedcards.get(this.unsortedcards.size()-1).getNum()));
			this.sortedcards.add(tmpc);
			this.unsortedcards.remove(this.unsortedcards.size()-1);

		}

	}

	private void pair() {
		for(int i=12;i>=0;i--) {
			int[] record=new int[4];
			int temp=0;
			for(int j=3;j>=0;j--) {
				if(Check.contain1(this.unsortedcards,4*i+j)) {
					record[temp]=4*i+j;
					temp++;
				}
			}
			if(temp>=2) {
				ArrayList<Integer> tmpv = new ArrayList<Integer>();
				tmpv.add(2);
				tmpv.add(2);
				tmpv.add(record[0]);
				this.values.add(tmpv);

				ArrayList<Pokers> tmpc = new ArrayList<Pokers>();
				tmpc.add(new Pokers(record[0]));
				tmpc.add(new Pokers(record[1]));
				this.sortedcards.add(tmpc);

				this.unsortedcards.remove(findIndex(this.unsortedcards,record[0]));
				this.unsortedcards.remove(findIndex(this.unsortedcards,record[1]));
			}
		}
	}

	private void straight() {
		int[] record= {4,4,4,4,4,4,4,4,4,4,4,4,4};
		for(int i=0;i<this.unsortedcards.size();i++) {
			record[this.unsortedcards.get(i).getNum()/4]=this.unsortedcards.get(i).getNum()%4;
		}
		int temp=0;
		for(int i=12;i>=4;i--) {
			if(record[i]!=4) {
				temp++;
			}else {
				temp=0;
			}
			if(temp==5) {
				ArrayList<Integer> tmpv = new ArrayList<Integer>();
				tmpv.add(5);
				tmpv.add(3);
				tmpv.add((i+4)*4+record[i+4]);
				this.values.add(tmpv);

				ArrayList<Pokers> tmpc = new ArrayList<Pokers>();
				for(int j=0;j<5;j++) {
					tmpc.add(new Pokers((i+j)*4+record[i+j]));
					this.unsortedcards.remove(findIndex(this.unsortedcards,(i+j)*4+record[i+j]));
				}
				this.sortedcards.add(tmpc);
				i-=5;
			}
		}
	}

	private void flush() {
		int[] record= {0,0,0,0};
		for(int i=0;i<this.unsortedcards.size();i++) {
			record[this.unsortedcards.get(i).getNum()%4]++;
		}
		for(int i=0;i<4;i++) {
			if(record[i]>=5) {
				ArrayList<Integer> tmpv = new ArrayList<Integer>();
				ArrayList<Pokers> tmpc = new ArrayList<Pokers>();
				tmpv.add(5);
				tmpv.add(4);
				int temp=0;
				for(int j=this.unsortedcards.size()-1;j>=0;j--) {
					if(temp<5 && this.unsortedcards.get(j).getNum()%4==i) {
						if(temp==0) {
							tmpv.add(this.unsortedcards.get(j).getNum());
						}
						tmpc.add(this.unsortedcards.get(j));
						temp++;
					}
				}
				this.values.add(tmpv);
				this.sortedcards.add(tmpc);
				for(int j=0;j<5;j++) {
					this.unsortedcards.remove(findIndex(this.unsortedcards, tmpc.get(j).getNum()));
				}
			}
		}
	}

	private void fullHouse() {
		for(int i=12;i>=0;i--) {
			int[] record=new int[3];
			int temp=0;
			for(int j=0;j<4;j++) {
				if(Check.contain1(this.unsortedcards, 4*i+j)) {
					record[temp]  = 4*i+j;
					temp++;
				}
			}
			if(temp==3) {
				for(int p=0;p<13;p++) {
					if(p!=i) {
						int[] record2=new int[3];
						int temp2=0;
						for(int q=0;q<4;q++) {
							if(Check.contain1(this.unsortedcards, 4*p+q)) {
								record2[temp2]=4*p+q;
								temp2++;
							}
						}
						if(temp2==2) {
							ArrayList<Integer> tmpv = new ArrayList<Integer>();
							tmpv.add(5);
							tmpv.add(5);
							tmpv.add(i);
							this.values.add(tmpv);

							ArrayList<Pokers> tmpc = new ArrayList<Pokers>();
							for(int s=0;s<3;s++) {
								tmpc.add(new Pokers(record[s]));
								this.unsortedcards.remove(findIndex(this.unsortedcards, record[s]));
							}
							for(int s=0;s<2;s++) {
								tmpc.add(new Pokers(record2[s]));
								this.unsortedcards.remove(findIndex(this.unsortedcards, record2[s]));
							}
							this.sortedcards.add(tmpc);

							break;
						}
					}
				}
			}
		}
	}

	private void fourOfaKind() {
		int record[] = new int[13];
		for(int i=0;i<this.unsortedcards.size();i++) {
			record[this.unsortedcards.get(i).getNum()/4]++;
		}
		for(int i=12;i>=0;i--) {
			if(record[i]==4) {

				ArrayList<Integer> tmpv = new ArrayList<Integer>();
				tmpv.add(5);
				tmpv.add(6);
				tmpv.add(i);
				this.values.add(tmpv);

				ArrayList<Pokers> tmpc = new ArrayList<Pokers>();
				for(int j=0;j<4;j++) {
					tmpc.add(new Pokers(4*i+j));
					this.unsortedcards.remove(findIndex(this.unsortedcards, 4*i+j));
				}

				int temp=this.unsortedcards.get(0).getNum();
				tmpc.add(new Pokers(temp));
				this.unsortedcards.remove(findIndex(this.unsortedcards, temp));
				this.sortedcards.add(tmpc);
			}
		}

	}

	private void straightFlush() {
		for(int i=8;i>=0;i--) {
			for(int j=3;j>=0;j--) {
				if(Check.contain1(this.unsortedcards,4*i+j)) {
					int card[] = {4*i+j, 4*i+j+4, 4*i+j+8, 4*i+j+12, 4*i+j+16};
					if(Check.containmore(this.unsortedcards, card)) {
						ArrayList<Integer> tmpv = new ArrayList<Integer>();
						tmpv.add(5);
						tmpv.add(7);
						tmpv.add(4*i+j+16);
						this.values.add(tmpv);

						ArrayList<Pokers> tmpc = new ArrayList<Pokers>();
						for(int k=0;k<5;k++) {
							tmpc.add(new Pokers(card[k]));
						}
						this.sortedcards.add(tmpc);

						for(int k=0;k<5;k++) {
							this.unsortedcards.remove(findIndex(this.unsortedcards, card[k]));
						}
					}
				}
			}
		}
	}
	
	int findIndex(ArrayList<Pokers> cards, int card) {
		int n =-1;
		for(int i=0;i<cards.size();i++) {
			if(cards.get(i).getNum()==card) {
				n = i;
			}
		}
		return n;
	}
}
