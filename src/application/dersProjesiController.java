package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import com.IsteMySQL.Util.VeritabaniUtil;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class dersProjesiController {
	
	Connection conn = null;
    PreparedStatement sorgu = null; // sorgu ifadesi
    ResultSet getirilen = null;// getirilen değer
    String sql;
	            

	    public dersProjesiController() {

	        conn = VeritabaniUtil.Connect();
	    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane anc1;

    @FXML
    private Button btn_Sil2;

    @FXML
    private Button btn_grs;

    @FXML
    private Button btn_persEk;

    @FXML
    private Label sonuc;

    @FXML
    private TextField txt2;

    @FXML
    private PasswordField ptxt2;
    
    @FXML
    void btn_Sil2_Click(ActionEvent event) {
    	txt2.clear();
    	ptxt2.clear();


    }

    @FXML
    void btn_grs_Click(ActionEvent event) {
    	
    	sql = "select * from login where kul_ad=? and sifre=?";
        try {
            sorgu = conn.prepareStatement(sql);
            sorgu.setString(1, txt2.getText().trim());
            sorgu.setString(2, ptxt2.getText().trim());

            ResultSet getirilen = sorgu.executeQuery();

            if (!getirilen.next()) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText(null);
                alert.setContentText("giriş başarısız");
                alert.showAndWait();
            } else {
            
                System.out.println("Kullanıcı adı:" + getirilen.getString("kul_ad"));
                System.out.println("Şifre:" + getirilen.getString("sifre"));
                
          
                Alert alert1 = new Alert(AlertType.INFORMATION);
                alert1.setTitle("Message");
                alert1.setHeaderText(null);
                alert1.setContentText("Başarıyla giriş yapıldı!");
                alert1.showAndWait();
                
                try {
            		Stage stage1 = new Stage();
        			AnchorPane pane1 = (AnchorPane)FXMLLoader.load(getClass().getResource("urunler.fxml"));
        			Scene scene = new Scene(pane1);
        			stage1.setScene(scene);
        			stage1.show();
        			Stage stage = (Stage) btn_grs.getScene().getWindow(); 
        		    stage.close();
        		} catch(Exception e) {
        			e.printStackTrace();
        		}
            }
        } catch (Exception e) {
        	Alert alert2 = new Alert(AlertType.ERROR);
            alert2.setTitle("ERROR");
            alert2.setHeaderText(null);
            alert2.setContentText("giriş başarısız");
            alert2.showAndWait();
            // TODO: handle exception

        }

    }

    

    @FXML
    void btn_persEk_Click(ActionEvent event) {
    	try {
    		Stage stage1 = new Stage();
			AnchorPane pane1 = (AnchorPane)FXMLLoader.load(getClass().getResource("persEkle.fxml"));
			Scene scene = new Scene(pane1);
			stage1.setScene(scene);
			stage1.show();
			Stage stage = (Stage) btn_persEk.getScene().getWindow(); 
		    stage.close();
		} catch(Exception e) {
			e.printStackTrace();
		}


    }

    @FXML
    void initialize() {
        

    }

}
