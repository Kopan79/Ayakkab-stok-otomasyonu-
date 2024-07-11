package application;

//import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class bulunanUrunController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label LabelID;

    @FXML
    private Label Labeladet;

    @FXML
    private Label Labelbdn;

    @FXML
    private Label Labelfiyat;

    @FXML
    private Label Labelrenk;

    @FXML
    private Label Labelsatildi;

    @FXML
    private Label Labeltur;

    @FXML
    private Button btn_Geri;

    @FXML
    private Button btn_Urara;
    

   	
   

    @FXML
    void btn_Geri_Click(ActionEvent event) {
    	try {
    		Stage stage1 = new Stage();
			AnchorPane pane1 = (AnchorPane)FXMLLoader.load(getClass().getResource("urunler.fxml"));
			Scene scene = new Scene(pane1);
			stage1.setScene(scene);
			stage1.show();
			Stage stage = (Stage) btn_Geri.getScene().getWindow(); 
		    stage.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
    }

    @FXML
    void btn_Urara_Click(ActionEvent event) {
    	try {
    		Stage stage1 = new Stage();
			AnchorPane pane1 = (AnchorPane)FXMLLoader.load(getClass().getResource("urunAra.fxml"));
			Scene scene = new Scene(pane1);
			stage1.setScene(scene);
			stage1.show();
			Stage stage = (Stage) btn_Urara.getScene().getWindow(); 
		    stage.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
    }

    public void setUrun(ResultSet urun) {
        try {
            // Veritabanından gelen verileri kontrol edip Label bileşenlerine atıyoruz
            LabelID.setText(String.valueOf(urun.getInt("urunID")));
            Labeltur.setText(urun.getString("kategori"));
            Labelbdn.setText(String.valueOf(urun.getInt("numara")));
            Labelrenk.setText(urun.getString("renk"));
            Labeladet.setText(String.valueOf(urun.getInt("adet")));
            Labelsatildi.setText(String.valueOf(urun.getInt("satildi")));
            Labelfiyat.setText(String.valueOf(urun.getFloat("fiyat")));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() {
        // İlgili işlemler yapılabilir, ancak burada herhangi bir özellik tanımlanmadı
    }
}
