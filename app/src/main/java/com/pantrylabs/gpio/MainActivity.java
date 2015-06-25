package com.pantrylabs.gpio;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.stericson.RootTools.RootTools;
import com.stericson.RootTools.execution.CommandCapture;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class MainActivity extends Activity {

    @InjectView(R.id.value)
    TextView value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
    }

    @OnClick(R.id.set_high)
    public void onSetHighClick() {
        CommandCapture command = new CommandCapture(0, "echo out > /sys/class/gpio/gpio124/direction");
        try {
            RootTools.getShell(true).add(command).waitForFinish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @OnClick(R.id.set_low)
    public void onSetLowClick() {
        CommandCapture command = new CommandCapture(0, "echo 1 > /sys/class/gpio/gpio124/value");
        try {
            RootTools.getShell(true).add(command).waitForFinish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @OnClick(R.id.read)
    public void onReadValueClick() {
        CommandCapture command = new CommandCapture(0, "cat /sys/class/gpio/gpio123/value") {
            @Override
            public void output(int id, final String line) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        value.setText(line);
                    }
                });
            }
        };
        try {
            RootTools.getShell(true).add(command).waitForFinish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
