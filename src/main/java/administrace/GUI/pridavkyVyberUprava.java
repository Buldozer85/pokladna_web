package administrace.GUI;

import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Model.Pridavek;


import java.awt.Dimension;
import java.awt.Toolkit;

import java.awt.FlowLayout;
import java.awt.GridLayout;

public class pridavkyVyberUprava extends JFrame {

    private static final long serialVersionUID = 1L;
    private Container obal = this.getContentPane();
    private JButton b, zpetButton;
    private pridavkyUpravaFrame upravaFrame;
    private JPanel obalCely;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    public pridavkyVyberUprava() {
        int height = screenSize.height * 2 / 3;
        int width = screenSize.width * 2 / 3;
        this.setSize(new Dimension(width, height));
        initComponents();
    }

    private void initComponents() {
        obalCely = new JPanel();
        zpetButton = new JButton("Zpět");
        FlowLayout fl = new FlowLayout();

        GridLayout gl = new GridLayout();
        gl.setHgap(5);
        gl.setVgap(5);

        obalCely.setLayout(gl);
        obal.setLayout(fl);
        obal.add(obalCely);
        obal.add(zpetButton);

        zpetButton.addActionListener((e)->{
            this.setVisible(false);
            new AdministraceRozcesti().setVisible(true);
        });

        
           
            for (Pridavek p : Pridavek.getAllAdministrace()) {

                b = new JButton(p.getNazev());

                b.addActionListener((l) -> {

                    upravaFrame = new pridavkyUpravaFrame(p);
                    upravaFrame.setVisible(true);
                    this.setVisible(false);
                });

                obalCely.add(b);
            }

       
    }
}
