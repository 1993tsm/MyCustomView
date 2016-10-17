package com.example.administrator.mycustomview;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {
    private CircleView cv;
    private EraserView ev;
    private PathEffectView pathEffectView;
    private ECGView ecgView;
    private BrickView brickView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // cv = (CircleView) findViewById(R.id.cv);
       // ev = (EraserView) findViewById(R.id.ev);
       // ecgView = (ECGView) findViewById(R.id.ev);

      //  new Thread(cv).start();
        brickView = (BrickView) findViewById(R.id.ev);
    }
}
