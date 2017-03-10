/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nyilvantarto;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 *
 * @author Ádám
 */
public class Felhasznalo implements Serializable, Comparable<Object> {

    private String fnev;
    private String jelszo;
    private String nev;
    private String telefon;
    private int tipus;

    public Felhasznalo(String fnev, String jelszo, String nev, String telefon, int tipus) {
        try {
            this.fnev = fnev;
            //this.jelszo = jelszo;
            this.nev = nev;
            this.telefon = telefon;
            this.tipus = tipus;

            String generatedSecuredPasswordHash = generateStorngPasswordHash(jelszo);
            //System.out.println(generatedSecuredPasswordHash);
            this.jelszo = generatedSecuredPasswordHash;

        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            Logger.getLogger(Felhasznalo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Felhasznalo(String fnev, String nev, String telefon, int tipus, String hashJelszo) {
        this.fnev = fnev;
        this.jelszo = hashJelszo;
        this.nev = nev;
        this.telefon = telefon;
        this.tipus = tipus;
    }

    public String getFnev() {
        return fnev;
    }

    public void setFnev(String fnev) {
        this.fnev = fnev;
    }

    public String getJelszo() {
        return jelszo;
    }

    public void setJelszo(String jelszo) {
        try {
            String generatedSecuredPasswordHash = generateStorngPasswordHash(jelszo);
            this.jelszo = generatedSecuredPasswordHash;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            Logger.getLogger(Felhasznalo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public int getTipus() {
        return tipus;
    }

    public void setTipus(int tipus) {
        this.tipus = tipus;
    }

    @Override
    public String toString() {
        return "Felhasználó{" + "felhasználónév=" + fnev + ", jelszó=" + jelszo + ", név=" + nev + ", telefon=" + telefon + '}';
    }

    private static String generateStorngPasswordHash(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        int iterations = 1000;
        char[] chars = password.toCharArray();
        byte[] salt = getSalt();

        PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = skf.generateSecret(spec).getEncoded();
        return iterations + ":" + toHex(salt) + ":" + toHex(hash);
    }

    private static byte[] getSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }

    private static String toHex(byte[] array) throws NoSuchAlgorithmException {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if (paddingLength > 0) {
            return String.format("%0" + paddingLength + "d", 0) + hex;
        } else {
            return hex;
        }
    }

    public boolean validatePassword(String originalPassword) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String[] parts = jelszo.split(":");
        int iterations = Integer.parseInt(parts[0]);
        byte[] salt = fromHex(parts[1]);
        byte[] hash = fromHex(parts[2]);

        PBEKeySpec spec = new PBEKeySpec(originalPassword.toCharArray(), salt, iterations, hash.length * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] testHash = skf.generateSecret(spec).getEncoded();

        int diff = hash.length ^ testHash.length;
        for (int i = 0; i < hash.length && i < testHash.length; i++) {
            diff |= hash[i] ^ testHash[i];
        }
        return diff == 0;

    }

    private static byte[] fromHex(String hex) throws NoSuchAlgorithmException {
        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }

    @Override
    public int compareTo(Object o) {
        Felhasznalo a = (Felhasznalo) o;
        int res = String.CASE_INSENSITIVE_ORDER.compare(nev, a.getNev());
        if (res == 0) {
            res = nev.compareTo(a.getNev());
        }
        return res;
    }
}
