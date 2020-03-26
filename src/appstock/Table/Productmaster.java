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
public class Productmaster {
   public SimpleIntegerProperty Id = new SimpleIntegerProperty();
   public SimpleIntegerProperty Product_Code = new SimpleIntegerProperty();
   public SimpleStringProperty Reference = new SimpleStringProperty(); 
   public SimpleStringProperty Deseignation = new SimpleStringProperty();
   public SimpleStringProperty Shelf = new SimpleStringProperty();
   public SimpleStringProperty Provider = new SimpleStringProperty();
   public SimpleIntegerProperty Discount = new SimpleIntegerProperty();
   public SimpleIntegerProperty Price = new SimpleIntegerProperty();
   public SimpleIntegerProperty Storage = new SimpleIntegerProperty();
   
    public Integer getId() {
        return Id.get();
    }

    public Integer getProduct_Code() {
        return Product_Code.get();
    }

    public String getReference() {
        return Reference.get();
    }

    public String getDeseignation() {
        return Deseignation.get();
    }

    public String getShelf() {
        return Shelf.get();
    }

    public String getProvider() {
        return Provider.get();
    }

    public Integer getDiscount() {
        return Discount.get();
    }

    public Integer getPrice() {
        return Price.get();
    }

    public Integer getStorage() {
        return Storage.get();
    }

}
