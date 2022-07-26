package Tools;

import java.util.ArrayList;

import Main.*;

//tools
public class Check {
	
	//check whether player has the cards to play
	public static boolean containmore(ArrayList<Pokers> cards, int card[]) {
		for(int i=0;i<card.length;i++) {
			if(!contain1(cards, card[i])) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean contain1(ArrayList<Pokers> cards, int a) {
		for(int i=0;i<cards.size();i++) {
			if(cards.get(i).getNum()==a) {
				return true;
			}
		}
		return false;
	}
	
	//check what kinds of combination they are
	public static int typeCheck(int cards[]) {
		if(straightFlushCheck5(cards)) {
			return 7;
		}
		if(fourOfaKindCheck5(cards)) {
			return 6;
		}
		if(fullHouseCheck5(cards)) {
			return 5;
		}
		if(flushCheck5(cards)) {
			return 4;
		}
		if(straightCheck5(cards)) {
			return 3;
		}
		return 0;
	}
	
	public static boolean pairCheck5(int cards[]) {
		if(cards[0]/4==cards[1]/4) {
			return true;
		}else {
			return false;
		}
	}
	
	public static boolean straightCheck5(int cards[]) {
		int[] tmp = new int[13];
		for(int i=0;i<5;i++) {
			tmp[cards[i]/4] = 1;
		}
		int n=0;
		while(tmp[n]!=1) {
			n++;
		}
		for(int i=0;i<5;i++) {
			if(tmp[n+i]!=1) {
				return false;
			}
		}
		return true;
	}

	public static boolean flushCheck5(int cards[]) {
		for(int i=0;i<4;i++) {
			if(cards[i]%4 != cards[i+1]%4) {
				return false;
			}
		}
		return true;
	}

	public static boolean fullHouseCheck5(int cards[]) {
		int[] record = new int[13];
		for(int i=0;i<5;i++) {
			record[cards[i]/4]++;
		}
		int three=0,two=0;
		for(int i : record) {
			if(i==3) {
				three++;
			}else if(i==2) {
				two++;
			}
		}
		if(three==1 && two==1) {
			return true;
		}else {
			return false;
		}
	}

	public static boolean fourOfaKindCheck5(int cards[]) {
		int[] record = new int[13];
		for(int i=0;i<5;i++) {
			record[cards[i]/4]++;
		}
		for(int i=0;i<12;i++) {
			if(record[i]==4) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean straightFlushCheck5(int cards[]) {
		if(flushCheck5(cards) && straightCheck5(cards)) {
			return true;
		}else {
			return false;
		}
	}
	
	//check what kinds of combinations you have
	//print in console if needed
	public static void check13(ArrayList<Integer> tmp) {
		straightFlushCheck(tmp);
		fourOfaKindCheck(tmp);
		fullHouseCheck(tmp);
		flushCheck(tmp);
		straightCheck(tmp);
		pairCheck(tmp);
	}
		
	public static void pairCheck(ArrayList<Integer> tmp) {	
		int[] record = new int[13];
		for(int i=0;i<tmp.size();i++) {
			record[tmp.get(i)/4]++;
		}
		System.out.print("pairs:");
		for(int i=0;i<13;i++) {
			if(record[i]>=2) {
				System.out.print(Show.shownum(i*4)+" ");
			}
		}
		System.out.println();
	}
	
	public static void straightCheck(ArrayList<Integer> tmp) {
		int[] record = new int[13];
		for(int i=0;i<tmp.size();i++) {
			record[tmp.get(i)/4]++;
		}
		System.out.print("straight:");
		int n=0;
		while(n<9) {
			while(n<8 && record[n]==0) {
				n++;
			}
			if(record[n]>=1 && record[n+1]>=1 && record[n+2]>=1 && record[n+3]>=1 && record[n+4]>=1) {
				System.out.print(Show.shownum((n+4)*4)+" ");
			}	
			n++;
		}
		System.out.println();
	}

	public static void flushCheck(ArrayList<Integer> tmp) {
		int[] record = new int[4];
		for(int i=0;i<tmp.size();i++) {
			record[tmp.get(i)%4]++;
		}
		System.out.print("flush:");
		for(int i=0;i<3;i++) {
			if(record[i] >= 5) {
				System.out.print(Show.showsuite(i)+record[i]+"±i ");
			}
		}
		System.out.println();
	}

	public static void fullHouseCheck(ArrayList<Integer> tmp) {
		int[] record = new int[13];
		for(int i=0;i<tmp.size();i++) {
			record[tmp.get(i)/4]++;
		}
		System.out.print("full house:");
		for(int i=0;i<13;i++) {
			if(record[i]>=3) {
				System.out.print(Show.shownum(i*4)+" ");
			}
		}
		System.out.println();
	}

	public static void fourOfaKindCheck(ArrayList<Integer> tmp) {
		int[] record = new int[13];
		for(int i=0;i<tmp.size();i++) {
			record[tmp.get(i)/4]++;
		}
		System.out.print("four of a kind:");
		for(int i=0;i<13;i++) {
			if(record[i]>=4) {
				System.out.print(Show.shownum(i*4)+" ");
			}
		}
		System.out.println();
	}
	
	public static void straightFlushCheck(ArrayList<Integer> tmp) {
		int[] record = new int[4];
		for(int i=0;i<tmp.size();i++) {
			record[tmp.get(i)%4]++;
		}
		System.out.print("straight flush:");
		for(int x=0;x<3;x++) {
			if(record[x]>=5) {
				int[] record2 = new int[13];
				for(int i=0;i<tmp.size();i++) {
					if(tmp.get(i)%4 == x) {
						record2[tmp.get(i)/4]++;
					}
				}
				int n=0;
				while(n<9) {
					while(n<8 && record2[n]==0) {
						n++;
					}
					if(record2[n]==1 && record2[n+1]==1 && record2[n+2]==1 && record2[n+3]==1 && record2[n+4]==1) {
						System.out.println(Show.show((n+4)*4+x)+" ");
					}
					n++;
				}
			}
		}
		System.out.println();
	}
}
