package com.example.proydiseo.CarryOn.Modelo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.example.proydiseo.BuildConfig;
import com.example.proydiseo.CarryOn.Controlador.TodoPedido;
import com.example.proydiseo.R;
import com.sun.mail.smtp.SMTPTransport;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.*;

public class GmailAPI {
    private static String endHTML1 = "<head>"+
            "<meta content=\"text/html; charset=utf-8\" http-equiv=\"Content-Type\">"+
            "<meta content=\"width=device-width\" name=\"viewport\">"+
            "<link href=\"https://fonts.googleapis.com/css?family=Poppins&display=swap\" rel=\"stylesheet\">"+
            "<style type=\"text/css\">"+
            ""+
            ""+
            "    "+
            "    @media screen and (max-width: 2000px) {"+
            "        .divInside{"+
            "            width:50%;"+
            "        }"+
            "    }"+
            ""+
            "    @media screen and (max-width: 800px) {"+
            "        .divInside{"+
            "            width:70%;"+
            "        }"+
            "    }"+
            ""+
            "    @media screen and (max-width: 500px) {"+
            "        .divInside{"+
            "            width:90%;"+
            "        }"+
            "    }"+
            ""+
            "</style>"+
            "<head/>"+
            ""+
            "<body style=\"margin: 0\">"+
            "	<div id=\"header\" style=\"background: #f5f5f5\">"+
            "    	<div class=\"divInside\" style=\"background: #f5f5f5;  margin: auto;  width: 60%\">"+
            "           <h1 style=\"color:#646464;"+
            "                    font-family: 'Poppins', sans-serif;"+
            "                    font-size: 30px;"+
            "                    font-weight: bold;"+
            "                    text-align:center;"+
            "                     margin:auto; padding-top: 20px; padding-bottom: 20px;\""+
            "        >CarryOn ®</h1>"+
            "        </div>"+
            "	</div>"+
            "    <div id=\"body\" style=\"background: white\">"+
            "    	<div  class=\"divInside\" style=\"background: white; margin: auto; width: 60%\">"+
            "            <br>"+
            "            <h1 style=\"color:#646464;"+
            "                    font-family: 'Poppins', sans-serif;"+
            "                    font-size: 30px;"+
            "                    font-weight: bold;"+
            "                    text-align:center;\" >"; private static String endHtml4 = ", gracias por usar CarryOn</h1>"+
            "            <br>"+
            "            <h1 style=\"color:#646464;"+
            "                        font-family: 'Poppins', sans-serif;"+
            "                        font-size: 20px;"+
            "                        font-weight: normal;"+
            "                        text-align:justify;\">Te desglosamos la lista de artículos de tu pedido: <b class=\"bold\">Cena para la familia</b></h1>"+
            "            <br>"+
            "            <div>"+
            "                <table style=\"width:100%;\" >"+
            "                      <thead style=\""+
            "                        color:#646464;"+
            "                        font-family: 'Poppins', sans-serif;"+
            "                        font-size: 20px;"+
            "                        font-weight: normal;"+
            "                        text-align:justify;"+
            "                        border-bottom: 1px solid #646464;"+
            "                        border-collapse: collapse;"+
            "                        \""+
            "                       >"+
            "                        <tr>"+
            "                          <th scope=\"col\">Producto</th>"+
            "                          <th scope=\"col\">Cantidad</th>"+
            "                          <th scope=\"col\">Establecimiento</th>"+
            "                        </tr>"+
            "                      </thead>"+
            "                      <tbody style=\""+
            "                        color:#646464;"+
            "                        font-family: 'Poppins', sans-serif;"+
            "                        font-size: 20px;"+
            "                        font-weight: normal;\">"+
            "                        ";
    private static String endHTML2 =
            "                      </tbody>"+
            "                    </table>"+
            ""+
            "            </div>"+
            "            <br>"+
            "            <br>"+
            "            <h1 style=\"color:#69f0ae;"+
            "                    font-family: 'Poppins', sans-serif;"+
            "                    font-size: 25px;"+
            "                    font-weight: bold;"+
            "                    text-align:right;\" >Costo de envío: ₡<b class=\"bold\">";private static String endHtml3 = "</b> </h1>"+
            "            <br>"+
            "            <h1 style=\"color:#646464;"+
            "                        font-family: 'Poppins', sans-serif;"+
            "                        font-size: 25px;"+
            "                        font-weight: normal;"+
            "                        text-align:justify;\">No te olvides de calificar este envío dentro de la aplicación, en la sección de pedidos finalizados.</h1>"+
            "            <br>"+
            "            <br>"+
            "        </div>"+
            "    </div>"+
            "    "+
            "    <div id=\"footer\" style=\"background: #69f0ae \">"+
            "	   <div  class=\"divInside\" style=\"background: #69f0ae; margin: auto; width: 60%;\">"+
            "            <br>"+
            "            <br>"+
            "            <div class=\"container\">"+
            "              <div class=\"row\">"+
            "                <div class=\"col-sm-6\">"+
            "                  <h1 style=\"color:#646464;"+
            "                        font-family: 'Poppins', sans-serif;"+
            "                        font-size: 18px;"+
            "                        font-weight: bold;"+
            "                        text-align:left;\">Acerca de CarryOn</h1>"+
            "                  <h1 style=\"color:#646464;"+
            "                            font-family: 'Poppins', sans-serif;"+
            "                            font-size: 18px;"+
            "                            font-weight: normal;"+
            "                            text-align:justify;\""+
            "                    >Con CarryOn no solo estamos modificando la manera en que haces pedidos, si no que también generamos un canal único de comunicación entre clientes y transportistas.</h1>"+
            "                   "+
            "                </div>"+
            "                <br>"+
            "                <div class=\"col-sm-6\">"+
            "                    <div class=\"divFooter\"><h1 style=\"color:#646464;"+
            "                            font-family: 'Poppins', sans-serif;"+
            "                            font-size: 18px;"+
            "                            font-weight: normal;"+
            "                            text-align:justify;\"><b>Dirección: </b>Limón, Costa Rica</h1></div>"+
            "                    <div class=\"divFooter\"><h1 style=\"color:#646464;"+
            "                            font-family: 'Poppins', sans-serif;"+
            "                            font-size: 18px;"+
            "                            font-weight: normal;"+
            "                            text-align:justify;\"><b>Teléfono: </b>+506 6050 1958</h1></div>"+
            "                    <div class=\"divFooter\"> <h1 style=\"color:#646464;"+
            "                            font-family: 'Poppins', sans-serif;"+
            "                            font-size: 18px;"+
            "                            font-weight: normal;"+
            "                            text-align:justify;\" ><b>Correo: </b>carryoncr@gmail.com</h1></div>"+
            "                    <br>"+
            ""+
            "                </div>"+
            "              </div>"+
            "              "+
            "            </div>"+
            "            <br>"+
            ""+
            "            <h1 style=\"color:#646464;"+
            "                    font-family: 'Poppins', sans-serif;"+
            "                    font-size: 15px;"+
            "                    font-weight: bold;"+
            "                    text-align:center;\""+
            "            >CarryOn® 2020 </h1>"+
            "            <br>"+
            "            <br>"+
            "        </div>"+
            "    </div>"+
            "</body>";



