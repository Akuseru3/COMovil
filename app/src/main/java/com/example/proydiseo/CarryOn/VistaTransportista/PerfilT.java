package com.example.proydiseo.CarryOn.VistaTransportista;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
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

import com.example.proydiseo.CarryOn.Controlador.Usuario;
import com.example.proydiseo.CarryOn.Modelo.ExtraTools;
import com.example.proydiseo.CarryOn.Modelo.UserData;
import com.example.proydiseo.R;
import com.example.proydiseo.CarryOn.VistaCliente.EditPerfilC;
import com.example.proydiseo.CarryOn.VistaGeneral.MainActivity;

public class PerfilT extends AppCompatActivity {
    private Button btnCerrarSesion;
    private ImageButton btnMas;
    private ImageButton btnSegui;
    private LinearLayout all;
    private int backButtonCount;
    private Button btnCom;
    private Button btnHisto;
    private Button btnStats;
    private String correo;
    private Usuario actual;
    private Button btnEdit;
    private String usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.perfil_t_lay);

        all = findViewById(R.id.allPinfo);

        btnMas = findViewById(R.id.btnMas);
        btnMas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirBusqueda();
            }
        });

        btnSegui = findViewById(R.id.btnSegui);
        btnSegui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirSeguimiento();
            }
        });

        btnCerrarSesion = findViewById(R.id.btnCerrar);
        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cerrarCuenta();
            }
        });

        btnEdit = findViewById(R.id.btnEditar);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEditor();
            }
        });

        btnCom = findViewById(R.id.btnComent);
        btnCom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadComments();
            }
        });

        //Boton de la seccion de historial en el perfil
        btnHisto = findViewById(R.id.btnHist);
        btnHisto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadHistory();
            }
        });

        //Boton de la seccion de estadisticas en el perfil
        btnStats = findViewById(R.id.btnStats);
        btnStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadStatistics();
            }
        });

        Intent in = getIntent();
        usuario = in.getStringExtra("user");

        //nombre = findViewById(R.id.txtV_nombre);
        correo = getIntent().getStringExtra("USER_MAIL");
        //telefono = findViewById(R.id.txtV_correo);

        TextView tipo = findViewById(R.id.txtV_tipo);
        tipo.setText("Transportista");
        fixLayouts();
        fixSw();
        new loadUser().execute();
        loadComments();
    }

    private class loadUser extends AsyncTask<Object, Object, Cursor> {

        @Override
        protected Cursor doInBackground(Object... params) {
            try
            {
                Usuario result = UserData.getUser(correo);
                actual = result;
                return null;
            }
            catch (Exception e)
            { e.printStackTrace();
                return null;}

        }

        @Override
        protected void onPostExecute(Cursor result) {
            fillUserInfo();
        }
    }

    private void fillUserInfo(){
        TextView nombre  = findViewById(R.id.txtV_nombre);
        nombre.setText(actual.nombre +" "+ actual.apellidos);
        TextView correo  = findViewById(R.id.txtV_correo);
        correo.setText(actual.correo);
        TextView numero  = findViewById(R.id.txtV_correo2);
        numero.setText(actual.telefono);
        TextView fecha  = findViewById(R.id.txtV_correo4);
        fecha.setText(ExtraTools.generateDate(actual.fecha));
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
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

    private void cerrarCuenta(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.overridePendingTransition(0, 0);
    }

    private void abrirSeguimiento(){
        Intent intent = new Intent(this, SeguiT.class);
        intent.putExtra("USER_MAIL",correo);
        startActivity(intent);
        this.overridePendingTransition(0, 0);
    }

    private void abrirBusqueda(){
        Intent intent = new Intent(this, BusquedaT.class);
        intent.putExtra("USER_MAIL",actual.correo);
        startActivity(intent);
        this.overridePendingTransition(0, 0);
    }

    public void fixLayouts(){
        final float scale = getResources().getDisplayMetrics().density;
        int pixels = (int) ((55+59) * scale + 0.5f);
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        int screenHeight = size.y;
        ConstraintLayout layout = (ConstraintLayout)findViewById(R.id.layInSW);
        ViewGroup.LayoutParams params = layout.getLayoutParams();
        params.height = screenHeight - getStatusBarHeight() - pixels;
        ScrollView sw = (ScrollView)findViewById(R.id.swMain);
        ViewGroup.LayoutParams params2 = sw.getLayoutParams();
        params2.height = screenHeight - getStatusBarHeight() - pixels;
    }

    public void fixSw(){
        final float scale = getResources().getDisplayMetrics().density;
        int pixels = (int) ((55+48+59) * scale + 0.5f);
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        int screenHeight = size.y;
        ScrollView layout = (ScrollView)findViewById(R.id.swAllPInfo);
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

    private void openEditor(){
        Intent intent = new Intent(this, EditPerfilT.class);
        intent.putExtra("USER_NAME",actual.nombre);
        intent.putExtra("USER_BDAY",actual.fecha);
        intent.putExtra("USER_PHONE",actual.telefono);
        intent.putExtra("USER_LNAME",actual.apellidos);
        intent.putExtra("USER_MAIL",actual.correo);
        startActivity(intent);
        this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    private void loadComments(){
        all.removeAllViews();
        btnCom.setBackgroundColor(this.getResources().getColor(R.color.lightColor));
        btnHisto.setBackgroundColor(this.getResources().getColor(R.color.trueWhite));
        btnStats.setBackgroundColor(this.getResources().getColor(R.color.trueWhite));
        cargaComents();
    }

    private void cargaComents(){
        int last = 0;
        for(int i = 0;i<4;i++){
            int textId = View.generateViewId();
            int nameId = View.generateViewId();
            int dateId = View.generateViewId();
            int imgId = View.generateViewId();

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
            title.setText("Nombre del transportista");
            Typeface typeface = ResourcesCompat.getFont(this, R.font.pop_med);
            title.setTypeface(typeface);
            title.setTextColor(this.getResources().getColor(R.color.offColor));
            title.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
            lay.addView(title);

            TextView name = new TextView(this);
            name.setId(nameId);
            name.setText("El muchacho se porto muy bien pero no me pago 1100 solo me pago 1000 asi que reporta2");
            Typeface typeface2 = ResourcesCompat.getFont(this, R.font.pop_light);
            name.setTypeface(typeface2);
            name.setTextColor(this.getResources().getColor(R.color.offColor));
            name.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
            lay.addView(name);

            TextView dateW = new TextView(this);
            dateW.setId(dateId);
            dateW.setText("Valoración: 2");
            dateW.setTypeface(typeface2);
            dateW.setTextColor(this.getResources().getColor(R.color.offColor));
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
            imageView.setColorFilter(ContextCompat.getColor(this, R.color.offColor), android.graphics.PorterDuff.Mode.SRC_IN);
            lay.addView(imageView);


            ConstraintSet cs = new ConstraintSet();
            cs.clone(lay);
            cs.connect(textId,ConstraintSet.TOP,lay.getId(),ConstraintSet.TOP,15);
            cs.connect(textId,ConstraintSet.LEFT,lay.getId(),ConstraintSet.LEFT,15);
            cs.connect(dateId,ConstraintSet.TOP,textId,ConstraintSet.BOTTOM,10);
            cs.connect(dateId,ConstraintSet.LEFT,lay.getId(),ConstraintSet.LEFT,15);
            cs.connect(nameId,ConstraintSet.TOP,dateId,ConstraintSet.BOTTOM,20);
            cs.connect(nameId,ConstraintSet.LEFT,lay.getId(),ConstraintSet.LEFT,15);
            cs.connect(imgId,ConstraintSet.TOP,dateId,ConstraintSet.TOP,0);
            cs.connect(imgId,ConstraintSet.LEFT,dateId,ConstraintSet.RIGHT,0);
            cs.applyTo(lay);

            all.addView(lay);
        }
    }

    private void loadHistory(){
        all.removeAllViews();
        btnCom.setBackgroundColor(this.getResources().getColor(R.color.trueWhite));
        btnHisto.setBackgroundColor(this.getResources().getColor(R.color.lightColor));
        btnStats.setBackgroundColor(this.getResources().getColor(R.color.trueWhite));
        cargaHistorial();
    }

    private void loadStatistics(){
        all.removeAllViews();
        btnCom.setBackgroundColor(this.getResources().getColor(R.color.trueWhite));
        btnHisto.setBackgroundColor(this.getResources().getColor(R.color.trueWhite));
        btnStats.setBackgroundColor(this.getResources().getColor(R.color.lightColor));
    }

    private void cargaHistorial(){
        int last = 0;
        for(int i = 0;i<6;i++){
            int textId = View.generateViewId();
            int nameId = View.generateViewId();
            int dateId = View.generateViewId();
            int butId = View.generateViewId();

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
            rlp.height = 280;
            lay.setBackground(ContextCompat.getDrawable(this,R.drawable.small_lay_custom_wh));

            TextView title = new TextView(this);
            title.setId(textId);
            title.setText("Nombre del pedido");
            Typeface typeface = ResourcesCompat.getFont(this, R.font.pop_med);
            title.setTypeface(typeface);
            title.setTextColor(this.getResources().getColor(R.color.offColor));
            title.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
            lay.addView(title);

            TextView name = new TextView(this);
            name.setId(nameId);
            name.setText("Nombre del transportista");
            Typeface typeface2 = ResourcesCompat.getFont(this, R.font.pop_light);
            name.setTypeface(typeface2);
            name.setTextColor(this.getResources().getColor(R.color.offColor));
            name.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
            lay.addView(name);

            TextView dateW = new TextView(this);
            dateW.setId(dateId);
            dateW.setText("Fecha del pedido");
            dateW.setTypeface(typeface2);
            dateW.setTextColor(this.getResources().getColor(R.color.offColor));
            dateW.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
            lay.addView(dateW);

            ImageButton butVer = new ImageButton(this);
            butVer.setId(butId);
            butVer.setImageResource(R.drawable.info);
            butVer.setBackgroundColor(this.getResources().getColor(R.color.whiteBack));
            butVer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openInfo();
                }
            });
            lay.addView(butVer);


            ConstraintSet cs = new ConstraintSet();
            cs.clone(lay);
            cs.connect(textId,ConstraintSet.TOP,lay.getId(),ConstraintSet.TOP,15);
            cs.connect(textId,ConstraintSet.LEFT,lay.getId(),ConstraintSet.LEFT,15);
            cs.connect(nameId,ConstraintSet.TOP,textId,ConstraintSet.BOTTOM,10);
            cs.connect(nameId,ConstraintSet.LEFT,lay.getId(),ConstraintSet.LEFT,15);
            cs.connect(dateId,ConstraintSet.TOP,nameId,ConstraintSet.BOTTOM,10);
            cs.connect(dateId,ConstraintSet.LEFT,lay.getId(),ConstraintSet.LEFT,15);
            cs.connect(butId,ConstraintSet.BOTTOM,lay.getId(),ConstraintSet.BOTTOM,40);
            cs.connect(butId,ConstraintSet.END,lay.getId(),ConstraintSet.END,60);
            cs.applyTo(lay);

            all.addView(lay);
        }
    }

    private void openInfo(){
        Intent intent = new Intent(this, InfoPedido.class);
        startActivity(intent);
        this.overridePendingTransition(0, 0);
    }
    float promedio;
    float total;
    float suma;

}
