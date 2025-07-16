/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.DAO_Interface;
import DAO.DAO_Mahasiswa;
import Model.varMahasiswa;
import View.FrmMahasiswa;
import java.util.List;
import java.awt.Color;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author l
 */
public class Controller_Mahasiswa {
    FrmMahasiswa form;
    DAO_Interface<varMahasiswa> model;
    List<varMahasiswa> list;
    String[] header;
    
    //deklarasi kontruktor
    public Controller_Mahasiswa(FrmMahasiswa form){
    this.form = form;
    model = new DAO_Mahasiswa();
    list = model.getAll();
    header = new String[]{"NIM", "Nama", "Alamat"};
    form.getTblMahasiswa().setShowGrid(true);
    form.getTblMahasiswa().setShowVerticalLines(true);
    form.getTblMahasiswa().setGridColor(Color.blue);
    }
    
    public void reset(){
        form.getTxtNIM().setText("");
        form.getTxtNama().setText("");
        form.getTxaAlamat().setText("");
        form.getTxtNIM().requestFocus();
        isiTabel();
    }

    public void isiTabel() {
        list = model.getAll();
        DefaultTableModel tblModel = new DefaultTableModel (new Object [][]{},header){
            public boolean isCellEditable(int rowIndex, int columnIndex){
            return false;
            }
        };
        Object[] data = new Object[header.length];
        for(varMahasiswa objMhs : list){
            data[0]=objMhs.getvNIM();
            data[1]=objMhs.getvNama();
            data[2]=objMhs.getvAlamat();
            tblModel.addRow(data);
        }
        form.getTblMahasiswa().setModel(tblModel);
    }
    
    public void isiField(int row){
        form.getTxtNIM().setText(list.get(row).getvNIM());
        form.getTxtNama().setText(list.get(row).getvNama());
        form.getTxaAlamat().setText(list.get(row).getvAlamat());
    }
   
    public void insert(){
        // membuat objek
        varMahasiswa objMhs = new varMahasiswa();
        
        // mengisi variabel objMhs dengan data yang dientri
        objMhs.setvNIM(form.getTxtNIM().getText());
        objMhs.setvNama(form.getTxtNama().getText());
        objMhs.setvAlamat(form.getTxaAlamat().getText());
        
        // menjalankan method insert yang ada di DAO_Mahasiswa
        model.insert(objMhs);
    }
    
    public void update(){
        // membuat objek
        varMahasiswa objMhs = new varMahasiswa();
        
        // mengisi variabel objMhs dengan data yang dientri
        objMhs.setvNIM(form.getTxtNIM().getText());
        objMhs.setvNama(form.getTxtNama().getText());
        objMhs.setvAlamat(form.getTxaAlamat().getText());
        
        // menjalankan method insert yang ada di DAO_Mahasiswa
        model.update(objMhs);
    }
    
    public void delete(){
     if(!form.getTxtNIM().getText().trim().isEmpty()){
         // mengisi variabel objMhs dengan data yang dientri
         String nim = form.getTxtNIM().getText();
         
         // menjalankan method update yang ada di DAO_Mahasiswa
         model.delete(nim);
     }
     else{
         JOptionPane.showMessageDialog(form, "Pilih data yang akan di hapus");
        }
    }
    
    public void isiTabelCari() {
        list = model.getCari(form.getTxtNIM().getText().trim());
        DefaultTableModel tblModel = new DefaultTableModel (new Object [][]{},header);
        Object[] data = new Object[header.length];
        for(varMahasiswa objMhs : list){
            data[0]=objMhs.getvNIM();
            data[1]=objMhs.getvNama();
            data[2]=objMhs.getvAlamat();
            tblModel.addRow(data);
        }
        form.getTblMahasiswa().setModel(tblModel);
    }
    
}

