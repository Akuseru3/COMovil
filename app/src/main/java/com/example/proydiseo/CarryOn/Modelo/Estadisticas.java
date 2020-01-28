package com.example.proydiseo.CarryOn.Modelo;

import android.util.Log;

import com.example.proydiseo.CarryOn.Controlador.Usuario;
import com.github.mikephil.charting.data.BarEntry;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Estadisticas {
    public static String getMonto(String email){
        try
        {
            Connection myDbConn = new Conexion().getConexion();
            Statement statement = myDbConn.createStatement();
            ResultSet results = statement.executeQuery("call montoTotal('"+email+"');");
            while (results.next())
            {
                Log.i("Resultado",""+results.getString(1));
                return results.getString(1);
            }
            return "-1";
        }
        catch (Exception e)
        {
            System.out.println(e);
            return "-1";
        }
    }
    public static ArrayList<BarEntry> getMontoMes(String email,String ano){
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        try
        {
            Connection myDbConn = new Conexion().getConexion();
            Statement statement = myDbConn.createStatement();
            ResultSet results = statement.executeQuery("call montoTotalMes('"+email+"',"+ano+");");
            while (results.next())
            {
                //Log.i("Resultado",""+);
                barEntries.add(new BarEntry(Integer.parseInt(results.getString(3)),Integer.parseInt(results.getString(2))-1));
            }
            return barEntries;
        }
        catch (Exception e)
        {
            System.out.println(e);
            return barEntries;
        }
    }
    public static ArrayList<BarEntry> cantidadPedidosMes(String email,String ano){
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        try
        {
            Connection myDbConn = new Conexion().getConexion();
            Statement statement = myDbConn.createStatement();
            ResultSet results = statement.executeQuery("call cantidadPedidos('"+email+"',"+ano+");");
            while (results.next())
            {
                //Log.i("Resultado",""+);
                barEntries.add(new BarEntry(Integer.parseInt(results.getString(1)),Integer.parseInt(results.getString(3))-1));
            }
            return barEntries;
        }
        catch (Exception e)
        {
            System.out.println(e);
            return barEntries;
        }
    }
    public static ArrayList<BarEntry> valoracionPromedioMes(String email,String ano){
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        try
        {
            Connection myDbConn = new Conexion().getConexion();
            Statement statement = myDbConn.createStatement();
            ResultSet results = statement.executeQuery("call valoracionxmes('"+email+"',"+ano+");");
            while (results.next())
            {
                //Log.i("Resultado",""+results.getString(1)+"-"+results.getString(2));
                barEntries.add(new BarEntry(Float.parseFloat(results.getString(1)),Integer.parseInt(results.getString(2))-1));
            }
            return barEntries;
        }
        catch (Exception e)
        {
            System.out.println(e);
            return barEntries;
        }
    }
    public static ArrayList<ArrayList<String>> establecimientosMasUsados(String email){

        ArrayList<ArrayList<String>> barEntries = new ArrayList<>();
        try
        {
            Connection myDbConn = new Conexion().getConexion();
            Statement statement = myDbConn.createStatement();
            ResultSet results = statement.executeQuery("call establecimientoMasUsados('"+email+"');");
            int count=0;
            while (results.next())
            {
                //Log.i("Resultado",""+);
                ArrayList<String> dato = new ArrayList<>();
                dato.add(Integer.toString(count));
                dato.add(results.getString(1));
                dato.add(results.getString(2));
                barEntries.add(dato);
                count++;
            }
            return barEntries;
        }
        catch (Exception e)
        {
            System.out.println(e);
            return barEntries;
        }
    }
    public static ArrayList<ArrayList<String>> topTransportistas(String email){

        ArrayList<ArrayList<String>> barEntries = new ArrayList<>();
        try
        {
            Connection myDbConn = new Conexion().getConexion();
            Statement statement = myDbConn.createStatement();
            ResultSet results = statement.executeQuery("call topTransportistas('"+email+"');");

            while (results.next())
            {
                //Log.i("Resultado",""+);
                ArrayList<String> dato = new ArrayList<>();
                dato.add(results.getString(1));
                dato.add(results.getString(2));
                dato.add(results.getString(3));
                barEntries.add(dato);
            }
            return barEntries;
        }
        catch (Exception e)
        {
            System.out.println(e);
            return barEntries;
        }
    }
    public static ArrayList<ArrayList<String>> topUsuarios(String email){

        ArrayList<ArrayList<String>> barEntries = new ArrayList<>();
        try
        {
            Connection myDbConn = new Conexion().getConexion();
            Statement statement = myDbConn.createStatement();
            ResultSet results = statement.executeQuery("call topUsuarios('"+email+"');");

            while (results.next())
            {
                //Log.i("Resultado",""+);
                ArrayList<String> dato = new ArrayList<>();
                dato.add(results.getString(1));
                dato.add(results.getString(2));
                dato.add(results.getString(3));
                barEntries.add(dato);
            }
            return barEntries;
        }
        catch (Exception e)
        {
            System.out.println(e);
            return barEntries;
        }
    }
}
