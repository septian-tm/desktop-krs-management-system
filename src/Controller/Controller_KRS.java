/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;
import DAO.DAO_Interface;
import DAO.DAO_KRS;
import Model.varKRS;
import View.FrmKRSS;
import java.util.List;
import java.awt.Color;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author l
 */
public class Controller_KRS {
    FrmKRSS form;
    DAO_Interface<varKRS> model;
    DAO_KRS model_internal;
    List<varKRS> list;
    String[] header;
    
    public Controller_KRS(FrmKRSS form){
        this.form = form;
        model = new DAO_KRS();
        model_internal = new DAO_KRS();
        list = model.getAll();
        header = new String[]{"No.", "Kode", "Nama Matakuliah", "SKS", "Kode Prasyarat"};
        form.getTblKRS().setShowGrid(true);
        form.getTblKRS().setShowVerticalLines(true);
        form.getTblKRS().setGridColor(Color.blue);
    }
    
    public void reset(){
        SimpleDateFormat tgl = new SimpleDateFormat("yyyy-MM-dd");
        form.getCboTA().setSelectedItem("Pilih");
        form.getCboSemester().setSelectedItem("Pilih");
        form.getTxtTglKRS().setText(String.valueOf(tgl.format(new Date())));
        form.getTxtNIM().setText("");
        form.getTxtNama().setText("");
        form.getTxtKodeMtk().setText("");
        form.getTxtNamaMtk().setText("");
        form.getTxtSKS().setText("");
        form.getTxtKodePrasyarat().setText("");
        isicomboTahunAjaran();
        isicomboSemester();
        form.getTxtTglKRS().setEditable(false);
        form.getTxtNIM().setEditable(true);
        form.getTxtNIM().requestFocus();
        isiTabel();
    }
    
    public void resetDetil(){
        form.getTxtKodeMtk().setText("");
        form.getTxtNamaMtk().setText("");
        form.getTxtSKS().setText("");
        form.getTxtKodePrasyarat().setText("");
        form.getTxtKodeMtk().requestFocus();
        isiTabel();
    }
    
    public void isicomboTahunAjaran(){
        form.getCboTA().removeAllItems();
        form.getCboTA().addItem("-Pilih-");
        for (varKRS b : model_internal.isicomboTA()){
            form.getCboTA().addItem(b.getvTA());
        }
    }
    
    public void isicomboSemester(){
        form.getCboSemester().removeAllItems();
        form.getCboSemester().addItem("-Pilih-");
        for (varKRS b : model_internal.isicomboSMT()){
            form.getCboSemester().addItem(b.getvSemester());
        }
    }    
    
    public void tampilNmMahasiswa(){
        for (varKRS b : model_internal.getNmMhs(form.getTxtNIM().getText())){
            form.getTxtNama().setText(String.valueOf(b.getvNama()));
        }
    }    
    
    public void tampilDataMatakuliah(){
        for (varKRS b : model_internal.getDataMtk(form.getTxtKodeMtk().getText())){
            form.getTxtNamaMtk().setText(String.valueOf(b.getvNamaMTK()));
            form.getTxtSKS().setText(String.valueOf(b.getvSKS()));
            form.getTxtKodePrasyarat().setText(String.valueOf(b.getvKodePrasyarat()));
        }
    }    
    
    
    public void isiTabel() { 
        String ta  =""+form.getCboTA().getSelectedItem();
        String semester = ""+form.getCboSemester ().getSelectedItem(); 
        String nim = form.getTxtNIM().getText();
        list = model_internal.getAllDetilKRS (ta, semester, nim);
        DefaultTableModel tblModel = new DefaultTableModel (new Object[][]{}, header) { 
            public boolean isCellEditable (int rowIndex, int columnIndex) {
                return false;
            }
        };
        Object[] data = new Object [header.length];
        int i=1;
        for (varKRS objKRS : list) {
            data[0] = ""+i;
            data[1] = objKRS.getvKodeMTK();
            data[2] = objKRS.getvNamaMTK();
            data[3] = objKRS.getvSKS();
            data[4] = objKRS.getvKodePrasyarat();
            tblModel.addRow(data);
            i++;
        }
        form.getTblKRS().setModel(tblModel);
        int total=0, sks = 0;
        for (i=0; i < tblModel.getRowCount(); i++) {
            sks = Integer.parseInt(String.valueOf(tblModel.getValueAt (i,3).toString())); 
            total = total+sks;
        }
            form.getTxtTotalSKS ().setText(""+total);
    }
    
    public void isiField(int row){
        form.getTxtKodeMtk().setText(list.get(row).getvKodeMTK());
        form.getTxtNamaMtk().setText(list.get(row).getvNamaMTK());
        form.getTxtSKS().setText(""+list.get(row).getvSKS());
        form.getTxtKodePrasyarat().setText(list.get(row).getvKodePrasyarat());
    }
    
    public void insert(){
        varKRS objKRS = new varKRS();
        
        objKRS.setvTA(""+form.getCboTA().getSelectedItem());
        objKRS.setvSemester(""+form.getCboSemester().getSelectedItem());
        objKRS.setvNIM(form.getTxtNIM().getText());
        objKRS.setvTglKRS(form.getTxtTglKRS().getText());
        objKRS.setvKdMTK(form.getTxtKodeMtk().getText());
        model.insert(objKRS);
    }
    
    public void delete(){
        if(!form.getTxtNIM().getText().trim().isEmpty()){
            String ta =""+form.getCboTA().getSelectedItem();
            String semester =""+form.getCboSemester().getSelectedItem();
            String nim =form.getTxtNIM().getText();
            String kdmtk =form.getTxtKodeMtk().getText();
                            System.out.println("1");

            model_internal.deleteKRS(ta, semester, nim, kdmtk);
        }
        else{
            JOptionPane.showMessageDialog(form, "Pilih data yang akan dihapus");
        }
    }
}

