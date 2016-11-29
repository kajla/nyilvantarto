/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nyilvantarto;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 *
 * @author Ádám
 */
public class MainController implements Initializable {

    private Nyilvantarto nyilvantarto;

    ArrayList<aru> aruLista = new ArrayList<>();

    ObservableList<String> olTermék = FXCollections.observableArrayList();

    @FXML
    private ComboBox cbTermék = new ComboBox();

    @FXML
    private TextField txtNev;

    @FXML
    private TextField txtMennyiseg;

    @FXML
    private TextField txtAr;

    @FXML
    private TextField txtMEgyseg;

    @FXML
    private Button btSzerkesztes;

    @FXML
    private Button btTorles;

    @FXML
    private Button btHozzaad;

    @FXML
    private Button btUj;

    // Bocsi :( @Ádám
//    @FXML
//    public void HozzaadMegnyom(ActionEvent UgyanittBojlerElado /* byGabor */) {
//        if (UgyanittBojlerElado.getSource() == btHozzaad) {
//            HozzaadAblak();
//
//        }
//
//    }
//    @FXML
//    private void HozzaadAblak() {
//        try {            
    //cbTermék.getItems().remove(cbTermék.getSelectionModel().selectedIndexProperty()); niet goed
//            Parent root2 = FXMLLoader.load(getClass().getResource("Hozzaadas.fxml"));
//            Stage stage = new Stage();
//            stage.setTitle("Áru hozzáadása");
//            stage.setScene(new Scene(root2));
//            stage.show();
//        } catch (IOException ex) {
//            Logger.getLogger(HozzaadasController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    @FXML
    private void kilepes() {
        // talán legegyszerűbb rész... :D
        Platform.exit();
        // Átadjuk a módosított listát
        //nyilvantarto.setAruk(aruLista);
        //Plat
    }
    

    @FXML
    private void SelectedIndexChanged(ActionEvent e) {
        if (e.getSource() == cbTermék) {
            // Ha üres, akkor SEMMIT se válasszunk ki, különben NullPointException... ;)
            // Ilyenkor csak töröljük ki a mezők tartalmát (valószínűleg ekkor törlünk)
            if (cbTermék.getSelectionModel().isEmpty()) {
                txtNev.setEditable(false);
                txtMEgyseg.setEditable(false);
                txtAr.setEditable(false);
                txtMennyiseg.setEditable(false);
                txtNev.setText("");
                txtMEgyseg.setText("");
                txtAr.setText("");
                txtMennyiseg.setText("");
            } else {
                String akt = cbTermék.getSelectionModel().getSelectedItem().toString();
                System.out.println(akt);
                for (aru termék : aruLista) {
                    if (termék.getNev() == akt) {
                        txtNev.setText(termék.getNev());
                        txtMennyiseg.setText(termék.getDarab() + "");
                        txtMEgyseg.setText(termék.getMertekegyseg());
                        txtAr.setText(termék.getEar() + "");
                        btSzerkesztes.setDisable(false);
                        txtNev.setEditable(false);
                        txtAr.setEditable(false);
                        txtMennyiseg.setEditable(false);
                        txtMEgyseg.setEditable(false);
                    }
                }

            }
        }

    }

