package com.example.proydiseo.CarryOn.VistaCliente;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proydiseo.CarryOn.Controlador.Producto;
import com.example.proydiseo.CarryOn.Controlador.TodoPedido;
import com.example.proydiseo.CarryOn.Controlador.Validations;
import com.example.proydiseo.CarryOn.Modelo.PedidoData;
import com.example.proydiseo.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ProcesarPedido extends AppCompatActivity {
    private String correo;
    private ImageButton btnFavoritos;
    private TextView tvTrans;
    private Button btnMapS;
    private EditText txtNombre;
    private EditText señasExtra;
    private TextView txtDir;
    private Button agregarP;

    int result;

    private String transName = "Buscar Nuevo";
    private String transSeleccionado = "BN";

    private double latitud = 0.0;
    private double longitud = 0.0;

    private ArrayList<TodoPedido> carrito = new ArrayList<TodoPedido>();
    private LinearLayout pedido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finish_anadir);
        correo = getIntent().getStringExtra("USER_MAIL");
        carrito = getIntent().getParcelableArrayListExtra("USER_ORDER");
        pedido = findViewById(R.id.txtPedido);

        btnFavoritos = findViewById(R.id.btnFavoritos2);
        btnFavoritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirFav();
            }
        });

        tvTrans = findViewById(R.id.textTrans2);
        tvTrans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnShake();
            }
        });

        btnMapS = findViewById(R.id.btnMapSel5);
        btnMapS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMap();
            }
        });

        agregarP = findViewById(R.id.btnAgregar2);
        agregarP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nuevoPedido();
            }
        });

        txtNombre = findViewById(R.id.txtNombre6);
        señasExtra = findViewById(R.id.extraInfo5);
        txtDir = findViewById(R.id.twLocale5);

        genPedido();
    }

    private void genPedido(){
        pedido.removeAllViews();
        int last = 0;
        for(TodoPedido actual : carrito){
            int textId = View.generateViewId();

            ConstraintLayout lay = new ConstraintLayout(this);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            if(last!=0)
                params.addRule(RelativeLayout.BELOW,last);
            lay.setLayoutParams(params);
            ViewGroup.LayoutParams rlp = lay.getLayoutParams();
            last = View.generateViewId();
            lay.setId(last);
            rlp.width = ViewGroup.LayoutParams.MATCH_PARENT;
            rlp.height = ViewGroup.LayoutParams.WRAP_CONTENT;

            TextView title = new TextView(this);
            title.setId(textId);
            title.setText(actual.nombre);
            Typeface typeface = ResourcesCompat.getFont(this, R.font.pop_med);
            title.setTypeface(typeface);
            title.setTextColor(this.getResources().getColor(R.color.offColor));
            title.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
            lay.addView(title);


            ConstraintSet cs = new ConstraintSet();
            cs.clone(lay);
            cs.connect(textId,ConstraintSet.LEFT,lay.getId(),ConstraintSet.LEFT,15);
            cs.applyTo(lay);
            pedido.addView(lay);
            for(Producto inActual : actual.productos){
                int productId = View.generateViewId();

                ConstraintLayout lay2 = new ConstraintLayout(this);
                RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
                if(last!=0)
                    params1.addRule(RelativeLayout.BELOW,last);
                params1.setMargins(0,10,0,0);
                lay2.setLayoutParams(params1);
                ViewGroup.LayoutParams rlp2 = lay2.getLayoutParams();
                last = View.generateViewId();
                lay2.setId(last);
                rlp2.width = ViewGroup.LayoutParams.MATCH_PARENT;
                rlp2.height = ViewGroup.LayoutParams.WRAP_CONTENT;

                TextView title1 = new TextView(this);
                title1.setId(productId);
                title1.setText(inActual.cantidad+" - "+inActual.nombre);
                Typeface typeface2 = ResourcesCompat.getFont(this, R.font.pop_light);
                title1.setTypeface(typeface2);
                title1.setTextColor(this.getResources().getColor(R.color.offColor));
                title1.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
                lay2.addView(title1);

                RelativeLayout.LayoutParams paramsbt2 = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);

                ConstraintSet csIn = new ConstraintSet();
                csIn.clone(lay2);
                csIn.connect(productId,ConstraintSet.LEFT,lay2.getId(),ConstraintSet.LEFT,30);
                csIn.applyTo(lay2);
                pedido.addView(lay2);
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        View current = getCurrentFocus();
        if (current != null) current.clearFocus();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            if (extras.containsKey("lat")) {
                double latit = extras.getDouble("lat");
                double longit = extras.getDouble("long");
                String location = extras.getString("info");
                if (latit != 0.0 && longit != 0.0) {
                    longitud = longit;
                    latitud = latit;
                    txtDir.setText(location);
                } else{
                    longitud = 0.0;
                    latitud = 0.0;
                    txtDir.setText("Sin definir");
                }
            }
        }
    }

    private void abrirFav(){
        Intent intent = new Intent(this, Favoritos.class);
        startActivity(intent);
        this.overridePendingTransition(0, 0);
    }

    private void openMap(){
        Intent intent = new Intent(this, MapLocation.class);
        startActivity(intent);
        this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void btnShake(){
        btnFavoritos.startAnimation(AnimationUtils.loadAnimation(this,R.anim.shake_view));
    }

    private void nuevoPedido(){
        String errores = Validations.validatePedido(txtNombre.getText().toString(),latitud,longitud,carrito);
        if(errores.equals("")) {
            new createPedido().execute();
        }
        else
            Toast.makeText(this, errores, Toast.LENGTH_SHORT).show();
    }

    private class createPedido extends AsyncTask<Object, Object, Cursor> {

        @Override
        protected Cursor doInBackground(Object... params) {
            try
            {
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date();
                System.out.println(dateFormat.format(date));
                result = PedidoData.agregarOrden(correo,transSeleccionado,txtNombre.getText().toString(), Double.toString(longitud),Double.toString(latitud),dateFormat.format(date),señasExtra.getText().toString(),carrito);
                return null;
            }
            catch (Exception e)
            { e.printStackTrace();
                return null;}

        }

        @Override
        protected void onPostExecute(Cursor result) {
            finishCreation();
        }
    }

    private void finishCreation(){
        if(result==1) {
            Toast.makeText(this, "Pedido creado con exito", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, SeguiC.class);
            intent.putExtra("USER_MAIL", correo);
            startActivity(intent);
            this.overridePendingTransition(0, 0);
        }
        else{
            Toast.makeText(this, "Ocurrio un error a la hora de añadir el pedido", Toast.LENGTH_SHORT).show();
        }
    }

}

