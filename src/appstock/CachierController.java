/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appstock;

import application.BDD;
import application.Parametre;
import appstock.Table.Facturemaster;
import appstock.Table.Productmaster;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Zakaria
 */
public class CachierController implements Initializable {

    ResultSet rs;
    String username1, password1, hak;
    BDD db = new BDD(new Parametre().HOST_DB, new Parametre().USERNAME_DB,
            new Parametre().PASSWORD_DB, new Parametre().PORT, new Parametre().IPHOST);

    int old, rec, now;
    @FXML
    private TableView user_table;

    @FXML
    private TableView tbl_facture;

    @FXML
    private TextField txtcash;
    @FXML
    private TextField txtcodeproduit;
    @FXML
    private TextField txtfacture;
    @FXML
    private TextField txtfactureajoute;
    @FXML
    private TextField txtfournisseur;
    @FXML
    private TextField txtnouveau;
    @FXML
    private TextField txtprix;
    @FXML
    private TextField txtrech;
    @FXML
    private TextField txtref;
    @FXML
    private TextField txtremise;
    @FXML
    private TextField txtstocksortie;
    @FXML
    private TextField txtrangement;

    @FXML
    private Label reste;
    @FXML
    private Label rp;
    @FXML
    private Label rp1;

//table1
    @FXML
    private TableColumn<Productmaster, Integer> colID;
    @FXML
    private TableColumn<Productmaster, Integer> colcodeproduit;
    @FXML
    private TableColumn<Productmaster, String> colreference;
    @FXML
    private TableColumn<Productmaster, String> coldeseignation;
    @FXML
    private TableColumn<Productmaster, String> colShelf;
    @FXML
    private TableColumn<Productmaster, String> colProvider;
    @FXML
    private TableColumn<Productmaster, Integer> colDiscount;
    @FXML
    private TableColumn<Productmaster, Integer> colPrice;
    @FXML
    private TableColumn<Productmaster, Integer> colStorage;
    //table2
    @FXML
    private TableColumn<Facturemaster, Integer> colfactureID;
    @FXML
    private TableColumn<Facturemaster, String> colfacturenumber;
    @FXML
    private TableColumn<Facturemaster, String> colfacturereference;
    @FXML
    private TableColumn<Facturemaster, Integer> coldfactureproductcode;
    @FXML
    private TableColumn<Facturemaster, Integer> colfacturesellprice;
    @FXML
    private TableColumn<Facturemaster, Integer> colfacturestorage;
    @FXML
    private TableColumn<Facturemaster, Integer> coltotal;

    final ObservableList<Productmaster> data = FXCollections.observableArrayList();
    final ObservableList<Facturemaster> dataf = FXCollections.observableArrayList();

    @FXML
    ComboBox myCombobox;

    public void setData() {

        myCombobox.getItems().clear();

        myCombobox.getItems().addAll(
                "Product",
                "Reference",
                "Deseignation",
                "Shelf",
                "Provider",
                "Discount",
                "Price",
                "Storage");

    }

    public void clicked(MouseEvent evt) {
        Productmaster p = (Productmaster) user_table.getSelectionModel().getSelectedItem();
        txtcodeproduit.setText(String.valueOf(p.getProduct_Code()));
        txtref.setText(String.valueOf(p.getReference()));
        txtrangement.setText(String.valueOf(p.getShelf()));
        txtfournisseur.setText(String.valueOf(p.getProvider()));
        txtremise.setText(String.valueOf(p.getDiscount()));
        txtprix.setText(String.valueOf(p.getPrice()));
        cout();

    }

    public void clear() {
        user_table.getItems().clear();
    }

    public void clearf() {
        tbl_facture.getItems().clear();
    }

    public void cout() {
        double a = Double.parseDouble(txtprix.getText().trim());
        double b = Double.parseDouble(txtremise.getText().trim());
        double c = a - a * (b / 100);
        txtnouveau.setText(String.valueOf(c));
    }

