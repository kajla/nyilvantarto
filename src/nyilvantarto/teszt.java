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
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ádám
 */
public class teszt {
    public static void main(String[] args) {
//        Felhasznalo emberek = new Felhasznalo("alma", "kisalma", "korte", 0210);
//        Felhasznalo emberek2 = new Felhasznalo("alma", "kisalma", "korte", 0210);
//        try {
//            System.out.println(emberek.validatePassword("kisalma"));
//        } catch (NoSuchAlgorithmException ex) {
//            Logger.getLogger(teszt.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (InvalidKeySpecException ex) {
//            Logger.getLogger(teszt.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        ArrayList <Lista> lista = new ArrayList<>();
//        lista.add(new Lista("Mizo tej 1 liter "));
//        lista.add(new Lista("Lada"));
        ArrayList<aru> lista = new ArrayList<>();
        lista.add(new aru("körte", "kiló", 300, 20));
        lista.add(new aru("alma", "kiló", 200, 2));
        lista.add(new aru("banán", "kiló", 400, 1));
    
    
    File file = new File("alma.dat");
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        
      try {    
            fos = new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);
            for (aru lista1 : lista) {
                oos.writeObject(lista1);
              
          }
            
} catch (FileNotFoundException ex) {
            System.out.println("Lökd ide a fájlt!");
        }
        catch (IOException ex) {
                ex.printStackTrace();
        }
      
      
        System.out.println(lista);
        Collections.sort(lista);
        // 
        //Collections.s
        System.out.println(lista);
        
         
}
    }

