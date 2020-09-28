package se.rasmusfalk.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

public class MainActivity extends AppCompatActivity {
    private long timeWhenStopped = 0;
    private boolean isStopped = true;

    Button toggle;
    Button reset;
    Chronometer counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toggle = findViewById(R.id.toggle);
        reset = findViewById(R.id.reset);
        counter = findViewById(R.id.counter);

        reset.setAlpha(0);

        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleCounter();
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeWhenStopped = 0;
                counter.stop();
                counter.setBase(SystemClock.elapsedRealtime());
                
                setToggleButton("Start");
                
                isStopped = true;
                reset.animate().alpha(0).setDuration(300);
            }
        });
    }

    public void toggleCounter() {
        if (isStopped) {
            stopCounter();
            return;
        }
        
        startCounter();
    }
    
    public void startCounter() {
        setToggleButton("Start");

        timeWhenStopped = counter.getBase() - SystemClock.elapsedRealtime();
        counter.stop();

        reset.animate().alpha(0).setDuration(300);
        isStopped = true;
    }
    
    public void stopCounter() {
        setToggleButton("Stop");

        reset.animate().alpha(1).setDuration(300);

        counter.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
        counter.start();

        isStopped = false;
    }
    
    public void setToggleButton(String label) {
        toggle.setText(label);
        toggle.setBackgroundResource(R.drawable.secondary_btn_background);
    }
}
