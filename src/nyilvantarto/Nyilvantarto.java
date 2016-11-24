/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nyilvantarto;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Ádám
 */
public class Nyilvantarto extends Application {

//    @Override
//    public void start(Stage stage) throws Exception {
//        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
//
//        Scene scene = new Scene(root);
//
//        stage.setScene(scene);
//        stage.setTitle("Nyilvantartó - Belépés");
//        stage.setResizable(false);
//        // Ezzel megoldjuk az átméretezési hibát
//        stage.sizeToScene();
//        stage.show();
//    }
//
//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String[] args) {
//        new Nyilvantarto().launch();
//    }
//     
    @Override public void start(Stage stage) throws IOException {
    Scene scene = new Scene(new StackPane());
    
    LoginManager loginManager = new LoginManager(scene);
    loginManager.showLoginScreen();

        stage.setScene(scene);
        stage.setTitle("Nyilvantartó - Belépés");
        //stage.setResizable(false);
        // Ezzel megoldjuk az átméretezési hibát
        stage.sizeToScene();
    stage.show();
  }
}
