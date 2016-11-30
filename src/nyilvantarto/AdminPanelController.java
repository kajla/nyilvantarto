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
import javafx.scene.control.Button;
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
    }
    @FXML
    public void gombEsemenyek(ActionEvent e) {
        if (e.getSource() == btBezar)
            stage.close();
    }
    
    
}
