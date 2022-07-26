package Main;

import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class PokerImage extends JLabel implements MouseListener{	
	
	
	public ImageIcon imageIcon;
    public Image image;
    public boolean isEntered = false;
    public boolean isClicked = false;
    public boolean canClicked = true;
    public boolean isChosen = false;
    public boolean canEntered = true;
    public static int chosenCard = 0;
    
    PokerImage(){
        this.addMouseListener(this);
        this.setVisible(true);
    }

	@Override
	public void mouseClicked(MouseEvent e) {
		 if(this.isClicked == false && canClicked){
            this.setLocation(this.getX(),this.getY()-10);
            this.isClicked = true;
            this.isChosen = true;
	        chosenCard++;
	     }else if(this.isClicked && canClicked ){
            this.setLocation(this.getX(),this.getY()+10);
            this.isClicked = false;
            this.isChosen = false;
            chosenCard--;
	     }
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		 if(this.isEntered == false && this.isClicked == false && canEntered){
	            this.setLocation(this.getX(),this.getY()-10);
	            this.isEntered = true;
	      }
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if(this.isEntered == true && this.isClicked == false && canEntered){
            this.setLocation(this.getX(),this.getY()+10);
            this.isEntered = false;
        }
	}

	public void setImageIcon(int width,int height){
        image = imageIcon.getImage();
        image = image.getScaledInstance(width,height ,Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(image);

        this.setIcon(imageIcon);
        this.setVisible(false);
        this.setBounds(300,400,width,height);
    }
}