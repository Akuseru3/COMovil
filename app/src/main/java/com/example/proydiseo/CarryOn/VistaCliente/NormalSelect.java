package com.example.proydiseo.CarryOn.VistaCliente;

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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proydiseo.CarryOn.Modelo.PedidoData;
import com.example.proydiseo.CarryOn.Modelo.Solicitante;
import com.example.proydiseo.CarryOn.SpecialListener;
import com.example.proydiseo.R;

import java.util.ArrayList;

public class NormalSelect extends AppCompatActivity {
    private ImageButton btnBack;
    private LinearLayout all;
    private String id;
    private String selTrans;
    private ArrayList<Solicitante> solicitantes;
    private int result;
    private String correo;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.elegir_normal_lay);
        all = findViewById(R.id.allTransNorm);
        btnBack = findViewById(R.id.btnBackN);

        id = getIntent().getStringExtra("ORD_ID");
        correo = getIntent().getStringExtra("USER_MAIL");

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        loadLayout();
        new showSols().execute();
    }

    private void loadLayout(){
        final float scale = getResources().getDisplayMetrics().density;
        int pixels = (int) (64 * scale + 0.5f);
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        int screenHeight = size.y;
        ScrollView sw = (ScrollView)findViewById(R.id.swTransSel);
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

    private class showSols extends AsyncTask<Object, Object, Cursor> {
        @Override
        protected Cursor doInBackground(Object... params) {
            try
            {
                solicitantes = PedidoData.getSolicitantes(id);
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
        int last = 0;
        for(int i = 0;i<solicitantes.size();i++){
            int textId = View.generateViewId();
            int dateId = View.generateViewId();
            int costId = View.generateViewId();
            int imgId = View.generateViewId();
            int butId = View.generateViewId();
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

            TextView title = new TextView(this);
            title.setId(textId);
            title.setText(solicitantes.get(i).nombre);
            Typeface typeface = ResourcesCompat.getFont(this, R.font.pop_med);
            title.setTypeface(typeface);
            title.setTextColor(this.getResources().getColor(R.color.offColor));
            title.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
            lay.addView(title);

            TextView dateW = new TextView(this);
            dateW.setId(dateId);
            dateW.setText("Calificación: "+solicitantes.get(i).valoracion);
            Typeface typeface2 = ResourcesCompat.getFont(this, R.font.pop_light);
            dateW.setTypeface(typeface2);
            dateW.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
            lay.addView(dateW);

            final float scale = getResources().getDisplayMetrics().density;
            int pixels = (int) (16 * scale + 0.5f);
            ImageView imageView = new ImageView(this);
            imageView.setId(imgId);
            imageView.setImageResource(R.drawable.ic_star_solid);
            imageView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT));
            imageView.getLayoutParams().height = pixels;
            imageView.getLayoutParams().width = pixels;
            lay.addView(imageView);

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

            TextView costW = new TextView(this);
            costW.setId(costId);
            costW.setText("₡"+solicitantes.get(i).costo);
            costW.setTypeface(typeface2);
            costW.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
            lay.addView(costW);

            RelativeLayout.LayoutParams paramsbt2 = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);

            Button butVer = new Button(this);
            butVer.setId(butId);
            butVer.setText("Elegir");
            butVer.setLayoutParams(paramsbt2);
            ViewGroup.LayoutParams rlp3 = butVer.getLayoutParams();
            rlp3.height = 100;
            rlp3.width = 300;
            butVer.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL);
            butVer.setTextColor(this.getResources().getColor(R.color.offColor));
            butVer.setBackground(this.getResources().getDrawable(R.drawable.wh_round_txt));
            int pixels1 = (int) (15 * scale + 0.5f);
            int pixels2 = (int) (19 * scale + 0.5f);
            Drawable img2 = this.getResources().getDrawable(R.drawable.ic_user_check_solid);
            img2.setBounds(0, 0, pixels2, pixels1);
            img2.setTint(getResources().getColor(R.color.offColor));
            butVer.setCompoundDrawables(img2, null, null, null);
            butVer.setPadding(10,0,0,0);
            butVer.setAllCaps(false);
            butVer.setTypeface(typeface);
            butVer.setTextSize(TypedValue.COMPLEX_UNIT_SP,13);
            butVer.setOnClickListener(new SpecialListener(solicitantes.get(i).id) {
                @Override
                public void onClick(View v) {
                    definirSeleccion(id);
                }
            });
            lay.addView(butVer);

            ConstraintSet cs = new ConstraintSet();
            cs.clone(lay);
            cs.connect(textId,ConstraintSet.TOP,lay.getId(),ConstraintSet.TOP,15);
            cs.connect(textId,ConstraintSet.LEFT,lay.getId(),ConstraintSet.LEFT,30);
            cs.connect(dateId,ConstraintSet.TOP,textId,ConstraintSet.BOTTOM,10);
            cs.connect(dateId,ConstraintSet.LEFT,lay.getId(),ConstraintSet.LEFT,30);
            cs.connect(imgid2,ConstraintSet.TOP,dateId,ConstraintSet.BOTTOM,20);
            cs.connect(imgid2,ConstraintSet.LEFT,lay.getId(),ConstraintSet.LEFT,30);
            cs.connect(costId,ConstraintSet.TOP,dateId,ConstraintSet.BOTTOM,15);
            cs.connect(costId,ConstraintSet.LEFT,imgid2,ConstraintSet.RIGHT,15);
            cs.connect(imgId,ConstraintSet.TOP,dateId,ConstraintSet.TOP,0);
            cs.connect(imgId,ConstraintSet.LEFT,dateId,ConstraintSet.RIGHT,0);
            cs.connect(butId,ConstraintSet.TOP,lay.getId(),ConstraintSet.TOP,80);
            cs.connect(butId,ConstraintSet.RIGHT,lay.getId(),ConstraintSet.RIGHT,50);
            cs.applyTo(lay);

            all.addView(lay);
        }
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
    }

    private void definirSeleccion(String idTrans){
        selTrans = idTrans;
        new elegirTrans().execute();
    }

    private class elegirTrans extends AsyncTask<Object, Object, Cursor> {
        @Override
        protected Cursor doInBackground(Object... params) {
            try
            {
                result = PedidoData.definirTransportista(id,selTrans);
                return null;
            }
            catch (Exception e)
            { e.printStackTrace();
                return null;}

        }

        @Override
        protected void onPostExecute(Cursor result) {
            endSeleccion();
        }
    }

    private void endSeleccion(){
        if(result==1) {
            Toast.makeText(this, "Transportista elegido", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, SeguiC.class);
            intent.putExtra("USER_MAIL", correo);
            startActivity(intent);
            this.overridePendingTransition(0, 0);
        }
        else{
            Toast.makeText(this, "Ocurrio un error a la hora de elegir el transportista", Toast.LENGTH_SHORT).show();
        }
    }
}

