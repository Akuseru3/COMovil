package com.example.proydiseo.CarryOn.VistaGeneral;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proydiseo.CarryOn.Modelo.Conexion;
import com.example.proydiseo.CarryOn.Modelo.LoginRegister;
import com.example.proydiseo.R;
import com.example.proydiseo.CarryOn.Controlador.Validations;

public class Register extends AppCompatActivity {
    private Button btnCrearCuenta;
    private ImageButton btnBack;
    private TextView txtNombre;
    private TextView txtApellidos;
    private TextView txtDia;
    private TextView txtMes;
    private TextView txtAño ;
    private TextView txtTelefono;
    private TextView txtCorreo;
    private TextView txtContra;
    private TextView txtContra2;
    private int tipo;

    private int resultLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro_lay);
        if(Build.VERSION.SDK_INT >= 21){
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.backColor));
        }

        btnCrearCuenta = findViewById(R.id.btnCrear);
        btnCrearCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarDatos();
            }
        });

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volverInicio();
            }
        });

        tipo = getIntent().getIntExtra("USER_TYPE",0);

        txtNombre = findViewById(R.id.editTextNombre);
        txtApellidos = findViewById(R.id.editTextApellidos);
        txtDia = findViewById(R.id.editTextD);
        txtMes = findViewById(R.id.editTextM);
        txtAño = findViewById(R.id.editTextA);
        txtTelefono = findViewById(R.id.editTextTelefono2);
        txtCorreo = findViewById(R.id.editTextCorreo);
        txtContra = findViewById(R.id.editTextPass);
        txtContra2 = findViewById(R.id.editTextPass2);
    }

    private void volverInicio(){
        Intent intent = new Intent(this, TypeSelection.class);
        startActivity(intent);
        this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void validarDatos(){
        String errores = "";
        errores = Validations.validateRegister(txtNombre.getText().toString(),txtApellidos.getText().toString(),txtDia.getText().toString(),txtMes.getText().toString(),txtAño.getText().toString(),txtTelefono.getText().toString(),txtCorreo.getText().toString(),txtContra.getText().toString(),txtContra2.getText().toString());
        if(errores.equals("")){
            new CreateAccount().execute();
        }
        else{
            Toast.makeText(this, errores, Toast.LENGTH_SHORT).show();
        }

    }

    private class CreateAccount extends AsyncTask<Object, Object, Cursor> {

        @Override
        protected Cursor doInBackground(Object... params) {
            try
            {
                String fecha = txtAño.getText().toString()+"-"+txtMes.getText().toString()+"-"+txtDia.getText().toString();
                int result = LoginRegister.addUser(txtCorreo.getText().toString(),txtContra.getText().toString(),txtNombre.getText().toString(),txtApellidos.getText().toString(),fecha,txtTelefono.getText().toString(),tipo);
                resultLog = result;
                return null;
            }
            catch (Exception e)
            { e.printStackTrace();
                return null;}

        }

        @Override
        protected void onPostExecute(Cursor result) {
            finishRegister(resultLog);
        }
    }

    private void finishRegister(int result){
        if(result == 1){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            Toast.makeText(this, "Cuenta creada exitosamente.", Toast.LENGTH_SHORT).show();
            this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
        else{
            Toast.makeText(this, "No se pudo conectar a la base de datos.", Toast.LENGTH_SHORT).show();
        }
    }

}