    public void subtotal() {
        double a = Double.parseDouble(txtnouveau.getText().trim());
        double b = Double.parseDouble(txtstocksortie.getText().trim());
        double c = a * b;
        rp.setText(String.valueOf(c));

    }

    public void total() throws SQLException {

        rs = db.executionQuery("SELECT SUM(subtotal) as subtotal FROM vente WHERE num_facture = '" + txtfacture.getText() + "'");
        rs.next();
        rp1.setText(rs.getString("subtotal"));

    }

    @FXML
    public void Keyreleseadsubtotal(KeyEvent evt) {
        subtotal();
    }

    @FXML
    public void Keyreleseadreste(KeyEvent evt) {
        payeapres();
    }

    @FXML
    public void Addtosales(ActionEvent evt) throws SQLException {
        if (txtcodeproduit.getText().equals("") || txtref.getText().equals("") || txtstocksortie.getText().equals("") || txtfournisseur.getText().equals("")
                || txtremise.getText().equals("") || txtprix.getText().equals("") || txtrangement.getText().equals("") || txtnouveau.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Please fill the informations");
        } else if (txtfactureajoute.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Please provide a facture number");
        } else {
            String cols[] = {"num_facture", "code_produit", "reference", "prix_vente", "stock_sortie", "subtotal"};
            String inserts[] = {txtfactureajoute.getText(), txtcodeproduit.getText(), txtref.getText(), txtnouveau.getText(), txtstocksortie.getText(),
                rp.getText()};
            db.queryInsert("vente", cols, inserts);

            if (!teststock()) {
                JOptionPane.showMessageDialog(null, "Stock limiter");
            } else {
                clear();
                deference();
                fill(tablep());
            }
            clearf();
            subtotal();
            fillbill(tablef());
            total();
        }
    }

    @FXML
    public void refresh(ActionEvent ext) throws SQLException {
        actualiser();
        clear();
        fill(tablep());
        clearf();
        fillbill(tablef());
        
    }
    
