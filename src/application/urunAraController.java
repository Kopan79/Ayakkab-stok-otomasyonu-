package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.IsteMySQL.Util.VeritabaniUtil;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class urunAraController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane anc2;

    @FXML
    private Button btn_Ara;

    @FXML
    private Button btn_Geri;

    @FXML
    private Button btn_Sil;

    @FXML
    private TextField txt1;

    private Connection conn = null;
    private PreparedStatement sorgu = null;
    private ResultSet getirilen = null;
    private String sql;

    public urunAraController() {
        conn = VeritabaniUtil.Connect();
    }

    @FXML
    void btn_Ara_Click(ActionEvent event) {
        try {
            int urunID = Integer.parseInt(txt1.getText());
            sql = "SELECT * FROM urun WHERE urunID = ?";
            sorgu = conn.prepareStatement(sql);
            sorgu.setInt(1, urunID);
            getirilen = sorgu.executeQuery();

            if (getirilen.next()) {
                // Ürün bulundu, bulunanUrunController'a geçiş yap
                FXMLLoader loader = new FXMLLoader(getClass().getResource("bulunanUrun.fxml"));
                AnchorPane pane = loader.load();
                bulunanUrunController controller = loader.getController();
                controller.setUrun(getirilen); // Bulunan ürünü gönder
                Scene scene = new Scene(pane);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
                Stage stage1 = (Stage) btn_Ara.getScene().getWindow(); 
    		    stage1.close();
            } else {
                // Ürün bulunamadı
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Hata");
                alert.setHeaderText(null);
                alert.setContentText("Ürün bulunamadı!");
                alert.showAndWait();
            }
        } catch (SQLException | NumberFormatException | IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btn_Geri_Click(ActionEvent event) {
    	try {
            Stage stage1 = new Stage();
            AnchorPane pane1 = (AnchorPane) FXMLLoader.load(getClass().getResource("urunler.fxml"));
            Scene scene = new Scene(pane1);
            stage1.setScene(scene);
            stage1.show();
            Stage stage = (Stage) btn_Geri.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btn_Sil_Click(ActionEvent event) {
        txt1.clear();
    }

    @FXML
    void initialize() {
        // İlgili işlemler yapılabilir, ancak burada herhangi bir özellik tanımlanmadı
    }
}
