/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nyilvantarto;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 *
 * @author Ádám
 */
public class MainController implements Initializable {

    private Nyilvantarto nyilvantarto;
    //private nyilvantarto.modell.Fajlkezeles fajlkezeles;

    ArrayList<aru> aruLista; // = new ArrayList<>();

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
    private TextArea txLog;

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

    @FXML
    ObservableList<aru> data = FXCollections.observableArrayList();

    private final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd - kk:mm:ss");

    @FXML
    private Menu mnAdmin;

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
//                txtNev.setEditable(false);
//                txtMEgyseg.setEditable(false);
//                txtAr.setEditable(false);
//                txtMennyiseg.setEditable(false);
                txtNev.clear();
                txtMEgyseg.clear();
                txtAr.clear();
                txtMennyiseg.clear();
                btSzerkesztes.setDisable(true);
                btTorles.setDisable(true);
            } else {
                aru akt = (aru) cbTermék.getSelectionModel().getSelectedItem();
                System.out.println("DEBUG: " + akt.getId() + " - " + akt.getNev());
                for (aru termék : aruLista) {
                    if (termék.equals(akt)) {
                        txtNev.setText(termék.getNev());
                        txtMennyiseg.setText(termék.getDarab() + "");
                        txtMEgyseg.setText(termék.getMertekegyseg());
                        txtAr.setText(termék.getEar() + "");
                        btSzerkesztes.setDisable(false);
                        txtNev.setEditable(false);
                        txtAr.setEditable(false);
                        txtMennyiseg.setEditable(false);
                        txtMEgyseg.setEditable(false);
                        btSzerkesztes.setDisable(false);
                        btTorles.setDisable(false);
                        // FIX: Ha "Új" hozzáadása közben váltottunk, akkor aktívak maradtak a gombok
                        btUj.setDisable(false);
                        btHozzaad.setDisable(true);
                        btMegse.setDisable(true);
                        lbUj.setVisible(false);
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
                    nyilvantarto.getHiba().nemszamHiba("darab");
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
                    //lbUj.setVisible(false); //-->ez mit keresett itt?
                    aru elozo = (aru) cbTermék.getSelectionModel().getSelectedItem();
                    nyilvantarto.addLog(
                            elozo.getNev()
                            + " szerkesztve lett "
                            + nyilvantarto.getaktFelhasznalo().getFnev() + " által"); //Ezt tovább lehet majd egyszer fejleszteni, hogy többet írjon ki

                    // Alapértelmezett, üres elem
                    cbTermék.getSelectionModel().select("Válasszon");
                    // Előző termék törlése
                    aruLista.remove(elozo);
                    // Régi termék újrafelvétele, azonos azonosítóval
                    aruLista.add(new aru(elozo.getId(), nev, megyseg, ar, darab));

                    // Egyből be is rendezzük ;) ... hátha változott a neve :)
                    Collections.sort(aruLista);
                    // Felülcsapjuk a globális listát
                    nyilvantarto.setAruk(aruLista);
                    // OB lista frissítése
                    obListaFrissit();
                    // Előzőt kiválasztjuk
                    cbTermék.getSelectionModel().select(elozo);

                    txtNev.setEditable(false);
                    txtAr.setEditable(false);
                    txtMennyiseg.setEditable(false);
                    txtMEgyseg.setEditable(false);
                    //btSzerkesztes.setDisable(true);
                    //btHozzaad.setDisable(false);
                    btUj.setDisable(false);
                    btTorles.setDisable(false);
                    btSzerkesztes.setText("Szerkesztés");
                }
            } else {
                btSzerkesztes.setText("Mentés");
                txtNev.setEditable(true);
                txtAr.setEditable(true);
                txtMennyiseg.setEditable(true);
                txtMEgyseg.setEditable(true);
                //btSzerkesztes.setDisable(true);
                btHozzaad.setDisable(true);
                btUj.setDisable(true);
                btTorles.setDisable(true);
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
                    // Kiválasztott elemet töröljük
                    cbTermék.getSelectionModel().select("Válasszon");

                    // Töröljük a listából az elemet
                    aruLista.remove(akt);

                    nyilvantarto.addLog(akt.getNev() + " eltávolítva " + nyilvantarto.getaktFelhasznalo().getFnev() + " által");

                    // Felülcsapjuk a globális listát
                    nyilvantarto.setAruk(aruLista);

                    obListaFrissit();
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
                aru akt = new aru(nyilvantarto.getMaxID(), nev, megyseg, ar, darab);
                lbUj.setVisible(false);

                // Alapértelmezett, üres elem
                cbTermék.getSelectionModel().select("Válasszon");

                // Felvesszük az új elemet
                aruLista.add(akt);

                // Egyből be is rendezzük ;)
                Collections.sort(aruLista);

                // Felülcsapjuk a globális listát
                nyilvantarto.setAruk(aruLista);

                // OB lista frissítése
                obListaFrissit();

                // Jelenlegit kiválasztjuk
                cbTermék.getSelectionModel().select(akt); //cbTermék.getSelectionModel().select(nev);

                btUj.setDisable(false);
                btHozzaad.setDisable(true);
                btTorles.setDisable(false);
                nyilvantarto.addLog(akt.getNev() + " hozzáadva " + nyilvantarto.getaktFelhasznalo().getFnev() + " által");
            }
        }
        //nyilvantarto.setLog(txLog.getText());

        if (e.getSource() == btUj) {
            btUj.setDisable(true);
            btHozzaad.setDisable(false);
            btSzerkesztes.setDisable(true);
            btTorles.setDisable(true);
            txtNev.requestFocus();
            txtNev.setEditable(true);
            txtAr.setEditable(true);
            txtMennyiseg.setEditable(true);
            txtMEgyseg.setEditable(true);
            txtAr.clear();
            txtMEgyseg.clear();
            txtMennyiseg.clear();
            txtNev.clear();
            cbTermék.getSelectionModel().clearSelection();
            lbUj.setVisible(true);
            btMegse.setDisable(false);
        }
        if (e.getSource() == btLogTorles) {
            txLog.clear();
            nyilvantarto.setLog("");
        }
        if (e.getSource() == btMegse) {
            cbTermék.getSelectionModel().select("Válasszon");
            btUj.setDisable(false);
            btMegse.setDisable(true);
            btHozzaad.setDisable(true);
            btTorles.setDisable(true);
            txtNev.setEditable(false);
            txtAr.setEditable(false);
            txtMennyiseg.setEditable(false);
            txtMEgyseg.setEditable(false);
            lbUj.setVisible(false);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO: ide kell valami?
    }

    @FXML
    private void tabKivalaszt(Event e) {
        if (tbLista.isSelected()) {
            //System.out.println("bojler");            
            obListaFrissit();
        }
        if (tbLog.isSelected()) {
            txLog.setText(nyilvantarto.getLog());
        }
//        if (e.getSource() == tbLista)
//            System.out.println("bojler");
//        tpTab.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
//            @Override
//            public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
//                if(newValue == tbLista)
//                    data.rem
//                    for (aru termék : aruLista) {
//                        data.add(termék);
//        }
//                tvLista.setItems(data);
//                System.out.println("bojler");
//            }
//        
//            });
    }

    @FXML
    private void admin() {
        nyilvantarto.showAdminScreen();
    }

    @FXML
    private void nevjegy() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Névjegy");
        alert.setHeaderText("Nyilvántartó alkalmazás");
        alert.setContentText("Ez egy nyilvántartó alkalmazás.\n\nKészítők:\nSzabó Gábor\nRadovits Ádám");
        alert.showAndWait();
    }

    public void initManager(final Nyilvantarto nyilvantarto) {
        this.nyilvantarto = nyilvantarto;
        this.aruLista = nyilvantarto.getAruk();
//        System.out.println(aruLista);
//        Collections.sort(aruLista);
//        for (aru termék : aruLista) {
//            olTermék.add(termék.getNev());
//        }
        //olTermék.setAll(aruLista.toString());
//        cbTermék.setItems(olTermék);
        lbUj.setVisible(false);
        //txLog.setText(fajlkezeles.logOlvasas());

        // Ezek így nem lesznek jók... :(
//        for (aru termék : aruLista) {
//            tcNev.getColumns().add(termék.getNev());
//            tcDarab.getColumns().add(termék.getDarab());
//            tcMertekegyseg.getColumns().add(termék.getMertekegyseg());
//            tcAr.getColumns().add(termék.getEar());
//        }
//        for (aru termék : nyilvantarto.getAruk()) {
//            data.add(termék);
//        }
        obListaFrissit();
        cbTermék.setItems(data);
        tcNev.setCellValueFactory(new PropertyValueFactory("nev"));
        tcDarab.setCellValueFactory(new PropertyValueFactory("darab"));
        tcMertekegyseg.setCellValueFactory(new PropertyValueFactory("mertekegyseg"));
        tcAr.setCellValueFactory(new PropertyValueFactory("ear"));
        tvLista.setItems(data);
        //tvLista.getColumns().addAll(tcNev, tcDarab, tcMertekegyseg, tcAr);

        // Csak adminisztrátor láthassa az adminisztrációs menüt és a log tabot
        if (nyilvantarto.getaktFelhasznalo().getTipus() == 0) {
            mnAdmin.setVisible(true);
            tbLog.setDisable(false);
        } else {
            mnAdmin.setVisible(false);
            tbLog.setDisable(true);
        }

        txLog.setText(nyilvantarto.getLog());
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
    public void tfSzuresFocus() {
        tfSzures.requestFocus();
        //Egyszerűbbnek találtam, hogy kiszervezem külön metódusba. Talán erőforráskímélőbb. Vagy nem. :D
    }

    @FXML
    private void aruExport() {
        nyilvantarto.getFajlkezeles().aruExport(nyilvantarto);
        nyilvantarto.addLog(nyilvantarto.getaktFelhasznalo().getFnev() + " exportálta az árucikkeket");
    }

    @FXML
    private void aruImport() {
        nyilvantarto.getFajlkezeles().aruImport(nyilvantarto);
        nyilvantarto.addLog(nyilvantarto.getaktFelhasznalo().getFnev() + " importálta az árucikkeket");

        // Törlés és újrafelvétel
//        olTermék.clear();
        this.aruLista = nyilvantarto.getAruk();
//        for (aru termék : nyilvantarto.getAruk()) {
//            olTermék.add(termék.getNev());
//        }
        obListaFrissit();
        lbUj.setVisible(false);
    }

    private void obListaFrissit() {
        data.removeAll(data);
        for (aru object : nyilvantarto.getAruk()) {
            data.add(object);
        }
    }
}
