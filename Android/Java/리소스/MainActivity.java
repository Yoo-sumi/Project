package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.myapplication.databinding.ActivityMainBinding;

import static com.example.myapplication.R.drawable.giraffe;
import static com.example.myapplication.R.drawable.lion;
import static com.example.myapplication.R.drawable.tiger;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.memu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.animalSelect:
                AlertDialog.Builder builder;
                builder=new AlertDialog.Builder(this);
                builder.setTitle(R.string.select);
                builder.setIcon(R.mipmap.ic_launcher);
                builder.setItems(R.array.animals, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Animation anim=null;
                        switch (which){
                            case 0:
                                binding.imageView.setImageResource(lion);
                                break;
                            case 1:
                                binding.imageView.setImageResource(tiger);
                                break;
                            case 2:
                                binding.imageView.setImageResource(giraffe);
                                break;

                        }
                        anim= AnimationUtils.loadAnimation(MainActivity.this,R.anim.scale);
                        binding.imageView.startAnimation(anim);
                    }
                });
                builder.create().show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
