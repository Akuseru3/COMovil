package com.example.proydiseo.CarryOn.VistaTransportista;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Point;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proydiseo.CarryOn.Controlador.Producto;
import com.example.proydiseo.CarryOn.Controlador.TodoPedido;
import com.example.proydiseo.CarryOn.Modelo.PedidoData;
import com.example.proydiseo.CarryOn.Modelo.PedidoDetallado;
import com.example.proydiseo.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class InfoPedido extends AppCompatActivity {
    private Button mapView;
    private Geocoder geocoder;
    private String idPedido;
    private PedidoDetallado pedidoD;
    private Button btnSolicitar;
    private String correo;
    private int resultado;
    private ArrayList<TodoPedido> carrito;
    private LinearLayout pedido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_ped_lay);
        mapView = findViewById(R.id.btnMapSel2);
        mapView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMap();
            }
        });
        loadLayout();

        pedido = findViewById(R.id.txtPedido);

        idPedido = getIntent().getStringExtra("ORDER_ID");
        correo = getIntent().getStringExtra("USER_MAIL");

        btnSolicitar = findViewById(R.id.btnAgregar);
        btnSolicitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prepareSolicitud();
            }
        });

        new loadPedido().execute();
    }

    private void prepareSolicitud(){
        CostDialog.defineValues(correo,idPedido);
        CostDialog costDefine = new CostDialog();
        costDefine.show(getSupportFragmentManager(),"Definición de costo");
    }


    private class loadPedido extends AsyncTask<Object, Object, Cursor> {

        @Override
        protected Cursor doInBackground(Object... params) {
            try
            {
                pedidoD = PedidoData.getPedido(idPedido);
                return null;
            }
            catch (Exception e)
            { e.printStackTrace();
                return null;}

        }

        @Override
        protected void onPostExecute(Cursor result) {
            showInfoPedido();
        }
    }

    private void showInfoPedido(){
        TextView txt1 = findViewById(R.id.textNombre2);
        txt1.setText(pedidoD.nombre);
        TextView txt2 = findViewById(R.id.textNombre3);
        txt2.setText(pedidoD.cliente);
        TextView txt3 = findViewById(R.id.twLocale);
        txt3.setText(coordAdress(pedidoD.lat,pedidoD.longt));
        TextView txt5 = findViewById(R.id.extraInfo);
        txt5.setText(pedidoD.señalesExtra);
        carrito = pedidoD.pedido;
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
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
    }

    private void openMap(){
        Intent intent = new Intent(this, LocationView.class);
        intent.putExtra("LAT",pedidoD.lat);
        intent.putExtra("LON",pedidoD.longt);
        startActivity(intent);
        this.overridePendingTransition(0, 0);
    }
    private void loadLayout(){
        final float scale = getResources().getDisplayMetrics().density;
        int pixels = (int) (59 * scale + 0.5f);
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        int screenHeight = size.y;
        ScrollView sw = (ScrollView)findViewById(R.id.swAddp);
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

    private String coordAdress(double lat, double lon){
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(lat, lon, 1);
            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            return address + "\n" + city;
        }catch(Exception e){
            return "";
        }
    }
}
