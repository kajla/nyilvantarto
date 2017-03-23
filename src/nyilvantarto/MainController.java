/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nyilvantarto;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 *
 * @author Ádám
 */
public class MainController implements Initializable {

    private Nyilvantarto nyilvantarto;

    @FXML
    private ComboBox cbTermék;

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

    @FXML
    private Button btKilepes;

    @FXML
    private Button btMegse;

    @FXML
    private Button btLogTorles;

    @FXML
    private Label lbUj;

    @FXML
    private TextField tfSzures;

    @FXML
    private TextField tfNaploSzures;

//    @FXML
//    private TextArea txLog;
    // Lista tab elemei
    @FXML
    private Tab tbLista; // = new Tab();

    @FXML
    private Tab tbLog;

    @FXML
    private TabPane tpTab; // = new TabPane();

    @FXML
    TableView tvLista = new TableView();

    @FXML
    TableColumn tcNev = new TableColumn();

    @FXML
    TableColumn tcDarab = new TableColumn();

    @FXML
    TableColumn tcMertekegyseg = new TableColumn();

    @FXML
    TableColumn tcAr = new TableColumn();

    // Napló tab elemei
    @FXML
    TableView tvNaplo = new TableView();

    @FXML
    TableColumn tcMikor = new TableColumn();

    @FXML
    TableColumn tcFelhasznalo = new TableColumn();

    @FXML
    TableColumn tcMuvelet = new TableColumn();

    @FXML
    ObservableList<aru> data = FXCollections.observableArrayList();

    @FXML
    ObservableList<Naplo> naploData = FXCollections.observableArrayList();

    @FXML
    private Menu mnAdmin;

    @FXML
    private void kilepes() {
        // talán legegyszerűbb rész... :D
        Platform.exit();
    }

    @FXML
    private void kijelentkezes() {
        Stage main = (Stage) nyilvantarto.getScene().getWindow();
        main.close();
        nyilvantarto.setaktFelhasznalo(null);
        nyilvantarto.showLoginScreen(main);
    }

    @FXML
    private void SelectedIndexChanged(ActionEvent e) {
        if (e.getSource() == cbTermék) {
            // Ha üres vagy alapértelmezett, akkor SEMMIT se válasszunk ki, különben NullPointException... ;)
            // Ilyenkor csak töröljük ki a mezők tartalmát (valószínűleg ekkor törlünk)
            if (cbTermék.getSelectionModel().isEmpty() || cbTermék.getSelectionModel().getSelectedItem().equals("Válasszon")) {
                txtTorles();
                btSzerkesztes.setDisable(true);
                btTorles.setDisable(true);
            } else {
                aru akt = (aru) cbTermék.getSelectionModel().getSelectedItem();
                System.out.println("DEBUG: " + akt.getId() + " - " + akt.getNev());
                for (aru termék : nyilvantarto.getAruk()) {
                    if (termék.equals(akt)) {
                        txtKitoltes(akt);
                        btSzerkesztes.setDisable(false);
                        txtSzerk(false);
                        btSzerkesztes.setDisable(false);
                        btTorles.setDisable(false);
                        // FIX: Ha "Új" hozzáadása közben váltottunk, akkor aktívak maradtak a gombok
                        btUj.setDisable(false);
                        btHozzaad.setDisable(true);
                        btMegse.setDisable(true);
                        lbUj.setVisible(false);
                        // FIX: Ha szerkesztettünk, de időközben módosítottak és más elemet választottunk ki, akkor Mentés maradt
                        btSzerkesztes.setText("Szerkesztés");
                    }
                }

            }
        }

    }

