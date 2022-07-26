package Main;

public class Main {
	
	//basic data
	public static Computer[] computers = new Computer[3];               
	public static Person person;       
	public static int total = 0 ,win = 0;

	public static void main(String[] args) {

		IDandPasswords idandPasswords = new IDandPasswords();
		LoginorSigninPage loginPage = new LoginorSigninPage(idandPasswords.getLoginInfo());
		//StartFrame startFrame = new StartFrame();
	}
}
