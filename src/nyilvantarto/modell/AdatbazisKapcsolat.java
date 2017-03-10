/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nyilvantarto.modell;

/**
 *
 * @author Ádám
 */
public interface AdatbazisKapcsolat {

    String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    String URL = "jdbc:derby://localhost:1527/nyilvantarto;create=true;collation=TERRITORY_BASED";
    String USER = "nyilvantarto";
    String PASSWORD = "dbjelszo";
    String TABLAK[] = {"ARUK", "NAPLO"};

    String SQLARUKELDOBAS = "DROP TABLE ARUK";
    String SQLARUKLETREHOZ = "CREATE TABLE ARUK (\n"
            + "id INTEGER NOT NULL PRIMARY KEY,\n"
            + "nev VARCHAR(120) NOT NULL,\n"
            + "mertekegyseg VARCHAR(64) NOT NULL,\n"
            + "egysegar INTEGER NOT NULL,\n"
            + "darab INTEGER NOT NULL)";
    String SQLARUK = "SELECT * FROM ARUK ORDER BY nev";
    String SQLARUHOZZAAD = "INSERT INTO ARUK VALUES (?, ?, ?, ?, ?)";
    String SQLARUTOROL = "DELETE FROM ARUK WHERE id = ?";
    String SQLARUMODOSIT = "UPDATE ARUK SET nev = ?, mertekegyseg = ?, egysegar = ?, darab = ? WHERE id = ?";
    String SQLNAPLOLETREHOZ = "CREATE TABLE NAPLO (\n"
            + "azon INT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),\n"
            + "mikor TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,\n"
            + "felhasznalo VARCHAR(120) NOT NULL,\n"
            + "muvelet VARCHAR(255) NOT NULL)";
    String SQLNAPLOHOZZAAD = "INSERT INTO NAPLO (MIKOR, FELHASZNALO, MUVELET) \n"
            + "	VALUES (DEFAULT, ?, ?)";
    String SQLNAPLOK = "SELECT * FROM NAPLO ORDER BY azon";
}
