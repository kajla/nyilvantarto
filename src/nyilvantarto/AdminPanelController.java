/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nyilvantarto;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
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
        for (Felhasznalo júzer : aThis.getFelhasznalok()) {
            felhasznalok.add(júzer);
        }
        tcFNev.setCellValueFactory(new PropertyValueFactory("fnev"));
        tcNev.setCellValueFactory(new PropertyValueFactory("nev"));
        tcJelszo.setCellValueFactory(new PropertyValueFactory("*****"));
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
            int telefon = 0;
            int tipus = 0;
            Boolean hiba = false;
            String hibauzenet = "";
            if (txtFNev.getText().isEmpty()) {
                System.out.println("Felhasználónév üres!");
                hiba = true;
                hibauzenet += "Felhasználónév üres! ";
            } else {
                fnev = txtFNev.getText();
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
            try {
                telefon = Integer.parseInt(txtTelefon.getText());
            } catch (NumberFormatException nan) {
                System.out.println("Telefon nem szám!");
                hiba = true;
                hibauzenet += "Telefon nem szám! ";
            }
            if (cbTipus.getSelectionModel().isEmpty()) {
                System.out.println("Típus üres!");
                hiba = true;
                hibauzenet += "Típus üres! ";
            } else {
                if (cbTipus.getSelectionModel().getSelectedItem().toString() == "Admin") {
                    tipus = 0;
                } else {
                    tipus = 1;
                }
            }
            // Ha bármi hiba van, NEM hajtjuk végre
            if (!hiba) {
                // Felvesszük az új elemet
                felhasznalok.add(new Felhasznalo(fnev, jelszo, nev, telefon, tipus));
                // Egyből be is rendezzük ;)
                Collections.sort(felhasznalok);
                // Felülcsapjuk a globális listát
                ArrayList<Felhasznalo> ideiglenes = new ArrayList<>();
                for (Felhasznalo felhasznalo : felhasznalok) {
                    ideiglenes.add(felhasznalo);
                }
                nyilvantarto.setFelhasznalok(ideiglenes);
// TODO: naplózás!
            } else {
                lblHiba.setText(hibauzenet);
                lblHiba.setVisible(true);
            }
        }
        if (e.getSource() == btTorles) {
            Felhasznalo törlendő = (Felhasznalo) tvFelhasznalok.getSelectionModel().getSelectedItem();
            Alert biztosan = new Alert(AlertType.CONFIRMATION);
            biztosan.setTitle("Nyilvántartó");
            biztosan.setHeaderText(törlendő.getNev() + " (" + törlendő.getFnev() + ") törlésére készül.");
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
                felhasznalok.remove((Felhasznalo) tvFelhasznalok.getSelectionModel().getSelectedItem());

                // Felülcsapjuk a globális listát
                ArrayList<Felhasznalo> ideiglenes = new ArrayList<>();
                for (Felhasznalo felhasznalo : felhasznalok) {
                    ideiglenes.add(felhasznalo);
                }
                nyilvantarto.setFelhasznalok(ideiglenes);
            }
        }
    }

}
