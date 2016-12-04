/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nyilvantarto.modell;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import nyilvantarto.Felhasznalo;
import nyilvantarto.Nyilvantarto;
import nyilvantarto.aru;

/**
 *
 * @author Ádám
 */
public class Fajlkezeles {

    private String aruFile;
    private String userFile;
    private String logFile;

    public Fajlkezeles() {
        this.aruFile = "aruk.dat";
        this.userFile = "felhasznalok.dat";
        this.logFile = "log.dat";
    }

    public void aruMentes(Nyilvantarto nyilvantarto) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(aruFile)))) {
            for (aru termek : nyilvantarto.getAruk()) {
                oos.writeObject(termek);
            }
        } catch (IOException e) {
            System.out.println("Váratlan I/O hiba történt!");
        }
    }

    public void felhasznaloMentes(Nyilvantarto nyilvantarto) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(userFile)))) {
            for (Felhasznalo júzer : nyilvantarto.getFelhasznalok()) {
                oos.writeObject(júzer);
            }
        } catch (IOException e) {
            System.out.println("Váratlan I/O hiba történt!");
        }
    }

    public ArrayList<aru> aruOlvasas() {
        ArrayList<aru> lista = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(aruFile)))) {
            while (true) {
                lista.add((aru) ois.readObject());
            }
        } catch (EOFException e) {
            // Fájl vége
        } catch (FileNotFoundException e) {
            System.out.println("Nem találom a fájlt! Hova raktad?!");
        } catch (IOException e) {
            System.out.println("Váratlan I/O hiba történt!");
        } catch (ClassNotFoundException e) {
            System.out.println("Az osztály nem található!");
        }
        if (lista.isEmpty()) {
            System.out.println("A lista üres!");
        }
        return lista;
    }

    public ArrayList<Felhasznalo> felhasznaloOlvasas() {
        ArrayList<Felhasznalo> lista = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(userFile)))) {
            while (true) {
                Object o = ois.readObject();
                if (o instanceof Felhasznalo) {
                    Felhasznalo júzer = (Felhasznalo) o;
                    lista.add(júzer);
                }
            }
        } catch (EOFException e) {
            // Fájl vége
        } catch (FileNotFoundException e) {
            System.out.println("Nem találom a fájlt! Hova raktad?!");
        } catch (IOException e) {
            System.out.println("Váratlan I/O hiba történt!");
        } catch (ClassNotFoundException e) {
            System.out.println("Az osztály nem található!");
        }
        if (lista.isEmpty()) {
            System.out.println("A lista üres!");
        }
        return lista;
    }

    public void logMentes(Nyilvantarto nyilvantarto) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(logFile)))) {
            oos.writeObject(nyilvantarto.getLog());
        } catch (IOException ex) {
            System.out.println("Váratlan I/O hiba történt!");
        }
    }

//    public String logOlvasas() {
//        String logTartalom = new String();
//        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(logFile)))) {
//            while (true) {
//                logTartalom = (String) ois.readObject();
//            }
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(Fajlkezeles.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(Fajlkezeles.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(Fajlkezeles.class.getName()).log(Level.SEVERE, null, ex);
//        } if(logTartalom.isEmpty())
//            System.out.println("baj van");
//        return logTartalom;
//    }
}
