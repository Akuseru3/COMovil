package com.example.proydiseo.CarryOn.Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginRegister {

    public static int tryLogin(String email, String pass){
        try
        {
            Connection myDbConn = new Conexion().getConexion();
            Statement statement = myDbConn.createStatement();
            ResultSet results = statement.executeQuery("SELECT * from usuario where email='"+email+"';");
            while (results.next())
            {
                if(email.equals(results.getString(1)) && pass.equals(results.getString(2)))
                {
                    return (Integer.parseInt(results.getString(7)));
                }
                else{
                    return -1;
                }
            }
            return -2;
        }
        catch (Exception e)
        {
            System.out.println(e);
            return -3;
        }
    }

    public static int addUser(String email,String pass,String nombre, String apellidos, String fechaNacimiento, String telefono, int tipo){
        try
        {
            Connection myDbConn = new Conexion().getConexion();
            PreparedStatement preparedStatement = myDbConn.prepareStatement("INSERT INTO usuario (email,contra,nombre,apellidos,fechaNacimiento,telefono,tipoUsuario) VALUES (?, ?, ?, ?, ?, ?, ?);");
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, pass);
            preparedStatement.setString(3, nombre);
            preparedStatement.setString(4, apellidos);
            preparedStatement.setString(5, fechaNacimiento);
            preparedStatement.setString(6, telefono);
            preparedStatement.setInt(7, tipo);
            preparedStatement.executeUpdate();
            return 1;
        }
        catch (SQLException e)
        {
            System.out.println(("Encountered an error when executing the given insert sql statement."));
            return -1;
        }
    }
}
