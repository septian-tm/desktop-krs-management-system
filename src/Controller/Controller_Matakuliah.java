/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.DAO_Interface;
import DAO.DAO_Matakuliah;
import Model.varMatakuliah;
import View.FrmMatakuliah;
import java.util.List;
import java.awt.Color;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author l
 */
public class Controller_Matakuliah {
    FrmMatakuliah form;
    DAO_Interface<varMatakuliah> model;
    List<varMatakuliah> list;
    String[] header;
    
    //deklarasi kontruktor
    public Controller_Matakuliah(FrmMatakuliah form){
    this.form = form;
    model = new DAO_Matakuliah();
    list = model.getAll();
    header = new String[]{"KodeMTK", "NamaMTK", "SKS", "KodePrasyarat"};
    form.getTblMatakuliah().setShowGrid(true);
    form.getTblMatakuliah().setShowVerticalLines(true);
    form.getTblMatakuliah().setGridColor(Color.blue);
    }
    
    public void reset(){
        form.getTxtKodeMTK().setText("");
        form.getTxtNamaMTK().setText("");
        form.getTxtSKS().setText("");
        form.getTxtKodePrasyarat().setText("");
        form.getTxtKodeMTK().requestFocus();
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
        for(varMatakuliah objMtk : list){
            data[0]=objMtk.getvKodeMTK();
            data[1]=objMtk.getvNamaMTK();
            data[2]=objMtk.getvSKS();
            data[3]=objMtk.getvKodePrasyarat();
            tblModel.addRow(data);
        }
        form.getTblMatakuliah().setModel(tblModel);
    }
    
    public void isiField(int row){
        form.getTxtKodeMTK().setText(list.get(row).getvKodeMTK());
        form.getTxtNamaMTK().setText(list.get(row).getvNamaMTK());
        form.getTxtSKS().setText(Integer.toString(list.get(row).getvSKS()));
        form.getTxtKodePrasyarat().setText(list.get(row).getvKodePrasyarat());
    }
   
    public void insert(){
        // membuat objek
        varMatakuliah objMtk = new varMatakuliah();
        
        // mengisi variabel objMhs dengan data yang dientri
        objMtk.setvKodeMTK(form.getTxtKodeMTK().getText());
        objMtk.setvNamaMTK(form.getTxtNamaMTK().getText());
        objMtk.setvSKS (Integer.parseInt(form.getTxtSKS().getText()));
        objMtk.setvKodePrasyarat(form.getTxtKodePrasyarat().getText());
        // menjalankan method insert yang ada di DAO_Mahasiswa
        model.insert(objMtk);
    }
    
    public void update(){
        // membuat objek
        varMatakuliah objMtk = new varMatakuliah();
        
        // mengisi variabel objMhs dengan data yang dientri
        objMtk.setvKodeMTK(form.getTxtKodeMTK().getText());
        objMtk.setvNamaMTK(form.getTxtNamaMTK().getText());
        objMtk.setvSKS (Integer.parseInt(form.getTxtSKS().getText()));
        objMtk.setvKodePrasyarat(form.getTxtKodePrasyarat().getText());
        
        // menjalankan method insert yang ada di DAO_Mahasiswa
        model.update(objMtk);
    }
    
    public void delete(){
     if(!form.getTxtKodeMTK().getText().trim().isEmpty()){
         // mengisi variabel objMhs dengan data yang dientri
         String kodemtk = form.getTxtKodeMTK().getText();
         
         // menjalankan method update yang ada di DAO_Mahasiswa
         model.delete(kodemtk);
     }
     else{
         JOptionPane.showMessageDialog(form, "Pilih data yang akan di hapus");
        }
    }
    
    public void isiTabelCari() {
        list = model.getCari(form.getTxtKodeMTK().getText().trim());
        DefaultTableModel tblModel = new DefaultTableModel (new Object [][]{},header);
        Object[] data = new Object[header.length];
        for(varMatakuliah objMtk : list){
            data[0]=objMtk.getvKodeMTK();
            data[1]=objMtk.getvNamaMTK();
            data[2]=objMtk.getvSKS();
            data[3]=objMtk.getvKodePrasyarat();
            tblModel.addRow(data);
        }
        form.getTblMatakuliah().setModel(tblModel);
    }
    
}