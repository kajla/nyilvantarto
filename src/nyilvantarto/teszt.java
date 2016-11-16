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

/**
 *
 * @author Ádám
 */
public class teszt {
    public static void main(String[] args) {
        Felhasznalo emberek = new Felhasznalo("alma", "kisalma", "korte", 0210);
        Felhasznalo emberek2 = new Felhasznalo("alma", "kisalma", "korte", 0210);
        try {
            System.out.println(emberek.validatePassword("kisalma"));
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(teszt.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(teszt.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