    @FXML
    public void Return(ActionEvent evt) throws IOException {
     Node node = (Node) evt.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(CachierController.class.getName()).log(Level.SEVERE, null, ex);
        }
            Scene scene = new Scene(root);
            stage.setScene(scene);

}
    
    public void actualiser(){
    txtcodeproduit.setText("");
        txtref.setText("");
        txtrangement.setText("");
        txtfournisseur.setText("");
        txtremise.setText("");
        txtprix.setText("");
        txtnouveau.setText("");
        txtstocksortie.setText("");
        txtfactureajoute.setText("");
        rp.setText("0");
        rp1.setText("0");
        txtfacture.setText("");
        reste.setText("0");
        txtcash.setText("");
        txtrech.setText("");
    }

    public boolean teststock() throws SQLException {
        boolean test;
        rs = db.querySelectAll("produit", "code_produit='" + txtcodeproduit.getText() + "'");
        while (rs.next()) {
            old = rs.getInt("stock");
        }
        rec = Integer.parseInt(txtstocksortie.getText().trim());
        if (old < rec) {
            test = false;
        } else {
            test = true;
        }
        return test;
    }

    public void deference() throws SQLException {

        rs = db.querySelectAll("produit", "code_produit='" + txtcodeproduit.getText() + "'");
        while (rs.next()) {
            old = rs.getInt("stock");
        }
        rec = Integer.parseInt(txtstocksortie.getText().trim());
        now = old - rec;
        String nvstock = Integer.toString(now);

        String a = String.valueOf(nvstock);
        String[] colon = {"stock"};
        String[] isi = {a};
        System.out.println(db.queryUpdate("produit", colon, isi, "code_produit='" + txtcodeproduit.getText() + "'"));
    }

    public void payeapres() {
        int a = Integer.parseInt(rp1.getText().trim());
        int b = Integer.parseInt(txtcash.getText().trim());
        int c = b - a;
        reste.setText(Integer.toString(c));
    }

    public ObservableList<Productmaster> recherche(String etat) throws SQLException {
        clear();

        rs = db.querySelectAll("Produit", etat);
        while (rs.next()) {
            Productmaster p = new Productmaster();
            p.Id.set(rs.getInt("Id"));
            p.Product_Code.set(rs.getInt("code_produit"));
            p.Reference.set(rs.getString("reference"));
            p.Deseignation.set(rs.getString("deseignation"));
            p.Shelf.set(rs.getString("rangement"));
            p.Provider.set(rs.getString("fournisseur"));
            p.Discount.set(rs.getInt("remise"));
            p.Price.set(rs.getInt("prix"));
            p.Storage.set(rs.getInt("stock"));
            data.add(p);
        }
        return data;

    }

    public ObservableList<Facturemaster> recherchebill() throws SQLException {
        clearf();
        String table[] = {"id", "num_facture", "code_produit", "reference", "prix_vente", "stock_sortie", "subtotal"};

        rs = db.fcSelectCommand(table, "vente", "num_facture=" + txtfacture.getText());
        while (rs.next()) {
            Facturemaster p = new Facturemaster();
            p.Id.set(rs.getInt("Id"));
            p.code_produit.set(rs.getInt("code_produit"));
            p.num_facture.set(rs.getString("num_facture"));
            p.reference.set(rs.getString("reference"));
            p.prix_vente.set(rs.getInt("prix_vente"));
            p.stock_sortie.set(rs.getInt("stock_sortie"));
            p.subtotal.set(rs.getInt("subtotal"));
            
            dataf.add(p);
        }
        total();
        return dataf;

    }

    @FXML
    public void Searchb(ActionEvent evt) throws SQLException {
    fillbill(recherchebill());
    }
    
    
    @FXML
    public void Deleteselected(ActionEvent evt) throws SQLException {
        Facturemaster p = (Facturemaster) tbl_facture.getSelectionModel().getSelectedItem();
        String id = String.valueOf(p.getId());
        if (JOptionPane.showConfirmDialog(null, "Do you really want to delete", "attention",
                JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {

            System.out.println(db.queryDelete("vente", "id=" + id));
        } else {
            return;
        }
        clearf();
        if(txtfacture.getText()!=""){
        fillbill(recherchebill());}
        else{
        fillbill(tablef());
        }

    }

    public ObservableList<Productmaster> tablep() throws SQLException {
        clear();
        String table[] = {"id", "code_produit", "reference", "deseignation", "rangement",
            "fournisseur", "remise", "prix", "stock"};
        rs = db.querySelect(table, "produit");
        while (rs.next()) {
            Productmaster p = new Productmaster();
            p.Id.set(rs.getInt("id"));
            p.Product_Code.set(rs.getInt("code_produit"));
            p.Reference.set(rs.getString("reference"));
            p.Deseignation.set(rs.getString("deseignation"));
            p.Shelf.set(rs.getString("rangement"));
            p.Provider.set(rs.getString("fournisseur"));
            p.Discount.set(rs.getInt("remise"));
            p.Price.set(rs.getInt("prix"));
            p.Storage.set(rs.getInt("stock"));
            data.add(p);
        }
        return data;

    }

    public ObservableList<Facturemaster> tablef() throws SQLException {
        clearf();
                String table[] = {"id", "num_facture", "code_produit", "reference", "prix_vente", "stock_sortie", "subtotal"};

        rs = db.querySelect(table, "vente");
        while (rs.next()) {
            Facturemaster p = new Facturemaster();
            p.Id.set(rs.getInt("Id"));
            p.code_produit.set(rs.getInt("code_produit"));
            p.num_facture.set(rs.getString("num_facture"));
            p.reference.set(rs.getString("reference"));
            p.prix_vente.set(rs.getInt("prix_vente"));
            p.stock_sortie.set(rs.getInt("stock_sortie"));
            p.subtotal.set(rs.getInt("subtotal"));

            dataf.add(p);
        }
        return dataf;

    }

    public void fill(ObservableList list) {

        colID.setCellValueFactory(
                new PropertyValueFactory<Productmaster, Integer>("Id"));
        colcodeproduit.setCellValueFactory(
                new PropertyValueFactory<Productmaster, Integer>("Product_Code"));
        colreference.setCellValueFactory(
                new PropertyValueFactory<Productmaster, String>("Reference"));
        coldeseignation.setCellValueFactory(
                new PropertyValueFactory<Productmaster, String>("Deseignation"));
        colShelf.setCellValueFactory(
                new PropertyValueFactory<Productmaster, String>("Shelf"));
        colProvider.setCellValueFactory(
                new PropertyValueFactory<Productmaster, String>("Provider"));
        colDiscount.setCellValueFactory(
                new PropertyValueFactory<Productmaster, Integer>("Discount"));
        colPrice.setCellValueFactory(
                new PropertyValueFactory<Productmaster, Integer>("Price"));
        colStorage.setCellValueFactory(
                new PropertyValueFactory<Productmaster, Integer>("Storage"));

        user_table.setItems(list);
    }

    public void fillbill(ObservableList list) {

        colfactureID.setCellValueFactory(
                new PropertyValueFactory<Facturemaster, Integer>("Id"));
        colfacturenumber.setCellValueFactory(
                new PropertyValueFactory<Facturemaster, String>("num_facture"));
        coldfactureproductcode.setCellValueFactory(
                new PropertyValueFactory<Facturemaster, Integer>("code_produit"));
        colfacturereference.setCellValueFactory(
                new PropertyValueFactory<Facturemaster, String>("reference"));
        colfacturesellprice.setCellValueFactory(
                new PropertyValueFactory<Facturemaster, Integer>("prix_vente"));
        colfacturestorage.setCellValueFactory(
                new PropertyValueFactory<Facturemaster, Integer>("stock_sortie"));
        coltotal.setCellValueFactory(
                new PropertyValueFactory<Facturemaster, Integer>("subtotal"));

        tbl_facture.setItems(list);
    }

    
    
    
    @FXML
    public void search(ActionEvent evt) throws SQLException {
        if (txtrech.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "please Insert what r you searching for");
        } else {
            if (myCombobox.getValue().equals("Product Code")) {
                clear();
                ObservableList r = recherche("code_produit=" + txtrech.getText());
                fill(r);

            } else if (myCombobox.getValue().equals("Reference")) {
                clear();
                ObservableList r = recherche("reference LIKE '%" + txtrech.getText() + "%' ");
                fill(r);

            } else if (myCombobox.getValue().equals("Deseignation")) {
                clear();
                ObservableList r = recherche("deseignation LIKE '%" + txtrech.getText() + "%' ");
                fill(r);
            } else if (myCombobox.getValue().equals("Shelf")) {
                clear();
                ObservableList r = recherche("rangement LIKE '%" + txtrech.getText() + "%' ");
                fill(r);
            } else if (myCombobox.getValue().equals("Provider")) {
                clear();
                ObservableList r = recherche("fournisseur LIKE '%" + txtrech.getText() + "%' ");
                fill(r);
            } else if (myCombobox.getValue().equals("Discount")) {
                clear();
                ObservableList r = recherche("remise =" + txtrech.getText());
                fill(r);
            } else if (myCombobox.getValue().equals("Price")) {
                clear();
                ObservableList r = recherche("prix =" + txtrech.getText());
                fill(r);
            } else if (myCombobox.getValue().equals("Storage")) {
                clear();
                ObservableList r = recherche("stock =" + txtrech.getText());
                fill(r);
            }

        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            fill(tablep());
        } catch (SQLException ex) {
            Logger.getLogger(CachierController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            fillbill(tablef());
        } catch (SQLException ex) {
            Logger.getLogger(CachierController.class.getName()).log(Level.SEVERE, null, ex);
        }
        setData();
     }

}
