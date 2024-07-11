package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.IsteMySQL.Util.VeritabaniUtil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class urunEkleController {
	

    Connection conn = null;
    PreparedStatement sorgu = null;
    ResultSet getirilen = null;
    String sql;

    public urunEkleController() {
        conn = VeritabaniUtil.Connect();
    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane anc4;

    @FXML
    private Button btn_Gerı;

    @FXML
    private Button btn_Ukaydet;
    
    @FXML
    private Button btn_temizle;

    @FXML
    private ComboBox<String> combo1;

    @FXML
    private DatePicker date;

    @FXML
    private TextField txtAdet;

    @FXML
    private TextField txtIDu;

    @FXML
    private TextField txtNo;

    @FXML
    private TextField txtRenk;

    @FXML
    private TextField txtSatıldı;

    @FXML
    private TextField txtfıyat;
    
    @FXML
    private Button btn_Usil;
    
    @FXML
    private Button btn_gncll;
    
    
    private boolean IdDoluMu() {
        return !txtIDu.getText().trim().isEmpty();
    }

    private boolean enAzBirAlanDoluMu() {
        return !txtRenk.getText().trim().isEmpty() || 
               !txtNo.getText().trim().isEmpty() || 
               !txtAdet.getText().trim().isEmpty() || 
               !txtfıyat.getText().trim().isEmpty() || 
               date.getValue() != null || 
               !txtSatıldı.getText().trim().isEmpty();
    }

    @FXML
    void btn_gncll_Click(ActionEvent event) {
        if (!IdDoluMu() || !enAzBirAlanDoluMu()) {
            System.out.println("Lütfen ID girin ve en az bir ürün bilgisi girin.");
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("Lütfen ID girin ve en az bir ürün bilgisi girin.");
            alert.showAndWait();
            return;
        }

        try {
            String sql = "UPDATE urun SET ";
            boolean first = true;

            if (!txtRenk.getText().trim().isEmpty()) {
                sql += "renk = ?";
                first = false;
            }

            if (!txtNo.getText().trim().isEmpty()) {
                if (!first) sql += ", ";
                sql += "numara = ?";
                first = false;
            }

            if (!txtAdet.getText().trim().isEmpty()) {
                if (!first) sql += ", ";
                sql += "adet = ?";
                first = false;
            }

            if (!txtfıyat.getText().trim().isEmpty()) {
                if (!first) sql += ", ";
                sql += "fiyat = ?";
                first = false;
            }

            if (date.getValue() != null) {
                if (!first) sql += ", ";
                sql += "tarih = ?";
                first = false;
            }

            if (!txtSatıldı.getText().trim().isEmpty()) {
                if (!first) sql += ", ";
                sql += "satildi = ?";
            }

            sql += " WHERE urunID = ?";

            sorgu = conn.prepareStatement(sql);

            int paramIndex = 1;

            if (!txtRenk.getText().trim().isEmpty()) {
                sorgu.setString(paramIndex++, txtRenk.getText().trim());
            }

            if (!txtNo.getText().trim().isEmpty()) {
                sorgu.setString(paramIndex++, txtNo.getText().trim());
            }

            if (!txtAdet.getText().trim().isEmpty()) {
                sorgu.setString(paramIndex++, txtAdet.getText().trim());
            }

            if (!txtfıyat.getText().trim().isEmpty()) {
                sorgu.setString(paramIndex++, txtfıyat.getText().trim());
            }

            if (date.getValue() != null) {
                sorgu.setString(paramIndex++, date.getValue().toString());
            }

            if (!txtSatıldı.getText().trim().isEmpty()) {
                sorgu.setString(paramIndex++, txtSatıldı.getText().trim());
            }

            sorgu.setString(paramIndex, txtIDu.getText().trim());

            int affectedRows = sorgu.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Ürün bilgileri başarıyla güncellendi");
                
                Alert alert1 = new Alert(AlertType.INFORMATION);
                alert1.setTitle("Message");
                alert1.setHeaderText(null);
                alert1.setContentText("Ürün bilgileri başarıyla güncellendi");
                alert1.showAndWait();

                txtIDu.clear();
                txtRenk.clear();
                txtNo.clear();
                txtAdet.clear();
                txtfıyat.clear();
                txtSatıldı.clear();
                date.setValue(null);
            } else {
                System.out.println("Güncelleme yapılamadı. Belirtilen kimlik numarasına sahip ürün bulunamadı.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    



    
    
    @FXML
    void btn_Usil_Click(ActionEvent event) {
    	 sql = "DELETE FROM urun WHERE urunID = ?";

         try {
             sorgu = conn.prepareStatement(sql);
             sorgu.setString(1, txtIDu.getText().trim());

             int affectedRows = sorgu.executeUpdate();

             if (affectedRows > 0) {
                 System.out.println("Ürün Başarıyla Silindi");
                 Alert alert1 = new Alert(AlertType.INFORMATION);
                 alert1.setTitle("Message");
                 alert1.setHeaderText(null);
                 alert1.setContentText("Ürün Başarıyla Silindi");
                 alert1.showAndWait();
                 
                 txtIDu.clear();
	             txtRenk.clear();
	             txtNo.clear();
	             txtAdet.clear();
	             txtfıyat.clear();
	             txtSatıldı.clear();
	             date.setValue(null);
	             combo1.setValue(null);
             	
             	
             } else {
                 System.out.println("Silme Yapılamadı. Silmek istediğiniz ürünün ID numarasını giriniz.");
                 Alert alert = new Alert(AlertType.ERROR);
                 alert.setTitle("ERROR");
                 alert.setHeaderText(null);
                 alert.setContentText("Silme Yapılamadı. Silmek istediğiniz ürünün ID numarasını giriniz.");
                 alert.showAndWait();
             }
         } catch (SQLException e) {
             System.out.println(e.getMessage());
             e.printStackTrace();
         }
    	
    	

    }
    
    
    @FXML
    void btn_temizle(ActionEvent event) {
    	txtIDu.clear();
    	txtSatıldı.clear();
    	txtRenk.clear();
    	txtNo.clear();
    	txtfıyat.clear();
    	txtAdet.clear();
    	date.setValue(null);
    	combo1.setValue(null);
    	
    }
    

    @FXML
    void btn_Gerı_Click(ActionEvent event) {
    	try {
    		Stage stage1 = new Stage();
			AnchorPane pane1 = (AnchorPane)FXMLLoader.load(getClass().getResource("urunler.fxml"));
			Scene scene = new Scene(pane1);
			stage1.setScene(scene);
			stage1.show();
			Stage stage = (Stage) btn_Gerı.getScene().getWindow(); 
		    stage.close();
		} catch(Exception e) {
			e.printStackTrace();
		}

    }

    
    private boolean bilgilerDoluMu() {
        return !txtIDu.getText().trim().isEmpty() && 
        		!combo1.getSelectionModel().isEmpty() && combo1.getValue() != null && 
               !txtRenk.getText().trim().isEmpty() && 
               !txtNo.getText().trim().isEmpty() && 
               !txtAdet.getText().trim().isEmpty() && 
               date.getValue() != null && 
               !txtfıyat.getText().trim().isEmpty()&&
               !txtSatıldı.getText().trim().isEmpty();
    }
    

 // ID'nin veritabanında zaten kullanılıp kullanılmadığını kontrol eden metod
 private boolean isIDAlreadyUsedu(String id) {
     String sql = "SELECT COUNT(*) FROM urun WHERE urunID  = ?";
     try {
         sorgu = conn.prepareStatement(sql);
         sorgu.setString(1, id);
         ResultSet rs = sorgu.executeQuery();
         if (rs.next()) {
             return rs.getInt(1) > 0;
         }
     } catch (SQLException e) {
         e.printStackTrace();
     }
     return false;
 }
    
    @FXML
    void btn_Ukaydet_Click(ActionEvent event) {
    	 if (!bilgilerDoluMu()) {
             Alert alert = new Alert(AlertType.ERROR);
             alert.setTitle("ERROR");
             alert.setHeaderText(null);
             alert.setContentText("Lütfen ürünün tüm bilgilerinizi giriniz.");
             alert.showAndWait();
             return;
         }
         
      // ID'nin veritabanında daha önce kullanılıp kullanılmadığını kontrol et
         if (isIDAlreadyUsedu(txtIDu.getText().trim())) {
             Alert alert = new Alert(AlertType.ERROR);
             alert.setTitle("ERROR");
             alert.setHeaderText(null);
             alert.setContentText("Bu ID numaralı ürün zaten var.");
             alert.showAndWait();
             return;
         }                          
         sql = "INSERT INTO urun ( urunID, kategori, renk ,satildi, numara, adet, fiyat, tarih\r\n"+ ") VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
         try {
             sorgu = conn.prepareStatement(sql);

             
             
             sorgu.setString(1, txtIDu.getText().trim());
             
             if (combo1.getValue() != null) {
            	    // Combobox'tan bir seçim yapılmış
            	    String secilenDeger = combo1.getValue().toString();
            	    // Geri kalan işlemleri yapabilirsiniz
            	    sorgu.setString(2, secilenDeger);
            	} else {
            	    // Combobox'tan bir seçim yapılmamış
            	    System.out.println("Lütfen bir değer seçiniz.");
            	    // veya bir uyarı mesajı gösterebilirsiniz
            	}
             sorgu.setString(3, txtRenk.getText().trim());
             sorgu.setString(4, txtSatıldı.getText().trim());
             sorgu.setString(5, txtNo.getText().trim());
             sorgu.setString(6, txtAdet.getText().trim());
             sorgu.setString(7, txtfıyat.getText().trim());
                               
               // index sayılarını düzenle 
             //hatayı yok edip devam et 
             
             LocalDate eklemeTarihi = date.getValue();
             if (eklemeTarihi != null) {
                 sorgu.setString(8, eklemeTarihi.toString());

             } else {
                 
             }


             sorgu.executeUpdate();
             System.out.println("Ürün Başarıyla eklendi.");
             Alert alert1 = new Alert(AlertType.INFORMATION);
             alert1.setTitle("Message");
             alert1.setHeaderText(null);
             alert1.setContentText("Ürün Başarıyla eklendi.");
             alert1.showAndWait();

             try {
                 Stage stage1 = new Stage();
                 AnchorPane pane1 = (AnchorPane)FXMLLoader.load(getClass().getResource("urunler.fxml"));
                 Scene scene = new Scene(pane1);
                 stage1.setScene(scene);
                 stage1.show();
                 Stage stage = (Stage) btn_Ukaydet.getScene().getWindow();
                 stage.close();
             } catch(Exception e) {
                 e.printStackTrace();
             }
         } catch (SQLException e) {
             System.out.println(e.getMessage());
             e.printStackTrace();
         }

         
}
    	

    

    @FXML
    void initialize() {
    	 
    	ObservableList<String> items = FXCollections.observableArrayList(
    			   "GÜNLÜK ",
    			    "SPOR",
    			    "TOPUKLU" ,
    			    "BABET",
    			    "SANDALET",
    			    "TERLİK");
        combo1.setItems(items);

    	
    }
}



            

    
