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
import android.widget.EditText;
import android.widget.Toast;

import com.example.proydiseo.R;
import com.example.proydiseo.CarryOn.Modelo.Conexion;
import com.example.proydiseo.CarryOn.Modelo.LoginRegister;
import com.example.proydiseo.CarryOn.VistaCliente.PerfilC;
import com.example.proydiseo.CarryOn.VistaTransportista.PerfilT;

public class MainActivity extends AppCompatActivity {
    Window window = this.getWindow();

    private Button btnReg;
    private Button btnIniciar;
    private EditText correo;
    private EditText contra;
    private int backButtonCount;

    private int resultLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_lay);
        if(Build.VERSION.SDK_INT >= 21){
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.backColor));
        }
        correo = findViewById(R.id.editCorreo);
        contra = findViewById(R.id.editContra);

        String strCorreo = correo.toString();
        String strContra = contra.toString();

        btnReg = findViewById(R.id.btnRegistro);
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegistro();
            }
        });
        btnIniciar = findViewById(R.id.btnIniciar);
        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                entrarCuenta();
                /*if(correo.getText().toString().equals("cliente")) {
                    iniciar = "cliente";
                    resultados();
                }
                else{
                    iniciar = "transportista";
                    resultados();
                }*/
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        if(backButtonCount >= 1)
        {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(this, "Presione de nuevo para salir de BringMe.", Toast.LENGTH_SHORT).show();
            backButtonCount++;
        }
    }

    public void openRegistro(){
        Intent intent = new Intent(this, TypeSelection.class);
        startActivity(intent);
        this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    String iniciar = "";
    private void resultados(){
        System.out.println(iniciar + "RESULT2");
        if(iniciar.equals("cliente")){
            Intent intent = new Intent(this, PerfilC.class);
            intent.putExtra("user",correo.getText().toString());
            intent.putExtra("Name","");
            intent.putExtra("Card","");
            intent.putExtra("Month","");
            intent.putExtra("Year","");
            startActivity(intent);
            this.overridePendingTransition(0, 0);
        }
        else if(iniciar.equals("transportista")){
            Intent intent = new Intent(this, PerfilT.class);
            intent.putExtra("user",correo.getText().toString());
            startActivity(intent);
            this.overridePendingTransition(0, 0);
        }
        else{
            Toast.makeText(getApplicationContext(), iniciar, Toast.LENGTH_LONG).show();
        }
    }
    public void entrarCuenta(){
        btnIniciar.setText("Cargando...");
        btnIniciar.setEnabled(false);
        new CheckLogin().execute();
    }

    private class CheckLogin extends AsyncTask<Object, Object, Cursor> {

        @Override
        protected Cursor doInBackground(Object... params) {
            try
            {
                int result = LoginRegister.tryLogin(correo.getText().toString(),contra.getText().toString());
                resultLog = result;
                return null;
            }
            catch (Exception e)
            { e.printStackTrace();
                return null;}

        }

        @Override
        protected void onPostExecute(Cursor result) {
            enterApp(resultLog);
        }
    }

    private void enterApp(int result){
        if(result == -3) {
            btnIniciar.setText("Iniciar Sesión");
            btnIniciar.setEnabled(true);
            Toast.makeText(this, "No se pudo conectar a la base de datos.", Toast.LENGTH_SHORT).show();
        }
        else if(result == -2){
            btnIniciar.setText("Iniciar Sesión");
            btnIniciar.setEnabled(true);
            Toast.makeText(this, "El correo ingresado no esta vinculado a una cuenta CarryOn", Toast.LENGTH_SHORT).show();
        }
        else if(result == -1){
            btnIniciar.setText("Iniciar Sesión");
            btnIniciar.setEnabled(true);
            Toast.makeText(this, "El correo y la contraseña ingresada no coinciden", Toast.LENGTH_SHORT).show();
        }
        else if(result == 1){
            Intent intent = new Intent(this, PerfilC.class);
            intent.putExtra("USER_MAIL",correo.getText().toString());
            startActivity(intent);
            this.overridePendingTransition(0, 0);
        }
        else if(result ==2){
            Intent intent = new Intent(this, PerfilT.class);
            intent.putExtra("USER_MAIL",correo.getText().toString());
            startActivity(intent);
            this.overridePendingTransition(0, 0);
        }
        else{
            btnIniciar.setText("Iniciar Sesión");
            btnIniciar.setEnabled(true);
            Toast.makeText(this, "Ha ocurrido un error inesperado", Toast.LENGTH_SHORT).show();
        }

    }
}
