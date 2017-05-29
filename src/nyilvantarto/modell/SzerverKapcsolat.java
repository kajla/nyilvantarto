/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nyilvantarto.modell;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import nyilvantarto.Felhasznalo;
import nyilvantarto.Naplo;
import nyilvantarto.Nyilvantarto;
import nyilvantarto.NyilvantartoService;
import nyilvantarto.aru;

/**
 *
 * @author Ádám
 */
public class SzerverKapcsolat {

    private NyilvantartoService service;

    public SzerverKapcsolat() {
        try {
            Registry reg = LocateRegistry.getRegistry("localhost", 1099);
            service = (NyilvantartoService) reg.lookup("NYILVANTARTO");
        } catch (AccessException ex) {
            Logger.getLogger(SzerverKapcsolat.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Platform.runLater(() -> {
                Hibauzenetek.szerverHiba();
                System.out.println("Kritikus hiba történt! A szerver nem elérhető!\nKilépés...");
                System.exit(1);
            });
            while (true) {
            }
        } catch (NotBoundException ex) {
            Logger.getLogger(SzerverKapcsolat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void aruOlvasas(Nyilvantarto nyilvantarto) {
        try {
            nyilvantarto.setAruk(service.aruLista());
            nyilvantarto.setMaxID(service.aruMaxID());
        } catch (RemoteException ex) {
            Hibauzenetek.szerverHiba();
        }
    }

    public boolean aruHozzaad(aru ujAru) {
        // Hozzáadja az újonnan felvitt árut az adatbázishoz.
        // Ha sikerülne felvinni, true értékkel tér vissza
        boolean allapot = false;
        try {
            allapot = service.aruHozzaad(ujAru);
        } catch (RemoteException ex) {
            System.out.println("Hiba: A szerver nem reagált!");
            Hibauzenetek.szerverHiba();
        }
        return allapot;
    }

    public int aruEllenoriz(aru aktAru) {
        // Ellenőrzi, hogy módosították-e az adott árut, mióta kiolvastuk azt
        // Visszatérési értékek:
        // 0: OK, stimmel; 1: nem stimmel; 2: nem kaptunk eredményt; -1: adatbázis hiba
        int x = -1;
        try {
            x = service.aruEllenoriz(aktAru);
        } catch (RemoteException ex) {
            System.out.println("Hiba: A szerver nem reagált!");
            Hibauzenetek.szerverHiba();
        }
        return x;
    }

    public boolean aruTorol(aru ujAru) {
        // Törli az árut az adatbázisban.
        // Ha sikerülne törölni, true értékkel tér vissza
        boolean allapot = false;
        try {
            allapot = service.aruTorol(ujAru);
        } catch (RemoteException ex) {
            System.out.println("Hiba: A szerver nem reagált!");
            Hibauzenetek.szerverHiba();
        }
        return allapot;
    }

    public boolean aruModosit(aru ujAru) {
        // Módosítja az árut az adatbázisban.
        // Ha sikerülne módosítani, true értékkel tér vissza
        boolean allapot = false;
        try {
            allapot = service.aruModosit(ujAru);
        } catch (RemoteException ex) {
            System.out.println("Hiba: A szerver nem reagált!");
            Hibauzenetek.szerverHiba();
        }
        return allapot;
    }

    public boolean aruImport(Nyilvantarto nyilvantarto) {
        // Importáltak, azaz dobjuk el jelenlegi táblát, hozzuk létre, majd töltsük fel
        // Ha sikerül, true a visszatérési érték
        boolean allapot = false;
        try {
            allapot = service.aruImport(nyilvantarto.getAruk());
        } catch (RemoteException ex) {
            System.out.println("Hiba: A szerver nem reagált!");
            Hibauzenetek.szerverHiba();
        }
        return allapot;
    }

    public boolean naploHozzaad(Naplo naplo) {
        // Hozzáadja az újonnan felvitt naplót az adatbázishoz.
        // Ha sikerülne felvinni, true értékkel tér vissza
        boolean allapot = false;
        try {
            allapot = service.naploHozzaad(naplo);
        } catch (RemoteException ex) {
            System.out.println("Hiba: A szerver nem reagált!");
            Hibauzenetek.szerverHiba();
        }
        return allapot;
    }

    public ArrayList<Naplo> naploOlvasas() {
        // Feltölti az árulistát az adatbázisból
        ArrayList<Naplo> naplok = new ArrayList<>();
        try {
            naplok = service.naploOlvasas();
        } catch (RemoteException ex) {
            System.out.println("Hiba: A szerver nem reagált!");
            Hibauzenetek.szerverHiba();
        }
        return naplok;
    }

    public void naploTisztitas() {
        // Kitisztítjuk a naplót, azaz töröljük és újra létrehozzuk
        try {
            service.naploTisztitas();
        } catch (RemoteException ex) {
            System.out.println("Hiba: A szerver nem reagált!");
            Hibauzenetek.szerverHiba();
        }
    }

    public void felhasznaloOlvasas(Nyilvantarto nyilvantarto) {
        // Feltölti a felhasználókat az adatbázisból
        try {
            nyilvantarto.setFelhasznalok(service.felhasznaloLista());
            nyilvantarto.setaktFelhasznalo(service.aktFelhasznalo());
        } catch (RemoteException ex) {
            System.out.println("Hiba: A szerver nem reagált!");
            Hibauzenetek.szerverHiba();
        }
        if (nyilvantarto.getFelhasznalok().isEmpty()) {
            Felhasznalo kezdetiFelh = new Felhasznalo("admin", "admin", "Admin Felhasználó", "+36 1 123 4567", 0);
            if (!felhasznaloHozzaad(kezdetiFelh)) {
                System.out.println("Admin felhasználó hozzáadva az adatbázishoz!");
            }
            nyilvantarto.felhasznaloHozzaadas(kezdetiFelh);
            nyilvantarto.setaktFelhasznalo(kezdetiFelh);
        }
    }

    public boolean felhasznaloHozzaad(Felhasznalo ujFelhasznalo) {
        // Hozzáadja az újonnan felvitt felhasználót az adatbázishoz.
        // Ha sikerülne felvinni, true értékkel tér vissza
        boolean allapot = false;
        try {
            allapot = service.felhasznaloHozzaad(ujFelhasznalo);
        } catch (RemoteException ex) {
            System.out.println("Hiba: A szerver nem reagált!");
            Hibauzenetek.szerverHiba();
        }
        return allapot;
    }

    public boolean felhasznaloTorol(Felhasznalo toroltFelhasznalo) {
        // Törli a felhasználót az adatbázisban.
        // Ha sikerülne törölni, true értékkel tér vissza
        boolean allapot = false;
        try {
            allapot = service.felhasznaloTorol(toroltFelhasznalo);
        } catch (RemoteException ex) {
            System.out.println("Hiba: A szerver nem reagált!");
            Hibauzenetek.szerverHiba();
        }
        return allapot;
    }

    public boolean felhasznaloModosit(Felhasznalo modositottFelhasznalo) {
        // Módosítja a felhasználót az adatbázisban.
        // Ha nem sikerülne módosítani, true értékkel tér vissza
        boolean allapot = false;
        try {
            allapot = service.felhasznaloModosit(modositottFelhasznalo);
        } catch (RemoteException ex) {
            System.out.println("Hiba: A szerver nem reagált!");
            Hibauzenetek.szerverHiba();
        }
        return allapot;
    }

    public int felhasznaloEllenoriz(Felhasznalo aktFelhasznalo) {
        // Ellenőrzi, hogy módosították-e az adott felhasználót, mióta kiolvastuk azt
        // Visszatérési értékek:
        // 0: OK, stimmel; 1: nem stimmel; 2: nem kaptunk eredményt; -1: adatbázis hiba
        try {
            return service.felhasznaloEllenoriz(aktFelhasznalo);
        } catch (RemoteException ex) {
            System.out.println("Hiba: A szerver nem reagált!");
            Hibauzenetek.szerverHiba();
            return -1;
        }
    }
}
