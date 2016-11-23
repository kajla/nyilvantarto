/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nyilvantarto;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.application.Platform;
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
    
    
    
    @FXML
    private void gombNyom (ActionEvent e) {
        //if(e.getSource() == btMégse)
            
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
