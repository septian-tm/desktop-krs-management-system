/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import Koneksi.Database;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import Model.varMatakuliah;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
/**
 *
 * @author l
 */
public class DAO_Matakuliah implements DAO_Interface<varMatakuliah>{
    Connection connection;
    public DAO_Matakuliah(){
        connection = Database.KoneksiDB();
    }
    
    String INSERT = "INSERT INTO matakuliah(KodeMTK, NamaMTK, SKS, KodePrasyarat) VALUES(?,?,?,?)";
    String UPDATE = "UPDATE matakuliah set NamaMTK=?, SKS=?, KodePrasyarat=? WHERE KodeMTK=?";
    String DELETE = "DELETE FROM matakuliah WHERE KodeMTK=?";
    String SELECT = "SELECT * FROM matakuliah ";
    String CARI = "SELECT * FROM matakuliah WHERE KodeMTK=?";
    
    @Override
    public void insert(varMatakuliah object) {
        PreparedStatement st = null;
        try{
            st = connection.prepareStatement(CARI);
            st.setString(1, object.getvKodeMTK()); 
            ResultSet rs = st.executeQuery();
            if (rs.next()){
                JOptionPane.showMessageDialog(null, "Data sudah pernah disimpan");
            }
            else{
                st = null;
                st= connection.prepareStatement(INSERT);
                st.setString(1,object.getvKodeMTK());
                st.setString(2,object.getvNamaMTK());
                st.setInt(3,object.getvSKS());
                st.setString(4,object.getvKodePrasyarat());
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
    public void update(varMatakuliah object) {
           PreparedStatement st = null;
        try{
                st = null;
                st= connection.prepareStatement(UPDATE);
                st.setString(1,object.getvNamaMTK());
                st.setInt(2,object.getvSKS());
                st.setString(3,object.getvKodePrasyarat());
                st.setString(4,object.getvKodeMTK());
                st.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data Berhasil di ubah");
                st.close();
            }
        catch(Exception e){
            e.printStackTrace();
        }    
    }

    @Override
    public void delete(String kodemtk) {
          PreparedStatement st = null;
        try{
                st = null;
                st= connection.prepareStatement(DELETE);
                st.setString(1, kodemtk);
                st.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data Berhasil di hapus");
                st.close();
            }
        catch(Exception e){
            e.printStackTrace();
        }    
    }    
    

    @Override
    public List<varMatakuliah> getAll() {
        List<varMatakuliah> list = null;
        PreparedStatement st = null;
        try{
                st = null;
                list = new ArrayList<varMatakuliah>();
                st= connection.prepareStatement(SELECT);
                ResultSet rs = st.executeQuery();
                while (rs.next()){
                    varMatakuliah objMtk = new varMatakuliah();
                    objMtk.setvKodeMTK(rs.getString("KodeMTK"));
                    objMtk.setvNamaMTK(rs.getString("NamaMTK"));
                    objMtk.setvSKS(rs.getInt("SKS"));
                    objMtk.setvKodePrasyarat(rs.getString("KodePrasyarat"));
                    list.add(objMtk);
                }
                st.close();
            }
        catch(Exception e){
            e.printStackTrace();
        }    
        return list;
    }

    @Override
    public List<varMatakuliah> getCari(String key) {
        List<varMatakuliah> list = null;
        PreparedStatement st = null;
        try{
                st = null;
                list = new ArrayList<varMatakuliah>();
                st = connection.prepareStatement(SELECT);
                st.setString(1, "%"+key+"%");
                ResultSet rs = st.executeQuery();
                while (rs.next()){
                    varMatakuliah objMtk = new varMatakuliah();
                    objMtk.setvKodeMTK(rs.getString("KodeMTK"));
                    objMtk.setvNamaMTK(rs.getString("NamaMTK"));
                    objMtk.setvSKS(rs.getInt("SKS"));
                    objMtk.setvKodePrasyarat(rs.getString("KodePrasyarat"));
                    list.add(objMtk);
                }
                st.close();
            }
        catch(Exception e){
            e.printStackTrace();
        }    
        return list;
    }
    
}
