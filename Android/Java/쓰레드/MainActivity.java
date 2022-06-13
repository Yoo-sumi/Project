package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.view.View;

import com.example.myapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    HandlerThread handlerThread;
    Handler handler;
    int num=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);

        binding.start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(handlerThread!=null && handlerThread.isAlive()){
                    return;
                }
                num=0;
                binding.progressBar.setProgress(num);

                handlerThread=new HandlerThread("My HandlerThread");
                handlerThread.start();
                handler=new Handler(handlerThread.getLooper()){
                    @Override
                    public void handleMessage(@NonNull Message msg) {
                        if(msg.what==0){
                            if(num>100){
                                getLooper().quit();
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    binding.progressBar.setProgress(num++);
                                }
                            });
                            Message m=Message.obtain();
                            m.what=0;
                            sendMessageDelayed(m,20);
                        }else if(msg.what==-1){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    num=0;
                                    binding.progressBar.setProgress(num);
                                }
                            });
                            getLooper().quit();
                        }
                    }
                };
                Message m=Message.obtain();
                m.what=0;
                handler.sendMessage(m);
            }
        });

        binding.stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(handlerThread!=null && handlerThread.isAlive()){
                    Message m=Message.obtain();
                    m.what=-1;
                    handler.sendMessage(m);
                }
            }
        });
    }
}
