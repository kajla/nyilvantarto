/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nyilvantarto;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 * @author Ádám
 */
public class MainController implements Initializable {

    @FXML
    private Label lblHiba;
    @FXML
    private TextField txtFelhasznalonev;
    @FXML
    private TextField txtJelszo;
    

    @FXML
    private void kilepes() {
        // talán legegyszerűbb rész... :D
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
