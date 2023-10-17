//package com.example.mediaplayer.viewmodel;
//
//import android.annotation.SuppressLint;
//import android.media.MediaPlayer;
//import android.net.Uri;
//import android.os.Handler;
//import android.os.Message;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.MediaController;
//import android.widget.SeekBar;
//import android.widget.VideoView;
//
//import androidx.annotation.NonNull;
//
//
//public class MainViewModel{
//    boolean isPrepared = false;
//    boolean isTouch = false;
//    private boolean isPlaying = false;
//    private Uri uri;
//    private VideoView videoView;
//    private Button playBtn, stopBtn;
//    private SeekBar seekBar;
//
//    MediaController mediaController;
//
//    public MainViewModel(VideoView videoView, Button playBtn, Button stopBtn, SeekBar seekBar) {
//        this.videoView = videoView;
//        this.playBtn = playBtn;
//        this.stopBtn = stopBtn;
//        this.seekBar = seekBar;
//
//        this.uri = Uri.parse("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4");
//        videoView.setVideoURI(uri);
//        videoView.requestFocus();
//
//        setButtonListeners(); // 버튼 리스너 설정
//
//
//        //재생 준비가 완료 되었을때
//        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//            @Override
//            public void onPrepared(MediaPlayer mediaPlayer) {   //재생 준비가 완료 되었을때
//                isPrepared = true;
//                seekBar.setMax(videoView.getDuration());    //getDuration -> 총 재생 시간을 ms 가져옴
//            }
//        });
//        //재생이 끝났을때
//        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer mediaPlayer) { //재생이 끝났을때
//
//            }
//        });
//
//
//        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
//
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//                isTouch = true;
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//                videoView.seekTo(seekBar.getProgress());    //seekTo() -> 해당하는 재생 위치로 이동
//                isTouch = false;
//            }
//        });
//
//
//        mediaController = new MediaController(videoView.getContext());      //???
//        videoView.setMediaController(mediaController);
//
//        handler.sendEmptyMessageDelayed(0, 1000);
//
//
//    }
//
//    private void setButtonListeners() {
//        playBtn.setOnClickListener(view -> isPlay());
//        stopBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                isStop();
//            }
//        });
//    }
//
//    private void isStop() {
//        isPlaying = false;
//        playBtn.setText("재생");
//        videoView.pause();
//    }
//
//    private void isPlay() {
//        if (videoView.isPlaying()){
//            videoView.pause();
//            playBtn.setText("재생");
//        }else {
//            videoView.start();
//            playBtn.setText("일시정지");
//        }
//    }
//
//
//    @SuppressLint("HandlerLeak")
//    Handler handler = new Handler(){
//        @Override
//        public void handleMessage(@NonNull Message msg) {
//            super.handleMessage(msg);
//            handler.sendEmptyMessageDelayed(0,500);
//            if(!isTouch){
//                seekBar.setProgress(videoView.getCurrentPosition());
//            }
//
//            Log.d("aabb","pos: "+ videoView.getCurrentPosition());
//        }
//    };
//
//
//}
