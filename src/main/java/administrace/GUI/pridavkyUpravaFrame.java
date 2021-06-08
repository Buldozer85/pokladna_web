package administrace.GUI;

import java.awt.Dimension;
import java.awt.FlowLayout;


import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Model.Pridavek;


public class pridavkyUpravaFrame extends JFrame {
    private Pridavek p1;
    private static final long serialVersionUID = 127860281665570197L;
    private JTextField nazevTextField, cenaTextField;
    private JButton potvrdVlozeniButton,zpetButton;

    private JCheckBox isActive;
    private JLabel isActiveLabel;
    private JPanel obalIsActive, obalCely;

    public pridavkyUpravaFrame(Pridavek p1) {
        this.p1 = p1;
        initComponents();

        this.setSize(new Dimension(300, 500));
    }

    public void initComponents() {
        nazevTextField = new JTextField();
        nazevTextField.setText(p1.getNazev());
        cenaTextField = new JTextField();
        cenaTextField.setText(p1.getCena().toString());
        potvrdVlozeniButton = new JButton("Vložit");
        isActive = new JCheckBox();
        isActiveLabel = new JLabel();
        obalIsActive = new JPanel();
        obalCely = new JPanel();
        isActive.setText("Aktivní: ");
        zpetButton = new JButton("Zpět");

        java.awt.Container pane = this.getContentPane();
        if (p1.isActive()) {
            isActive.setSelected(true);
        }

        potvrdVlozeniButton.addActionListener((e) -> {
            try {
               
                Pridavek p = new Pridavek().setId(p1.getId()).setNazev(nazevTextField.getText())
                        .setCena(Double.parseDouble(cenaTextField.getText()));
                if (isActive.isSelected())
                    p.setActive(true);
                else
                    p.setActive(false);

                if (!p.save()) {
                    JOptionPane.showMessageDialog(this, "Nepodařilo se Upravit přídavek");

                } else {
                    JOptionPane.showMessageDialog(this,
                            "Přídavek: " + nazevTextField.getText() + "byl úspěšně upraven");
                    this.setVisible(false);
                    new pridavkyVyberUprava().setVisible(true);
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        
        });

        potvrdVlozeniButton.setAlignmentX(CENTER_ALIGNMENT);
        potvrdVlozeniButton.setAlignmentY(CENTER_ALIGNMENT);

        zpetButton.setAlignmentX(CENTER_ALIGNMENT);
        zpetButton.setAlignmentY(CENTER_ALIGNMENT);
        zpetButton.addActionListener((l)->{
            this.setVisible(false);
            new pridavkyVyberUprava().setVisible(true);
        });

        BoxLayout b = new BoxLayout(obalCely, BoxLayout.PAGE_AXIS);
        FlowLayout fl = new FlowLayout();
        pane.setLayout(fl);
        obalCely.setLayout(b);
        pane.add(obalCely);
        obalCely.add(nazevTextField);

        obalCely.add(Box.createRigidArea(new Dimension(40, 40)));
        cenaTextField.setSize(new Dimension(200, 100));
        nazevTextField.setAlignmentX(CENTER_ALIGNMENT);
        nazevTextField.setAlignmentY(CENTER_ALIGNMENT);
        obalCely.add(cenaTextField);
        obalCely.add(Box.createRigidArea(new Dimension(40, 40)));

        obalIsActive.setLayout(fl);
        obalIsActive.add(isActiveLabel);
        obalIsActive.add(isActive);
        obalCely.add(obalIsActive);
        obalCely.add(Box.createRigidArea(new Dimension(40, 40)));
        obalCely.add(potvrdVlozeniButton);
        obalCely.add(Box.createRigidArea(new Dimension(20, 20)));
        obalCely.add(zpetButton);

    }

}
