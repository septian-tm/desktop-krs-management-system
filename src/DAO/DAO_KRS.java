/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import Koneksi.Database;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import Model.varKRS;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author l
 */
public class DAO_KRS implements DAO_Interface<varKRS>{
    Connection connection;
    public DAO_KRS(){
        connection = Database.KoneksiDB();
    }
    
    public List<varKRS> isicomboTA(){
        PreparedStatement statement;
        List<varKRS> list = null;
        try{
            list = new ArrayList();
            statement = connection.prepareStatement("SELECT DISTINCT TA FROM PERIODE ORDER BY TA");
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                varKRS b = new varKRS();
                b.setvTA(rs.getString("TA"));
                list.add(b);
            }
            rs.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public List<varKRS> isicomboSMT(){
        PreparedStatement statement;
        List<varKRS> list=null;
        try{
            list = new ArrayList();
            statement = connection.prepareStatement("SELECT DISTINCT Semester FROM PERIODE ORDER BY Semester");
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                varKRS b = new varKRS();
                b.setvSemester(rs.getString("Semester"));
                list.add(b);
            }
            rs.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }
       
    public List<varKRS> getNmMhs(String nim){
        PreparedStatement statement;
        List<varKRS> list=null;
        try{
            list = new ArrayList();
            statement = connection.prepareStatement("SELECT * FROM Mahasiswa where NIM=?");
            statement.setString(1, nim);
            ResultSet rs = statement.executeQuery();
            if(!rs.next()){
                System.out.println("Data tidak ditemukan");
            }else{
                varKRS b = new varKRS();
                b.setvNama(rs.getString("nama"));
                list.add(b);
            }    
            
        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public List<varKRS> getDataMtk(String kodemtk){
        PreparedStatement statement;
        List<varKRS> list=null;
        try{
            list = new ArrayList();
            statement = connection.prepareStatement("SELECT * FROM Matakuliah where KodeMtk=?");
            statement.setString(1, kodemtk);
            ResultSet rs = statement.executeQuery();
            if(!rs.next()){
                System.out.println("Data tidak ditemukan");
            }else{
                varKRS b = new varKRS();
                b.setvNamaMTK(rs.getString("NamaMTK"));
                b.setvSKS(rs.getInt("SKS"));
                b.setvKodePrasyarat(rs.getString("KodePrasyarat"));
                list.add(b);
            }    
            
        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }
    
    
    public List<varKRS> getAllDetilKRS(String ta, String semester, String nim){
        List<varKRS> list=null;
        PreparedStatement st = null;
        try{
            st = null;
            list = new ArrayList<varKRS>();
            String sql = "SELECT * From Detil_KRS a, Matakuliah b Where a.TA=? and a.Semester=? and a.NIM=? and a.kodeMtk=b.kodeMtk";
            st = connection.prepareStatement(sql);
            st.setString(1, ta);
            st.setString(2, semester);
            st.setString(3, nim);
            
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                varKRS objKRS = new varKRS();
                objKRS.setvKdMTK(rs.getString("KodeMTK"));
                objKRS.setvNamaMTK(rs.getString("NamaMTK"));
                objKRS.setvSKS(rs.getInt("SKS"));
                objKRS.setvKodePrasyarat(rs.getString("KodePrasyarat"));
                list.add(objKRS);
            }
            rs.close();
            st.close();
            
        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }    
    
    @Override
    public void insert(varKRS object) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = connection.prepareStatement("SELECT * FROM krs where TA=? and Semester=? and NIM=?");
            st.setString(1, object.getvTA());
            st.setString(2, object.getvSemester());
            st.setString(3, object.getvNIM());
            rs = st.executeQuery();
            if (!rs.next()){
                st = null;
                st = connection.prepareStatement("INSERT INTO krs(TA, Semester, NIM, TGL_KRS) VALUES(?,?,?,?)");
                st.setString(1, object.getvTA());
                st.setString(2, object.getvSemester());
                st.setString(3, object.getvNIM());
                st.setString(4, object.getvTglKRS().substring(0,10));
                System.out.println(""+st);
                st.executeUpdate();
            }
            
            st = null;
            st = connection.prepareStatement("SELECT * FROM detil_krs WHERE TA=? and Semester=? and NIM=? and kodeMTK=?");
            st.setString(1, object.getvTA());
            st.setString(2, object.getvSemester());
            st.setString(3, object.getvNIM());
            st.setString(4, object.getvKodeMTK());
            rs = st.executeQuery();
            if (!rs.next()){
                st = null;
                st = connection.prepareStatement("INSERT INTO detil_krs(TA, Semester, NIM, KodeMTK) VALUES(?,?,?,?)");
                st.setString(1, object.getvTA());
                st.setString(2, object.getvSemester());
                st.setString(3, object.getvNIM());
                st.setString(4, object.getvKodeMTK());
                st.executeUpdate();
            }
            rs.close();
            st.close(); 
        }
        catch(Exception e){
            e.printStackTrace();
        }    
    }



    public void deleteKRS(String ta, String semester, String nim, String kodemtk) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = connection.prepareStatement("SELECT * FROM detil_krs where TA=? and Semester=? and NIM=? and kodeMTK=?");
            st.setString(1, ta);
            st.setString(2, semester);
            st.setString(3, nim);
            st.setString(4, kodemtk);
            rs = st.executeQuery();
                 System.out.println("2");
           
            if (rs.next()){
                System.out.println("3");
                st = null;
                st = connection.prepareStatement("DELETE FROM detil_krs where TA=? and Semester=? and NIM=? and kodeMTK=?");
                st.setString(1, ta);
                st.setString(2, semester);
                st.setString(3, nim);
                st.setString(4, kodemtk);
                st.executeUpdate();
                }
           
            else{
                st = null;
                st = connection.prepareStatement("DELETE FROM krs where TA=? and Semester=? and NIM=?");
                st.setString(1, ta);
                st.setString(2, semester);
                st.setString(3, nim);
                st.executeUpdate();
            }
            rs.close();
            st.close(); 
        }
        catch(Exception e){
            e.printStackTrace();
        }    
    }    
    

    

    
 
    
 


    @Override
    public void update(varKRS Object) {
        
    }

    @Override
    public void delete(String Fieldkey) {
        
    }

    @Override
    public List<varKRS> getAll() {
        return null;
    }

    @Override
    public List<varKRS> getCari(String key) {
       return null;
    }
}
