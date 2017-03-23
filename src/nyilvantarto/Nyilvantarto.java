/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nyilvantarto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import nyilvantarto.modell.Adatbaziskezeles;
import nyilvantarto.modell.Fajlkezeles;
import nyilvantarto.modell.Hibauzenetek;

/**
 *
 * @author Ádám
 */
public class Nyilvantarto extends Application {

    private Scene scene;
    private Felhasznalo aktFelhasznalo;
    private final Fajlkezeles fajlkezeles;
    private final Adatbaziskezeles adatbaziskezeles;
    private final Hibauzenetek hiba;
    private ArrayList<Felhasznalo> felhasznalok;
    private ArrayList<aru> aruk;
//    private String log;
    private int maxID = 0;

    public int getMaxID() {
        return maxID++;
    }

    public int getCurrentID() {
        return maxID;
    }

    public void setMaxID(int maxID) {
        this.maxID = maxID;
    }

//    public String getLog() {
//        return log;
//    }
//
//    public void setLog(String log) {
//        this.log = log;
//    }
//    public void addLog(String log) {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd - kk:mm:ss");
//        sdf.format(new Date());
//        if (this.log.isEmpty()) {
//            this.log = sdf.format(new Date()) + ": " + log;
//        } else {
//            this.log = this.log + "\n" + sdf.format(new Date()) + ": " + log;
//        }
//    }
    public Felhasznalo getaktFelhasznalo() {
        return aktFelhasznalo;
    }

    public void setaktFelhasznalo(Felhasznalo aktFelhasznalo) {
        this.aktFelhasznalo = aktFelhasznalo;
    }

    public ArrayList<Felhasznalo> getFelhasznalok() {
        return felhasznalok;
    }

    public void setFelhasznalok(ArrayList<Felhasznalo> felhasznalok) {
        this.felhasznalok = felhasznalok;
    }

    public ArrayList<aru> getAruk() {
        return aruk;
    }

