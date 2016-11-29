package com.example.administrator.mycustomview;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.administrator.mycustomview.test.TestButton;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends Activity implements View.OnTouchListener, View.OnClickListener {
    private CircleView cv;
    private EraserView ev;
    private PathEffectView pathEffectView;
    private ECGView ecgView;
    private BrickView brickView;

//    @BindView(R.id.myLayout)
//    LinearLayout layout;
//    @BindView(R.id.my_bt)
//    TestButton testButton;
    private LinearLayout layout;
    private TestButton testButton;



    private static String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.iconview);
        layout = (LinearLayout) findViewById(R.id.myLayout);
        testButton = (TestButton) findViewById(R.id.my_bt);

       // ButterKnife.bind(this);
       // cv = (CircleView) findViewById(R.id.cv);
       // ev = (EraserView) findViewById(R.id.ev);
       // ecgView = (ECGView) findViewById(R.id.ev);

      //  new Thread(cv).start();
       // brickView = (BrickView) findViewById(R.id.ev);
        layout.setOnTouchListener(this);
        testButton.setOnTouchListener(this);

        layout.setOnClickListener(this);
        testButton.setOnClickListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.i(TAG, "OnTouchListener--onTouch-- action="+event.getAction()+" --"+v);

        return false;
    }

    @Override
    public void onClick(View v) {
        Log.i(TAG, "OnClickListener--onClick--"+v);
    }
}
