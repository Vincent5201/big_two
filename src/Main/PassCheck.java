package Main;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JCheckBox;

public class PassCheck extends JCheckBox{
	
	PassCheck(){
        this.setText("pass");
        this.setBackground(new Color(0xE6C619));
        this.setFont(new Font("Comis Sans",Font.BOLD,25));
        this.setFocusable(false);
        this.setEnabled(true);
        this.setBounds(840,550,120,40);
    }
}
