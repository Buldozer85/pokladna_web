package Model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;



import Interfaces.IPolozka;

public class Polozka extends Produkty implements IPolozka {
    private int id;
    private String nazev;
    private Double cena;
    private String druh;
    private List<Pridavek> pridavky = new ArrayList<>();
    private boolean isActive;

    @Override
    public int getId() {

        return super.getId();
    }

    @Override
    public Polozka setId(int id) {

        this.id = id;
        return this;
    }

    public Double getCena() {
        return cena;
    }

    public Polozka setCena(Double cena) {
        this.cena = cena;
        return this;
    }

    public String getDruh() {
        return druh;
    }

    public Polozka setDruh(String druh) {
        this.druh = druh;
        return this;
    }

    public String getNazev() {
        return nazev;
    }

    public Polozka setNazev(String nazev) {
        this.nazev = nazev;
        return this;

    }

    public Polozka setActive(boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    public boolean isActive() {
        return isActive;
    }

    public Polozka setPridavky(List<Pridavek> pridavky) {
        this.pridavky = pridavky;
        return this;
    }

    public List<Pridavek> getPridavky() {
        return pridavky;
    }

    @Override
    public void load() {
        // TODO Auto-generated method stub

    }

    
    public static List<Polozka> getAll() {
        List<Polozka> polozky = new ArrayList<>();
        try (Connection conn = Db.get().getConnection();
                Statement polozkyStmt = conn.createStatement();
                ResultSet polozkyRs = polozkyStmt.executeQuery(
                        "SELECT polozky.ID, polozky.nazev, polozky.cena, polozky.druh,polozky.isActive FROM polozky WHERE polozky.isActive = 1")) {
            ;

            while (polozkyRs.next()) {
                Polozka polozka = new Polozka().setId(polozkyRs.getInt("ID")).setNazev(polozkyRs.getString("nazev"))
                        .setCena(polozkyRs.getDouble("cena")).setDruh(polozkyRs.getString("druh"))
                        .setActive(polozkyRs.getBoolean("isActive"));
                polozky.add(polozka);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return polozky;
    }

}
