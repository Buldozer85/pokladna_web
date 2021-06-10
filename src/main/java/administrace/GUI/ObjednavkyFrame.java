package administrace.GUI;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import Model.Objednavka;

import Model.Polozka;
import Model.Pridavek;

public class ObjednavkyFrame extends JFrame {
    /**
     *
     */
    private static final long serialVersionUID = -6020615196913765941L;
    private JTabbedPane tabbedPane;
    private JButton zpetNaUvodni;

    private Container container = this.getContentPane();

    public ObjednavkyFrame() {
        this.setSize(new Dimension(400, 800));
        initComponents();
    }

    private void initComponents() {
        tabbedPane = new JTabbedPane();

        int cislo = 1;
        for (Objednavka objednavka : Objednavka.getAll()) {
            try {

                JPanel obal = new JPanel();
                BoxLayout b = new BoxLayout(obal, BoxLayout.Y_AXIS);
                obal.setLayout(b);
                JTextArea obj = new JTextArea();
                obj.setEditable(false);

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = format.parse(objednavka.getCasObjednavky());
                DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
                String result = df.format(date);
                obj.setText("Objednávka z: " + result);
                obj.append("\n");
                obj.append("___________________________");
                obj.append("\n");

                for (Polozka polozka : objednavka.getPolozky()) {
                    obj.append(polozka.getNazev() + "\t" + polozka.getCena() + " Kč");
                    obj.append("\n");

                    for (Pridavek pridavek : polozka.getPridavky()) {
                        obj.append("\t" + "+" + pridavek.getNazev() + "\t" + pridavek.getCena() + " Kč");
                        obj.append("\n");
                    }
                }
                obj.append("\n");
                obj.append("___________________________");
                obj.append("\n");
                obj.append("Celková cena:\t" + objednavka.getCena() + " Kč");
                obal.add(obj);

                zpetNaUvodni = new JButton("Zpět");
                zpetNaUvodni.addActionListener((e) -> {
                    AdminUvodniFrame af = new AdminUvodniFrame();
                    af.setVisible(true);
                    this.setVisible(false);
                });
                JPanel obalBtn = new JPanel();
                FlowLayout fl = new FlowLayout();
                fl.setHgap(5);
                fl.setVgap(5);
                obalBtn.setLayout(fl);

                obalBtn.add(zpetNaUvodni);

                obal.add(obalBtn);

                tabbedPane.add("Č" + cislo, obal);
                container.add(tabbedPane);
                cislo++;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

    }

}
