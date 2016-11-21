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
import javafx.scene.control.ComboBox;

/**
 *
 * @author Ádám
 */
public class MainController implements Initializable {

    ObservableList<String> olTermék = FXCollections.observableArrayList();
    @FXML
    private ComboBox cbTermék = new ComboBox();

    @FXML
    private void kilepes() {
        // talán legegyszerűbb rész... :D
        System.exit(0);
    }

    @FXML
    private void SelectedIndexChanged(ActionEvent e) {
        if (e.getSource() == cbTermék) {
            System.out.println(cbTermék.valueProperty().getValue().toString());
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO code application logic here
        ArrayList<aru> lista = new ArrayList<>();

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

}
