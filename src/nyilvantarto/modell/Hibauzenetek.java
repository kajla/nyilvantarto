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

    public void fajlHiba(String s) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Nyilvántartó - Hiba");
        alert.setHeaderText("Nem található a fájl!");
        alert.setContentText("Sajnos nem találom a(z) " + s + " fájlt.\nAmennyiben a hiba továbbra is fennáll, kérjük, keresse fel a fejlesztőket!");
        alert.showAndWait();
    }

    public void iolHiba(String s) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Nyilvántartó - Hiba");
        alert.setHeaderText("Nem található a fájl!");
        alert.setContentText("Sajnos nem találom a(z) " + s + " fájlt.\nAmennyiben a hiba továbbra is fennáll, kérjük, keresse fel a fejlesztőket!");
        alert.showAndWait();
    }

}
