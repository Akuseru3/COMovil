package com.example.proydiseo.CarryOn.VistaTransportista;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.proydiseo.R;

public class ValoracionT extends AppCompatActivity {
    private ImageButton s1;
    private ImageButton s2;
    private ImageButton s3;
    private ImageButton s4;
    private ImageButton s5;
    private int valoracion;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.valoracion_t);
        View current = getCurrentFocus();
        findViewById(R.id.textView38).requestFocus();

        s1 = findViewById(R.id.star1);
        s1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                starTouch(1);
            }
        });

        s2 = findViewById(R.id.star2);
        s2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                starTouch(2);
            }
        });

        s3 = findViewById(R.id.star3);
        s3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                starTouch(3);
            }
        });

        s4 = findViewById(R.id.star4);
        s4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                starTouch(4);
            }
        });

        s5 = findViewById(R.id.star5);
        s5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                starTouch(5);
            }
        });

        id = getIntent().getStringExtra("ORD_ID");
    }

    private void starTouch(int star){
        cleanStars();
        if(star>=1){
            s1.setImageResource(R.drawable.star2);
            valoracion = 1;
        }
        if(star>=2){
            s2.setImageResource(R.drawable.star2);
            valoracion = 2;
        }
        if(star>=3){
            s3.setImageResource(R.drawable.star2);
            valoracion = 3;
        }
        if(star>=4){
            s4.setImageResource(R.drawable.star2);
            valoracion = 4;
        }
        if(star>=5){
            s5.setImageResource(R.drawable.star2);
            valoracion = 5;
        }
    }

    private void cleanStars(){
        s1.setImageResource(R.drawable.star1);
        s2.setImageResource(R.drawable.star1);
        s3.setImageResource(R.drawable.star1);
        s4.setImageResource(R.drawable.star1);
        s5.setImageResource(R.drawable.star1);
    }
}
