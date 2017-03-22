/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nyilvantarto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;

/**
 *
 * @author kajla
 */
public class aru implements Serializable, Comparable<Object> {

    private String nev;
    private String mertekegyseg;
    private int ear;
    private int darab;
    private int id;
    private Timestamp modositva;

    public aru(int id, String nev, String mertekegyseg, int ear, int darab) {
        this.id = id;
        this.nev = nev;
        this.mertekegyseg = mertekegyseg;
        this.ear = ear;
        this.darab = darab;
        this.modositva = new Timestamp(Calendar.getInstance().getTime().getTime());
    }

    public aru(int id, String nev, String mertekegyseg, int ear, int darab, Timestamp modositva) {
        this.id = id;
        this.nev = nev;
        this.mertekegyseg = mertekegyseg;
        this.ear = ear;
        this.darab = darab;
        this.modositva = modositva;
    }

    public int getId() {
        return id;
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

    public Timestamp getModositva() {
        return modositva;
    }

    public void setModositva(Timestamp modositas) {
        this.modositva = modositas;
    }

    @Override
    public String toString() {
        return nev;

    }

    public int getOsszErtek() {
        return ear * darab;
    }

    @Override
    public int compareTo(Object o) {
        aru a = (aru) o;
        int res = String.CASE_INSENSITIVE_ORDER.compare(nev, a.getNev());
        if (res == 0) {
            res = nev.compareTo(a.getNev());
        }
        return res;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final aru other = (aru) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    public boolean modositasOsszehasonlit(Timestamp idopont) {
        return modositva.equals(idopont);
    }
}
