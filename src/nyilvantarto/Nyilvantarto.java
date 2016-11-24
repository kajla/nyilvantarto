/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nyilvantarto;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private Nyilvantarto nyilvantarto;
    private int alma = 0;
    private String felhasznalonev;
    private Fajlkezeles fajlkezeles;
    private Hibauzenetek hiba;
    private ArrayList<Felhasznalo> felhasznalok;
    
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
    
    public Nyilvantarto(Scene scene) {
        this.scene = scene;
    }
    
    public Nyilvantarto() {
    }
    
    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(new StackPane());
        this.nyilvantarto = new Nyilvantarto(scene);
        nyilvantarto.felhasznaloFeltolt();
        nyilvantarto.showLoginScreen(stage);

//        stage.setScene(scene);
//        stage.setTitle("Nyilvantartó - Belépés");
//        stage.setResizable(false);
//        // Ezzel megoldjuk az átméretezési hibát
//        stage.sizeToScene();
//        stage.show();
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
    
    public void felhasznaloFeltolt() {
        ArrayList<Felhasznalo> ideiglenes = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("felhasznalok.dat")))) {
            while (true) {
                Object o = ois.readObject();
                if (o instanceof Felhasznalo) {
                    Felhasznalo júzer = (Felhasznalo) o;
                    ideiglenes.add(júzer);
                }
            }
        } catch (EOFException e) {
            // Fájl vége
        } catch (FileNotFoundException e) {
            System.out.println("Nem találom a fájlt! Hova raktad?!");
            hiba.fajlHiba("felhasznalok.dat");
        } catch (IOException e) {
            System.out.println("Váratlan I/O hiba történt!");
        } catch (ClassNotFoundException e) {
            System.out.println("Az osztály nem található!");
        }
        if (ideiglenes.isEmpty()) {
            System.out.println("A lista üres!");
        } else {
            this.felhasznalok = ideiglenes;
        }
    }
    
    public void run(String[] args) {
        this.launch(args);
    }
    
    public static void main(String[] args) {
        new Nyilvantarto().run(args);
    }
}
