package com.example.proydiseo.CarryOn.VistaCliente;

import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.proydiseo.R;

public class Seguimiento extends AppCompatActivity {

    private Button btnList;
    private Button btnCons;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seguimiento_lay);
        btnList = findViewById(R.id.btnLista);
        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listClick();
            }
        });

        btnCons = findViewById(R.id.btnCons);
        btnCons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consultClick();
            }
        });

        fixBtns();
    }

    private void fixBtns(){
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        int screenWidth = size.x;
        ViewGroup.LayoutParams params = btnList.getLayoutParams();
        params.width = screenWidth/2;
        ViewGroup.LayoutParams params2 = btnCons.getLayoutParams();
        params2.width = screenWidth/2;
    }

    private void listClick(){
        btnCons.setBackgroundColor(this.getResources().getColor(R.color.whiteBack));
        btnList.setBackgroundColor(this.getResources().getColor(R.color.lightColor));
    }

    private void consultClick(){
        btnCons.setBackgroundColor(this.getResources().getColor(R.color.lightColor));
        btnList.setBackgroundColor(this.getResources().getColor(R.color.whiteBack));
    }
}
