package com.example.android.chatterbox;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.widget.TextView;

/**
 * Created by Kunal on 10-Nov-17.
 */

public class CustomDialog extends Dialog {
    Context c;
    TextView txt_dialog;
    int year;

    public CustomDialog(@NonNull Context context, int year) {
        super(context);
        this.c=context;
        this.year=year;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customdialog);
        txt_dialog=(TextView)findViewById(R.id.txt_dialogmessage);
        txt_dialog.setText("CSE "+year+"th year");
    }
}
