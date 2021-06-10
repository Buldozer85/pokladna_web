package administrace.GUI;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Model.Polozka;

import java.awt.Container;

import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

public class polozkyVyberUprava extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Container obal = this.getContentPane();
    private JButton b, zpetButton;
    private polozkyUpravaFrame upravaFrame;
    private JPanel obalCely;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    public polozkyVyberUprava() {
        int height = screenSize.height * 2 / 3;
        int width = screenSize.width * 2 / 3;
        this.setSize(new Dimension(width, height));
        initComponents();
    }

    private void initComponents() {
        obalCely = new JPanel();
        zpetButton = new JButton("Zpět");
        BoxLayout boxl = new BoxLayout(obal, BoxLayout.Y_AXIS);

        GridLayout gl = new GridLayout();
        gl.setHgap(5);
        gl.setVgap(5);

        obalCely.setLayout(gl);
        obal.setLayout(boxl);
        obal.add(obalCely);
        obal.add(zpetButton);

        zpetButton.addActionListener((e) -> {
            this.setVisible(false);
            new AdministraceRozcesti().setVisible(true);
        });

        for (Polozka p : Polozka.getAllAdmin()) {

            b = new JButton(p.getNazev());

            b.addActionListener((l) -> {

                upravaFrame = new polozkyUpravaFrame(p);
                upravaFrame.setVisible(true);
                this.setVisible(false);
            });

            obalCely.add(b);
        }

    }
}
