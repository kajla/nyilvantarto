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
import java.util.Date;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

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
    private Tab tbLista = new Tab();

    @FXML
    private TabPane tpTab = new TabPane();

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

    private DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd - kk:mm:ss");

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
                        btSzerkesztes.setDisable(false);
                        btTorles.setDisable(false);
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

                    lbUj.setVisible(false);
                    int i = 0;
                    int szerkesztendő = 0;
                    for (aru termék : aruLista) {
                        // Jobb megoldást nem találtam... mivel NINCS egyedi azonosító! :(
                        if (termék.getNev().equals(cbTermék.getSelectionModel().getSelectedItem().toString())) {//FIXME: && termék.getMertekegyseg() == txtMEgyseg.getText() && termék.getEar() == Integer.parseInt(txtAr.getText()) && termék.getDarab() == Integer.parseInt(txtMennyiseg.getText())) {
                            szerkesztendő = i;
                            txLog.appendText(dateFormat.format(new Date()) + ": "
                                    + cbTermék.getSelectionModel().getSelectedItem().toString()
                                    + " szerkesztve lett "
                                    + nyilvantarto.getFelhasznalonev() + " által\n"); //Ezt tovább lehet majd egyszer fejleszteni, hogy többet írjon ki
                        }
                        i++;
                    }
                    aruLista.remove(szerkesztendő);
                    aruLista.add(new aru(nev, megyseg, ar, darab));
                    // Alapértelmezett, üres elem
                    cbTermék.getSelectionModel().select("Válasszon");
                    // Egyből be is rendezzük ;)
                    Collections.sort(aruLista);
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
            // Ha NEM üres ("Válasszon" is üres!!!)
            if (!cbTermék.getSelectionModel().isEmpty()) {
                String akt = cbTermék.getSelectionModel().getSelectedItem().toString(); //cbTermék.valueProperty().getValue().toString();
                int törlendő = 0;
                int i = 0;
                for (aru termék : aruLista) {
                    // Jobb megoldást nem találtam... mivel NINCS egyedi azonosító! :(
                    if (termék.getNev().equals(akt)) {//FIXME: && termék.getMertekegyseg() == txtMEgyseg.getText() && termék.getEar() == Integer.parseInt(txtAr.getText()) && termék.getDarab() == Integer.parseInt(txtMennyiseg.getText())) {
                        // Ezt kell törölnünk majd...
                        törlendő = i;
                        txLog.appendText(dateFormat.format(new Date()) + ": " + akt + " eltávolítva " + nyilvantarto.getFelhasznalonev() +" által\n");
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
                lbUj.setVisible(false);
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
                txLog.appendText(dateFormat.format(new Date()) + ": " + nev + " hozzáadva " + nyilvantarto.getFelhasznalonev() + " által\n");
            }
        }
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
        }
        if (e.getSource() == btLogTorles) {
            txLog.clear();
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
    private void tabKivalaszt(Event e) {
        if (tbLista.isSelected()) {
            //System.out.println("bojler");            
            data.removeAll(data);
            for (aru object : aruLista) {
                data.add(object);

            }
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
        lbUj.setVisible(false);

        // Ezek így nem lesznek jók... :(
//        for (aru termék : aruLista) {
//            tcNev.getColumns().add(termék.getNev());
//            tcDarab.getColumns().add(termék.getDarab());
//            tcMertekegyseg.getColumns().add(termék.getMertekegyseg());
//            tcAr.getColumns().add(termék.getEar());
//        }
        for (aru termék : aruLista) {
            data.add(termék);
        }
        tcNev.setCellValueFactory(new PropertyValueFactory("nev"));
        tcDarab.setCellValueFactory(new PropertyValueFactory("darab"));
        tcMertekegyseg.setCellValueFactory(new PropertyValueFactory("mertekegyseg"));
        tcAr.setCellValueFactory(new PropertyValueFactory("ear"));
        tvLista.setItems(data);
        //tvLista.getColumns().addAll(tcNev, tcDarab, tcMertekegyseg, tcAr);
        System.out.println(olTermék);
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
}
