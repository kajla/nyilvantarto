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
        Felhasznalo emberek = new Felhasznalo("alma", "alma", "korte", 0210, 0);
        try {
            //Felhasznalo emberek2 = new Felhasznalo("alma", "kisalma", "korte", 0210);

            System.out.println(emberek.validatePassword("kisnyul"));
            System.out.println(emberek.validatePassword("alma"));
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(teszt.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(teszt.class.getName()).log(Level.SEVERE, null, ex);
        }
        

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("felhasznalok.dat")))) {
            oos.writeObject(emberek);
        } catch (IOException ex) {
            Logger.getLogger(teszt.class.getName()).log(Level.SEVERE, null, ex);
        }

//        ArrayList<aru> lista = new ArrayList<>();
//        lista.add(new aru("körte", "kg", 300, 20));
//        lista.add(new aru("alma", "kg", 200, 2));
//        lista.add(new aru("banán", "kg", 400, 1));
//        lista.add(new aru("mosógép", "db", 310000, 5));
//
//        File file = new File("aruk.dat");
//        FileOutputStream fos = null;
//        ObjectOutputStream oos = null;
//
//        try {
//            fos = new FileOutputStream(file);
//            oos = new ObjectOutputStream(fos);
//            //oos.writeObject(lista);
//            for (aru lista1 : lista) {
//                oos.writeObject(lista1);
//
//            }
//
//        } catch (FileNotFoundException ex) {
//            System.out.println("Lökd ide a fájlt!");
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//
//        System.out.println(lista);
//        Collections.sort(lista);
//        // 
//        //Collections.s
//        System.out.println(lista);

    }
}
