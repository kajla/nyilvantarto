/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nyilvantarto.modell;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import nyilvantarto.Felhasznalo;
import nyilvantarto.Nyilvantarto;
import nyilvantarto.aru;

/**
 *
 * @author Ádám
 */
public class Fajlkezeles {

    private final String aruFile;
    private final String userFile;
    private final String logFile;

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

    public void aruOlvasas(Nyilvantarto nyilvantarto) {
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

    public String logOlvasas() {
        String logTartalom = new String();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(logFile)))) {
            while (true) {
                logTartalom = (String) ois.readObject();
            }
        } catch (EOFException e) {
            // Fájl vége
        } catch (FileNotFoundException e) {
            // Ha nincs meg a fájl, akkor szimplán kezdjünk egy üres naplót. :)
            logTartalom = "";
        } catch (IOException e) {
            System.out.println("Váratlan I/O hiba történt!");
        } catch (ClassNotFoundException e) {
            System.out.println("Az osztály nem található!");
        }
        if (logTartalom.isEmpty()) {
            System.out.println("A napló még üres.");
        }
        return logTartalom;
    }

    public void aruExport(Nyilvantarto nyilvantarto) {
        FileChooser fajlValaszto = new FileChooser();
        fajlValaszto.setTitle("Árucikkek exportálása");
        // Fájlnév
        fajlValaszto.setInitialFileName("arucikkek");
        // Kiterjesztés
        fajlValaszto.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV fájl", "*.csv"));
        // Alapértelmezett könyvtár -> felhasználó home könyvtára
        fajlValaszto.setInitialDirectory(new File(System.getProperty("user.home")));
        File fajl = fajlValaszto.showSaveDialog((Stage) nyilvantarto.getScene().getWindow());
        if (fajl != null) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(fajl))) {
                bw.write("Név (szöveg);Darabszám (szám);Mértékegység (szöveg);Ár (szám)\n");
                for (aru termék : nyilvantarto.getAruk()) {
                    bw.write(termék.getNev() + ";" + termék.getDarab() + ";" + termék.getMertekegyseg() + ";" + termék.getEar() + "\n");
                }
            } catch (IOException ex) {
                System.out.println("Váratlan I/O hiba történt!");
                nyilvantarto.getHiba().fajlioHiba(fajl.getName());
            }
        }
        if (fajl.exists()) {
            nyilvantarto.getHiba().exportSiker(fajl.getName());
        }
    }

    public void aruImport(Nyilvantarto nyilvantarto) {
        ArrayList<aru> lista = new ArrayList<>();
        int importDarab = 0;

        FileChooser fajlValaszto = new FileChooser();
        fajlValaszto.setTitle("Árucikkek importálása");
        // Fájlnév
        fajlValaszto.setInitialFileName("arucikkek");
        // Kiterjesztés
        fajlValaszto.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV fájl", "*.csv"));
        // Alapértelmezett könyvtár -> felhasználó home könyvtára
        fajlValaszto.setInitialDirectory(new File(System.getProperty("user.home")));
        File fajl = fajlValaszto.showOpenDialog((Stage) nyilvantarto.getScene().getWindow());
        if (fajl != null) {
            try (BufferedReader br = new BufferedReader(new FileReader(fajl))) {
                // Első sor kihagyása
                br.readLine();
                String sor = "";
                while ((sor = br.readLine()) != null) {
                    String[] daraboltSor = sor.split(";");     // ;-nél darabolunk
                    String nev = daraboltSor[0]; // 0. elem név
                    int db = Integer.parseInt(daraboltSor[1]); // 1. elem darab (szám)
                    String mertekegyseg = daraboltSor[2];  // 2. elem mértékegység
                    int ear = Integer.parseInt(daraboltSor[3]); // 3. elem egység ár (szám)
                    lista.add(new aru(nyilvantarto.getMaxID(), nev, mertekegyseg, ear, db));
                    importDarab++;
                }
            } catch (NumberFormatException ex) {   // ha a beolvasott szám valóban nem szám
                System.out.println("Hiba! Az egyik beolvasott érték nem szám!");
                nyilvantarto.getHiba().importNemSzamHiba();
            } catch (FileNotFoundException ex) {
                System.out.println("Hiba! Nincs meg a fájl!");
                nyilvantarto.getHiba().fajlHiba(fajl.getName());
            } catch (IOException ex) {
                System.out.println("Váratlan I/O hiba történt!");
                nyilvantarto.getHiba().fajlioHiba(fajl.getName());
            }
        }
        if (lista.isEmpty()) {
            System.out.println("A lista üres!");
            nyilvantarto.getHiba().importUres();
        } else {
            // Rendezzük a felvett listát
            Collections.sort(lista);
            nyilvantarto.setAruk(lista);
            nyilvantarto.getHiba().importEredmeny(importDarab);
            // TODO: csak azt töltsük be, ami új, illetve meglévőnek frissítsük az értékét
//            for (aru termékImport : lista) {
//                for (aru termék : nyilvantarto.getAruk()) {
//                    if (termék.equals(termékImport)) {
//                        System.out.println("egyezőek");
//                    } else {
//                        System.out.println("nem egyezőek");
//                    }
//                }
//            }
        }
    }
}
