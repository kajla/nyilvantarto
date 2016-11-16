/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nyilvantarto;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 * @author Ádám
 */
public class LoginController implements Initializable {

    @FXML
    private Label lblHiba;
    @FXML
    private TextField txtFelhasznalonev;
    @FXML
    private TextField txtJelszo;

    @FXML
    private void belepes(ActionEvent event) {
        // TODO: fókuszon legyen a gomb + enterrel is lehessen kattintani 
        if ("".equals(txtFelhasznalonev.getText())) {
            lblHiba.setText("A felhasználónév nem lehet üres!");
            lblHiba.setVisible(true);
        } else if ("".equals(txtJelszo.getText())) {
            lblHiba.setText("A jelszó nem lehet üres!");
            lblHiba.setVisible(true);
        } else if (!"alma".equals(txtFelhasznalonev.getText()) || !"kisnyul".equals(txtJelszo.getText())) {
            lblHiba.setText("Hibás felhasználónév / jelszó!");
            lblHiba.setVisible(true);
        } else {
            lblHiba.setVisible(false);
            Alert siker = new Alert(Alert.AlertType.INFORMATION);
            siker.setTitle("Nyilvántartó");
            siker.setHeaderText("Sikeres belépés");
            siker.setContentText("Ön sikeresen bejelentkezett a szoftverbe.");
            siker.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
