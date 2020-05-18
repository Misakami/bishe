package com.example.bishe.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.bishe.R;

public class LoadingDialog extends AlertDialog {
    private Context mContext;
    ImageView imageView;

    public LoadingDialog(Context context) {
        super(context,R.style.CustomDialog);
        mContext=context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView() {
        setContentView(R.layout.dialog_loading);
        imageView = (ImageView) findViewById(R.id.loading_view);
        AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getDrawable();
        animationDrawable.start();


        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(params);
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }
}
