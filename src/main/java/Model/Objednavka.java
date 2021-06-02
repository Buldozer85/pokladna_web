package Model;

import java.util.LinkedList;

import Interfaces.IObjednavka;

public class Objednavka implements IObjednavka {

    private int id;
    private Double cena;
    private String casObjednavky;
    private static LinkedList<Polozka> objednavka = new LinkedList<>();
    private LinkedList<Polozka> polozky = new LinkedList<>();

    public int getId() {
        return id;
    }

    public Objednavka setId(int id) {
        this.id = id;
        return this;
    }

    public String getCasObjednavky() {
        return casObjednavky;
    }

    public Objednavka setCasObjednavky(String casObjednavky) {
        this.casObjednavky = casObjednavky;
        return this;
    }

    public Double getCena() {
        return cena;
    }

    public Objednavka setCena(Double cena) {
        this.cena = cena;
        return this;
    }

    public static LinkedList<Polozka> getObjednavka() {
        return objednavka;
    }

    public static void setObjednavka(LinkedList<Polozka> objednavka) {
        Objednavka.objednavka = objednavka;
    }

    public LinkedList<Polozka> getPolozky() {
        return polozky;
    }

    public Objednavka setPolozky(LinkedList<Polozka> polozky) {
        this.polozky = polozky;
        return this;
    }

    @Override
    public void load() {
        // TODO Auto-generated method stub

    }

}
