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
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;
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

    ArrayList<aru> lista = new ArrayList<>();

    ObservableList<String> olTermék = FXCollections.observableArrayList();
    @FXML
    private ComboBox cbTermék = new ComboBox();

    @FXML
    private TextField txtMennyiseg;

    @FXML
    private TextField txtAr;

    @FXML
    private Button btSzerkesztes;

    @FXML
    private void kilepes() {
        // talán legegyszerűbb rész... :D
        System.exit(0);
    }

    @FXML
    private void SelectedIndexChanged(ActionEvent e) {
        if (e.getSource() == cbTermék) {
            String akt = cbTermék.valueProperty().getValue().toString();
            System.out.println(akt);
            for (aru termék : lista) {
                if (termék.getNev() == akt) {
                    txtMennyiseg.setText(termék.getDarab() + " " + termék.getMertekegyseg());
                    txtAr.setText(termék.getEar() + "");
                    btSzerkesztes.setDisable(false);
                    txtAr.setEditable(false);
                    txtMennyiseg.setEditable(false);
                }
            }
        }

    }

    @FXML
    private void gombEsemenyek(ActionEvent e) {
        if (e.getSource() == btSzerkesztes) {
            txtAr.setEditable(true);
            txtMennyiseg.setEditable(true);
            btSzerkesztes.setDisable(true);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO code application logic here
        //ArrayList<aru> lista = new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("alma.dat")))) {
            while (true) {
                lista.add((aru) ois.readObject());
            }
        } catch (EOFException e) {
        } catch (FileNotFoundException e) {
            System.out.println("Nem találom a fájlt! Hova raktad?!");
        } catch (IOException e) {
            System.out.println(e.toString());
        } catch (ClassNotFoundException e) {
            System.out.println(e.toString());
        }
        Collections.sort(lista);
        for (aru termék : lista) {
            olTermék.add(termék.getNev());
        }
        cbTermék.setItems(olTermék);
    }

    @FXML
    private void nevjegy() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Névjegy");
        alert.setHeaderText("Nyilvántartó alkalmazás");
        alert.setContentText("Ez egy nyilvántartó alkalmazás.\n\nKészítők:\nSzabó Gábor\nRadovits Ádám.");
        alert.showAndWait();
    }
}
