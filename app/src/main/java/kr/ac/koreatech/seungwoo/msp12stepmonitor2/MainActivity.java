package kr.ac.koreatech.seungwoo.msp12stepmonitor2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView mAccelX;
    private TextView mAccelY;
    private TextView mAccelZ;
    private TextView stepsText;
    private int steps;

    private BroadcastReceiver MyStepReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals("kr.ac.koreatech.msp.stepmonitor")) {
                steps = intent.getIntExtra("steps", 0);
                stepsText.setText("steps: " + steps);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAccelX = (TextView)findViewById(R.id.accelX);
        mAccelY = (TextView)findViewById(R.id.accelY);
        mAccelZ = (TextView)findViewById(R.id.accelZ);

        stepsText = (TextView)findViewById(R.id.count);
    }

    @Override
    protected void onResume() {
        super.onResume();

        IntentFilter intentFilter = new IntentFilter("kr.ac.koreatech.msp.stepmonitor");
        registerReceiver(MyStepReceiver, intentFilter);

    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(MyStepReceiver);
    }

    // Start/Stop 버튼을 눌렀을 때 호출되는 콜백 메소드
    // Activity monitoring을 수행하는 service 시작/종료
    public void onClick(View v) {
        if(v.getId() == R.id.startMonitor) {
            Intent intent = new Intent(this, StepMonitor.class);
            startService(intent);
        } else if(v.getId() == R.id.stopMonitor) {
            stopService(new Intent(this, StepMonitor.class));
            stepsText.setText("steps: " + 0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
