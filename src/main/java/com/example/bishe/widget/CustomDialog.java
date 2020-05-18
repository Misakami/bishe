package com.example.bishe.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.bishe.R;

public class CustomDialog extends AlertDialog {
    private TextView tvTitle;
    private TextView tvContent;
    private String title;
    private String content;

    public CustomDialog(Context context) {
        super(context, R.style.CustomDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_course);

        setCanceledOnTouchOutside(true);

        //设置对话框的显示位置
        setDialogStartPositon();

        //绑定控件
        initView();

        //初始化个组件的内容
        initText();

    }

    private void setDialogStartPositon() {
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        window.setAttributes(lp);
    }


    private void initText() {
        if (title != null) tvTitle.setText(title);
        if (content != null) tvContent.setText(content);
    }

    public CustomDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public CustomDialog setMessage(String content) {
        this.content = content;
        return this;
    }

    private void initView() {
        tvTitle = findViewById(R.id.dialog_title);
        tvContent = findViewById(R.id.dialog_content);
    }

}
