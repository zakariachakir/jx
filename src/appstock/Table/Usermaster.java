/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appstock.Table;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Zakaria
 */
public class Usermaster {

    public SimpleIntegerProperty Id = new SimpleIntegerProperty();
    public SimpleIntegerProperty User_id = new SimpleIntegerProperty();
    public SimpleStringProperty Username = new SimpleStringProperty();
    public SimpleStringProperty Password = new SimpleStringProperty();
    public SimpleStringProperty Type = new SimpleStringProperty();

    public Integer getId() {
        return Id.get();
    }

    public Integer getUser_id() {
        return User_id.get();
    }

    public String getUsername() {
        return Username.get();
    }

    public String getPassword() {
        return Password.get();
    }

    public String getType() {
        return Type.get();
    }
    
}