    public void setAruk(ArrayList<aru> aruk) {
        this.aruk = aruk;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public Hibauzenetek getHiba() {
        return hiba;
    }

    public Fajlkezeles getFajlkezeles() {
        return fajlkezeles;
    }

    public Adatbaziskezeles getAdatbaziskezeles() {
        return adatbaziskezeles;
    }

    public void aruImport() {
        // Ha sikerült az importálás (tehát többet importáltunk, mint 0), töltsük be az adatbázisba
        Integer importdb = fajlkezeles.aruImport(this);
        if (importdb > 0) {
            // Ha nem volt probléma az adatbázis import közben, írjuk ki, hogy sikeres
            if (adatbaziskezeles.aruImport(this)) {
//                addLog(getaktFelhasznalo().getFnev() + " importálta az árucikkeket");
                addNaplo(new Naplo(getaktFelhasznalo().getFnev(), "Árucikkek importálása, importált elemek száma: " + importdb));
                hiba.importEredmeny(importdb);
            } else {
                hiba.adatbazisHiba();
            }
        }
    }

    public void aruExport() {
        fajlkezeles.aruExport(this);
        addNaplo(new Naplo(getaktFelhasznalo().getFnev(), "Árucikkek exportálása"));
    }

    public boolean aruTorles(aru toroltAru) {
        // Törli az adott árut az adatbázisból, true értékkel tér vissza, ha sikerült
        boolean allapot = false;
        switch (adatbaziskezeles.aruEllenoriz(toroltAru)) {
            case 0:
                // Adatbázis törlés
                if (adatbaziskezeles.aruTorol(toroltAru)) {
                    aruk.remove(toroltAru);
//                    addLog(getaktFelhasznalo().getFnev() + " törölte: " + toroltAru.getNev());
                    addNaplo(new Naplo(getaktFelhasznalo().getFnev(), "Áru törlés: " + toroltAru.getNev()));
                    allapot = true;

                } else {
                    hiba.adatbazisHiba();
                }
                break;

            case 1:
                adatbaziskezeles.aruOlvasas(this);
                hiba.adatbazisKesobbModositva();
                break;

            case 2:
                adatbaziskezeles.aruOlvasas(this);
                hiba.adatbazisNemtalalhato();
                break;

            default:
                hiba.adatbazisHiba();
        }
        return allapot;
    }

    public boolean aruModositas(aru modositottAru, aru elozoAru) {
        // Módosítja az adott árut az adatbázisból, true értékkel tér vissza, ha sikerült
        boolean allapot = false;
        switch (adatbaziskezeles.aruEllenoriz(elozoAru)) {
            case 0:
                // Adatbázis módosítás
                if (adatbaziskezeles.aruModosit(modositottAru)) {
                    // Előző termék törlése
                    aruk.remove(elozoAru);
                    // Régi termék újrafelvétele
                    aruk.add(modositottAru);
                    // Egyből be is rendezzük ;) ... hátha változott a neve :)
                    Collections.sort(aruk);
//                    addLog(getaktFelhasznalo().getFnev() + " módosította: " + modositottAru.getNev());
                    addNaplo(new Naplo(getaktFelhasznalo().getFnev(), "Áru módosítás: " + modositottAru.getNev()));
                    allapot = true;
                } else {
                    hiba.adatbazisHiba();
                }
                break;

            case 1:
                adatbaziskezeles.aruOlvasas(this);
                hiba.adatbazisKesobbModositva();
                break;

            case 2:
                adatbaziskezeles.aruOlvasas(this);
                hiba.adatbazisNemtalalhato();
                break;

            default:
                hiba.adatbazisHiba();
        }
        return allapot;
    }

    public boolean aruHozzaad(aru ujAru) {
        boolean allapot;
        if (adatbaziskezeles.aruHozzaad(ujAru)) {
            // Felvesszük az új elemet
            aruk.add(ujAru);
            // Egyből be is rendezzük ;)
            Collections.sort(aruk);
//            addLog(getaktFelhasznalo().getFnev() + " hozzáadta: " + ujAru.getNev());
            addNaplo(new Naplo(getaktFelhasznalo().getFnev(), "Áru hozzáadás: " + ujAru.getNev()));
            allapot = true;
        } else {
            allapot = false;
        }
        return allapot;
    }

    public void aruFrissit() {
        adatbaziskezeles.aruOlvasas(this);
    }

    public boolean felhasznaloHozzaadas(Felhasznalo ujFelhasznalo) {
        boolean allapot;
        if (adatbaziskezeles.felhasznaloHozzaad(ujFelhasznalo)) {
            // Felvesszük az új felhasználót
            felhasznalok.add(ujFelhasznalo);
            // Egyből be is rendezzük ;)
            Collections.sort(felhasznalok);
//            addLog(getaktFelhasznalo().getFnev() + " hozzáadott egy új felhasználót: " + ujFelhasznalo.getFnev());
            addNaplo(new Naplo(getaktFelhasznalo().getFnev(), "Felhasználó hozzáadás: " + ujFelhasznalo.getFnev()));
            allapot = true;
        } else {
            allapot = false;
        }
        return allapot;
    }

    public boolean felhasznaloTorles(Felhasznalo toroltFelhasznalo) {
        // Törli az adott felhasználót az adatbázisból, true értékkel tér vissza, ha sikerült
        boolean allapot = false;
        switch (adatbaziskezeles.felhasznaloEllenoriz(toroltFelhasznalo)) {
            case 0:
                // Adatbázis törlés
                if (adatbaziskezeles.felhasznaloTorol(toroltFelhasznalo)) {
                    // Töröljük a felhasználót
                    felhasznalok.remove(toroltFelhasznalo);
//                    addLog(getaktFelhasznalo().getFnev() + " törölt egy felhasználót: " + toroltFelhasznalo.getFnev());
                    addNaplo(new Naplo(getaktFelhasznalo().getFnev(), "Felhasználó törlés: " + toroltFelhasznalo.getFnev()));
                    allapot = true;
                }
                break;

            case 1:
                adatbaziskezeles.felhasznaloOlvasas(this);
                hiba.adatbazisKesobbModositva();
                break;

            case 2:
                adatbaziskezeles.felhasznaloOlvasas(this);
                hiba.adatbazisNemtalalhato();
                break;

            default:
                hiba.adatbazisHiba();
        }
        return allapot;
    }

    public void addNaplo(Naplo ujNaplo) {
        adatbaziskezeles.naploHozzaad(ujNaplo);
    }

    public ArrayList<Naplo> getNaplo() {
        return adatbaziskezeles.naploOlvasas(this);
    }

    public void clearNaplo() {
        adatbaziskezeles.naploTisztitas();
        addNaplo(new Naplo(getaktFelhasznalo().getFnev(), "Törölte a naplót"));
    }

    public Nyilvantarto() {
        this.scene = new Scene(new StackPane());
        this.fajlkezeles = new Fajlkezeles();
        this.hiba = new Hibauzenetek();
        this.adatbaziskezeles = new Adatbaziskezeles();
        adatbaziskezeles.adatbazisInicializalas();
        adatbaziskezeles.aruOlvasas(this);
        adatbaziskezeles.felhasznaloOlvasas(this);
//        this.log = fajlkezeles.logOlvasas();
    }

    @Override
    public void start(Stage stage) throws IOException {
        showLoginScreen(stage);
    }

    public void showLoginScreen(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            scene.setRoot((Parent) loader.load());
            LoginController controller = loader.<LoginController>getController();
            controller.initManager(this);
            stage.setScene(scene);
            stage.setTitle("Nyilvántartó - Belépés");
            stage.setResizable(false);
            // Ezzel megoldjuk az átméretezési hibát
            stage.sizeToScene();
            stage.show();
            if (getFelhasznalok().size() == 1 && getFelhasznalok().get(0).getFnev().equals("admin")) {
                getHiba().elsoInditas();
            }
        } catch (IOException ex) {
            hiba.fajlHiba("Login.fxml");
        }
    }

    public void showMainScreen() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
            scene.setRoot((Parent) loader.load());
            MainController controller = loader.<MainController>getController();
            controller.initManager(this);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Nyilvántartó");
            stage.show();
        } catch (IOException ex) {
            hiba.fajlHiba("Main.fxml");
        }
    }

    // BETA
    public void showAdminScreen() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminPanel.fxml"));
            Scene alma = new Scene((Parent) loader.load());
            AdminPanelController controller = loader.<AdminPanelController>getController();

            Stage stage = new Stage();
            controller.initManager(this, stage);
            stage.setScene(alma);
            stage.setTitle("Nyilvántartó");
            stage.showAndWait();

        } catch (IOException ex) {
            hiba.fajlHiba("AdminPanel.fxml");
        }
    }

    // Platform.exit hívja meg, ekkor mentünk fájlba
    @Override
    public void stop() {
//        fajlkezeles.aruMentes(this);
//        fajlkezeles.felhasznaloMentes(this);
//        fajlkezeles.logMentes(this);
        System.exit(0);
    }

    public void run(String[] args) {
        System.out.println("START");
        this.launch(args);
        System.out.println("END");
    }

    public static void main(String[] args) {
        new Nyilvantarto().run(args);
    }
}
