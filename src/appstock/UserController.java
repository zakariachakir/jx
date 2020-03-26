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
import appstock.Table.Usermaster;
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
import javafx.scene.control.TableColumn;
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
public class UserController implements Initializable {

    ResultSet rs;
    String username1, password1, hak;
    BDD db = new BDD(new Parametre().HOST_DB, new Parametre().USERNAME_DB,
            new Parametre().PASSWORD_DB, new Parametre().PORT, new Parametre().IPHOST);

    @FXML
    private TableView user_table;

    @FXML
    private TableColumn<Usermaster, Integer> colID;
    @FXML
    private TableColumn<Usermaster, Integer> coluserid;
    @FXML
    private TableColumn<Usermaster, String> colusername;
    @FXML
    private TableColumn<Usermaster, String> coluserpass;
    @FXML
    private TableColumn<Usermaster, String> coltype;
    @FXML
    private TextField txtid;
    @FXML
    private TextField txtus;
    @FXML
    private TextField txtpa;

    @FXML
    ComboBox Type;
    @FXML
    private TextField txtrech;
    @FXML
    ComboBox Search;
    final ObservableList<Usermaster> data = FXCollections.observableArrayList();

    @FXML
    public void refresh() throws SQLException {
        user_table.getItems().clear();
        fill(table());
        actualiser();
    }

    @FXML
    public void Modify(ActionEvent evt) throws SQLException {
        if (txtid.getText().equals("") || txtus.getText().equals("") || txtpa.getText().equals("") || Type.getValue().equals("type")) {
            JOptionPane.showMessageDialog(null, "Please the informations");
        } else {
            Usermaster p = (Usermaster) user_table.getSelectionModel().getSelectedItem();
            String[] colon = {"id_user", "username", "password", "type"};
            String[] inf = {txtid.getText(), txtus.getText(), txtpa.getText(), Type.getValue().toString()};
            String id = String.valueOf(p.getId());
            System.out.println(db.queryUpdate("utilisateur", colon, inf, "id='" + id + "'"));
            fill(table());
            actualiser();
        }
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
    public void Delete(ActionEvent evt) {
        Usermaster p = (Usermaster) user_table.getSelectionModel().getSelectedItem();
        if(p==null){
        JOptionPane.showMessageDialog(null,"please Select something to delete");
        }
        String id = String.valueOf(p.getId());
        System.out.println(id);
        if (JOptionPane.showConfirmDialog(null, "Do you really want to delete", "attention",
                JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {

            System.out.println(db.queryDelete("utilisateur", "id='" + id + "'"));
        }
        try {
            refresh();
        } catch (SQLException ex) {
            Logger.getLogger(ProduitController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void Search(ActionEvent evt) throws SQLException {
        if (txtrech.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "please Insert what r you searching for");
        } else {
            if (Search.getValue().equals("USER ID")) {
                clear();
                ObservableList r = Recherche("id_user LIKE '%" + txtrech.getText() + "%' ");
                fill(r);
            } else if (Search.getValue().equals("USERNAME")) {
                clear();
                ObservableList r = Recherche("username LIKE '%" + txtrech.getText() + "%' ");
                fill(r);

            } else if (Search.getValue().equals("TYPE")) {
                clear();
                ObservableList r = Recherche("type LIKE '%" + txtrech.getText() + "%' ");
                fill(r);

            }

        }

    }

    @FXML
    public void clicked(MouseEvent evt) {
        Usermaster p = (Usermaster) user_table.getSelectionModel().getSelectedItem();
        txtid.setText(String.valueOf(p.getUser_id()));
        txtus.setText(String.valueOf(p.getUsername()));
        txtpa.setText(String.valueOf(p.getPassword()));
        Type.getSelectionModel().select(p.getType());

    }
    
    @FXML
    public void Add(ActionEvent evt) throws SQLException {
        if (txtid.getText().equals("") || txtus.getText().equals("") || txtpa.getText().equals("") || Type.getValue().equals("type")) {
            JOptionPane.showMessageDialog(null, "Please fill the informations");
        } else {
            clear();
            String[] colon = {"id_user", "username", "password", "type"};
            String[] inf = {txtid.getText(), txtus.getText(), txtpa.getText(), Type.getValue().toString()};
            System.out.println(db.queryInsert("utilisateur", colon, inf));
            fill(table());
            actualiser();
        }

    }

    void actualiser() {
        txtid.setText("");
        txtus.setText("");
        txtpa.setText("");

    }

    public void setDataSearch() {

        Search.getItems().clear();

        Search.getItems().addAll(
                "USER ID",
                "USERNAME",
                "TYPE"
        );

    }

    public void setDataType() {

        Type.getItems().clear();

        Type.getItems().addAll(
                
                "DIRECTOR",
                "CACHIER"
        );

    }

    public ObservableList<Usermaster> table() throws SQLException {
        clear();
        String table[] = {"id", "id_user", "username", "password", "Type"};
        rs = db.querySelect(table, "utilisateur");
        while (rs.next()) {
            Usermaster p = new Usermaster();
            p.Id.set(rs.getInt("id"));
            p.User_id.set(rs.getInt("id_user"));
            p.Username.set(rs.getString("username"));
            p.Password.set(rs.getString("password"));
            p.Type.set(rs.getString("type"));
            data.add(p);
        }
        return data;

    }

    public ObservableList<Usermaster> Recherche(String etat) throws SQLException {
        clear();
        String table[] = {"id", "id_user", "username", "password", "Type"};
        rs = db.querySelectAll("utilisateur", etat);
        while (rs.next()) {
            Usermaster p = new Usermaster();
            p.Id.set(rs.getInt("id"));
            p.User_id.set(rs.getInt("id_user"));
            p.Username.set(rs.getString("username"));
            p.Password.set(rs.getString("password"));
            p.Type.set(rs.getString("type"));
            data.add(p);
        }
        return data;

    }

    public void clear() {
        user_table.getItems().clear();
    }

    public void fill(ObservableList list) {

        colID.setCellValueFactory(
                new PropertyValueFactory<Usermaster, Integer>("Id"));
        coluserid.setCellValueFactory(
                new PropertyValueFactory<Usermaster, Integer>("User_id"));
        colusername.setCellValueFactory(
                new PropertyValueFactory<Usermaster, String>("Username"));
        coluserpass.setCellValueFactory(
                new PropertyValueFactory<Usermaster, String>("Password"));
        coltype.setCellValueFactory(
                new PropertyValueFactory<Usermaster, String>("Type"));

        user_table.setItems(list);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            fill(table());
        } catch (SQLException ex) {
            Logger.getLogger(ProduitController.class.getName()).log(Level.SEVERE, null, ex);
        }
        setDataType();
        setDataSearch();
    }

}
