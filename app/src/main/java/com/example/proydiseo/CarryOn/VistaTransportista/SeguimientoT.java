package com.example.proydiseo.CarryOn.VistaTransportista;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proydiseo.CarryOn.Controlador.ListaPedido;
import com.example.proydiseo.CarryOn.Controlador.ProductoLista;
import com.example.proydiseo.CarryOn.Modelo.ConsultaData;
import com.example.proydiseo.CarryOn.Modelo.PedidoData;
import com.example.proydiseo.CarryOn.SpecialListener;
import com.example.proydiseo.CarryOn.SpecialListenerDouble;
import com.example.proydiseo.R;

import java.util.ArrayList;

public class SeguimientoT extends AppCompatActivity {
    private Button btnList;
    private Button btnCons;
    private Button btnCons2;
    private ImageButton btnMap;
    private String idOrden;
    private String correo;
    private ArrayList<ListaPedido> carroActual;
    private String stabID;
    private String prod;
    private LinearLayout all;
    private EditText titleC;
    private EditText detailsC;
    private int result;
    private Button btnCCons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seguimiento_t_lay);

        all = findViewById(R.id.allInfo);

        correo = getIntent().getStringExtra("USER_MAIL");
        idOrden = getIntent().getStringExtra("ORD_ID");

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
        btnCons2 = findViewById(R.id.btnCons2);
        btnCons2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consultViewerClick();
            }
        });
        fixBtns();
        loadLayout();
        listClick();
    }

    private void fixBtns(){
        final float scale = getResources().getDisplayMetrics().density;
        int pixels = (int) (48 * scale + 0.5f);
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        int screenWidth = size.x;
        ViewGroup.LayoutParams params = btnList.getLayoutParams();
        params.width = (screenWidth)/3;
        ViewGroup.LayoutParams params2 = btnCons.getLayoutParams();
        params2.width = (screenWidth)/3;
        ViewGroup.LayoutParams params3 = btnCons2.getLayoutParams();
        params3.width = (screenWidth)/3;
    }

    private void listClick(){
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        btnCons.setBackgroundColor(this.getResources().getColor(R.color.trueWhite));
        btnList.setBackgroundColor(this.getResources().getColor(R.color.lightColor));
        btnCons2.setBackgroundColor(this.getResources().getColor(R.color.trueWhite));
        new loadCarrito().execute();
    }

    private class loadCarrito extends AsyncTask<Object, Object, Cursor> {
        @Override
        protected Cursor doInBackground(Object... params) {
            try
            {
                carroActual = PedidoData.generateCarritoDet(idOrden);
                return null;
            }
            catch (Exception e)
            { e.printStackTrace();
                return null;}

        }

        @Override
        protected void onPostExecute(Cursor result) {
            cargarListaPedido();
        }
    }

    private void cargarListaPedido(){
        all.removeAllViews();
        int last = 0;
        for(ListaPedido actual : carroActual){
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
            lay.setBackground(ContextCompat.getDrawable(this,R.drawable.small_lay_custom_whgr_nostroke));

            TextView title = new TextView(this);
            title.setId(textId);
            title.setText("Productos de "+actual.nombre);
            Typeface typeface = ResourcesCompat.getFont(this, R.font.pop_bold);
            title.setTypeface(typeface);
            title.setTextColor(this.getResources().getColor(R.color.offColor));
            title.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
            lay.addView(title);


            ConstraintSet cs = new ConstraintSet();
            cs.clone(lay);
            cs.connect(textId,ConstraintSet.LEFT,lay.getId(),ConstraintSet.LEFT,15);
            cs.connect(textId,ConstraintSet.TOP,lay.getId(),ConstraintSet.TOP,30);
            cs.applyTo(lay);
            all.addView(lay);
            for(ProductoLista inActual : actual.productos){
                int productId = View.generateViewId();
                int cantId = View.generateViewId();
                int btId1 = View.generateViewId();

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
                lay2.setBackground(ContextCompat.getDrawable(this,R.drawable.small_lay_custom_wht));

                TextView title1 = new TextView(this);
                title1.setId(productId);
                title1.setText("Producto: "+inActual.nombre);
                Typeface typeface3 = ResourcesCompat.getFont(this, R.font.pop_med);
                Typeface typeface2 = ResourcesCompat.getFont(this, R.font.pop_light);
                title1.setTypeface(typeface3);
                title1.setTextColor(this.getResources().getColor(R.color.offColor));
                title1.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
                lay2.addView(title1);

                TextView title2 = new TextView(this);
                title2.setId(cantId);
                title2.setText("Cantidad: "+inActual.cantidad);
                title2.setTypeface(typeface2);
                title2.setTextColor(this.getResources().getColor(R.color.offColor));
                title2.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
                lay2.addView(title2);

                ImageButton butVer = new ImageButton(this);
                if(inActual.estado.equals("0")){
                    butVer.setId(btId1);
                    butVer.setLayoutParams(new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.WRAP_CONTENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT));
                    ViewGroup.LayoutParams rlp3 = butVer.getLayoutParams();
                    rlp3.height = 100;
                    rlp3.width = 100;
                    butVer.setBackground(this.getResources().getDrawable(R.drawable.small_lay_custom_wht));
                    butVer.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    butVer.setImageResource(R.drawable.ic_circle_regular);
                    butVer.setImageTintList(this.getResources().getColorStateList(R.color.offColor));
                    butVer.setOnClickListener(new SpecialListenerDouble(actual.id,inActual.nombre) {
                        @Override
                        public void onClick(View v) {
                            markGotProd(id,id2);
                        }
                    });
                }
                else{
                    butVer.setId(btId1);
                    butVer.setLayoutParams(new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.WRAP_CONTENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT));
                    ViewGroup.LayoutParams rlp3 = butVer.getLayoutParams();
                    rlp3.height = 100;
                    rlp3.width = 100;
                    butVer.setBackground(this.getResources().getDrawable(R.drawable.small_lay_custom_wht));
                    butVer.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    butVer.setImageResource(R.drawable.ic_check_circle_regular);
                    butVer.setImageTintList(this.getResources().getColorStateList(R.color.offColor));
                    butVer.setOnClickListener(new SpecialListenerDouble(actual.id,inActual.nombre) {
                        @Override
                        public void onClick(View v) {
                            markLostProd(id,id2);
                        }
                    });
                }
                lay2.addView(butVer);

                ConstraintSet csIn = new ConstraintSet();
                csIn.clone(lay2);
                csIn.connect(productId,ConstraintSet.LEFT,lay2.getId(),ConstraintSet.LEFT,60);
                csIn.connect(productId,ConstraintSet.TOP,lay2.getId(),ConstraintSet.TOP,20);
                csIn.connect(cantId,ConstraintSet.LEFT,lay2.getId(),ConstraintSet.LEFT,60);
                csIn.connect(cantId,ConstraintSet.TOP,productId,ConstraintSet.BOTTOM,5);
                csIn.connect(btId1,ConstraintSet.RIGHT,lay2.getId(),ConstraintSet.RIGHT,45);
                csIn.connect(btId1,ConstraintSet.TOP,lay2.getId(),ConstraintSet.TOP,55);
                csIn.applyTo(lay2);
                all.addView(lay2);
            }
        }
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
    }

    private void markGotProd(String idStab,String product){
        ListaPedido establecimiento = ListaPedido.findId(idStab,carroActual);
        ProductoLista prod = ProductoLista.findProd(product,establecimiento.productos);
        prod.estado = "1";
        stabID = establecimiento.id;
        this.prod = prod.nombre;
        cargarListaPedido();
        new pedidoObtain().execute();
    }

    private class pedidoObtain extends AsyncTask<Object, Object, Cursor> {
        @Override
        protected Cursor doInBackground(Object... params) {
            try
            {
                PedidoData.obtenerProducto(idOrden,stabID,prod);
                return null;
            }
            catch (Exception e)
            { e.printStackTrace();
                return null;}

        }

        @Override
        protected void onPostExecute(Cursor result) {

        }
    }

    private void markLostProd(String idStab,String product){
        ListaPedido establecimiento = ListaPedido.findId(idStab,carroActual);
        ProductoLista prod = ProductoLista.findProd(product,establecimiento.productos);
        prod.estado = "0";
        stabID = establecimiento.id;
        this.prod = prod.nombre;
        cargarListaPedido();
        new pedidoLost().execute();
    }

    private class pedidoLost extends AsyncTask<Object, Object, Cursor> {
        @Override
        protected Cursor doInBackground(Object... params) {
            try
            {
                PedidoData.perderProducto(idOrden,stabID,prod);
                return null;
            }
            catch (Exception e)
            { e.printStackTrace();
                return null;}

        }

        @Override
        protected void onPostExecute(Cursor result) {

        }
    }

    private void consultClick(){
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        btnCons.setBackgroundColor(this.getResources().getColor(R.color.lightColor));
        btnList.setBackgroundColor(this.getResources().getColor(R.color.trueWhite));
        btnCons2.setBackgroundColor(this.getResources().getColor(R.color.trueWhite));
        loadConCreatorLayout();
    }

    private void loadConCreatorLayout(){
        all.removeAllViews();
        int titleId = View.generateViewId();
        int titleEt = View.generateViewId();
        int detailId = View.generateViewId();
        int detailEt = View.generateViewId();
        int btId = View.generateViewId();

        final float scale = this.getResources().getDisplayMetrics().density;
        int pixels = (int) (112 * scale + 0.5f);
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        int screenHeight = size.y;

        ConstraintLayout lay = new ConstraintLayout(this);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        lay.setLayoutParams(params);
        ViewGroup.LayoutParams rlp = lay.getLayoutParams();
        int last = View.generateViewId();
        lay.setId(last);
        rlp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        rlp.height = screenHeight - getStatusBarHeight() - pixels;
        lay.setBackground(ContextCompat.getDrawable(this,R.drawable.small_lay_custom_wh));

        TextView title1 = new TextView(this);
        title1.setId(titleId);
        title1.setText("Título de la consulta");
        Typeface typeface = ResourcesCompat.getFont(this, R.font.pop_light);
        title1.setTypeface(typeface);
        title1.setTextColor(this.getResources().getColor(R.color.offColor));
        title1.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
        lay.addView(title1);

        TextView title2 = new TextView(this);
        title2.setId(detailId);
        title2.setText("Describa brevemente su consulta");
        title2.setTypeface(typeface);
        title2.setTextColor(this.getResources().getColor(R.color.offColor));
        title2.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
        lay.addView(title2);

        EditText editt1 = new EditText(this);
        editt1.setId(titleEt);
        editt1.setBackground(ContextCompat.getDrawable(this,R.drawable.round_txtwh_stroke));
        editt1.setLayoutParams(new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT));
        ViewGroup.LayoutParams rlp1 = editt1.getLayoutParams();
        rlp1.height = (int)(24 * scale + 0.5f);
        rlp1.width = (int)(311 * scale + 0.5f);
        editt1.setPadding((int)(10 * scale + 0.5f),0,0,0);
        editt1.setTypeface(typeface);
        editt1.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
        editt1.setTextColor(this.getResources().getColor(R.color.offColor));
        lay.addView(editt1);

        EditText editt2 = new EditText(this);
        editt2.setId(detailEt);
        editt2.setBackground(ContextCompat.getDrawable(this,R.drawable.round_txtwh_stroke));
        editt2.setLayoutParams(new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT));
        ViewGroup.LayoutParams rlp2 = editt2.getLayoutParams();
        rlp2.height = (int)(180 * scale + 0.5f);
        rlp2.width = (int)(311 * scale + 0.5f);
        editt2.setInputType(InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE);
        editt2.setSingleLine(false);
        editt2.setGravity(Gravity.TOP | Gravity.LEFT);
        editt2.setPadding((int)(10 * scale + 0.5f),0,0,0);
        editt2.setTypeface(typeface);
        editt2.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
        editt2.setTextColor(this.getResources().getColor(R.color.offColor));
        lay.addView(editt2);

        titleC = editt1;
        detailsC = editt2;

        Button butVer = new Button(this);
        butVer.setId(btId);
        butVer.setText("Enviar consulta");
        butVer.setLayoutParams(new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT));
        ViewGroup.LayoutParams rlp3 = butVer.getLayoutParams();
        rlp3.height = (int)(33 * scale + 0.5f);
        rlp3.width = (int)(311 * scale + 0.5f);
        butVer.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL);
        butVer.setTextColor(this.getResources().getColor(R.color.offColor));
        butVer.setBackground(this.getResources().getDrawable(R.drawable.round_btn_grn));
        int pixels2 = (int) (16 * scale + 0.5f);
        Drawable img2 = this.getResources().getDrawable(R.drawable.ic_paper_plane_solid);
        img2.setBounds(0, 0, pixels2, pixels2);
        img2.setTint(getResources().getColor(R.color.offColor));
        butVer.setCompoundDrawables(img2, null, null, null);
        butVer.setPadding(10,0,0,0);
        butVer.setAllCaps(false);
        Typeface typeface2 = ResourcesCompat.getFont(this, R.font.pop_med);
        butVer.setTypeface(typeface2);
        butVer.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
        butVer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processAddConsult();
            }
        });
        lay.addView(butVer);

        btnCCons = butVer;

        ConstraintSet csIn = new ConstraintSet();
        csIn.clone(lay);
        csIn.connect(titleId,ConstraintSet.LEFT,lay.getId(),ConstraintSet.LEFT,70);
        csIn.connect(titleId,ConstraintSet.TOP,lay.getId(),ConstraintSet.TOP,40);
        csIn.connect(titleEt,ConstraintSet.LEFT,titleId,ConstraintSet.LEFT,0);
        csIn.connect(titleEt,ConstraintSet.TOP,titleId,ConstraintSet.BOTTOM,10);
        csIn.connect(detailId,ConstraintSet.LEFT,titleEt,ConstraintSet.LEFT,0);
        csIn.connect(detailId,ConstraintSet.TOP,titleEt,ConstraintSet.BOTTOM,40);
        csIn.connect(detailEt,ConstraintSet.LEFT,titleEt,ConstraintSet.LEFT,0);
        csIn.connect(detailEt,ConstraintSet.TOP,detailId,ConstraintSet.BOTTOM,10);
        csIn.connect(btId,ConstraintSet.LEFT,titleEt,ConstraintSet.LEFT,0);
        csIn.connect(btId,ConstraintSet.TOP,detailEt,ConstraintSet.BOTTOM,40);
        csIn.applyTo(lay);
        all.addView(lay);

        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
    }

    private void processAddConsult(){
        btnCCons.setText("Enviando...");
        new addConsult().execute();
    }

    private class addConsult extends AsyncTask<Object, Object, Cursor> {
        @Override
        protected Cursor doInBackground(Object... params) {
            try
            {
                result = ConsultaData.agregarConsulta(idOrden,titleC.getText().toString(),detailsC.getText().toString());
                return null;
            }
            catch (Exception e)
            { e.printStackTrace();
                return null;}

        }

        @Override
        protected void onPostExecute(Cursor result) {
            finishCreationC();
        }
    }

    private void finishCreationC(){
        if(result==1) {
            Toast.makeText(this, "Consulta enviada con exito.", Toast.LENGTH_SHORT).show();
            consultViewerClick();
        }
        else{
            Toast.makeText(this, "Ocurrio un error a la hora de enviar la consulta", Toast.LENGTH_SHORT).show();
        }
    }

    private void consultViewerClick(){
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        btnCons2.setBackgroundColor(this.getResources().getColor(R.color.lightColor));
        btnCons.setBackgroundColor(this.getResources().getColor(R.color.trueWhite));
        btnList.setBackgroundColor(this.getResources().getColor(R.color.trueWhite));
    }

    private void loadLayout(){
        final float scale = getResources().getDisplayMetrics().density;
        int pixels = (int) (112 * scale + 0.5f);
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        int screenHeight = size.y;
        ScrollView sw = (ScrollView)findViewById(R.id.scrollViewTodo);
        ViewGroup.LayoutParams params = sw.getLayoutParams();
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
