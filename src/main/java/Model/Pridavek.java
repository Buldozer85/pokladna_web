package Model;

import Interfaces.IPridavek;

public class Pridavek extends Produkty implements IPridavek {

    
    private String nazev;
    private Double cena;
    private boolean isActive;

    @Override
    public int getId() {
       
        return super.getId();
    }

    @Override
    public Produkty setId(int id) {
        super.setId(id);
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
    
}
