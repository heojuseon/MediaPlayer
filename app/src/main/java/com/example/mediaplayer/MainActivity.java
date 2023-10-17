package com.example.mediaplayer;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity {
    boolean isPrepared = false;
    boolean isTouch = false;
    double c_Time = 0;

    VideoView videoView;
    Button playBtn, stopBtn, moveBtn, backBtn;
    SeekBar seekBar;
    TextView totalTime, currentTime;

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        finID();

        Uri uri = Uri.parse("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4");
        videoView.setVideoURI(uri);
        videoView.requestFocus();   //requestFocus ??
        videoView.setOnPreparedListener(mediaPlayer -> {
            //재생 준비가 완료 되었을 때
            isPrepared = true;
            long finalTime = videoView.getDuration();
            seekBar.setMax((int) finalTime);
//            seekBar.setMax(videoView.getDuration());    //getDuration() -> 총 재생시간을 ms 가져옴

            totalTime.setText(String.format("%d : %d",
                    TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                    TimeUnit.MILLISECONDS.toSeconds((long) finalTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) finalTime))));
        });
        videoView.setOnCompletionListener(mediaPlayer -> {
            //재생 끝났을 때
        });


        //seekbar 관련 함수
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                isTouch = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                videoView.seekTo(seekBar.getProgress());    //getProgress ???
                isTouch = false;
            }
        });

        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);

        playBtn.setOnClickListener(view -> {
            if(videoView.isPlaying()){
                videoView.pause();
                playBtn.setText("재생");
            }else{
                videoView.start();
                playBtn.setText("일시정지");
            }
        });

        //아예 처음으로 정지
        stopBtn.setOnClickListener(view -> {
            videoView.pause();
            videoView.seekTo(0);
            playBtn.setText("재생");
        });

        moveBtn.setOnClickListener(view -> {
            videoView.seekTo(videoView.getCurrentPosition() + 10000);    //10초 뒤로
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoView.seekTo(videoView.getCurrentPosition() - 10000);    //10초 앞으로
            }
        });

        handler.sendEmptyMessageDelayed(0, 1000);

    }


    //핸들러로 계속해서 그 위치가 어딘지를 알려줘야한다. 현재 플레이되고 있는 시간을 알려줌.
    Handler handler = new Handler(Looper.getMainLooper()){  //Handler 는 Deprecated 되면서, 생성자로 Looper를 넣어서 사용 -> 버그 발생
        @SuppressLint("DefaultLocale")
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            handler.sendEmptyMessageDelayed(0, 500);    //0.5초마다 보내게
            if(!isTouch){
                seekBar.setProgress(videoView.getCurrentPosition());
            }

            c_Time = videoView.getCurrentPosition();
            currentTime.setText(String.format("%d : %d",
                    TimeUnit.MILLISECONDS.toMinutes((long) c_Time),
                    TimeUnit.MILLISECONDS.toSeconds((long) c_Time) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) c_Time))));

            Log.d("handler","pos: "+videoView.getCurrentPosition());
        }
    };


    private void finID() {
        videoView = findViewById(R.id.vv);
        playBtn = findViewById(R.id.play_bt);
        stopBtn = findViewById(R.id.stop_bt);
        moveBtn = findViewById(R.id.move_bt);
        seekBar = findViewById(R.id.seekBar);
        backBtn = findViewById(R.id.back_btn);
        totalTime = findViewById(R.id.total_time);
        currentTime = findViewById(R.id.current_time);
    }

}
