package com.example.proydiseo.CarryOn.Modelo;

import com.example.proydiseo.CarryOn.Controlador.Establecimiento;
import com.example.proydiseo.CarryOn.Controlador.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class UserData {

    public static Usuario getUser(String email){
        try
        {
            Connection myDbConn = new Conexion().getConexion();
            Statement statement = myDbConn.createStatement();
            ResultSet results = statement.executeQuery("SELECT * from usuario where email='"+email+"';");
            while (results.next())
            {
                Usuario found = new Usuario(results.getString(3),results.getString(4),results.getString(5),results.getString(6),results.getString(1));
                return found;
            }
            return new Usuario("","","","","");
        }
        catch (Exception e)
        {
            System.out.println(e);
            return new Usuario("","","","","");
        }
    }

    public static ArrayList<Establecimiento> getAllEstablish(){
        try
        {
            Connection myDbConn = new Conexion().getConexion();
            ArrayList<Establecimiento> estabs = new ArrayList<Establecimiento>();
            Statement statement = myDbConn.createStatement();
            ResultSet results = statement.executeQuery("SELECT * from establecimiento;");
            while (results.next())
            {
                Establecimiento found = new Establecimiento(results.getString(1),results.getString(2),results.getString(3),results.getString(3));
                estabs.add(found);
            }
            return estabs;
        }
        catch (Exception e)
        {
            System.out.println(e);
            return new ArrayList<Establecimiento>();
        }
    }

    public static int modifyUser(String email,String name,String lastName,String phone, String bday){
        try
        {
            Connection myDbConn = new Conexion().getConexion();
            PreparedStatement preparedStatement = myDbConn.prepareStatement("CALL updateUser(?, ?, ?, ?, ?);");
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, lastName);
            preparedStatement.setString(4, bday);
            preparedStatement.setString(5, phone);
            preparedStatement.executeUpdate();
            return 1;
        }
        catch (Exception e)
        {
            System.out.println(e);
            return -1;
        }
    }

    public static int modifyPassword(String email,String pass){
        try
        {
            Connection myDbConn = new Conexion().getConexion();
            PreparedStatement preparedStatement = myDbConn.prepareStatement("CALL updatePassword(?, ?);");
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, pass);
            preparedStatement.executeUpdate();
            return 1;
        }
        catch (Exception e)
        {
            System.out.println(e);
            return -1;
        }
    }

}
