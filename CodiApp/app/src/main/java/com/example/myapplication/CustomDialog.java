package com.example.myapplication;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import de.hdodenhof.circleimageview.CircleImageView;

public class CustomDialog extends Dialog {

    private Context context;
    private ImageView logo;
    private TextView tv;
    public CustomDialog(@NonNull Context context) {
        super(context);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCanceledOnTouchOutside(false);

        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_dialog);
        logo = (ImageView) findViewById(R.id.loding);
        logo.setBackground(new ShapeDrawable(new OvalShape()));
        logo.setClipToOutline(true);
        Glide.with(context).asGif().diskCacheStrategy(DiskCacheStrategy.DATA).load(R.raw.loding).into(logo);
        tv = (TextView) findViewById(R.id.tv);
        tv.setText("Loding...");
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }
}
