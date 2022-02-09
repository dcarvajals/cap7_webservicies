/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ejb.backend.appdata;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

/**
 * 
 * @author dcarvajals
 */
public class ConectionDB {
    
    java.sql.Connection conex;
    DefaultTableModel dataModel;
    ResultSet result;
    ResultSetMetaData rsmd;
    java.sql.Statement st;
    
    private String dbHost = "";
    private String dbPort = "";
    private String dbName = "";
    private String dbUser = "";
    private String dbPassword = "";
    
    public ConectionDB () {
        this.dbHost = "localhost";
        this.dbPort = "5434";
        this.dbName = "dbejb";
        this.dbUser = "postgres";
        this.dbPassword = "carvajal2000";
    }
    
    public boolean openConecction() {
        try {
            Class.forName("org.postgresql.Driver");
            conex = DriverManager.getConnection("jdbc:postgresql://" + this.dbHost + ":" +  this.dbPort + 
                    "/" +  this.dbName,  this.dbUser,  this.dbPassword);
        } catch (ClassNotFoundException | SQLException exc) {
            System.out.println("No connection");
            System.out.println(exc.getMessage());
            return false;
        }
        return true;
    }
    
    public boolean closeConnection() {
        try {
            st.close();
            conex.close();
        } catch (SQLException exc) {
            System.out.println("Error close connection:" + exc.getMessage());
            return false;
        }
        return true;
    }
    
    public boolean executeSQL(String sentecy) {
        if (openConecction()) {
            try {
                st = conex.createStatement();
                st.execute(sentecy);
            } catch (SQLException exc) {
                System.out.println("Error ModifyBD:" + exc.getMessage());
                return false;
            }
            closeConnection();
            return true;
        } else {
            return false;
        }
    }
    
    public boolean closeResulSet() {
        try {
            result.close();
        } catch (SQLException ex) {
            System.out.println("error in close resulset:" + ex.getMessage());
            return false;
        }
        return true;
    }
    
    public DefaultTableModel returnRecord(String sentecy) {
        dataModel = new DefaultTableModel();
        if (openConecction()) {
            try {
                st = conex.createStatement();
                result = st.executeQuery(sentecy);
                rsmd = result.getMetaData();
                int n = rsmd.getColumnCount();
                for (int i = 1; i <= n; i++) {
                    dataModel.addColumn(rsmd.getColumnName(i));
                }
                String[] row = new String[n];
                while (result.next()) {
                    for (int i = 0; i < n; i++) {
                        row[i] = (result.getString(rsmd.getColumnName(i + 1)) == null) ? "" : result.getString(rsmd.getColumnName(i + 1));
                    }
                    dataModel.addRow(row);
                }
            } catch (SQLException exc) {
                System.out.println("Error return Record:" + exc.getMessage());
                dataModel = new DefaultTableModel();
            } finally {
                if (result != null) {
                    closeResulSet();
                }
            }
            closeConnection();
        }
        return dataModel;
    }
    
    public String getRecordsInJson(String sentency) {
        String resul = "[";
        DefaultTableModel table = returnRecord(sentency);
        if (table != null) {
            int columCount = table.getColumnCount();
            for (int row = 0; row < table.getRowCount(); row++) {
                String line = "";
                for (int colum = 0; colum < columCount; colum++) {
                    line += "\"" + table.getColumnName(colum) + "\":\"" + table.getValueAt(row, colum).toString() + "\"";
                    if (colum < columCount - 1) {
                        line += ",";
                    }
                }
                if (line.length() > 0) {
                    resul += "{" + line + "}";
                    if (row < table.getRowCount() - 1) {
                        resul += ",";
                    }
                }
            }
            resul += "]";
        } else {
            resul = "[]";
        }
        return resul;
    }

}
