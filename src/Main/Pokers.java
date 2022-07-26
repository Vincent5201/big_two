package Main;

public class Pokers {
	
	public PokerImage pokerImage;
	private int num;
	
	Pokers(int n) {
		this.num = n;
		this.pokerImage = GameScene.pokers[n].pokerImage;
	}
	
	Pokers(int n,PokerImage p) {
		this.num = n;
		this.pokerImage = p;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}
	
	public Pokers ClonePoker(){
        Pokers clonePoker = new Pokers(this.num);
        PokerImage clonePokerImage = new PokerImage();
        clonePoker.num= this.num;
        clonePokerImage.imageIcon = this.pokerImage.imageIcon;
        clonePokerImage.setImageIcon(60,90);
        clonePokerImage.canClicked=false;
        clonePokerImage.isClicked=false;
        clonePokerImage.isChosen=false;
        clonePokerImage.isEntered=false;
        clonePokerImage.canEntered=false;
        clonePoker.pokerImage=clonePokerImage;
        return clonePoker;
    }
}