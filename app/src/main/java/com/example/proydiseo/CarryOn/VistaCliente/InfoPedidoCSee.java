package com.example.proydiseo.CarryOn.VistaCliente;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;

import com.example.proydiseo.R;
import com.example.proydiseo.CarryOn.VistaTransportista.LocationView;

public class InfoPedidoCSee extends AppCompatActivity {
    private Button mapView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_ped_see_c);
        mapView = findViewById(R.id.btnMapSel2);
        mapView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMap();
            }
        });
        loadLayout();
    }

    private void openMap(){
        Intent intent = new Intent(this, LocationView.class);
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
}
