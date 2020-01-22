package com.example.proydiseo.CarryOn.VistaCliente;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.example.proydiseo.CarryOn.SpecialListener;
import com.example.proydiseo.R;
import com.example.proydiseo.CarryOn.Controlador.Establecimiento;
import com.example.proydiseo.CarryOn.Modelo.UserData;

import java.util.ArrayList;

public class Establecimientos extends AppCompatActivity {

    private ConstraintLayout all;
    private ArrayList<Establecimiento> actualD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_establecimientos);
        all = findViewById(R.id.inSwEstablecs);
        fixLayouts();
        new prepEst().execute();
    }

    private class prepEst extends AsyncTask<Object, Object, Cursor> {

        @Override
        protected Cursor doInBackground(Object... params) {
            actualD = UserData.getAllEstablish();
            return null;
        }

        @Override
        protected void onPostExecute(Cursor result) {
            loadEstablishments();
        }
    }

    private void loadEstablishments(){
        int lastR = 0;
        int lastT = 0;
        int cont = 0;
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        int screenHeight = size.x;
        int marg = screenHeight/17;
        for(int i = 0; i<actualD.size();i++){
            int butId = View.generateViewId();

            RelativeLayout.LayoutParams paramsbt2 = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            paramsbt2.width = 282;
            paramsbt2.height = 412;
            Typeface typeface = ResourcesCompat.getFont(this, R.font.pop_med);

            Button butVer = new Button(this);
            butVer.setId(butId);
            butVer.setText(actualD.get(i).nombre);
            butVer.setLayoutParams(paramsbt2);
            butVer.setGravity(Gravity.CENTER);
            butVer.setTextColor(this.getResources().getColor(R.color.offColor));
            butVer.setBackground(this.getResources().getDrawable(R.drawable.small_lay_custom_wh));
            Bitmap bitmap;
            if(actualD.get(i).image == null)
                bitmap = ((BitmapDrawable) this.getResources().getDrawable(R.drawable.carryon_basic)).getBitmap();
            else
                bitmap = ((BitmapDrawable) actualD.get(i).image).getBitmap();
            Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 280, 280, true));
            butVer.setCompoundDrawablesWithIntrinsicBounds(null, d , null, null);
            butVer.setAllCaps(false);
            butVer.setTypeface(typeface);
            butVer.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
            butVer.setOnClickListener(new SpecialListener(actualD.get(i).id) {
                @Override
                public void onClick(View v) {
                    Button b = (Button)v;
                    String name = b.getText().toString();
                    retEstablish(this.id,name);
                }
            });
            all.addView(butVer);

            ConstraintSet cs = new ConstraintSet();
            cs.clone(all);
            if(cont==0)
                cs.connect(butId,ConstraintSet.LEFT,all.getId(),ConstraintSet.LEFT,marg);
            if(cont!=0)
                cs.connect(butId,ConstraintSet.LEFT,lastR,ConstraintSet.RIGHT,marg);
            if(i>2)
                cs.connect(butId,ConstraintSet.TOP,lastT,ConstraintSet.BOTTOM,60);
            else
                cs.connect(butId,ConstraintSet.TOP,all.getId(),ConstraintSet.TOP,60);

            cont++;

            if(cont>2){
                cont = 0;
                lastT = butId;
            }

            lastR = butId;

            cs.applyTo(all);
        }
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
    }

    private void retEstablish(String id,String name){
        Intent intent = new Intent(this, AñadirC.class);
        intent.putExtra("id_estab",id);
        intent.putExtra("estab", name);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
        this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        finish();
    }

    public void fixLayouts(){
        final float scale = getResources().getDisplayMetrics().density;
        int pixels = (int) (59 * scale + 0.5f);
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        int screenHeight = size.y;
        ScrollView layout = (ScrollView) findViewById(R.id.swEstablecs);
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
