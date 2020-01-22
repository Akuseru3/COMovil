package com.example.proydiseo.CarryOn.VistaTransportista;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
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

import com.example.proydiseo.CarryOn.Modelo.PedidoCurso;
import com.example.proydiseo.CarryOn.Modelo.PedidoData;
import com.example.proydiseo.CarryOn.SpecialListener;
import com.example.proydiseo.R;

import java.util.ArrayList;

public class SeguiT extends AppCompatActivity{
    private ImageButton btnMas;
    private ImageButton btnPerfil;
    private int backButtonCount;
    private ImageButton btnBuscar;
    private ImageButton btnFin;
    private ImageButton btnEnCurso;
    private TextView txtBuscar;
    private TextView txtFin;
    private TextView txtEnCurso;
    private LinearLayout all;
    private String correo;
    private ArrayList<PedidoCurso> solicitados;
    private ArrayList<PedidoCurso> pedidosCurs;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seg_trans_lay);

        all = findViewById(R.id.allInfo);

        btnMas = findViewById(R.id.btnMas3);
        btnMas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirBusqueda();
            }
        });

        btnPerfil = findViewById(R.id.btnPerfil3);
        btnPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirPerfil();
            }
        });

        correo = getIntent().getStringExtra("USER_MAIL");

        btnBuscar = findViewById(R.id.btnBuscando);
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarBuscando();
            }
        });
        btnEnCurso = findViewById(R.id.btnCurso);
        btnEnCurso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarEnCurso();
            }
        });
        btnFin = findViewById(R.id.btnTerminados);
        btnFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarTerminados();
            }
        });
        txtEnCurso = findViewById(R.id.textViewCurso);
        txtBuscar = findViewById(R.id.textViewBuscando);
        txtFin = findViewById(R.id.textViewTerminados);
        loadLayout();
        cargarBuscando();

    }

    private void loadLayout(){
        final float scale = getResources().getDisplayMetrics().density;
        int pixels = (int) (187 * scale + 0.5f);
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

    private void abrirPerfil(){
        Intent intent = new Intent(this, PerfilT.class);
        intent.putExtra("USER_MAIL",correo);
        startActivity(intent);
        this.overridePendingTransition(0, 0);
    }

    private void abrirBusqueda(){
        Intent intent = new Intent(this, BusquedaT.class);
        intent.putExtra("USER_MAIL",correo);
        startActivity(intent);
        this.overridePendingTransition(0, 0);
    }

    private void cargarBuscando(){
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        all.removeAllViews();
        btnBuscar.setColorFilter(ContextCompat.getColor(this, R.color.lightColor), android.graphics.PorterDuff.Mode.SRC_IN);
        txtBuscar.setTextColor(getResources().getColor(R.color.lightColor));
        btnEnCurso.setColorFilter(ContextCompat.getColor(this, R.color.offColor), android.graphics.PorterDuff.Mode.SRC_IN);
        txtEnCurso.setTextColor(getResources().getColor(R.color.offColor));
        btnFin.setColorFilter(ContextCompat.getColor(this, R.color.offColor), android.graphics.PorterDuff.Mode.SRC_IN);
        txtFin.setTextColor(getResources().getColor(R.color.offColor));
        new showSolic().execute();
    }

    private class showSolic extends AsyncTask<Object, Object, Cursor> {
        @Override
        protected Cursor doInBackground(Object... params) {
            try
            {
                solicitados = PedidoData.getMyPedidosSolicitados(correo);
                return null;
            }
            catch (Exception e)
            { e.printStackTrace();
                return null;}

        }

        @Override
        protected void onPostExecute(Cursor result) {
            loadBuscando();
        }
    }

    private void loadBuscando(){
        all.removeAllViews();
        int last = 0;
        for(int i = 0;i<solicitados.size();i++){
            int textId = View.generateViewId();
            int nameId = View.generateViewId();
            int dateId = View.generateViewId();
            int infId = View.generateViewId();
            int imgid = View.generateViewId();
            int imgid2 = View.generateViewId();

            ConstraintLayout lay = new ConstraintLayout(this);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            if(i!=0)
                params.addRule(RelativeLayout.BELOW,last);
            lay.setLayoutParams(params);
            ViewGroup.LayoutParams rlp = lay.getLayoutParams();
            last = View.generateViewId();
            lay.setId(last);
            rlp.width = ViewGroup.LayoutParams.MATCH_PARENT;
            lay.setBackground(ContextCompat.getDrawable(this,R.drawable.small_lay_custom_wh));

            ImageView pre1 = new ImageView(this);
            pre1.setLayoutParams(new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT));
            pre1.setId(imgid);
            pre1.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_user_tie_solid));
            pre1.setScaleType(ImageView.ScaleType.FIT_CENTER);
            pre1.setImageTintList(this.getResources().getColorStateList(R.color.offColor));
            ViewGroup.LayoutParams rl = pre1.getLayoutParams();
            rl.height = 52;
            rl.width = 52;
            lay.addView(pre1);


            ImageView pre2 = new ImageView(this);
            pre2.setLayoutParams(new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT));
            pre2.setId(imgid2);
            pre2.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_hand_holding_usd_solid));
            pre2.setScaleType(ImageView.ScaleType.FIT_CENTER);
            pre2.setImageTintList(this.getResources().getColorStateList(R.color.offColor));
            ViewGroup.LayoutParams rl2 = pre2.getLayoutParams();
            rl2.height = 52;
            rl2.width = 52;
            lay.addView(pre2);

            TextView title = new TextView(this);
            title.setId(textId);
            title.setText(solicitados.get(i).nombre);
            Typeface typeface = ResourcesCompat.getFont(this, R.font.pop_med);
            title.setTypeface(typeface);
            title.setTextColor(this.getResources().getColor(R.color.offColor));
            title.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
            lay.addView(title);

            TextView name = new TextView(this);
            name.setId(nameId);
            name.setText(solicitados.get(i).cliente);
            Typeface typeface2 = ResourcesCompat.getFont(this, R.font.pop_light);
            name.setTypeface(typeface2);
            name.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
            lay.addView(name);

            TextView dateW = new TextView(this);
            dateW.setId(dateId);
            dateW.setText("₡"+solicitados.get(i).oferta);
            dateW.setTypeface(typeface2);
            dateW.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
            lay.addView(dateW);

            TextView infoW = new TextView(this);
            infoW.setId(infId);
            infoW.setText("Esperando\nrespuesta...");
            infoW.setTypeface(typeface);
            infoW.setTextColor(this.getResources().getColor(R.color.lightColor));
            infoW.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
            lay.addView(infoW);

            ConstraintSet cs = new ConstraintSet();
            cs.clone(lay);
            cs.connect(textId,ConstraintSet.TOP,lay.getId(),ConstraintSet.TOP,20);
            cs.connect(textId,ConstraintSet.LEFT,lay.getId(),ConstraintSet.LEFT,30);
            cs.connect(imgid,ConstraintSet.TOP,textId,ConstraintSet.BOTTOM,20);
            cs.connect(imgid,ConstraintSet.LEFT,lay.getId(),ConstraintSet.LEFT,30);
            cs.connect(nameId,ConstraintSet.TOP,textId,ConstraintSet.BOTTOM,20);
            cs.connect(nameId,ConstraintSet.LEFT,imgid,ConstraintSet.RIGHT,15);
            cs.connect(imgid2,ConstraintSet.TOP,nameId,ConstraintSet.BOTTOM,20);
            cs.connect(imgid2,ConstraintSet.LEFT,lay.getId(),ConstraintSet.LEFT,30);
            cs.connect(dateId,ConstraintSet.TOP,nameId,ConstraintSet.BOTTOM,20);
            cs.connect(dateId,ConstraintSet.LEFT,imgid2,ConstraintSet.RIGHT,20);
            cs.connect(infId,ConstraintSet.TOP,lay.getId(),ConstraintSet.TOP,75);
            cs.connect(infId,ConstraintSet.RIGHT,lay.getId(),ConstraintSet.RIGHT,50);
            cs.applyTo(lay);

            all.addView(lay);
        }
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
    }

    private void cargarEnCurso(){
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        all.removeAllViews();
        btnBuscar.setColorFilter(ContextCompat.getColor(this, R.color.offColor), android.graphics.PorterDuff.Mode.SRC_IN);
        txtBuscar.setTextColor(getResources().getColor(R.color.offColor));
        btnEnCurso.setColorFilter(ContextCompat.getColor(this, R.color.lightColor), android.graphics.PorterDuff.Mode.SRC_IN);
        txtEnCurso.setTextColor(getResources().getColor(R.color.lightColor));
        btnFin.setColorFilter(ContextCompat.getColor(this, R.color.offColor), android.graphics.PorterDuff.Mode.SRC_IN);
        txtFin.setTextColor(getResources().getColor(R.color.offColor));
        new showCurso().execute();
    }

    private class showCurso extends AsyncTask<Object, Object, Cursor> {
        @Override
        protected Cursor doInBackground(Object... params) {
            try
            {
                pedidosCurs = PedidoData.getMyPedidosAceptados(correo);
                return null;
            }
            catch (Exception e)
            { e.printStackTrace();
                return null;}

        }

        @Override
        protected void onPostExecute(Cursor result) {
            loadEnCurso();
        }
    }

    private void loadEnCurso(){
        all.removeAllViews();
        int last = 0;
        for(int i = 0;i<pedidosCurs.size();i++){
            int textId = View.generateViewId();
            int nameId = View.generateViewId();
            int btId1 = View.generateViewId();
            int dateId = View.generateViewId();
            int imgid = View.generateViewId();
            int imgid2 = View.generateViewId();

            ConstraintLayout lay = new ConstraintLayout(this);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            if(i!=0)
                params.addRule(RelativeLayout.BELOW,last);
            lay.setLayoutParams(params);
            ViewGroup.LayoutParams rlp = lay.getLayoutParams();
            last = View.generateViewId();
            lay.setId(last);
            rlp.width = ViewGroup.LayoutParams.MATCH_PARENT;
            lay.setBackground(ContextCompat.getDrawable(this,R.drawable.small_lay_custom_wh));

            ImageView pre1 = new ImageView(this);
            pre1.setLayoutParams(new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT));
            pre1.setId(imgid);
            pre1.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_user_tie_solid));
            pre1.setScaleType(ImageView.ScaleType.FIT_CENTER);
            pre1.setImageTintList(this.getResources().getColorStateList(R.color.offColor));
            ViewGroup.LayoutParams rl = pre1.getLayoutParams();
            rl.height = 52;
            rl.width = 52;
            lay.addView(pre1);


            ImageView pre2 = new ImageView(this);
            pre2.setLayoutParams(new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT));
            pre2.setId(imgid2);
            pre2.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_money_bill_wave_solid));
            pre2.setScaleType(ImageView.ScaleType.FIT_CENTER);
            pre2.setImageTintList(this.getResources().getColorStateList(R.color.offColor));
            ViewGroup.LayoutParams rl2 = pre2.getLayoutParams();
            rl2.height = 48;
            rl2.width = 58;
            lay.addView(pre2);

            TextView title = new TextView(this);
            title.setId(textId);
            title.setText(pedidosCurs.get(i).nombre);
            Typeface typeface = ResourcesCompat.getFont(this, R.font.pop_med);
            title.setTypeface(typeface);
            title.setTextColor(this.getResources().getColor(R.color.offColor));
            title.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
            lay.addView(title);

            TextView name = new TextView(this);
            name.setId(nameId);
            name.setText(pedidosCurs.get(i).cliente);
            Typeface typeface2 = ResourcesCompat.getFont(this, R.font.pop_light);
            name.setTypeface(typeface2);
            name.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
            lay.addView(name);

            TextView dateW = new TextView(this);
            dateW.setId(dateId);
            dateW.setText("₡"+pedidosCurs.get(i).oferta);
            dateW.setTypeface(typeface2);
            dateW.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
            lay.addView(dateW);

            RelativeLayout.LayoutParams paramsbt2 = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);

            Button butVer = new Button(this);
            butVer.setId(btId1);
            butVer.setText("Seguimiento");
            butVer.setLayoutParams(new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT));
            ViewGroup.LayoutParams rlp3 = butVer.getLayoutParams();
            rlp3.height = 100;
            rlp3.width = 360;
            butVer.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL);
            butVer.setTextColor(this.getResources().getColor(R.color.offColor));
            butVer.setBackground(this.getResources().getDrawable(R.drawable.wh_round_txt));
            final float scale = getResources().getDisplayMetrics().density;
            int pixels = (int) (15 * scale + 0.5f);
            Drawable img2 = this.getResources().getDrawable(R.drawable.ic_map_marked_alt_solid);
            img2.setBounds(0, 0, pixels, pixels);
            img2.setTint(getResources().getColor(R.color.offColor));
            butVer.setCompoundDrawables(img2, null, null, null);
            butVer.setPadding(10,0,0,0);
            butVer.setAllCaps(false);
            butVer.setTypeface(typeface);
            butVer.setTextSize(TypedValue.COMPLEX_UNIT_SP,13);
            butVer.setOnClickListener(new SpecialListener(pedidosCurs.get(i).id) {
                @Override
                public void onClick(View v) {
                    openFollowing(id);
                }
            });
            lay.addView(butVer);

            ConstraintSet cs = new ConstraintSet();
            cs.clone(lay);
            cs.connect(textId,ConstraintSet.TOP,lay.getId(),ConstraintSet.TOP,15);
            cs.connect(textId,ConstraintSet.LEFT,lay.getId(),ConstraintSet.LEFT,30);
            cs.connect(imgid,ConstraintSet.LEFT,lay.getId(),ConstraintSet.LEFT,30);
            cs.connect(imgid,ConstraintSet.TOP,textId,ConstraintSet.BOTTOM,10);
            cs.connect(nameId,ConstraintSet.TOP,textId,ConstraintSet.BOTTOM,10);
            cs.connect(nameId,ConstraintSet.LEFT,imgid,ConstraintSet.RIGHT,15);
            cs.connect(imgid2,ConstraintSet.LEFT,lay.getId(),ConstraintSet.LEFT,30);
            cs.connect(imgid2,ConstraintSet.TOP,nameId,ConstraintSet.BOTTOM,16);
            cs.connect(dateId,ConstraintSet.TOP,nameId,ConstraintSet.BOTTOM,10);
            cs.connect(dateId,ConstraintSet.LEFT,imgid2,ConstraintSet.RIGHT,15);
            cs.connect(btId1,ConstraintSet.TOP,lay.getId(),ConstraintSet.TOP,76);
            cs.connect(btId1,ConstraintSet.RIGHT,lay.getId(),ConstraintSet.RIGHT,35);
            cs.applyTo(lay);

            all.addView(lay);
        }
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
    }

    private void openFollowing(String id){
        Intent intent = new Intent(this, SeguimientoT.class);
        intent.putExtra("ORD_ID",id);
        intent.putExtra("USER_MAIL",correo);
        startActivity(intent);
        this.overridePendingTransition(0, 0);
    }

    private void cargarTerminados(){
        all.removeAllViews();
        btnBuscar.setColorFilter(ContextCompat.getColor(this, R.color.offColor), android.graphics.PorterDuff.Mode.SRC_IN);
        txtBuscar.setTextColor(getResources().getColor(R.color.offColor));
        btnEnCurso.setColorFilter(ContextCompat.getColor(this, R.color.offColor), android.graphics.PorterDuff.Mode.SRC_IN);
        txtEnCurso.setTextColor(getResources().getColor(R.color.offColor));
        btnFin.setColorFilter(ContextCompat.getColor(this, R.color.lightColor), android.graphics.PorterDuff.Mode.SRC_IN);
        txtFin.setTextColor(getResources().getColor(R.color.lightColor));
        Intent intent = new Intent(this, ValoracionT.class);
        startActivity(intent);
        this.overridePendingTransition(0, 0);
        loadFinished();
    }

    private void loadFinished() {
        int last = 0;
        for (int i = 0; i < 3; i++) {
            int textId = View.generateViewId();
            int nameId = View.generateViewId();
            int priceId = View.generateViewId();
            int dateId = View.generateViewId();

            ConstraintLayout lay = new ConstraintLayout(this);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            if (i != 0)
                params.addRule(RelativeLayout.BELOW, last);
            lay.setLayoutParams(params);
            ViewGroup.LayoutParams rlp = lay.getLayoutParams();
            last = View.generateViewId();
            lay.setId(last);
            rlp.width = ViewGroup.LayoutParams.MATCH_PARENT;
            rlp.height = 280;
            lay.setBackground(ContextCompat.getDrawable(this, R.drawable.small_lay_custom_wh));

            TextView title = new TextView(this);
            title.setId(textId);
            title.setText("Nombre del pedido");
            Typeface typeface = ResourcesCompat.getFont(this, R.font.pop_med);
            title.setTypeface(typeface);
            title.setTextColor(this.getResources().getColor(R.color.offColor));
            title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            lay.addView(title);

            TextView name = new TextView(this);
            name.setId(nameId);
            name.setText("Nombre del cliente");
            Typeface typeface2 = ResourcesCompat.getFont(this, R.font.pop_light);
            name.setTypeface(typeface2);
            name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            lay.addView(name);

            TextView dateW = new TextView(this);
            dateW.setId(dateId);
            dateW.setText("Fecha y hora del pedido");
            dateW.setTypeface(typeface2);
            dateW.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            lay.addView(dateW);

            ConstraintSet cs = new ConstraintSet();
            cs.clone(lay);
            cs.connect(textId, ConstraintSet.TOP, lay.getId(), ConstraintSet.TOP, 15);
            cs.connect(textId, ConstraintSet.LEFT, lay.getId(), ConstraintSet.LEFT, 15);
            cs.connect(nameId, ConstraintSet.TOP, textId, ConstraintSet.BOTTOM, 10);
            cs.connect(nameId, ConstraintSet.LEFT, lay.getId(), ConstraintSet.LEFT, 15);
            cs.connect(dateId, ConstraintSet.TOP, nameId, ConstraintSet.BOTTOM, 10);
            cs.connect(dateId, ConstraintSet.LEFT, lay.getId(), ConstraintSet.LEFT, 15);
            cs.applyTo(lay);

            all.addView(lay);
        }
    }
}
