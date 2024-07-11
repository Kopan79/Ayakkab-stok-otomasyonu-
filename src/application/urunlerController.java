package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import com.IsteMySQL.Util.VeritabaniUtil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class urunlerController {

    public urunlerController() {
        conn = VeritabaniUtil.Connect();
    }

    @FXML
    private TableView<kayitlar_urun1> tableBulunan;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<kayitlar_urun1, Integer> adet;

    @FXML
    private AnchorPane anc3;

    @FXML
    private TableColumn<kayitlar_urun1, Integer> beden;

    @FXML
    private Button btn_Kapat;

    @FXML
    private Button btn_Urekle;
    
    @FXML
    private Button btn_Ara;

    @FXML
    private TableColumn<kayitlar_urun1, Float> fiyat;

    @FXML
    private TableColumn<kayitlar_urun1, String> renk;

    @FXML
    private TableColumn<kayitlar_urun1, Integer> satildi;

    @FXML
    private TableColumn<kayitlar_urun1, Integer> urunID;

    @FXML
    private TableColumn<kayitlar_urun1, String> urunturu;

    Connection conn = null;
    PreparedStatement sorgu = null;
    ResultSet getirilen = null;
    String sql;

    private void DegerleriGetir(TableView<kayitlar_urun1> tableView) {
        try {
            // Veritabanından ürün verilerini almak için SQL sorgusu
            String sql = "SELECT * FROM urun";

            // SQL sorgusunu hazırla
            sorgu = conn.prepareStatement(sql);

            // Sorguyu çalıştır ve sonuçları al
            ResultSet sonuclar = sorgu.executeQuery();

            // TableView için ObservableList oluştur
            ObservableList<kayitlar_urun1> urunListesi = FXCollections.observableArrayList();

            // Sonuçları döngüye al
            while (sonuclar.next()) {
                // Her bir sonuç için bir urun nesnesi oluştur ve verileri ekle
                kayitlar_urun1 urun = new kayitlar_urun1();
                urun.setUrunID(sonuclar.getInt("urunID"));
                urun.setKategori(sonuclar.getString("kategori"));
                urun.setRenk(sonuclar.getString("renk"));
                urun.setBeden(sonuclar.getInt("numara"));
                urun.setAdet(sonuclar.getInt("adet"));
                urun.setFiyat(sonuclar.getFloat("fiyat"));
                urun.setSatildi(sonuclar.getInt("satildi"));

                // TableView için oluşturduğumuz ObservableList'e ekle
                urunListesi.add(urun);
            }

            // TableView'in içeriğini güncelle
            tableView.setItems(urunListesi);

            // TableView'e sütunları ekle ve her bir sütunu ilgili veri özelliğiyle eşleştir
            urunID.setCellValueFactory(new PropertyValueFactory<>("urunID"));
            urunturu.setCellValueFactory(new PropertyValueFactory<>("kategori"));
            beden.setCellValueFactory(new PropertyValueFactory<>("beden"));
            renk.setCellValueFactory(new PropertyValueFactory<>("renk"));
            adet.setCellValueFactory(new PropertyValueFactory<>("adet"));
            satildi.setCellValueFactory(new PropertyValueFactory<>("satildi"));
            fiyat.setCellValueFactory(new PropertyValueFactory<>("fiyat"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btn_Kapat_Click(ActionEvent event) {
        try {
            Stage stage1 = new Stage();
            AnchorPane pane1 = (AnchorPane) FXMLLoader.load(getClass().getResource("dersProjesi.fxml"));
            Scene scene = new Scene(pane1);
            stage1.setScene(scene);
            stage1.show();
            Stage stage = (Stage) btn_Kapat.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    void btn_Ara_Click(ActionEvent event) {
        try {
            Stage stage1 = new Stage();
            AnchorPane pane1 = (AnchorPane) FXMLLoader.load(getClass().getResource("urunAra.fxml"));
            Scene scene = new Scene(pane1);
            stage1.setScene(scene);
            stage1.show();
            Stage stage = (Stage) btn_Ara.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btn_Urekle_Click(ActionEvent event) {
        try {
            Stage stage1 = new Stage();
            AnchorPane pane1 = (AnchorPane) FXMLLoader.load(getClass().getResource("urunEkle.fxml"));
            Scene scene = new Scene(pane1);
            stage1.setScene(scene);
            stage1.show();
            Stage stage = (Stage) btn_Urekle.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() {
        DegerleriGetir(tableBulunan);
    }

}