    @FXML
    private void gombEsemenyek(ActionEvent e) {
        if (e.getSource() == btSzerkesztes) {
            txtNev.setEditable(true);
            txtAr.setEditable(true);
            txtMennyiseg.setEditable(true);
            txtMEgyseg.setEditable(true);
            btSzerkesztes.setDisable(true);
            btHozzaad.setDisable(true);
        }
        if (e.getSource() == btTorles) {
            // Ha NEM üres ("Válasszon" is üres!!!)
            if (!cbTermék.getSelectionModel().isEmpty()) {
                String akt = cbTermék.getSelectionModel().getSelectedItem().toString(); //cbTermék.valueProperty().getValue().toString();
                int törlendő = 0;
                int i = 0;
                for (aru termék : aruLista) {
                    // Jobb megoldást nem találtam... mivel NINCS egyedi azonosító! :(
                    if (termék.getNev() == akt) {//FIXME: && termék.getMertekegyseg() == txtMEgyseg.getText() && termék.getEar() == Integer.parseInt(txtAr.getText()) && termék.getDarab() == Integer.parseInt(txtMennyiseg.getText())) {
                        // Ezt kell törölnünk majd...
                        törlendő = i;
                    }
                    // Debug
//                    System.out.println(termék.getNev() + " vs " + akt);
//                    System.out.println(termék.getMertekegyseg() + " vs " + txtMEgyseg.getText());
//                    System.out.println(termék.getEar() + " vs " + txtAr.getText());
//                    System.out.println(termék.getDarab() + " vs " + txtMennyiseg.getText());
                    i++;
                }
                // Töröljük a listából az elemet
                aruLista.remove(törlendő);
                // Kiválasztott elemet töröljük
                cbTermék.getSelectionModel().select("Válasszon");
                // Mindent kiírtunk...
                olTermék.clear();
                // Felülcsapjuk a globális listát
                nyilvantarto.setAruk(aruLista);
                // Újra feltöltjük, because erőforrás pazarlás most nem érdekel...
                for (aru termék : aruLista) {
                    olTermék.add(termék.getNev());
                }
            }
        }
        if (e.getSource() == btHozzaad) {
            //aruLista.add(e)
            String nev = "";
            String megyseg = "";
            int ar = 0;
            int darab = 0;
            Boolean hiba = false;
            try {
                ar = Integer.parseInt(txtAr.getText());
            } catch (NumberFormatException nan) {
                System.out.println("Ár nem szám!");
                nyilvantarto.getHiba().nemszamHiba("ár");
                hiba = true;

            }
            try {
                darab = Integer.parseInt(txtMennyiseg.getText());
            } catch (NumberFormatException nan) {
                System.out.println("Darab nem szám!");
                hiba = true;
            }
            if (txtNev.getText().isEmpty()) {
                System.out.println("Név üres!");
                hiba = true;
            } else {
                nev = txtNev.getText();
            }
            if (txtMEgyseg.getText().isEmpty()) {
                System.out.println("Mértékegység üres!");
                hiba = true;
            } else {
                megyseg = txtMEgyseg.getText();
            }
            // Ha bármi hiba van, NEM hajtjuk végre
            if (!hiba) {
                // Felvesszük az új elemet
                aruLista.add(new aru(nev, megyseg, ar, darab));
                // Egyből be is rendezzük ;)
                Collections.sort(aruLista);
                // Alapértelmezett, üres elem
                cbTermék.getSelectionModel().select("Válasszon");
                // Mindent kiírtunk, inkább nem kockáztatok, meg amúgy is lehetünk még pazarlók... TODO: optimalizálni!
                olTermék.clear();
                // Újra felvesszük.
                for (aru termék : aruLista) {
                    olTermék.add(termék.getNev());
                }
                // Jelenlegit kiválasztjuk
                cbTermék.getSelectionModel().select(nev);
                // Felülcsapjuk a globális listát
                nyilvantarto.setAruk(aruLista);
                btUj.setDisable(false);
                btHozzaad.setDisable(true);
                btTorles.setDisable(false);
            }
        }
        if (e.getSource() == btUj) {
            btUj.setDisable(true);
            btHozzaad.setDisable(false);
            btSzerkesztes.setDisable(true);
            btTorles.setDisable(true);
            txtNev.setEditable(true);
            txtAr.setEditable(true);
            txtMennyiseg.setEditable(true);
            txtMEgyseg.setEditable(true);
            txtAr.clear();
            txtMEgyseg.clear();
            txtMennyiseg.clear();
            txtNev.clear();
            cbTermék.getSelectionModel().clearSelection();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO code application logic here
        //ArrayList<aru> aruLista = new ArrayList<>();

//        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("alma.dat")))) {
//            while (true) {
//                aruLista.add((aru) ois.readObject());
//            }
//        } catch (EOFException e) {
//        } catch (FileNotFoundException e) {
//            System.out.println("Nem találom a fájlt! Hova raktad?!");
//        } catch (IOException e) {
//            System.out.println(e.toString());
//        } catch (ClassNotFoundException e) {
//            System.out.println(e.toString());
//        }
        // TODO: ezzel valamit kezdeni kellene
    }

    @FXML
    private void nevjegy() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Névjegy");
        alert.setHeaderText("Nyilvántartó alkalmazás");
        alert.setContentText("Ez egy nyilvántartó alkalmazás.\n\nKészítők:\nSzabó Gábor\nRadovits Ádám.");
        alert.showAndWait();
    }

    public void initManager(final Nyilvantarto nyilvantarto) {
        this.nyilvantarto = nyilvantarto;
        this.aruLista = nyilvantarto.getAruk();
        System.out.println(aruLista);
        Collections.sort(aruLista);
        for (aru termék : aruLista) {
            olTermék.add(termék.getNev());
        }
        //olTermék.setAll(aruLista.toString());
        cbTermék.setItems(olTermék);
        System.out.println(nyilvantarto.getAlma());

    }
}
