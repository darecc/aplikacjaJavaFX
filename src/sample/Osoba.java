package sample;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Osoba {
    public SimpleStringProperty imie;
    public SimpleStringProperty nazwisko;
    public SimpleIntegerProperty wzrost;

    public Osoba() {
        this("","",0);
    }
    public Osoba(String fName, String lName, int wzrost) {
        this.imie = new SimpleStringProperty(fName);
        this.nazwisko = new SimpleStringProperty(lName);
        this.wzrost = new SimpleIntegerProperty(wzrost);
    }

    public String getImie() {
        return imie.get();
    }
    public void setImie(String fName) {
        imie.set(fName);
    }

    public String getNazwisko() {
        return nazwisko.get();
    }
    public void setNazwisko(String fName) {
        nazwisko.set(fName);
    }

    public int getWzrost() {
        return wzrost.get();
    }
    public void setWzrost(int w) {
        wzrost.set(w);
    }
    @Override
    public String toString() {
        return imie + " " + nazwisko + " (" + wzrost + ")";
    }
}
