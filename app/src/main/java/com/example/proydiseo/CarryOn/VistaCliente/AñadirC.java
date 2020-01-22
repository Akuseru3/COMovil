package com.example.proydiseo.CarryOn.VistaCliente;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proydiseo.CarryOn.Controlador.Producto;
import com.example.proydiseo.CarryOn.Controlador.TodoPedido;
import com.example.proydiseo.CarryOn.Controlador.Validations;
import com.example.proydiseo.CarryOn.Modelo.PedidoData;
import com.example.proydiseo.CarryOn.SpecialListenerDouble;
import com.example.proydiseo.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AñadirC extends AppCompatActivity{
    private ImageButton btnPerfil;
    private ImageButton btnSegui;
    private Button btnAnadirP;
    private LinearLayout pedido;
    private TextView txtDir;
    private EditText producto;
    private EditText productoC;
    private int backButtonCount;
    private Button btnNuevo;
    private Button btnStabs;

    private int result;

    private String correo;


    private TextView tvStab;

    private ArrayList<TodoPedido> carrito = new ArrayList<TodoPedido>();


    private String stabId = "";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anadir_lay);
        if(Build.VERSION.SDK_INT >= 21){
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.backColor));
        }
        btnAnadirP = findViewById(R.id.btnAnadirP);
        btnAnadirP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anadirProducto();
            }
        });

        btnSegui = findViewById(R.id.btnSegui);
        btnSegui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirSeguimiento();
            }
        });

        tvStab = findViewById(R.id.tvEstab);

        btnNuevo = findViewById(R.id.btnAgregar);
        btnNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                procesarPedido();
            }
        });
        btnPerfil = findViewById(R.id.btnPerfil);
        btnPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirPerfil();
            }
        });

        btnStabs = findViewById(R.id.Stab);
        btnStabs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openStabs();
            }
        });

        producto = findViewById(R.id.txtProduct);
        productoC = findViewById(R.id.txtProduct2);
        pedido = findViewById(R.id.txtPedido);


        correo = getIntent().getStringExtra("USER_MAIL");

        fixLayouts();
    }

    private void openStabs(){
        Intent intent = new Intent(this, Establecimientos.class);
        startActivity(intent);
        this.overridePendingTransition(0, 0);
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

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        View current = getCurrentFocus();
        if (current != null) current.clearFocus();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            if (extras.containsKey("estab")) {
                stabId = extras.getString("id_estab");
                String selected = extras.getString("estab");
                tvStab.setText(selected);
            }
        }
    }

    private void anadirProducto(){
        String infoP = producto.getText().toString();
        String infoPC = productoC.getText().toString();
        if(!infoP.trim().equals("") && !infoPC.trim().equals("") && !tvStab.getText().toString().equals("")){
            TodoPedido exist = TodoPedido.findId(stabId,carrito);
            if(exist==null){
                TodoPedido nuevo = new TodoPedido(stabId,tvStab.getText().toString());
                nuevo.addProduct(infoP,infoPC);
                carrito.add(nuevo);
            }
            else{
                exist.addProduct(infoP,infoPC);
            }
            genPedido();
            NestedScrollView scrollP = findViewById(R.id.scrollView2);
            scrollP.fullScroll(View.FOCUS_DOWN);
            producto.setText("");
            productoC.setText("");
        }
        else{
            Toast.makeText(this, "Para añadir un producto al pedido se debe definir su establecimiento, nombre y cantidad.", Toast.LENGTH_SHORT).show();
        }
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
                int butId = View.generateViewId();

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

                Button butVer = new Button(this);
                butVer.setId(butId);
                butVer.setLayoutParams(paramsbt2);
                butVer.setText("X");
                butVer.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL);
                butVer.setBackground(ContextCompat.getDrawable(this,R.drawable.round_btn_redside));
                ViewGroup.LayoutParams rlp3 = butVer.getLayoutParams();
                rlp3.height = 50;
                rlp3.width = 50;
                butVer.setTextColor(this.getResources().getColor(R.color.pastelRed));
                butVer.setTypeface(typeface2);
                butVer.setTextSize(TypedValue.COMPLEX_UNIT_SP,10);
                butVer.setPadding(0,2,0,0);
                butVer.setMinHeight(0);
                butVer.setMinimumHeight(0);
                butVer.setOnClickListener(new SpecialListenerDouble(actual.id, inActual.nombre) {
                    @Override
                    public void onClick(View v) {
                        deleteProduct(id,id2);
                    }
                });
                lay2.addView(butVer);

                ConstraintSet csIn = new ConstraintSet();
                csIn.clone(lay2);
                csIn.connect(productId,ConstraintSet.LEFT,lay2.getId(),ConstraintSet.LEFT,30);
                csIn.connect(butId,ConstraintSet.RIGHT,lay2.getId(),ConstraintSet.RIGHT,40);
                csIn.applyTo(lay2);
                pedido.addView(lay2);
            }
        }
    }

    private void deleteProduct(String stabId, String product){
        TodoPedido exist = TodoPedido.findId(stabId,carrito);
        for(int i = 0; i<exist.productos.size();i++){
            if(exist.productos.get(i).nombre.equals(product)){
                exist.productos.remove(i);
                if(exist.productos.size()==0)
                    carrito.remove(exist);
                break;
            }
        }
        genPedido();
    }

    private void procesarPedido(){
        if(carrito.size()!=0){
            Intent intent = new Intent(this, ProcesarPedido.class);
            intent.putExtra("USER_MAIL",correo);
            intent.putParcelableArrayListExtra("USER_ORDER",carrito);
            startActivity(intent);
            this.overridePendingTransition(0, 0);
        }
        else
            Toast.makeText(this, "No se permite procesar un pedido vacío", Toast.LENGTH_SHORT).show();
    }

    private void abrirPerfil() {
        Intent intent = new Intent(this, PerfilC.class);
        intent.putExtra("USER_MAIL",correo);
        startActivity(intent);
        this.overridePendingTransition(0, 0);
    }

    private void abrirSeguimiento(){
        Intent intent = new Intent(this, SeguiC.class);
        intent.putExtra("USER_MAIL",correo);
        startActivity(intent);
        this.overridePendingTransition(0, 0);
    }

    public void fixLayouts(){
        final float scale = getResources().getDisplayMetrics().density;
        int pixels = (int) (114 * scale + 0.5f);
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        int screenHeight = size.y;
        ScrollView layout = (ScrollView) findViewById(R.id.swAddp);
        ViewGroup.LayoutParams params = layout.getLayoutParams();
        params.height = screenHeight - getStatusBarHeight() - pixels;
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

}
