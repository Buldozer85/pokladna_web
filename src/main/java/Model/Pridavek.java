package Model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Interfaces.IPridavek;

public class Pridavek extends Produkty implements IPridavek {

    private int id;
    private String nazev;
    private Double cena;
    private boolean isActive;

    @Override
    public int getId() {
       
        return this.id;
    }

    @Override
    public Pridavek setId(int id) {
        this.id = id;
        return this;
    }

    public Double getCena() {
        return cena;
    }
    public Pridavek setCena(Double cena) {
        this.cena = cena;
        return this;
    }

    public String getNazev() {
        return nazev;
    }
    public Pridavek setNazev(String nazev) {
        this.nazev = nazev;
        return this;
    }
    public Pridavek setActive(boolean isActive) {
        this.isActive = isActive;
        return this;
    }
    public boolean isActive() {
        return isActive;
    }

    @Override
    public void load() {
        // TODO Auto-generated method stub
        
    }

    public static List<Pridavek> getAll(){
        List<Pridavek> pridavky = new ArrayList<>();
        try (Connection conn = Db.get().getConnection()) {
            Statement pridavkyStatement = conn.createStatement();
            ResultSet pridavkyRs = pridavkyStatement.executeQuery(
                    "SELECT pridavky.ID, pridavky.nazev, pridavky.cena, pridavky.isActive FROM pridavky WHERE pridavky.isActive = 1");
            while (pridavkyRs.next()) {
                Pridavek pridavek = new Pridavek().setId(pridavkyRs.getInt("ID"))
                        .setNazev(pridavkyRs.getString("nazev")).setCena(pridavkyRs.getDouble("cena"))
                        .setActive(pridavkyRs.getBoolean("isActive"));
                pridavky.add(pridavek);
            }
        } catch (Exception e) {
            e.printStackTrace();
            pridavky = null;
        }
        return pridavky;
    }
    
}
