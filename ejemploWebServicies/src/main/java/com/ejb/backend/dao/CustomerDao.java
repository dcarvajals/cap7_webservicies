/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ejb.backend.dao;

import com.ejb.backend.appdata.ConectionDB;
import com.ejb.backend.model.Customer;

/**
 * 
 * @author dcarvajals
 */
public class CustomerDao {
    
    ConectionDB conex;
    private String query = "";
    
    public CustomerDao () {
        conex = new ConectionDB();
    }
    
    public boolean insertCustomer(Customer c){
        this.query = String.format("insert into customer (lastaname, name, email, address)\n" +
                    "values ('%s', '%s', '%s', '%s')", c.getLastname(), c.getName(), c.getEmail(), c.getAddress());
        return conex.executeSQL(query);
    }
    
    public boolean updateCustomer(Customer c){
        this.query = String.format("update customer set lastaname = '%s', name = '%s', email = '%s', "
                + "address = '%s' where id_customer = %s", c.getLastname(), c.getName(), c.getEmail(), c.getAddress(), c.getId_customer());
        return conex.executeSQL(query);
    }
    
    public boolean deleteCustomer(Customer c){
        this.query = String.format("delete from Customer where id_customer = %s",  c.getId_customer());
        return conex.executeSQL(query);
    }
    
    public String listCustomer (){
        this.query = "select * from customer";
        return conex.getRecordsInJson(query);
    }

}
