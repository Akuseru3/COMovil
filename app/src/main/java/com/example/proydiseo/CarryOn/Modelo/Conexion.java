package com.example.proydiseo.CarryOn.Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    final String host = "carryon.mysql.database.azure.com";
    final String database = "carryon";
    static final String user = "myadmin@carryoncr";
    static final String password = "Admin12345";

    private Connection myDbConn = null;

    private void createConexion(){
        try
        {
            String url ="jdbc:mysql://carryoncr.mysql.database.azure.com:3306/carryon?useSSL=true&requireSSL=false";
            myDbConn = DriverManager.getConnection(url, user, password);
        }
        catch (SQLException e)
        {
            System.out.println(e);
        }
    }

    public Connection getConexion(){
            createConexion();
            return myDbConn;
    }

    public void closeConexion(){
        try {
            if(myDbConn!=null)
                myDbConn.close();
                myDbConn = null;
        }catch (SQLException e)
        {
            System.out.println(e);
            myDbConn = null;
        }
    }
}
