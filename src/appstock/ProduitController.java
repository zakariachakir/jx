/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appstock;

import application.BDD;
import application.Parametre;
import application.ResultSetTableModel;
import appstock.Table.Productmaster;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Zakaria
 */
public class ProduitController implements Initializable {

    ResultSet rs;
    String username1, password1, hak;
    BDD db = new BDD(new Parametre().HOST_DB, new Parametre().USERNAME_DB,
            new Parametre().PASSWORD_DB, new Parametre().PORT, new Parametre().IPHOST);

    @FXML
    private TableView user_table;
    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
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
    @FXML
    private TextField txtcodeproduit;
    @FXML
    private TextField txtref;
    @FXML
    private TextField txtdesignation;
    @FXML
    private TextField txtrangement;
    @FXML
    private TextField txtprix;
    @FXML
    private TextField txtremise;
    @FXML
    private TextField txtfournisseur;
    @FXML
    private TextField txtstock;
    @FXML
    private TextField txtrech;

    final ObservableList<Productmaster> data = FXCollections.observableArrayList();

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

    void actualiser() {
        txtcodeproduit.setText("");
        txtref.setText("");
        txtdesignation.setText("");
        txtfournisseur.setText("");
        txtremise.setText("");
        txtprix.setText("");
        txtrangement.setText("");
        txtstock.setText("");
        txtrech.setText("");
    }

    @FXML
    public void refresh() throws SQLException {
        actualiser();
        user_table.getItems().clear();
        fill(table());
    }

      @FXML
    public void Return(ActionEvent evt) throws IOException{
    Node node = (Node) evt.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("Principale.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
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

    @FXML
    public void clicked(MouseEvent evt) {
        Productmaster p = (Productmaster) user_table.getSelectionModel().getSelectedItem();
        txtcodeproduit.setText(String.valueOf(p.getProduct_Code()));
        txtref.setText(String.valueOf(p.getReference()));
        txtdesignation.setText(String.valueOf(p.getDeseignation()));
        txtrangement.setText(String.valueOf(p.getShelf()));
        txtfournisseur.setText(String.valueOf(p.getProvider()));
        txtremise.setText(String.valueOf(p.getDiscount()));
        txtprix.setText(String.valueOf(p.getPrice()));
        txtstock.setText(String.valueOf(p.getStorage()));
    }

    @FXML
    public void Modify(ActionEvent evt) throws SQLException {
        if (txtcodeproduit.getText().equals("") || txtref.getText().equals("") || txtdesignation.getText().equals("") || txtfournisseur.getText().equals("")
                || txtremise.getText().equals("") || txtprix.getText().equals("") || txtrangement.getText().equals("") || txtstock.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Please fill the informations");
        } else {
            Productmaster p = (Productmaster) user_table.getSelectionModel().getSelectedItem();

            clear();
            String[] colon = {"code_produit", "reference", "deseignation", "rangement",
                "fournisseur", "remise", "prix", "stock"};
            String[] inf = {txtcodeproduit.getText(), txtref.getText(), txtdesignation.getText(), txtrangement.getText(),
                txtfournisseur.getText(), txtremise.getText(), txtprix.getText(), txtstock.getText()};

            String id = String.valueOf(p.getId());
            System.out.println(db.queryUpdate("produit", colon, inf, "id='" + id + "'"));
            fill(table());
            actualiser();
        }
    }

   

    @FXML
    public void Delete(ActionEvent evt) {
        Productmaster p = (Productmaster) user_table.getSelectionModel().getSelectedItem();
        String id = String.valueOf(p.getId());
        System.out.println(id);
        if (JOptionPane.showConfirmDialog(null, "Do you really want to delete", "attention",
                JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {

            System.out.println(db.queryDelete("produit", "id='" + id + "'"));
        }
        try {
            refresh();
        } catch (SQLException ex) {
            Logger.getLogger(ProduitController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void Add(ActionEvent evt) throws SQLException {
        if (txtcodeproduit.getText().equals("") || txtref.getText().equals("") || txtdesignation.getText().equals("") || txtfournisseur.getText().equals("")
                || txtremise.getText().equals("") || txtprix.getText().equals("") || txtrangement.getText().equals("") || txtstock.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Please fill All the fields");
        } else {
            clear();
            String[] colon = {"code_produit", "reference", "deseignation", "rangement",
                "fournisseur", "remise", "prix", "stock"};
            String[] inf = {txtcodeproduit.getText(), txtref.getText(), txtdesignation.getText(), txtrangement.getText(),
                txtfournisseur.getText(), txtremise.getText(), txtprix.getText(), txtstock.getText()};
            System.out.println(db.queryInsert("produit", colon, inf));

            fill(table());
            actualiser();
        }

    }

    public ObservableList<Productmaster> table() throws SQLException {
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

    public void clear() {
        user_table.getItems().clear();
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
    
    
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            fill(table());
        } catch (SQLException ex) {
            Logger.getLogger(ProduitController.class.getName()).log(Level.SEVERE, null, ex);
        }
        setData();
    }
}
