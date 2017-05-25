/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nyilvantarto.modell;

import javafx.scene.control.Alert;

/**
 *
 * @author Ádám
 */
public class Hibauzenetek {

    public Hibauzenetek() {
    }

    public void nevjegy() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Névjegy");
        alert.setHeaderText("Nyilvántartó alkalmazás");
        alert.setContentText("Ez egy nyilvántartó alkalmazás.\n\nKészítők:\nSzabó Gábor\nRadovits Ádám");
        alert.showAndWait();
    }

    public void fajlHiba(String s) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Nyilvántartó");
        alert.setHeaderText("Nem található a fájl.");
        alert.setContentText("Sajnos nem találom a(z) " + s + " fájlt.\nKérjük, ellenőrizze le, hogy megtalálható-e a fájl!");
        alert.showAndWait();
    }

    public void fajlioHiba(String s) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Nyilvántartó");
        alert.setHeaderText("Váratlan I/O hiba történt.");
        alert.setContentText("Sajnos nem sikerült beolvasni a(z) " + s + " fájlt.\nAmennyiben a hiba továbbra is fennáll, kérjük, keresse fel a fejlesztőket!");
        alert.showAndWait();
    }

    public void nemszamHiba(String s) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Nyilvántartó");
        alert.setHeaderText("A bevitt érték hibás.");
        alert.setContentText("Sajnos a(z) " + s + " bevitt érték nem szám.\nKérem, javítsa ki, hogy a bevitt érték szám legyen!");
        alert.showAndWait();
    }

    public void UresMezoHiba(String s) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Nyilvántartó");
        alert.setHeaderText("Üres mező.");
        alert.setContentText("Sajnos a(z) " + s + " mező üres.\nKérem, töltse ki a mezőt.");
        alert.showAndWait();
    }

    public void onmagunkHiba() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Nyilvántartó");
        alert.setHeaderText("Önmagát akarta törölni.");
        alert.setContentText("Önmagát nem törölheti ki a rendszerből, mert különben nem fog tudni belépni a rendszerbe.");
        alert.showAndWait();
    }

    public void marfoglaltHiba(String s) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Nyilvántartó");
        alert.setHeaderText("Foglalt felhasználónév.");
        alert.setContentText("Sajnos a(z) " + s + " felhasználónév már foglalt.\nKérem, válasszon egy másik felhasználónevet!");
        alert.showAndWait();
    }

    public void exportSiker(String s) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Nyilvántartó");
        alert.setHeaderText("Az exportálás sikeres.");
        alert.setContentText("Sikeresen exportáltuk a árucikkeket!\nFájl neve: " + s);
        alert.showAndWait();
    }

    public void importNemSzamHiba() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Nyilvántartó");
        alert.setHeaderText("Az importálás sikertelen.");
        alert.setContentText("Sajnos nem sikerült beimportálni a CSV fájlt, mert egy vagy több szám mezőben nem szám található.\nKérjük, javítsa a hibás mező(ke)t és próbálja meg újra beimportálni a fájlt!");
        alert.showAndWait();
    }

    public void importUres() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Nyilvántartó");
        alert.setHeaderText("Az importálás sikertelen.");
        alert.setContentText("A megadott CSV fájl egyetlen árut sem tartalmaz!");
        alert.showAndWait();
    }

    public void importEredmeny(int db) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Nyilvántartó");
        alert.setHeaderText("Az importálás sikeres.");
        alert.setContentText("Sikeresen beimportáltuk a CSV fájlt!\nFelvett elemek száma: " + db);
        alert.showAndWait();
    }

    public void elsoInditas() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Nyilvántartó");
        alert.setHeaderText("Az alkalmazást feltehetően először indítják.");
        alert.setContentText("Csak admin felhasználó található, ami arra enged következtetni, hogy ez a program első indítása.\nAlapértelmezett belépési adatok: admin / admin");
        alert.showAndWait();
    }

    public void adatbazisHiba() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Nyilvántartó");
        alert.setHeaderText("Adatbázis hiba történt");
        alert.setContentText("A kérés végrehajtása közben váratlan adatbázis hiba történt.\nKérjük, ellenőrizze, hogy elérhető-e az adatbázis!");
        alert.showAndWait();
    }

    public void adatbazisKesobbModositva() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Nyilvántartó");
        alert.setHeaderText("Adatbázisban módosítás történt");
        alert.setContentText("Az adatbázisban időközben módosították az adott elemet.\nA kért művelet nem hajtódott végre.\nAz adatok újratöltve.");
        alert.showAndWait();
    }

    public void adatbazisNemtalalhato() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Nyilvántartó");
        alert.setHeaderText("A kiválasztott elem nem található");
        alert.setContentText("Az adatbázisban nem található a kiválasztott elem,\nfeltehetően az alkalmazás indítása óta törölték.\nA kért művelet nem hajtódott végre.\nAz adatok újratöltve.");
        alert.showAndWait();
    }
    
    public void szerverHiba() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Nyilvántartó");
        alert.setHeaderText("Szerver hiba");
        alert.setContentText("Szerver hiba történt. A változások nem léptek életbe.\nKérjük, ellenőrizze, hogy fut-e a szerver!");
        alert.showAndWait();
    }

}
