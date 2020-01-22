package com.example.proydiseo.CarryOn;

import android.view.View;

public class SpecialListenerDouble implements View.OnClickListener {
    public String id;
    public String id2;
    public SpecialListenerDouble(String id,String id2) {
        this.id = id;
        this.id2 = id2;
    }

    @Override
    public void onClick(View v)
    {
        //read your lovely variable
    }
}
