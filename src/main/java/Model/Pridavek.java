package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Interfaces.IPridavek;

public class Pridavek extends Produkty implements IPridavek {

    private int id;
    private String nazev;
    private Double cena;
    private boolean isActive;

    public Pridavek() {
        super();
    }

    public Pridavek(int id) {
        this.id = id;
        this.load();
    }

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
        if (this.id <= 0)
            throw new IllegalStateException("Není definované ID");

        try (Connection connection = Db.get().getConnection();
                PreparedStatement stmt = connection.prepareStatement(
                        "SELECT pridavky.ID, pridavky.nazev, pridavky.cena, pridavky.isActive  FROM pridavky WHERE ID = ?")) {
            stmt.setInt(1, this.id);
            try (ResultSet result = stmt.executeQuery()) {
                if (result.next()) {
                    this.setNazev(result.getString("nazev")).setCena(result.getDouble("cena"))
                            .setActive(result.getBoolean("isActive"));
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

    public static List<Pridavek> getAll() {
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

    public static List<Pridavek> getAllAdministrace() {
        List<Pridavek> pridavky = new ArrayList<>();
        try (Connection conn = Db.get().getConnection()) {
            Statement pridavkyStatement = conn.createStatement();
            ResultSet pridavkyRs = pridavkyStatement
                    .executeQuery("SELECT pridavky.ID, pridavky.nazev, pridavky.cena, pridavky.isActive FROM pridavky");
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

    @Override
    public boolean save() {
        if (this.id <= 0) {
            try (Connection conn = Db.get().getConnection()) {
                conn.setAutoCommit(false);

                try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO pridavky (nazev, cena) VALUES (?,?)",
                        PreparedStatement.RETURN_GENERATED_KEYS)) {
                    stmt.setString(1, this.getNazev());
                    stmt.setDouble(2, this.getCena());

                    if (stmt.executeUpdate() != 1) {
                        throw new Exception("Nepodaril se zapis pridavku");
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
        } else {
            try (Connection conn = Db.get().getConnection()) {
                conn.setAutoCommit(false);

                try (PreparedStatement stmt = conn.prepareStatement(
                        "UPDATE pridavky SET pridavky.isActive = ?, pridavky.nazev = ?, pridavky.cena = ? WHERE pridavky.ID = ?")) {
                    int pom;
                    if (this.isActive())
                        pom = 1;
                    else
                        pom = 0;
                    stmt.setInt(1, pom);
                    stmt.setString(2, this.getNazev());
                    stmt.setDouble(3, this.getCena());

                    stmt.setInt(4, this.getId());

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
