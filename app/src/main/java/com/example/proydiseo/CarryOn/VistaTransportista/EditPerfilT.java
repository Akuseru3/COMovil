package com.example.proydiseo.CarryOn.VistaTransportista;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proydiseo.CarryOn.Controlador.Validations;
import com.example.proydiseo.CarryOn.Modelo.ExtraTools;
import com.example.proydiseo.CarryOn.Modelo.GmailAPI;
import com.example.proydiseo.CarryOn.Modelo.UserData;
import com.example.proydiseo.R;

public class EditPerfilT extends AppCompatActivity {

    private String correo;
    private String nombre;
    private String apellidos;
    private String telefono;
    private String fecha;

    private EditText tvNombre;
    private EditText tvApellidos;
    private EditText tvTelefono;
    private EditText tvD;
    private EditText tvM;
    private EditText tvA;

    private EditText tvCode;
    private EditText tvPass;
    private EditText tvPassR;

    private int result;

    private String actualCode = "";

    private Button btnSendCode;
    private Button btnModGeneral;
    private Button btnPassMod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_perfil_t);

        Intent in = getIntent();
        correo = in.getStringExtra("USER_MAIL");
        nombre = in.getStringExtra("USER_NAME");
        apellidos = in.getStringExtra("USER_LNAME");
        telefono = in.getStringExtra("USER_PHONE");
        fecha = in.getStringExtra("USER_BDAY");

        tvNombre = findViewById(R.id.editTextNombre);
        tvApellidos = findViewById(R.id.editTextApellidos);
        tvTelefono = findViewById(R.id.editTextTelefono);
        tvD = findViewById(R.id.editTextD);
        tvM = findViewById(R.id.editTextM);
        tvA = findViewById(R.id.editTextA);

        tvCode = findViewById(R.id.editTextCode);
        tvPass = findViewById(R.id.editTextPass);
        tvPassR = findViewById(R.id.editTextPass4);

        btnSendCode = findViewById(R.id.btnCodeMail);
        btnSendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendToMail();
            }
        });

        btnModGeneral = findViewById(R.id.btnModGen);
        btnModGeneral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarDatosMod();
            }
        });

        btnPassMod = findViewById(R.id.btnModPass);
        btnPassMod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarDatosContra();
            }
        });

        setActualInfo();
    }

    private void setActualInfo(){
        String[] result = fecha.split("-");
        String dia = result[2];
        String mes = result[1];
        String año = result[0];
        tvNombre.setText(nombre);
        tvApellidos.setText(apellidos);
        tvTelefono.setText(telefono);
        tvD.setText(dia);
        tvM.setText(mes);
        tvA.setText(año);
    }

    private void sendToMail(){
        actualCode = ExtraTools.generateCode();
        System.out.println(actualCode);
        new sendMail().execute();
    }

    private void validarDatosMod() {
        String errores = "";
        errores = Validations.validateUserMod(tvNombre.getText().toString(), tvApellidos.getText().toString(), tvD.getText().toString(), tvM.getText().toString(), tvA.getText().toString(), tvTelefono.getText().toString());
        if (errores.equals("")) {
            new updateUsuario().execute();
        } else {
            Toast.makeText(this, errores, Toast.LENGTH_SHORT).show();
        }
    }

    private void finishMod(){
        if(result==1){
            Toast.makeText(this, "Se ha modificado la información con exito.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, PerfilT.class);
            intent.putExtra("USER_MAIL",correo);
            startActivity(intent);
            this.overridePendingTransition(0, 0);
            finish();
        }
        else{
            Toast.makeText(this, "No se pudo conectar con la base de datos", Toast.LENGTH_SHORT).show();
        }
    }

    private void validarDatosContra(){
        if(tvCode.getText().toString().equals(actualCode) && !actualCode.equals("")){
            if(tvPass.getText().toString().equals(tvPassR.getText().toString()))
                new updatePassword().execute();
            else
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "El codigo ingresado es incorrecto", Toast.LENGTH_SHORT).show();
        }

    }

    private class sendMail extends AsyncTask<Object, Object, Cursor> {

        @Override
        protected Cursor doInBackground(Object... params) {
            try
            {
                GmailAPI.sendPassMail(correo,actualCode);

                return null;
            }
            catch (Exception e)
            { e.printStackTrace();
                return null;}

        }

        @Override
        protected void onPostExecute(Cursor result) {
            btnSendCode.setText("Reenviar código");
        }
    }

    private class updateUsuario extends AsyncTask<Object, Object, Cursor> {
        @Override
        protected Cursor doInBackground(Object... params) {
            try
            {
                String fecha = tvA.getText().toString()+"-"+tvM.getText().toString()+"-"+tvD.getText().toString();
                result = UserData.modifyUser(correo,tvNombre.getText().toString(),tvApellidos.getText().toString(),tvTelefono.getText().toString(),fecha);
                return null;
            }
            catch (Exception e)
            { e.printStackTrace();
                return null;}

        }

        @Override
        protected void onPostExecute(Cursor result) {
            finishMod();
        }
    }

    private class updatePassword extends AsyncTask<Object, Object, Cursor> {
        @Override
        protected Cursor doInBackground(Object... params) {
            try
            {
                result = UserData.modifyPassword(correo,tvPass.getText().toString());
                return null;
            }
            catch (Exception e)
            { e.printStackTrace();
                return null;}

        }

        @Override
        protected void onPostExecute(Cursor result) {
            finishModP();
        }
    }

    private void finishModP(){
        if(result==1){
            Toast.makeText(this, "Se ha cambiado la contraseña con exito.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, PerfilT.class);
            intent.putExtra("USER_MAIL",correo);
            startActivity(intent);
            this.overridePendingTransition(0, 0);
            finish();
        }
        else{
            Toast.makeText(this, "No se pudo conectar con la base de datos", Toast.LENGTH_SHORT).show();
        }
    }
}
