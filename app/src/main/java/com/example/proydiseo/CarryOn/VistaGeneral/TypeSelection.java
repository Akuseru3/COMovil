package com.example.proydiseo.CarryOn.VistaGeneral;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.proydiseo.R;

public class TypeSelection extends AppCompatActivity {

    private Button btnTipoC;
    private Button btnTipoT;
    private Button btnContinue;
    private ImageButton goBack;
    private int chosen = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tipouser_lay);
        if(Build.VERSION.SDK_INT >= 21){
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.backColor));
        }

        btnTipoC = findViewById(R.id.btnTipoC);
        btnTipoC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipoCClick();
            }
        });

        btnTipoT = findViewById(R.id.btnTipoT);
        btnTipoT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipoTClick();
            }
        });

        btnContinue = findViewById(R.id.btnContReg);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                continueToRegister();
            }
        });

        goBack = findViewById(R.id.btnGoMain);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volverInicio();
            }
        });
    }

    private void tipoCClick(){
        btnTipoC.setBackground(getDrawable(R.drawable.round_btn_grn_thicc));
        btnTipoT.setBackground(getDrawable(R.drawable.round_btn_grey_thicc));
        chosen = 1;
    }

    private void tipoTClick(){
        btnTipoC.setBackground(getDrawable(R.drawable.round_btn_grey_thicc));
        btnTipoT.setBackground(getDrawable(R.drawable.round_btn_grn_thicc));
        chosen = 2;
    }

    private void continueToRegister(){
        if(chosen != 0) {
            Intent intent = new Intent(this, Register.class);
            intent.putExtra("USER_TYPE", chosen);
            startActivity(intent);
            this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
        else{
            Toast.makeText(this, "Debe elegir un tipo de cuenta para continuar.", Toast.LENGTH_SHORT).show();
        }
    }

    private void volverInicio(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
