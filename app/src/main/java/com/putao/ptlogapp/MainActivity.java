package com.putao.ptlogapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.putao.ptlog.PTLog;
import static com.putao.ptlog.PTLog.DEBUG;


public class MainActivity extends AppCompatActivity {
    static final String TAG = MainActivity.class.getSimpleName();

    private Button btn_debug;
    private Button btn_info;
    private Button btn_error;
    private Button btn_log_browse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PTLog.init(getApplicationContext(), DEBUG);

        btn_debug = (Button) this.findViewById(R.id.btn_debug);
        btn_debug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PTLog.d("hello world ------ debug");
                PTLog.d("debug a = %d", 123);
            }
        });

        btn_info = (Button)this.findViewById(R.id.btn_info);
        btn_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PTLog.i("hello world ------ info");
                PTLog.i("info b = %d", 456);
            }
        });

        btn_error = (Button)this.findViewById(R.id.btn_error);
        btn_error.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PTLog.e("hello world ------ error");
                PTLog.e("error c = %d", 789);
            }
        });

        btn_log_browse = (Button)this.findViewById(R.id.btn_log_browse);
        btn_log_browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PTLog.showViewer();
            }
        });
    }
}
