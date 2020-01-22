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
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.proydiseo.CarryOn.Modelo.PedidoBasico;
import com.example.proydiseo.CarryOn.Modelo.PedidoData;
import com.example.proydiseo.CarryOn.SpecialListener;
import com.example.proydiseo.R;

import java.util.ArrayList;

public class BusquedaT extends AppCompatActivity {

    private ImageButton btnPerfil;
    private ImageButton btnSegui;
    private String correo;
    private LinearLayout all;
    private ArrayList<PedidoBasico> pedidosDisp = new ArrayList<PedidoBasico>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda_t);

        correo = getIntent().getStringExtra("USER_MAIL");

        all = findViewById(R.id.allSol);

        btnPerfil = findViewById(R.id.btnPerfil4);
        btnPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirPerfil();
            }
        });

        btnSegui = findViewById(R.id.btnSegui3);
        btnSegui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirSeguimiento();
            }
        });
        loadLayout();
        new showPedidos().execute();
    }

    private class showPedidos extends AsyncTask<Object, Object, Cursor> {
        @Override
        protected Cursor doInBackground(Object... params) {
            try
            {
                pedidosDisp = PedidoData.getAllPedidos(correo);
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

    private void loadLayout(){
        final float scale = getResources().getDisplayMetrics().density;
        int pixels = (int) (114 * scale + 0.5f);
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        int screenHeight = size.y;
        ScrollView sw = (ScrollView)findViewById(R.id.swSearch);
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

    private void abrirPerfil(){
        Intent intent = new Intent(this, PerfilT.class);
        intent.putExtra("USER_MAIL",correo);
        startActivity(intent);
        this.overridePendingTransition(0, 0);
    }

    private void abrirSeguimiento(){
        Intent intent = new Intent(this, SeguiT.class);
        intent.putExtra("USER_MAIL",correo);
        startActivity(intent);
        this.overridePendingTransition(0, 0);
    }

    private void loadBuscando(){
        int last = 0;
        for(int i = 0;i<pedidosDisp.size();i++){
            int textId = View.generateViewId();
            int nameId = View.generateViewId();
            int dateId = View.generateViewId();
            int butId = View.generateViewId();
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
            pre2.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_calendar_alt_regular));
            pre2.setScaleType(ImageView.ScaleType.FIT_CENTER);
            pre2.setImageTintList(this.getResources().getColorStateList(R.color.offColor));
            ViewGroup.LayoutParams rl2 = pre2.getLayoutParams();
            rl2.height = 52;
            rl2.width = 52;
            lay.addView(pre2);

            TextView title = new TextView(this);
            title.setId(textId);
            title.setText(pedidosDisp.get(i).nombre);
            Typeface typeface = ResourcesCompat.getFont(this, R.font.pop_med);
            title.setTypeface(typeface);
            title.setTextColor(this.getResources().getColor(R.color.offColor));
            title.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
            lay.addView(title);

            TextView name = new TextView(this);
            name.setId(nameId);
            name.setText(pedidosDisp.get(i).cliente);
            Typeface typeface2 = ResourcesCompat.getFont(this, R.font.pop_light);
            name.setTypeface(typeface2);
            name.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
            lay.addView(name);

            TextView dateW = new TextView(this);
            dateW.setId(dateId);
            dateW.setText(pedidosDisp.get(i).fecha);
            dateW.setTypeface(typeface2);
            dateW.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
            lay.addView(dateW);

            RelativeLayout.LayoutParams paramsbt2 = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);

            Button butVer = new Button(this);
            butVer.setId(butId);
            butVer.setText("Detalles");
            butVer.setLayoutParams(paramsbt2);
            ViewGroup.LayoutParams rlp3 = butVer.getLayoutParams();
            rlp3.height = 100;
            rlp3.width = 300;
            butVer.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL);
            butVer.setTextColor(this.getResources().getColor(R.color.offColor));
            butVer.setBackground(this.getResources().getDrawable(R.drawable.wh_round_txt));
            Drawable img2 = this.getResources().getDrawable(R.drawable.ic_info_circle_solid);
            final float scale = getResources().getDisplayMetrics().density;
            int pixels = (int) (15 * scale + 0.5f);
            img2.setBounds(0, 0, pixels, pixels);
            img2.setTint(getResources().getColor(R.color.offColor));
            butVer.setCompoundDrawables(img2, null, null, null);
            butVer.setPadding(10,0,0,0);
            butVer.setAllCaps(false);
            butVer.setTypeface(typeface);
            butVer.setTextSize(TypedValue.COMPLEX_UNIT_SP,13);
            butVer.setOnClickListener(new SpecialListener(pedidosDisp.get(i).id) {
                @Override
                public void onClick(View v) {
                    openInfo(id);
                }
            });
            lay.addView(butVer);


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
            cs.connect(butId,ConstraintSet.TOP,lay.getId(),ConstraintSet.TOP,90);
            cs.connect(butId,ConstraintSet.RIGHT,lay.getId(),ConstraintSet.RIGHT,35);
            cs.applyTo(lay);

            all.addView(lay);
        }
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
    }

    private void openInfo(String id){
        Intent intent = new Intent(this, InfoPedido.class);
        intent.putExtra("ORDER_ID",id);
        intent.putExtra("USER_MAIL", correo);
        startActivity(intent);
        this.overridePendingTransition(0, 0);
    }

}
