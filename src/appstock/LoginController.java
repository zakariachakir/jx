/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appstock;

import application.BDD;
import application.Parametre;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;

/**
 *
 * @author Zakaria
 */
public class LoginController implements Initializable {

    @FXML
    private Label jLabel;

    @FXML
    private TextField txt_username;
    @FXML
    private TextField txt_password;

    ResultSet rs;
    String username1, password1, hak;
    BDD db = new BDD(new Parametre().HOST_DB, new Parametre().USERNAME_DB, new Parametre().PASSWORD_DB, new Parametre().PORT, new Parametre().IPHOST);

    @FXML
    public void Connect(ActionEvent evt) throws IOException {

        rs = db.querySelectAll("utilisateur", " username='" + txt_username.getText() + "'and Password='"
                + txt_password.getText() + "'");

        try {
            while (rs.next()) {
                username1 = rs.getString("username");
                password1 = rs.getString("password");
                hak = rs.getString("type");
            }
        } catch (SQLException ex) {
            System.out.println("hna kayn prob");
            System.out.println(ex.getMessage());
        }

        if (username1 == null && password1 == null) {
            JOptionPane.showMessageDialog(null, "Please insert something");
            jLabel.setText("Please Insert Your Informations");
            jLabel.setVisible(true);
        } else if (hak.equals("Director") || hak.equals("DIRECTOR")) {
            Node node = (Node) evt.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("Principale.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } else {
            Node node = (Node) evt.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("Cachier.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
        }

    }

    public static void showInformation(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle("Warning");
        alert.setHeaderText(title);
        alert.setContentText(message);

        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
