/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nyilvantarto.modell;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import nyilvantarto.Nyilvantarto;
import nyilvantarto.aru;

/**
 *
 * @author Ádám
 */
public class Adatbaziskezeles implements AdatbazisKapcsolat {

    public void adatbazisInicializalas() {
        // Indításkor ellenőrizzük, hogy léteznek-e a táblák, ha nem, létrehozzuk őket
        // ... illetve, ha adatbázis nem elérhető, kilépünk hibával
        for (String tabla : TABLAK) {
            if (!tablaLetezik(tabla)) {
                System.out.println(tabla + " tábla még nem létezik, létrehozás ...");
                switch (tabla) {
                    case "ARUK":
                        arukLetrehozas();
                        break;

                    case "NAPLO":
                        naploLetrehozas();
                        break;

                    default:
                        System.out.println("Ismeretlen tábla: " + tabla);
                }
            }
        }
    }

    private static boolean tablaLetezik(String tabla) {
        // Ellenőrzi, hogy a paraméterként megkapott tábla létezik-e, illetve ha nem elérhető az adatbázis, kilépünk
        // Kívülről nem meghívható!
        int sorokSzama = 0;
        try (Connection kapcsolat = DriverManager.getConnection(URL, USER, PASSWORD)) {
            DatabaseMetaData dbmd = kapcsolat.getMetaData();
            // A getTables kisbetű-nagybetű érzékeny!
            ResultSet rs = dbmd.getTables(null, "NYILVANTARTO", tabla.toUpperCase(), null);
            while (rs.next()) {
                sorokSzama++;
            }
        } catch (SQLException e) {
            String hiba = e.getLocalizedMessage();
            System.out.println("Végzetes adatbázis hiba történt:\n" + hiba);
            System.exit(e.getErrorCode());
        }
        return sorokSzama > 0;
    }

    public void aruOlvasas(Nyilvantarto nyilvantarto) {
        // Feltölti az árulistát az adatbázisból
        ArrayList<aru> lista = new ArrayList<>();
        try (Connection kapcsolat = DriverManager.getConnection(URL, USER, PASSWORD)) {
            ResultSet eredmeny = kapcsolat.createStatement().executeQuery(SQLARUK);
            while (eredmeny.next()) {
                lista.add(new aru(eredmeny.getInt(1), eredmeny.getString(2), eredmeny.getString(3), eredmeny.getInt(4), eredmeny.getInt(5)));
            }
        } catch (SQLException ex) {
            // FIXME: ezt hogy kellene normálisan kezelni? Alkalmazás elinduljon vagy sem?
            System.out.println("Adatbázis hiba történt!");
            System.out.println(ex.getLocalizedMessage());
        }
        if (lista.isEmpty()) {
            System.out.println("A lista üres!");
            nyilvantarto.setMaxID(0);
        } else {
            int max = lista.get(0).getId();
            for (int i = 1; i < lista.size(); i++) {
                if (lista.get(i).getId() > max) {
                    max = lista.get(i).getId();
                }
            }
            max++;
            nyilvantarto.setMaxID(max);
        }
        nyilvantarto.setAruk(lista);
    }

    public boolean aruHozzaad(aru ujAru) {
        // Hozzáadja az újonnan felvitt árut az adatbázishoz.
        // Ha nem sikerülne felvinni, true értékkel tér vissza
        try (Connection kapcsolat = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement ps = kapcsolat.prepareStatement(SQLARUHOZZAAD);
            // aru(id, nev, mertekegyseg, ear, darab)
            ps.setInt(1, ujAru.getId());
            ps.setString(2, ujAru.getNev());
            ps.setString(3, ujAru.getMertekegyseg());
            ps.setInt(4, ujAru.getEar());
            ps.setInt(5, ujAru.getDarab());
            ps.execute();
        } catch (SQLException ex) {
            System.out.println(ex.getLocalizedMessage());
            return true;
        }
        return false;
    }

    public boolean aruTorol(aru ujAru) {
        // Törli az árut az adatbázishoz.
        // Ha nem sikerülne törölni, true értékkel tér vissza
        try (Connection kapcsolat = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement ps = kapcsolat.prepareStatement(SQLARUTOROL);
            ps.setInt(1, ujAru.getId());
            ps.execute();
        } catch (SQLException ex) {
            System.out.println(ex.getLocalizedMessage());
            return true;
        }
        return false;
    }

    public boolean aruModosit(aru ujAru) {
        // Módosítja az árut az adatbázishoz.
        // Ha nem sikerülne módosítani, true értékkel tér vissza
        try (Connection kapcsolat = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement ps = kapcsolat.prepareStatement(SQLARUMODOSIT);
            // SQL bemenő értékek: nev, mertekegyseg, ear, darab, id
            ps.setString(1, ujAru.getNev());
            ps.setString(2, ujAru.getMertekegyseg());
            ps.setInt(3, ujAru.getEar());
            ps.setInt(4, ujAru.getDarab());
            ps.setInt(5, ujAru.getId());
            ps.execute();
        } catch (SQLException ex) {
            System.out.println(ex.getLocalizedMessage());
            return true;
        }
        return false;
    }

    private boolean arukEldobas() {
        // ARUK tábla eldobása, kívülről nem meghívható!
        boolean allapot = false;
        try (Connection kapcsolat = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement ps = kapcsolat.prepareStatement(SQLARUKELDOBAS);
            ps.execute();
        } catch (SQLException ex) {
            System.out.println(ex.getLocalizedMessage());
            return true;
        }
        return allapot;
    }

    private boolean arukLetrehozas() {
        // Áruk tábla létrehozása, kívülről nem meghívható!
        boolean allapot = false;
        try (Connection kapcsolat = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement ps = kapcsolat.prepareStatement(SQLARUKLETREHOZ);
            ps.execute();
        } catch (SQLException ex) {
            System.out.println(ex.getLocalizedMessage());
            return true;
        }
        return allapot;
    }

    public boolean aruImport(Nyilvantarto nyilvantarto) {
        // Importáltak, azaz dobjuk el jelenlegi táblát, hozzuk létre, majd töltsük fel
        boolean allapot = false;
        if (arukEldobas()) {
            allapot = true;
        }
        if (arukLetrehozas()) {
            allapot = true;
        }
        for (aru akt : nyilvantarto.getAruk()) {
            if (aruHozzaad(akt)) {
                allapot = true;
            }
        }
        return allapot;
    }

    private boolean naploLetrehozas() {
        // Áruk tábla létrehozása, kívülről nem meghívható!
        boolean allapot = false;
        try (Connection kapcsolat = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement ps = kapcsolat.prepareStatement(SQLNAPLOLETREHOZ);
            ps.execute();
        } catch (SQLException ex) {
            System.out.println(ex.getLocalizedMessage());
            return true;
        }
        return allapot;
    }

    // TODO: NAPLÓ újragondolása
//    public boolean naploHozzaad(Naplo naplo) {
//        // Hozzáadja az újonnan felvitt árut az adatbázishoz.
//        // Ha nem sikerülne felvinni, true értékkel tér vissza
//        try (Connection kapcsolat = DriverManager.getConnection(URL, USER, PASSWORD)) {
//            PreparedStatement ps = kapcsolat.prepareStatement(SQLNAPLOHOZZAAD);
//            // naplo(felhasznalo, muvelet)
//            ps.setString(1, naplo.getFelhasznalo());
//            ps.setString(2, naplo.getMuvelet());
//            ps.execute();
//        } catch (SQLException ex) {
//            return true;
//        }
//        return false;
//    }
}
