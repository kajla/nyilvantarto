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
import nyilvantarto.Felhasznalo;
import nyilvantarto.aru;

/**
 *
 * @author Ádám
 */
public class Fajlkezeles {
    private String aruFile;
    private String userFile;
    
    public Fajlkezeles() {
        this.aruFile = "aruk.dat";
        this.userFile = "felhasznalok.dat";
    }

    public void aruMentes(ArrayList<aru> al) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(aruFile)))) {
            for (aru termek : al) {
                oos.writeObject(termek);
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
}
