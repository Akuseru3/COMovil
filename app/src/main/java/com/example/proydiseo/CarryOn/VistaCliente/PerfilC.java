package com.example.proydiseo.CarryOn.VistaCliente;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
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
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proydiseo.CarryOn.Modelo.Conexion;
import com.example.proydiseo.CarryOn.Modelo.Estadisticas;
import com.example.proydiseo.CarryOn.Modelo.ExtraTools;
import com.example.proydiseo.R;
import com.example.proydiseo.CarryOn.Modelo.UserData;
import com.example.proydiseo.CarryOn.Controlador.Usuario;
import com.example.proydiseo.CarryOn.VistaGeneral.MainActivity;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class PerfilC extends AppCompatActivity {
    private Button btnCerrarSesion;
    private ImageButton btnMas;
    private ImageButton btnSegui;
    private Button btnCom;
    private Button btnHisto;
    private Button btnStats;
    private int backButtonCount;
    private Button btnEdit;
    private LinearLayout all;
    private String correo;
    private Usuario actual;
    private String total;
    ArrayList<BarEntry> mesMontoEntries;
    ArrayList<BarEntry> pedidosMes;
    ArrayList<BarEntry> valoracionMes;
    ArrayList pieEntries = new ArrayList();
    ArrayList names = new ArrayList();
    ArrayList<String> topEntries = new ArrayList();
    ArrayList<ArrayList<String>> listEntries = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.perfil_lay);
        if(Build.VERSION.SDK_INT >= 21){
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.backColor));
        }
        fixLayouts();
        fixSw();

        all = findViewById(R.id.allPCinfo);
        btnMas = findViewById(R.id.btnMas);
        btnMas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                añadirPedido();
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

        //Boton de la seccion de comentarios en el perfil
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

        btnEdit = findViewById(R.id.btnEditar);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEditor();
            }
        });

        correo = getIntent().getStringExtra("USER_MAIL");

        TextView tipo = findViewById(R.id.txtV_tipo);
        tipo.setText("Cliente");
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

    private class loadEstadisticas extends AsyncTask<Object, Object, Cursor> {

        @Override
        protected Cursor doInBackground(Object... params) {
            try
            {
                String result = Estadisticas.getMonto(correo);
                total = result;
                ArrayList<BarEntry> entries = Estadisticas.getMontoMes(correo,"2020");
                mesMontoEntries = entries;
                ArrayList<BarEntry> entries2 = Estadisticas.cantidadPedidosMes(correo,"2020");
                pedidosMes = entries2;
                ArrayList<BarEntry> entries3 = Estadisticas.valoracionPromedioMes(correo,"2020");
                valoracionMes = entries3;
                ArrayList<ArrayList<String>> pieData= Estadisticas.establecimientosMasUsados(correo);
                for(int i=0;i<pieData.size();i++){
                    names.add(pieData.get(i).get(2));
                    pieEntries.add(new Entry(Integer.parseInt(pieData.get(i).get(1)),Integer.parseInt(pieData.get(i).get(0))));
                }
                listEntries = Estadisticas.topTransportistas(correo);
                return null;
            }
            catch (Exception e)
            { e.printStackTrace();
                return null;}

        }

        @Override
        protected void onPostExecute(Cursor result) {
            cargaStatistics();
        }
    }

    private void fillUserInfo(){
        TextView nombre  = findViewById(R.id.txtV_nombre);
        nombre.setText(actual.nombre +" "+ actual.apellidos);
        TextView correo  = findViewById(R.id.txtV_correo);
        correo.setText(actual.correo);
        TextView numero  = findViewById(R.id.txtV_correo2);
        numero.setText(actual.telefono);
        TextView fecha  = findViewById(R.id.txtV_correo3);
        fecha.setText(ExtraTools.generateDate(actual.fecha));
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
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
            imageView.setColorFilter(ContextCompat.getColor(this, R.color.offColor), android.graphics.PorterDuff.Mode.SRC_IN);
            imageView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT));
            imageView.getLayoutParams().height = pixels;
            imageView.getLayoutParams().width = pixels;
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

            /*ImageButton butVer = new ImageButton(this);
            butVer.setId(butId);
            butVer.setImageResource(R.drawable.info);
            butVer.setBackgroundColor(this.getResources().getColor(R.color.whiteBack));
            butVer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openInfo();
                }
            });
            lay.addView(butVer);*/

            RelativeLayout.LayoutParams paramsbt2 = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);

            Button butVer = new Button(this);
            butVer.setId(butId);
            butVer.setText("Ver detalles");
            butVer.setLayoutParams(paramsbt2);
            ViewGroup.LayoutParams rlp3 = butVer.getLayoutParams();
            rlp3.height = 120;
            rlp3.width = 350;
            butVer.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL);
            butVer.setTextColor(this.getResources().getColor(R.color.offColor));
            butVer.setBackground(this.getResources().getDrawable(R.drawable.wh_round_txt));
            butVer.setCompoundDrawablesWithIntrinsicBounds(R.drawable.info, 0, 0, 0);
            butVer.setAllCaps(false);
            butVer.setTypeface(typeface);
            butVer.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
            butVer.setPadding(10,0,0,0);
            lay.addView(butVer);


            ConstraintSet cs = new ConstraintSet();
            cs.clone(lay);
            cs.connect(textId,ConstraintSet.TOP,lay.getId(),ConstraintSet.TOP,15);
            cs.connect(textId,ConstraintSet.LEFT,lay.getId(),ConstraintSet.LEFT,15);
            cs.connect(nameId,ConstraintSet.TOP,textId,ConstraintSet.BOTTOM,10);
            cs.connect(nameId,ConstraintSet.LEFT,lay.getId(),ConstraintSet.LEFT,15);
            cs.connect(dateId,ConstraintSet.TOP,nameId,ConstraintSet.BOTTOM,10);
            cs.connect(dateId,ConstraintSet.LEFT,lay.getId(),ConstraintSet.LEFT,15);
            cs.connect(butId,ConstraintSet.BOTTOM,lay.getId(),ConstraintSet.BOTTOM,50);
            cs.connect(butId,ConstraintSet.END,lay.getId(),ConstraintSet.END,50);
            cs.applyTo(lay);

            all.addView(lay);
        }
    }

    private void openInfo(){
        Intent intent = new Intent(this, InfoPedidoCSee.class);
        startActivity(intent);
        this.overridePendingTransition(0, 0);
    }

    private void loadStatistics(){
        all.removeAllViews();
        btnCom.setBackgroundColor(this.getResources().getColor(R.color.trueWhite));
        btnHisto.setBackgroundColor(this.getResources().getColor(R.color.trueWhite));
        btnStats.setBackgroundColor(this.getResources().getColor(R.color.lightColor));
        new loadEstadisticas().execute();
    }

    private void cargaStatistics(){



        int nameId = View.generateViewId();
        int costId = View.generateViewId();
        int chartId = View.generateViewId();
        int chart2Id = View.generateViewId();
        int chart3Id = View.generateViewId();
        int chart4Id = View.generateViewId();
        int topLay = View.generateViewId();

        ConstraintLayout lay = new ConstraintLayout(this);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        lay.setLayoutParams(params);
        ViewGroup.LayoutParams rlp = lay.getLayoutParams();

        rlp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        rlp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        lay.setBackground(ContextCompat.getDrawable(this,R.drawable.small_lay_custom_wh));

        TextView name = new TextView(this);
        name.setId(nameId);
        name.setText("Monto total usado en la aplicación");
        Typeface typeface2 = ResourcesCompat.getFont(this, R.font.pop_light);
        name.setTypeface(typeface2);
        name.setTextColor(this.getResources().getColor(R.color.offColor));
        name.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
        lay.addView(name);

        TextView cost = new TextView(this);
        cost.setId(costId);
        if(total!= null){
            cost.setText(total);
        }
        else{
            cost.setText("No ha consumido");
        }
        Typeface typeface3 = ResourcesCompat.getFont(this, R.font.pop_light);
        cost.setTypeface(typeface3);
        cost.setTextColor(this.getResources().getColor(R.color.offColor));
        cost.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
        lay.addView(cost);

        BarChart chart = new BarChart(this);
        chart.setDescription("");
        chart.setId(chartId);
        chart.setLayoutParams(new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT));

        ViewGroup.LayoutParams rlp2 = chart.getLayoutParams();

        rlp2.width = ViewGroup.LayoutParams.MATCH_PARENT;
        rlp2.height = 1000;


        BarDataSet barDataset = new BarDataSet(mesMontoEntries,"Montos");

        ArrayList<String> dates = new ArrayList<>();
        dates.add("Enero");
        dates.add("Febrero");
        dates.add("Marzo");
        dates.add("Abril");
        dates.add("Mayo");
        dates.add("Junio");
        dates.add("Julio");
        dates.add("Agosto");
        dates.add("Setiembre");
        dates.add("Octubre");
        dates.add("Noviembre");
        dates.add("Diciembre");

        BarData data = new BarData(dates,barDataset);
        barDataset.setColor(Color.rgb(105, 240, 175));
        barDataset.setValueTextSize(10);
        barDataset.setValueTypeface(typeface2);
        chart.setData(data);

        chart.setTouchEnabled(true);
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);
        chart.getXAxis().setLabelRotationAngle(60);
        chart.getXAxis().setLabelsToSkip(0);

        lay.addView(chart); /*ver aqui*/
        PieChart chart3 = new PieChart(this);
        chart3.setDescription("");
        chart3.setId(chart3Id);
        chart3.setLayoutParams(new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT));

        ViewGroup.LayoutParams rlp4 = chart3.getLayoutParams();

        rlp4.width = ViewGroup.LayoutParams.MATCH_PARENT;
        rlp4.height = 1000;

        PieDataSet dataSet = new PieDataSet(pieEntries, "Establecimientos usados");
        dataSet.setValueTextSize(12);
        dataSet.setValueTypeface(typeface2);
        PieData pieData = new PieData(names, dataSet);
        chart3.setData(pieData);
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        chart3.animateXY(5000, 5000);
        lay.addView(chart3);

        BarChart chart2 = new BarChart(this);
        chart2.setDescription("");
        chart2.setId(chart2Id);
        chart2.setLayoutParams(new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT));

        ViewGroup.LayoutParams rlp3 = chart2.getLayoutParams();

        rlp3.width = ViewGroup.LayoutParams.MATCH_PARENT;
        rlp3.height = 1000;


        BarDataSet barDataset2 = new BarDataSet(pedidosMes,"Veces usados");
        barDataset2.setColor(Color.rgb(105, 240, 175));
        barDataset2.setValueTextSize(10);
        barDataset2.setValueTypeface(typeface2);
        BarData data2 = new BarData(dates,barDataset2);
        chart2.setData(data2);

        chart2.setTouchEnabled(true);
        chart2.setDragEnabled(true);
        chart2.setScaleEnabled(true);
        chart2.getXAxis().setLabelRotationAngle(60);
        chart2.getXAxis().setLabelsToSkip(0);

        lay.addView(chart2);/*ver aqui*/


        LinearLayout top = new LinearLayout(this);
        top.setId(topLay);
        LinearLayout.LayoutParams topParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        top.setLayoutParams(topParams);
        ViewGroup.LayoutParams topRlp = top.getLayoutParams();

        topRlp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        topRlp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        top.setBackground(ContextCompat.getDrawable(this,R.drawable.small_lay_custom_wh));

        int count=0;
        for(int i=0;i<listEntries.size();i++){
            TextView text = new TextView(this);
            text.setText(listEntries.get(i).get(0)+"-"+listEntries.get(i).get(1)+"-"+listEntries.get(i).get(2)+"|");
            text.setTypeface(typeface2);
            text.setTextColor(this.getResources().getColor(R.color.offColor));
            text.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);


            RelativeLayout.LayoutParams textParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            if(i!=0)
                textParams.addRule(RelativeLayout.BELOW,count);
            text.setLayoutParams(textParams);
            ViewGroup.LayoutParams textRlp = text.getLayoutParams();
            //textRlp.width = ViewGroup.LayoutParams.MATCH_PARENT;

            count= View.generateViewId();

            text.setId(count);

            top.addView(text);
        }


        lay.addView(top);

        BarChart chart4 = new BarChart(this);
        chart4.setDescription("");
        chart4.setId(chart4Id);
        chart4.setLayoutParams(new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT));

        ViewGroup.LayoutParams rlp5 = chart4.getLayoutParams();

        rlp5.width = ViewGroup.LayoutParams.MATCH_PARENT;
        rlp5.height = 1000;


        BarDataSet barDataset3 = new BarDataSet(valoracionMes,"Cantidad de valoracion"); /*come back*/
        barDataset3.setColor(Color.rgb(105, 240, 175));
        barDataset3.setValueTextSize(10);
        barDataset3.setValueTypeface(typeface2);
        BarData data3 = new BarData(dates,barDataset3);
        chart4.setData(data3);

        chart4.setTouchEnabled(true);
        chart4.setDragEnabled(true);
        chart4.setScaleEnabled(true);
        chart4.getXAxis().setLabelRotationAngle(60);
        chart4.getXAxis().setLabelsToSkip(0);

        lay.addView(chart4);/*ver aqui*/


        ConstraintSet cs = new ConstraintSet();
        cs.clone(lay);
        cs.connect(nameId,ConstraintSet.TOP,lay.getId(),ConstraintSet.TOP,15);
        cs.connect(nameId,ConstraintSet.LEFT,lay.getId(),ConstraintSet.LEFT,15);
        cs.connect(costId,ConstraintSet.TOP,nameId,ConstraintSet.BOTTOM,15);
        cs.connect(costId,ConstraintSet.LEFT,lay.getId(),ConstraintSet.LEFT,15);
        cs.connect(chartId,ConstraintSet.TOP,costId,ConstraintSet.BOTTOM,50);
        cs.connect(chartId,ConstraintSet.LEFT,lay.getId(),ConstraintSet.LEFT,0);
        cs.connect(chart2Id,ConstraintSet.TOP,chartId,ConstraintSet.BOTTOM,50);
        cs.connect(chart2Id,ConstraintSet.LEFT,lay.getId(),ConstraintSet.LEFT,0);
        cs.connect(chart3Id,ConstraintSet.TOP,chart2Id,ConstraintSet.BOTTOM,50);
        cs.connect(chart3Id,ConstraintSet.LEFT,lay.getId(),ConstraintSet.LEFT,0);
        cs.connect(topLay,ConstraintSet.TOP,chart3Id,ConstraintSet.BOTTOM,50);
        cs.connect(topLay,ConstraintSet.LEFT,lay.getId(),ConstraintSet.LEFT,0);
        cs.connect(chart4Id,ConstraintSet.TOP,topLay,ConstraintSet.BOTTOM,50);
        cs.connect(chart4Id,ConstraintSet.LEFT,lay.getId(),ConstraintSet.LEFT,0);
        cs.applyTo(lay);


        all.addView(lay);

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
        NestedScrollView layout = (NestedScrollView)findViewById(R.id.swPC);
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

    private void añadirPedido(){
        Intent intent = new Intent(this, AñadirC.class);
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

    private void openEditor(){
        Intent intent = new Intent(this, EditPerfilC.class);
        intent.putExtra("USER_NAME",actual.nombre);
        intent.putExtra("USER_BDAY",actual.fecha);
        intent.putExtra("USER_PHONE",actual.telefono);
        intent.putExtra("USER_LNAME",actual.apellidos);
        intent.putExtra("USER_MAIL",actual.correo);
        startActivity(intent);
        this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
