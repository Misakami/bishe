package com.example.bishe.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.bishe.R;

import org.angmarch.views.NiceSpinner;
import org.angmarch.views.OnSpinnerItemSelectedListener;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class EventDialog extends AlertDialog {
    private OnClickListener listener;
    private TextView cancel;
    private TextView confirm;
    private TextView td_content;
    private String message;
    private NiceSpinner spinner;
    private int[] time = new int[]{15,30,60,120,1440};

    public EventDialog(Context context) {
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
        final int[] i = new int[1];
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        spinner.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
                i[0] = time[position];
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null){
                    listener.onConfirmClick(i[0]);
                }
                dismiss();
            }
        });
    }

    private void initView() {
        cancel = findViewById(R.id.cancel_button);
        confirm = findViewById(R.id.confirm_button);
        td_content = findViewById(R.id.td_content);
        spinner = findViewById(R.id.spinner);
        if (message != null){
            td_content.setText(message);
        }
        spinner.setVisibility(View.VISIBLE);
        spinner.setSelectedIndex(0);
    }

    public EventDialog setMessage(String message){
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

    public EventDialog setListener(OnClickListener clickListener){
        listener = clickListener;
        return this;
    }

    public interface OnClickListener{
        void onConfirmClick(int time);
    }
}
