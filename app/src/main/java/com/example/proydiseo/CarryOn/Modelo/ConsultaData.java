package com.example.proydiseo.CarryOn.Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ConsultaData {
    public static int agregarConsulta(String idOrden, String titulo,String consulta){
        try
        {
            Connection myDbConn = new Conexion().getConexion();
            int id = Integer.parseInt(idOrden);
            PreparedStatement preparedStatement = myDbConn.prepareStatement("Insert into consultasxorden values(?, ?, ?, ?);");
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, titulo);
            preparedStatement.setString(3, consulta);
            preparedStatement.setString(4, "");
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
