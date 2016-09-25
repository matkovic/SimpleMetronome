package com.mat.simplemetronome;

import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textViewBpm;
    SeekBar seekBarBpm;
    SoundPool soundPool;
    Switch switchOnOff;
    int soundIdTick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewBpm = (TextView) findViewById(R.id.textViewBPM);
        seekBarBpm = (SeekBar) findViewById(R.id.seekBarBPM);
        switchOnOff = (Switch) findViewById(R.id.switchOnOff);

        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        soundIdTick = soundPool.load(this, R.raw.tick_500ms, 1);

        seekBarBpm.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                playSound(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        switchOnOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    playSound(seekBarBpm.getProgress());
                }
                else {
                    soundPool.autoPause();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    public void onButtonDecreaseClick(View v){
        seekBarBpm.setProgress(seekBarBpm.getProgress()-1);
    }

    public void onButtonIncreaseClick(View v){
        seekBarBpm.setProgress(seekBarBpm.getProgress()+1);
    }

    private void playSound(int progress){
        int bpm = progress+30; //seekbar progress is from 0 to 230, add 30 so its from 30 to 260
        textViewBpm.setText(Integer.toString(bpm));
        float rate = bpm/60f;
        rate /= 2; //because its 500ms long sound

        if(switchOnOff.isChecked())
            soundPool.play(soundIdTick, 1, 1, 0, -1, rate);
    }
}
