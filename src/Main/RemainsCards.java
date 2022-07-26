package Main;

import javax.swing.*;
import java.awt.*;

public class RemainsCards extends JLabel {

    int remainsCard = 13;

    public ImageIcon imageIcon = new ImageIcon("images/pokerback.jpg");
    public Image image;
    
    RemainsCards(){
        this.setText(Integer.toString(remainsCard));
        this.setIcon(imageIcon);
        this.setHorizontalTextPosition(JLabel.CENTER); 
        this.setVerticalTextPosition(JLabel.TOP); 
        this.setIconTextGap(-90); 
        this.setForeground(Color.WHITE); 
        this.setFont(new Font("MV Boil",Font.PLAIN,50)); 
        this.setOpaque(true); 
        this.setVerticalAlignment(JLabel.CENTER); 
        this.setHorizontalAlignment(JLabel.CENTER);
        this.setVisible(true);
    }

    public void RefreshRemainsCard(int n){
        this.remainsCard = GameScene.sizePC[n];
    }
    public void ShowNewCardsNum(){
        this.setText(Integer.toString(remainsCard));
    }

    public void setImageIcon(int width,int height){
        image = imageIcon.getImage();
        image = image.getScaledInstance(width,height ,Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(image);

        this.setIcon(imageIcon);
        this.setVisible(true);
        this.setPreferredSize(new Dimension(width,height));
    }
}
