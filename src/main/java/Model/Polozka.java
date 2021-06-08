package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    public Polozka(int id) {
        this.id = id;
        this.load();
    }

    public Polozka() {
        super();
    }

    public int getId() {

        return this.id;
    }

    public Polozka setId(int id) {

        this.id = id;
        return this;
    }

    public Double getCena() {
        return this.cena;
    }

    public Polozka setCena(Double cena) {
        this.cena = cena;
        return this;
    }

    public String getDruh() {
        return this.druh;
    }

    public Polozka setDruh(String druh) {
        this.druh = druh;
        return this;
    }

    public String getNazev() {
        return this.nazev;
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
        return this.isActive;
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
        if (this.id <= 0)
            throw new IllegalStateException("Není definované ID");

        try (Connection connection = Db.get().getConnection();
                PreparedStatement stmt = connection.prepareStatement(
                        "SELECT polozky.id, polozky.nazev, polozky.cena, polozky.druh, polozky.isActive  FROM polozky WHERE id = ?")) {
            stmt.setInt(1, this.id);
            try (ResultSet result = stmt.executeQuery()) {
                if (result.next()) {
                    this.setNazev(result.getString("nazev")).setCena(result.getDouble("cena"))
                            .setDruh(result.getString("druh")).setActive(result.getBoolean("isActive"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

    public static List<Polozka> getAll() {
        List<Polozka> polozky = new ArrayList<>();
        try (Connection conn = Db.get().getConnection();
                Statement polozkyStmt = conn.createStatement();
                ResultSet polozkyRs = polozkyStmt.executeQuery(
                        "SELECT polozky.ID, polozky.nazev, polozky.cena, polozky.druh,polozky.isActive FROM polozky WHERE polozky.isActive = 1")) {

            while (polozkyRs.next()) {
                System.out.println(polozkyRs.getString("nazev"));
                polozky.add(new Polozka().setId(polozkyRs.getInt("ID")).setNazev(polozkyRs.getString("nazev"))
                        .setCena(polozkyRs.getDouble("cena")).setDruh(polozkyRs.getString("druh"))
                        .setActive(polozkyRs.getBoolean("isActive")));
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            
            polozky = null;
        }
        return polozky;
    }
    public static List<Polozka> getAllAdmin() {
        List<Polozka> polozky = new ArrayList<>();
        try (Connection conn = Db.get().getConnection();
                Statement polozkyStmt = conn.createStatement();
                ResultSet polozkyRs = polozkyStmt.executeQuery(
                        "SELECT polozky.ID, polozky.nazev, polozky.cena, polozky.druh,polozky.isActive FROM polozky")) {

            while (polozkyRs.next()) {
                System.out.println(polozkyRs.getString("nazev"));
                polozky.add(new Polozka().setId(polozkyRs.getInt("ID")).setNazev(polozkyRs.getString("nazev"))
                        .setCena(polozkyRs.getDouble("cena")).setDruh(polozkyRs.getString("druh"))
                        .setActive(polozkyRs.getBoolean("isActive")));
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            
            polozky = null;
        }
        return polozky;
    }

    @Override
    public boolean save() {
    if(this.id <= 0){
        try (Connection conn = Db.get().getConnection()) {
            conn.setAutoCommit(false);

            try (java.sql.PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO polozky (nazev, cena, druh) VALUES (?,?,?)",
                    PreparedStatement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, this.getNazev());
                stmt.setDouble(2, this.getCena());
                stmt.setObject(3, this.getDruh());

                if (stmt.executeUpdate() != 1) {
                    throw new Exception("Nepodaril se zapis polozky");
                }

                ResultSet rs = stmt.getGeneratedKeys();

                if (rs.next()) {
                    this.setId(rs.getInt(1));
                }
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
                conn.rollback();
                throw e;
            }
            conn.commit();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return false;
        }
    }else{
        try (Connection conn = Db.get().getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmt = conn.prepareStatement(
                    "UPDATE polozky SET polozky.isActive = ?, polozky.nazev = ?, polozky.cena = ?, polozky.druh = ? WHERE polozky.ID = ?")) {
                int pom;
                if (this.isActive())
                    pom = 1;
                else
                    pom = 0;
                stmt.setInt(1, pom);
                stmt.setString(2, this.getNazev());
                stmt.setDouble(3, this.getCena());
                stmt.setString(4, this.getDruh());
                stmt.setInt(5, this.getId());
               

                
               
                if (stmt.executeUpdate() != 1) {
                    throw new Exception("Nepodařilo se upravit polozku");
                }

            } catch (SQLException e) {
                e.printStackTrace();
                conn.rollback();
                System.out.println(e.getMessage());
                throw e;
            }
            conn.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return false;
        }


    }

        
    }

}
