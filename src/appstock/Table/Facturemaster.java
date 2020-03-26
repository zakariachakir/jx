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
public class Facturemaster {
    
   public SimpleIntegerProperty Id = new SimpleIntegerProperty();
   public SimpleStringProperty num_facture = new SimpleStringProperty();
   public SimpleIntegerProperty code_produit = new SimpleIntegerProperty(); 
   public SimpleStringProperty reference = new SimpleStringProperty();
   public SimpleIntegerProperty prix_vente = new SimpleIntegerProperty();
   public SimpleIntegerProperty stock_sortie = new SimpleIntegerProperty();
   public SimpleIntegerProperty subtotal = new SimpleIntegerProperty();

    public Integer getId() {
        return Id.get();
    }

    public String getNum_facture() {
        return num_facture.get();
    }

    public Integer getCode_produit() {
        return code_produit.get();
    }

    public String getReference() {
        return reference.get();
    }

    public Integer getPrix_vente() {
        return prix_vente.get();
    }

    public Integer getStock_sortie() {
        return stock_sortie.get();
    }

    public Integer getSubtotal() {
        return subtotal.get();
    }
   
    
}
