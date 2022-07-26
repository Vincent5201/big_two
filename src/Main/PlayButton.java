package Main;

import javax.swing.*;
import java.awt.*;

public class PlayButton extends JButton {

	PlayButton(){
        this.setText("play");
        this.setBackground(new Color(0xE6C619));
        this.setFont(new Font("Comis Sans",Font.BOLD,25));
        this.setFocusable(false);
        this.setEnabled(true);
        this.setBounds(840,610,120,40);
    }
    
}