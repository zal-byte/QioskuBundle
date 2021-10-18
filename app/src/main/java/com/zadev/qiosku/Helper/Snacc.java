package com.zadev.qiosku.Helper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textview.MaterialTextView;
import com.zadev.qiosku.R;

public class Snacc {
    Activity activity;
    public Snacc(Activity activity)
    {
        this.activity = activity;
    }
    @SuppressLint("ResourceAsColor")
    public void Snackme(String msg, boolean type)
    {

        final Snackbar snackbar = Snackbar.make(this.activity.getWindow().getDecorView().getRootView(), "", Snackbar.LENGTH_LONG);

        View customSnackView = this.activity.getLayoutInflater().inflate(R.layout.snackbar_auth, null);

        final MaterialTextView txt_msg = customSnackView.findViewById(R.id.txt_msg);
        txt_msg.setText(msg);
        final MaterialCardView snac_card = customSnackView.findViewById(R.id.snac_card);
        if ( type == true )
        {
            //Success
            snac_card.setBackgroundColor(R.color.success);
        }else
        {
            snac_card.setBackgroundColor(R.color.error);
        }

        snackbar.getView().setBackgroundColor(Color.TRANSPARENT);

        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbar.getView();

        snackbarLayout.setPadding(0,0,0,0);


        snackbarLayout.addView(customSnackView);

        snackbar.show();
    }

}
