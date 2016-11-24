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
import nyilvantarto.aru;

/**
 *
 * @author Ádám
 */
public class Fajlkezeles {

    public void aruMentes(String f, ArrayList<aru> al) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream( new File(f)))) {
            for (aru termek : al) {
                oos.writeObject(termek);
            }
        } catch (IOException e) {
            System.out.println("Váratlan I/O hiba történt!");
        }
    }

    public ArrayList aruOlvasas(String f) {
        ArrayList<Object> lista = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(f)))) {
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
        if (lista.isEmpty())
            System.out.println("A lista üres!");
        return lista;
    }
}