    private static String msghtml = "<!DOCTYPE html>"+
            "<head>"+
            "<meta content=\"text/html; charset=utf-8\" http-equiv=\"Content-Type\">"+
            "<meta content=\"width=device-width\" name=\"viewport\">"+
            "<link href=\"https://fonts.googleapis.com/css?family=Poppins&display=swap\" rel=\"stylesheet\">"+
            ""+
            "<link href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh\" crossorigin=\"anonymous\">"+
            "<script src=\"https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js\" integrity=\"sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6\" crossorigin=\"anonymous\"></script>"+
            "<script src=\"https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.bundle.min.js\" integrity=\"sha384-6khuMg9gaYr5AxOqhkVIODVIvm9ynTT5J4V1cfthmT+emCG6yVmEZsRHdxlotUnm\" crossorigin=\"anonymous\"></script>"+
            "<script src='https://kit.fontawesome.com/a076d05399.js'></script>"+
            "<link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css\">"+
            "<style type=\"text/css\">"+
            ""+
            ""+
            "    @media screen and (max-width: 2000px) {"+
            "    .divInside{"+
            "        width:50%;"+
            "    }"+
            ""+
            "    @media screen and (max-width: 800px) {"+
            "    .divInside{"+
            "        width:70%;"+
            "    }"+
            ""+
            "    @media screen and (max-width: 500px) {"+
            "    .divInside{"+
            "        width:90%;"+
            "    }"+
            ""+
            "}"+
            ""+
            "</style>"+
            "<head/>"+
            ""+
            "<body style=\"margin: 0\">"+
            "	<div id=\"header\" style=\"background: #f5f5f5\">"+
            "    	<div class=\"divInside\" style=\"background: #f5f5f5;  margin: auto; width: 50%;\">"+
            "    	"+
            "        <h1 style=\"color:#646464;"+
            "    				font-family: 'Poppins', sans-serif;"+
            "			        font-size: 30px;"+
            "			        font-weight: bold;"+
            "			        text-align:center;"+
            "			         margin:auto; padding-top: 20px; padding-bottom: 20px;\""+
            "		>CarryOn ®</h1>"+"        </div>"+
            "	</div>"+
            "    <div id=\"body\" style=\"background: white\">"+
            "    	<div  class=\"divInside\" style=\"background: white; margin: auto; width: 50%;\">"+
            "            <br>"+
            "            <h1 style=\"color:#646464;"+
            "        				font-family: 'Poppins', sans-serif;"+
            "				        font-size: 25px;"+
            "				        font-weight: bold;"+
            "				        text-align:center;\""+
            "			>Recuperación de contraseña</h1>"+
            "            <br>"+
            "            <h1 style=\"color:#646464;"+
            "			        font-family: 'Poppins', sans-serif;"+
            "			        font-size: 18px;"+
            "			        font-weight: normal;"+
            "			        text-align:justify;\""+
            "        	>Usted recibió este correo electrónico porque solicitó un restablecimiento de contraseña para su cuenta de CarryOn.</h1>"+
            "            <br>"+
            "            <h1 style=\"color:#646464;"+
            "			        font-family: 'Poppins', sans-serif;"+
            "			        font-size: 18px;"+
            "			        font-weight: normal;"+
            "			        text-align:justify;\">Utilice el siguiente codigo dentro de la aplicación para restablecer su contraseña.</h1>"+
            "            <br>"+
            "            <h1 style=\" color:#69f0ae;"+
            "				        font-family: 'Poppins', sans-serif;"+
            "				        font-size: 25px;"+
            "				        font-weight: bold;"+
            "				        text-align:center;\" "+
            "			        >";
        private static String html2 = "</h1>"+
            "            <br>"+
            "            <br>"+
            "        </div>"+
            "    </div>"+
            "    "+
            "    <div id=\"footer\" style=\"background: #69f0ae\">"+
            "	   <div  class=\"divInside\" style=\"background: #69f0ae; margin: auto; width: 50%;\">"+
            "            <br>"+
            "            <br>"+
            "            <div class=\"container\">"+
            "              <div class=\"row\">"+
            "              	<div class=\"col-sm-6\">"+
            "                  <h1 style=\"color:#646464;"+
            "				        font-family: 'Poppins', sans-serif;"+
            "				        font-size: 15px;"+
            "				        font-weight: bold;"+
            "				        text-align:left;\">Acerca de CarryOn</h1>"+
            "                  <h1 style=\"color:#646464;"+
            "					        font-family: 'Poppins', sans-serif;"+
            "					        font-size: 15px;"+
            "					        font-weight: normal;"+
            "					        text-align:justify;\""+
            "					>Con CarryOn no solo estamos modificando la manera en que haces pedidos, si no que también generamos un canal único de comunicación entre clientes y transportistas.</h1>"+
            "                   "+
            "                </div>"+
            "                <br>"+
            "                <div class=\"col-sm-6\">"+
            "                    <div class=\"divFooter\"><h1 style=\"color:#646464;"+
            "					        font-family: 'Poppins', sans-serif;"+
            "					        font-size: 15px;"+
            "					        font-weight: normal;"+
            "					        text-align:justify;\"><b>Dirección: </b>Limón, Costa Rica</h1></div>"+
            "                    <div class=\"divFooter\"><h1 style=\"color:#646464;"+
            "					        font-family: 'Poppins', sans-serif;"+
            "					        font-size: 15px;"+
            "					        font-weight: normal;"+
            "					        text-align:justify;\"><b>Teléfono: </b>+506 6050 1958</h1></div>"+
            "                    <div class=\"divFooter\"> <h1 style=\"color:#646464;"+
            "					        font-family: 'Poppins', sans-serif;"+
            "					        font-size: 15px;"+
            "					        font-weight: normal;"+
            "					        text-align:justify;\" ><b>Correo: </b>carryoncr@gmail.com</h1></div>"+
            "                    <br>"+
            ""+
            "                </div>"+
            "              </div>"+
            "              "+
            "            </div>"+
            "            <br>"+
            ""+
            "          	<h1 style=\"color:#646464;"+
            "    				font-family: 'Poppins', sans-serif;"+
            "			        font-size: 13px;"+
            "			        font-weight: bold;"+
            "			        text-align:center;\""+
            "			>CarryOn® 2020 </h1>"+
            "            <br>"+
            "            <br>"+
            "        </div>"+
            "    </div>"+
            "</body>";

