/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nyilvantarto;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import nyilvantarto.modell.Fajlkezeles;
import nyilvantarto.modell.Hibauzenetek;

/**
 *
 * @author Ádám
 */
public class Nyilvantarto extends Application {

//    @Override
//    public void start(Stage stage) throws Exception {
//        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
//
//        Scene scene = new Scene(root);
//
//        stage.setScene(scene);
//        stage.setTitle("Nyilvantartó - Belépés");
//        stage.setResizable(false);
//        // Ezzel megoldjuk az átméretezési hibát
//        stage.sizeToScene();
//        stage.show();
//    }
//
//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String[] args) {
//        new Nyilvantarto().launch();
//    }
//     
    private Scene scene;
    private int alma;
    private String felhasznalonev;
    private final Fajlkezeles fajlkezeles;
    private final Hibauzenetek hiba;
    private ArrayList<Felhasznalo> felhasznalok;
    private ArrayList<aru> aruk;
    private String log;

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public void addLog(String log) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd - kk:mm:ss");
        sdf.format(new Date());
        if (this.log.isEmpty()) {
            this.log = sdf.format(new Date()) + ": " + log;
        } else {
            this.log = this.log + "\n" + sdf.format(new Date()) + ": " + log;
        }
    }

    public String getFelhasznalonev() {
        return felhasznalonev;
    }

    public void setFelhasznalonev(String felhasznalonev) {
        this.felhasznalonev = felhasznalonev;
    }

    public int getAlma() {
        return alma;
    }

    public void setAlma(int alma) {
        this.alma = alma;
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

    public Nyilvantarto() {
        this.scene = new Scene(new StackPane());
        this.fajlkezeles = new Fajlkezeles();
        this.hiba = new Hibauzenetek();
        //this.felhasznalok = fajlkezeles.felhasznaloOlvasas("felhasznalok.dat"); //--> TODO
        this.felhasznalok = fajlkezeles.felhasznaloOlvasas();
        this.aruk = fajlkezeles.aruOlvasas();
        this.log = fajlkezeles.logOlvasas();
        this.alma = 0; //--> XXX TESZTHEZ! Objektum jól viszi-e át a változókat
    }

    @Override
    public void start(Stage stage) throws IOException {
        // JavaFX hívja meg, itt csak annyi a dolgunk, hogy megjelenítsük a bejelentkező felületet;
        // nem ide való kódok innen kiszervezésre kerültek
        showLoginScreen(stage);
    }

    public void showLoginScreen(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            scene.setRoot((Parent) loader.load());
            LoginController controller = loader.<LoginController>getController();
            controller.initManager(this, felhasznalok);
            stage.setScene(scene);
            stage.setTitle("Nyilvántartó - Belépés");
            stage.setResizable(false);
            // Ezzel megoldjuk az átméretezési hibát
            stage.sizeToScene();
            stage.show();
        } catch (IOException ex) {
            // Valaha is ezek meghívásra kerülnének?! /csak mert ezek a jar fájlban lesznek/
            // FIXME: nem működik, hisz ha ez lefut, akkor már nagy gondok vannak...
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
        fajlkezeles.aruMentes(this);
        fajlkezeles.felhasznaloMentes(this);
        fajlkezeles.logMentes(this);
        System.exit(0);
    }

//    // TODO: Kiszervezendő?
//    private void felhasznaloFeltolt() {
//        ArrayList<Felhasznalo> ideiglenes = new ArrayList<>();
//        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("felhasznalok.dat")))) {
//            while (true) {
//                Object o = ois.readObject();
//                if (o instanceof Felhasznalo) {
//                    Felhasznalo júzer = (Felhasznalo) o;
//                    ideiglenes.add(júzer);
//                }
//            }
//        } catch (EOFException e) {
//            // Fájl vége
//        } catch (FileNotFoundException e) {
//            System.out.println("Nem találom a fájlt! Hova raktad?!");
//            hiba.fajlHiba("felhasznalok.dat");
//            System.exit(1); // Ha bármi hiba van, lépjünk ki, hisz úgy is hibás lenne a programunk működése
//        } catch (IOException e) {
//            System.out.println("Váratlan I/O hiba történt!");
//        } catch (ClassNotFoundException e) {
//            System.out.println("Az osztály nem található!");
//        }
//        if (ideiglenes.isEmpty()) {
//            System.out.println("A lista üres!");
//        } else {
//            this.felhasznalok = ideiglenes;
//        }
//    }
//    
//     // TODO: Kiszervezendő?
//    private void aruFeltolt() {
//        ArrayList<aru> ideiglenes = new ArrayList<>();
//        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("alma.dat")))) {
//            while (true) {
//                Object o = ois.readObject();
//                if (o instanceof aru) {
//                    aru termék = (aru) o;
//                    ideiglenes.add(termék);
//                }
//            }
//        } catch (EOFException e) {
//            // Fájl vége
//        } catch (FileNotFoundException e) {
//            System.out.println("Nem találom a fájlt! Hova raktad?!");
//            hiba.fajlHiba("felhasznalok.dat");
//            System.exit(1); // Ha bármi hiba van, lépjünk ki, hisz úgy is hibás lenne a programunk működése
//        } catch (IOException e) {
//            System.out.println("Váratlan I/O hiba történt!");
//        } catch (ClassNotFoundException e) {
//            System.out.println("Az osztály nem található!");
//        }
//        if (ideiglenes.isEmpty()) {
//            System.out.println("A lista üres!");
//        } else {
//            this.aruk = ideiglenes;
//        }
//    }
    public void run(String[] args) {
        System.out.println("START");
        this.launch(args);
        System.out.println("END");
    }

    public static void main(String[] args) {
        new Nyilvantarto().run(args);
    }
}
