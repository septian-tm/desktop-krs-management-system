/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Koneksi;
import java.util.Properties;
import java.sql.Connection;
import java.io.FileInputStream;
import javax.swing.JOptionPane;
import java.sql.DriverManager;
/**
 *
 * @author l
 */
public class Database {
    static Properties mypanel;
    static String driver, database, user, pass;
    static Connection conn;
    
    public static Connection KoneksiDB(){
       if(conn == null){
           try{
           mypanel = new Properties();
           mypanel.load(new FileInputStream("lib/database.ini"));
           driver = mypanel.getProperty("DBDriver");
           database = mypanel.getProperty("DBDatabase");
           user = mypanel.getProperty("DBUsername");
           pass = mypanel.getProperty("DBPassword");
           
           Class.forName(driver).newInstance();
           conn = DriverManager.getConnection(database,user,pass);
           JOptionPane.showMessageDialog(null,"Koneksi Berhasil!","Pesan",JOptionPane.INFORMATION_MESSAGE);
           }catch(Exception ex){
           JOptionPane.showMessageDialog(null,"Koneksi Tidak Berhasil!","Pesan",JOptionPane.INFORMATION_MESSAGE);
           System.out.println("Error :"+ex.getMessage());
           }
           
       }
    return conn;
    }
}

