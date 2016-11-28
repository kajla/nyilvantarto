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
    private int x;
    //private Fajlkezeles asd = new Fajlkezeles();

    ArrayList<aru> lista = new ArrayList<>();
    //ArrayList<aru> lista = new ArrayList<>(); //FIXME = asd.aruOlvasas("alma.dat");

    ObservableList<String> olTermék = FXCollections.observableArrayList();
    @FXML
    private ComboBox cbTermék = new ComboBox();

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
    public void HozzaadMegnyom(ActionEvent UgyanittBojlerElado /* byGabor */) {
        if (UgyanittBojlerElado.getSource() == btHozzaad) {
            HozzaadAblak();

        }

    }

    @FXML
    private void HozzaadAblak() {

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
    }

    @FXML
    private void kilepes() {
        // talán legegyszerűbb rész... :D
        Platform.exit();
    }

    @FXML
    private void SelectedIndexChanged(ActionEvent e) {
        if (e.getSource() == cbTermék) {
            String akt = lista.get(0).getNev();
            try {
                akt = cbTermék.valueProperty().getValue().toString();
            } catch (Exception ex) {
                System.out.println("eee");
            }

            //String akt = cbTermék.valueProperty().getValue().toString();
            System.out.println(akt);
            int index = 0;
//            x = 0;
            for (aru termék : lista) {
                if (termék.getNev() == akt) {
                    txtMennyiseg.setText(termék.getDarab() + "");
                    txtMEgyseg.setText(termék.getMertekegyseg());
                    txtAr.setText(termék.getEar() + "");
                    btSzerkesztes.setDisable(false);
                    txtAr.setEditable(false);
                    txtMennyiseg.setEditable(false);
                    x = index;
                    System.out.println(x);

                    index++;
                } else {
                    index++;
                }
            }

        }

    }

    @FXML
    private void gombEsemenyek(ActionEvent e) {
        if (e.getSource() == btSzerkesztes) {
            txtAr.setEditable(true);
            txtMennyiseg.setEditable(true);
            txtMEgyseg.setEditable(true);
            btSzerkesztes.setDisable(true);
        }
        if (e.getSource() == btTorles) {
            olTermék.remove(x);
            System.out.println(x);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO code application logic here
        //ArrayList<aru> lista = new ArrayList<>();

//        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("alma.dat")))) {
//            while (true) {
//                lista.add((aru) ois.readObject());
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
        this.lista = nyilvantarto.getAruk();
        System.out.println(lista);
        Collections.sort(lista);
        for (aru termék : lista) {
            olTermék.add(termék.getNev());
        }
        //olTermék.setAll(lista.toString());
        cbTermék.setItems(olTermék);
        System.out.println(nyilvantarto.getAlma());

    }
}
