/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nyilvantarto;

import java.sql.Timestamp;

/**
 *
 * @author Ádám
 */
public class Naplo {

    private Timestamp mikor;
    private String felhasznalo;
    private String muvelet;

    public Naplo(String felhasznalo, String muvelet) {
        this.felhasznalo = felhasznalo;
        this.muvelet = muvelet;
    }

    public Naplo(Timestamp mikor, String felhasznalo, String muvelet) {
        this.mikor = mikor;
        this.felhasznalo = felhasznalo;
        this.muvelet = muvelet;
    }

    public void setMikor(Timestamp mikor) {
        this.mikor = mikor;
    }

    public void setFelhasznalo(String felhasznalo) {
        this.felhasznalo = felhasznalo;
    }

    public void setMuvelet(String muvelet) {
        this.muvelet = muvelet;
    }

    public Timestamp getMikor() {
        return mikor;
    }

    public String getFelhasznalo() {
        return felhasznalo;
    }

    public String getMuvelet() {
        return muvelet;
    }
}