    private static final String SMTP_SERVER = "smtp.gmail.com";
    private static final String USERNAME = "ratlinkgames@gmail.com";
    private static final String PASSWORD = "kevinaxel";

    private static final String EMAIL_FROM = "ratlinkgames@gmail.com";

    private static final String EMAIL_SUBJECT = "Modificación de contraseña";
    private static final String EMAIL_TEXT = "El codigo para modificar su contraseña es: \n";

    public static int sendEndMail(String userMail,String Code, String Usuario,String precio){
        Properties prop = System.getProperties();
        prop.put("mail.smtp.host", SMTP_SERVER); //optional, defined in SMTPTransport
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.port", "587"); // default port 25

        Session session = Session.getInstance(prop, null);
        Message msg = new MimeMessage(session);


        String item1 = "<tr style=\"border-bottom: 1px solid #f5f5f5;"+
                "                                    border-collapse: collapse;\" >"+
                "                          <td style=\" padding-bottom: 15px; padding-top:  15px;\">"; String item2 ="</td>"+
                "                          <td style=\" padding-bottom: 15px; padding-top:  15px;\">";String item3 = "</td>"+
                "                          <td style=\" padding-bottom: 15px; padding-top:  15px;\">";String item4 ="</td>"+
                "                        </tr>";


        String infoPedido="";
        ArrayList<TodoPedido> datos = PedidoData.generateCarrito("5");
        for(int i=0;i<datos.size();i++){
            String nombre =datos.get(i).nombre;
            for(int y=0;y<datos.get(i).productos.size();y++)
            infoPedido+= item1+datos.get(i).productos.get(y).nombre+item2+datos.get(i).productos.get(y).cantidad+item3+nombre+item4;
        }

        try {
            // from
            msg.setFrom(new InternetAddress(EMAIL_FROM));

            // to
            msg.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(userMail, false));

            // subject
            msg.setSubject("Has finalizado un pedido con CarryOn");

            // content
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(
                    endHTML1+Usuario+endHtml4+infoPedido+endHTML2+precio+endHtml3,
                    "text/html; charset=utf-8");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            msg.setContent(multipart);

            //msg.setText(EMAIL_TEXT+code);


            msg.setSentDate(new Date());

            // Get SMTPTransport
            SMTPTransport t = (SMTPTransport) session.getTransport("smtp");

            // connect
            t.connect(SMTP_SERVER, USERNAME, PASSWORD);

            // send
            t.sendMessage(msg, msg.getAllRecipients());

            System.out.println("Response: " + t.getLastServerResponse());

            t.close();

            return 1;

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static int sendPassMail(String userMail, String code){
        Properties prop = System.getProperties();
        prop.put("mail.smtp.host", SMTP_SERVER); //optional, defined in SMTPTransport
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.port", "587"); // default port 25

        Session session = Session.getInstance(prop, null);
        Message msg = new MimeMessage(session);

        try {
            // from
            msg.setFrom(new InternetAddress(EMAIL_FROM));

            // to
            msg.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(userMail, false));

            // subject
            msg.setSubject(EMAIL_SUBJECT);

            // content
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(
                    msghtml+code+html2,
                    "text/html; charset=utf-8");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            msg.setContent(multipart);

            //msg.setText(EMAIL_TEXT+code);


            msg.setSentDate(new Date());

            // Get SMTPTransport
            SMTPTransport t = (SMTPTransport) session.getTransport("smtp");

            // connect
            t.connect(SMTP_SERVER, USERNAME, PASSWORD);

            // send
            t.sendMessage(msg, msg.getAllRecipients());

            System.out.println("Response: " + t.getLastServerResponse());

            t.close();

            return 1;

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }


}
