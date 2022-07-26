package Tools;

//tools
public class Show {
	
	public static String show(int n) {
		String cardname=new String();
		cardname = showsuite(n)+shownum(n);
		return cardname;
	}
	
	public static String showsuite(int n) {
		String cardname=new String();
		if(n%4 == 3) {
			cardname = "spade";
		}else if(n%4 == 2) {
			cardname = "heart";
		}else if(n%4 == 1) {
			cardname = "diamond";
		}else if(n%4 == 0) {
			cardname = "club";
		}
		return cardname;
	}
	
	public static String shownum(int n) {
		String cardname=new String();
		if(n/4==11) {
			cardname = "A";
		}else if(n/4==12) {
			cardname = "2";
		}else {
			cardname = Integer.toString(n/4+3);
		}
		return cardname;
	}
	
}