    @FXML
    private void gombEsemenyek(ActionEvent e) {
        if (e.getSource() == btSzerkesztes) {
            if (btUj.isDisabled()) {
                String nev = "";
                String megyseg = "";
                int ar = 0;
                int darab = 0;
                Boolean hiba = false;
                if (txtAr.getText().isEmpty()) {
                    nyilvantarto.getHiba().UresMezoHiba("ár");
                    hiba = true;
                } else {
                    try {
                        ar = Integer.parseInt(txtAr.getText());
                    } catch (NumberFormatException nan) {
                        System.out.println("Ár nem szám!");
                        nyilvantarto.getHiba().nemszamHiba("ár");
                        hiba = true;

                    }
                }
                if (txtMennyiseg.getText().isEmpty()) {
                    nyilvantarto.getHiba().UresMezoHiba("mennyiség");
                    hiba = true;
                } else {
                    try {
                        darab = Integer.parseInt(txtMennyiseg.getText());
                    } catch (NumberFormatException nan) {
                        System.out.println("Darab nem szám!");
                        nyilvantarto.getHiba().nemszamHiba("darab");
                        hiba = true;
                    }
                }
                if (txtNev.getText().isEmpty()) {
                    System.out.println("Név üres!");
                    nyilvantarto.getHiba().UresMezoHiba("név");
                    hiba = true;
                } else {
                    nev = txtNev.getText();
                }
                if (txtMEgyseg.getText().isEmpty()) {
                    System.out.println("Mértékegység üres!");
                    nyilvantarto.getHiba().UresMezoHiba("mértékegység");
                    hiba = true;
                } else {
                    megyseg = txtMEgyseg.getText();
                }
                // Ha bármi hiba van, NEM hajtjuk végre
                if (!hiba) {
                    // Előző áru eltárolása
                    aru elozoAru = (aru) cbTermék.getSelectionModel().getSelectedItem();
                    // Új áru készítése, azonos azonosítóval
                    aru modositottAru = new aru(elozoAru.getId(), nev, megyseg, ar, darab);
                    // Áru módosítása
                    if (nyilvantarto.aruModositas(modositottAru, elozoAru)) {
                        // OB lista frissítése
                        obListaFrissit();

                        // Előzőt kiválasztjuk
                        cbTermék.getSelectionModel().select(elozoAru);

                        txtSzerk(false);
                        //btSzerkesztes.setDisable(true);
                        //btHozzaad.setDisable(false);
                        btUj.setDisable(false);
                        btTorles.setDisable(false);
                        btSzerkesztes.setText("Szerkesztés");
                        btMegse.setVisible(false);
                    } else {
                        System.out.println(modositottAru.getModositva());
                        obListaFrissit();
                        for (aru termek : nyilvantarto.getAruk()) {
                            if (termek.getId() == elozoAru.getId()) {
                                txtKitoltes(termek);
                                cbTermék.getSelectionModel().select(termek);
                                txtSzerk(true);
                                btUj.setDisable(true);
                                btTorles.setDisable(true);
                                btMegse.setDisable(false);
                                btSzerkesztes.setText("Mentés");
                            }
                        }
                    }
                }
            } else {
                btSzerkesztes.setText("Mentés");
                txtSzerk(true);
                //btSzerkesztes.setDisable(true);
                btHozzaad.setDisable(true);
                btUj.setDisable(true);
                btTorles.setDisable(true);
                btMegse.setDisable(false);
                btMegse.setVisible(true);
            }
        }
        if (e.getSource() == btTorles) {
            // Ha NEM üres vagy ha NEM "Válasszon"
            if (!cbTermék.getSelectionModel().isEmpty() || !cbTermék.getSelectionModel().getSelectedItem().equals("Válasszon")) {
                aru akt = (aru) cbTermék.getSelectionModel().getSelectedItem(); //cbTermék.valueProperty().getValue().toString();
                Alert biztosan = new Alert(Alert.AlertType.CONFIRMATION);
                biztosan.setTitle("Nyilvántartó");
                biztosan.setHeaderText(akt + " törlésére készül.");
                biztosan.setContentText("Valóban törölni szeretné az árucikket?");
                // IGEN - NEM gombok hozzáadása, sajnos az igen lesz az alapértelmezett... ezt meg miért...
                biztosan.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

                // Alapértelmezett gombok felcserélése + magyarítás
                Button btIgen = (Button) biztosan.getDialogPane().lookupButton(ButtonType.YES);
                Button btNem = (Button) biztosan.getDialogPane().lookupButton(ButtonType.NO);
                btIgen.setDefaultButton(false);
                btNem.setDefaultButton(true);
                btIgen.setText("Igen");
                btNem.setText("Nem");

                Optional<ButtonType> eredmeny = biztosan.showAndWait();
                if (eredmeny.get() == ButtonType.YES) {
                    // Áru törlése
                    if (nyilvantarto.aruTorles(akt)) {
                        obListaFrissit();
                        cbTermék.getSelectionModel().select("Válasszon");
                    } else {
                        obListaFrissit();
                    }

                }
            }
        }
        if (e.getSource() == btHozzaad) {
            String nev = "";
            String megyseg = "";
            int ar = 0;
            int darab = 0;
            Boolean hiba = false;

            if (txtNev.getText().isEmpty()) {
                System.out.println("Név üres!");
                nyilvantarto.getHiba().UresMezoHiba("név");
                hiba = true;
            } else {
                nev = txtNev.getText();
            }

            if (txtAr.getText().isEmpty()) {
                nyilvantarto.getHiba().UresMezoHiba("ár");
                hiba = true;
            } else {
                try {
                    ar = Integer.parseInt(txtAr.getText());
                } catch (NumberFormatException nan) {
                    System.out.println("Ár nem szám!");
                    nyilvantarto.getHiba().nemszamHiba("ár");
                    hiba = true;
                }
            }
            if (txtMennyiseg.getText().isEmpty()) {
                nyilvantarto.getHiba().UresMezoHiba("mennyiség");
                hiba = true;
            } else {
                try {
                    darab = Integer.parseInt(txtMennyiseg.getText());
                } catch (NumberFormatException nan) {
                    System.out.println("Darab nem szám!");
                    nyilvantarto.getHiba().nemszamHiba("mennyiség");
                    hiba = true;
                }
            }

            if (txtMEgyseg.getText().isEmpty()) {
                System.out.println("Mértékegység üres!");
                nyilvantarto.getHiba().UresMezoHiba("mértékegység");
                hiba = true;
            } else {
                megyseg = txtMEgyseg.getText();
            }

            // Ha bármi hiba van, NEM hajtjuk végre
            if (!hiba) {
                aru ujAru = new aru(nyilvantarto.getMaxID(), nev, megyseg, ar, darab);

                // Új áru hozzáadása
                if (nyilvantarto.aruHozzaad(ujAru)) {
                    lbUj.setVisible(false);

                    // OB lista frissítése
                    obListaFrissit();

                    // Alapértelmezett, üres elem
                    cbTermék.getSelectionModel().select("Válasszon");

                    // Jelenlegit kiválasztjuk
                    cbTermék.getSelectionModel().select(ujAru); //cbTermék.getSelectionModel().select(nev);

                    btMegse.setVisible(false);
                    btUj.setDisable(false);
                    btHozzaad.setDisable(true);
                    btTorles.setDisable(false);

                } else {
                    obListaFrissit();
                }
            }
        }

        if (e.getSource() == btUj) {
            btUj.setDisable(true);
            btHozzaad.setDisable(false);
            btSzerkesztes.setDisable(true);
            btTorles.setDisable(true);
            txtNev.requestFocus();
            txtSzerk(true);
            txtTorles();
            cbTermék.getSelectionModel().clearSelection();
            lbUj.setVisible(true);
            btMegse.setDisable(false);
            btMegse.setVisible(true);
        }
        if (e.getSource() == btLogTorles) {
//            txLog.clear();
//            nyilvantarto.setLog("");
            nyilvantarto.clearNaplo();
            obNaploFrissit();
        }
        if (e.getSource() == btMegse) {
            cbTermék.getSelectionModel().select("Válasszon");
            btUj.setDisable(false);
            btMegse.setVisible(false);
            btHozzaad.setDisable(true);
            btTorles.setDisable(true);
            txtSzerk(false);
            lbUj.setVisible(false);
            btSzerkesztes.setText("Szerkesztés");

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // nothing
    }

    @FXML
    private void tabKivalaszt(Event e) {
        if (tbLista.isSelected()) {
            obListaFrissit();
        }
        if (tbLog.isSelected()) {
//            txLog.setText(nyilvantarto.getLog());
            obNaploFrissit();
        }
    }

    @FXML
    private void admin() {
        nyilvantarto.showAdminScreen();
    }

    @FXML
    private void nevjegy() {
        nyilvantarto.getHiba().nevjegy();
    }

    public void initManager(final Nyilvantarto nyilvantarto) {
        this.nyilvantarto = nyilvantarto;
        lbUj.setVisible(false);

        obListaFrissit();
        obNaploFrissit();
        cbTermék.setItems(data);
        tcNev.setCellValueFactory(new PropertyValueFactory("nev"));
        tcDarab.setCellValueFactory(new PropertyValueFactory("darab"));
        tcMertekegyseg.setCellValueFactory(new PropertyValueFactory("mertekegyseg"));
        tcAr.setCellValueFactory(new PropertyValueFactory("ear"));
        tvLista.setItems(data);
        tcMikor.setCellValueFactory(new PropertyValueFactory("mikor"));
        tcFelhasznalo.setCellValueFactory(new PropertyValueFactory("felhasznalo"));
        tcMuvelet.setCellValueFactory(new PropertyValueFactory("muvelet"));
        tvNaplo.setItems(naploData);
        //tvLista.getColumns().addAll(tcNev, tcDarab, tcMertekegyseg, tcAr);

        // Csak adminisztrátor láthassa az adminisztrációs menüt és a log tabot
        if (nyilvantarto.getaktFelhasznalo().getTipus() == 0) {
            mnAdmin.setVisible(true);
            tbLog.setDisable(false);
        } else {
            mnAdmin.setVisible(false);
            tbLog.setDisable(true);
        }

        //txLog.setText(nyilvantarto.getLog());
    }

    @FXML
    public void szures() {
        FilteredList<aru> fList = new FilteredList<>(data, p -> true);
        tfSzures.textProperty().addListener((observable, oldValue, newValue)
                -> {
            fList.setPredicate(tfSzures -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (tfSzures.toString().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;

            });
        });
        SortedList<aru> sortedData = new SortedList<>(fList);
        sortedData.comparatorProperty().bind(tvLista.comparatorProperty());
        tvLista.setItems(sortedData);
    }

    @FXML
    public void naploSzures() {
        FilteredList<Naplo> fList = new FilteredList<>(naploData, p -> true);
        tfNaploSzures.textProperty().addListener((observable, oldValue, newValue)
                -> {
            fList.setPredicate(tfNaploSzures -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (tfNaploSzures.toString().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;

            });
        });
        SortedList<Naplo> sortedData = new SortedList<>(fList);
        sortedData.comparatorProperty().bind(tvNaplo.comparatorProperty());
        tvNaplo.setItems(sortedData);
    }

    @FXML
    public void tfSzuresFocus() {
        if (tbLista.isSelected()) {
            tfSzures.requestFocus();
        } else if (tbLog.isSelected()) {
            tfNaploSzures.requestFocus();
        }
        //Egyszerűbbnek találtam, hogy kiszervezem külön metódusba. Talán erőforráskímélőbb. Vagy nem. :D
    }

    @FXML
    private void aruExport() {
        nyilvantarto.aruExport();
    }

    @FXML
    private void aruImport() {
        nyilvantarto.aruImport();
        obListaFrissit();
        lbUj.setVisible(false);
    }

    @FXML
    private void aruFrissites() {
        nyilvantarto.aruFrissit();
        obListaFrissit();
    }

    private void obListaFrissit() {
        data.removeAll(data);
        for (aru object : nyilvantarto.getAruk()) {
            data.add(object);
        }
    }

    private void obNaploFrissit() {
        naploData.removeAll(naploData);
        for (Naplo bejegyzes : nyilvantarto.getNaplo()) {
            naploData.add(bejegyzes);
        }
    }

    @FXML
    public void szamEllenorzes(KeyEvent e) {

        TextField txtMezo = (TextField) e.getSource();

        // Maximum 9 karakter lehet, ez azért kell, mert integert tárolunk, ami max: 2 147 483 647 ... azaz biztonságosan 9 karakter
        if (txtMezo.getText().length() >= 9) {
            e.consume();
        }

        // A bevitt érték ellenőrzése, hogy az csak szám lehessen, ha nem az, töröljük
        if (!e.getCharacter().matches("[0-9]")) {
            e.consume();
        }
    }

    private void txtKitoltes(aru akt) {
        txtNev.setText(akt.getNev());
        txtMennyiseg.setText(akt.getDarab() + "");
        txtMEgyseg.setText(akt.getMertekegyseg());
        txtAr.setText(akt.getEar() + "");
    }

    private void txtSzerk(boolean allapot) {
        txtNev.setEditable(allapot);
        txtAr.setEditable(allapot);
        txtMennyiseg.setEditable(allapot);
        txtMEgyseg.setEditable(allapot);
    }

    private void txtTorles() {
        txtAr.clear();
        txtMEgyseg.clear();
        txtMennyiseg.clear();
        txtNev.clear();
    }
}
