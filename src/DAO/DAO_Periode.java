/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import Koneksi.Database;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import Model.varPeriode;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
/**
 *
 * @author l
 */
public class DAO_Periode implements DAO_Interface<varPeriode>{
    Connection connection;
    public DAO_Periode(){
        connection = Database.KoneksiDB();
    }
    
    String INSERT = "INSERT INTO periode(TA, Semester) VALUES(?,?)";
    String UPDATE = "UPDATE periode set Semester=? WHERE TA=?";
    String DELETE = "DELETE FROM periode WHERE TA=?";
    String SELECT = "SELECT * FROM periode ";
    String CARI = "SELECT * FROM periode WHERE TA=?";
    
    @Override
    public void insert(varPeriode object) {
        PreparedStatement st = null;
        try{
            st = connection.prepareStatement(CARI);
            st.setString(1, object.getvTA()); 
            ResultSet rs = st.executeQuery();
            if (rs.next()){
                JOptionPane.showMessageDialog(null, "Data sudah pernah disimpan");
            }
            else{
                st = null;
                st= connection.prepareStatement(INSERT);
                st.setString(1,object.getvTA());
                st.setString(2,object.getvSemester());
                st.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data Berhasil di simpan");
            }
            st.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }    
    }

    @Override
    public void update(varPeriode object) {
           PreparedStatement st = null;
        try{
                st = null;
                st= connection.prepareStatement(UPDATE);
                st.setString(1,object.getvSemester());
                st.setString(2,object.getvTA());
                st.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data Berhasil di ubah");
                st.close();
            }
        catch(Exception e){
            e.printStackTrace();
        }    
    }

    @Override
    public void delete(String ta) {
          PreparedStatement st = null;
        try{
                st = null;
                st= connection.prepareStatement(DELETE);
                st.setString(1, ta);
                st.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data Berhasil di hapus");
                st.close();
            }
        catch(Exception e){
            e.printStackTrace();
        }    
    }    
    

    @Override
    public List<varPeriode> getAll() {
        List<varPeriode> list = null;
        PreparedStatement st = null;
        try{
                st = null;
                list = new ArrayList<varPeriode>();
                st= connection.prepareStatement(SELECT);
                ResultSet rs = st.executeQuery();
                while (rs.next()){
                    varPeriode objPrd = new varPeriode();
                    objPrd.setvTA(rs.getString("TA"));
                    objPrd.setvSemester(rs.getString("Semester"));
                    list.add(objPrd);
                }
                st.close();
            }
        catch(Exception e){
            e.printStackTrace();
        }    
        return list;
    }

    @Override
    public List<varPeriode> getCari(String key) {
        List<varPeriode> list = null;
        PreparedStatement st = null;
        try{
                st = null;
                list = new ArrayList<varPeriode>();
                st = connection.prepareStatement(SELECT);
                st.setString(1, "%"+key+"%");
                ResultSet rs = st.executeQuery();
                while (rs.next()){
                    varPeriode objPrd = new varPeriode();
                    objPrd.setvTA(rs.getString("TA"));
                    objPrd.setvSemester(rs.getString("Semester"));
                    list.add(objPrd);
                }
                st.close();
            }
        catch(Exception e){
            e.printStackTrace();
        }    
        return list;
    }
    
}
