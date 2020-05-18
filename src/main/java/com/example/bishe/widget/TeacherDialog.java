package com.example.bishe.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.bishe.R;
import com.example.bishe.model.bean.Teacher;

public class TeacherDialog extends AlertDialog {
    private OnClickListener listener;
    private TextView cancel;
    private TextView confirm;
    private TextView td_content;
    private String message;

    public TeacherDialog(Context context) {
        super(context, R.style.CustomDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_teacher);

        setCanceledOnTouchOutside(false);

        //设置对话框的显示位置
        setDialogStartPositon();

        //绑定控件
        initView();

        //初始化点击
        initClick();
    }

    private void initClick() {
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null){
                    listener.onConfirmClick();
                }
                dismiss();
            }
        });
    }

    private void initView() {
        cancel = findViewById(R.id.cancel_button);
        confirm = findViewById(R.id.confirm_button);
        td_content = findViewById(R.id.td_content);
        if (message != null){
            td_content.setText(message);
        }
    }

    public TeacherDialog setMessage(String message){
        this.message = message;
        return this;
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

    public TeacherDialog setListener(OnClickListener clickListener){
        listener = clickListener;
        return this;
    }
    public interface OnClickListener{
        void onConfirmClick();
    }
}
