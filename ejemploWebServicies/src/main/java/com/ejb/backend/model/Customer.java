/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ejb.backend.model;

/**
 * 
 * @author dcarvajals
 */
public class Customer {
    
    private String id_customer = "";
    private String lastname = "";
    private String name = "";
    private String email = "";
    private String address = "";

    public Customer() {
    }
    
    public Customer(String lastname, String name, String email, String address) {
        this.lastname = lastname;
        this.name = name;
        this.email = email;
        this.address = address;
    }
    

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getId_customer() {
        return id_customer;
    }

    public void setId_customer(String id_customer) {
        this.id_customer = id_customer;
    }
    
    
    
   
}
