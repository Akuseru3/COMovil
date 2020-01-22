package com.example.proydiseo.CarryOn.Modelo;

import com.example.proydiseo.CarryOn.Controlador.ListaPedido;
import com.example.proydiseo.CarryOn.Controlador.Producto;
import com.example.proydiseo.CarryOn.Controlador.TodoPedido;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class PedidoData {
    public static int agregarItemAOrden(String idOrden, String idEst, String item, String cant){
        try
        {
            Connection myDbConn = new Conexion().getConexion();
            int canti = Integer.parseInt(cant);
            int id = Integer.parseInt(idOrden);
            PreparedStatement preparedStatement = myDbConn.prepareStatement("Insert into itemxorden values(?, ?, ?, ?, ?, ?);");
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, item);
            preparedStatement.setString(3, idEst);
            preparedStatement.setInt(4, 0);
            preparedStatement.setInt(5, canti);
            preparedStatement.setString(6, "");
            preparedStatement.executeUpdate();
            return 1;
        }
        catch (Exception e)
        {
            System.out.println(e);
            return -1;
        }
    }

    public static int agregarOrden(String usuario,String trans, String señas, String longt, String lat, String date,String extraSigns, ArrayList<TodoPedido> carrito){
        try
        {
            if(extraSigns.equals(""))
                extraSigns = "No se ha proporcionado información adicional.";
            Connection myDbConn = new Conexion().getConexion();
            PreparedStatement preparedStatement = myDbConn.prepareStatement("Insert into ordenes(id_usuario,id_transportista,estado,descripcion,longitud,latitud,fecha,infoExtra) values(?, ?, ?, ?, ?, ?, ?,?);");
            preparedStatement.setString(1, usuario);
            preparedStatement.setString(2, trans);
            preparedStatement.setInt(3, 0);
            preparedStatement.setString(4, señas);
            preparedStatement.setString(5, longt);
            preparedStatement.setString(6, lat);
            preparedStatement.setString(7, date);
            preparedStatement.setString(8, extraSigns);
            preparedStatement.executeUpdate();
            String ord = getLastOrden();
            for(TodoPedido x : carrito){
                String estId = x.id;
                for(Producto y : x.productos){
                    agregarItemAOrden(ord,estId,y.nombre,y.cantidad);
                }
            }
            return 1;
        }
        catch (Exception e)
        {
            System.out.println(e);
            return -1;
        }
    }

    public static int solicitarOrden(String id, String transportista, String costo){
        try
        {
            int idOrd = Integer.parseInt(id);
            int cost = Integer.parseInt(costo);
            Connection myDbConn = new Conexion().getConexion();
            PreparedStatement preparedStatement = myDbConn.prepareStatement("insert into solicitantexpedido (idOrden,transportista,estado,oferta) values(?, ?, ?,?);");
            preparedStatement.setInt(1, idOrd);
            preparedStatement.setString(2, transportista);
            preparedStatement.setInt(3, 0);
            preparedStatement.setFloat(4, cost);
            preparedStatement.executeUpdate();
            return 1;
        }
        catch (Exception e)
        {
            System.out.println(e);
            return -1;
        }
    }

    public static String getLastOrden(){
        try {
            Connection myDbConn = new Conexion().getConexion();
            Statement statement = myDbConn.createStatement();
            ResultSet results = statement.executeQuery("SELECT idOrden FROM ordenes ORDER BY idOrden DESC LIMIT 1;");
            while (results.next()) {
                return results.getString(1);
            }
            return "1";
        }
        catch (Exception e)
        {
            System.out.println(e);
            return "-1";
        }
    }

    public static ArrayList<PedidoBasico> getAllPedidos(String actualUser){
        try
        {
            Connection myDbConn = new Conexion().getConexion();
            Statement statement = myDbConn.createStatement();
            ResultSet results = statement.executeQuery("SELECT idOrden,descripcion,id_usuario,fecha, usuario.nombre, usuario.apellidos from ordenes as ord INNER JOIN usuario on ord.id_usuario = usuario.email where estado = 0 and (id_transportista = \""+actualUser+"\" or id_transportista = \"BN\") and NOT EXISTS (select 1 from solicitantexpedido as txo where txo.idOrden = ord.idOrden and transportista = \""+actualUser+"\");");
            ArrayList<PedidoBasico> pedidos = new ArrayList<PedidoBasico>();
            while (results.next())
            {
                String nombreCliente = results.getString(5)+" "+results.getString(6);
                PedidoBasico found = new PedidoBasico(results.getString(1),results.getString(2),nombreCliente,results.getString(4));
                pedidos.add(found);
            }
            return pedidos;
        }
        catch (Exception e)
        {
            System.out.println(e);
            return new ArrayList<PedidoBasico>();
        }
    }

    public static ArrayList<PedidoSol> getMyPedidos(String actualUser){
        try
        {
            Connection myDbConn = new Conexion().getConexion();
            Statement statement = myDbConn.createStatement();
            ResultSet results = statement.executeQuery("SELECT ordenes.idOrden, descripcion, id_usuario, fecha, sols  from ordenes  inner join solicitantesxOrden as sxo on sxo.idOrden = ordenes.idOrden where id_usuario=\""+actualUser+"\" and estado=0;");
            ArrayList<PedidoSol> pedidos = new ArrayList<PedidoSol>();
            while (results.next())
            {
                PedidoSol found = new PedidoSol(results.getString(1),results.getString(2),results.getString(3),results.getString(4),results.getString(5));
                pedidos.add(found);
            }
            return pedidos;
        }
        catch (Exception e)
        {
            System.out.println(e);
            return new ArrayList<PedidoSol>();
        }
    }

    public static ArrayList<PedidoCurso> getMyPedidosSolicitados(String actualUser){
        try
        {
            Connection myDbConn = new Conexion().getConexion();
            Statement statement = myDbConn.createStatement();
            ResultSet results = statement.executeQuery("SELECT ord.idOrden,descripcion,id_usuario,fecha, usuario.nombre, usuario.apellidos, sxp.oferta from ordenes as ord INNER JOIN usuario on ord.id_usuario = usuario.email inner join solicitantexpedido as sxp on ord.idOrden = sxp.idOrden where ord.estado = 0 and sxp.transportista = '"+actualUser+"';");
            ArrayList<PedidoCurso> pedidos = new ArrayList<PedidoCurso>();
            while (results.next())
            {
                String nombreCliente = results.getString(5)+" "+results.getString(6);
                PedidoCurso found = new PedidoCurso(results.getString(1),results.getString(2),nombreCliente,results.getString(4),actualUser,results.getString(7));
                pedidos.add(found);
            }
            return pedidos;
        }
        catch (Exception e)
        {
            System.out.println(e);
            return new ArrayList<PedidoCurso>();
        }
    }

    public static ArrayList<PedidoCurso> getMyPedidosAceptados(String actualUser){
        try
        {
            Connection myDbConn = new Conexion().getConexion();
            Statement statement = myDbConn.createStatement();
            ResultSet results = statement.executeQuery("SELECT ord.idOrden,descripcion,id_usuario,fecha, usuario.nombre, usuario.apellidos, sxp.oferta from ordenes as ord INNER JOIN usuario on ord.id_usuario = usuario.email inner join solicitantexpedido as sxp on ord.idOrden = sxp.idOrden and sxp.transportista = '"+actualUser+"' where ord.estado = 1 and id_transportista = '"+actualUser+"';");
            ArrayList<PedidoCurso> pedidos = new ArrayList<PedidoCurso>();
            while (results.next())
            {
                String nombreCliente = results.getString(5)+" "+results.getString(6);
                PedidoCurso found = new PedidoCurso(results.getString(1),results.getString(2),nombreCliente,results.getString(4),actualUser,results.getString(7));
                pedidos.add(found);
            }
            return pedidos;
        }
        catch (Exception e)
        {
            System.out.println(e);
            return new ArrayList<PedidoCurso>();
        }
    }

    public static ArrayList<PedidoCurso> getMyPedidosCurso(String actualUser){
        try
        {
            Connection myDbConn = new Conexion().getConexion();
            Statement statement = myDbConn.createStatement();
            ResultSet results = statement.executeQuery("select ord.idOrden,descripcion,fecha,nombre,apellidos,oferta from ordenes as ord inner join usuario on id_transportista = email inner join solicitantexpedido as sxp on sxp.idOrden = ord.idOrden where ord.estado = 1 and id_usuario = \""+actualUser+"\";");
            ArrayList<PedidoCurso> pedidos = new ArrayList<PedidoCurso>();
            while (results.next())
            {
                String t_name = results.getString(4)+" "+results.getString(5);
                PedidoCurso found = new PedidoCurso(results.getString(1),results.getString(2),actualUser,results.getString(4),t_name,results.getString(6));
                pedidos.add(found);
            }
            return pedidos;
        }
        catch (Exception e)
        {
            System.out.println(e);
            return new ArrayList<PedidoCurso>();
        }
    }

    public static ArrayList<Solicitante> getSolicitantes(String idOrden){
        try
        {
            Connection myDbConn = new Conexion().getConexion();
            Statement statement = myDbConn.createStatement();
            ResultSet results = statement.executeQuery("SELECT transportista,oferta,usuario.nombre, usuario.apellidos from solicitanteXpedido INNER JOIN usuario on transportista = usuario.email where idOrden="+idOrden+";");
            ArrayList<Solicitante> pedidos = new ArrayList<Solicitante>();
            while (results.next())
            {
                String nombreSol = results.getString(3)+" "+results.getString(4);
                Solicitante found = new Solicitante(results.getString(1),nombreSol,"5",results.getString(2));
                pedidos.add(found);
            }
            return pedidos;
        }
        catch (Exception e)
        {
            System.out.println(e);
            return new ArrayList<Solicitante>();
        }
    }

    public static PedidoDetallado getPedido(String id){
        try
        {
            Connection myDbConn = new Conexion().getConexion();
            Statement statement = myDbConn.createStatement();
            ResultSet results = statement.executeQuery("SELECT descripcion,id_usuario,longitud,latitud,fecha, usuario.nombre, usuario.apellidos, infoExtra from ordenes INNER JOIN usuario on ordenes.id_usuario = usuario.email where idOrden = "+id+";");
            ArrayList<TodoPedido> pedido = generateCarrito(id);
            while (results.next())
            {
                String nombreCliente = results.getString(6)+" "+results.getString(7);
                return new PedidoDetallado(id,results.getString(1),nombreCliente,results.getString(4),results.getString(3),results.getString(8),pedido);
            }
            return new PedidoDetallado("","","","0.0","0.0","",new ArrayList<TodoPedido>());
        }
        catch (Exception e)
        {
            System.out.println(e);
            return new PedidoDetallado("","","","0.0","0.0","",new ArrayList<TodoPedido>());
        }
    }

    public static int definirTransportista(String idOrden, String transportista){
        try
        {
            int idOrd = Integer.parseInt(idOrden);
            Connection myDbConn = new Conexion().getConexion();
            PreparedStatement preparedStatement = myDbConn.prepareStatement("update ordenes set id_transportista = ?, estado = 1 where idOrden = ?");
            preparedStatement.setString(1, transportista);
            preparedStatement.setInt(2, idOrd);
            preparedStatement.executeUpdate();
            return 1;
        }
        catch (Exception e)
        {
            System.out.println(e);
            return -1;
        }
    }

    public static int obtenerProducto(String idOrden, String establecimiento, String producto){
        try
        {
            int idOrd = Integer.parseInt(idOrden);
            int idStab = Integer.parseInt(establecimiento);
            Connection myDbConn = new Conexion().getConexion();
            PreparedStatement preparedStatement = myDbConn.prepareStatement("update itemxorden set estado = 1 where orden = ? and establecimiento = ? and nombreProducto = ?;");
            preparedStatement.setInt(1, idOrd);
            preparedStatement.setInt(2, idStab);
            preparedStatement.setString(3, producto);
            preparedStatement.executeUpdate();
            return 1;
        }
        catch (Exception e)
        {
            System.out.println(e);
            return -1;
        }
    }

    public static int perderProducto(String idOrden, String establecimiento, String producto){
        try
        {
            int idOrd = Integer.parseInt(idOrden);
            int idStab = Integer.parseInt(establecimiento);
            Connection myDbConn = new Conexion().getConexion();
            PreparedStatement preparedStatement = myDbConn.prepareStatement("update itemxorden set estado = 0 where orden = ? and establecimiento = ? and nombreProducto = ?;");
            preparedStatement.setInt(1, idOrd);
            preparedStatement.setInt(2, idStab);
            preparedStatement.setString(3, producto);
            preparedStatement.executeUpdate();
            return 1;
        }
        catch (Exception e)
        {
            System.out.println(e);
            return -1;
        }
    }

    public static ArrayList<TodoPedido> generateCarrito(String id){
        try
        {
            Connection myDbConn = new Conexion().getConexion();
            Statement statement = myDbConn.createStatement();
            ResultSet results = statement.executeQuery("SELECT nombreProducto,establecimiento,cantidad,estab.nombre from itemxorden as ixo inner join establecimiento as estab on ixo.establecimiento = estab.id where orden ="+id+";");
            ArrayList<TodoPedido> pedido = new ArrayList<TodoPedido>();
            while (results.next())
            {
                String idStab = results.getString(2);
                TodoPedido exist = TodoPedido.findId(idStab,pedido);
                if(exist==null){
                    String name = results.getString(4);
                    TodoPedido nuevo = new TodoPedido(idStab,name);
                    nuevo.addProduct(results.getString(1),results.getString(3));
                    pedido.add(nuevo);
                }
                else{
                    exist.addProduct(results.getString(1),results.getString(3));
                }
            }
            return pedido;
        }
        catch (Exception e)
        {
            System.out.println(e);
            return new ArrayList<TodoPedido>();
        }
    }

    public static ArrayList<ListaPedido> generateCarritoDet(String id){
        try
        {
            Connection myDbConn = new Conexion().getConexion();
            Statement statement = myDbConn.createStatement();
            ResultSet results = statement.executeQuery("SELECT nombreProducto,ixo.establecimiento,cantidad,est.nombre,estado from itemxorden as ixo inner join establecimiento as est on ixo.establecimiento = est.id where orden ="+id+";");
            ArrayList<ListaPedido> pedido = new ArrayList<ListaPedido>();
            while (results.next())
            {
                String idStab = results.getString(2);
                ListaPedido exist = ListaPedido.findId(idStab,pedido);
                if(exist==null){
                    String name = results.getString(4);
                    ListaPedido nuevo = new ListaPedido(idStab,name);
                    nuevo.addProduct(results.getString(1),results.getString(3),results.getString(5));
                    pedido.add(nuevo);
                }
                else{
                    exist.addProduct(results.getString(1),results.getString(3),results.getString(5));
                }
            }
            return pedido;
        }
        catch (Exception e)
        {
            System.out.println(e);
            return new ArrayList<ListaPedido>();
        }
    }
}
