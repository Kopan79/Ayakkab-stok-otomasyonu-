package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.IsteMySQL.Util.VeritabaniUtil;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class persEkleController {

    Connection conn = null;
    PreparedStatement sorgu = null;
    ResultSet getirilen = null;
    String sql;

    public persEkleController() {
        conn = VeritabaniUtil.Connect();
    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane anc5;

    @FXML
    private DatePicker bday;
    
    @FXML
    private Button btn_tmz;

    @FXML
    private Button btn_Geri;

    @FXML
    private Button btn_Pkaydet;
    
    @FXML
    private Button btn_gncll;

    @FXML
    private Button btn_sil;

    @FXML
    private TextField txtID;
    
    @FXML
    private TextField txtisim;

    @FXML
    private TextField txtkuladi;

    @FXML
    private TextField txtsifre;

    @FXML
    private TextField txtsoyisim;

    @FXML
    private TextField txtsube;


    @FXML
    void btn_tmz_Click(ActionEvent event) {
    	txtID.clear();
    	txtisim.clear();
    	txtkuladi.clear();
    	txtsifre.clear();
    	txtsoyisim.clear();
    	txtsube.clear();
    	bday.setValue(null);
    	

    }
    
    @FXML
    void btn_Geri_Click(ActionEvent event) {
        try {
            Stage stage1 = new Stage();
            AnchorPane pane1 = (AnchorPane)FXMLLoader.load(getClass().getResource("dersProjesi.fxml"));
            Scene scene = new Scene(pane1);
            stage1.setScene(scene);
            stage1.show();
            Stage stage = (Stage) btn_Geri.getScene().getWindow();
            stage.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private boolean IdDoluMu() {
        return !txtID.getText().trim().isEmpty();
    }

    private boolean enAzBirAlanDoluMu() {
        return !txtkuladi.getText().trim().isEmpty() || 
               !txtsifre.getText().trim().isEmpty() || 
               !txtisim.getText().trim().isEmpty() || 
               !txtsoyisim.getText().trim().isEmpty() || 
               bday.getValue() != null || 
               !txtsube.getText().trim().isEmpty();
    }

    @FXML
    void btn_gncll_Click(ActionEvent event) {
        if (!IdDoluMu() || !enAzBirAlanDoluMu()) {
            System.out.println("Lütfen ID girin ve en az bir bilgiyi doldurun.");
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("Güncelleme yapılamadı. Güncellemek istediğiniz personelin ID numarasını ve en az bir yeni bilgiyi giriniz.");
            alert.showAndWait();
            return;
        }

        try {
            String sql = "UPDATE login SET ";
            boolean first = true;

            if (!txtkuladi.getText().trim().isEmpty()) {
                sql += "kul_ad = ?";
                first = false;
            }

            if (!txtsifre.getText().trim().isEmpty()) {
                if (!first) sql += ", ";
                sql += "sifre = ?";
                first = false;
            }

            if (!txtisim.getText().trim().isEmpty()) {
                if (!first) sql += ", ";
                sql += "isim = ?";
                first = false;
            }

            if (!txtsoyisim.getText().trim().isEmpty()) {
                if (!first) sql += ", ";
                sql += "s_isim = ?";
                first = false;
            }

            if (bday.getValue() != null) {
                if (!first) sql += ", ";
                sql += "d_tarihi = ?";
                first = false;
            }

            if (!txtsube.getText().trim().isEmpty()) {
                if (!first) sql += ", ";
                sql += "magaza = ?";
            }

            sql += " WHERE k__ID = ?";

            sorgu = conn.prepareStatement(sql);

            int paramIndex = 1;

            if (!txtkuladi.getText().trim().isEmpty()) {
                sorgu.setString(paramIndex++, txtkuladi.getText().trim());
            }

            if (!txtsifre.getText().trim().isEmpty()) {
                sorgu.setString(paramIndex++, txtsifre.getText().trim());
            }

            if (!txtisim.getText().trim().isEmpty()) {
                sorgu.setString(paramIndex++, txtisim.getText().trim());
            }

            if (!txtsoyisim.getText().trim().isEmpty()) {
                sorgu.setString(paramIndex++, txtsoyisim.getText().trim());
            }

            if (bday.getValue() != null) {
                sorgu.setString(paramIndex++, bday.getValue().toString());
            }

            if (!txtsube.getText().trim().isEmpty()) {
                sorgu.setString(paramIndex++, txtsube.getText().trim());
            }

            sorgu.setString(paramIndex, txtID.getText().trim());

            int affectedRows = sorgu.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Personel bilgileri başarıyla güncellendi");
                
                Alert alert1 = new Alert(AlertType.INFORMATION);
                alert1.setTitle("Message");
                alert1.setHeaderText(null);
                alert1.setContentText("Personel bilgileri başarıyla güncellendi");
                alert1.showAndWait();

                txtID.clear();
            	txtisim.clear();
            	txtkuladi.clear();
            	txtsifre.clear();
            	txtsoyisim.clear();
            	txtsube.clear();
            	bday.setValue(null);
            } else {
                System.out.println("Güncelleme yapılamadı. Belirtilen kimlik numarasına sahip veri bulunamadı.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void btn_sil_Click(ActionEvent event) {
         sql = "DELETE FROM login WHERE k__ID = ?";

        try {
            sorgu = conn.prepareStatement(sql);
            sorgu.setString(1, txtID.getText().trim());

            int affectedRows = sorgu.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Personel Başarıyla Silindi");
                Alert alert1 = new Alert(AlertType.INFORMATION);
                alert1.setTitle("Message");
                alert1.setHeaderText(null);
                alert1.setContentText("Personel Başarıyla Silindi");
                alert1.showAndWait();
                txtID.clear();
            	txtisim.clear();
            	txtkuladi.clear();
            	txtsifre.clear();
            	txtsoyisim.clear();
            	txtsube.clear();
            	bday.setValue(null);
            	
            } else {
                System.out.println("Silme Yapılamadı. Silmek istediğiniz personelin ID numarasını giriniz.");
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText(null);
                alert.setContentText("Silme Yapılamadı. Silmek istediğiniz personelin ID numarasını giriniz.");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private boolean bilgilerDoluMu() {
        return !txtID.getText().trim().isEmpty() && 
               !txtkuladi.getText().trim().isEmpty() && 
               !txtsifre.getText().trim().isEmpty() && 
               !txtisim.getText().trim().isEmpty() && 
               !txtsoyisim.getText().trim().isEmpty() && 
               bday.getValue() != null && 
               !txtsube.getText().trim().isEmpty();
    }
    

 // ID'nin veritabanında zaten kullanılıp kullanılmadığını kontrol eden metod
 private boolean isIDAlreadyUsed(String id) {
     String sql = "SELECT COUNT(*) FROM login WHERE k__ID = ?";
     try {
         PreparedStatement sorgu = conn.prepareStatement(sql);
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
    void btn_Pkaydet_Click(ActionEvent event) {
        if (!bilgilerDoluMu()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("Lütfen Tüm Bilgilerinizi Giriniz.");
            alert.showAndWait();
            return;
        }
        
     // ID'nin veritabanında daha önce kullanılıp kullanılmadığını kontrol et
        if (isIDAlreadyUsed(txtID.getText().trim())) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("Bu ID numarası zaten kullanılmış. Lütfen başka bir ID numarası giriniz.");
            alert.showAndWait();
            return;
        }

        sql = "INSERT INTO login (k__ID, kul_ad, sifre, isim, s_isim, d_tarihi, magaza) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            sorgu = conn.prepareStatement(sql);

            sorgu.setString(1, txtID.getText().trim());
            sorgu.setString(2, txtkuladi.getText().trim());
            sorgu.setString(3, txtsifre.getText().trim());
            sorgu.setString(4, txtisim.getText().trim());
            sorgu.setString(5, txtsoyisim.getText().trim());

            LocalDate dogumTarihi = bday.getValue();
            if (dogumTarihi != null) {
                sorgu.setString(6, dogumTarihi.toString());
            } else {
                System.out.println("Lütfen geçerli bir doğum tarihi seçiniz.");
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText(null);
                alert.setContentText("Lütfen geçerli bir doğum tarihi seçiniz.");
                alert.showAndWait();
                return;
            }

            sorgu.setString(7, txtsube.getText().trim());

            sorgu.executeUpdate();
            System.out.println("Personel Başarıyla Kaydedildi");
            Alert alert1 = new Alert(AlertType.INFORMATION);
            alert1.setTitle("Message");
            alert1.setHeaderText(null);
            alert1.setContentText("Personel Başarıyla Kaydedildi");
            alert1.showAndWait();

            try {
                Stage stage1 = new Stage();
                AnchorPane pane1 = (AnchorPane)FXMLLoader.load(getClass().getResource("dersProjesi.fxml"));
                Scene scene = new Scene(pane1);
                stage1.setScene(scene);
                stage1.show();
                Stage stage = (Stage) btn_Pkaydet.getScene().getWindow();
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
       
    }
}
