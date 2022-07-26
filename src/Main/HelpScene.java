package Main;

import javax.swing.*;
import java.io.FileInputStream;
import java.util.Scanner;

public class HelpScene extends JFrame {

    HelpScene(){
        try {                          
            Scanner scanner=new Scanner(new FileInputStream("rule.txt"),"UTF-8");
            JTextArea jTextArea=new JTextArea();
            jTextArea.setEditable(false);
            while(scanner.hasNextLine()) {
                jTextArea.append(scanner.nextLine()+"\n");
            }
            JScrollPane jScrollPane=new JScrollPane(jTextArea);
            jScrollPane.setBounds(0,0,600,600);
            this.add(jScrollPane);
        }
        catch(Exception e1) {
        	e1.printStackTrace();
        }

        this.setSize(600,600);
        this.setLayout(null);
        this.setVisible(true);
    }
}
