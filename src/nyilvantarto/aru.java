/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nyilvantarto;

import java.io.Serializable;

/**
 *
 * @author kajla
 */
public class aru implements Serializable {

    private String nev;
    private String mertekegyseg;
    private int ear;
    private int darab;

    public aru(String nev, String mertekegyseg, int ear, int darab) {
        this.nev = nev;
        this.mertekegyseg = mertekegyseg;
        this.ear = ear;
        this.darab = darab;
    }

    public String getNev() {
        return nev;
    }

    public String getMertekegyseg() {
        return mertekegyseg;
    }

    public int getEar() {
        return ear;
    }

    public int getDarab() {
        return darab;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public void setMertekegyseg(String mertekegyseg) {
        this.mertekegyseg = mertekegyseg;
    }

    public void setEar(int ear) {
        this.ear = ear;
    }

    public void setDarab(int darab) {
        this.darab = darab;
    }

    @Override
    public String toString() {
        return "Áru{" + "név=" + nev
                + ", mértékegység=" + mertekegyseg
                + ", egységár=" + ear
                + ", darab=" + darab + '}';
    }

    public int getOsszErtek() {
        return ear * darab;
    }
}
