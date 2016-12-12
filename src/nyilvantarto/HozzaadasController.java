/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nyilvantarto;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author szabogabor
 */
public class HozzaadasController extends Application implements Initializable {

    @FXML
    private TextField txtNev;

    @FXML
    private TextField txtDarab;

    @FXML
    private TextField txtAr;

    @FXML
    private TextField txtMertekegyseg;

    @FXML
    private Button btOk;

    @FXML
    private Button btMégse;
    
    private Nyilvantarto nyilvantarto;

    @FXML
    private void gombNyom(ActionEvent e) {
        if (e.getSource() == btOk) {
            String Nev = txtNev.getText();
            String Me = txtMertekegyseg.getText();
            String ArString = txtAr.getText();
            int Ar = Integer.parseInt(ArString);
            String DbString = txtDarab.getText();
            int Db = Integer.parseInt(DbString);

            ArrayList<aru> lista = new ArrayList<>();
            lista.add(new aru(nyilvantarto.getMaxID(), Nev, Me, Ar, Db));

            File file = new File("alma.dat");
            FileOutputStream fos = null;
            ObjectOutputStream oos = null;

            try {
                fos = new FileOutputStream(file);
                oos = new ObjectOutputStream(fos);
                //oos.writeObject(lista);
                for (aru lista1 : lista) {
                    oos.writeObject(lista1);

                }

            } catch (FileNotFoundException ex) {
                System.out.println("Lökd ide a fájlt!");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            
            //debug kedvéért
            Collections.sort(lista);
            System.out.println(lista);
            
            

        }

    }

    @FXML
    public void CloseWindow(ActionEvent e) {
        //ez még todo
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }

}
