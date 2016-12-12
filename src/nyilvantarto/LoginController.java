/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nyilvantarto;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author Ádám
 */
public class LoginController {

    private Nyilvantarto nyilvantarto;

    @FXML
    private Label lblHiba;
    @FXML
    private TextField txtFelhasznalonev;
    @FXML
    private TextField txtJelszo;
    @FXML
    private Button btBelepes;

    @FXML
    private void belepes(ActionEvent event) {
        if (event.getSource() == btBelepes) {
            // TODO: fókuszon legyen a gomb + enterrel is lehessen kattintani --> gomb típusa: default
            // https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/Button.html#isFocusTraversable--
            if (txtFelhasznalonev.getText().isEmpty()) {
                lblHiba.setText("A felhasználónév nem lehet üres!");
                lblHiba.setVisible(true);
            } else if (txtJelszo.getText().isEmpty()) {
                lblHiba.setText("A jelszó nem lehet üres!");
                lblHiba.setVisible(true);
            } else {
                if (nyilvantarto.getFelhasznalok() == null || nyilvantarto.getFelhasznalok().isEmpty()) {
                    lblHiba.setText("Végzetes hiba történt!");
                    lblHiba.setVisible(true);
                } else {
                    for (Felhasznalo felhasznalo : nyilvantarto.getFelhasznalok()) {
                        if (txtFelhasznalonev.getText().equals(felhasznalo.getFnev())) {
                            try {
                                if (felhasznalo.validatePassword(txtJelszo.getText())) {
                                    lblHiba.setVisible(false);
                                    Stage login = (Stage) lblHiba.getScene().getWindow();
                                    login.close();
                                    nyilvantarto.setaktFelhasznalo(felhasznalo);
                                    nyilvantarto.showMainScreen();
                                } else {
                                    // Biztonsági okokból ne írjuk ki, hogy pontosan melyik volt a hibás
                                    lblHiba.setText("Hibás felhasználónév / jelszó!");
                                    lblHiba.setVisible(true);
                                }
                            } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
                                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } else {
                            lblHiba.setText("Hibás felhasználónév / jelszó!");
                            lblHiba.setVisible(true);
                        }
                    }
                }

            }
            // Régi kód
//            if (!"alma".equals(txtFelhasznalonev.getText()) || !"kisnyul".equals(txtJelszo.getText())) {
//                lblHiba.setText("Hibás felhasználónév / jelszó!");
//                lblHiba.setVisible(true);
//            } else {
//                lblHiba.setVisible(false);
//                Stage login = (Stage) lblHiba.getScene().getWindow();
//                login.close();
//                nyilvantarto.setFelhasznalonev(txtFelhasznalonev.getText());
//                nyilvantarto.setAlma(10);
//                nyilvantarto.showMainScreen();
//            }
        }
    }

    public void initialize() {
        // Indításkor a felhasználónév mezőre teszem a fókuszt
        Platform.runLater(txtFelhasznalonev::requestFocus);
    }

    public void initManager(final Nyilvantarto nyilvantarto) {
        this.nyilvantarto = nyilvantarto;

    }
}
