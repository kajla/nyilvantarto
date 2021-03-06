/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nyilvantarto;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Ádám
 */
public class AdminPanelController implements Initializable {

    private Nyilvantarto nyilvantarto;
    private Stage stage;

    @FXML
    private Button btBezar;

    @FXML
    private Label lblHiba;

    @FXML
    ObservableList<Felhasznalo> felhasznalok = FXCollections.observableArrayList();

    @FXML
    TableView tvFelhasznalok = new TableView();

    @FXML
    TableColumn tcFNev = new TableColumn();

    @FXML
    TableColumn tcNev = new TableColumn();

    @FXML
    TableColumn tcJelszo = new TableColumn();

    @FXML
    TableColumn tcTelefon = new TableColumn();

    @FXML
    TableColumn tcTipus = new TableColumn();

    @FXML
    private TextField txtFNev;

    @FXML
    private TextField txtNev;

    @FXML
    private PasswordField pwJelszo;

    @FXML
    private TextField txtTelefon;

    @FXML
    private ComboBox cbTipus;

    @FXML
    private Button btHozzaadas;

    @FXML
    private Button btTorles;

    @FXML
    private TextField txtSzures;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    void initManager(Nyilvantarto aThis, Stage stage) {
        this.nyilvantarto = aThis;
        this.stage = stage;
        obListaFrissit();
        tcFNev.setCellValueFactory(new PropertyValueFactory("fnev"));
        tcNev.setCellValueFactory(new PropertyValueFactory("nev"));
        tcJelszo.setCellValueFactory(new PropertyValueFactory("*****"));
        tcJelszo.setVisible(false);
        tcTelefon.setCellValueFactory(new PropertyValueFactory("telefon"));
        tcTipus.setCellValueFactory(new PropertyValueFactory("tipus"));
        tvFelhasznalok.setItems(felhasznalok);
        ObservableList<String> obTipusok = FXCollections.observableArrayList("Admin", "Felhasználó");
        cbTipus.setItems(obTipusok);
    }

    @FXML
    public void gombEsemenyek(ActionEvent e) {
        if (e.getSource() == btBezar) {
            stage.close();
        }
        if (e.getSource() == btHozzaadas) {
            String fnev = "";
            String nev = "";
            String jelszo = "";
            String telefon = "";
            int tipus = 0;
            Boolean hiba = false;
            String hibauzenet = "";
            if (txtFNev.getText().isEmpty()) {
                System.out.println("Felhasználónév üres!");
                hiba = true;
                hibauzenet += "Felhasználónév üres! ";
            } else {
                fnev = txtFNev.getText();
                for (Felhasznalo felhasznalo : felhasznalok) {
                    if (felhasznalo.getFnev().equals(fnev)) {
                        //nyilvantarto.getHiba().marfoglaltHiba(fnev);
                        hiba = true;
                        hibauzenet += "Felhasználónév már foglalt! ";
                    }
                }
            }
            if (txtNev.getText().isEmpty()) {
                System.out.println("Név üres!");
                hiba = true;
                hibauzenet += "Név üres! ";
            } else {
                nev = txtNev.getText();
            }
            if (pwJelszo.getText().isEmpty()) {
                System.out.println("Jelszó üres!");
                hiba = true;
                hibauzenet += "Jelszó üres! ";
            } else {
                jelszo = pwJelszo.getText();
            }
            if (txtTelefon.getText().isEmpty()) {
                System.out.println("Telefon üres!");
                hiba = true;
                hibauzenet += "Telefon üres! ";
            } else {
                telefon = txtTelefon.getText();
            }
            if (cbTipus.getSelectionModel().isEmpty()) {
                System.out.println("Típus üres!");
                hiba = true;
                hibauzenet += "Típus üres! ";
            } else {
                if ("Admin".equals(cbTipus.getSelectionModel().getSelectedItem().toString())) {
                    tipus = 0;
                } else {
                    tipus = 1;
                }
            }
            // Ha bármi hiba van, NEM hajtjuk végre
            if (!hiba) {
                // Felvesszünk egy új elemet
                Felhasznalo ujfelh = new Felhasznalo(fnev, jelszo, nev, telefon, tipus);

                // Új felhasználó felvétele
                if (nyilvantarto.felhasznaloHozzaadas(ujfelh)) {
                    txtFNev.clear();
                    txtNev.clear();
                    pwJelszo.clear();
                    txtTelefon.clear();
                    obListaFrissit();
                } else {
                    obListaFrissit();
                }

            } else {
                lblHiba.setText(hibauzenet);
                lblHiba.setVisible(true);
            }
        }
        if (e.getSource() == btTorles) {
            Felhasznalo torlendo = (Felhasznalo) tvFelhasznalok.getSelectionModel().getSelectedItem();
            if (nyilvantarto.getaktFelhasznalo().getFnev().equals(torlendo.getFnev())) {
                nyilvantarto.getHiba().onmagunkHiba();
            } else {
                Alert biztosan = new Alert(AlertType.CONFIRMATION);
                biztosan.setTitle("Nyilvántartó");
                biztosan.setHeaderText(torlendo.getNev() + " (" + torlendo.getFnev() + ") törlésére készül.");
                biztosan.setContentText("Valóban törölni szeretné a felhasználót?");
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
                    // Sajnos ezt a felhasználót elveszítettük...
                    Felhasznalo toroltFelh = (Felhasznalo) tvFelhasznalok.getSelectionModel().getSelectedItem();

                    // Adatbáziskezelés
                    if (nyilvantarto.felhasznaloTorles(toroltFelh)) {
                        obListaFrissit();
                    } else {
                        obListaFrissit();
                    }
                }
            }
        }
    }

    public void szerkesztes(Event e) {
//        Felhasznalo akt = (Felhasznalo) tvFelhasznalok.getSelectionModel().getSelectedItem();
//        txtFNev.setText(akt.getFnev());
//        txtNev.setText(akt.getNev());
//        txtTelefon.setText(Integer.toString(akt.getTelefon()));
//        if (akt.getTipus() == 0) {
//            cbTipus.getSelectionModel().select("Admin");
//        } else {
//            cbTipus.getSelectionModel().select("Felhasználó");
//        }

    }

    @FXML
    public void szures() {
        FilteredList<Felhasznalo> fList = new FilteredList<>(felhasznalok, p -> true);
        txtSzures.textProperty().addListener((observable, oldValue, newValue)
                -> {
            fList.setPredicate(txtSzures -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (txtSzures.toString().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;

            });
        });
        SortedList<Felhasznalo> sortedData = new SortedList<>(fList);
        sortedData.comparatorProperty().bind(tvFelhasznalok.comparatorProperty());
        tvFelhasznalok.setItems(sortedData);
    }

    private void obListaFrissit() {
        felhasznalok.removeAll(felhasznalok);
        for (Felhasznalo felh : nyilvantarto.getFelhasznalok()) {
            felhasznalok.add(felh);
        }
    }
}
