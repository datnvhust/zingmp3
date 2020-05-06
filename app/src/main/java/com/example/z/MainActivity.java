package com.example.z;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imagePlay, imageBack, imageNext;

    private MediaPlayer mediaPlayer;
    private int[] soundIds = new int[]{R.raw.aa, R.raw.ab, R.raw.ac};
    private int currentPosition = 0;
    private int currentTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupViews();
        initMediaPlayer();
        listenEvents();
        playMusic(soundIds[currentPosition]);
    }

    private void setupViews() {
        imagePlay = findViewById(R.id.imagePlay);
        imageBack = findViewById(R.id.imageBack);
        imageNext = findViewById(R.id.imageNext);
    }

    private void initMediaPlayer() {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setLooping(true);
    }

    private void listenEvents() {
        imagePlay.setOnClickListener(this);
        imageBack.setOnClickListener(this);
        imageNext.setOnClickListener(this);
    }

    private void playMusic(int rawId) {
        mediaPlayer = MediaPlayer.create(this, rawId);
        mediaPlayer.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageBack:
                back();
                break;
            case R.id.imagePlay:
                if (mediaPlayer.isPlaying()) {
                    pause();
                } else {
                    continueMusic();
                }
                break;
            case R.id.imageNext:
                next();
                break;
        }
    }

    private void pause() {
        currentTime = mediaPlayer.getCurrentPosition();
        mediaPlayer.stop();
        imagePlay.setImageResource(R.drawable.ic_play_arrow_black_24dp);
    }

    private void continueMusic() {
        playMusic(soundIds[currentPosition]);
        mediaPlayer.seekTo(currentTime);
        imagePlay.setImageResource(R.drawable.ic_pause_black_24dp);

    }

    private void next() {
        mediaPlayer.stop();
        playMusic(soundIds[(++currentPosition) % soundIds.length]);
    }

    private void back() {
        mediaPlayer.stop();
        playMusic(soundIds[(--currentPosition + soundIds.length) % soundIds.length]);
    }
}
