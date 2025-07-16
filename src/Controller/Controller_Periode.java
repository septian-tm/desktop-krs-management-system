/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.DAO_Interface;
import DAO.DAO_Periode;
import Model.varPeriode;
import View.FrmPeriode;
import java.util.List;
import java.awt.Color;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author l
 */
public class Controller_Periode {
    FrmPeriode form;
    DAO_Interface<varPeriode> model;
    List<varPeriode> list;
    String[] header;
    
    //deklarasi kontruktor
    public Controller_Periode(FrmPeriode form){
    this.form = form;
    model = new DAO_Periode();
    list = model.getAll();
    header = new String[]{"TA", "Semester"};
    form.getTblPeriode().setShowGrid(true);
    form.getTblPeriode().setShowVerticalLines(true);
    form.getTblPeriode().setGridColor(Color.blue);
    }
    
    public void reset(){
        form.getTxtTA().setText("");
        form.getTxtSemester().setText("");
        form.getTxtTA().requestFocus();
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
        for(varPeriode objPrd : list){
            data[0]=objPrd.getvTA();
            data[1]=objPrd.getvSemester();
            tblModel.addRow(data);
        }
        form.getTblPeriode().setModel(tblModel);
    }
    
    public void isiField(int row){
        form.getTxtTA().setText(list.get(row).getvTA());
        form.getTxtSemester().setText(list.get(row).getvSemester());
    }
   
    public void insert(){
        // membuat objek
        varPeriode objPrd = new varPeriode();
        
        // mengisi variabel objMhs dengan data yang dientri
        objPrd.setvTA(form.getTxtTA().getText());
        objPrd.setvSemester(form.getTxtSemester().getText());
        // menjalankan method insert yang ada di DAO_Mahasiswa
        model.insert(objPrd);
    }
    
    public void update(){
        // membuat objek
        varPeriode objPrd= new varPeriode();
        
        // mengisi variabel objMhs dengan data yang dientri
        objPrd.setvTA(form.getTxtTA().getText());
        objPrd.setvSemester(form.getTxtSemester().getText());
      
        // menjalankan method insert yang ada di DAO_Mahasiswa
        model.update(objPrd);
    }
    
    public void delete(){
     if(!form.getTxtTA().getText().trim().isEmpty()){
         // mengisi variabel objMhs dengan data yang dientri
         String ta = form.getTxtTA().getText();
         
         // menjalankan method update yang ada di DAO_Mahasiswa
         model.delete(ta);
     }
     else{
         JOptionPane.showMessageDialog(form, "Pilih data yang akan di hapus");
        }
    }
    
    public void isiTabelCari() {
        list = model.getCari(form.getTxtTA().getText().trim());
        DefaultTableModel tblModel = new DefaultTableModel (new Object [][]{},header);
        Object[] data = new Object[header.length];
        for(varPeriode objPrd : list){
            data[0]=objPrd.getvTA();
            data[1]=objPrd.getvSemester();
            tblModel.addRow(data);
        }
        form.getTblPeriode().setModel(tblModel);
    }
    
}