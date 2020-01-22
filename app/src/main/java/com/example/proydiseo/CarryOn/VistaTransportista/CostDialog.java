package com.example.proydiseo.CarryOn.VistaTransportista;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proydiseo.CarryOn.Modelo.PedidoData;
import com.example.proydiseo.R;

public class CostDialog extends AppCompatDialogFragment {
    private int resultado;
    private static String correo;
    private static String idPedido;
    private EditText costoDef;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.costo_dialog,null);

        builder.setView(view)
                .setNegativeButton("volver", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setPositiveButton("Solicitar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
        });

        costoDef = view.findViewById(R.id.editText2);

        Dialog dialog = builder.create();
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getContext().getResources().getColor(R.color.lightColor));
        ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prepareSolicitud();
            }
        });
        ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getContext().getResources().getColor(R.color.offColor));
        dialog.dismiss();
        return dialog;
    }

    public static void defineValues(String correo1, String idP){
        correo = correo1;
        idPedido = idP;
    }

    private void prepareSolicitud(){
        if(!costoDef.getText().toString().equals(""))
            new pedirSolicitud().execute();
        else
            Toast.makeText(getActivity(), "Por favor ingrese la cantidad que se cobrará", Toast.LENGTH_SHORT).show();
    }

    private class pedirSolicitud extends AsyncTask<Object, Object, Cursor> {

        @Override
        protected Cursor doInBackground(Object... params) {
            try
            {
                resultado = PedidoData.solicitarOrden(idPedido,correo,costoDef.getText().toString());
                return null;
            }
            catch (Exception e)
            { e.printStackTrace();
                return null;}

        }

        @Override
        protected void onPostExecute(Cursor result) {
            finishSolicitud();
        }
    }

    private void finishSolicitud(){
        if(resultado==1) {
            Toast.makeText(getContext(), "Se ha solicitado realizar el pedido", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), SeguiT.class);
            intent.putExtra("USER_MAIL", correo);
            startActivity(intent);
            getActivity().overridePendingTransition(0, 0);
        }
        else{
            Toast.makeText(getActivity(), "Ocurrio un error a la hora de solicitar el pedido", Toast.LENGTH_SHORT).show();
        }
    }
}
